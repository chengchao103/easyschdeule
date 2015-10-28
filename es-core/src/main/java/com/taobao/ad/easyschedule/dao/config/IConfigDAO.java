/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.config;

import java.util.List;

import com.taobao.ad.easyschedule.dataobject.ConfigDO;

public interface IConfigDAO {

	public void insertConfig(ConfigDO config);

	public void deleteConfigByConfigKey(String configkey);

	public void updateConfig(ConfigDO config);

	public ConfigDO getConfigById(String id);

	public ConfigDO getConfigByIdFromCache(String id);

	public List<ConfigDO> findConfigs(ConfigDO config);

	public List<ConfigDO> findAllConfigs(ConfigDO config);

	public List<ConfigDO> findAllConfigsFromCache();

}