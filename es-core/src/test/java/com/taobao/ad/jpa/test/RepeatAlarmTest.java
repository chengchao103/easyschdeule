/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.repeatalarm.RepeatAlarmBO;
import com.taobao.ad.easyschedule.dataobject.RepeatAlarmDO;

/**
 * 重复告警处理
 * 
 * 
 * @author bolin.hbc
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class RepeatAlarmTest {

	@Autowired
	private RepeatAlarmBO repeatAlarmBO;

	@Test
	public void testGetReapeatAlermCount() {

		Assert.assertEquals(8, repeatAlarmBO.getRepeatAlarmCount().intValue());
	}

	@Test
	public void testGetReapeatAlermById() {
		RepeatAlarmDO r = repeatAlarmBO.getRepeatAlarmById(1);

		Assert.assertNotNull(r);
		Assert.assertEquals(1, r.getId().intValue());
		Assert.assertEquals("11", r.getJobName());
		Assert.assertEquals("11", r.getJobGroup());
		Assert.assertEquals(1111, r.getSignTime());
		Assert.assertEquals(11, r.getRepeatAlarmNum());
		Assert.assertEquals(1, r.getStatus().intValue());
	}

	@Test
	public void testFindReapeatAlerms() {
		RepeatAlarmDO r = new RepeatAlarmDO();
		List<RepeatAlarmDO> rs = repeatAlarmBO.findRepeatAlarms(r);
		Assert.assertNotNull(rs);
		Assert.assertEquals(8, rs.size());
	}

	@Test
	public void testDeleteIfNumIsZero_real_delete() {

		repeatAlarmBO.checkRepeatAlarm(4);
		RepeatAlarmDO r = repeatAlarmBO.getRepeatAlarmById(4);
		Assert.assertNull(r);
	}

	@Test
	public void testInsertReapeatAlerm_save() {
		RepeatAlarmDO r = new RepeatAlarmDO();
		r.setJobGroup("123");
		r.setJobName("222");
		r.setRepeatAlarmNum(1);
		r.setStatus(1);
		long t = System.currentTimeMillis();
		r.setSignTime(t);
		repeatAlarmBO.saveOrUpdateRepeatAlarm(r);

		r = repeatAlarmBO.getRepeatAlarmById(r.getId());

		Assert.assertNotNull(r);
		Assert.assertEquals("222", r.getJobName());
		Assert.assertEquals("123", r.getJobGroup());
		// Assert.assertEquals(t, r.getSignTime());
		Assert.assertEquals(1, r.getRepeatAlarmNum());
		Assert.assertEquals(1, r.getStatus().intValue());
		repeatAlarmBO.checkRepeatAlarm(r.getId());
	}

	@Test
	public void testInsertReapeatAlerm_update() {
		RepeatAlarmDO r = new RepeatAlarmDO();
		r.setJobGroup("11");
		r.setJobName("11");
		r.setRepeatAlarmNum(2);
		r.setStatus(1);
		repeatAlarmBO.saveOrUpdateRepeatAlarm(r);
		System.out.println(r.getId());
		r = repeatAlarmBO.getRepeatAlarmById(r.getId());
		Assert.assertNotNull(r);
		Assert.assertEquals("11", r.getJobName());
		Assert.assertEquals("11", r.getJobGroup());
		Assert.assertEquals(1111, r.getSignTime());
		Assert.assertEquals(2, r.getRepeatAlarmNum());
		Assert.assertEquals(1, r.getStatus().intValue());
	}

	@Test
	public void testDeleteReapeatAlermById() {
		repeatAlarmBO.deleteRepeatAlarmById(6);
	}

	@Test
	public void testDeleteReapeatAlerm() {
		repeatAlarmBO.deleteRepeatAlarm("delete7", "17");
	}

	@Test
	public void tesDeleteIfNumIsZero_just_jian1() {
		repeatAlarmBO.checkRepeatAlarm(5);
		RepeatAlarmDO r = repeatAlarmBO.getRepeatAlarmById(5);
		Assert.assertNotNull(r);
		Assert.assertEquals(5, r.getId().intValue());
		Assert.assertEquals("delete5", r.getJobName());
		Assert.assertEquals("15", r.getJobGroup());
		Assert.assertEquals(1111, r.getSignTime());
		Assert.assertEquals(1, r.getRepeatAlarmNum());
		Assert.assertEquals(1, r.getStatus().intValue());
	}

	@Test
	public void tesUpdateRepeatAlarmStatus() {
		RepeatAlarmDO repeatAlarm = new RepeatAlarmDO();
		repeatAlarm.setJobName("11");
		repeatAlarm.setJobGroup("11");
		repeatAlarm.setStatus(0);
		repeatAlarmBO.updateRepeatAlarmStatus(repeatAlarm);

		repeatAlarm = repeatAlarmBO.findRepeatAlarms(repeatAlarm).get(0);
		Assert.assertNotNull(repeatAlarm);
		Assert.assertEquals(1, repeatAlarm.getId().intValue());
		Assert.assertEquals("11", repeatAlarm.getJobName());
		Assert.assertEquals("11", repeatAlarm.getJobGroup());
		Assert.assertEquals(0, repeatAlarm.getStatus().intValue());
	}
}
