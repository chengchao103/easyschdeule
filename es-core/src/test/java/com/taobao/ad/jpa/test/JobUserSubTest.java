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

import com.taobao.ad.easyschedule.bo.jobusersub.JobUserSubBO;
import com.taobao.ad.easyschedule.dataobject.JobUserSubDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class JobUserSubTest {

	@Autowired
	private JobUserSubBO jobUserSubBO;

	@Test
	public void getUserSub() {

		JobUserSubDO sub = new JobUserSubDO();
		sub.setJobGroup("110");
		sub.setJobName("dtJustTest");
		sub.setUserId(2L);
		sub.setType(0);
		sub = jobUserSubBO.getUserSub(sub);

		Assert.assertNotNull(sub);
		Assert.assertEquals("110", sub.getJobGroup());
		Assert.assertEquals("dtJustTest", sub.getJobName());
		Assert.assertEquals(2L, sub.getUserId().longValue());
		Assert.assertEquals(0, sub.getType());
		Assert.assertEquals(1, sub.getWangwang());
		Assert.assertEquals(1, sub.getEmail());
		Assert.assertEquals(1, sub.getMobile());
	}

	@Test
	public void findJobUserSub() {
		Assert.assertNotNull(jobUserSubBO.findJobUserSub("110", "dtJustTest", null, 0));
	}

	@Test
	public void insertUserSub() {
		JobUserSubDO sub = new JobUserSubDO();
		sub.setJobGroup("1");
		sub.setJobName("2");
		sub.setUserId(123L);
		sub.setCreator(1);
		sub.setModifier(1);
		sub.setWangwang(1);
		sub.setMobile(1);
		sub.setEmail(1);
		sub.setType(1);
		try {
			jobUserSubBO.insertUserSub(sub);
		} catch (Exception e) {

		}

	}

	@Test
	public void deleteUserSub() {

		JobUserSubDO sub = new JobUserSubDO();
		sub.setJobGroup("116");
		sub.setJobName("dtJustTest");
		sub.setUserId(1L);
		sub.setType(3);
		jobUserSubBO.deleteUserSub(sub);

	}

	@Test
	public void updateUserSub() {
		JobUserSubDO sub = new JobUserSubDO();
		sub.setJobGroup("110");
		sub.setJobName("dtJustTest");
		sub.setUserId(2L);
		sub.setCreator(1);
		sub.setModifier(1);
		sub.setWangwang(1);
		sub.setMobile(1);
		sub.setEmail(1);
		sub.setType(2);
		jobUserSubBO.updateUserSub(sub);
	}

	@Test
	public void saveOrUpdateJobUserSub_1() {
		JobUserSubDO sub = new JobUserSubDO();
		sub.setJobGroup("111");
		sub.setJobName("dtJustTest1");
		sub.setUserId(2L);
		sub.setCreator(1);
		sub.setModifier(1);
		sub.setWangwang(1);
		sub.setMobile(1);
		sub.setEmail(1);
		sub.setType(2);
		jobUserSubBO.saveOrUpdateJobUserSub(sub, 1);

	}

	@Test
	public void saveOrUpdateJobUserSub_2() {
		JobUserSubDO sub = new JobUserSubDO();
		sub.setJobGroup("111");
		sub.setJobName("dtJustTest1");
		sub.setUserId(2L);
		sub.setCreator(1);
		sub.setModifier(1);
		sub.setWangwang(1);
		sub.setMobile(1);
		sub.setEmail(1);
		sub.setType(2);
		jobUserSubBO.saveOrUpdateJobUserSub(sub, 2);

	}

	@Test
	public void saveOrUpdateJobUserSub_3() {
		JobUserSubDO sub = new JobUserSubDO();
		sub.setJobGroup("111");
		sub.setJobName("dtJustTest1");
		sub.setUserId(2L);
		sub.setCreator(1);
		sub.setModifier(1);
		sub.setWangwang(1);
		sub.setMobile(1);
		sub.setEmail(1);
		sub.setType(2);
		jobUserSubBO.saveOrUpdateJobUserSub(sub, 3);

	}

}
