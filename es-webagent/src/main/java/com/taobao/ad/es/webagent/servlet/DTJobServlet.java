/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.webagent.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.ad.es.webagent.commons.WebAgentConstants;
import com.taobao.ad.es.common.job.executor.DTHttpJobExecutor;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.HttpJobExecutor;
import com.taobao.ad.easyschedule.executor.JobExecutor;

/**
 * agent 任务处理（数据监控）
 * 
 * @author baimei
 */
public class DTJobServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(DTJobServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		JobExecutor executor = new DTHttpJobExecutor(WebAgentConstants.DATASOURCE_CONTEXT_PATH);
		JobResult result = HttpJobExecutor.getInstance().execute(request, executor);
		JSONObject json = (JSONObject) JSONObject.toJSON(result);
		out.print(json.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}