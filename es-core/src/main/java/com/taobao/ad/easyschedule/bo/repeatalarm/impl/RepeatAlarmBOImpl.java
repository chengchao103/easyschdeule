/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.repeatalarm.impl;

import java.util.List;

import com.taobao.ad.easyschedule.bo.repeatalarm.RepeatAlarmBO;
import com.taobao.ad.easyschedule.dao.repeatalarm.RepeatAlarmDAO;
import com.taobao.ad.easyschedule.dataobject.RepeatAlarmDO;
import com.taobao.ad.easyschedule.manager.message.MessageManager;

/**
 * 
 * 重复告警BOImpl
 * 
 * @author bolin.hbc
 * 
 */
public class RepeatAlarmBOImpl implements RepeatAlarmBO {
	private RepeatAlarmDAO repeatAlarmDAO;
	private MessageManager messageManager;

	@Override
	public void checkRepeatAlarm(Integer id) {
		RepeatAlarmDO repeatAlarm = this.getRepeatAlarmById(id);
		if (repeatAlarm == null) {
			return;
		}
		if (repeatAlarm.getRepeatAlarmNum() > 0) {
			messageManager.sendFailMessage(repeatAlarm.getJobName(), repeatAlarm.getJobGroup(), repeatAlarm.getMessage(), repeatAlarm.getContent());
			if (repeatAlarm.getRepeatAlarmNum() == 1) {
				repeatAlarmDAO.deleteRepeatAlarmById(id);
			} else {
				repeatAlarm.setRepeatAlarmNum(repeatAlarm.getRepeatAlarmNum() - 1);
				repeatAlarmDAO.updateRepeatAlarm(repeatAlarm);
			}
		} else {
			repeatAlarmDAO.deleteRepeatAlarmById(id);
		}
	}

	@Override
	public void saveOrUpdateRepeatAlarm(RepeatAlarmDO repeatAlarm) {

		RepeatAlarmDO oldRepeatAlarm = repeatAlarmDAO.getRepeatAlarm(repeatAlarm.getJobName(), repeatAlarm.getJobGroup());
		if (oldRepeatAlarm == null) {
			repeatAlarm.setSignTime(System.currentTimeMillis());
			repeatAlarmDAO.insertRepeatAlarm(repeatAlarm);
		} else {
			repeatAlarm.setId(oldRepeatAlarm.getId());
			oldRepeatAlarm.setRepeatAlarmNum(repeatAlarm.getRepeatAlarmNum());
			repeatAlarmDAO.updateRepeatAlarm(oldRepeatAlarm);
		}
	}

	@Override
	public List<RepeatAlarmDO> findRepeatAlarms(RepeatAlarmDO repeatAlarm) {

		return repeatAlarmDAO.findRepeatAlarms(repeatAlarm);
	}

	@Override
	public RepeatAlarmDO getRepeatAlarmById(Integer id) {

		return repeatAlarmDAO.getRepeatAlarmById(id);
	}

	@Override
	public Integer getRepeatAlarmCount() {

		return repeatAlarmDAO.getRepeatAlarmCount();
	}

	@Override
	public void updateRepeatAlarmStatus(RepeatAlarmDO repeatAlarm) {
		repeatAlarmDAO.updateRepeatAlarmStatus(repeatAlarm);
	}

	@Override
	public void deleteRepeatAlarmById(Integer id) {
		repeatAlarmDAO.deleteRepeatAlarmById(id);
	}

	@Override
	public void deleteRepeatAlarm(String jobName, String jobGroup) {
		repeatAlarmDAO.deleteRepeatAlarm(jobName, jobGroup);
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public void setRepeatAlarmDAO(RepeatAlarmDAO repeatAlarmDAO) {
		this.repeatAlarmDAO = repeatAlarmDAO;
	}

}
