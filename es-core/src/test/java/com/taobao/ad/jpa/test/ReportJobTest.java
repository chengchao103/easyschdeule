/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.reportjob.ReportJobBO;
import com.taobao.ad.easyschedule.dataobject.ReportJobDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class ReportJobTest {

	@Resource
	ReportJobBO reportJobBO;

	@Test
	public void testInsertReportJob_save() {

		ReportJobDO job = new ReportJobDO();
		job.setJobNum(2L);
		job.setSuccessNum(2L);
		job.setErrorNum(2L);
		job.setRt(new BigDecimal(22.3));
		job.setReportTime(new Date());
		reportJobBO.saveOrUpdateReportJob(job);

	}

	@Test
	public void testFindReportJob() {
		ReportJobDO job = new ReportJobDO();
		job.setStartTime(new Date());
		job.setEndTime(new Date());
		Assert.assertNotNull(reportJobBO.findReportJob(job));

	}

}
