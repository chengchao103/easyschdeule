/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.repeatalarm;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.RepeatAlarmDO;

/**
 * 
 * 重复告警BO
 * 
 * @author bolin.hbc
 * 
 */
@Transactional
public interface RepeatAlarmBO {

	/**
	 * 根据ID处理重复告警数据(alarmNum>0:--1，alarmNum<=0:delete)
	 * 
	 * @param id
	 */
	public void checkRepeatAlarm(Integer id);

	/**
	 * 插入或者重置
	 * 
	 * @param repeatAlarm
	 * @return
	 */
	public void saveOrUpdateRepeatAlarm(RepeatAlarmDO repeatAlarm);

	/**
	 * 根据ID获取重复告警的信息
	 * 
	 * @param id
	 * @return
	 */
	public RepeatAlarmDO getRepeatAlarmById(Integer id);

	/**
	 * 分页获取重复告警信息
	 * 
	 * 
	 * @param repeatAlarm
	 *            jobName jobGroup
	 * @return
	 */
	public List<RepeatAlarmDO> findRepeatAlarms(RepeatAlarmDO repeatAlarm);

	/**
	 * 获取重复告警的总数
	 * 
	 * @return
	 */
	public Integer getRepeatAlarmCount();

	/**
	 * 根据ID产生报警配置
	 * 
	 * @param id
	 * @return
	 */
	public void deleteRepeatAlarmById(Integer id);

	/**
	 * 根据jobName和jobGroup删除重复告警
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	public void deleteRepeatAlarm(String jobName, String jobGroup);

	/**
	 * 更新状态
	 * 
	 * @param repeatAlarm
	 * @return
	 */
	public void updateRepeatAlarmStatus(RepeatAlarmDO repeatAlarm);

}
