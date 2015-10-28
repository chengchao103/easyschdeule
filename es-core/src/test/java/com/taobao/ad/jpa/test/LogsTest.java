/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.dataobject.LogsDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class LogsTest {
	@Autowired
	ILogsBO logsBO;

	@Test
	public void insertLogs() {
		LogsDO log = new LogsDO();
		log.setOpname("123");
		log.setOptime(new Date());
		logsBO.insertLogs(log);
	}

	@Test
	public void getLogsById() {
		LogsDO log = logsBO.getLogsById(1048723L);

		Assert.assertNotNull(log);
		Assert.assertEquals(log.getId().longValue(), 1048723L);
		Assert.assertEquals(log.getOptype().longValue(), 1L);
		Assert.assertEquals(log.getOpname(), "v039024.sqa.cm41336717573334");
		Assert.assertEquals(log.getOpsubtype().longValue(), 901L);
		Assert.assertEquals(log.getOpsubname(), "110|httpXXX1726");
		Assert.assertEquals(log.getOpuser(), "SYS");
	}

	@Test
	public void findLogss() {
		Assert.assertNotNull(logsBO.findLogss(new LogsDO()));

	}

	@Test
	public void deleteLogsById_DATA_NOT_EXIST() {
		logsBO.deleteLogsById(12344L);
	}

	@Test
	public void deleteLogsById_DATA_EXIST() {
		logsBO.deleteLogsById(1048724L);
	}

	@Test
	public void updateLogs() {
		LogsDO log = logsBO.getLogsById(1048723L);
		Assert.assertNotNull(log);
		log.setOpdetail("222");
		logsBO.updateLogs(log);
	}

	@Test
	public void insertSuccess() {
		logsBO.insertSuccess(1, "2", "3", "4");
	}

	@Test
	public void insertError() {
		logsBO.insertFail(1, "2", "3", "4");
	}

	@Test
	public void insertWarning() {
		logsBO.insertWarning(1, "2", "3", "4");
	}

	@Test
	public void testGetLastStartLog() {
		LogsDO log = logsBO.getLastStartLog("110", "httpXXX1726");
		Assert.assertNotNull(log);
		Assert.assertEquals(1048723L, log.getId().longValue());
		Assert.assertEquals(1L, log.getOptype().longValue());
		Assert.assertEquals(901L, log.getOpsubtype().longValue());
		Assert.assertEquals("v039024.sqa.cm41336717573334", log.getOpname());
		Assert.assertEquals("110|httpXXX1726", log.getOpsubname());
		Assert.assertEquals("222", log.getOpdetail());
		Assert.assertEquals("SYS", log.getOpuser());

	}

	@Test
	public void testGetCountByTypeAndTime() {
		Long v = logsBO.getCountByTypeAndTime(901L, new Date(), new Date());
		Assert.assertNotNull(v);
		Assert.assertEquals(0L, v.longValue());
	}

	@Test
	public void deleteAllLogs() {
		logsBO.deleteAllLogs();

	}

}
