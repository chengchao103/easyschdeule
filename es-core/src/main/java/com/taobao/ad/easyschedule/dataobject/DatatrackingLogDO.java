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
import javax.persistence.Transient;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-8-13 下午02:34:12 类说明:DATTRACKINGJOB监控类
 */
@Entity(name = "es_datatracking_log")
public class DatatrackingLogDO {

	public int hashCode() {
		return super.hashCode();
	}

	public boolean equals(Object obj) {
		return this.equals(obj);
	}

	public DatatrackingLogDO() {
	}

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * jobName
	 */
	@Column(name = "JOB_NAME")
	private String jobName;

	/**
	 * jobGroup
	 */
	@Column(name = "JOB_GROUP")
	private String jobGroup;

	/**
	 * trackingValue
	 */
	@Column(name = "tracking_value")
	private Double trackingValue;
	/**
	 * createTime
	 */
	@Column(name = "CREATETIME")
	private Date createTime;
	/**
	 * logNumber页面展现的数据量
	 */
	@Transient
	private int logNumber;
	@Transient
	private Date queryStartTime;
	@Transient
	private Date queryEndTime;

	public Date getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(Date queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public Date getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(Date queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public Long getId() {
		return id;
	}

	public int getLogNumber() {
		return logNumber;
	}

	public void setLogNumber(int logNumber) {
		this.logNumber = logNumber;
	}

	public void setId(Long id) {
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

	public Double getTrackingValue() {
		return trackingValue;
	}

	public void setTrackingValue(Double trackingValue) {
		this.trackingValue = trackingValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
