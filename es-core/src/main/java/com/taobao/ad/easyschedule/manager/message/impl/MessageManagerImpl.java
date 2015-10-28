/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.manager.message.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.bo.jobusersub.JobUserSubBO;
import com.taobao.ad.easyschedule.bo.repeatalarm.RepeatAlarmBO;
import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.MailUtil;
import com.taobao.ad.easyschedule.commons.utils.SmsUtil;
import com.taobao.ad.easyschedule.commons.utils.WangWangUtil;
import com.taobao.ad.easyschedule.dataobject.JobUserSubDO;
import com.taobao.ad.easyschedule.dataobject.RepeatAlarmDO;
import com.taobao.ad.easyschedule.dataobject.UUserDO;
import com.taobao.ad.easyschedule.manager.message.MessageManager;

public class MessageManagerImpl implements MessageManager {
	final static Logger logger = LoggerFactory.getLogger("MESSAGELOG");

	private JobUserSubBO jobUserSubBO;
	private IUUserBO userBO;
	private RepeatAlarmBO repeatAlarmBO;
	
	@Override
	public void sendFailMessage(String jobName, String jobGroup, String message, String content) {
		sendMessage(jobName, jobGroup, message, content, Constants.SUBTYPE_FAILURE);
	}
	
	@Override
	public void sendFailMessage(String jobName, String jobGroup, String message, String content, int repeatNum) {
		if (repeatNum > 0) {
			try {
				RepeatAlarmDO repeatAlarm = new RepeatAlarmDO();
				repeatAlarm.setJobGroup(jobGroup);
				repeatAlarm.setJobName(jobName);
				repeatAlarm.setRepeatAlarmNum(repeatNum);
				repeatAlarmBO.saveOrUpdateRepeatAlarm(repeatAlarm);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		sendMessage(jobName, jobGroup, message, content, Constants.SUBTYPE_FAILURE);
	}

	@Override
	public void sendSuccessMessage(String jobName, String jobGroup, String message, String content) {
		sendMessage(jobName, jobGroup, message, content, Constants.SUBTYPE_SUCCESS);
	}

	@Override
	public void sendWarningMessage(String jobName, String jobGroup, String message, String content) {
		sendMessage(jobName, jobGroup, message, content, Constants.SUBTYPE_WARNING);
	}

	private void sendMessage(String jobName, String jobGroup, String message, String content, int type) {
		List<JobUserSubDO> userSubList = jobUserSubBO.findJobUserSub(jobName, jobGroup, null, type);
		StringBuilder moblieList = new StringBuilder();
		StringBuilder emalList = new StringBuilder();
		StringBuilder wangwangList = new StringBuilder();
		for (JobUserSubDO sub : userSubList) {
			UUserDO user = userBO.getUUserById(sub.getUserId());
			if (sub.getMobile() == Constants.IS_SUB && StringUtils.isNotBlank(user.getMobile())) {
				moblieList = moblieList.append(user.getMobile()).append(";");
			}
			if (sub.getEmail() == Constants.IS_SUB && StringUtils.isNotBlank(user.getEmail())) {
				emalList = emalList.append(user.getEmail()).append(";");
			}
			if (sub.getWangwang() == Constants.IS_SUB && StringUtils.isNotBlank(user.getDescn())) {
				wangwangList = wangwangList.append(user.getDescn()).append(";");
			}
		}
		if (moblieList != null && moblieList.length() > 0) {
			// 短信内容长度限制，只发标题不发详细信息
			SmsUtil.sendSMS(moblieList.substring(0, moblieList.length() - 1), Constants.DEPLOY_MODE + ":" + message, "");
			logger.info("type:" + type + "|jobGroup:" + jobGroup + "|jobName:" + jobName + "|message:" + message + "|sendSms:" + moblieList);
		}
		if (emalList != null && emalList.length() > 0) {
			MailUtil.sendMail(emalList.substring(0, emalList.length() - 1), Constants.DEPLOY_MODE + ":" + message, content);
			logger.info("type:" + type + "|jobGroup:" + jobGroup + "|jobName:" + jobName + "|message:" + message + "|sendEmail:" + emalList);
		}
		if (wangwangList != null && wangwangList.length() > 0) {
			WangWangUtil.sendWangWang(wangwangList.substring(0, wangwangList.length() - 1), Constants.DEPLOY_MODE + ":" + message, content);
			logger.info("type:" + type + "|jobGroup:" + jobGroup + "|jobName:" + jobName + "|message:" + message + "|sendWangwang:" + wangwangList);
		}
	}

	public void setJobUserSubBO(JobUserSubBO jobUserSubBO) {
		this.jobUserSubBO = jobUserSubBO;
	}

	public void setUserBO(IUUserBO userBO) {
		this.userBO = userBO;
	}

	public void setRepeatAlarmBO(RepeatAlarmBO repeatAlarmBO) {
		this.repeatAlarmBO = repeatAlarmBO;
	}
}
