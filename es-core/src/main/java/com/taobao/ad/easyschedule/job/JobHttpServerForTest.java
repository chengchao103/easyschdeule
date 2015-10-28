/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.job;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.taobao.ad.easyschedule.commons.utils.HttpJobUtils;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * httpServer
 * 
 * @author baimei
 */
public class JobHttpServerForTest extends Thread {
	final Logger logger = LoggerFactory.getLogger(JobHttpServerForTest.class);

	public static String SERVER_PORT = "9876";
	public static String SERVER_CONTEXT = "/esagent";

	// 返回任务成功
	public static final String QUERYPATH_TEST_HTTPJOB_RETURNTRUE = "/doTestHttpJobReturnTrue";
	// 返回任务失败
	public static final String QUERYPATH_TEST_HTTPJOB_RETURNFALSE = "/doTestHttpJobReturnFalse";
	// 返回非JSON内容
	public static final String QUERYPATH_TEST_HTTPJOB_RETURNERROR = "/doTestHttpJobReturnError";
	// 返回DT任务成功
	public static final String QUERYPATH_TEST_DTJOB_RETURNTRUE = "/doTestDTJobReturnTrue";
	// 返回DT任务失败
	public static final String QUERYPATH_TEST_DTJOB_RETURNFALSE = "/doTestDTJobReturnFalse";

	public static JobHttpServerForTest jobHttpServer;

	private JobHttpServerForTest() {
	}

	public static void StartServer() {
		if (jobHttpServer == null) {
			synchronized (JobHttpServerForTest.class) {
				if (jobHttpServer == null) {
					jobHttpServer = new JobHttpServerForTest();
					jobHttpServer.start();
				}
			}
		}
		return;
	}

	@Override
	public void run() {
		try {
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(SERVER_PORT)), 0);
			httpServer.createContext(SERVER_CONTEXT, new HttpHandler() {
				@Override
				public void handle(final HttpExchange exchange) throws IOException {
					JobResult result = JobResult.succcessResult();
					String queryPath = exchange.getRequestURI().getPath();
					String queryStr = exchange.getRequestURI().getQuery();
					if (StringUtils.isEmpty(queryStr)) {
						InputStream in = exchange.getRequestBody();
						int count = 0;
						while (count == 0) {
							count = in.available();
						}
						byte[] b = new byte[count];
						in.read(b);
						queryStr = new String(b);
					}
					if (StringUtils.isEmpty(queryStr) || StringUtils.isEmpty(queryPath)) {
						result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "任务参数非法");
					} else {
						try {
							queryPath = queryPath.replaceAll(SERVER_CONTEXT, "");
							JobData jobData = HttpJobUtils.createJobData(queryStr);
							result = HttpJobUtils.checkJobData(jobData);
							if (result.isSuccess()) {
								if (queryPath.equals(QUERYPATH_TEST_HTTPJOB_RETURNTRUE)) {
									result = JobResult.succcessResult("任务执行成功");
								} else if (queryPath.equals(QUERYPATH_TEST_HTTPJOB_RETURNFALSE)) {
									result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "" + queryPath);
								} else if (queryPath.equals(QUERYPATH_TEST_DTJOB_RETURNTRUE)) {
									result = JobResult.succcessResult("任务执行成功");
									result.setResultMsg("30.0");
								} else if (queryPath.equals(QUERYPATH_TEST_DTJOB_RETURNFALSE)) {
									result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "" + queryPath);
								} else if (queryPath.equals(QUERYPATH_TEST_HTTPJOB_RETURNERROR)) {
									// 返回非JSON内容
									exchange.getResponseHeaders().set("Content-type", "application/json;charset=utf-8");
									exchange.sendResponseHeaders(200, 0L);
									OutputStream os = exchange.getResponseBody();
									os.write("test for error json".getBytes("utf-8"));
									os.close();
									return;
								} else {
									result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "无效的任务请求：" + queryPath);
								}
							}
						} catch (Exception e) {
							result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "任务未知异常" + e.getMessage());
							logger.info("任务失败|" + queryStr);
						}
					}
					JSONObject json = (JSONObject) JSONObject.toJSON(result);
					exchange.getResponseHeaders().set("Content-type", "application/json;charset=utf-8");
					exchange.sendResponseHeaders(200, 0L);
					OutputStream os = exchange.getResponseBody();
					os.write(json.toString().getBytes("utf-8"));
					os.close();
				}
			});
			httpServer.start();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		String[] urls = "1,2,3,4,5".split(",");
		for (int i = 0; i < 20; i++) {
			System.out.print(urls[Math.abs((System.currentTimeMillis() + "").hashCode()) % urls.length]);
		}
		System.out.println("");
		for (int i = 0; i < 20; i++) {
			System.out.print(urls[Math.abs((System.nanoTime() + "").hashCode()) % urls.length]);
		}
	}
}
