/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.schedule;

import java.util.List;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.commons.AjaxResult;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.FormatUtil;
import com.taobao.ad.easyschedule.commons.utils.TokenUtils;
import com.taobao.ad.easyschedule.commons.web.SchedulerDTO;
import com.taobao.ad.easyschedule.dataobject.LogsDO;

/**
 * 本地实例管理Action
 * 
 * @author baimei
 * 
 */
public class LocalScheduleAction extends BaseAction {

	private static final long serialVersionUID = 1433908530815520652L;

	private SchedulerDTO schedulerDTO;
	private List<SchedulerDTO> schedulerList;
	private ILogsBO logsBO;
	private String token;
	private String signTime;
	private AjaxResult result;
	private String userName;

	public String getInstanceAjax() {
		try {
			schedulerDTO = this.getSchedulerInfo();
		} catch (Exception e) {
			logger.error("操作失败:" + e.getMessage());
		}
		return SUCCESS;
	}

	public String startInstanceAjax() {
		try {
			if(!TokenUtils.validateToken(token, signTime)) {
				result = AjaxResult.errorResult("token验证失败");
				return SUCCESS;
			}
			logsBO.insertSuccess(LogsDO.SUBTYPE_SCHEDULE_START, easyscheduler.getSchedulerInstanceId(), null, userName);
			easyscheduler.start();
			result = AjaxResult.succResult();
		} catch (Exception e) {
			logger.error("操作失败:" + e.getMessage());
			result = AjaxResult.errorResult(e.getMessage());
		}
		return SUCCESS;

	}

	public String stopInstanceAjax() {
		try {
			if(!TokenUtils.validateToken(token, signTime)) {
				result = AjaxResult.errorResult("token验证失败");
				return SUCCESS;
			}
			logsBO.insertSuccess(LogsDO.SUBTYPE_SCHEDULE_STOP, easyscheduler.getSchedulerInstanceId(), null, userName);
			easyscheduler.shutdown(false);
			result = AjaxResult.succResult();
		} catch (Exception e) {
			logger.error("操作失败:" + e.getMessage());
			result = AjaxResult.errorResult(e.getMessage());
		}
		return SUCCESS;

	}

	public String pauseInstanceAjax() {
		try {
			if(!TokenUtils.validateToken(token, signTime)) {
				result = AjaxResult.errorResult("token验证失败");
				return SUCCESS;
			}
			logsBO.insertSuccess(LogsDO.SUBTYPE_SCHEDULE_PAUSE, easyscheduler.getSchedulerInstanceId(), null, userName);
			easyscheduler.standby();
			result = AjaxResult.succResult();
		} catch (Exception e) {
			logger.error("操作失败:" + e.getMessage());
			result = AjaxResult.errorResult(e.getMessage());
		}
		return SUCCESS;

	}

	public String waitAndStopSchedulerInstanceAjax() {
		try {
			if(!TokenUtils.validateToken(token, signTime)) {
				result = AjaxResult.errorResult("token验证失败");
				return SUCCESS;
			}
			logsBO.insertSuccess(LogsDO.SUBTYPE_SCHEDULE_SAFESTOP, easyscheduler.getSchedulerInstanceId(), null, userName);
			easyscheduler.shutdown(true);
			result = AjaxResult.succResult();
		} catch (Exception e) {
			logger.error("操作失败:" + e.getMessage());
			result = AjaxResult.errorResult(e.getMessage());
		}
		return SUCCESS;
	}

	private SchedulerDTO getSchedulerInfo() throws Exception {
		SchedulerDTO schedulerDTO = new SchedulerDTO();
		schedulerDTO.setSchedulerInstanceId(easyscheduler.getSchedulerInstanceId());
		schedulerDTO.setSchedulerName(easyscheduler.getSchedulerName());
		schedulerDTO.setNumJobsExecuted(String.valueOf(easyscheduler.getMetaData().getNumberOfJobsExecuted()));

		if (easyscheduler.getMetaData().isJobStoreSupportsPersistence()) {
			schedulerDTO.setPersistenceType("DB");
		} else {
			schedulerDTO.setPersistenceType("Memory");
		}
		schedulerDTO.setRunningSince(FormatUtil.getDateAsString((easyscheduler.getMetaData().getRunningSince())));
		if (easyscheduler.isShutdown()) {
			schedulerDTO.setState("停止中");
			schedulerDTO.setStateCode(Constants.INSTANCE_STOP);
		} else if (easyscheduler.isInStandbyMode()) {
			schedulerDTO.setState("暂停中");
			schedulerDTO.setStateCode(Constants.INSTANCE_PAUSE);
		} else {
			schedulerDTO.setState("运行中");
			schedulerDTO.setStateCode(Constants.INSTANCE_START);
		}

		schedulerDTO.setThreadPoolSize(String.valueOf(easyscheduler.getMetaData().getThreadPoolSize()));
		schedulerDTO.setVersion(easyscheduler.getMetaData().getVersion());
		schedulerDTO.setSummary(easyscheduler.getMetaData().getSummary());
		return schedulerDTO;
	}

	public SchedulerDTO getSchedulerDTO() {
		return schedulerDTO;
	}

	public void setSchedulerDTO(SchedulerDTO schedulerDTO) {
		this.schedulerDTO = schedulerDTO;
	}

	public ILogsBO getLogsBO() {
		return logsBO;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public List<SchedulerDTO> getSchedulerList() {
		return schedulerList;
	}

	public void setSchedulerList(List<SchedulerDTO> schedulerList) {
		this.schedulerList = schedulerList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getLogname() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
