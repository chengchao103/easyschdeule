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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.job.JobBO;
import com.taobao.ad.easyschedule.dataobject.JobDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class JobTest {
	@Autowired
	private JobBO jobBO;

	@Test
	public void testGetJobList() {
		JobDO job = new JobDO();
		job.setJobname("http-dumpJoinedCpsShopKeeperToFile-53274");
		job.setJobgroup("170");
		Assert.assertNotNull(jobBO.jobList(job));

	}

	@Test
	public void testGetCurrentJobNum() {
		Assert.assertEquals(0, jobBO.getCurrentJobNum().intValue());

	}

}