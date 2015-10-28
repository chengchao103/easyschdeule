/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDataMap;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.commons.utils.FormatUtil;
import com.taobao.ad.easyschedule.commons.utils.JobUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class UtilsTest {

	@Test
	public void testFormatUtils() throws ParseException {
		FormatUtil.getDateAsString(new Date());
		FormatUtil.parseStringToDate(FormatUtil.getDateAsString(new Date()));

	}

	@Test
	public void testJobUtils_STRING_FOR_DATAMAP() {
		JobDataMap map = new JobDataMap();
		map.put("1", "1");
		JobUtil.getJobData(map);
	}

	@Test
	public void testGetConnectionTimeOut() {

		JobUtil.getConnectionTimeout("1111");
	}

	@Test
	public void testGetJobRetries() {

		JobUtil.getJobRetries("22");
	}
}
