/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.scheduler;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.taobao.ad.easyschedule.dataobject.SchedulerDO;

/**
 * @author baimei
 */

public interface ISchedulerDAO {

	public SchedulerDO getScheduler(String instanceName) throws DataAccessException;

	public List<SchedulerDO> findSchedulers(SchedulerDO schedulerDO) throws DataAccessException;
}
