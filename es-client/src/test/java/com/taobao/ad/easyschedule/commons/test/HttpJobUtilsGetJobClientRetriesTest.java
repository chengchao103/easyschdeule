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

public class HttpJobUtilsGetJobClientRetriesTest {

	@Before
	public void setUp() {
	}

	@Test
	public void testGetJobClientRetries_正常次数() {
		Assert.assertEquals(2, HttpJobUtils.getJobClientRetries("2"));
	}

	@Test
	public void testGetJobClientRetries_最小次数() {
		Assert.assertEquals(0, HttpJobUtils.getJobClientRetries("0"));
	}

	@Test
	public void testGetJobClientRetries_最大次数() {
		Assert.assertEquals(10, HttpJobUtils.getJobClientRetries("10"));
	}
	
	@Test
	public void testGetJobClientRetries_超过最小次数() {
		Assert.assertEquals(0, HttpJobUtils.getJobClientRetries("-1"));
	}

	@Test
	public void testGetJobClientRetries_超过最大次数() {
		Assert.assertEquals(10, HttpJobUtils.getJobClientRetries("11"));
	}
}
