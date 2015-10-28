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
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.taobao.ad.easyschedule.base.PageInfo;

@Entity(name = "es_report_job_rt")
public class ReportJobRtDO extends PageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 删除 */
	public static final int REPORT_JOB_RT_STATUS_DELETE = 0;
	/** 执行中 */
	public static final int REPORT_JOB_RT_STATUS_EXECUTION = 1;
	/** 成功完成 */
	public static final int REPORT_JOB_RT_STATUS_SUCCESS = 2;
	/** 失败完成 */
	public static final int REPORT_JOB_RT_STATUS_FAILED = 3;
	/** 超时完成 */
	public static final int REPORT_JOB_RT_STATUS_TIME_OUT = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "job_name")
	private String jobName;
	@Column(name = "job_group")
	private String jobGroup;
	@Column(name = "rt")
	private Long rt;
	@Column(name = "status")
	private Integer status;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;
	@Column(name = "sync")
	private String sync;

	@Column(name = "start_time")
	private Long startTime;
	@Column(name = "end_time")
	private Long endTime;
	@Transient
	private Date queryStartTime;
	@Transient
	private Date queryEndTime;

	/** 成功率 */
	@Transient
	private BigDecimal successRate;
	/** 失败率 */
	@Transient
	private BigDecimal errorRate;

	/** 任务执行总数 */
	@Transient
	private int totalNum;
	/** 展示的RT */
	@Transient
	private BigDecimal showRt;
	/** 成功率排序,true倒叙,false正序 */
	@Transient
	private boolean successRateOrderBy = true;

	public boolean isSuccessRateOrderBy() {
		return successRateOrderBy;
	}

	public boolean getSuccessRateOrderBy() {

		return successRateOrderBy;
	}

	public void setSuccessRateOrderBy(boolean successRateOrderBy) {
		this.successRateOrderBy = successRateOrderBy;
	}

	public BigDecimal getSuccessRate() {
		return successRate;
	}

	@Transient
	private int successNum;
	@Transient
	private int errorNum;

	public int getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}

	public int getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}

	public void setSuccessRate(BigDecimal successRate) {
		this.successRate = successRate;
	}

	public BigDecimal getErrorRate() {
		return errorRate;
	}

	public void setErrorRate(BigDecimal errorRate) {
		this.errorRate = errorRate;
	}

	public BigDecimal getShowRt() {

		if (this.showRt == null) {
			return new BigDecimal(0);
		}
		return showRt;
	}

	public void setShowRt(BigDecimal showRt) {
		this.showRt = showRt;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getId() {
		return id;
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

	public Long getRt() {
		return rt;
	}

	public void setRt(Long rt) {
		this.rt = rt;
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

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

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

}
