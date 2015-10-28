/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.config.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.config.IConfigDAO;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;

@SuppressWarnings("unchecked")
public class JPAConfigDAOImpl extends JPABaseDAO implements IConfigDAO {

	public void insertConfig(ConfigDO config) {
		getJpaTemplate().persist(config);
	}

	public ConfigDO getConfigById(String id) {
		return getJpaTemplate().find(ConfigDO.class, id);
	}

	public ConfigDO getConfigByIdFromCache(String id) {
		return getConfigById(id);
	}

	public List<ConfigDO> findConfigs(ConfigDO config) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.configkey", config.getConfigkey());
		map.put("t.configvalue", config.getConfigvalue());
		config.setTotalItem(super.getQueryCount("select count(*) from es_config t", map));
		return super.queryForListIsNotEmpty("select t from es_config t", map, config, "order by CONFIGKEY desc ");
	}

	public List<ConfigDO> findAllConfigs(ConfigDO config) {
		return (List<ConfigDO>) getJpaTemplate().find("from es_config");
	}

	public List<ConfigDO> findAllConfigsFromCache() {
		ConfigDO config = new ConfigDO();
		config.setPerPageSize(1000);
		return findConfigs(config);
	}

	public void deleteConfigByConfigKey(String configkey) {
		getJpaTemplate().remove(getConfigById(configkey));
	}

	public void updateConfig(ConfigDO config) {
		getJpaTemplate().merge(config);
	}

}