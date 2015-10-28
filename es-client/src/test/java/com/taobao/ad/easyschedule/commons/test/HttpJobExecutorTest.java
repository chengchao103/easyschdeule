/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.HttpJobExecutor;
import com.taobao.ad.easyschedule.executor.JobExecutor;

public class HttpJobExecutorTest {

	private JobData jobData = null;
	private String JOBID = "12345";
	private String JOBGROUP = "12345";
	private String JOBNAME = "12345";

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
	public void testexecute_重试为0() {
		jobData.getData().put(JobData.JOBDATA_DATA_CLIENTRETRIES, "0");
		jobData.getData().put("result", "true");
		JobResult r = HttpJobExecutor.getInstance().execute(jobData, new TestJobExecutor());
		Assert.assertEquals(true, r.isSuccess());
		Assert.assertEquals(true, null == r.getData().get(JobResult.JOBRESULT_DATA_CLIENTRETRYCOUNT));
	}

	@Test
	public void testexecute_重试为1() {
		jobData.getData().put(JobData.JOBDATA_DATA_CLIENTRETRIES, "1");
		jobData.getData().put("result", "false");
		JobResult r = HttpJobExecutor.getInstance().execute(jobData, new TestJobExecutor());
		Assert.assertEquals(false, r.isSuccess());
		Assert.assertEquals(true, "2".equals(r.getData().get(JobResult.JOBRESULT_DATA_CLIENTRETRYCOUNT)));
	}

	class TestJobExecutor implements JobExecutor {

		@Override
		public JobResult execute(JobData jobData) throws Exception {
			if ("true".equals(jobData.getData().get("result"))) {
				return JobResult.succcessResult("成功");
			} else {
				return JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "失败");
			}
		}
	}
}
