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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import com.taobao.ad.easyschedule.base.PageInfo;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-9-2 下午08:27:50 类说明:触发器DO
 */
@Entity(name = "es_triggers")
@IdClass(TriggerKey.class)
public class TriggerDO extends PageInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String triggername;
	@Id
	private String triggergroup;
	@Column(name = "JOB_NAME")
	private String jobname;
	@Column(name = "JOB_GROUP")
	private String jobgroup;
	@Transient
	private Date nextfiretime;
	@Transient
	private Date prevfiretime;
	@Column(name = "PRIORITY")
	private String priority;
	@Column(name = "TRIGGER_STATE")
	private String triggerstate;
	@Column(name = "TRIGGER_TYPE")
	private String triggertype;
	@Transient
	private Date starttime;
	@Transient
	private Date endtime;
	@Transient
	private int repeatCount;
	@Transient
	private long repeatInterval;
	@Transient
	private String cronExpression;
	
	public String getTriggername() {
		return triggername;
	}

	public void setTriggername(String triggername) {
		this.triggername = triggername;
	}

	public String getTriggergroup() {
		return triggergroup;
	}

	public void setTriggergroup(String triggergroup) {
		this.triggergroup = triggergroup;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getJobgroup() {
		return jobgroup;
	}

	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTriggerstate() {
		return triggerstate;
	}

	public void setTriggerstate(String triggerstate) {
		this.triggerstate = triggerstate;
	}

	public String getTriggertype() {
		return triggertype;
	}

	public void setTriggertype(String triggertype) {
		this.triggertype = triggertype;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public long getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Date getNextfiretime() {
		return nextfiretime;
	}

	public void setNextfiretime(Date nextfiretime) {
		this.nextfiretime = nextfiretime;
	}

	public Date getPrevfiretime() {
		return prevfiretime;
	}

	public void setPrevfiretime(Date prevfiretime) {
		this.prevfiretime = prevfiretime;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

}

@Embeddable
class TriggerKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "TRIGGER_NAME")
	private String triggername;
	@Column(name = "TRIGGER_GROUP")
	private String triggergroup;

	public TriggerKey() {
	}

	public int hashCode() {
		return this.hashCode();
	}

	public boolean equals(Object obj) {
		return this.equals(obj);
	}

	public String getTriggername() {
		return triggername;
	}

	public void setTriggername(String triggername) {
		this.triggername = triggername;
	}

	public String getTriggergroup() {
		return triggergroup;
	}

	public void setTriggergroup(String triggergroup) {
		this.triggergroup = triggergroup;
	}

}