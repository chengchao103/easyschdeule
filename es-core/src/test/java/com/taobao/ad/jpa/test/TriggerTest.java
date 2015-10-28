/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.trigger.TriggerBO;
import com.taobao.ad.easyschedule.dataobject.TriggerDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class TriggerTest {

	@Autowired
	TriggerBO triggerBO;

	@Test
	public void testGetTriggerList() {

		Assert.assertNotNull(triggerBO.getTriggerList(new TriggerDO()));

	}

}
