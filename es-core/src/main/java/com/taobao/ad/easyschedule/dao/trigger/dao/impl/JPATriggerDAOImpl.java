/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.trigger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.trigger.dao.TriggerDAO;
import com.taobao.ad.easyschedule.dataobject.TriggerDO;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-9-2 下午09:07:45 类说明:
 */

public class JPATriggerDAOImpl extends JPABaseDAO implements TriggerDAO {

	@SuppressWarnings("unchecked")
	public List<TriggerDO> getTriggerList(TriggerDO triggerDO) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobname", triggerDO.getJobname());
		map.put("t.jobgroup", triggerDO.getJobgroup());
		triggerDO.setTotalItem(super.getQueryCount("select count(*) from es_triggers t", map));
		return super.queryForListIsNotEmpty("select t from es_triggers t ", map, triggerDO, "order by t.jobgroup, t.jobname");
	}
}
