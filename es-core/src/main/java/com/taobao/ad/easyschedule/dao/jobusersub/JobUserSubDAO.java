/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.jobusersub;

import java.util.List;

import com.taobao.ad.easyschedule.dataobject.JobUserSubDO;

public interface JobUserSubDAO {

	public JobUserSubDO getUserSub(JobUserSubDO userSub);

	public List<JobUserSubDO> findJobUserSub(String jobName, String jobGroup, Long userId, Integer type);

	public void insertUserSub(JobUserSubDO userSub);

	public void deleteUserSub(JobUserSubDO userSub);

	public void updateUserSub(JobUserSubDO userSub);

}
