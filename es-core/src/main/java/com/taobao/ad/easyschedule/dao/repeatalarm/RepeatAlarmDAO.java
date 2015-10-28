/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.repeatalarm;

import java.util.List;

import com.taobao.ad.easyschedule.dataobject.RepeatAlarmDO;

public interface RepeatAlarmDAO {
	public void insertRepeatAlarm(RepeatAlarmDO repeatAlarm);

	public RepeatAlarmDO getRepeatAlarmById(Integer id);

	public List<RepeatAlarmDO> findRepeatAlarms(RepeatAlarmDO repeatAlarm);

	public void deleteRepeatAlarmById(Integer id);

	public void updateRepeatAlarm(RepeatAlarmDO repeatAlarm);

	public void updateRepeatAlarmStatus(RepeatAlarmDO repeatAlarm);

	public Integer getRepeatAlarmCount();

	public void deleteRepeatAlarm(String jobName, String jobGroup);

	public RepeatAlarmDO getRepeatAlarm(String jobName, String jobGroup);

}