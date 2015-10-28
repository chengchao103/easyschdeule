/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.jobusersub.impl;

import java.util.List;
import com.taobao.ad.easyschedule.bo.jobusersub.JobUserSubBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dao.jobusersub.JobUserSubDAO;
import com.taobao.ad.easyschedule.dataobject.JobUserSubDO;

public class JobUserSubBOImpl implements JobUserSubBO {

	JobUserSubDAO jobUserSubDAO;

	@Override
	public void deleteUserSub(JobUserSubDO userSub) {
		jobUserSubDAO.deleteUserSub(userSub);
	}

	@Override
	public List<JobUserSubDO> findJobUserSub(String jobName, String jobGroup, Long userId, Integer type) {
		return jobUserSubDAO.findJobUserSub(jobName, jobGroup, userId, type);
	}

	@Override
	public JobUserSubDO getUserSub(JobUserSubDO userSub) {
		return jobUserSubDAO.getUserSub(userSub);
	}

	@Override
	public void insertUserSub(JobUserSubDO userSub) {
		jobUserSubDAO.insertUserSub(userSub);
	}

	@Override
	public void updateUserSub(JobUserSubDO userSub) {
		jobUserSubDAO.updateUserSub(userSub);
	}

	public void setJobUserSubDAO(JobUserSubDAO jobUserSubDAO) {
		this.jobUserSubDAO = jobUserSubDAO;
	}

	@Override
	public void saveOrUpdateJobUserSub(JobUserSubDO userSub, int messageType) {
		JobUserSubDO sub = jobUserSubDAO.getUserSub(userSub);
		if (sub == null) {
			jobUserSubDAO.insertUserSub(userSub);
		} else {
			switch (messageType) {
			case Constants.EMAIL_SUB:
				userSub.setWangwang(sub.getWangwang());
				userSub.setMobile(sub.getMobile());
				break;
			case Constants.WANGWANG_SUB:
				userSub.setEmail(sub.getEmail());
				userSub.setMobile(sub.getMobile());
				break;
			case Constants.MOBILE_SUB:
				userSub.setWangwang(sub.getWangwang());
				userSub.setEmail(sub.getEmail());
				break;
			}
			jobUserSubDAO.updateUserSub(userSub);
		}
	}
}
