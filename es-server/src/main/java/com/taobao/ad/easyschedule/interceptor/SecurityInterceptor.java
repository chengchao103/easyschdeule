/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.aop.support.AopUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.taobao.ad.easyschedule.bo.uresc.IURescBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.security.ActionSecurityBean;

/**
 * 检查用户操作权限的拦截器
 * 
 */
public class SecurityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private ActionSecurityBean securityBean;
	private IURescBO rescBO;

	@Override
	public String intercept(ActionInvocation invocation) throws AuthException, Exception {
		if ("dev".equals(Constants.DEPLOY_MODE)) {
			return invocation.invoke();
		}
	 
		if (!checkAuth(invocation, rescBO.findAllUserRescs(ThreadLocalManager.getLoginUser().getUsername()))) {
			return "authError";
		}
		return invocation.invoke();
	}

	@SuppressWarnings("unchecked")
	private Boolean checkAuth(ActionInvocation invocation, List<Integer> featureList) throws AuthException, Exception {
		Class action = AopUtils.getTargetClass(invocation.getAction());
		Method targetMethod = action.getMethod(invocation.getProxy().getMethod(), null);
		if (!securityBean.hasAccess(featureList, targetMethod)) {
			return false;
		}
		return true;
	}

	public void setSecurityBean(ActionSecurityBean securityBean) {
		this.securityBean = securityBean;
	}

	public void setRescBO(IURescBO rescBO) {
		this.rescBO = rescBO;
	}
}
