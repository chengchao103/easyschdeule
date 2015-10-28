/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dataobject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.taobao.ad.easyschedule.base.PageInfo;

@Entity(name = "es_repeat_alarm")
public class RepeatAlarmDO extends PageInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/** 任务名称 */
	@Column(name = "JOB_NAME")
	private String jobName;
	/** 任务组 */
	@Column(name = "JOB_GROUP")
	private String jobGroup;
	/** 任务失败时间戳 */
	@Column(name = "SIGN_TIME")
	private long signTime;
	/** 重复告警次数 */
	@Column(name = "REPEAT_ALARM_NUM")
	private int repeatAlarmNum;
	/** 状态 1：有效 0：无效 */
	@Column(name = "STATUS")
	private Integer status = 1;
	/** 创建时间 */
	@Column(name = "CREATE_TIME")
	private Date createTime;
	/** 更新时间 */
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public String getMessage() {
		return getJobGroup() + getJobName() + "_失败后无人处理,请特别关注_" + getRepeatAlarmNum();
	}

	public String getContent() {
		return "重复告警:" + getJobGroup() + getJobName() + "_失败后无人处理,请特别关注_" + getRepeatAlarmNum();
	}

	public Integer getId() {
		return id;
	}

	public Date getSTime() {
		return new Date(this.getSignTime());
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public long getSignTime() {
		return signTime;
	}

	public void setSignTime(long signTime) {
		this.signTime = signTime;
	}

	public int getRepeatAlarmNum() {
		return repeatAlarmNum;
	}

	public void setRepeatAlarmNum(int repeatAlarmNum) {
		this.repeatAlarmNum = repeatAlarmNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
