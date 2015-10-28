/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.taobao.ad.easyschedule.commons.utils.HttpJobUtils;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.HttpJobExecutor;
import com.taobao.ad.easyschedule.executor.JobExecutor;

/**
 * 任务处理
 * 
 * @author baimei
 */
public class ScheduleJobServlet extends HttpServlet {

	private static final long serialVersionUID = 3227396753020881893L;

	private final Logger logger = LoggerFactory.getLogger(ScheduleJobServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JobData jobdata = HttpJobUtils.createJobData(request);
		JobResult result = null;
		String beanId = jobdata.getData().get(JobData.JOBDATA_DATA_BEANID);
		if (StringUtils.isEmpty(beanId)) {
			result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，无效的请求！");
		} else {
			try {
				WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
				JobExecutor executor = (JobExecutor) context.getBean(beanId);
				result = HttpJobExecutor.getInstance().execute(jobdata, executor);
			} catch (BeansException e) {
				result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "beanid参数非法！" + e.getMessage());
				logger.warn("beanid参数非法！", e);
			}
		}
		JSONObject json = (JSONObject) JSONObject.toJSON(result);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}
