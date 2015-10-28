/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.config.IConfigBO;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class ConfigTest {

	@Resource
	IConfigBO configBO;

	@Test
	public void testInsertConfig() {

		ConfigDO config = new ConfigDO();
		config.setConfigkey("1");
		config.setConfigvalue("2");
		config.setDescription("2");
		Assert.assertEquals(1L, configBO.insertConfig(config).longValue());
		configBO.deleteConfigByConfigKey("1");
	}

	@Test
	public void testGetConfigById() {
		ConfigDO config = configBO.getConfigById("JOB_DEFAULT_CRONEXPRESSION");
		Assert.assertNotNull(config);
		Assert.assertEquals("JOB_DEFAULT_CRONEXPRESSION", config.getConfigkey());
		Assert.assertEquals("0 0 8 * * ?", config.getConfigvalue());
		//Assert.assertEquals("任务默认参数：触发规则", config.getDescription());
	}

	@Test
	public void testFindConfigs() {
		ConfigDO config = new ConfigDO();
		Assert.assertNotNull(configBO.findConfigs(config));

	}

	@Test
	public void testDeleteConfigByConfigKey() {
		configBO.deleteConfigByConfigKey("JOB_DEFAULT_REPEATINTERVAL");
	}

	@Test
	public void testUpdateConfig() {

		ConfigDO config = new ConfigDO();
		config.setConfigkey("JOB_DEFAULT_CRONEXPRESSION");
		config.setConfigvalue("0 0 8 * * ?");
		config.setDescription("test");
		configBO.updateConfig(config);
	}

	@Test
	public void testGetStringValue() {
		Assert.assertEquals("0 0 8 * * ?", configBO.getStringValue("JOB_DEFAULT_CRONEXPRESSION"));

	}
	@Test
	public void testGetIntValue() {
		Assert.assertEquals(4, configBO.getIntValue("JOB_DEFAULT_PRIORITY").intValue());
		
	}
}
