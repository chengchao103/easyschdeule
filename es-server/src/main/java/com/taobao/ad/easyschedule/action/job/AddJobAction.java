/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.job;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.opensymphony.xwork2.ActionContext;
import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.bo.config.IConfigBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.JobUtil;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;
import com.taobao.ad.easyschedule.dataobject.LogsDO;
import com.taobao.ad.easyschedule.dataobject.UUserXGroupDO;
import com.taobao.ad.easyschedule.job.DataTrackingJob;
import com.taobao.ad.easyschedule.job.FileLineCountJob;
import com.taobao.ad.easyschedule.job.HttpJob;
import com.taobao.ad.easyschedule.job.ShellJob;
import com.taobao.ad.easyschedule.job.StoredProcedureJob;
import com.taobao.ad.easyschedule.security.ActionSecurity;

/**
 * 新增任务Action
 * 
 * @author baimei
 */
public class AddJobAction extends BaseAction {

	private static final long serialVersionUID = 8412800967797301980L;

	private int action = Constants.JOB_ACTION_ADD;

	private ILogsBO logsBO;

	private ICodeBO codeBO;

	private IConfigBO configBO;

	private List<CodeDO> codes;

	private List<CodeDO> groups;

	private JobDetail jobDetail = new JobDetail();

	private String cronExpression = "";

	private String className = "";

	private String parameterNames[] = {};

	private String parameterValues[] = {};
	// 输入的参数名称
	private String parameterInputOutPutT[] = {};
	private String parameterInputOutPutType[] = {};
	private String parameterInputOutPutValue[] = {};

	private String triggerType = "";

	private int repeatCount = 1;

	private long repeatInterval = 5000L;

	private int priority = 5;

	private List<UUserXGroupDO> userGroups;
	
	private IUUserBO userBO;

	/**
	 * 新增一个测试任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@ActionSecurity(module = 1)
	public String addTestJob() throws Exception {
		try {
			for (int i = 0; i < 20; i++) {
				String countjob = getNewJobId();
				String schedId = easyscheduler.getSchedulerInstanceId();
				JobDetail job = new JobDetail("test_" + countjob, "999", HttpJob.class);

				codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_HTTPJOB_JOBDATA);
				for (CodeDO code : codes) {
					job.getJobDataMap().put(code.getKeycode(), code.getKeyname());
				}
				job.setRequestsRecovery(false);
				job.setDurability(true);
				CronTrigger trigger = new CronTrigger(job.getName(), job.getGroup(), job.getName(), job.getGroup(), configBO
						.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_CRONEXPRESSION));
				trigger.setDescription(this.getDescription());
				easyscheduler.deleteJob("test_" + countjob, schedId);
				easyscheduler.scheduleJob(job, trigger);
				logsBO.insertSuccess(LogsDO.SUBTYPE_ADD_JOB, job.getGroup() + "|" + job.getName(), "", getLogname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 进入新增Http任务界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddHttpJob() throws Exception {

		action = Constants.JOB_ACTION_ADD;
		jobDetail = new JobDetail();
		jobDetail.setName("stdXXX" + getNewJobId());
		jobDetail.setJobClass(HttpJob.class);
		jobDetail.setDescription("Std－XXX");
		jobDetail.setRequestsRecovery(false);
		jobDetail.setDurability(true);
		codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_HTTPJOB_JOBDATA);
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		repeatCount = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATCOUNT);
		repeatInterval = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATINTERVAL);
		triggerType = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_TRIGGERTYPE);
		cronExpression = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_CRONEXPRESSION);
		priority = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_PRIORITY);
		startTimeAsDate = null;
		stopTimeAsDate = null;
		return SUCCESS;
	}

	/**
	 * 进入新增Shell任务界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddShellJob() throws Exception {

		action = Constants.JOB_ACTION_ADD;
		jobDetail = new JobDetail();
		jobDetail.setName("shellXXX" + getNewJobId());
		jobDetail.setJobClass(ShellJob.class);
		jobDetail.setDescription("Shell－XXX");
		jobDetail.setRequestsRecovery(false);
		jobDetail.setDurability(true);
		codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_SHELLJOB_JOBDATA);
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		repeatCount = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATCOUNT);
		repeatInterval = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATINTERVAL);
		triggerType = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_TRIGGERTYPE);
		cronExpression = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_CRONEXPRESSION);
		priority = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_PRIORITY);
		startTimeAsDate = null;
		stopTimeAsDate = null;
		return SUCCESS;
	}
	
	/**
	 * 进入新增文件行数统计任务界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddLineCountJob() throws Exception {

		action = Constants.JOB_ACTION_ADD;
		jobDetail = new JobDetail();
		jobDetail.setName("lineCountXXX" + getNewJobId());
		jobDetail.setJobClass(FileLineCountJob.class);
		jobDetail.setDescription("文件－XXX");
		jobDetail.setRequestsRecovery(false);
		jobDetail.setDurability(true);
		codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_LINECOUNTJOB_JOBDATA);
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		repeatCount = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATCOUNT);
		repeatInterval = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATINTERVAL);
		triggerType = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_TRIGGERTYPE);
		cronExpression = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_CRONEXPRESSION);
		priority = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_PRIORITY);
		startTimeAsDate = null;
		stopTimeAsDate = null;
		return SUCCESS;
	}

	
	/**
	 * 进入新增一个数据监控任务界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddDataTrackingJob() throws Exception {

		action = Constants.JOB_ACTION_ADD;
		jobDetail = new JobDetail();
		jobDetail.setName("dtXXX" + getNewJobId());
		jobDetail.setJobClass(DataTrackingJob.class);
		jobDetail.setDescription("数据－XXX");
		jobDetail.setRequestsRecovery(false);
		jobDetail.setDurability(true);
		codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_DTJOB_JOBDATA);
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		repeatCount = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATCOUNT);
		repeatInterval = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATINTERVAL);
		triggerType = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_TRIGGERTYPE);
		cronExpression = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_CRONEXPRESSION);
		priority = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_PRIORITY);
		startTimeAsDate = null;
		stopTimeAsDate = null;
		return SUCCESS;
	}

	/**
	 * 进入新增存储过程任务界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddProcedureJob() throws Exception {

		action = Constants.JOB_ACTION_ADD;
		jobDetail = new JobDetail();
		jobDetail.setName("procedureXXX" + getNewJobId());
		jobDetail.setJobClass(StoredProcedureJob.class);
		jobDetail.setDescription("Procedure－XXX");
		jobDetail.setRequestsRecovery(false);
		jobDetail.setDurability(true);
		codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_PROCEDURE_JOBDATA);
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		repeatCount = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATCOUNT);
		repeatInterval = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_REPEATINTERVAL);
		triggerType = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_TRIGGERTYPE);
		cronExpression = configBO.getStringValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_CRONEXPRESSION);
		priority = configBO.getIntValue(ConfigDO.CONFIG_KEY_JOB_DEFAULT_PRIORITY);
		startTimeAsDate = null;
		stopTimeAsDate = null;
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
	 * 新增/修改一个任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@ActionSecurity(module = 2)
	public String addJob() throws Exception {

		if (!checkIsInGroup(jobDetail.getGroup())) {
			return ERROR;
		}

		try {
			jobDetail.setJobClass(Class.forName(className));
			for (int i = 0; i < parameterNames.length; i++) {
				if (parameterNames[i].trim().length() > 0 && parameterValues[i].trim().length() > 0) {
					jobDetail.getJobDataMap().put(parameterNames[i].trim(), parameterValues[i].trim());
				}
			}
			// 存储过程中的参数个数和用户设置的个数不匹配
			if (parameterInputOutPutT != null && parameterInputOutPutT.length > 0 && parameterInputOutPutType != null && parameterInputOutPutType.length > 0
					&& parameterInputOutPutValue != null && parameterInputOutPutValue.length > 0) {
				JSONArray array = new JSONArray();
				for (int i = 0; i < parameterInputOutPutT.length; i++) {
					JSONObject result = new JSONObject();
					int type = Integer.parseInt(parameterInputOutPutType[i]);
					if (parameterInputOutPutValue[i] != null && !parameterInputOutPutValue[i].isEmpty()) {
						switch (type) {
						case Constants.STOREDPROCEDUREJOB_VARCHAR:
						case Constants.STOREDPROCEDUREJOB_NUMBER:
							result.put("value", parameterInputOutPutValue[i]);
							break;
						case Constants.STOREDPROCEDUREJOB_DATE:
							result.put("value", parameterInputOutPutValue[i]);
							break;
						}
					}
					result.put("t", parameterInputOutPutT[i]);
					result.put("type", type);
					array.put(result);
				}
				// 存储过程都默认为同步处理
				jobDetail.getJobDataMap().put("synchronous", "true");
				jobDetail.getJobDataMap().put("parameter", array.toString());
			}
			jobDetail.getJobDataMap().put("version", Constants.ES_CURRENT_VERSION);

			if (Constants.TRIGGERTYPE_SIMPLE.equals(triggerType)) {
				// 简单触发
				SimpleTrigger trigger = new SimpleTrigger(jobDetail.getName(), jobDetail.getGroup(), repeatCount, repeatInterval);
				trigger.setPriority(priority);
				trigger.setDescription(description);
				if (startTimeAsDate != null)
					trigger.setStartTime(startTimeAsDate);
				if (stopTimeAsDate != null)
					trigger.setEndTime(stopTimeAsDate);
				if (action == Constants.JOB_ACTION_ADD) {
					easyscheduler.scheduleJob(jobDetail, trigger);
				} else if (action == Constants.JOB_ACTION_MOD) {
					easyscheduler.deleteJob(jobDetail.getName(), jobDetail.getGroup());
					easyscheduler.scheduleJob(jobDetail, trigger);
				} else {
					return ERROR;
				}

			} else {
				// Cron触发
				CronTrigger trigger = new CronTrigger(jobDetail.getName(), jobDetail.getGroup(), jobDetail.getName(), jobDetail.getGroup(), cronExpression);
				trigger.setPriority(priority);
				trigger.setDescription(this.getDescription());
				if (startTimeAsDate != null)
					trigger.setStartTime(startTimeAsDate);
				if (stopTimeAsDate != null)
					trigger.setEndTime(stopTimeAsDate);
				if (action == Constants.JOB_ACTION_ADD) {
					easyscheduler.scheduleJob(jobDetail, trigger);
				} else if (action == Constants.JOB_ACTION_MOD) {
					easyscheduler.addJob(jobDetail, true);
					easyscheduler.rescheduleJob(jobDetail.getName(), jobDetail.getGroup(), trigger);
				} else {
					return ERROR;
				}
			}
			createJobDetail(jobDetail.getJobDataMap(), action, className);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	

	/**
	 * 进入修改任务界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toUpdateJob() throws Exception {
		if (StringUtils.isNotEmpty(jobName) && StringUtils.isNotEmpty(jobGroup)) {
			action = Constants.JOB_ACTION_MOD;
			jobDetail = easyscheduler.getJobDetail(jobName, jobGroup);
			Trigger trigger = easyscheduler.getTrigger(jobName, jobGroup);
			if (trigger instanceof SimpleTrigger) {
				triggerType = Constants.TRIGGERTYPE_SIMPLE;
				repeatCount = ((SimpleTrigger) trigger).getRepeatCount();
				repeatInterval = ((SimpleTrigger) trigger).getRepeatInterval();
				priority = ((SimpleTrigger) trigger).getPriority();
			} else if (trigger instanceof CronTrigger) {
				triggerType = Constants.TRIGGERTYPE_CRON;
				cronExpression = ((CronTrigger) trigger).getCronExpression();
				priority = ((CronTrigger) trigger).getPriority();
			}
			startTimeAsDate = trigger.getStartTime();
			stopTimeAsDate = trigger.getEndTime();
			if (jobDetail.getJobClass().getName().equals(HttpJob.class.getName())) {
				codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_HTTPJOB_JOBDATA);
			} else if (jobDetail.getJobClass().getName().equals(ShellJob.class.getName())) {
				codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_SHELLJOB_JOBDATA);
			} else if (jobDetail.getJobClass().getName().equals(DataTrackingJob.class.getName())) {
				codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_DTJOB_JOBDATA);
			} else if (jobDetail.getJobClass().getName().equals(FileLineCountJob.class.getName())) {
				codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_LINECOUNTJOB_JOBDATA);
			} else if (jobDetail.getJobClass().getName().equals(StoredProcedureJob.class.getName())) {
				codes = codeBO.getCodesByKey(CodeDO.CODE_KEY_PROCEDURE_JOBDATA);
			} 
			groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		}
		return SUCCESS;
	}

	/**
	 * 查看任务详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewJob() throws Exception {
		ActionContext.getContext().put("disabled", "disabled");
		return toUpdateJob();
	}

	/**
	 * 创建任务详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	private void createJobDetail(JobDataMap jobData, int action, String className) throws Exception {
		Long logSubType;
		logSubType = action == Constants.JOB_ACTION_ADD ? LogsDO.SUBTYPE_ADD_JOB : LogsDO.SUBTYPE_MOD_JOB;
		logsBO.insertSuccess(logSubType, jobDetail.getGroup() + "|" + jobDetail.getName(), JobUtil.getJobData(jobData), getLogname());
		return;
	}

	/**
	 * 进入Cron表达式说明页
	 * 
	 * @return
	 */
	public String toViewTriggerHelp() throws Exception {
		return SUCCESS;
	}
	
	public String getNewJobId() {
		double a = Math.random() * 100000;
		a = Math.ceil(a);
		//return new Double(a).intValue();
		return "";
	}

	public ILogsBO getLogsBO() {
		return logsBO;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public ICodeBO getCodeBO() {
		return codeBO;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public List<CodeDO> getCodes() {
		return codes;
	}

	public void setCodes(List<CodeDO> codes) {
		this.codes = codes;
	}

	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(String[] parameterNames) {
		this.parameterNames = parameterNames;
	}

	public String[] getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(String[] parameterValues) {
		this.parameterValues = parameterValues;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public long getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public List<CodeDO> getGroups() {
		return groups;
	}

	public void setGroups(List<CodeDO> groups) {
		this.groups = groups;
	}

	public void setConfigBO(IConfigBO configBO) {
		this.configBO = configBO;
	}

	public String[] getParameterInputOutPutT() {
		return parameterInputOutPutT;
	}

	public void setParameterInputOutPutT(String[] parameterInputOutPutT) {
		this.parameterInputOutPutT = parameterInputOutPutT;
	}

	public String[] getParameterInputOutPutType() {
		return parameterInputOutPutType;
	}

	public void setParameterInputOutPutType(String[] parameterInputOutPutType) {
		this.parameterInputOutPutType = parameterInputOutPutType;
	}

	public String[] getParameterInputOutPutValue() {
		return parameterInputOutPutValue;
	}

	public void setParameterInputOutPutValue(String[] parameterInputOutPutValue) {
		this.parameterInputOutPutValue = parameterInputOutPutValue;
	}

	/**
	 * @return the configBO
	 */
	public IConfigBO getConfigBO() {
		return configBO;
	}


	public List<UUserXGroupDO> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UUserXGroupDO> userGroups) {
		this.userGroups = userGroups;
	}

	public void setUserBO(IUUserBO userBO) {
		this.userBO = userBO;
	}

 
}
