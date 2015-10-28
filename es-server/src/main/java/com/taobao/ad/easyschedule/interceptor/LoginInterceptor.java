/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dataobject.UUserDO;

public class LoginInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();// 获取session

		// 如果不存在相关session,则返回登录页面
		if (session == null || !session.containsKey(Constants.LOGNAME) || !session.containsKey(Constants.NICKNAME)) {
			return Action.LOGIN;
		}

		UUserDO user = new UUserDO();
		user.setUsername(String.valueOf(session.get(Constants.LOGNAME)));
		user.setDescn(String.valueOf(session.get(Constants.NICKNAME)));

		ThreadLocalManager.setLoginUser(user);
		return invocation.invoke();
	}

}