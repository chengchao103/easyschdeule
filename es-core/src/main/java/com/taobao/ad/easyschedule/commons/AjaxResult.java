/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class AjaxResult {

	private Integer resultCode;
	private String message;

	private Map resultMap;

	public final static Integer OK = 1;
	public final static Integer FAILURE = 0;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public boolean isOK() {
		if (this.resultCode.equals(AjaxResult.OK)) {
			return true;
		} else {
			return false;
		}
	}

	public Map getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map resultMap) {
		this.resultMap = resultMap;
	}

	public static AjaxResult succResult() {
		AjaxResult r = new AjaxResult();
		r.setResultCode(AjaxResult.OK);
		return r;
	}

	public static AjaxResult errorResult(String message) {
		AjaxResult r = new AjaxResult();
		r.setResultCode(AjaxResult.FAILURE);
		r.setMessage(message);
		return r;
	}

	public static AjaxResult commonResult(String message) {
		AjaxResult r = new AjaxResult();
		r.setResultCode(AjaxResult.OK);
		r.setMessage(message);
		return r;
	}
}
