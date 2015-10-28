/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.config;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.ConfigDO;

@Transactional
public interface IConfigBO {

	/**
	 * 从缓存中根据KEY获取(String)VALUE
	 * 
	 */
	public String getStringValue(String key);

	/**
	 * 从缓存中根据KEY获取(Integer)VALUE
	 * 
	 */
	public Integer getIntValue(String key);

	public ConfigDO getConfigById(String id);

	public List<ConfigDO> findConfigs(ConfigDO config);

	public Long insertConfig(ConfigDO config);

	public void deleteConfigByConfigKey(String configkey);

	public void updateConfig(ConfigDO config);
}