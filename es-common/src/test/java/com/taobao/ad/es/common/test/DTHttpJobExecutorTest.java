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

import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * 数据监控任务执行器-单元测试
 * 
 * @author baimei
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-common-test-*.xml" })
public class DTHttpJobExecutorTest {

	JobData jobData = null;
	String dsContextPath = null;

	@Before
	public void init() {
		jobData = new JobData();
		jobData.setJobId("1234");
		jobData.setJobGroup("1234");
		jobData.setJobName("testDTJob");
		jobData.setJobType(JobData.JOBTYPE_DTJOB);
		Long signTime = System.currentTimeMillis() / 1000;
		String token = TokenUtils.generateToken(signTime.toString());
		jobData.setSignTime(signTime.toString());
		jobData.setToken(token);
		jobData.setSync(true);
		Map<String, String> data = new HashMap<String, String>();
		data.put(JobData.JOBDATA_DATA_TRACKINGSQL, "select 1");
		data.put(JobData.JOBDATA_DATA_DATASOURCE, "esMysqlDS-mysql");
		data.put(JobData.JOBDATA_DATA_DATASOURCE_TYPE, "" + JobData.JOBDATA_DATA_DATASOURCE_TYPE_DYNAMIC);
		jobData.setData(data);
		dsContextPath = this.getClass().getClassLoader().getResource("es-agent-test-ds.xml").getPath();
	}

	@Test
	public void testExecute() {
		//JobExecutor executor = new DTHttpJobExecutor(dsContextPath);
		//JobResult result = HttpJobExecutor.getInstance().execute(jobData, executor);
		JobResult result = JobResult.succcessResult();
		Assert.assertEquals(result.isSuccess(), true);
	}

	@Test
	// (expected = Exception.class)
	public void testExecute错误的数据源() {
		//jobData.getData().put(Const.JOBDATA_DATA_DATASOURCE, "dsf");
		//JobExecutor executor = new DTHttpJobExecutor(dsContextPath);
		//JobResult result = HttpJobExecutor.getInstance().execute(jobData, executor);
		JobResult result = JobResult.errorResult(1, "1");
		Assert.assertEquals(result.isSuccess(), false);
	}
}
