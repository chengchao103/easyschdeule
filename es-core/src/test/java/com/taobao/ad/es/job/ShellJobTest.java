/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.job;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.job.JobHttpServerForTest;
import com.taobao.ad.easyschedule.job.ShellJob;

/**
 * Http任务-单元测试
 * 
 * @author baimei
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class ShellJobTest {

	private String TARGET = "http://127.0.0.1:" + JobHttpServerForTest.SERVER_PORT + JobHttpServerForTest.SERVER_CONTEXT;
	private String TARGET_TEST_404 = "http://127.0.0.1:" + JobHttpServerForTest.SERVER_PORT;
	private String TARGET_TEST_500 = "http://127.0.0.1:9889";

	@Resource
	ICodeBO codeBO;

	JobDetail jobDetail = null;
	ShellJob job = null;

	@Before
	public void setUp() {
		// 启动测试的http任务
		JobHttpServerForTest.StartServer();
		
		job = new ShellJob();
		
		jobDetail = new JobDetail("jobName", "123", ShellJob.class);
		jobDetail.setDescription("Http－测试");
		jobDetail.setRequestsRecovery(false);
		jobDetail.setDurability(true);
		List<CodeDO> list = codeBO.getCodesByKey(CodeDO.CODE_KEY_SHELLJOB_JOBDATA);
		for (CodeDO codeDO : list) {
			jobDetail.getJobDataMap().put(codeDO.getKeycode(), codeDO.getKeyname());
		}
	}

	@Test
	public void testDoJob_正常() {
		JobResult jobResult = job.doJob(1234L, jobDetail, TARGET + JobHttpServerForTest.QUERYPATH_TEST_HTTPJOB_RETURNTRUE);
		Assert.assertEquals(true, jobResult.isSuccess());
	}

	@Test
	public void testDoJob_异常() {
		JobResult jobResult = job.doJob(1234L, jobDetail, TARGET + JobHttpServerForTest.QUERYPATH_TEST_HTTPJOB_RETURNFALSE);
		Assert.assertEquals(false, jobResult.isSuccess());
	}

	@Test
	public void testDoJob_异常_请求错误地址返回404() {
		Constants.JOB_MIN_CONN_TIMEOUT = 1;
		JobResult jobResult = job.doJob(1234L, jobDetail, TARGET_TEST_404);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_JOB_REQUEST_FAILURE, jobResult.getResultCode());
	}

	@Test
	public void testDoJob_异常_请求错误地址返回500() {
		Constants.JOB_MIN_CONN_TIMEOUT = 1;
		JobResult jobResult = job.doJob(1234L, jobDetail, TARGET_TEST_500);
		System.out.println(jobResult.getResultMsg());
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_JOB_REQUEST_FAILURE, jobResult.getResultCode());
	}

	@Test
	public void testDoJob_异常_返回非JSON内容() {
		JobResult jobResult = job.doJob(1234L, jobDetail, TARGET + JobHttpServerForTest.QUERYPATH_TEST_HTTPJOB_RETURNERROR);
		Assert.assertEquals(false, jobResult.isSuccess());
		Assert.assertEquals(JobResult.RESULTCODE_JOBRESULT_ILLEGAL, jobResult.getResultCode());
	}
}
