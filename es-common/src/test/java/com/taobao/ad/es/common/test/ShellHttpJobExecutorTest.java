/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.common.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.es.common.job.executor.ShellHttpJobExecutor;
import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.HttpJobExecutor;
import com.taobao.ad.easyschedule.executor.JobExecutor;

/**
 * 数据监控任务执行器-单元测试
 * 
 * @author baimei
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-common-test-*.xml" })
public class ShellHttpJobExecutorTest {

	JobData jobData = null;

	@Before
	public void init() {
		jobData = new JobData();
		jobData.setJobId("1234");
		jobData.setJobGroup("123");
		jobData.setJobName("testDTJob");
		jobData.setJobType(JobData.JOBTYPE_SHELLJOB);
		Long signTime = System.currentTimeMillis() / 1000;
		String token = TokenUtils.generateToken(signTime.toString());
		jobData.setSignTime(signTime.toString());
		jobData.setToken(token);
		jobData.setSync(true);
		Map<String, String> data = new HashMap<String, String>();
		//data.put(Const.JOBDATA_DATA_JOBCOMMAND, "echo");
		jobData.setData(data);
	}

	@Test
	public void testExecute缺少参数() {
		JobExecutor executor = new ShellHttpJobExecutor();
		JobResult result = HttpJobExecutor.getInstance().execute(jobData, executor);
		Assert.assertEquals(result.isSuccess(), false);
	}
}
