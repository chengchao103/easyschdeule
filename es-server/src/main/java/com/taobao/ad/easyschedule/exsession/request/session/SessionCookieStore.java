/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;

import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.taobao.ad.easyschedule.exsession.commons.EncryptUtil;
import com.taobao.ad.easyschedule.exsession.commons.StringUtil;
@SuppressWarnings("rawtypes")
public class SessionCookieStore {
	private static final Logger log = Logger.getLogger(StringUtil
			.getShortName(SessionCookieStore.class));
	public static String CONFIG = "config";
	public static String COOKIE     = "cookies";
	public static String MEMORY     = "memory";
	public static String DATABASE   = "database";
	public static String TBSTORE    = "tbstore";
	public static String SAVE_STORE = "SAVE_STORE";
	public static String SESSION    = "session";
	public static String NICK_NAME  = "_nk_";
	private static final String COOKIE_PATH = "/";
	public static final Random random = new SecureRandom();
	
	private Map sessionAttrMap;

	/**
     * 取得环境上下文
     */
	public void initContext(Map context) {
		this.sessionAttrMap = (Map) context.get(CONFIG);
	}

	 /**
     * 保存单个的值到COOKIE中去
     *
     * @param response
     * @param key
     *
     * @throws Exception
     */
	public void saveSingleKey(HttpServletResponse response, String key,
			Object value) throws Exception {

		SessionAttributeConfig config = (SessionAttributeConfig) sessionAttrMap
				.get(key);

		this.saveCookie(response, config, value);

	}
	public void removeSingleKey(HttpServletResponse response, String key) throws Exception {

		SessionAttributeConfig config = (SessionAttributeConfig) sessionAttrMap
				.get(key);

		this.removeCookie(response, config);

	}

	/**
	 * @param response
	 * @param config
	 * @param value
	 * 
	 * @throws Exception
	 */
	private void saveCookie(HttpServletResponse response,
			SessionAttributeConfig config, Object value) throws Exception {
		String cookieName = config.getNickName();
		int lifeTime = config.getLifeTime();

		 //得到COOKIE的值
		String attrValue = getEncodedValue(config,value);
		Cookie cookie = null;

		if (attrValue != null) {
			// if (config.isEncrypt()) {
			attrValue = URLEncoder.encode(attrValue, "UTF-8");
			// }

			cookie = new Cookie(cookieName, attrValue);
		} else {
			cookie = new Cookie(cookieName, "");
		}

		log
				.debug("cookie name: " + cookieName + "  cookie value: "
						+ attrValue);
		
		//设置一些COOKIE的其它相关属性
		String cookiePath = COOKIE_PATH;

		if (config.getCookiePath() != null) {
			cookiePath = config.getCookiePath();
		}

		cookie.setPath(cookiePath);

		if (lifeTime > 0) {
			cookie.setMaxAge(lifeTime);
		}

		String domain = config.getDomain();

		if ((domain != null) && (domain.length() > 0)) {
			cookie.setDomain(domain);
		}

		response.addCookie(cookie);
	}
	/**
	 * @param response
	 * @param config
	 * @param value
	 * 
	 * @throws Exception
	 */
	private void removeCookie(HttpServletResponse response, SessionAttributeConfig config) throws Exception {
		String cookieName = config.getNickName();

		Cookie cookie = new Cookie(cookieName, null);
		;
		// 设置一些COOKIE的其它相关属性
		String cookiePath = COOKIE_PATH;

		if (config.getCookiePath() != null) {
			cookiePath = config.getCookiePath();
		}

		cookie.setPath(cookiePath);

		log.debug("remove cookie name: " + cookieName);

		cookie.setMaxAge(0);
		String domain = config.getDomain();

		if ((domain != null) && (domain.length() > 0)) {
			cookie.setDomain(domain);
		}

		response.addCookie(cookie);
	}

	/**
     * 根据原始值得到应该保存到COOKIE中的值
     *
     * @param config
     * @param encrypter
     *
     * @return
     *
     * @throws Exception
     */
	protected String getEncodedValue(SessionAttributeConfig config,Object obj)
			throws Exception {

		String attrValue = null;
	
		if (obj == null) {
			return null;
		}

		if (obj instanceof String) {
			attrValue = (String) obj;
		} else {
			attrValue = obj.toString();
		}

		String attrName = config.getNickName();

		if (StringUtil.isBlank(attrValue)) {
			return attrValue;
		}

		if (attrName.equals("nick_name")) {
			attrValue = StringEscapeUtils.escapeJava(attrValue);
		} else {
			if (config.isEncrypt()) {
				// add encryption here
				if (config.isBase64()) {
					attrValue = random.nextInt(10) + "`9d%7*()" + attrValue;
				}

				attrValue = EncryptUtil.eCode(attrValue);
			} else {
				if (config.isBase64()) {
					attrValue = SessionEncode.encode(attrValue);
				}
			}
		}

		return attrValue;
	}

	
}
