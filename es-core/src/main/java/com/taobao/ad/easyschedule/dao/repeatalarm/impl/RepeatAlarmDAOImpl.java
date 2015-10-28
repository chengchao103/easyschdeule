/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.repeatalarm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.repeatalarm.RepeatAlarmDAO;
import com.taobao.ad.easyschedule.dataobject.RepeatAlarmDO;

public class RepeatAlarmDAOImpl extends JPABaseDAO implements RepeatAlarmDAO {

	@Override
	public void deleteRepeatAlarmById(Integer id) {
		RepeatAlarmDO repeatAlarm = getRepeatAlarmById(id);
		if (repeatAlarm != null) {
			getJpaTemplate().remove(repeatAlarm);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepeatAlarmDO> findRepeatAlarms(RepeatAlarmDO repeatAlarm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobName", repeatAlarm.getJobName());
		map.put("t.jobGroup", repeatAlarm.getJobGroup());
		repeatAlarm.setTotalItem(super.getQueryCount("select count(*) from es_repeat_alarm t", map));
		return super.queryForListIsNotEmpty("select t from es_repeat_alarm t", map, repeatAlarm, "order by ID desc");
	}

	@Override
	public RepeatAlarmDO getRepeatAlarmById(Integer id) {
		return super.getJpaTemplate().find(RepeatAlarmDO.class, id);
	}

	@Override
	public void insertRepeatAlarm(RepeatAlarmDO repeatAlarm) {
		repeatAlarm.setCreateTime(new Date());
		repeatAlarm.setUpdateTime(new Date());
		getJpaTemplate().persist(repeatAlarm);
	}

	@Override
	public void updateRepeatAlarm(final RepeatAlarmDO repeatAlarm) {
		RepeatAlarmDO r = getRepeatAlarm(repeatAlarm.getJobName(), repeatAlarm.getJobGroup());
		r.setRepeatAlarmNum(repeatAlarm.getRepeatAlarmNum());
		r.setUpdateTime(new Date());
		getJpaTemplate().merge(r);
	}

	@Override
	public void updateRepeatAlarmStatus(final RepeatAlarmDO repeatAlarm) {
		RepeatAlarmDO r = getRepeatAlarm(repeatAlarm.getJobName(), repeatAlarm.getJobGroup());
		if (r != null) {
			r.setStatus(repeatAlarm.getStatus());
			r.setUpdateTime(new Date());
			getJpaTemplate().merge(r);
		}
	}

	@Override
	public Integer getRepeatAlarmCount() {
		return super.getQueryCount("select count(*) from es_repeat_alarm t", new HashMap<String, Object>());
	}

	@Override
	public void deleteRepeatAlarm(final String jobName, final String jobGroup) {
		RepeatAlarmDO r = getRepeatAlarm(jobName, jobGroup);
		if (r != null)
			getJpaTemplate().remove(r);
	}

	@Override
	public RepeatAlarmDO getRepeatAlarm(String jobName, String jobGroup) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobName", jobName);
		map.put("t.jobGroup", jobGroup);
		return (RepeatAlarmDO) super.executeSingleIsNotEmpty("select t from es_repeat_alarm t", map);
	}
}