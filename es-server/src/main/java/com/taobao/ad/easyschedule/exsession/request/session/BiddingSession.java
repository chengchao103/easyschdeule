/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.taobao.ad.easyschedule.exsession.commons.BeanLocator;
import com.taobao.ad.easyschedule.exsession.commons.EncryptUtil;
import com.taobao.ad.easyschedule.exsession.commons.StringUtil;

@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class BiddingSession implements HttpSession {
	
	private static Logger log = Logger.getLogger(BiddingSession.class);
	/** 包装的SESSION */
	private String sessionId = null;
	private BiddingHttpContext biddingHttpContext;
	private HttpServletRequest request;
	private Map attrMap = new HashMap(); //session属性的内部保存
	private long createTime;
	private int maxInactiveInterval = 1800;
	private static String JSESSION_ID = "sessionID"; //sessionID的值
	private SessionManager sessionManager = null;
	private SessionCookieStore store = null;

	/**
	 * 构造函数，以便将通过系统获得容器的SESSION
	 * 
	 * @param session
	 */
	public BiddingSession(HttpServletRequest request,
			BiddingHttpContext biddingHttpContext) {
		this.biddingHttpContext = biddingHttpContext;
		createTime = System.currentTimeMillis();
		this.request = request;
		initCookies(request.getCookies());
		// 初始化 SESSIONID
		sessionId = (String) getAttribute(JSESSION_ID);

		if (StringUtil.isBlank(sessionId)) {
			sessionId = DigestUtils.md5Hex(UniqID.getInstance().getUniqID());
			// 写入COOKIE中
			setAttribute(JSESSION_ID, sessionId);

		}

	}

	private void initCookies(Cookie[] cookies) {

		if (cookies == null) {
			return;
		}
		for (int i = 0; i < cookies.length; i++) {
			String name = cookies[i].getName();
			String value = cookies[i].getValue();

			setAttributeByCookieName(name, value);
		}
	}

	private SessionManager getSessionManager() {
		if (sessionManager == null) {
			sessionManager = BeanLocator.getSessionManager();
		}
		return sessionManager;
	}

	public void saveCookie(String key, Object value) {

		SessionAttributeConfig config = (SessionAttributeConfig) getSessionManager()
				.getSessionAttrsMap().get(key);

		if (config == null) {
			return;
		}

		try {

			getStore().saveSingleKey(getBiddingHttpContext().getResponse(),
					key, value);
		} catch (Exception e) {
			log.error(e);
		}

	}

	public void removeCookie(String key) {

		SessionAttributeConfig config = (SessionAttributeConfig) getSessionManager()
				.getSessionAttrsMap().get(key);

		if (config == null) {
			return;
		}

		try {

			getStore().removeSingleKey(getBiddingHttpContext().getResponse(),
					key);
		} catch (Exception e) {
			log.error(e);
		}

	}

	public SessionCookieStore getStore() {
		if (store == null) {
			store = new SessionCookieStore();
			Map context = new HashMap();
			context.put(SessionCookieStore.CONFIG, getSessionManager()
					.getSessionAttrsMap());
			store.initContext(context);
		}
		return store;
	}

	public long getCreationTime() {
		return this.createTime;
	}

	public String getId() {
		return sessionId;
	}

	public long getLastAccessedTime() {
		return createTime;
	}

	public void setMaxInactiveInterval(int arg0) {
		maxInactiveInterval = arg0;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public Object getValue(String key) {
		return getAttribute(key);
	}

	public Enumeration getAttributeNames() {
		return new IteratorEnumeration(attrMap.keySet().iterator());
	}

	public String[] getValueNames() {
		List names = new ArrayList();

		for (Enumeration e = getAttributeNames(); e.hasMoreElements();) {
			names.add(e.nextElement());
		}

		return (String[]) names.toArray(new String[names.size()]);
	}

	/**
	 * 设置属性,直接更新原来的属性
	 */
	public void setAttribute(String key, Object object) {
		attrMap.put(key, object);
		saveCookie(key, object);

	}

	private void setAttributeByCookieName(String cookieName, String value) {
		SessionAttributeConfig config = getSessionManager()
				.getConfigByCookieName(cookieName);
		if (config == null)
			return;
		String key = config.getName();
		Object obj = parseReturnValue(config, value);

		attrMap.put(key, obj);

	}
	 /**
     * 根据配置文件，决定如何将COOKIE中的字符串或字节流解析成合适的对象
     *
     * @param value
     *
     * @return
     */
	private Object parseReturnValue(SessionAttributeConfig config, String value) {
		String retValue = "";

		if (value == null) {
			return retValue;
		}

		try {
			retValue = URLDecoder.decode(value, "UTF-8");
		} catch (IllegalArgumentException e) {
			return retValue;
		} catch (UnsupportedEncodingException e) {
			return retValue;
		}

		// 对NICK-NAME特殊处理
		if (config.getName().equals(SessionCookieStore.NICK_NAME)) {
			retValue = StringEscapeUtils.unescapeJava(retValue);
		} else {
			// 如果是加密过的
			if (config.isEncrypt()) {

				retValue = EncryptUtil.dCode(retValue);

				if (config.isBase64() && (retValue != null)
						&& (retValue.length() > 9)) {
					// 去掉BASE64时增加的头
					retValue = retValue.substring(9);
				}
			} else {
				if (config.isBase64()) {
					retValue = SessionDecode.decode(retValue);
				}
			}
		}

		return retValue;
	}

	/**
     *
     */
	public void putValue(String key, Object object) {
		setAttribute(key, object);
	}

	/**
	 * 移除属性
	 */
	public void removeAttribute(String key) {
		attrMap.remove(key);
		removeCookie(key);
	}

	public void removeValue(String key) {
		removeAttribute(key);
	}

	public void invalidate() {

		request.getSession().invalidate();
		for (Iterator iter = getSessionManager().getSessionAttrsMap()
				.entrySet().iterator(); iter.hasNext();) {
			Entry element = (Entry) iter.next();
			SessionAttributeConfig config = (SessionAttributeConfig) element
					.getValue();
			if (config.getLifeTime() <= 0) { // 说明需要处理
				removeAttribute(config.getName());
			}
		}

	}

	public boolean isNew() {
		return true;
	}

	public Object getAttribute(String key) {
		if (key == null) {
			return null;
		}

		if (attrMap.containsKey(key)) {
			return attrMap.get(key);
		}

		return null;
	}

	public HttpSessionContext getSessionContext() {
		throw new UnsupportedOperationException(
				"No longer supported method: getSessionContext");
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public ServletContext getServletContext() {
		return biddingHttpContext.getContext();
	}

	public BiddingHttpContext getBiddingHttpContext() {
		return biddingHttpContext;
	}

}
