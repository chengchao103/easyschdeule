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
 * 任务返回值
 * 
 * @author baimei
 */
public class JobResult implements java.io.Serializable {

	private static final long serialVersionUID = -8794685851689123785L;

	public final static boolean SUCCESS_TRUE = true;
	public final static boolean SUCCESS_FALSE = false;

	public final static int RESULTCODE_NORMAL = 0; // 正常
	public final static int RESULTCODE_JOB_REQUEST_FAILURE = 1; // 任务请求失败
	public final static int RESULTCODE_TOKENVALIDATE_FAILURE = 5; // 签名验证失败
	public final static int RESULTCODE_PARAMETER_ILLEGAL = 6; // 任务参数非法
	public final static int RESULTCODE_JOBRESULT_ILLEGAL = 7; // 任务结果非法
	public final static int RESULTCODE_JOBRESULT_IGNORE = 8; // 任务结果忽略
	public final static int RESULTCODE_JOB_EXPIRED = 10; // 任务已经过期
	public final static int RESULTCODE_OTHER_ERR = 99; // 未知错误

	
	public static String JOBRESULT_DATA_RESULTVALUE = "resultValue";
	public static String JOBRESULT_DATA_RETRYCOUNT = "retryCount";
	public static String JOBRESULT_DATA_CLIENTRETRYCOUNT = "clientRetryCount";
	
	/**
	 * 必选项：任务ID
	 */
	private String jobId;

	/**
	 * 必选项：成功标志
	 */
	private boolean success;

	/**
	 * 可选项：任务返回CODE
	 */
	private int resultCode;

	/**
	 * 可选项：任务返回信息
	 */
	private String resultMsg;

	/**
	 * 可选项：附加信息
	 */
	private Map<String, String> data = new HashMap<String, String>();
	
	public JobResult() {
	}

	public JobResult(boolean success, int resultCode, String resultMsg) {
		this.success = success;
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public static JobResult succcessResult() {
		JobResult r = new JobResult();
		r.setSuccess(JobResult.SUCCESS_TRUE);
		r.setResultCode(RESULTCODE_NORMAL);
		return r;
	}

	public static JobResult succcessResult(String resultMessage) {
		JobResult r = new JobResult();
		r.setSuccess(JobResult.SUCCESS_TRUE);
		r.setResultCode(RESULTCODE_NORMAL);
		r.setResultMsg(resultMessage);
		return r;
	}

	public static JobResult errorResult(int resultCode, String resultMsg) {
		JobResult r = new JobResult();
		r.setSuccess(JobResult.SUCCESS_FALSE);
		r.setResultCode(resultCode);
		r.setResultMsg(resultMsg);
		return r;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
