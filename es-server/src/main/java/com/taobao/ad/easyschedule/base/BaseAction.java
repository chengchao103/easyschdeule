/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.base;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.taobao.ad.easyschedule.interceptor.ThreadLocalManager;

public class BaseAction extends ActionSupport implements SessionAware {

	/**
	 * Es基础Action
	 * 
	 * @author baimei
	 */
	private static final long serialVersionUID = -2341240694617243423L;

	protected final static Logger logger = LoggerFactory.getLogger(BaseAction.class);

	protected String jobName = "";

	protected String jobGroup = "";

	protected String triggerGroup = "";

	protected String description;

	protected String triggerName;

	protected Date startTimeAsDate;

	protected Date stopTimeAsDate;

	protected Scheduler easyscheduler;

	protected Map<String, Object> session;

	public void setEasyscheduler(Scheduler easyscheduler) {
		this.easyscheduler = easyscheduler;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public String getLogname() {

		return ThreadLocalManager.getLoginUser().getUsername();
	}

	public String getNickname() {

		return ThreadLocalManager.getLoginUser().getDescn();

	}

	public String getJobGroup() {
		return jobGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobGroup(String string) {
		jobGroup = string;
	}

	public void setJobName(String string) {
		jobName = string;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTimeAsDate() {
		return startTimeAsDate;
	}

	public void setStartTimeAsDate(Date startTimeAsDate) {
		this.startTimeAsDate = startTimeAsDate;
	}

	public Date getStopTimeAsDate() {
		return stopTimeAsDate;
	}

	public void setStopTimeAsDate(Date stopTimeAsDate) {
		this.stopTimeAsDate = stopTimeAsDate;
	}


}
