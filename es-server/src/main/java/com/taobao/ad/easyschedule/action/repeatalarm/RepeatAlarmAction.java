/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.repeatalarm;

import java.util.List;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.bo.config.IConfigBO;
import com.taobao.ad.easyschedule.bo.repeatalarm.RepeatAlarmBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.dataobject.RepeatAlarmDO;
import com.taobao.ad.easyschedule.manager.message.MessageManager;

/**
 * 
 * 重复告警Action
 * 
 * @author bolin.hbc
 * 
 * 
 */
public class RepeatAlarmAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String waitingMsg;
	private String finishedMsg;
	private String sendMsg;
	private MessageManager messageManager;
	private int repeatStatus;
	private JobResult jobResult;
	private RepeatAlarmBO repeatAlarmBO;
	private final static int pageSize = 100;
	private RepeatAlarmDO query;
	private List<RepeatAlarmDO> list;
	private List<CodeDO> groups;
	private ICodeBO codeBO;
	private IConfigBO configBO;
	
	public String checkRepeatAlarm() {
		jobResult = JobResult.succcessResult();
		try {
			int count = repeatAlarmBO.getRepeatAlarmCount();
			int page = count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1;

			for (int i = 1; i <= page; i++) {
				RepeatAlarmDO reapeatAlarm = new RepeatAlarmDO();
				reapeatAlarm.setToPage(i);
				reapeatAlarm.setPerPageSize(pageSize);
				List<RepeatAlarmDO> reapeatAlarms = repeatAlarmBO.findRepeatAlarms(reapeatAlarm);

				for (RepeatAlarmDO r : reapeatAlarms) {
					if (r.getStatus() != Constants.REPEAT_ALARM_STATUS_PAUSE) {
						repeatAlarmBO.checkRepeatAlarm(r.getId());
					}
				}
			}
		} catch (Exception e) {
			jobResult = JobResult.errorResult(JobResult.RESULTCODE_OTHER_ERR, e.getMessage());
		}
		return SUCCESS;
	}

	public String listRepeatAlarm() {
		waitingMsg = this.getNickname() + configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_NOTIFY_WAITING_MSG);
		finishedMsg = this.getNickname() + configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_NOTIFY_FINISHED_MSG);
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		if (query == null) {
			query = new RepeatAlarmDO();
		}
		list = repeatAlarmBO.findRepeatAlarms(query);
		return SUCCESS;
	}

	public String sendMsgAndProcessRepeatAlarm() {
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

	public JobResult getJobResult() {
		return jobResult;
	}

	public void setJobResult(JobResult jobResult) {
		this.jobResult = jobResult;
	}

	public RepeatAlarmDO getQuery() {
		return query;
	}

	public List<RepeatAlarmDO> getList() {
		return list;
	}

	public void setList(List<RepeatAlarmDO> list) {
		this.list = list;
	}

	public void setQuery(RepeatAlarmDO query) {
		this.query = query;
	}

	public List<CodeDO> getGroups() {
		return groups;
	}

	public void setGroups(List<CodeDO> groups) {
		this.groups = groups;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public void setRepeatAlarmBO(RepeatAlarmBO repeatAlarmBO) {
		this.repeatAlarmBO = repeatAlarmBO;
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

	public int getRepeatStatus() {
		return repeatStatus;
	}

	public void setRepeatStatus(int repeatStatus) {
		this.repeatStatus = repeatStatus;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public void setConfigBO(IConfigBO configBO) {
		this.configBO = configBO;
	}

}