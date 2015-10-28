/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

import java.io.IOException;
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
 * 短信发送工具类
 * 
 * @author baimei
 * 
 */
public class SmsUtil {

	static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

	/**
	 * 发送短信
	 * 
	 * @param list
	 *            （分号分隔）
	 * @param subject
	 * @param content
	 */
	public static void sendSMS(String list, String subject, String content) {
		if ("false".equals(Constants.SENDSMS) || StringUtils.isEmpty(list) || StringUtils.isEmpty(subject)) {
			return;
		}
		try {
			String command = Constants.SMS_SEND_COMMAND;
			String strSubject = subject;
			if (subject.getBytes().length > 50) {
				strSubject = StringUtil.bSubstring(subject, 49);
			}
			String strContent = subject;
			if (subject.getBytes().length > 150) {
				// 本接口只会发送content信息，所以将subject作为content发送
				strContent = StringUtil.bSubstring(subject + ":" + content, 149);
			}
			command = command.replaceAll("#list#", URLEncoder.encode(list, "GBK")).replaceAll("#subject#", URLEncoder.encode(strSubject, "GBK"))
					.replaceAll("#content#", URLEncoder.encode(strContent, "GBK"));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(Constants.NOTIFY_API_CONN_TIMEOUT);
			GetMethod getMethod = new GetMethod(command);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			for (int i = 1; i <= 3; i++) {
				try {
					int statusCode = client.executeMethod(getMethod);
					if (statusCode != HttpStatus.SC_OK) {
						logger.error("SmsUtil.sendWangWang statusCode:" + statusCode);
						sendSMSByShell(list, "es:" + subject);
						Thread.sleep(Constants.JOB_RETRY_WAITTIME);
						continue;
					}
					String ret = getMethod.getResponseBodyAsString();
					if (!"OK".equals(ret)) {
						logger.error("Send message failed[" + i + "];list:" + list + ";subject:" + subject + ";content:" + content);
						sendSMSByShell(list, "es:" + subject);
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
			logger.error("SmsUtil.sendSMS", e);
		}
	}

	/**
	 * 老接口发送短信
	 * 
	 * @param list
	 * @param subject
	 */
	public static void sendSMSByShell(String list, String subject) {
		try {
			Runtime.getRuntime().exec("/home/a/project/sendSMS.sh " + list + " " + subject);
		} catch (IOException e) {
			logger.error("Send SMS failed;list:" + list + ";subject:" + subject);
		}
	}

	public static void main(String[] args) {
		Constants.SENDSMS = "true";
		sendSMS("13666699203", "real:160shelltask_pushIncrementTaoCode_failed_10《淘宝》", "a");
	}
}
