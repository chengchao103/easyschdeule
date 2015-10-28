/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.reportjob.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.taobao.ad.easyschedule.bo.reportjob.ReportJobBO;
import com.taobao.ad.easyschedule.dao.reportjob.ReportJobDAO;
import com.taobao.ad.easyschedule.dataobject.ReportJobDO;

/**
 * Job的天维度报表 BOImplp
 * 
 * @author bolin.hbc
 * 
 */
public class ReportJobBOImpl implements ReportJobBO {

	private ReportJobDAO reportJobDAO;
 

	@Override
	public void saveOrUpdateReportJob(ReportJobDO reportJob) {

		ReportJobDO oldReportJob = reportJobDAO.getReportJobByTime(reportJob.getReportTime());
		if (oldReportJob == null) {
			reportJobDAO.insertReportJob(reportJob);
		} else {
			oldReportJob.setSuccessNum(reportJob.getSuccessNum());
			oldReportJob.setErrorNum(reportJob.getErrorNum());
			oldReportJob.setJobNum(reportJob.getJobNum());
			oldReportJob.setRt(reportJob.getRt());
			reportJobDAO.updateReportJob(oldReportJob);
		}

	}

	@Override
	public List<ReportJobDO> findReportJob(ReportJobDO reportJob) {
		List<ReportJobDO> resultList = reportJobDAO.findReportJob(reportJob);

		for (ReportJobDO job : resultList) {
			job.setTotalNum(job.getSuccessNum() + job.getErrorNum());
			job.setSuccessRate(new BigDecimal((job.getSuccessNum() * 100 + 0.0) / job.getTotalNum()).setScale(2, RoundingMode.HALF_DOWN));
			job.setErrorRate(new BigDecimal(100).subtract(job.getSuccessRate()).setScale(2, RoundingMode.HALF_DOWN));
		}

		return resultList;
	}

	public void setReportJobDAO(ReportJobDAO reportJobDAO) {
		this.reportJobDAO = reportJobDAO;
	}

	}
