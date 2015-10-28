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
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.ad.easyschedule.commons.Const;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * 随机和并发的任务处理
 * 
 * @author bolin.hbc
 * 
 */
public abstract class RandomOrConcurrentJob implements Job {

	final static Logger logger = LoggerFactory.getLogger(RandomOrConcurrentJob.class);

	public abstract JobResult doJob(Long jobId, JobDetail jobDetail, String target);

	public abstract JobResult doJob(Long jobId, JobDetail jobDetail, String[] target);

	public void excuteJob(JobExecutionContext context) throws JobExecutionException {
		JobResult result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "ConcurrentJob未知的错误 ");
		try {
			JobDetail jobDetail = context.getJobDetail();
			JobDataMap data = jobDetail.getJobDataMap();
			String runType = StringUtils.isEmpty(data.getString(Constants.JOBDATA_RUNTYPE)) ? Constants.RUNTYPE_HASH : data
					.getString(Constants.JOBDATA_RUNTYPE);
			String targetUrl = data.getString(Constants.JOBDATA_TARGETURL);
			if (targetUrl == null) {
				result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "任务参数非法:targetUrl为空");
				context.setResult(result);
				return;
			}
			Long jobId = (Long) context.get(Const.JOBID);
			String urls[] = targetUrl.split(",");
			if (Constants.RUNTYPE_HASH.equals(runType)) {
				result = doJob(jobId, jobDetail, urls);
			} else if (Constants.RUNTYPE_CONCURRENT.equals(runType)) {
				boolean success = true;
				JSONObject obj = new JSONObject();
				for (String url : urls) {
					JobResult jobresult = doJob(jobId, jobDetail, url);
					success = success && jobresult.isSuccess();
					obj.put(url, jobresult.isSuccess() + "|" + jobresult.getResultCode() + "|" + jobresult.getResultMsg());
				}
				if (success) {
					result = JobResult.succcessResult();
				} else {
					result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "并发任务中存在至少一个错误");
				}
				result.setResultMsg(result.getResultMsg() + ":" + obj);
			}
		} catch (Exception e) {
			logger.error("ConcurrentJob执行出错", e);
			result.setResultMsg(result.getResultMsg() + e.getMessage());
		}
		context.setResult(result);
	}
}
