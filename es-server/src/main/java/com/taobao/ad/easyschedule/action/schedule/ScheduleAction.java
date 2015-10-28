/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.schedule;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;
import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.config.IConfigBO;
import com.taobao.ad.easyschedule.commons.Const;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.commons.web.SchedulerDTO;
import com.taobao.ad.easyschedule.dao.scheduler.ISchedulerDAO;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;
import com.taobao.ad.easyschedule.dataobject.SchedulerDO;
import com.taobao.ad.easyschedule.security.ActionSecurity;

/**
 * 集群实例管理Action
 * 
 * @author baimei
 * 
 */
public class ScheduleAction extends BaseAction {

	private static final long serialVersionUID = 1433908530815520652L;

	private List<SchedulerDTO> schedulerList;
	private IConfigBO configBO;
	private ISchedulerDAO schedulerDAO;
	private String instance;

	/**
	 * 获取所有实例
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listInstance() {
		try {
			schedulerList = new ArrayList<SchedulerDTO>();
			List<SchedulerDO> list = schedulerDAO.findSchedulers(new SchedulerDO());
			for (SchedulerDO schedulerDO : list) {
				String instanceName = schedulerDO.getInstanceName();
				if (StringUtils.isEmpty(instanceName)) {
					continue;
				}
				SchedulerDTO s = getSchedulerInfo(getInstanceName(instanceName));
				if (s != null) {
					schedulerList.add(s);
				} else {
					SchedulerDTO ss = new SchedulerDTO();
					ss.setSchedulerInstanceId(instanceName);
					schedulerList.add(ss);
				}
			}
		} catch (Exception e) {
			logger.error("操作失败:" + e.getMessage());
		}
		return SUCCESS;
	}

	@ActionSecurity(module = 25)
	public String startInstance() {
		controlRemoteInstance("startInstanceAjax");
		listInstance();
		return SUCCESS;
	}

	@ActionSecurity(module = 26)
	public String stopInstance() {
		controlRemoteInstance("stopInstanceAjax");
		listInstance();
		return SUCCESS;
	}

	@ActionSecurity(module = 27)
	public String pauseInstance() {
		controlRemoteInstance("pauseInstanceAjax");
		listInstance();
		return SUCCESS;
	}

	@ActionSecurity(module = 28)
	public String waitAndStopSchedulerInstance() {
		controlRemoteInstance("waitAndStopSchedulerInstanceAjax");
		listInstance();
		return SUCCESS;
	}

	private void controlRemoteInstance(String action) {
		try {
			if (!checkInstance(instance)) {
				return;
			}
			Long currentTime = System.currentTimeMillis() / 1000;
			String token = TokenUtils.generateToken(currentTime.toString());
			HttpClient client = new HttpClient();
			int connTimeout = 1000;
			client.getHttpConnectionManager().getParams().setConnectionTimeout(connTimeout);
			GetMethod getMethod = null;
			String controlInterface = configBO.getStringValue(ConfigDO.CONFIG_KEY_INSTANCE_CONTROL_INTERFACE);
			controlInterface = controlInterface.replaceAll("#instance#", getInstanceName(instance));
			StringBuilder url = new StringBuilder(controlInterface);
			url = url.append(action);
			url = url.append("?").append(Const.TOKEN).append("=").append(token);
			url = url.append("&").append(Const.SIGNTIME).append("=").append(currentTime.toString());
			url = url.append("&").append("userName").append("=").append(getLogname());
			getMethod = new GetMethod(url.toString());
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			client.executeMethod(getMethod);
		} catch (Exception e) {
		}
	}

	private boolean checkInstance(String instanceName) {
		SchedulerDO s = schedulerDAO.getScheduler(instanceName);
		return s != null && instanceName.equals(s.getInstanceName());
	}

	private String getInstanceName(String instance) {
		if (StringUtils.isEmpty(instance)) {
			return null;
		}
		return instance.substring(0, instance.length() - String.valueOf(System.currentTimeMillis()).length());
	}

	private SchedulerDTO getSchedulerInfo(String instanceName) {
		SchedulerDTO result = null;
		try {
			HttpClient client = new HttpClient();
			int connTimeout = 1000;
			client.getHttpConnectionManager().getParams().setConnectionTimeout(connTimeout);
			GetMethod getMethod = null;
			String url = configBO.getStringValue(ConfigDO.CONFIG_KEY_INSTANCE_CONTROL_INTERFACE);
			url = url.replaceAll("#instance#", instanceName);
			url = url + "getInstanceAjax";
			getMethod = new GetMethod(url);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = JSONObject.parseObject(getMethod.getResponseBodyAsString(), SchedulerDTO.class);
			}
		} catch (Exception e) {
		}
		return result;
	}

	public IConfigBO getConfigBO() {
		return configBO;
	}

	public void setConfigBO(IConfigBO configBO) {
		this.configBO = configBO;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public void setSchedulerDAO(ISchedulerDAO schedulerDAO) {
		this.schedulerDAO = schedulerDAO;
	}

	public List<SchedulerDTO> getSchedulerList() {
		return schedulerList;
	}

	public void setSchedulerList(List<SchedulerDTO> schedulerList) {
		this.schedulerList = schedulerList;
	}

}
