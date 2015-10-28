/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.commons;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taobao.ad.easyschedule.exsession.request.session.SessionManager;

public class BeanLocator {

	public static final String KEY_SESSION = "session.xml";

	private static ApplicationContext context = null;

	private BeanLocator() {

	}

	private static synchronized void init() {

		if (context != null)
			return;

		context = new ClassPathXmlApplicationContext(new String[] { KEY_SESSION });
	}

	public static SessionManager getSessionManager() {
		return (SessionManager) getBean(StringUtil.getShortName(SessionManager.class));
	}

	private static Object getBean(String key) {
		if (context == null)
			init();
		return context.getBean(key);
	}

}
