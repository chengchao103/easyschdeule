/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.datatrackinglog.IDataTrackingLogBO;
import com.taobao.ad.easyschedule.dataobject.DatatrackingLogDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class DataTrackingLogTest {

	@Autowired
	IDataTrackingLogBO dataTrackingLogBO;

	@Test
	public void testInsertDataTrackingLog() {

		DatatrackingLogDO d = new DatatrackingLogDO();
		d.setJobName("110");
		d.setJobGroup("2-TEST");
		d.setTrackingValue(1D);
		dataTrackingLogBO.insertDataTrackingLog(d);

	}

	@Test
	public void testGetDataTrackingByGroupAndName() {
		DatatrackingLogDO d = new DatatrackingLogDO();
		d.setJobName("dtJustTest");
		d.setJobGroup("110");
		Assert.assertNotNull(dataTrackingLogBO.getDataTrackingByGroupAndName(d));

	}

	@Test
	public void testProcessDataTrackingLog() {
		JobDetail jobDetail = new JobDetail();
		JobResult jobResult = new JobResult();
		jobResult.setResultMsg("110");
		jobResult.setSuccess(true);
		jobResult.setJobId("1234555");
		jobResult.setResultCode(0);
		jobDetail.setName("dtJustTest");
		jobDetail.setGroup("110");
		jobDetail.setJobDataMap(new JobDataMap());
		dataTrackingLogBO.processDataTrackingLog(jobDetail, jobResult);
	}

}
