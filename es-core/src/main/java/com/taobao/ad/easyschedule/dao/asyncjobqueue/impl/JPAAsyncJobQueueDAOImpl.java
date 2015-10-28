/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.asyncjobqueue.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.asyncjobqueue.AsyncJobQueueDAO;
import com.taobao.ad.easyschedule.dataobject.AsyncJobQueueDO;

public class JPAAsyncJobQueueDAOImpl extends JPABaseDAO implements AsyncJobQueueDAO {

	@Override
	public void deleteAsyncJobById(Integer id) {
		AsyncJobQueueDO r = getJpaTemplate().find(AsyncJobQueueDO.class, id);
		if (r != null)
			getJpaTemplate().remove(r);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AsyncJobQueueDO> getAsyncJob(AsyncJobQueueDO asyncJob) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobname", asyncJob.getJobname());
		map.put("t.jobgroup", asyncJob.getJobgroup());
		asyncJob.setTotalItem(super.getQueryCount("select count(t) from es_async_job_queue t", map));
		return super.queryForListIsNotEmpty("select t from es_async_job_queue t", map, asyncJob, "order by t.createTime desc");
	}

	@Override
	public void insertAsyncJob(AsyncJobQueueDO asyncJob) {
		getJpaTemplate().persist(asyncJob);
	}

	@Override
	public Integer getAsyncJobCount() {
		return super.getQueryCount("select count(t) from es_async_job_queue t", new HashMap<String, Object>());
	}

	@Override
	public AsyncJobQueueDO getLastAsyncJobQueue(final AsyncJobQueueDO asyncJob) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobname", asyncJob.getJobname());
		map.put("t.jobgroup", asyncJob.getJobgroup());
		return (AsyncJobQueueDO) super.executeSingleIsNotEmpty("select t from es_async_job_queue t", map, "order by t.createTime");
	}

	@Override
	public void updateAsyncJob(AsyncJobQueueDO asyncJob) {
		getJpaTemplate().merge(asyncJob);
	}

}
