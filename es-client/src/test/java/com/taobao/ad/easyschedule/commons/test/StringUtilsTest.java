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

import com.taobao.ad.easyschedule.commons.utils.StringUtils;

public class StringUtilsTest {

	@Before
	public void setUp() {
	}

	@Test
	public void testIsEmpty_为NULL() {
		Assert.assertEquals(true, StringUtils.isEmpty(null));
	}

	@Test
	public void testIsEmpty_为空() {
		Assert.assertEquals(true, StringUtils.isEmpty(""));
	}

	@Test
	public void testIsEmpty_不为空() {
		Assert.assertEquals(false, StringUtils.isEmpty("a"));
	}
}
