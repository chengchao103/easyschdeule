/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.job.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.job.JobDAO;
import com.taobao.ad.easyschedule.dataobject.JobDO;

/**
 * @author bolin.hbc
 * 
 */
public class JPAJobDAOImpl extends JPABaseDAO implements JobDAO {

	@SuppressWarnings("unchecked")
	public List<JobDO> jobList(JobDO jobDO) throws DataAccessException {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("t.jobname", jobDO.getJobname());
		map.put("t.jobgroup", jobDO.getJobgroup());
		jobDO.setTotalItem(super.getQueryCount("select count(*) from es_job_details t", map));
		return super.queryForListIsNotEmpty("select t from es_job_details t", map, jobDO, "order by t.jobgroup, t.jobname");

	}

	@Override
	public Integer getCurrentJobNum() throws DataAccessException {
		return super.getQueryCount("select count(*) from es_job_details t", null);
	}
}
