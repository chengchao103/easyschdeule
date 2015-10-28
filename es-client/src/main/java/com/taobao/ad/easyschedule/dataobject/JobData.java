/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dataobject;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务参数
 * 
 * @author baimei
 */
public class JobData implements java.io.Serializable {

	private static final long serialVersionUID = -536161515895540624L;

	public static int JOBTYPE_HTTPJOB = 1;
	public static int JOBTYPE_SHELLJOB = 2;
	public static int JOBTYPE_DTJOB = 3;
	public static int JOBTYPE_STOREDPROCEDURE = 4;
	public static int JOBTYPE_LINECOUNTJOB = 5;

	public final static String JOBDATA_DATA_BEANID = "beanId";
	public final static String JOBDATA_DATA_JOBCOMMAND = "jobCommand";
	public final static String JOBDATA_DATA_FILEFULLNAME = "fileFullName";
	public final static String JOBDATA_DATA_DATASOURCE = "dataSource";
	public final static String JOBDATA_DATA_DATASOURCE_TYPE = "dataSourceType";
	public final static String JOBDATA_DATA_TRACKINGSQL = "trackingSql";
	public final static String JOBDATA_DATA_PARAMETER = "parameter";
	public final static String JOBDATA_DATA_CLIENTRETRIES = "clientRetries";
	public final static String JOBDATA_DATA_STOREDPROCEDURECALL = "storedProcedureCall";

	public final static int JOBDATA_DATA_DATASOURCE_TYPE_DYNAMIC = 1;
	public final static int JOBDATA_DATA_DATASOURCE_TYPE_JNDI = 2;
	
	/**
	 * 可选项：任务ID
	 */
	private String jobId;

	/**
	 * 必须项：任务名称
	 */
	private String jobName;

	/**
	 * 必须项：任务组
	 */
	private String jobGroup;

	/**
	 * 必须项：任务类型 <br>
	 * 1: httpJob<br>
	 * 2: shellJob<br>
	 * 3: dtJob<br>
	 * 4: storedProcedure
	 */
	private int jobType = 1;

	/**
	 * 必须项：token
	 */
	private String token;

	/**
	 * 必须项：signTime
	 */
	private String signTime;

	/**
	 * 可选项：sync
	 */
	private boolean sync = true;

	/**
	 * 可选项：回调地址
	 */
	private String callBackUrl;

	/**
	 * 可选项：回调地址(多个)
	 */
	private String callBackUrls;
	
	/**
	 * 可选项：任务附加信息
	 */
	private Map<String, String> data = new HashMap<String, String>();

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
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

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
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

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getCallBackUrls() {
		return callBackUrls;
	}

	public void setCallBackUrls(String callBackUrls) {
		this.callBackUrls = callBackUrls;
	}

}
