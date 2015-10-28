/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.ad.easyschedule.commons.Const;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.job.DataTrackingJob;
import com.taobao.ad.easyschedule.job.FileLineCountJob;
import com.taobao.ad.easyschedule.job.HttpJob;
import com.taobao.ad.easyschedule.job.ShellJob;
import com.taobao.ad.easyschedule.job.StoredProcedureJob;

/**
 * 任务工具类
 * 
 * @author baimei
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class JobUtil {

	final static Logger logger = LoggerFactory.getLogger(JobUtil.class);  
	final static Logger ignoreLogger = LoggerFactory.getLogger("IGNOREERROR");

	/**
	 * 生成任务详细信息
	 * 
	 * @param map
	 * @return
	 */
	public static String getJobData(JobDataMap map) {
		StringBuffer p = new StringBuffer("");
		Iterator<Map.Entry> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = it.next();
			String key = entry.getKey() != null ? entry.getKey().toString() : "";
			String value = entry.getValue() != null ? entry.getValue().toString() : "";
			p.append(key).append(":").append(value).append("<br>");
		}
		return p.toString();
	}

	/**
	 * 随机选择一个节点生成目标URL
	 * 
	 * @param jobId
	 * @param jobDetail
	 * @param baseUrl
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getJobTargetUrl(Long jobId, JobDetail jobDetail, String[] baseUrl) throws UnsupportedEncodingException {
		return getJobTargetUrl(jobId, jobDetail, baseUrl[Math.abs((System.nanoTime() + "").hashCode()) % baseUrl.length]);
	}

	/**
	 * 生成目标URL
	 * 
	 * @param jobId
	 * @param jobDetail
	 * @param baseUrl
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getJobTargetUrl(Long jobId, JobDetail jobDetail, String baseUrl) throws UnsupportedEncodingException {
		String jobClass = jobDetail.getJobClass().getName();
		JobDataMap data = jobDetail.getJobDataMap();
		Long currentTime = System.currentTimeMillis() / 1000;
		String token = TokenUtils.generateToken(currentTime.toString());
		StringBuffer targetUrl = new StringBuffer();
		targetUrl.append(baseUrl);
		targetUrl.append(baseUrl.contains("?") ? "&" : "?");
		targetUrl.append(Const.JOBID).append("=").append(jobId);
		targetUrl.append("&").append(Const.JOBGROUP).append("=").append(jobDetail.getGroup());
		targetUrl.append("&").append(Const.JOBNAME).append("=").append(URLEncoder.encode(jobDetail.getName(), "utf-8"));
		targetUrl.append("&").append(Const.SIGNTIME).append("=").append(currentTime.toString());
		targetUrl.append("&").append(Const.TOKEN).append("=").append(token);
		targetUrl.append("&").append(Const.CLIENTRETRIES).append("=").append(HttpJobUtils.getJobClientRetries(data.getString(Constants.JOBDATA_CLIENTRETRIES)));
		targetUrl.append("&").append(Const.SYNC).append("=").append(data.getString(Constants.JOBDATA_SYNCHRONOUS));
		targetUrl.append("&").append(Const.CALLBACKURL).append("=").append(URLEncoder.encode(Constants.CALLBACKURL, "utf-8"));
		targetUrl.append("&").append(Const.CALLBACKURLS).append("=").append(URLEncoder.encode(Constants.CALLBACKURLS, "utf-8"));
		if (HttpJob.class.getName().equals(jobClass)) {
			String parameter = data.getString(Constants.JOBDATA_PARAMETER);
			if (StringUtils.isNotBlank(parameter)) {
				parameter = URLEncoder.encode(parameter, "utf-8");
				targetUrl.append("&").append(Const.PARAMETER).append("=").append(parameter);
			}
		} else if (ShellJob.class.getName().equals(jobClass)) {
			String jobCommand = data.getString(Constants.JOBDATA_JOBCOMMAND);
			if (StringUtils.isNotBlank(jobCommand)) {
				jobCommand = URLEncoder.encode(jobCommand, "utf-8");
				targetUrl.append("&").append(Const.JOBCOMMAND).append("=").append(jobCommand);
			}
		} else if (FileLineCountJob.class.getName().equals(jobClass)) {
			String fileFullName = data.getString(Constants.JOBDATA_FILEFULLNAME);
			if (StringUtils.isNotBlank(fileFullName)) {
				fileFullName = URLEncoder.encode(fileFullName, "utf-8");
				targetUrl.append("&").append(Const.FILEFULLNAME).append("=").append(fileFullName);
			}
		} else if (DataTrackingJob.class.getName().equals(jobClass)) {
			String trackingSql = data.getString(Constants.JOBDATA_TRACKINGSQL);
			if (StringUtils.isNotBlank(trackingSql)) {
				trackingSql = URLEncoder.encode(trackingSql, "utf-8");
				targetUrl.append("&").append(Const.TRACKINGSQL).append("=").append(trackingSql);
			}
			String dataSource = data.getString(Constants.JOBDATA_DATASOURCE);
			if (StringUtils.isNotBlank(dataSource)) {
				dataSource = URLEncoder.encode(dataSource, "utf-8");
				targetUrl.append("&").append(Const.DATASOURCE).append("=").append(dataSource);
			}
			String dataSourceType = data.getString(Constants.JOBDATA_DATASOURCETYPE);
			if (StringUtils.isNotBlank(dataSourceType)) {
				dataSourceType = URLEncoder.encode(dataSourceType, "utf-8");
				targetUrl.append("&").append(Const.DATASOURCETYPE).append("=").append(dataSourceType);
			}
		} else if (StoredProcedureJob.class.getName().equals(jobClass)) {
			String parameter = data.getString(Constants.JOBDATA_PARAMETER);
			if (StringUtils.isNotBlank(parameter)) {
				parameter = URLEncoder.encode(parameter, "utf-8");
				targetUrl.append("&").append(Const.PARAMETER).append("=").append(parameter);
			}
			String storedProcedureCall = data.getString(JobData.JOBDATA_DATA_STOREDPROCEDURECALL);
			if (StringUtils.isNotBlank(storedProcedureCall)) {
				storedProcedureCall = URLEncoder.encode(storedProcedureCall, "utf-8");
				targetUrl.append("&").append(Const.STOREDPROCEDURECALL).append("=").append(storedProcedureCall);
			}
			String dataSource = data.getString(Constants.JOBDATA_DATASOURCE);
			if (StringUtils.isNotBlank(dataSource)) {
				dataSource = URLEncoder.encode(dataSource, "utf-8");
				targetUrl.append("&").append(Const.DATASOURCE).append("=").append(dataSource);
			}
			String dataSourceType = data.getString(Constants.JOBDATA_DATASOURCETYPE);
			if (StringUtils.isNotBlank(dataSourceType)) {
				dataSourceType = URLEncoder.encode(dataSourceType, "utf-8");
				targetUrl.append("&").append(Const.DATASOURCETYPE).append("=").append(dataSourceType);
			}
		}
		return targetUrl.toString();
	}

	/**
	 * 
	 * @param jobId
	 * @param jobDetail
	 * @param target
	 * @return
	 */
	public static JobResult executeRemoteJob(Long jobId, JobDetail jobDetail, String[] target) {
		JobResult result = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, "未知的错误 ");
		try {
			JobDataMap data = jobDetail.getJobDataMap();
			HttpClient client = new HttpClient();
			int retries = JobUtil.getJobRetries(data.getString(Constants.JOBDATA_RETRIES));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(Constants.JOB_MIN_CONN_TIMEOUT);
			client.getHttpConnectionManager().getParams().setSoTimeout(Constants.JOB_MAX_SO_TIMEOUT);
			GetMethod getMethod = null;
			for (int i = 1; i <= retries + 1; i++) {
				try {
					getMethod = new GetMethod(JobUtil.getJobTargetUrl(jobId, jobDetail, target));
					getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
				} catch (UnsupportedEncodingException e) {
					logger.error("ShellJob.execute,jobCommand:URL转义失败", e);
					result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "URL转义失败" + e.getMessage());
					return result;
				}
				try {
					int statusCode = client.executeMethod(getMethod);
					if (statusCode != HttpStatus.SC_OK) {
						ignoreLogger.warn(jobDetail.getFullName() + "请求远程任务在第" + i + "次失败statuscode:" + statusCode);
						/*logger.warn(jobDetail.getFullName() + "请求远程任务在第" + i + "次失败statuscode:" + statusCode);*/
						result = JobResult.errorResult(JobResult.RESULTCODE_JOB_REQUEST_FAILURE, "请求出错，返回码 " + statusCode);
						Thread.sleep(Constants.JOB_RETRY_WAITTIME);
						if (i > 1) {
							result.getData().put(JobResult.JOBRESULT_DATA_RETRYCOUNT, String.valueOf(i));
						}
						continue;
					}
					if (Constants.JOBDATA_CHECKRESULT_VAL_FALSE.equals(data.getString(Constants.JOBDATA_CHECKRESULT))) {
						result = new JobResult(true, JobResult.RESULTCODE_JOBRESULT_IGNORE, "第" + i + "次重试成功。忽略任务结果检查");
						break;
					}
					try {
						result = JSONObject.parseObject(getMethod.getResponseBodyAsString(), JobResult.class);
						if (i > 1) {
							result.getData().put(JobResult.JOBRESULT_DATA_RETRYCOUNT, String.valueOf(i));
						}
					} catch (Exception e) {
						ignoreLogger.error(jobDetail.getFullName() + "返回结果非法:" + e.getMessage());
						/*logger.error(jobDetail.getFullName() + "返回结果非法:" + e.getMessage());*/
						result = JobResult.errorResult(JobResult.RESULTCODE_JOBRESULT_ILLEGAL, "返回结果非法" + StringUtil.html(e.getMessage()));
						Thread.sleep(Constants.JOB_RETRY_WAITTIME);
						continue;
					}
					break;
				} catch (Exception e) {
					ignoreLogger.error(jobDetail.getFullName() + "请求远程任务在第" + i + "次失败" + e);
					/*logger.error(jobDetail.getFullName() + "请求远程任务在第" + i + "次失败" + e);*/
					result = JobResult.errorResult(JobResult.RESULTCODE_JOB_REQUEST_FAILURE, "请求远程任务在第" + i + "次失败" + e.getMessage());
					try {
						Thread.sleep(Constants.JOB_RETRY_WAITTIME);
					} catch (InterruptedException e1) {
					}
					continue;
				}
			}
		} catch (Exception e) {
			logger.error("Job执行出错", e);
			result.setResultMsg(result.getResultMsg() + e.getMessage());
		}
		return result;
	}

	public static int getConnectionTimeout(String jobTimeout) {
		int connTimeout = Constants.JOB_MIN_CONN_TIMEOUT;
		if (StringUtils.isNotEmpty(jobTimeout)) {
			try {
				connTimeout = Integer.parseInt(jobTimeout);
			} catch (Exception e) {
				connTimeout = Constants.JOB_MIN_CONN_TIMEOUT;
			}
		}
		if (connTimeout > Constants.JOB_MAX_CONN_TIMEOUT || connTimeout < Constants.JOB_MIN_CONN_TIMEOUT) {
			connTimeout = Constants.JOB_MIN_CONN_TIMEOUT;
		}
		return connTimeout;
	}

	public static int getJobRetries(String jobRetries) {
		int retries = Constants.JOB_MIN_RETRIES;
		if (StringUtils.isNotEmpty(jobRetries)) {
			try {
				retries = Integer.parseInt(jobRetries);
			} catch (Exception e) {
				retries = Constants.JOB_MIN_RETRIES;
			}
		}
		if (retries > Constants.JOB_MAX_RETRIES || retries < Constants.JOB_MIN_RETRIES) {
			retries = Constants.JOB_MIN_RETRIES;
		}
		return retries;
	}
}