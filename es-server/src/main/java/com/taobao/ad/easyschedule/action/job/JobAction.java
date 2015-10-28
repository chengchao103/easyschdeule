/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.bo.job.JobBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.commons.AjaxResult;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.JobDO;
import com.taobao.ad.easyschedule.dataobject.LogsDO;
import com.taobao.ad.easyschedule.dataobject.UUserXGroupDO;
import com.taobao.ad.easyschedule.security.ActionSecurity;

/**
 * 任务管理Action
 * 
 * @author baimei
 * 
 */
public class JobAction extends BaseAction {

	private static final long serialVersionUID = 1433908530815520652L;
	private List<JobDO> jobList;
	private ILogsBO logsBO;
	private JobDO query;
	private JobBO jobBO;
	private ICodeBO codeBO;
	private List<CodeDO> groups;
	private IUUserBO userBO;
	private AjaxResult result;

	private Map<String, Boolean> userGroupsMap;
	private List<UUserXGroupDO> userGroups;

	public String listJob() throws Exception {
		try {
			groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
			query = (query == null ? new JobDO() : query);
			jobList = jobBO.jobList(query);
			userGroups = userBO.findUUserXGroups(userBO.getUUserByUsername(this.getLogname()).getId());
			userGroupsMap = new HashMap<String, Boolean>(jobList.size());
			for (JobDO job : jobList) {
				job.setJobDataMap(easyscheduler.getJobDetail(job.getJobname(), job.getJobgroup()).getJobDataMap());
				// 设置权限
				for (UUserXGroupDO uGroup : userGroups) {

					if ("dev".equals(Constants.DEPLOY_MODE)) {
						userGroupsMap.put(job.getJobgroup(), true);
						continue;
					}

					if (job.getJobgroup().equals(uGroup.getGroupId().toString())) {
						userGroupsMap.put(job.getJobgroup(), true);
					}
				}
				// 设置触发器相关信息
				Trigger trigger = this.easyscheduler.getTrigger(job.getJobname(), job.getJobgroup());
				if (trigger == null) {
					continue;
				}
				if (trigger instanceof SimpleTrigger) {
					job.setRepeatCount(((SimpleTrigger) trigger).getRepeatCount());
					job.setRepeatInterval(((SimpleTrigger) trigger).getRepeatInterval());
				}
				if (trigger instanceof CronTrigger) {
					job.setCronExpression(((CronTrigger) trigger).getCronExpression());
				}
				job.setPriority(trigger.getPriority());
				job.setStarttime(trigger.getStartTime());
				job.setEndtime(trigger.getEndTime());
				job.setPrevfiretime(trigger.getPreviousFireTime());
				job.setNextfiretime(trigger.getNextFireTime());
			}

		} catch (Exception e) {
			logger.error("查询任务列表失败，可能是当前实例已经暂停或停止运行！", e);
		}
		return SUCCESS;
	}
	/**
	 * 查询当前的用户是否有权向执行当前组的写操作
	 * 
	 * @return
	 */

	protected boolean checkIsInGroup(String group) {
		if ("dev".equals(Constants.DEPLOY_MODE)) {
			return true;
		}
		if (group == null || group.isEmpty()) {
			return false;
		}

		List<UUserXGroupDO> userGroups = userBO.findUUserXGroups(userBO.getUUserByUsername(this.getLogname()).getId());

		for (UUserXGroupDO g : userGroups) {
			if (g.getGroupId().longValue() == Long.parseLong(group)) {
				return true;
			}
		}
		addActionError("你沒有当前组的操作权限,请找管理员开通权限");
		return false;
	}

	/**
	 * 立即执行一个任务
	 * 
	 * @return
	 */
	@ActionSecurity(module = 4)
	public String runJob() {
		if (!checkIsInGroup(jobGroup)) {
			return ERROR;
		}
		try {
			easyscheduler.triggerJob(jobName, jobGroup);
			logsBO.insertWarning(LogsDO.SUBTYPE_TRIGGERJOB, jobGroup + "|" + jobName, "", getLogname());
		} catch (Exception e) {
			logger.error("立即执行任务失败！", e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 立即执行一个任务(自动化测试使用)
	 * 
	 * @return
	 */
	public String runJobForTest() {
		if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroup) || Constants.REAL_MODE.equals(Constants.DEPLOY_MODE)) {
			result = AjaxResult.errorResult("缺少jobName,jobGroup或者当前运行模式不允许调用");
			return SUCCESS;
		}
		try {
			easyscheduler.triggerJob(jobName, jobGroup);
			logsBO.insertWarning(LogsDO.SUBTYPE_TRIGGERJOB, jobGroup + "|" + jobName, "", "guest");
			result = AjaxResult.succResult();
			result.setMessage("调用成功");
		} catch (Exception e) {
			logger.error("立即执行任务失败！", e);
			result = AjaxResult.errorResult(e.getMessage());
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除一个任务
	 * 
	 * @return
	 */
	@ActionSecurity(module = 8)
	public String deleteJob() {
		if (!checkIsInGroup(jobGroup)) {
			return ERROR;
		}
		try {
			easyscheduler.deleteJob(jobName, jobGroup);
			logsBO.insertSuccess(LogsDO.SUBTYPE_DELETEJOB, jobGroup + "|" + jobName, "", getLogname());
		} catch (Exception e) {
			logger.error("删除任务失败！", e);
			return ERROR;
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

	/**
	 * 暂停一个触发器
	 * 
	 * @return
	 * @throws Exception
	 */
	@ActionSecurity(module = 6)
	public String pauseJob() throws Exception {
		if (!checkIsInGroup(jobGroup)) {
			return ERROR;
		}
		try {
			easyscheduler.pauseTrigger(jobName, jobGroup);
			logsBO.insertSuccess(LogsDO.SUBTYPE_TRIGGER_PAUSE, jobGroup + "|" + jobName, "", getLogname());
		} catch (Exception e) {
			logger.error("暂停一个触发器失败，可能是当前实例已经暂停或停止运行！", e);
		}
		return SUCCESS;
	}

	/**
	 * 恢复一个触发器
	 * 
	 * @return
	 * @throws Exception
	 */
	@ActionSecurity(module = 5)
	public String resumeJob() throws Exception {
		if (!checkIsInGroup(jobGroup)) {
			return ERROR;
		}
		try {
			easyscheduler.resumeTrigger(jobName, jobGroup);
			logsBO.insertSuccess(LogsDO.SUBTYPE_TRIGGER_RESUME, jobGroup + "|" + jobName, "", getLogname());
		} catch (Exception e) {
			logger.error("恢复一个触发器失败，可能是当前实例已经暂停或停止运行！", e);
		}
		return SUCCESS;
	}

	public ILogsBO getLogsBO() {
		return logsBO;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public JobDO getQuery() {
		return query;
	}

	public void setQuery(JobDO query) {
		this.query = query;
	}

	public void setJobBO(JobBO jobBO) {
		this.jobBO = jobBO;
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

	public void setUserBO(IUUserBO userBO) {
		this.userBO = userBO;
	}

	public List<UUserXGroupDO> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UUserXGroupDO> userGroups) {
		this.userGroups = userGroups;
	}

	public Map<String, Boolean> getUserGroupsMap() {
		return userGroupsMap;
	}

	public void setUserGroupsMap(Map<String, Boolean> userGroupsMap) {
		this.userGroupsMap = userGroupsMap;
	}

	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
	}

}
