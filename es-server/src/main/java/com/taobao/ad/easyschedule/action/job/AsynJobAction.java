/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.job;

import java.io.UnsupportedEncodingException;

import org.quartz.JobDetail;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.asyncjob.AsyncJobBO;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * 异步任务管理Action
 * 
 * @author baimei
 */
public class AsynJobAction extends BaseAction {

	private static final long serialVersionUID = 1433908530815520652L;
	private AsyncJobBO asyncJobBO;

	private String jobId;
	private String success;
	private int resultCode;
	private String resultMsg = "";
	private JobResult jobResult;

	/**
	 * 异步任务完成
	 * 
	 * @param jobId
	 * @param jobName
	 * @param JobGroup
	 * @Param success
	 * @param resultCode
	 * @param resultMsg
	 * 
	 * @return
	 */
	public String completeJob() {
		if (StringUtils.isEmpty(jobGroup) || StringUtils.isEmpty(jobName) || StringUtils.isEmpty(success)) {
			jobResult = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "任务不存在或参数非法");
			return SUCCESS;
		}
		try {
			JobDetail jobDetail = easyscheduler.getJobDetail(jobName, jobGroup);
			if (jobDetail == null) {
				jobResult = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "任务不存在" + jobGroup + jobName);
				logger.warn("任务不存在" + jobGroup + ":" + jobName);
				return SUCCESS;
			}
			jobResult = asyncJobBO.completeJob(jobDetail, createJobResult());
		} catch (Exception e) {
			logger.error("异步任务完成处理失败！", e);
			jobResult = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "未知异常或参数非法");
		}
		return SUCCESS;
	}

	/**
	 * 封装任务返回值
	 * 
	 * @return
	 */
	private JobResult createJobResult() {
		JobResult result = new JobResult();
		result.setJobId(jobId);
		result.setSuccess("true".equals(success));
		result.setResultCode(resultCode);
		try {
			if (!StringUtils.isEmpty(resultMsg)) {
				result.setResultMsg(java.net.URLDecoder.decode(resultMsg, "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			result.setResultMsg(resultMsg);
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public String checkAsyncJobQueue() {
		jobResult = asyncJobBO.checkAsyncJobQueue();
		jobResult.setJobId(jobId);
		return SUCCESS;
	}

	public void setAsyncJobBO(AsyncJobBO asyncJobBO) {
		this.asyncJobBO = asyncJobBO;
	}

	public JobResult getJobResult() {
		return jobResult;
	}

	public void setJobResult(JobResult jobResult) {
		this.jobResult = jobResult;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String isSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}