/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.jobusersub;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.JobUserSubDO;

@Transactional
public interface JobUserSubBO {
	public JobUserSubDO getUserSub(JobUserSubDO userSub);

	/**
	 * 查询多个订阅信息
	 * @param jobName（必须项）
	 * @param jobGroup（必须项）
	 * @param userId（userId和type必选其一）
	 * @param type（userId和type必选其一）
	 * @return
	 */
	public List<JobUserSubDO> findJobUserSub(String jobName, String jobGroup, Long userId, Integer type);

	public void insertUserSub(JobUserSubDO userSub);

	public void deleteUserSub(JobUserSubDO userSub);

	public void updateUserSub(JobUserSubDO userSub);
	
	/**
	 * 当订阅的类型存在,则更新,当订阅的类型不存在则新增
	 * 
	 * @param userSub
	 */
	public void saveOrUpdateJobUserSub(JobUserSubDO userSub,int messageType);
}
