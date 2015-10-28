/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.config.impl;

import java.util.List;

import com.taobao.ad.easyschedule.bo.config.IConfigBO;
import com.taobao.ad.easyschedule.dao.config.IConfigDAO;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;

public class ConfigBOImpl implements IConfigBO {

	private IConfigDAO configDAO;

	public String getStringValue(String key) {
/*		List<ConfigDO> configList = configDAO.findAllConfigsFromCache();
		for (ConfigDO config : configList) {
			if (key.equals(config.getConfigkey())) {
				return config.getConfigvalue();
			}
		}
		return null;*/
		ConfigDO config = configDAO.getConfigById(key);
		if (config == null) {
			return null;
		}
		return config.getConfigvalue();
	}

	public Integer getIntValue(String key) {
/*		List<ConfigDO> configList = configDAO.findAllConfigsFromCache();
		for (ConfigDO config : configList) {
			if (key.equals(config.getConfigkey())) {
				return Integer.parseInt(config.getConfigvalue());
			}
		}
		return null;*/
		ConfigDO config = configDAO.getConfigById(key);
		if (config == null) {
			return null;
		}
		return Integer.parseInt(config.getConfigvalue());
	}

	public Long insertConfig(ConfigDO config) {
		if (configDAO.getConfigById(config.getConfigkey()) != null) {
			return -3L;
		}
		configDAO.insertConfig(config);
		return 1L;
	}

	public ConfigDO getConfigById(String id) {
		return configDAO.getConfigById(id);
	}

	public List<ConfigDO> findConfigs(ConfigDO config) {
		return configDAO.findConfigs(config);
	}

	public void deleteConfigByConfigKey(String configkey) {
		configDAO.deleteConfigByConfigKey(configkey);
	}

	public void updateConfig(ConfigDO config) {
		configDAO.updateConfig(config);
	}

	public void setConfigDAO(IConfigDAO configDAO) {
		this.configDAO = configDAO;
	}
}