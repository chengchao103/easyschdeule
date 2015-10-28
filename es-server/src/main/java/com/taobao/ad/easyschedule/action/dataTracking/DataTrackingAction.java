/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.dataTracking;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.datatracking.IDataTrackingJobBO;
import com.taobao.ad.easyschedule.bo.datatrackinglog.IDataTrackingLogBO;
import com.taobao.ad.easyschedule.commons.utils.DateTimeUtil;
import com.taobao.ad.easyschedule.dataobject.DatatrackingLogDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * datatracking的数据获取
 * 
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-8-19 下午03:28:47
 */
public class DataTrackingAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private IDataTrackingJobBO dataTrackingJobBO;
	private IDataTrackingLogBO dataTrackingLogBO;
	private DatatrackingLogDO query;
	private String displayValue;
	private String trackingSql;
	private String dataSource;
	private JobResult httpJobResult;
	private String token;
	private String signTime;
	private String callback;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTrackingDisplayValue() throws Exception {

		initQueryTime();

		return SUCCESS;
	}

	/**
	 * 初始化查询时间
	 */
	private void initQueryTime() {
		if (query == null) {
			query = new DatatrackingLogDO();
		}
		if (query.getQueryStartTime() == null) {
			query.setQueryStartTime(DateTimeUtil.getDateBeforeOrAfterV2(-7));
		}
		if (query.getQueryEndTime() == null) {
			query.setQueryEndTime(DateTimeUtil.getDateBeforeOrAfterV2(0));
		}
	}

	/**
	 * 获取chart图表的JSON值
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getTrackingDisplayJsonValue() throws IOException, Exception {

		initQueryTime();
		query.setQueryEndTime(new Date(query.getQueryEndTime().getTime() + 86399000));

		HttpServletResponse response = ServletActionContext.getResponse();
		StringBuffer value = new StringBuffer();
		List<DatatrackingLogDO> displayValueList = dataTrackingLogBO.getDataTrackingByGroupAndName(query);
		value.append(org.apache.commons.lang.StringEscapeUtils.escapeHtml(callback) + "([");
		for (int i = 0; i < displayValueList.size(); i++) {
			DatatrackingLogDO datatrackingLog = displayValueList.get(i);
			value.append("[").append(datatrackingLog.getCreateTime().getTime()).append(",").append(datatrackingLog.getTrackingValue()).append("]");
			if (i < displayValueList.size() - 1) {
				value.append(",");
			}
		}
		value.append("]);");
		response.getWriter().write(value.toString());
	}

	public IDataTrackingLogBO getDataTrackingLogBO() {
		return dataTrackingLogBO;
	}

	public void setDataTrackingLogBO(IDataTrackingLogBO dataTrackingLogBO) {
		this.dataTrackingLogBO = dataTrackingLogBO;
	}

	public DatatrackingLogDO getQuery() {
		return query;
	}

	public void setQuery(DatatrackingLogDO query) {
		this.query = query;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public IDataTrackingJobBO getDataTrackingJobBO() {
		return dataTrackingJobBO;
	}

	public void setDataTrackingJobBO(IDataTrackingJobBO dataTrackingJobBO) {
		this.dataTrackingJobBO = dataTrackingJobBO;
	}

	public String getTrackingSql() {
		return trackingSql;
	}

	public void setTrackingSql(String trackingSql) {
		this.trackingSql = trackingSql;
	}

	public JobResult getHttpJobResult() {
		return httpJobResult;
	}

	public void setHttpJobResult(JobResult httpJobResult) {
		this.httpJobResult = httpJobResult;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

}
