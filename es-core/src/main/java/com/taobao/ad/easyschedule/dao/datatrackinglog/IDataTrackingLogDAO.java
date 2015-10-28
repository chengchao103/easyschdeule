/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.datatrackinglog;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.taobao.ad.easyschedule.dataobject.DatatrackingLogDO;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-8-13 下午02:41:45 类说明:DATTRACKINGJOB BO持久化類
 */

public interface IDataTrackingLogDAO {
	public void insertDataTrackingLog(DatatrackingLogDO datatrackingLogDO) throws DataAccessException;

	public List<DatatrackingLogDO> getDataTrackingByGroupAndName(DatatrackingLogDO datatrackingLogDO) throws DataAccessException;
 }
