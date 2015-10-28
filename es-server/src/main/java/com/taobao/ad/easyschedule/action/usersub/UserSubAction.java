/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.usersub;

import java.util.List;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.bo.config.IConfigBO;
import com.taobao.ad.easyschedule.bo.job.JobBO;
import com.taobao.ad.easyschedule.bo.jobusersub.JobUserSubBO;
import com.taobao.ad.easyschedule.bo.repeatalarm.RepeatAlarmBO;
import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.commons.AjaxResult;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;
import com.taobao.ad.easyschedule.dataobject.JobDO;
import com.taobao.ad.easyschedule.dataobject.JobUserSubDO;
import com.taobao.ad.easyschedule.dataobject.RepeatAlarmDO;
import com.taobao.ad.easyschedule.dataobject.UUserDO;
import com.taobao.ad.easyschedule.manager.message.MessageManager;

public class UserSubAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private AjaxResult ajaxResult;
	private String message;
	private String successSub;
	private String failSub;
	private String warnSub;
	private String jobGroup;
	private String jobName;
	private String notifyUserName;
	private JobUserSubBO jobUserSubBO;
	private IUUserBO userBO;
	private List<UUserDO> userList;
	private List<JobDO> jobList;
	private List<CodeDO> groups;
	private ICodeBO codeBO;
	private IConfigBO configBO;
	private JobDO query;
	private JobBO jobBO;
	private String subject;
	private String waitingMsg;
	private String finishedMsg;
	private String sendMsg;
	private MessageManager messageManager;
	private int subType;
	private int repeatStatus;

	// 1=email 2=wangwang 3=sms
	int messageType;
	int isSub;

	private RepeatAlarmBO repeatAlarmBO;

	public String listSub() throws Exception {
		try {
			if (StringUtils.isEmpty(notifyUserName)) {
				notifyUserName = this.getLogname();
			}
			waitingMsg = this.getNickname() + configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_NOTIFY_WAITING_MSG);
			finishedMsg = this.getNickname() + configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_NOTIFY_FINISHED_MSG);
			UUserDO q = new UUserDO();
			q.setStatus(1L);
			q.setPerPageSize(300);
			userList = userBO.findUUsers(q);
			groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
			query = (query == null ? new JobDO() : query);
			jobList = jobBO.jobList(query);
			UUserDO userDO = userBO.getUUserByUsername(notifyUserName);
			for (JobDO job : jobList) {
				List<JobUserSubDO> userSubList = jobUserSubBO.findJobUserSub(job.getJobname(), job.getJobgroup(), userDO.getId(), null);
				for (JobUserSubDO subDO : userSubList) {
					switch (subDO.getType()) {
					case Constants.SUBTYPE_FAILURE:
						job.setFailUserSub(subDO);
						break;
					case Constants.SUBTYPE_SUCCESS:
						job.setSuccessUserSub(subDO);
						break;
					case Constants.SUBTYPE_WARNING:
						job.setWarnUserSub(subDO);
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询任务列表失败，可能是当前实例已经暂停或停止运行！", e);
		}
		return SUCCESS;
	}

	public String sendSubMsg() {
		if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroup) || StringUtils.isEmpty(sendMsg)) {
			return ERROR;
		}

		if (repeatStatus == Constants.REPEAT_ALARM_STATUS_PAUSE) {
			RepeatAlarmDO repeatAlarm = new RepeatAlarmDO();
			repeatAlarm.setJobName(jobName);
			repeatAlarm.setJobGroup(jobGroup);
			repeatAlarm.setStatus(Constants.REPEAT_ALARM_STATUS_PAUSE);
			repeatAlarmBO.updateRepeatAlarmStatus(repeatAlarm);
		} else {
			repeatAlarmBO.deleteRepeatAlarm(jobName, jobGroup);
		}
		messageManager.sendFailMessage(jobName, jobGroup, sendMsg, sendMsg);
		return SUCCESS;
	}

	public String addUserSub() {
		if (StringUtils.isEmpty(notifyUserName)) {
			notifyUserName = this.getLogname();
		}
		try {
			UUserDO userDO = userBO.getUUserByUsername(notifyUserName);
			JobUserSubDO sub = new JobUserSubDO();
			sub.setType(subType);
			sub.setJobGroup(jobGroup);
			sub.setJobName(jobName);
			sub.setUserId(userDO.getId());
			switch (messageType) {
			case Constants.EMAIL_SUB:
				sub.setEmail(isSub);
				break;
			case Constants.WANGWANG_SUB:
				sub.setWangwang(isSub);
				break;
			case Constants.MOBILE_SUB:
				sub.setMobile(isSub);
				break;
			}

			jobUserSubBO.saveOrUpdateJobUserSub(sub, messageType);
			ajaxResult = AjaxResult.succResult();
			ajaxResult.setMessage("保存成功");
		} catch (Exception e) {
			message = "保存失败";
			logger.error(e.getMessage(), e);
			ajaxResult = AjaxResult.errorResult(message);
		}
		return SUCCESS;
	}

	public AjaxResult getAjaxResult() {
		return ajaxResult;
	}

	public void setAjaxResult(AjaxResult ajaxResult) {
		this.ajaxResult = ajaxResult;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSuccessSub() {
		return successSub;
	}

	public void setSuccessSub(String successSub) {
		this.successSub = successSub;
	}

	public String getFailSub() {
		return failSub;
	}

	public void setFailSub(String failSub) {
		this.failSub = failSub;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public JobUserSubBO getJobUserSubBO() {
		return jobUserSubBO;
	}

	public void setJobUserSubBO(JobUserSubBO jobUserSubBO) {
		this.jobUserSubBO = jobUserSubBO;
	}

	public IUUserBO getUserBO() {
		return userBO;
	}

	public void setUserBO(IUUserBO userBO) {
		this.userBO = userBO;
	}

	public String getWarnSub() {
		return warnSub;
	}

	public void setWarnSub(String warnSub) {
		this.warnSub = warnSub;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getIsSub() {
		return isSub;
	}

	public void setIsSub(int isSub) {
		this.isSub = isSub;
	}

	public List<JobDO> getJobList() {
		return jobList;
	}

	public void setJobList(List<JobDO> jobList) {
		this.jobList = jobList;
	}

	public List<CodeDO> getGroups() {
		return groups;
	}

	public void setGroups(List<CodeDO> groups) {
		this.groups = groups;
	}

	public ICodeBO getCodeBO() {
		return codeBO;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public IConfigBO getConfigBO() {
		return configBO;
	}

	public void setConfigBO(IConfigBO configBO) {
		this.configBO = configBO;
	}

	public JobDO getQuery() {
		return query;
	}

	public void setQuery(JobDO query) {
		this.query = query;
	}

	public JobBO getJobBO() {
		return jobBO;
	}

	public void setJobBO(JobBO jobBO) {
		this.jobBO = jobBO;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWaitingMsg() {
		return waitingMsg;
	}

	public void setWaitingMsg(String waitingMsg) {
		this.waitingMsg = waitingMsg;
	}

	public String getFinishedMsg() {
		return finishedMsg;
	}

	public void setFinishedMsg(String finishedMsg) {
		this.finishedMsg = finishedMsg;
	}

	public String getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public String getNotifyUserName() {
		return notifyUserName;
	}

	public void setNotifyUserName(String notifyUserName) {
		this.notifyUserName = notifyUserName;
	}

	public List<UUserDO> getUserList() {
		return userList;
	}

	public void setUserList(List<UUserDO> userList) {
		this.userList = userList;
	}

	public void setRepeatAlarmBO(RepeatAlarmBO repeatAlarmBO) {
		this.repeatAlarmBO = repeatAlarmBO;
	}

	public int getRepeatStatus() {
		return repeatStatus;
	}

	public void setRepeatStatus(int repeatStatus) {
		this.repeatStatus = repeatStatus;
	}
	
 

 

}
