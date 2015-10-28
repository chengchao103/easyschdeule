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

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.dataobject.UUserDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class UUserTest {

	@Autowired
	IUUserBO userBO;

	@Test
	public void testInsertUUser_ROLE_GROUP() {
		UUserDO uUser = new UUserDO();
		uUser.setCreateTime(new Date());
		uUser.setUpdateTime(new Date());
		uUser.setStatus(1L);
		uUser.setUsername("huangbaichuan");
		uUser.setPassword("1234567");
		uUser.setDescn("游客");
		uUser.setEmail("r");
		Long[] roles = new Long[1];
		roles[0] = 1L;
		Long[] groups = new Long[1];
		groups[0] = 1L;
		userBO.insertUUser(uUser, roles, groups);
	}

	@Test
	public void testInsertUUser() {
		UUserDO uUser = new UUserDO();
		uUser.setCreateTime(new Date());
		uUser.setUpdateTime(new Date());
		uUser.setStatus(1L);
		uUser.setUsername("huangbaichuan1");
		uUser.setPassword("12345671");
		uUser.setDescn("游客1");
		uUser.setEmail("r1");
		userBO.insertUUser(uUser);
	}

	@Test
	public void testGetUUserById() {
		UUserDO uUser = userBO.getUUserById(1L);
		Assert.assertNotNull(uUser);
		Assert.assertEquals("白眉1", uUser.getDescn());
		Assert.assertEquals(1, uUser.getId().longValue());
		Assert.assertEquals("baimei1@taobao.com", uUser.getEmail());
		Assert.assertEquals("136666992031", uUser.getMobile());
		Assert.assertEquals("eabd8ce9404507aa8c22714d3f5eada9", uUser.getPassword());
	}

	@Test
	public void testGetUUserByUsername() {
		UUserDO uUser = userBO.getUUserByUsername("baimei");
		Assert.assertNotNull(uUser);
		Assert.assertEquals("白眉1", uUser.getDescn());
		Assert.assertEquals(1, uUser.getId().longValue());
		Assert.assertEquals("baimei1@taobao.com", uUser.getEmail());
		Assert.assertEquals("136666992031", uUser.getMobile());
		Assert.assertEquals("eabd8ce9404507aa8c22714d3f5eada9", uUser.getPassword());

	}

	@Test
	public void testFindUUsers() {
		UUserDO u = new UUserDO();
		Assert.assertNotNull(userBO.findUUsers(u));
	}

	@Test
	public void testDeleteUUserById() {
		userBO.deleteUUserById(6L);
	}

	@Test
	public void testUpdateUUser() {
		UUserDO uUser = new UUserDO();
		uUser.setId(3L);
		uUser.setStatus(0L);
		userBO.updateUUser(uUser);
	}

	@Test
	public void testUpdateUUser_ROLE_GROUP() {
		UUserDO uUser = new UUserDO();
		uUser.setId(3L);
		uUser.setStatus(0L);
		Long[] roles = new Long[1];
		roles[0] = 1L;
		Long[] groups = new Long[1];
		groups[0] = 1L;
		userBO.updateUUser(uUser, roles, groups);
	}

	@Test
	public void testUpdateUUserStatus() {
		UUserDO uUser = new UUserDO();
		uUser.setId(3L);
		uUser.setUsername("1");
		uUser.setStatus(1L);
		userBO.updateUUserStatus(uUser);
	}

	@Test
	public void testFindUUserXRoles() {
		Assert.assertNotNull(userBO.findUUserXRoles(1L));
	}

	@Test
	public void testFindUUserXGroups() {
		Assert.assertNotNull(userBO.findUUserXGroups(1L));
	}

	@Test
	public void testCheckLogin() {
		Assert.assertNull(userBO.checkLogin("1111", "2222"));
	}
}
