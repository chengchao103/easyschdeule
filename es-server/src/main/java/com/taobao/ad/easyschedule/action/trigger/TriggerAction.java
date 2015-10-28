/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.trigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.bo.trigger.TriggerBO;
import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.TriggerDO;
import com.taobao.ad.easyschedule.dataobject.UUserXGroupDO;

/**
 * 触发器管理Action
 * 
 * @author baimei
 * 
 */
public class TriggerAction extends BaseAction {

	private static final long serialVersionUID = -5522888318824166108L;

	private String searchGroup;
	private String searchName;
	private ILogsBO logsBO;
	private TriggerBO triggerBO;
	private ICodeBO codeBO;
	private List<TriggerDO> triggerList;
	private List<CodeDO> groups;
	private Map<String, Boolean> userGroupsMap;
	private List<UUserXGroupDO> userGroups;
	private IUUserBO userBO;
	private TriggerDO query = new TriggerDO();

	/**
	 *获取触发器列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listTrigger() throws Exception {
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		this.triggerList = new ArrayList<TriggerDO>();
		this.triggerList = triggerBO.getTriggerList(query);
		userGroups = userBO.findUUserXGroups(userBO.getUUserByUsername(this.getLogname()).getId());
		userGroupsMap = new HashMap<String, Boolean>(triggerList.size());
		try {
			for (TriggerDO triggerDO : triggerList) {
				Trigger trigger = this.easyscheduler.getTrigger(triggerDO.getTriggername(), triggerDO.getTriggergroup());
				if (trigger == null) {
					continue;
				}
				if (trigger instanceof SimpleTrigger) {
					triggerDO.setRepeatCount(((SimpleTrigger) trigger).getRepeatCount());
					triggerDO.setRepeatInterval(((SimpleTrigger) trigger).getRepeatInterval());
				}
				if (trigger instanceof CronTrigger) {
					triggerDO.setCronExpression(((CronTrigger) trigger).getCronExpression());
				}
				triggerDO.setStarttime(trigger.getStartTime());
				triggerDO.setEndtime(trigger.getEndTime());
				triggerDO.setPrevfiretime(trigger.getPreviousFireTime());
				triggerDO.setNextfiretime(trigger.getNextFireTime());
				// 设置权限
				for (UUserXGroupDO uGroup : userGroups) {
					if (triggerDO.getJobgroup().equals(uGroup.getGroupId().toString())) {
						userGroupsMap.put(triggerDO.getJobgroup(), true);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询触发器列表失败，可能是当前实例已经暂停或停止运行！", e);
		}
		return SUCCESS;
	}

	/**
	 *获取触发器状态code
	 * 
	 * @return
	 */
	public Integer getTriggerStateCode(String triggerName, String groupName) {

		try {
			return easyscheduler.getTriggerState(triggerName, groupName);
		} catch (Exception e) {
			logger.error("getTriggerStateCode", e);
		}
		return -1;
	}

	/**
	 * 获取触发器状态名
	 * 
	 * @return
	 */
	public String getTriggerStateName(String triggerName, String groupName) {
		try {
			return Constants.TRIGGER_STATE.get(easyscheduler.getTriggerState(triggerName, groupName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public ILogsBO getLogsBO() {
		return logsBO;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public TriggerDO getQuery() {
		return query;
	}

	public void setQuery(TriggerDO query) {
		this.query = query;
	}

	public String getSearchGroup() {
		return searchGroup;
	}

	public void setSearchGroup(String searchGroup) {
		this.searchGroup = searchGroup;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setTriggerBO(TriggerBO triggerBO) {
		this.triggerBO = triggerBO;
	}

	public List<TriggerDO> getTriggerList() {
		return triggerList;
	}

	public void setTriggerList(List<TriggerDO> triggerList) {
		this.triggerList = triggerList;
	}

	public ICodeBO getCodeBO() {
		return codeBO;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public List<CodeDO> getGroups() {
		return groups;
	}

	public void setGroups(List<CodeDO> groups) {
		this.groups = groups;
	}

	public Map<String, Boolean> getUserGroupsMap() {
		return userGroupsMap;
	}

	public void setUserGroupsMap(Map<String, Boolean> userGroupsMap) {
		this.userGroupsMap = userGroupsMap;
	}

	public List<UUserXGroupDO> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UUserXGroupDO> userGroups) {
		this.userGroups = userGroups;
	}

	public IUUserBO getUserBO() {
		return userBO;
	}

	public void setUserBO(IUUserBO userBO) {
		this.userBO = userBO;
	}
}
