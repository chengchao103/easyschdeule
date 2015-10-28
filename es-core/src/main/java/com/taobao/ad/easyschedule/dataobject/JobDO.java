/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dataobject;

import static com.taobao.ad.easyschedule.commons.utils.StringUtil.bSubstring;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import org.quartz.JobDataMap;

import com.taobao.ad.easyschedule.base.PageInfo;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-9-1 下午07:51:27 类说明:
 */
@Entity(name = "es_job_details")
@IdClass(JobKey.class)
public class JobDO extends PageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String jobname;
	@Id
	private String jobgroup;
	@Column(name = "description")
	private String description;
	@Column(name = "job_class_name")
	private String jobclassname;
	@Column(name = "is_durable")
	private String isdurable;
	@Column(name = "is_volatile")
	private String isvolatile;
	@Column(name = "is_stateful")
	private String isstateful;
	@Column(name = "requests_recovery")
	private String requestrecovery;
	@Column(name = "job_data")
	private String jobdata;
	@Transient
	private JobDataMap jobDataMap;
	@Transient
	private JobUserSubDO successUserSub;
	@Transient
	private JobUserSubDO failUserSub;
	@Transient
	private JobUserSubDO warnUserSub;

	// 以下为触发器相关参数
	@Transient
	private Date nextfiretime;
	@Transient
	private Date prevfiretime;
	@Transient
	private int priority;
	@Transient
	private String triggerstate;
	@Transient
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

	public JobDO() {

	}

	public int hashCode() {
		return super.hashCode();
	}

	public boolean equals(Object obj) {

		return this.equals(obj);
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

	public JobUserSubDO getWarnUserSub() {
		return warnUserSub;
	}

	public void setWarnUserSub(JobUserSubDO warnUserSub) {
		this.warnUserSub = warnUserSub;
	}

	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}

	public String getDescription() {
		return description;
	}
	
	//获取短任务描述
	public String getShortDescription() {
		if (description != null && description.getBytes().length > 30) {
            return bSubstring(description,29) + "...";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobclassname() {
		return jobclassname;
	}

	public void setJobclassname(String jobclassname) {
		this.jobclassname = jobclassname;
	}

	public String getIsdurable() {
		return isdurable;
	}

	public void setIsdurable(String isdurable) {
		this.isdurable = isdurable;
	}

	public String getIsvolatile() {
		return isvolatile;
	}

	public void setIsvolatile(String isvolatile) {
		this.isvolatile = isvolatile;
	}

	public String getIsstateful() {
		return isstateful;
	}

	public void setIsstateful(String isstateful) {
		this.isstateful = isstateful;
	}

	public String getRequestrecovery() {
		return requestrecovery;
	}

	public void setRequestrecovery(String requestrecovery) {
		this.requestrecovery = requestrecovery;
	}

	public String getJobdata() {
		return jobdata;
	}

	public void setJobdata(String jobdata) {
		this.jobdata = jobdata;
	}

	public JobDataMap getJobDataMap() {
		return jobDataMap;
	}

	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

	public JobUserSubDO getSuccessUserSub() {
		return successUserSub;
	}

	public void setSuccessUserSub(JobUserSubDO successUserSub) {
		this.successUserSub = successUserSub;
	}

	public JobUserSubDO getFailUserSub() {
		return failUserSub;
	}

	public void setFailUserSub(JobUserSubDO failUserSub) {
		this.failUserSub = failUserSub;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
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

}

@Embeddable
class JobKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "job_name")
	private String jobname;
	@Column(name = "job_group")
	private String jobgroup;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobname == null) ? 0 : jobname.hashCode());
		result = prime * result + ((jobgroup == null) ? 0 : jobgroup.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final JobKey other = (JobKey) obj;
		if (jobname == null) {
			if (other.jobname != null)
				return false;
		} else if (!jobname.equals(other.jobname))
			return false;
		if (jobgroup == null) {
			if (other.jobgroup != null)
				return false;
		} else if (!jobgroup.equals(other.jobgroup))
			return false;
		return true;
	}

}