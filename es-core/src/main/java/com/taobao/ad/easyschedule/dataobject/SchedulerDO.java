/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dataobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.taobao.ad.easyschedule.base.PageInfo;

/**
 * @author baimei
 */
@Entity(name = "es_scheduler_state")
public class SchedulerDO extends PageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "instance_name")
	private String instanceName;

	@Column(name = "last_checkin_time")
	private long lastCheckinTime;

	@Column(name = "checkin_interval")
	private long checkinInterval;

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public long getLastCheckinTime() {
		return lastCheckinTime;
	}

	public void setLastCheckinTime(long lastCheckinTime) {
		this.lastCheckinTime = lastCheckinTime;
	}

	public long getCheckinInterval() {
		return checkinInterval;
	}

	public void setCheckinInterval(long checkinInterval) {
		this.checkinInterval = checkinInterval;
	}

}