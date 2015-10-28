/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.commons.Const;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * http任务处理工具类
 * 
 * @author baimei
 * 
 */
public class HttpJobUtils {

	final static Logger logger = LoggerFactory.getLogger(HttpJobUtils.class);
	
	/**
	 * 根据request封装任务参数
	 * 
	 * @param request
	 * @return
	 */
	public static JobData createJobData(final HttpServletRequest request) {
		JobData jobData = new JobData();
		Map<String, String> data = new HashMap<String, String>();
		jobData.setJobId(request.getParameter(Const.JOBID));
		jobData.setJobGroup(request.getParameter(Const.JOBGROUP));
		jobData.setJobName(request.getParameter(Const.JOBNAME));
		jobData.setJobType(request.getParameter(Const.JOBTYPE) != null ? Integer.parseInt(request.getParameter(Const.JOBTYPE)) : 1);
		data.put(JobData.JOBDATA_DATA_CLIENTRETRIES, request.getParameter(Const.CLIENTRETRIES));
		data.put(JobData.JOBDATA_DATA_BEANID, request.getParameter(Const.BEANID));
		data.put(JobData.JOBDATA_DATA_JOBCOMMAND, request.getParameter(Const.JOBCOMMAND));
		data.put(JobData.JOBDATA_DATA_TRACKINGSQL, request.getParameter(Const.TRACKINGSQL));
		data.put(JobData.JOBDATA_DATA_DATASOURCE, request.getParameter(Const.DATASOURCE));
		data.put(JobData.JOBDATA_DATA_DATASOURCE_TYPE, request.getParameter(Const.DATASOURCETYPE));
		data.put(JobData.JOBDATA_DATA_PARAMETER, request.getParameter(Const.PARAMETER));
		data.put(JobData.JOBDATA_DATA_STOREDPROCEDURECALL, request.getParameter(Const.STOREDPROCEDURECALL));
		data.put(JobData.JOBDATA_DATA_FILEFULLNAME, request.getParameter(Const.FILEFULLNAME));
		jobData.setData(data);
		jobData.setSignTime(request.getParameter(Const.SIGNTIME));
		jobData.setToken(request.getParameter(Const.TOKEN));
		jobData.setSync(!"false".equals(request.getParameter(Const.SYNC)));
		jobData.setCallBackUrl(request.getParameter(Const.CALLBACKURL));
		jobData.setCallBackUrls(request.getParameter(Const.CALLBACKURLS));
		buildParameter(data);
		return jobData;
	}

	/**
	 * 根据queryStr封装任务参数
	 * 
	 * @param request
	 * @return
	 */
	public static JobData createJobData(String queryStr) {
		Map<String, String> param = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(queryStr, "&");
		while (st.hasMoreTokens()) {
			String pair = st.nextToken();
			int pos = pair.indexOf('=');
			if (pos != -1) {
				String key = parseName(pair.substring(0, pos), sb);
				String val = parseName(pair.substring(pos + 1, pair.length()), sb);
				try {
					param.put(key, java.net.URLDecoder.decode(val, "utf-8"));
				} catch (UnsupportedEncodingException e) {
					param.put(key, val);
				}
			}
		}
		JobData jobData = new JobData();
		jobData.setJobId(param.get(Const.JOBID));
		jobData.setJobName(param.get(Const.JOBNAME));
		jobData.setJobGroup(param.get(Const.JOBGROUP));
		jobData.setJobType(param.get(Const.JOBTYPE) != null ? Integer.parseInt(param.get(Const.JOBTYPE)) : 1);
		jobData.setToken(param.get(Const.TOKEN));
		jobData.setSignTime(param.get(Const.SIGNTIME));
		jobData.setSync(!"false".equals(param.get(Const.SYNC)));
		jobData.setCallBackUrl(param.get(Const.CALLBACKURL));
		jobData.setCallBackUrls(param.get(Const.CALLBACKURLS));
		Map<String, String> data = new HashMap<String, String>();
		data.put(JobData.JOBDATA_DATA_CLIENTRETRIES, param.get(Const.CLIENTRETRIES));
		data.put(JobData.JOBDATA_DATA_BEANID, param.get(Const.BEANID));
		data.put(JobData.JOBDATA_DATA_JOBCOMMAND, param.get(Const.JOBCOMMAND));
		data.put(JobData.JOBDATA_DATA_TRACKINGSQL, param.get(Const.TRACKINGSQL));
		data.put(JobData.JOBDATA_DATA_DATASOURCE, param.get(Const.DATASOURCE));
		data.put(JobData.JOBDATA_DATA_DATASOURCE_TYPE, param.get(Const.DATASOURCETYPE));
		data.put(JobData.JOBDATA_DATA_PARAMETER, param.get(Const.PARAMETER));
		data.put(JobData.JOBDATA_DATA_STOREDPROCEDURECALL, param.get(Const.STOREDPROCEDURECALL));
		data.put(JobData.JOBDATA_DATA_FILEFULLNAME, param.get(Const.FILEFULLNAME));
		jobData.setData(data);
		buildParameter(data);
		return jobData;
	}

	/**
	 * 检查任务参数合法性
	 * 
	 * @param jobData
	 * @return
	 */
	public static JobResult checkJobData(final JobData jobData) {
		if (StringUtils.isEmpty(Const.TOKEN) || StringUtils.isEmpty(Const.SIGNTIME) || !TokenUtils.validateToken(jobData.getToken(), jobData.getSignTime())) {
			return JobResult.errorResult(JobResult.RESULTCODE_TOKENVALIDATE_FAILURE, "签名验证失败！");
		}
		if (StringUtils.isEmpty(jobData.getJobName()) || StringUtils.isEmpty(jobData.getJobGroup())) {
			return JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，无效的请求！");
		}
		if (!jobData.isSync() && StringUtils.isEmpty(jobData.getJobId())) {
			return JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，异步任务没有任务ID！");
		}
		if (!jobData.isSync() && StringUtils.isEmpty(jobData.getCallBackUrl()) && StringUtils.isEmpty(jobData.getCallBackUrls())) {
			return JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，异步任务没有回调地址！");
		}
		if (jobData.getJobType() == JobData.JOBTYPE_SHELLJOB && jobData.getData() != null && StringUtils.isEmpty(jobData.getData().get(Const.JOBCOMMAND))) {
			return JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，Shell任务缺少必须参数！");
		}
		if (jobData.getJobType() == JobData.JOBTYPE_STOREDPROCEDURE
				&& jobData.getData() != null
				&& (StringUtils.isEmpty(jobData.getData().get(Const.PARAMETER)) || StringUtils.isEmpty(jobData.getData().get(Const.STOREDPROCEDURECALL))
						&& StringUtils.isEmpty(jobData.getData().get(Const.DATASOURCE)) || StringUtils.isEmpty(jobData.getData().get(Const.DATASOURCETYPE)))) {
			return JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，StoredProcedure任务缺少必须参数！");
		}
		if (jobData.getJobType() == JobData.JOBTYPE_LINECOUNTJOB && jobData.getData() != null && StringUtils.isEmpty(jobData.getData().get(Const.FILEFULLNAME))) {
			return JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，文件行数统计任务缺少必须参数！");
		}
		if (jobData.getJobType() == JobData.JOBTYPE_DTJOB
				&& jobData.getData() != null
				&& (StringUtils.isEmpty(jobData.getData().get(Const.TRACKINGSQL)) || StringUtils.isEmpty(jobData.getData().get(Const.DATASOURCE)) || StringUtils
						.isEmpty(jobData.getData().get(Const.DATASOURCETYPE)))) {
			return JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "参数非法，DT任务缺少必须参数！");
		}
		return JobResult.succcessResult();
	}

	/**
	 * 向es回调异步任务处理结果
	 * 
	 * @param jobData
	 * @param result
	 * @throws IOException
	 */
	public static void callBackHttpJob(final JobData jobData, final JobResult result) throws IOException {
		HttpURLConnection httpURLConnection = null;
		BufferedReader in = null;
		try {
			StringBuilder parameter = new StringBuilder("?");
			parameter.append("jobId=").append(jobData.getJobId());
			parameter.append("&jobName=").append(jobData.getJobName());
			parameter.append("&jobGroup=").append(jobData.getJobGroup());
			parameter.append("&success=").append(result.isSuccess());
			parameter.append("&resultCode=").append(result.getResultCode());
			if (result.getResultMsg() != null) {
				parameter.append("&resultMsg=").append(URLEncoder.encode(result.getResultMsg(), "utf-8"));
			}

			String[] baseUrls = null;
			if (!StringUtils.isEmpty(jobData.getCallBackUrls())) {
				baseUrls = jobData.getCallBackUrls().split(",");
			} else {
				baseUrls = new String[] { jobData.getCallBackUrl() };
			}
			for (int i = 1; i <= 8; i++) {
				try {
					URL url = new URL(baseUrls[Math.abs((System.nanoTime() + "").hashCode()) % baseUrls.length] + parameter);
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.setDoOutput(true);
					httpURLConnection.setDoInput(true);
					httpURLConnection.setRequestMethod("GET");
					httpURLConnection.connect();
					in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
					in.readLine();
					break;
				} catch (IOException e) {
					logger.error("异步任务回调请求发送在第" + i + "次尝试失败", e);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
			}
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
			try {
				if (httpURLConnection != null) {
					httpURLConnection.disconnect();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 分解parameter参数
	 * 
	 * @param data
	 */
	public static void buildParameter(Map<String, String> data) {
		String parameter = data.get(JobData.JOBDATA_DATA_PARAMETER);
		if (StringUtils.isEmpty(parameter)) {
			return;
		}
		try {
			parameter = URLDecoder.decode(parameter, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		for (String val : parameter.split("&")) {
			String[] a = val.split("=");
			if (a.length == 2) {
				data.put(a[0], a[1]);
			}
		}
	}

	private static String parseName(String s, StringBuilder sb) {
		sb.setLength(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(s.substring(i + 1, i + 3), 16));
					i += 2;
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				} catch (StringIndexOutOfBoundsException e) {
					String rest = s.substring(i);
					sb.append(rest);
					if (rest.length() == 2)
						i++;
				}
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public static int getJobClientRetries(String clientRetries) {
		int retries = Const.JOB_CLIENT_MIN_RETRIES;
		if (!StringUtils.isEmpty(clientRetries)) {
			try {
				retries = Integer.parseInt(clientRetries);
			} catch (Exception e) {
				retries = Const.JOB_CLIENT_MIN_RETRIES;
			}
		}
		if (retries > Const.JOB_CLIENT_MAX_RETRIES) {
			retries = Const.JOB_CLIENT_MAX_RETRIES;
		} else if (retries < Const.JOB_CLIENT_MIN_RETRIES) {
			retries = Const.JOB_CLIENT_MIN_RETRIES;
		}
		return retries;
	}
}
