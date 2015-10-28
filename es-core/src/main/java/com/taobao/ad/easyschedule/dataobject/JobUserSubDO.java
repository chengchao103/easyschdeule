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

/**
 * @author bolin.hbc 用户任务订阅表
 * 
 */
@IdClass(JobUserSubKey.class)
@Entity(name = "es_job_user_sub")
public class JobUserSubDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String jobName;
	@Id
	private String jobGroup;
	@Id
	private Long userId;
	@Id
	private int type;
	@Column(name = "wangwang")
	private int wangwang;
	@Column(name = "email")
	private int email;
	@Column(name = "mobile")
	private int mobile;
	@Column(name = "createtime")
	private Date createtime;
	@Column(name = "updatetime")
	private Date updatetime;
	@Column(name = "creator")
	private int creator;
	@Column(name = "modifier")
	private int modifier;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getWangwang() {
		return wangwang;
	}

	public void setWangwang(int wangwang) {
		this.wangwang = wangwang;
	}

	public int getEmail() {
		return email;
	}

	public void setEmail(int email) {
		this.email = email;
	}

	public int getMobile() {
		return mobile;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}

@Embeddable
class JobUserSubKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "job_name")
	private String jobName;
	@Column(name = "job_group")
	private String jobGroup;
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "type")
	private int type;

	public JobUserSubKey() {
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}