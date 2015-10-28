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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * 任务执行器(例子)
 * 
 * @author baimei
 * 
 */
public class SampleJobExecutor implements JobExecutor {

	final Logger logger = LoggerFactory.getLogger(SampleJobExecutor.class);

	@Override
	public JobResult execute(JobData jobData) throws IOException {
		JobResult jobResult = JobResult.succcessResult();
		return jobResult;
	}
}
