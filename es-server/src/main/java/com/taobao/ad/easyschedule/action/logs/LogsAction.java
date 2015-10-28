/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.logs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.ad.easyschedule.security.ActionSecurity;
import com.opensymphony.xwork2.ActionContext;
import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.job.JobBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.commons.AjaxResult;
import com.taobao.ad.easyschedule.dataobject.LogsDO;

public class LogsAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Long id;

	private LogsDO query;

	private LogsDO logs;

	private ILogsBO logsBO;

	private JobBO jobBO;

	private AjaxResult result;

	private String opsubname;
	public String listLog() {
		if (query == null) {
			query = new LogsDO();
		}
		List<LogsDO> logss = logsBO.findLogss(query);
		ActionContext.getContext().put("logss", logss);
		return SUCCESS;
	}

	public String viewAddLog() {
		return SUCCESS;
	}

	public String addLog() {
		logsBO.insertLogs(logs);
		return SUCCESS;
	}

	public String viewUpdateLog() {
		logs = logsBO.getLogsById(id);
		return SUCCESS;
	}

	public String updateLog() {
		LogsDO logsDO = logsBO.getLogsById(this.logs.getId());
		if (logs != null) {
			logsDO.setId(this.logs.getId());
			logsDO.setOptype(this.logs.getOptype());
			logsDO.setOpname(this.logs.getOpname());
			logsDO.setOpsubtype(this.logs.getOpsubtype());
			logsDO.setOpsubname(this.logs.getOpsubname());
			logsDO.setOpdetail(this.logs.getOpdetail());
			logsDO.setOptime(this.logs.getOptime());
			this.logsBO.updateLogs(logsDO);
		}

		return SUCCESS;
	}
	@ActionSecurity(module = 9)
	public String deleteLog() {
		this.logsBO.deleteLogsById(id);
		return SUCCESS;
	}

	/**
	 * 获取json格式的任务组和名称
	 * 
	 * @return
	 */
	public String findAllJobGroupAndName() {

		result = AjaxResult.succResult();
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
		resultMap.put("groupNames", jobBO.findAllJobGroupAndNames());
		result.setResultMap(resultMap);
		return SUCCESS;
	}

	@ActionSecurity(module = 9)
	public String deleteAllLog() {
		this.logsBO.deleteAllLogs();
		return SUCCESS;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public LogsDO getQuery() {
		return this.query;
	}

	public void setQuery(LogsDO logs) {
		this.query = logs;
	}

	public LogsDO getLogs() {
		return this.logs;
	}

	public void setLogs(LogsDO logs) {
		this.logs = logs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJobBO(JobBO jobBO) {
		this.jobBO = jobBO;
	}

	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
	}

	public String getOpsubname() {
		return opsubname;
	}

	public void setOpsubname(String opsubname) {
		this.opsubname = opsubname;
	}

}