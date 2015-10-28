/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.datatrackinglog.impl;

import java.util.Collections;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.bo.datatrackinglog.IDataTrackingLogBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.JobUtil;
import com.taobao.ad.easyschedule.dao.datatrackinglog.IDataTrackingLogDAO;
import com.taobao.ad.easyschedule.dataobject.DatatrackingLogDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.dataobject.LogsDO;
import com.taobao.ad.easyschedule.manager.message.MessageManager;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-8-13 下午02:40:44 类说明:
 */

public class IDataTrackingLogBOImpl implements IDataTrackingLogBO {

	private IDataTrackingLogDAO dataTrackingLogDAO;
	private ILogsBO logsBO;
	private MessageManager messageManager;

	final static Logger logger = LoggerFactory.getLogger(IDataTrackingLogBOImpl.class);

	public void insertDataTrackingLog(DatatrackingLogDO datatrackingLogDO) {
		dataTrackingLogDAO.insertDataTrackingLog(datatrackingLogDO);
	}

	@SuppressWarnings("unchecked")
	public List<DatatrackingLogDO> getDataTrackingByGroupAndName(DatatrackingLogDO datatrackingLogDO) {
		try {

			return dataTrackingLogDAO.getDataTrackingByGroupAndName(datatrackingLogDO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public JobResult processDataTrackingLog(JobDetail jobDetail, JobResult jobResult) {
		JobResult result = JobResult.succcessResult();
		Double resultData = null;
		try {
			resultData = Double.parseDouble(jobResult.getResultMsg());
			if (resultData == null) {
				logger.error("processDataTrackingJob dataTrackingValue is empty!");
				result.setSuccess(false);
				result.setResultCode(JobResult.RESULTCODE_JOBRESULT_ILLEGAL);
				return result;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setResultCode(JobResult.RESULTCODE_JOBRESULT_ILLEGAL);
			return result;
		}
		// 插入监控日志
		DatatrackingLogDO datatrackingLogDO = new DatatrackingLogDO();
		datatrackingLogDO.setJobGroup(jobDetail.getGroup());
		datatrackingLogDO.setJobName(jobDetail.getName());
		datatrackingLogDO.setTrackingValue(resultData);
		insertDataTrackingLog(datatrackingLogDO);
		JobDataMap map = jobDetail.getJobDataMap();
		// 发送警告级别通知
		String subject = null;
		String equal_L1 = map.getString(Constants.JOBDATA_ISEQUAL_LEVEL1);
		String lessequal_L1 = map.getString(Constants.JOBDATA_ISLESSEQUAL_LEVEL1);
		String greaterequal_l1 = map.getString(Constants.JOBDATA_ISGREATEREQUAL_LEVEL1);
		if (equal_L1 != null && resultData.equals(new Double(equal_L1))) {
			subject = new StringBuffer(resultData.toString()).append("=").append(equal_L1).toString();
		} else if (lessequal_L1 != null && resultData <= new Double(lessequal_L1)) {
			subject = new StringBuffer(resultData.toString()).append("<=").append(lessequal_L1).toString();
		} else if (greaterequal_l1 != null && resultData >= new Double(greaterequal_l1)) {
			subject = new StringBuffer(resultData.toString()).append(">=").append(greaterequal_l1).toString();
		}
		if (subject != null) {
			String jobdata = JobUtil.getJobData(map);
			// 警告级别
			messageManager.sendWarningMessage(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getGroup() + jobDetail.getName() + "_" + subject, subject
					+ "<br>" + jobdata);
			logsBO.insertWarning(LogsDO.SUBTYPE_JOB_DATATRACKING_WARNING, jobDetail.getGroup() + "|" + jobDetail.getName(), subject + "<br>" + jobdata,
					LogsDO.USER_SYS);
		}
		// 发送错误级别通知
		subject = null;
		String equal_L2 = map.getString(Constants.JOBDATA_ISEQUAL_LEVEL2);
		String lessequal_L2 = map.getString(Constants.JOBDATA_ISLESSEQUAL_LEVEL2);
		String greaterequal_l2 = map.getString(Constants.JOBDATA_ISGREATEREQUAL_LEVEL2);
		if (equal_L2 != null && resultData.equals(new Double(equal_L2))) {
			subject = new StringBuffer(resultData.toString()).append("=").append(equal_L2).toString();
		} else if (lessequal_L2 != null && resultData <= new Double(lessequal_L2)) {
			subject = new StringBuffer(resultData.toString()).append("<=").append(lessequal_L2).toString();
		} else if (greaterequal_l2 != null && resultData >= new Double(greaterequal_l2)) {
			subject = new StringBuffer(resultData.toString()).append(">=").append(greaterequal_l2).toString();
		}
		if (subject != null) {
			String jobdata = JobUtil.getJobData(map);
			// 错误级别
			messageManager.sendFailMessage(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getGroup() + jobDetail.getName() + "_" + subject, subject
					+ "<br>" + jobdata);
			logsBO.insertWarning(LogsDO.SUBTYPE_JOB_DATATRACKING_WARNING, jobDetail.getGroup() + "|" + jobDetail.getName(), subject + "<br>" + jobdata,
					LogsDO.USER_SYS);
		}
		return result;
	}

	public void setDataTrackingLogDAO(IDataTrackingLogDAO dataTrackingLogDAO) {
		this.dataTrackingLogDAO = dataTrackingLogDAO;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

}
