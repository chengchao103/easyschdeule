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

import com.taobao.ad.easyschedule.bo.uresc.IURescBO;
import com.taobao.ad.easyschedule.dataobject.URescDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class URescTest {
	@Autowired
	IURescBO rescBO;

	@Test
	public void testFindAllUserRoese() {
		Assert.assertNotNull(rescBO.findAllUserRescs("baimei"));
	}

	@Test
	public void testInsertUResc() {
		URescDO r = new URescDO();
		r.setCreateTime(new Date());
		r.setUpdateTime(new Date());
		r.setName("132123");
		r.setPriority(1L);
		r.setReturnCount(2);
		r.setResType("1");
		r.setResString("1");
		r.setDescn("11");
		Assert.assertNotNull(rescBO.insertUResc(r));
	}

	@Test
	public void getURescById() {
		URescDO r = rescBO.getURescById(1L);
		Assert.assertNotNull(r);
		Assert.assertEquals(1, r.getId().longValue());
		Assert.assertEquals("URL", r.getResType());
		Assert.assertEquals("新增测试任务", r.getResString());
		Assert.assertEquals(1, r.getPriority().longValue());
	}

	@Test
	public void testFindURescs() {
		Assert.assertNotNull(rescBO.findURescs(new URescDO()));
	}

	@Test
	public void testDeleteURescById() {
		rescBO.deleteURescById(2L);
	}

	@Test
	public void testUpdateUResc() {
		URescDO r = new URescDO();
		r.setId(3L);
		r.setPriority(1L);
		r.setReturnCount(2);
		r.setResType("1");
		r.setResString("1");
		r.setDescn("11");
		rescBO.updateUResc(r);
	}

}
