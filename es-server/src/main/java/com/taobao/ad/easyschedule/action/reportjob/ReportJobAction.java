/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.reportjob;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.bo.job.JobBO;
import com.taobao.ad.easyschedule.bo.reportjob.ReportJobBO;
import com.taobao.ad.easyschedule.bo.reportjobrt.ReportJobRtBO;
import com.taobao.ad.easyschedule.commons.utils.DateTimeUtil;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.dataobject.ReportJobDO;
import com.taobao.ad.easyschedule.dataobject.ReportJobRtDO;

/**
 * 
 * 任务日报表操作
 * 
 * @author bolin.hbc
 * 
 */
public class ReportJobAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private JobResult jobResult;
	private JobBO jobBO;
	private ReportJobRtBO reportJobRtBO;
	private ReportJobBO reportJobBO;
	private Date startTime;
	private Date endTime;
	private List<ReportJobRtDO> jobRtList;
	private ReportJobRtDO query;
	private List<ReportJobDO> jobReportList;
	private ICodeBO codeBO;
	private List<CodeDO> groups;
	private String reportValue;
	private int reportType;
	private String pageName;
	private Integer start;
	private Integer end;
	private String callback;

	/**
	 * 每天凌晨12:05初始化昨天的数据
	 * 
	 * @return
	 */
	public String initJobReport() {
		jobResult = JobResult.succcessResult();
		try {
			Date startTime = DateTimeUtil.getDateBeforeOrAfterV2(-1);
			Date endTime = DateTimeUtil.getDateBeforeOrAfterV2(0);

			int jobNum = jobBO.getCurrentJobNum();
			int successNum = reportJobRtBO.getCount(ReportJobRtDO.REPORT_JOB_RT_STATUS_SUCCESS, startTime, endTime);
			int errorNum = reportJobRtBO.getCount(ReportJobRtDO.REPORT_JOB_RT_STATUS_TIME_OUT, startTime, endTime)
					+ reportJobRtBO.getCount(ReportJobRtDO.REPORT_JOB_RT_STATUS_FAILED, startTime, endTime);
			/*
			 * 
			 * reportJobRtBO.getCount(ReportJobRtDO.REPORT_JOB_RT_STATUS_DELETE,
			 * startTime, endTime) 删除的以后也不记入统计
			 */

			if (successNum == 0 && errorNum == 0) {
				return SUCCESS;
			}

			double rt = reportJobRtBO.getAverageRt(startTime, endTime);

			ReportJobDO job = new ReportJobDO();
			job.setJobNum(Long.valueOf(jobNum));
			job.setErrorNum(Long.valueOf(errorNum));
			job.setSuccessNum(Long.valueOf(successNum));
			job.setRt(new BigDecimal(rt).setScale(2, RoundingMode.HALF_DOWN));
			job.setReportTime(DateTimeUtil.getDateBeforeOrAfterV2(-1));
			reportJobBO.saveOrUpdateReportJob(job);
		} catch (Exception e) {
			jobResult = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, e.getMessage());
			logger.error("initJobReport error", e);

		}
		return SUCCESS;
	}


	/**
	 * 查看汇总报表视图
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewReportJob() throws Exception {

		initQueryTime();
		pageName = ReportJobDO.getReportJobPageName(reportType);
		return SUCCESS;

	}
	
	private void initQueryTime() {
		startTime = startTime == null ? DateTimeUtil.getDateBeforeOrAfterV2(-30) : startTime;
		endTime = endTime == null ? DateTimeUtil.getDateBeforeOrAfterV2(0) : endTime;

	}

	public void viewReportJobJson() throws IOException, Exception {
		initQueryTime();
		HttpServletResponse response = ServletActionContext.getResponse();
		StringBuffer value = new StringBuffer();
		ReportJobDO job = new ReportJobDO();
		// 并上23:59:59
		endTime = new Date(endTime.getTime() + 86399000);

		job.setStartTime(startTime);
		job.setEndTime(endTime);
		List<ReportJobDO> dataList = reportJobBO.findReportJob(job);
		value.append(org.apache.commons.lang.StringEscapeUtils.escapeHtml(callback) + "([");
		for (int i = 0; i < dataList.size(); i++) {
			ReportJobDO reportJob = dataList.get(i);
			double reportValue = getDoubleValue(reportJob, reportType);
			// 加上8个小时,解决非东八时区的bug
			value.append("[").append(reportJob.getReportTime().getTime() + 8 * 60 * 60 * 1000).append(",").append(reportValue).append("]");
			if (i < dataList.size() - 1) {
				value.append(",");
			}
		}
		value.append("]);");
		response.getWriter().write(value.toString());
	}

	public String listReportJobRt() {
		if (query == null) {
			query = new ReportJobRtDO();
			query.setQueryStartTime(DateTimeUtil.getDateBeforeOrAfterV2(-30));
			query.setQueryEndTime(DateTimeUtil.getDateBeforeOrAfterV2(0));
		}
		// 并上23:59:59
		query.setQueryEndTime(new Date(query.getQueryEndTime().getTime() + 86399000));
		// 任务明细报表 最多一个月
		if (DateTimeUtil.getDaysBetweenDates(query.getQueryStartTime(), query.getQueryEndTime()) > 30) {
			this.addActionError("任务明细报表跨度不能超过1个月");
			return ERROR;
		}

		query.setPerPageSize(100);
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		this.jobRtList = reportJobRtBO.findReportJobRtPageList(query);
		return SUCCESS;
	}

	private Double getDoubleValue(ReportJobDO job, int type) {

		switch (type) {

		case ReportJobDO.REPORT_TYPE_JOB_TOTAL_NUM:
			return job.getJobNum().doubleValue();
		case ReportJobDO.REPORT_TYPE_JOB_AGV_RT:
			return job.getRt().doubleValue();
		case ReportJobDO.REPORT_TYPE_JOB_ERROR_NUM:
			return job.getErrorNum().doubleValue();
		case ReportJobDO.REPORT_TYPE_JOB_ERROR_RATE:

			if (job.getSuccessNum() + job.getErrorNum() == 0) {
				return Double.valueOf(0);
			} else {

				return new BigDecimal(100).subtract(new BigDecimal((job.getSuccessNum() * 100 + 0.0) / (job.getSuccessNum() + job.getErrorNum())))
						.setScale(2, RoundingMode.HALF_DOWN).doubleValue();

			}
		case ReportJobDO.REPORT_TYPE_JOB_SUCCESS_NUM:
			return job.getSuccessNum().doubleValue();
		case ReportJobDO.REPORT_TYPE_JOB_SUCCESS_RATE:

			if (job.getSuccessNum() + job.getErrorNum() == 0) {
				return Double.valueOf(0);
			} else {
				return new BigDecimal((job.getSuccessNum() * 100 + 0.0) / (job.getSuccessNum() + job.getErrorNum())).setScale(2, RoundingMode.HALF_DOWN)
						.doubleValue();

			}

		}

		return null;
	}

	public ReportJobBO getReportJobBO() {
		return reportJobBO;
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

	public void setReportJobBO(ReportJobBO reportJobBO) {
		this.reportJobBO = reportJobBO;
	}

	public void setReportJobRtBO(ReportJobRtBO reportJobRtBO) {
		this.reportJobRtBO = reportJobRtBO;
	}

	public void setJobBO(JobBO jobBO) {
		this.jobBO = jobBO;
	}

	public JobResult getJobResult() {
		return jobResult;
	}

	public void setJobResult(JobResult jobResult) {
		this.jobResult = jobResult;
	}

	public List<ReportJobRtDO> getJobRtList() {
		return jobRtList;
	}

	public void setJobRtList(List<ReportJobRtDO> jobRtList) {
		this.jobRtList = jobRtList;
	}

	public ReportJobRtDO getQuery() {
		return query;
	}

	public void setQuery(ReportJobRtDO query) {
		this.query = query;
	}

	public List<ReportJobDO> getJobReportList() {
		return jobReportList;
	}

	public void setJobReportList(List<ReportJobDO> jobReportList) {
		this.jobReportList = jobReportList;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public List<CodeDO> getGroups() {
		return groups;
	}

	public void setGroups(List<CodeDO> groups) {
		this.groups = groups;
	}

	public String getReportValue() {
		return reportValue;
	}

	public void setReportValue(String reportValue) {
		this.reportValue = reportValue;
	}

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

}
