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

/**
 * @author bolin.hbc
 * 
 *         异步队列检测
 * 
 */
@Entity(name = "es_async_job_queue")
public class AsyncJobQueueDO extends PageInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "JOB_NAME")
	private String jobname;
	@Column(name = "JOB_GROUP")
	private String jobgroup;
	@Column(name = "SIGN_TIME")
	private Long signTime;
	@Column(name = "COMPLETE_TIME")
	private Long completeTime;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "CREATETIME")
	private Date createTime;
	@Column(name = "UPDATETIME")
	private Date updateTime;
	@Column(name = "WAIT_CALL_NUM")
	private Integer waitCallNum;

	public Date getSTime() {

		return new Date(signTime);
	}

	public Date getCTime() {
		return new Date(completeTime);
	}

	public Integer getWaitCallNum() {
		return waitCallNum;
	}

	public void setWaitCallNum(Integer waitCallNum) {
		this.waitCallNum = waitCallNum;
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

	public Long getSignTime() {
		return signTime;
	}

	public void setSignTime(Long signTime) {
		this.signTime = signTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Long completeTime) {
		this.completeTime = completeTime;
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
