/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.config.IConfigBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;
import com.taobao.ad.easyschedule.dataobject.LogsDO;
import com.taobao.ad.easyschedule.security.ActionSecurity;

public class ConfigAction extends BaseAction {

	final Logger logger = LoggerFactory.getLogger(ConfigAction.class);
	private static final long serialVersionUID = -1053243392919646107L;
	private String id;
	private ConfigDO query;
	private ConfigDO config;
	private IConfigBO configBO;
	private String action;
	private String error;
	private String sendMail;
	private String mailSendCommand;
	private String sendSms;
	private String smsSendCommand;
	private String sendWangWang;
	private String wangwangSendCommand;
	private ILogsBO logsBO;

	public String listConfig() {
		if (query == null) {
			query = new ConfigDO();
		}
		List<ConfigDO> configs = configBO.findConfigs(query);
		ActionContext.getContext().put("configs", configs);
		return SUCCESS;
	}

	public String viewAddConfig() {
		action = "add";
		return SUCCESS;
	}

	@ActionSecurity(module = 14)
	public String addConfig() {
		error = Constants.ERROR_STATUS.get(configBO.insertConfig(config));
		if (error == null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String viewUpdateConfig() {
		config = configBO.getConfigById(id);
		return SUCCESS;
	}

	@ActionSecurity(module = 16)
	public String updateConfig() {
		ConfigDO configDO = configBO.getConfigById(config.getConfigkey());
		if (config != null) {
			configDO.setConfigkey(config.getConfigkey());
			configDO.setConfigvalue(config.getConfigvalue());
			configDO.setDescription(config.getDescription());
			configBO.updateConfig(configDO);
		}
		return SUCCESS;
	}

	public String viewOtherConfig() {
		sendMail = Constants.SENDMAIL;
		mailSendCommand = Constants.MAIL_SEND_COMMAND;
		sendSms = Constants.SENDSMS;
		smsSendCommand = Constants.SMS_SEND_COMMAND;
		sendWangWang = Constants.SENDWANGWANG;
		wangwangSendCommand = Constants.WANGWANG_SEND_COMMAND;
		return SUCCESS;
	}

	@ActionSecurity(module = 18)
	public String updateOtherConfig() {
		logsBO.insertSuccess(LogsDO.SUBTYPE_CONFIG_MOD, "sendMail", "sendMail:" + Constants.SENDMAIL + "->" + sendMail, getLogname());
		logsBO.insertSuccess(LogsDO.SUBTYPE_CONFIG_MOD, "mailSendCommand", "mailSendCommand:" + Constants.MAIL_SEND_COMMAND + "->" + mailSendCommand,
				getLogname());
		logsBO.insertSuccess(LogsDO.SUBTYPE_CONFIG_MOD, "sendSms", "sendSms:" + Constants.SENDSMS + "->" + sendSms, getLogname());
		logsBO
				.insertSuccess(LogsDO.SUBTYPE_CONFIG_MOD, "smsSendCommand", "smsSendCommand:" + Constants.SMS_SEND_COMMAND + "->" + smsSendCommand,
						getLogname());
		logsBO.insertSuccess(LogsDO.SUBTYPE_CONFIG_MOD, "sendWangWang", "sendWangWang:" + Constants.SENDWANGWANG + "->" + sendWangWang, getLogname());
		logsBO.insertSuccess(LogsDO.SUBTYPE_CONFIG_MOD, "wangwangSendCommand", "wangwangSendCommand:" + Constants.WANGWANG_SEND_COMMAND + "->"
				+ wangwangSendCommand, getLogname());
		Constants.SENDMAIL = sendMail;
		Constants.MAIL_SEND_COMMAND = mailSendCommand;
		Constants.SENDSMS = sendSms;
		Constants.SMS_SEND_COMMAND = smsSendCommand;
		Constants.SENDWANGWANG = sendWangWang;
		Constants.WANGWANG_SEND_COMMAND = wangwangSendCommand;
		return SUCCESS;
	}

	@ActionSecurity(module = 19)
	public String deleteConfig() {
		configBO.deleteConfigByConfigKey(id);
		return SUCCESS;
	}

	public void setConfigBO(IConfigBO configBO) {
		this.configBO = configBO;
	}

	public ConfigDO getQuery() {
		return this.query;
	}

	public void setQuery(ConfigDO config) {
		this.query = config;
	}

	public ConfigDO getConfig() {
		return this.config;
	}

	public void setConfig(ConfigDO config) {
		this.config = config;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getSendMail() {
		return sendMail;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public String getMailSendCommand() {
		return mailSendCommand;
	}

	public String getSendSms() {
		return sendSms;
	}

	public void setSendSms(String sendSms) {
		this.sendSms = sendSms;
	}

	public String getSmsSendCommand() {
		return smsSendCommand;
	}

	public void setSmsSendCommand(String smsSendCommand) {
		this.smsSendCommand = smsSendCommand;
	}

	public void setMailSendCommand(String mailSendCommand) {
		this.mailSendCommand = mailSendCommand;
	}

	public String getSendWangWang() {
		return sendWangWang;
	}

	public void setSendWangWang(String sendWangWang) {
		this.sendWangWang = sendWangWang;
	}

	public String getWangwangSendCommand() {
		return wangwangSendCommand;
	}

	public void setWangwangSendCommand(String wangwangSendCommand) {
		this.wangwangSendCommand = wangwangSendCommand;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

}