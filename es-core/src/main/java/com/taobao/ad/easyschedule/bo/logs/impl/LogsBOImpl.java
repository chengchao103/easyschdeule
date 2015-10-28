/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.logs.impl;

import java.util.Date;
import java.util.List;

import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.dao.logs.ILogsDAO;
import com.taobao.ad.easyschedule.dataobject.LogsDO;

public class LogsBOImpl implements ILogsBO {

	public final static Logger logger = LoggerFactory.getLogger("OPLOG");

	private ILogsDAO logsDAO;

	private Scheduler easyscheduler;

	public void insertLogs(LogsDO logs) {
		logsDAO.insertLogs(logs);
	}

	public void insertSuccess(long opsubtype, String opsubname, String opdetail, String opuser) {
		LogsDO logs = new LogsDO();
		try {
			logs.setOptype(LogsDO.TYPE_SUCCESS);
			logs.setOpsubtype(opsubtype);
			logs.setOpsubname(opsubname);
			logs.setOpdetail(opdetail);
			logs.setOpuser(opuser);
			insertLog(logs);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	public void insertFail(long opsubtype, String opsubname, String opdetail, String opuser) {
		LogsDO logs = new LogsDO();
		try {
			logs.setOptype(LogsDO.TYPE_FAILE);
			logs.setOpsubtype(opsubtype);
			logs.setOpsubname(opsubname);
			logs.setOpdetail(opdetail);
			logs.setOpuser(opuser);
			insertLog(logs);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	public void insertWarning(long opsubtype, String opsubname, String opdetail, String opuser) {
		LogsDO logs = new LogsDO();
		try {
			logs.setOptype(LogsDO.TYPE_WARNING);
			logs.setOpsubtype(opsubtype);
			logs.setOpsubname(opsubname);
			logs.setOpdetail(opdetail);
			logs.setOpuser(opuser);
			insertLog(logs);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	private void insertLog(LogsDO logs) {
		try {
			if (easyscheduler == null) {
				easyscheduler = (Scheduler) com.taobao.ad.easyschedule.job.EsApplicationContext.getBean("easyscheduler");
			}
			logs.setOpname(easyscheduler.getSchedulerInstanceId());
		} catch (Exception e) {
		}
		StringBuffer log = new StringBuffer();
		log.append(logs.getOptype()).append("|").append(logs.getOpname()).append("|");
		log.append(logs.getOpsubtype()).append("|").append(logs.getOpsubname()).append("|");
		log.append(logs.getOpdetail()).append("|").append(logs.getOpuser());
		logger.info(log.toString());
		logsDAO.insertLogs(logs);
	}

	@Override
	public LogsDO getLastStartLog(String jobGroup, String jobName) {
		return logsDAO.getLastStartLog(jobGroup, jobName);
	}

	@Override
	public Long getCountByTypeAndTime(Long opsubtype, Date startTime, Date endTime) {
		return logsDAO.getCountByTypeAndTime(opsubtype, startTime, endTime);
	}

	public LogsDO getLogsById(Long id) {
		return logsDAO.getLogsById(id);
	}

	public List<LogsDO> findLogss(LogsDO logs) {
		return logsDAO.findLogss(logs);
	}

	public void deleteLogsById(Long id) {
		logsDAO.deleteLogsById(id);
	}

	public void deleteAllLogs() {
		logsDAO.deleteAllLogs();
	}

	public void updateLogs(LogsDO logs) {
		logsDAO.updateLogs(logs);
	}

	public void setLogsDAO(ILogsDAO logsDAO) {
		this.logsDAO = logsDAO;
	}
}