/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.jobusersub.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.jobusersub.JobUserSubDAO;
import com.taobao.ad.easyschedule.dataobject.JobUserSubDO;

public class JPAJobUserSubDAOImpl extends JPABaseDAO implements JobUserSubDAO {

	@Override
	public void deleteUserSub(final JobUserSubDO userSub) {
		JobUserSubDO sub = this.getUserSub(userSub);
		if (sub != null) {
			getJpaTemplate().remove(sub);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobUserSubDO> findJobUserSub(String jobName, String jobGroup, Long userId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobName", jobName);
		map.put("t.jobGroup", jobGroup);
		if (userId != null) {
			map.put("t.userId", userId);
		}
		if (type != null) {
			map.put("t.type", type);
		}
		return super.executeQueryIsNotEmpty("select t from es_job_user_sub t,es_u_user a where a.id=t.userId and a.status=1", map);
	}

	@Override
	public JobUserSubDO getUserSub(JobUserSubDO userSub) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobName", userSub.getJobName());
		map.put("t.userId", userSub.getUserId());
		map.put("t.jobGroup", userSub.getJobGroup());
		map.put("t.type", userSub.getType());
		return (JobUserSubDO) super.executeSingleIsNotEmpty("select t from es_job_user_sub t,es_u_user a where a.id=t.userId and a.status=1", map);
	}

	@Override
	public void insertUserSub(JobUserSubDO userSub) {
		userSub.setCreatetime(new Date());
		userSub.setUpdatetime(new Date());
		getJpaTemplate().persist(userSub);
	}

	@Override
	public void updateUserSub(JobUserSubDO userSub) {
		JobUserSubDO sub = this.getUserSub(userSub);
		userSub.setUpdatetime(new Date());
		userSub.setCreatetime(sub.getCreatetime());
		getJpaTemplate().merge(userSub);
	}
}