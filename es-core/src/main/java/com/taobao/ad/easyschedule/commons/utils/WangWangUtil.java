/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

import java.net.URLEncoder;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.commons.Constants;

/**
 * 旺旺发送工具类
 * 
 * @author baimei
 * 
 */
public class WangWangUtil {

	static final Logger logger = LoggerFactory.getLogger(WangWangUtil.class);

	/**
	 * 发送旺旺
	 * 
	 * @param list
	 *            （分号分隔）
	 * @param subject
	 * @param content
	 */
	public static void sendWangWang(String list, String subject, String content) {

		if ("false".equals(Constants.SENDWANGWANG) || StringUtils.isEmpty(list) || StringUtils.isEmpty(subject) || StringUtils.isEmpty(content)) {
			return;
		}
		try {
			String command = Constants.WANGWANG_SEND_COMMAND;
			String strSubject = subject;
			if (subject.getBytes().length > 50) {
				strSubject = StringUtil.bSubstring(subject, 49);
			}
			String strContent = content;
			if (content.getBytes().length > 900) {
				strContent = StringUtil.bSubstring(content, 899);
			}
			command = command.replaceAll("#list#", URLEncoder.encode(list, "GBK")).replaceAll("#subject#", URLEncoder.encode(strSubject, "GBK")).replaceAll(
					"#content#", URLEncoder.encode(StringUtils.isEmpty(strContent) ? "null" : strContent, "GBK"));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(Constants.NOTIFY_API_CONN_TIMEOUT);
			GetMethod getMethod = new GetMethod(command);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			for (int i = 1; i <= 3; i++) {
				try {
					int statusCode = client.executeMethod(getMethod);
					if (statusCode != HttpStatus.SC_OK) {
						logger.error("WangWangUtil.sendWangWang statusCode:" + statusCode);
						Thread.sleep(Constants.JOB_RETRY_WAITTIME);
						continue;
					}
					String ret = getMethod.getResponseBodyAsString();
					if (!"OK".equals(ret)) {
						logger.error("Send message failed[" + i + "];list:" + list + ";subject:" + subject + ";content:" + content);
						Thread.sleep(Constants.JOB_RETRY_WAITTIME);
						continue;
					}
					break;	
				} catch (Exception e) {
					logger.error("Send message failed[" + i + "]:" + e.getMessage());
					Thread.sleep(Constants.JOB_RETRY_WAITTIME);
					continue;
				}
			}
		} catch (Exception e) {
			logger.error("WangWangUtil.sendWangWang", e);
		}
	}
}
