/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.manager.message.MessageManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class MessageManagerTest {

	@Autowired
	MessageManager messageManager;

	@Test
	public void testSendFailMessage() {
		messageManager.sendFailMessage("dtJustTest", "110", "woc", "woc");
	}

	@Test
	public void testSendSuccessMessage() {
		messageManager.sendSuccessMessage("dtJustTest", "110", "woc", "woc");
	}

	@Test
	public void testSendWarningMessage() {
		messageManager.sendWarningMessage("dtJustTest", "110", "woc", "woc");
	}

}
