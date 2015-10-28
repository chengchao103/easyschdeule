/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.bo.datatrackinglog.IDataTrackingLogBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.JobUtil;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * 文件行数统计任务
 * 
 * @author baimei
 */
public class FileLineCountJob extends RandomOrConcurrentJob implements Job {

	final static Logger logger = LoggerFactory.getLogger(FileLineCountJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		super.excuteJob(context);
	}

	@Override
	public JobResult doJob(Long jobId, JobDetail jobDetail, String target) {
		return doJob(jobId, jobDetail, new String[] { target });
	}

	@Override
	public JobResult doJob(Long jobId, JobDetail jobDetail, String[] target) {
		JobResult result = JobUtil.executeRemoteJob(jobId, jobDetail, target);
		if (result.isSuccess() && jobDetail.getJobDataMap().getBoolean(Constants.JOBDATA_SYNCHRONOUS)) {
			IDataTrackingLogBO dataTrackingLogBO = (IDataTrackingLogBO) EsApplicationContext.getBean("dataTrackingLogBO");
			dataTrackingLogBO.processDataTrackingLog(jobDetail, result);
		}
		return result;
	}
}
