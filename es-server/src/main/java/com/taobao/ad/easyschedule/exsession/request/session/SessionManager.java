/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;

import java.util.Map;

public interface SessionManager {

	/**
	 * 判断是否在配置文件中存在该KEY
	 */
	public boolean isExistKey(String key);

	/**
	 * 从给定的存储中取回属性值
	 */
	//public Object getAttribute(String key);

	/**
	 * 取得当前线程的HTTPSESSION
	 */
	public BiddingSession getHttpSession();
	
	/**
	 * 线程空间范围的session
	 */
	public void setHttpSession(BiddingSession biddingSession);

	/**
	 * 将SESSION中的值保存到配置的地方去, 最后统一调用一次
	 */
	//public void save();

	//public void invalidate();
	
	@SuppressWarnings("rawtypes")
	public Map getSessionAttrsMap() ;
	
	public SessionAttributeConfig getConfigByCookieName(String cookieName);

}