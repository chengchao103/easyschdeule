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

import com.taobao.ad.easyschedule.bo.urole.IURoleBO;
import com.taobao.ad.easyschedule.dataobject.URoleDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class URoleTest {

	@Autowired
	private IURoleBO roleBO;

	@Test
	public void insertURole() {
		URoleDO uRole = new URoleDO();
		uRole.setName("11");
		uRole.setDescn("管理员");
		roleBO.insertURole(uRole);
	}

	@Test
	public void getURoleById() {
		URoleDO uRole = roleBO.getURoleById(1L);

		Assert.assertEquals(1, uRole.getId().longValue());
		Assert.assertEquals("ROLE_ADMIN", uRole.getName());
		// Assert.assertEquals("管理员", uRole.getDescn());
	}

	@Test
	public void findURoles() {
		Assert.assertNotNull(roleBO.findURoles(new URoleDO()));
	}

	@Test
	public void deleteURoleById() {
		roleBO.deleteURoleById(2L);
	}

	@Test
	public void testUpdateURole() {
		URoleDO uRole = new URoleDO();
		uRole.setId(1L);
		uRole.setName("ROLE_ADMIN");
		uRole.setDescn("管理员1");
		roleBO.updateURole(uRole);
	}

	@Test
	public void testFindAllUserRoese() {
		roleBO.findURoles("baimei");
	}
}
