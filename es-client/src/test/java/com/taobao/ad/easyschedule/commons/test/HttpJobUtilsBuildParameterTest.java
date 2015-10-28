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

import com.taobao.ad.easyschedule.commons.utils.HttpJobUtils;
import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;

public class HttpJobUtilsBuildParameterTest {

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
	public void testBuildParameter_正常() {
		jobData.getData().put(JobData.JOBDATA_DATA_PARAMETER, "a%3D1%26b%3D2");
		HttpJobUtils.buildParameter(jobData.getData());
		Assert.assertEquals("1", jobData.getData().get("a"));
		Assert.assertEquals("2", jobData.getData().get("b"));
	}
}
