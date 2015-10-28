/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.reportjobrt.impl;

import java.util.Date;
import java.util.List;

import com.taobao.ad.easyschedule.bo.reportjobrt.ReportJobRtBO;
import com.taobao.ad.easyschedule.dao.reportjobrt.ReportJobRtDAO;
import com.taobao.ad.easyschedule.dataobject.ReportJobRtDO;

/**
 * Job的详细RT报表DAO
 * 
 * @author bolin.hbc
 * 
 */
public class ReportJobRtBOImpl implements ReportJobRtBO {

	private ReportJobRtDAO reportJobRtDAO;

	@Override
	public void updateReportJobRt(Long jobId, String jobGroup, String jobName, int status) {
		ReportJobRtDO rt = new ReportJobRtDO();
		ReportJobRtDO startRt = null;
		rt.setJobGroup(jobGroup);
		rt.setJobName(jobName);
		if (jobId == null) {
			startRt = getLastReportJobRt(jobGroup, jobName);
		} else {
			rt.setStartTime(jobId);
			startRt = getReportJobRt(rt);
		}
		if (startRt != null) {
			startRt.setStatus(status);
			Long t = System.currentTimeMillis();
			startRt.setEndTime(t);
			startRt.setRt(t - startRt.getStartTime());
			reportJobRtDAO.updateReportJobRt(startRt);;
		}
	}

	@Override
	public void insertReportJobRt(ReportJobRtDO reportJobRt) {
		reportJobRtDAO.insertReportJobRt(reportJobRt);
	}

	@Override
	public List<ReportJobRtDO> findReportJobRt(ReportJobRtDO reportJobRt) {
		return reportJobRtDAO.findReportJobRt(reportJobRt);
	}

	@Override
	public ReportJobRtDO getLastReportJobRt(String jobGroup, String jobName) {

		return reportJobRtDAO.getLastReportJobRt(jobGroup, jobName);
	}

	@Override
	public ReportJobRtDO getReportJobRt(ReportJobRtDO reportJobRt) {
		return reportJobRtDAO.getReportJobRt(reportJobRt);
	}

	@Override
	public Integer getSyncAndStatusCount(String sync, int status) {
		return reportJobRtDAO.getSyncAndStatusCount(sync, status);
	}

	@Override
	public List<ReportJobRtDO> findReportJobRtPageList(ReportJobRtDO reportJobRt) {
		return reportJobRtDAO.findReportJobRtPageList(reportJobRt);
	}

	@Override
	public Double getAverageRt(Date startTime, Date endTime) {
		return reportJobRtDAO.getAverageRt(startTime, endTime);
	}

	@Override
	public Integer getCount(int status, Date startTime, Date endTime) {
		return reportJobRtDAO.getCount(status, startTime, endTime);
	}

	public void setReportJobRtDAO(ReportJobRtDAO reportJobRtDAO) {
		this.reportJobRtDAO = reportJobRtDAO;
	}

}
