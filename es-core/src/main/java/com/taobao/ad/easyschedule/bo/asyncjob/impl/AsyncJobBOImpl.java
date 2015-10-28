/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.asyncjob.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.ad.easyschedule.bo.asyncjob.AsyncJobBO;
import com.taobao.ad.easyschedule.bo.datatrackinglog.IDataTrackingLogBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.bo.reportjobrt.ReportJobRtBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.JobUtil;
import com.taobao.ad.easyschedule.dao.asyncjobqueue.AsyncJobQueueDAO;
import com.taobao.ad.easyschedule.dataobject.AsyncJobQueueDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.dataobject.LogsDO;
import com.taobao.ad.easyschedule.dataobject.ReportJobRtDO;
import com.taobao.ad.easyschedule.job.DataTrackingJob;
import com.taobao.ad.easyschedule.job.FileLineCountJob;
import com.taobao.ad.easyschedule.manager.message.MessageManager;

public class AsyncJobBOImpl implements AsyncJobBO {

	protected final static Logger logger = LoggerFactory.getLogger(AsyncJobBOImpl.class);

	private AsyncJobQueueDAO asyncJobQueueDAO;
	private MessageManager messageManager;
	private IDataTrackingLogBO dataTrackingLogBO;
	private ReportJobRtBO reportJobRtBO;
	private Scheduler easyscheduler;
	private ILogsBO logsBO;

	@Override
	public JobResult completeJob(JobDetail jobDetail, JobResult jobResult) {
		AsyncJobQueueDO asyncJob = new AsyncJobQueueDO();
		asyncJob.setJobgroup(jobDetail.getGroup());
		asyncJob.setJobname(jobDetail.getName());
		// 更新异步队列表中的回调次数
		deleteAsyncJobIfWaitNumIsZero(asyncJob);
		if (jobResult.isSuccess()) {
			doTrueComplete(jobDetail, jobResult);
		} else {
			doFalseComplete(jobDetail, jobResult);
		}
		// 更新异步任务的RT
		reportJobRtBO.updateReportJobRt(jobResult.getJobId() == null ? null : Long.parseLong(jobResult.getJobId()), jobDetail.getGroup(), jobDetail.getName(),
				jobResult.isSuccess() ? ReportJobRtDO.REPORT_JOB_RT_STATUS_SUCCESS : ReportJobRtDO.REPORT_JOB_RT_STATUS_FAILED);
		return JobResult.succcessResult();
	}

	@Override
	public JobResult checkAsyncJobQueue() {
		JobResult jobResult = JobResult.succcessResult();
		int count = asyncJobQueueDAO.getAsyncJobCount();
		int page = (count % Constants.DEFAULT_PAGE_SIZE == 0) ? count / Constants.DEFAULT_PAGE_SIZE : (count / Constants.DEFAULT_PAGE_SIZE + 1);
		AsyncJobQueueDO job = new AsyncJobQueueDO();
		job.setPerPageSize(Constants.DEFAULT_PAGE_SIZE);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 1; i <= page; i++) {
			job.setToPage(i);
			List<AsyncJobQueueDO> list = asyncJobQueueDAO.getAsyncJob(job);
			for (AsyncJobQueueDO async : list) {
				// 判断任务是否超时
				if (async.getCompleteTime() < System.currentTimeMillis()) {
					JobDetail jobDetail = null;
					try {
						jobDetail = easyscheduler.getJobDetail(async.getJobname(), async.getJobgroup());
					} catch (SchedulerException e) {
						jobResult = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "未知异常或参数非法！");
					}
					if (jobDetail == null) {
						asyncJobQueueDAO.deleteAsyncJobById(async.getId());
						// 任务被删除
						reportJobRtBO.updateReportJobRt(null, async.getJobgroup(), async.getJobname(), ReportJobRtDO.REPORT_JOB_RT_STATUS_DELETE);
						continue;
					}
					JobResult result = new JobResult();
					result.setSuccess(false);
					result.setResultCode(JobResult.RESULTCODE_JOB_EXPIRED);
					result.setResultMsg(async.getWaitCallNum() + "个调用没有正常返回!signTime:" + simple.format(new Date(async.getSignTime())));
					doFalseComplete(jobDetail, result);
					asyncJobQueueDAO.deleteAsyncJobById(async.getId());
					// 更新任务RT
					reportJobRtBO.updateReportJobRt(null, async.getJobgroup(), async.getJobname(), ReportJobRtDO.REPORT_JOB_RT_STATUS_TIME_OUT);
				} else if (async.getWaitCallNum() <= 0) {
					asyncJobQueueDAO.deleteAsyncJobById(async.getId());
					reportJobRtBO.updateReportJobRt(null, async.getJobgroup(), async.getJobname(), ReportJobRtDO.REPORT_JOB_RT_STATUS_SUCCESS);
				}
			}
		}
		return jobResult;
	}

	@Override
	public List<AsyncJobQueueDO> findAsyncJobs(AsyncJobQueueDO asyncJob) {
		return asyncJobQueueDAO.getAsyncJob(asyncJob);
	}

	@Override
	public void insertAsyncJob(AsyncJobQueueDO asyncJob) {
		asyncJob.setStatus(1);
		asyncJob.setCreateTime(new Date());
		asyncJob.setUpdateTime(new Date());
		asyncJobQueueDAO.insertAsyncJob(asyncJob);
	}

	@Override
	public void deleteAsyncJobIfWaitNumIsZero(AsyncJobQueueDO asyncJob) {
		// 返回最早的那个定时任务
		AsyncJobQueueDO job = asyncJobQueueDAO.getLastAsyncJobQueue(asyncJob);
		if (job != null) {
			if (job.getWaitCallNum() <= 1) {
				asyncJobQueueDAO.deleteAsyncJobById(job.getId());
			} else {
				job.setWaitCallNum(job.getWaitCallNum() - 1);
				asyncJobQueueDAO.updateAsyncJob(job);
			}
		}
	}
	
	public void deleteAsyncJob(AsyncJobQueueDO asyncJob) {
		// 返回最早的那个定时任务
		AsyncJobQueueDO job = asyncJobQueueDAO.getLastAsyncJobQueue(asyncJob);
		if (job != null) {
			asyncJobQueueDAO.deleteAsyncJobById(job.getId());
		}
	}

	/**
	 * 处理成功的回调
	 * 
	 * @param jobDetail
	 * @param msg
	 * @param result
	 */
	private void doTrueComplete(JobDetail jobDetail, JobResult jobResult) {
		try {
			messageManager.sendSuccessMessage(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getGroup() + jobDetail.getName() + "_成功_"
					+ jobResult.getResultMsg(), JobUtil.getJobData(jobDetail.getJobDataMap()) + "result:" + getJobResultStr(jobResult));
			if (DataTrackingJob.class.getName().equals(jobDetail.getJobClass().getName())
					|| FileLineCountJob.class.getName().equals(jobDetail.getJobClass().getName())) {
				dataTrackingLogBO.processDataTrackingLog(jobDetail, jobResult);
			}
			logsBO.insertSuccess(LogsDO.SUBTYPE_JOB_END_ASYN, jobDetail.getGroup() + "|" + jobDetail.getName(),
			// JobUtil.getJobData(jobDetail.getJobDataMap())+
					"result:" + getJobResultStr(jobResult), LogsDO.USER_SYS);
		} catch (Exception e) {
			logger.error("异步任务回调处理失败：" + "|" + jobDetail.getGroup() + "|" + jobDetail.getName());
		}
	}

	/**
	 * 处理失败的回调
	 * 
	 * @param jobDetail
	 * @param error
	 * @param result
	 */
	private void doFalseComplete(JobDetail jobDetail, JobResult jobResult) {
		try {
			if (jobDetail.getJobDataMap().containsKey(Constants.JOBDATA_REPEAT_ALARM_NUM)) {
				messageManager.sendFailMessage(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getGroup() + jobDetail.getName() + "_失败_"
						+ jobResult.getResultMsg(), JobUtil.getJobData(jobDetail.getJobDataMap()) + "result:" + getJobResultStr(jobResult), jobDetail
						.getJobDataMap().getInt(Constants.JOBDATA_REPEAT_ALARM_NUM));
			} else {
				messageManager.sendFailMessage(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getGroup() + jobDetail.getName() + "_失败_"
						+ jobResult.getResultMsg(), JobUtil.getJobData(jobDetail.getJobDataMap()) + "result:" + getJobResultStr(jobResult));
			}
			logsBO.insertFail(LogsDO.SUBTYPE_JOB_FAILED_ASYN, jobDetail.getGroup() + "|" + jobDetail.getName(),
			// JobUtil.getJobData(jobDetail.getJobDataMap())+
					"result:" + getJobResultStr(jobResult), LogsDO.USER_SYS);
		} catch (Exception e) {
			logger.error("异步任务回调处理失败：" + "|" + jobDetail.getGroup() + "|" + jobDetail.getName());
		}
	}

	private String getJobResultStr(JobResult result) {
		JSONObject json = (JSONObject) JSONObject.toJSON(result);
		return json.toString();
		
		
	}

	public void setAsyncJobQueueDAO(AsyncJobQueueDAO asyncJobQueueDAO) {
		this.asyncJobQueueDAO = asyncJobQueueDAO;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public void setDataTrackingLogBO(IDataTrackingLogBO dataTrackingLogBO) {
		this.dataTrackingLogBO = dataTrackingLogBO;
	}

	public void setReportJobRtBO(ReportJobRtBO reportJobRtBO) {
		this.reportJobRtBO = reportJobRtBO;
	}

	public void setEasyscheduler(Scheduler easyscheduler) {
		this.easyscheduler = easyscheduler;
	}
}
