/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.taobao.ad.easyschedule.exsession.commons.StringUtil;

@SuppressWarnings({ "rawtypes", "unchecked"})
public class DefaultSessionManager implements SessionManager {
	private static final Logger log = Logger.getLogger(StringUtil.getShortName(DefaultSessionManager.class));
	// 属性值的配置文件
	private Map sessionAttrsMap = new HashMap(); // 解析成对象的配置文件
	private Map originSessionAttrsMap = new HashMap(); // 原始的配置文件
	private Map defaultConfig = new HashMap(); // 一些缺省的配置属性

	// 线程存储空间，用于保存本次解析的一系列压缩的值ֵ
	private ThreadLocal session = new ThreadLocal();

	 
	public boolean isExistKey(String key) {
		if (sessionAttrsMap.containsKey(key)) {
			return true;
		}

		return false;
	}

	public void setSessionAttrsMap(Map sessionAttrsMap) {
		this.sessionAttrsMap = sessionAttrsMap;
	}

	public void setDefaultConfig(Map defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	/**
	 * 线程空间范围的session
	 */
	public void setHttpSession(BiddingSession biddingSession) {
		this.session.set(biddingSession);
	}

	public BiddingSession getHttpSession() {
		return (BiddingSession) this.session.get();
	}

	/**
	 * 初始化上下文环境
	 */
	public void init() {
		buildAttributeConfig();
	}

	/**
	 * 解析属性的配置文件
	 * 
	 */
	private void buildAttributeConfig() {
		for (Iterator it = originSessionAttrsMap.keySet().iterator(); it
				.hasNext();) {
			String key = (String) it.next();

			Properties element = (Properties) originSessionAttrsMap.get(key);
			SessionAttributeConfig config = this.buildSessionAttributeConfig(
					key, element);

			// config.setName(key);
			sessionAttrsMap.put(key, config);
		}
	}
	
	public SessionAttributeConfig getConfigByCookieName(String cookieName) {
		for (Iterator it = sessionAttrsMap.keySet().iterator(); it
				.hasNext();) {
			String key = (String) it.next();

			SessionAttributeConfig config = (SessionAttributeConfig) sessionAttrsMap.get(key);
			if (config.getNickName().equalsIgnoreCase(cookieName))
				return config;
		}
		return null;
	}

	/**
	 * 从配置文件中解析出配置文件对象
	 * 
	 * @param ee
	 * @return
	 */
	private SessionAttributeConfig buildSessionAttributeConfig(String key,
			Properties ee) {

		log.debug("start parser config attribute: key = " + key);

		SessionAttributeConfig config = new SessionAttributeConfig();

		// 设置该配置的名字
		config.setName(key);

		// 设置COOKIE别名
		config.setNickName((String) ee.get(SessionAttributeConfig.NICK_NAME));

		// 是否需要 BASE64 编码
		String isBase64 = (String) ee.get(SessionAttributeConfig.IS_BASE64);

		if ((isBase64 != null) && isBase64.equalsIgnoreCase("true")) {
			config.setBase64(true);
		}

		// 域名 针对COOKIE
		String domain = (String) ee.get(SessionAttributeConfig.DOMAIN);
		if (domain != null) {
			config.setDomain(domain);
		} else {
			// 缺省值ֵ
			config.setDomain((String) defaultConfig
					.get(SessionAttributeConfig.DOMAIN));
		}

		// 设置path
		String path = ee.getProperty(SessionAttributeConfig.COOKIE_PATH);
		if (path != null) {
			config.setCookiePath(path);
		}

		// 值是否需要加密
		String isEntrypt = (String) ee.get(SessionAttributeConfig.IS_ENCRYPT);

		if ((isEntrypt != null) && isEntrypt.equalsIgnoreCase("true")) {
			config.setEncrypt(true);
		}

		// 生命周期
		String lifeCycle = ee.getProperty(SessionAttributeConfig.LIFE_CYCLE);
		if (lifeCycle != null) {
			config.setLifeTime(Integer.parseInt(lifeCycle));
		} else {
			// 缺省值
			String dl = (String) defaultConfig
					.get(SessionAttributeConfig.LIFE_CYCLE);
			if (dl != null) {
				config.setLifeTime(Integer.parseInt(dl));
			}
		}

		return config;
	}

	public Map getOriginSessionAttrsMap() {
		return originSessionAttrsMap;
	}

	public void setOriginSessionAttrsMap(Map originSessionAttrsMap) {
		this.originSessionAttrsMap = originSessionAttrsMap;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///// some get/set method
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Map getDefaultConfig() {
		return defaultConfig;
	}

	public Map getSessionAttrsMap() {
		return sessionAttrsMap;
	}
	
}
