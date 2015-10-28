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

import com.taobao.ad.easyschedule.bo.datatracking.IDataTrackingJobBO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class DataTrackingJobTest {

	@Autowired
	IDataTrackingJobBO dataTrackingJobBO;

	@Test
	public void testGetDataTrackingData() {
		Assert.assertEquals(1.0, dataTrackingJobBO.getDataTrackingData("select 1 from dual"));
	}

}
