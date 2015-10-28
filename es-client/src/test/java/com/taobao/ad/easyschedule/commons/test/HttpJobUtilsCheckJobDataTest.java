/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.taobao.ad.easyschedule.commons.Const;
import com.taobao.ad.easyschedule.commons.utils.HttpJobUtils;
import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;

public class HttpJobUtilsCheckJobDataTest {

	private JobData jobData = null;
	private String JOBID = "12345";
	private String JOBGROUP = "12345";
	private String JOBNAME = "12345";
	private String CALLBACKURL = "http://127.0.0.1:/1212/es/completeJob";

	@Before
	public void setUp() {
		jobData = new JobData();
		jobData.setJobId(JOBID);
		jobData.setJobGroup(JOBGROUP);
		jobData.setJobName(JOBNAME);
		jobData.setJobType(JobData.JOBTYPE_HTTPJOB);
		Long signTime = (System.currentTimeMillis() / 1000);
		jobData.setSignTime(signTime.toString());
		jobData.setToken(TokenUtils.generateToken(signTime.toString()));
	}

	@Test
	public void testCheckJobData校验HTTP任务_缺少jobGroup() {
		jobData.setJobGroup(null);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_PARAMETER_ILLEGAL, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验HTTP任务_缺少jobName() {
		jobData.setJobName(null);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_PARAMETER_ILLEGAL, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验HTTP任务_缺少token() {
		jobData.setSync(true);
		jobData.setToken(null);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_TOKENVALIDATE_FAILURE, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验HTTP任务_缺少signTime() {
		jobData.setSync(true);
		jobData.setSignTime(null);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_TOKENVALIDATE_FAILURE, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验HTTP任务_同步() {
		jobData.setSync(true);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(true, jobResult.isSuccess());
	}

	@Test
	public void testCheckJobData校验HTTP任务_异步() {
		jobData.setSync(false);
		jobData.setCallBackUrl(CALLBACKURL);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(true, jobResult.isSuccess());
	}

	@Test
	public void testCheckJobData校验HTTP任务_异步_缺少jobId() {
		jobData.setSync(false);
		jobData.setJobId(null);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_PARAMETER_ILLEGAL, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验HTTP任务_异步_缺少callBackUrl() {
		jobData.setSync(false);
		jobData.setCallBackUrl(null);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_PARAMETER_ILLEGAL, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验Shell任务_正常() {
		jobData.setJobType(JobData.JOBTYPE_SHELLJOB);
		Map<String, String> data = new HashMap<String, String>();
		data.put(Const.JOBCOMMAND, "echo");
		jobData.setData(data);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(true, jobResult.isSuccess());
	}

	@Test
	public void testCheckJobData校验Shell任务_缺少jobCommand() {
		jobData.setJobType(JobData.JOBTYPE_SHELLJOB);
		Map<String, String> data = new HashMap<String, String>();
		jobData.setData(data);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_PARAMETER_ILLEGAL, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验DT任务_正常() {
		jobData.setJobType(JobData.JOBTYPE_DTJOB);
		Map<String, String> data = new HashMap<String, String>();
		data.put(Const.TRACKINGSQL, "select 1");
		data.put(Const.DATASOURCETYPE, "1");
		data.put(Const.DATASOURCE, "dataSource");
		jobData.setData(data);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(true, jobResult.isSuccess());
	}

	@Test
	public void testCheckJobData校验DT任务_缺少dataSource() {
		jobData.setJobType(JobData.JOBTYPE_DTJOB);
		Map<String, String> data = new HashMap<String, String>();
		data.put(Const.TRACKINGSQL, "select 1");
		data.put(Const.DATASOURCETYPE, "1");
		jobData.setData(data);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_PARAMETER_ILLEGAL, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验DT任务_缺少dataSourceType() {
		jobData.setJobType(JobData.JOBTYPE_DTJOB);
		Map<String, String> data = new HashMap<String, String>();
		data.put(Const.TRACKINGSQL, "select 1");
		data.put(Const.DATASOURCE, "dataSource");
		jobData.setData(data);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_PARAMETER_ILLEGAL, jobResult.getResultCode());
	}

	@Test
	public void testCheckJobData校验DT任务_缺少trackingSql() {
		jobData.setJobType(JobData.JOBTYPE_DTJOB);
		Map<String, String> data = new HashMap<String, String>();
		data.put(Const.DATASOURCETYPE, "1");
		data.put(Const.DATASOURCE, "dataSource");
		jobData.setData(data);
		JobResult jobResult = HttpJobUtils.checkJobData(jobData);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_PARAMETER_ILLEGAL, jobResult.getResultCode());
	}
}
