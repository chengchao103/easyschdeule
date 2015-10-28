/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.taobao.ad.easyschedule.commons.Const;
import com.taobao.ad.easyschedule.commons.utils.HttpJobUtils;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.HttpJobExecutor;
import com.taobao.ad.easyschedule.executor.JobExecutor;

/**
 * httpagent 任务处理
 * 
 * @author bolin.hbc
 * @author baimei
 */
public class JobHttpServer extends Thread {
	final Logger logger = LoggerFactory.getLogger(JobHttpServer.class);

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		System.out.println("DATASOURCE_CONTEXT_PATH:" + Const.DATASOURCE_CONTEXT_PATH);
		System.out.println("SERVER_CONTEXT:" + Const.SERVER_CONTEXT);
		System.out.println("SERVER_PORT:" + Const.SERVER_PORT);
		JobHttpServer server = new JobHttpServer();
		server.start();
		System.out.println("es-httpserver-agent startup in " + (System.currentTimeMillis() - start) + "ms.");
	}

	@Override
	public void run() {
		try {
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(Const.SERVER_PORT)), 0);
			httpServer.createContext(Const.SERVER_CONTEXT, new HttpHandler() {
				@Override
				public void handle(final HttpExchange exchange) throws IOException {
					JobResult result = JobResult.succcessResult();
					String queryPath = exchange.getRequestURI().getPath();
					String queryStr = exchange.getRequestURI().getQuery();
					if (StringUtils.isEmpty(queryStr) || StringUtils.isEmpty(queryPath)) {
						result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "任务参数非法");
					} else {
						try {
							queryPath = queryPath.replaceAll(Const.SERVER_CONTEXT, "");
							JobData jobData = HttpJobUtils.createJobData(queryStr);
							String beanId = jobData.getData().get(JobData.JOBDATA_DATA_BEANID);
							if (StringUtils.isEmpty(beanId)) {
								result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，无效的请求！");
							} else {
								if (Const.DEPLOY_MODE.equals("dev")) {
									Long signTime = System.currentTimeMillis() / 1000;
									String token = TokenUtils.generateToken(signTime.toString());
									jobData.setSignTime(signTime.toString());
									jobData.setToken(token);
								}
								result = HttpJobUtils.checkJobData(jobData);
								if (result.isSuccess()) {
									if (queryPath.equals(Const.QUERYPATH_DOSTDJOB)) {
										WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
										JobExecutor executor = (JobExecutor) context.getBean(beanId);
										result = HttpJobExecutor.getInstance().execute(jobData, executor);
									} else {
										result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "无效的任务请求：" + queryPath);
									}
								}
							}
						} catch (Exception e) {
							result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "任务未知异常");
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
			logger.error("JobHttpServer.run is fail:" + e.getMessage(), e);
		}
	}
}
