/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.job.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.taobao.ad.easyschedule.bo.job.JobBO;
import com.taobao.ad.easyschedule.dao.job.JobDAO;
import com.taobao.ad.easyschedule.dataobject.JobDO;

/**
 * @author huangbaichuan.pt 2010 下午07:39:58
 */
public class JobBOImpl implements JobBO {

	private JobDAO jobDAO;

	public List<JobDO> jobList(JobDO jobDO) {

		return this.jobDAO.jobList(jobDO);
	}

	public void setJobDAO(JobDAO jobDAO) {
		this.jobDAO = jobDAO;
	}

	@Override
	public List<String> findAllJobGroupAndNames() {

		JobDO query = new JobDO();
		query.setPerPageSize(500); // TODO 就当任务不超过500,以后一起重构

		List<JobDO> jobs = jobDAO.jobList(query);
		List<String> jobGroupNames = new ArrayList<String>(jobs.size());

		for (JobDO job : jobs) {

			jobGroupNames.add(job.getJobgroup() + "|" + job.getJobname());
		}

		return jobGroupNames;
	}

	@Override
	public Integer getCurrentJobNum() throws DataAccessException {
		return jobDAO.getCurrentJobNum();
	}

}
