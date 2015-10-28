/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.common.job.executor;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.JobExecutor;

/**
 * 文件行数统计任务执行器
 * 
 * @author baimei
 * 
 */
public class FileLineCountJobExecutor implements JobExecutor {

	final Logger logger = LoggerFactory.getLogger(FileLineCountJobExecutor.class);

	@Override
	public JobResult execute(JobData jobData) throws IOException {
		JobResult jobResult = JobResult.succcessResult();
		String fileName = jobData.getData().get(JobData.JOBDATA_DATA_FILEFULLNAME);
		jobResult.setResultMsg(String.valueOf(getFileLineCount(fileName)));
		return jobResult;
	}

	private static long getFileLineCount(String fileName) throws IOException {
		FileReader in = new FileReader(fileName);
		LineNumberReader reader = new LineNumberReader(in);
		String strLine = reader.readLine();
		long totalLines = 0;
		while (strLine != null) {
			totalLines++;
			strLine = reader.readLine();
		}
		reader.close();
		in.close();
		return totalLines;
	}
}
