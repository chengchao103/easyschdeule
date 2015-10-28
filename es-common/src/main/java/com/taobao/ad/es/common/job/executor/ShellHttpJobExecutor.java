/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.common.job.executor;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.JobExecutor;

/**
 * Shell任务执行器
 * 
 * @author baimei
 * 
 */
public class ShellHttpJobExecutor implements JobExecutor {

	final Logger logger = LoggerFactory.getLogger(ShellHttpJobExecutor.class);

	@Override
	public JobResult execute(JobData jobData) throws IOException {
		JobResult jobResult = JobResult.succcessResult();
		CommandLine cmdLine = CommandLine.parse(jobData.getData().get(JobData.JOBDATA_DATA_JOBCOMMAND));
		Executor executor = new DefaultExecutor();
		ExecuteWatchdog watchdog = new ExecuteWatchdog(1200000);
		executor.setExitValue(0);
		executor.setWatchdog(watchdog);
		int exitValue = -1;
		try {
			exitValue = executor.execute(cmdLine);
		} catch (ExecuteException e) {
			exitValue = e.getExitValue();
		}
		if (exitValue != 0) {
			jobResult = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "Shell任务执行时返回失败！");
			jobResult.setResultCode(exitValue);
			return jobResult;
		}
		jobResult.setResultCode(exitValue);
		return jobResult;
	}
}
