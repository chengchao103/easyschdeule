/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.scheduler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.scheduler.ISchedulerDAO;
import com.taobao.ad.easyschedule.dataobject.SchedulerDO;

/**
 * @author baimei
 * 
 */
public class JPASchedulerDAOImpl extends JPABaseDAO implements ISchedulerDAO {

	public SchedulerDO getScheduler(String instanceName) throws DataAccessException {
		return this.getJpaTemplate().find(SchedulerDO.class, instanceName);
	}

	@SuppressWarnings("unchecked")
	public List<SchedulerDO> findSchedulers(SchedulerDO schedulerDO) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.instance_name", schedulerDO.getInstanceName());
		schedulerDO.setTotalItem(super.getQueryCount("select count(*) from es_scheduler_state t", map));
		return super.queryForListIsNotEmpty("select t from es_scheduler_state t", map, schedulerDO, "order by t.instanceName");
	}
}
