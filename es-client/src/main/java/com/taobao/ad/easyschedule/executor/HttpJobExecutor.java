/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.executor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.commons.utils.HttpJobUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * http任务执行器
 * 
 * @author baimei
 * 
 */
public class HttpJobExecutor {

	public static HttpJobExecutor httpJobExecutor;
	protected final static Logger logger = LoggerFactory.getLogger("OPLOG");

	private HttpJobExecutor() {
	}

	public static HttpJobExecutor getInstance() {
		if (httpJobExecutor == null) {
			synchronized (HttpJobExecutor.class) {
				if (httpJobExecutor == null) {
					httpJobExecutor = new HttpJobExecutor();
				}
			}
		}
		return httpJobExecutor;
	}

	/**
	 * 执行一个http任务
	 * 
	 * @param request
	 * @param executor
	 * @return
	 */
	public JobResult execute(HttpServletRequest request, JobExecutor executor) {
		return execute(HttpJobUtils.createJobData(request), executor);
	}

	/**
	 * 执行一个http任务
	 * 
	 * @param request
	 * @param executor
	 * @return
	 */
	public JobResult execute(JobData jobData, JobExecutor executor) {
		JobResult result = HttpJobUtils.checkJobData(jobData);
		if (!result.isSuccess()) {
			return result;
		}
		try {
			logger.info("start|jobId:" + jobData.getJobId() + "|jobGroup:" + jobData.getJobGroup() + "|jobName:" + jobData.getJobName());
			if (jobData.isSync()) {
				result = executeJob(jobData, executor);
			} else {
				result = executeAsyncJob(jobData, executor);
			}
			if (result.isSuccess()) {
				logger.info("end|jobId:" + jobData.getJobId() + "|jobGroup:" + jobData.getJobGroup() + "|jobName:" + jobData.getJobName() + "|success:"
						+ result.isSuccess() + "|resultCode:" + result.getResultCode() + "|resultMsg:" + result.getResultMsg(), new Object[] {
						jobData.getJobId(), jobData.getJobGroup(), jobData.getJobName(), result.isSuccess(), result.getResultCode(), result.getResultMsg() });
			} else {
				logger.info("fail|jobId:" + jobData.getJobId() + "|jobGroup:" + jobData.getJobGroup() + "|jobName:" + jobData.getJobName() + "|success:"
						+ result.isSuccess() + "|resultCode:" + result.getResultCode() + "|resultMsg:" + result.getResultMsg(), new Object[] {
						jobData.getJobId(), jobData.getJobGroup(), jobData.getJobName(), result.isSuccess(), result.getResultCode(), result.getResultMsg() });
			}
		} catch (Exception e) {
			result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "处理任务失败,errorMsg is:" + e.getMessage());
			logger.info("fail|jobId:" + jobData.getJobId() + "|jobGroup:" + jobData.getJobGroup() + "|jobName:" + jobData.getJobName() + "|success:"
					+ result.isSuccess() + "|resultCode:" + result.getResultCode() + "|resultMsg:" + result.getResultMsg());
		}
		result.setJobId(jobData.getJobId());
		return result;
	}

	/**
	 * 同步执行一个任务
	 * 
	 * @param jobData
	 * @param executor
	 * @return
	 */
	private JobResult executeJob(JobData jobData, JobExecutor executor) throws Exception {
		JobResult result = null;
		int clientRetries = HttpJobUtils.getJobClientRetries(jobData.getData().get(JobData.JOBDATA_DATA_CLIENTRETRIES));
		for (int i = 1; i <= clientRetries + 1; i++) {
			result = executor.execute(jobData);
			if (result.isSuccess()) {
				if (i > 1) {
					result.getData().put(JobResult.JOBRESULT_DATA_CLIENTRETRYCOUNT, String.valueOf(i));
				}
				break;
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
			}
			if (i > 1 && i == clientRetries + 1) {
				result.getData().put(JobResult.JOBRESULT_DATA_CLIENTRETRYCOUNT, String.valueOf(i));
			}
		}
		return result;
	}

	/**
	 * 异步执行一个任务并且把结果回调给ES
	 * 
	 * @param jobData
	 * @param executor
	 * @return
	 */
	private JobResult executeAsyncJob(final JobData jobData, final JobExecutor executor) {
		JobResult result = JobResult.succcessResult();
		new Thread(new Runnable() {
			@Override
			public void run() {
				JobResult result = null;
				try {
					result = executeJob(jobData, executor);
				} catch (Exception e) {
					result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "异步任务处理失败:" + e.getMessage());
				} finally {
					try {
						HttpJobUtils.callBackHttpJob(jobData, result);
					} catch (IOException e) {
						logger.error("异步任务回调处理失败", e);
					}
				}
			}
		}).start();
		return result;
	}
}