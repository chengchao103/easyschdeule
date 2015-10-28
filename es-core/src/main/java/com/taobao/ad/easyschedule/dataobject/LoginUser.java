/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dataobject;

import java.util.List;

public class LoginUser {
	
	private String operName;
	private List<Long> operFeatures;
	private List<String> operRoles;
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public List<Long> getOperFeatures() {
		return operFeatures;
	}
	public void setOperFeatures(List<Long> operFeatures) {
		this.operFeatures = operFeatures;
	}
	public List<String> getOperRoles() {
		return operRoles;
	}
	public void setOperRoles(List<String> operRoles) {
		this.operRoles = operRoles;
	}
	
	
}
