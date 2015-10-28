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
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.reportjobrt.ReportJobRtBO;
import com.taobao.ad.easyschedule.dataobject.ReportJobRtDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class ReportJobRtTest {

	@Resource
	ReportJobRtBO reportJobRtBO;

	@Test
	public void testFindReportJobRt() {
		ReportJobRtDO rt = new ReportJobRtDO();

		rt.setQueryStartTime(new Date());
		rt.setQueryEndTime(new Date());
		List<ReportJobRtDO> rtList = reportJobRtBO.findReportJobRt(rt);
		Assert.assertNotNull(rtList);

	}

	@Test
	public void testInsertReportJobRt() {
		ReportJobRtDO rt = new ReportJobRtDO();
		rt.setJobGroup("1");
		rt.setJobName("2");
		rt.setRt(2L);
		rt.setStartTime(System.currentTimeMillis());
		rt.setStatus(2);
		rt.setEndTime(System.currentTimeMillis());
		rt.setSync("true");
		reportJobRtBO.insertReportJobRt(rt);
	}

	@Test
	public void testGetLastReportJobRt() {
		ReportJobRtDO rt = reportJobRtBO.getLastReportJobRt("1", "1");
		Assert.assertNotNull(rt);
		Assert.assertEquals(1, rt.getId().intValue());
		Assert.assertEquals("1", rt.getJobGroup());
		Assert.assertEquals("1", rt.getJobName());
		Assert.assertEquals("true", rt.getSync());
		Assert.assertEquals(123, rt.getStartTime().intValue());
		Assert.assertEquals(123, rt.getEndTime().intValue());
		Assert.assertEquals(1, rt.getRt().intValue());
		Assert.assertEquals(1, rt.getStatus().intValue());
	}

	@Test
	public void testGetReportJobRt() {
		ReportJobRtDO rt = new ReportJobRtDO();
		rt.setJobGroup("1");
		rt.setJobName("1");
		rt.setStartTime(123L);

		rt = reportJobRtBO.getReportJobRt(rt);
		Assert.assertNotNull(rt);
		Assert.assertEquals(1, rt.getId().intValue());
		Assert.assertEquals("1", rt.getJobGroup());
		Assert.assertEquals("1", rt.getJobName());
		Assert.assertEquals("true", rt.getSync());
		Assert.assertEquals(123, rt.getStartTime().intValue());
		Assert.assertEquals(123, rt.getEndTime().intValue());
		Assert.assertEquals(1, rt.getRt().intValue());
		Assert.assertEquals(1, rt.getStatus().intValue());
	}

	@Test
	public void testGetSyncAndStatusCount_3() {
		Assert.assertEquals(4, reportJobRtBO.getSyncAndStatusCount("true", 1).intValue());

	}

	@Test
	public void testGetSyncAndStatusCount_0() {
		Assert.assertEquals(0, reportJobRtBO.getSyncAndStatusCount("true", 12).intValue());

	}

	@Test
	public void testFindReportJobRtPageList() {
		Assert.assertNotNull(reportJobRtBO.findReportJobRtPageList(new ReportJobRtDO()));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGet() {

		Date d = new Date();
		d.setYear(d.getYear() - 1);
		Assert.assertNotNull(reportJobRtBO.getAverageRt(d, new Date()));
		System.out.println(reportJobRtBO.getAverageRt(d, new Date()));

	}

}
