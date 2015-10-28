/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.commons.AjaxResult;
import com.taobao.ad.easyschedule.commons.Const;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.SmsUtil;
import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;

public class TestAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ILogsBO logsBO;
	private JobResult httpJobResult;
	private AjaxResult ajaxResult;
	private ICodeBO codeBO;
	private List<CodeDO> jobGroups;
	private Long waitMillis;
	private String jobId;
	private String mobilelist;
	private String subject;

	/**
	 * 测试
	 * 
	 * @return
	 * @throws Exception
	 */
	public String testDoStdJob() throws Exception {
		// Runtime.getRuntime().exec("notepad.exe");
		if (waitMillis != null && waitMillis < 30000) {
			Thread.sleep(waitMillis);
		}
		int errorCode = getRandomData();
		if (errorCode == 1) {
			httpJobResult = JobResult.succcessResult();
		} else {
			httpJobResult = JobResult.errorResult(errorCode, "为了测试而产生的随机错误" + errorCode);
		}
		httpJobResult.setJobId(jobId);
		return SUCCESS;
	}

	/**
	 * 测试老短信接口
	 * 
	 * @return
	 * @throws Exception
	 */
	public String testSendSms() throws Exception {
		if (mobilelist == null || "".equals(mobilelist) || subject == null || "".equals(subject)) {
			httpJobResult = JobResult.errorResult(99, "参数不正确：缺少mobilelist，subject");
			return SUCCESS;
		}
		SmsUtil.sendSMSByShell(mobilelist, "es:" + subject);
		httpJobResult = JobResult.succcessResult("已发送");
		return SUCCESS;
	}

	/**
	 * 异步任务请求测试
	 * 
	 * @return
	 * @throws Exception
	 */
	public String testRequestAsynJob() throws Exception {
		ActionContext.getContext().put(Const.CALLBACKURL, Constants.CALLBACKURL);
		jobGroups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		return SUCCESS;
	}

	/**
	 * 异步任务回调测试
	 * 
	 * @return
	 * @throws Exception
	 */
	public String testCallbackAsynJob() throws Exception {
		ActionContext.getContext().put(Const.CALLBACKURL, Constants.CALLBACKURL);
		jobGroups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		return SUCCESS;
	}

	/**
	 * 数据监控请求测试
	 * 
	 * @return
	 * @throws Exception
	 */
	public String testDataTrackingJob() throws Exception {
		ActionContext.getContext().put(Const.CALLBACKURL, Constants.CALLBACKURL);
		jobGroups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		return SUCCESS;
	}


	/**
	 * 获取TOKEN
	 * 
	 * @return
	 * @throws Exception
	 */
	public String generateToken() throws Exception {
		Long signTime = System.currentTimeMillis() / 1000;
		Map<String, String> result = new HashMap<String, String>();
		result.put("signTime", signTime.toString());
		result.put("token", TokenUtils.generateToken(signTime.toString()));
		ajaxResult = AjaxResult.succResult();
		ajaxResult.setResultMap(result);
		return SUCCESS;
	}

	public int getRandomData() {
		double a = Math.random() * 5;
		a = Math.ceil(a);
		return new Double(a).intValue();
	}

	public ILogsBO getLogsBO() {
		return logsBO;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public JobResult getHttpJobResult() {
		return httpJobResult;
	}

	public void setHttpJobResult(JobResult httpJobResult) {
		this.httpJobResult = httpJobResult;
	}

	public ICodeBO getCodeBO() {
		return codeBO;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public List<CodeDO> getJobGroups() {
		return jobGroups;
	}

	public void setJobGroups(List<CodeDO> jobGroups) {
		this.jobGroups = jobGroups;
	}

	public AjaxResult getAjaxResult() {
		return ajaxResult;
	}

	public void setAjaxResult(AjaxResult ajaxResult) {
		this.ajaxResult = ajaxResult;
	}

	public Long getWaitMillis() {
		return waitMillis;
	}

	public void setWaitMillis(Long waitMillis) {
		this.waitMillis = waitMillis;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getMobilelist() {
		return mobilelist;
	}

	public void setMobilelist(String mobilelist) {
		this.mobilelist = mobilelist;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
