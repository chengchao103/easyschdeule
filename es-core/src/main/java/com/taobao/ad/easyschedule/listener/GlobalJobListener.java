/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.listener;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.ad.easyschedule.bo.asyncjob.AsyncJobBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.bo.reportjobrt.ReportJobRtBO;
import com.taobao.ad.easyschedule.commons.Const;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.JobUtil;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.AsyncJobQueueDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.dataobject.LogsDO;
import com.taobao.ad.easyschedule.dataobject.ReportJobRtDO;
import com.taobao.ad.easyschedule.job.EsApplicationContext;
import com.taobao.ad.easyschedule.manager.message.MessageManager;

/**
 * 全局任务监听器
 * 
 * @author baimei
 */
public class GlobalJobListener implements JobListener {

	private static final Logger logger = LoggerFactory.getLogger(GlobalJobListener.class);

	public String getName() {
		return getClass().getSimpleName();
	}

	private ILogsBO logsBO;
	private MessageManager messageManager;

	private ReportJobRtBO reportJobRtBO;

	/**
	 * 准备执行
	 */
	public void jobToBeExecuted(JobExecutionContext context) {
		try {
			JobDetail jobDetail = context.getJobDetail();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			Long jobId = System.currentTimeMillis();
			context.put(Const.JOBID, jobId);
			long opsubtype;
			if (context.isRecovering()) {
				if ("true".equals(jobDataMap.getString(Constants.JOBDATA_SYNCHRONOUS))) {
					opsubtype = LogsDO.SUBTYPE_JOB_START_RECOVERING;
				} else {
					opsubtype = LogsDO.SUBTYPE_JOB_START_RECOVERING_ASYN;
				}
			} else {
				if ("true".equals(jobDataMap.getString(Constants.JOBDATA_SYNCHRONOUS))) {
					opsubtype = LogsDO.SUBTYPE_JOB_START;
				} else {
					opsubtype = LogsDO.SUBTYPE_JOB_START_ASYN;
				}
			}
			logsBO.insertSuccess(opsubtype, jobDetail.getGroup() + "|" + jobDetail.getName(), JobUtil.getJobData(jobDetail.getJobDataMap()), LogsDO.USER_SYS);

			/** 任务RT初始化 ,仅仅初始化开始时间 */
			ReportJobRtDO rt = new ReportJobRtDO();
			rt.setJobGroup(context.getJobDetail().getGroup());
			rt.setJobName(context.getJobDetail().getName());
			rt.setStartTime(jobId);
			rt.setSync(jobDataMap.getString(Constants.JOBDATA_SYNCHRONOUS));
			rt.setStatus(ReportJobRtDO.REPORT_JOB_RT_STATUS_EXECUTION);
			reportJobRtBO.insertReportJobRt(rt);
			
			//如果是异步任务,则插入异步队列
			if (Constants.JOBDATA_SYNCHRONOUS_VAL_FALSE.equals(jobDataMap.getString(Constants.JOBDATA_SYNCHRONOUS))) {
				AsyncJobQueueDO asyncJob = new AsyncJobQueueDO();
				asyncJob.setJobgroup(context.getJobDetail().getGroup());
				asyncJob.setJobname(context.getJobDetail().getName());
				asyncJob.setSignTime(jobId);
				asyncJob.setCompleteTime(jobId
						+ (jobDataMap.get(Constants.JOBDATA_COMPLETETIME) == null ? Constants.JOB_MAX_COMPLETE_TIMEOUT : jobDataMap
								.getLong(Constants.JOBDATA_COMPLETETIME)));
				String targetUrl = jobDataMap.getString(Constants.JOBDATA_TARGETURL);
				String runType = StringUtils.isEmpty(jobDataMap.getString(Constants.JOBDATA_RUNTYPE)) ? Constants.RUNTYPE_HASH : jobDataMap
						.getString(Constants.JOBDATA_RUNTYPE);
				if (Constants.RUNTYPE_HASH.equals(runType)) {
					asyncJob.setWaitCallNum(1);
				} else {
					asyncJob.setWaitCallNum(targetUrl.split(",").length);
				}
				//备注，asyncJobBO不能申明成成员变量是由于asyncJobBO中依赖了easyscheduler，而easyscheduler依赖了jobListener，造成循环依赖的场景，故而无法注入成功。
				AsyncJobBO asyncJobBO = (AsyncJobBO) EsApplicationContext.getBean("asyncJobBO");
				asyncJobBO.insertAsyncJob(asyncJob);

			}
		} catch (Exception e) {
			logger.error("jobToBeExecuted", e);
		}
	}

	/**
	 * 执行失败
	 */
	public void jobExecutionVetoed(JobExecutionContext context) {
		try {
			JobDetail jobDetail = context.getJobDetail();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			JobResult result = (JobResult) context.getResult();
			JSONObject json = (JSONObject) JSONObject.toJSON(result);
			long opsubtype;
			if (context.isRecovering()) {
				if ("true".equals(jobDataMap.getString(Constants.JOBDATA_SYNCHRONOUS))) {
					opsubtype = LogsDO.SUBTYPE_JOB_FAILED_RECOVERING;
				} else {
					opsubtype = LogsDO.SUBTYPE_JOB_FAILED_RECOVERING_ASYN;
				}
			} else {
				if ("true".equals(jobDataMap.getString(Constants.JOBDATA_SYNCHRONOUS))) {
					opsubtype = LogsDO.SUBTYPE_JOB_FAILED;
				} else {
					opsubtype = LogsDO.SUBTYPE_JOB_FAILED_ASYN;
				}
			}
			logsBO.insertFail(opsubtype, jobDetail.getGroup() + "|" + jobDetail.getName(), /*JobUtil.getJobData(jobDataMap) +*/ "result:" + json.toString(),
					LogsDO.USER_SYS);
			try {
				if (jobDataMap.containsKey(Constants.JOBDATA_REPEAT_ALARM_NUM)) {
					messageManager.sendFailMessage(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getGroup() + jobDetail.getName() + "_失败_"
							+ result.getResultMsg(), "result:" + json.toString(), jobDataMap.getInt(Constants.JOBDATA_REPEAT_ALARM_NUM));
				} else {
					messageManager.sendFailMessage(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getGroup() + jobDetail.getName() + "_失败_"
							+ result.getResultMsg(), "result:" + json.toString());
				}
			} catch (Exception e) {
				logger.error("jobExecutionVetoed:sendmail failed:" + "|" + jobDetail.getGroup() + "|" + jobDetail.getName());
			}
			// 更新RT和结束时间
			reportJobRtBO.updateReportJobRt((Long) context.get(Const.JOBID), jobDetail.getGroup(), jobDetail.getName(),
					ReportJobRtDO.REPORT_JOB_RT_STATUS_FAILED);
			
			//异步任务并且失败则删除异步队列
			if (Constants.JOBDATA_SYNCHRONOUS_VAL_FALSE.equals(jobDataMap.getString(Constants.JOBDATA_SYNCHRONOUS))) {
				AsyncJobBO asyncJobBO = (AsyncJobBO) EsApplicationContext.getBean("asyncJobBO");
				AsyncJobQueueDO asyncJob = new AsyncJobQueueDO();
				asyncJob.setJobgroup(context.getJobDetail().getGroup());
				asyncJob.setJobname(context.getJobDetail().getName());
				asyncJobBO.deleteAsyncJob(asyncJob);
			}
		} catch (Exception e) {
			logger.error("jobExecutionVetoed", e);
		}
	}

	/**
	 * 执行成功
	 */
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		try {
			JobDetail jobDetail = context.getJobDetail();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			long jobId = (Long) context.get(Const.JOBID);
			JobResult result = (JobResult) context.getResult();
			JSONObject json = (JSONObject) JSONObject.toJSON(result);
			if (result == null || !result.isSuccess()) {
				// 执行失败
				jobExecutionVetoed(context);
			} else {
				if (Constants.JOBDATA_SYNCHRONOUS_VAL_TRUE.equals(jobDataMap.getString(Constants.JOBDATA_SYNCHRONOUS))) {
					logsBO.insertSuccess(context.isRecovering() ? LogsDO.SUBTYPE_JOB_END_RECOVERING : LogsDO.SUBTYPE_JOB_END, jobDetail.getGroup() + "|"
							+ jobDetail.getName(), /*JobUtil.getJobData(jobDataMap) +*/ "result:" + json.toString(), LogsDO.USER_SYS);
					try {
						messageManager.sendSuccessMessage(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getGroup() + jobDetail.getName() + "_成功_"
								+ result.getResultMsg(), "result:" + json.toString());
					} catch (Exception e) {
						logger.error("jobWasExecuted:sendmail failed:" + "|" + jobDetail.getGroup() + "|" + jobDetail.getName());
					}
					// 更新RT和结束时间
					reportJobRtBO.updateReportJobRt(jobId, jobDetail.getGroup(), jobDetail.getName(), ReportJobRtDO.REPORT_JOB_RT_STATUS_SUCCESS);
				}  
			}
		} catch (Exception e) {
			logger.error("jobWasExecuted", e);
		}
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public ILogsBO getLogsBO() {
		return logsBO;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public void setReportJobRtBO(ReportJobRtBO reportJobRtBO) {
		this.reportJobRtBO = reportJobRtBO;
	}

}