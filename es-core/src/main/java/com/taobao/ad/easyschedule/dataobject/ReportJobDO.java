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

@Entity(name = "es_report_job")
public class ReportJobDO extends PageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int REPORT_TYPE_JOB_TOTAL_NUM = 0;
	public static final int REPORT_TYPE_JOB_SUCCESS_NUM = 1;
	public static final int REPORT_TYPE_JOB_SUCCESS_RATE = 2;
	public static final int REPORT_TYPE_JOB_ERROR_NUM = 3;
	public static final int REPORT_TYPE_JOB_ERROR_RATE = 4;
	public static final int REPORT_TYPE_JOB_AGV_RT = 5;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "success_num")
	private Long successNum;
	@Column(name = "error_num")
	private Long errorNum;
	@Column(name = "job_num")
	private Long jobNum;
	@Column(name = "rt")
	private BigDecimal rt;
	@Column(name = "status")
	private Integer status;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "report_time")
	private Date reportTime;

	@Transient
	private Date startTime;
	@Transient
	private Date endTime;

	/** 成功率 */
	@Transient
	private BigDecimal successRate;
	/** 失败率 */
	@Transient
	private BigDecimal errorRate;
	@Transient
	private Long totalNum;

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getSuccessRate() {
		return successRate;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Long successNum) {
		this.successNum = successNum;
	}

	public Long getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Long errorNum) {
		this.errorNum = errorNum;
	}

	public Long getJobNum() {
		return jobNum;
	}

	public void setJobNum(Long jobNum) {
		this.jobNum = jobNum;
	}

	public BigDecimal getRt() {
		return rt;
	}

	public void setRt(BigDecimal rt) {
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

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public static String getReportJobPageName(int type) {
		switch (type) {

		case ReportJobDO.REPORT_TYPE_JOB_TOTAL_NUM:
			return "任务总数";
		case ReportJobDO.REPORT_TYPE_JOB_AGV_RT:
			return "平均响应时间";
		case ReportJobDO.REPORT_TYPE_JOB_ERROR_NUM:
			return "任务失败数";
		case ReportJobDO.REPORT_TYPE_JOB_ERROR_RATE:
			return "任务失败率";
		case ReportJobDO.REPORT_TYPE_JOB_SUCCESS_NUM:
			return "任务成功数";
		case ReportJobDO.REPORT_TYPE_JOB_SUCCESS_RATE:

			return "任务成功率";

		}
		return null;
	}

}
