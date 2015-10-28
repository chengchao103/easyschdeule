/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.asyncjobqueue;

import java.util.List;

import com.taobao.ad.easyschedule.dataobject.AsyncJobQueueDO;

public interface AsyncJobQueueDAO {

	public Integer getAsyncJobCount();

	public void insertAsyncJob(AsyncJobQueueDO asyncJob);

	public List<AsyncJobQueueDO> getAsyncJob(AsyncJobQueueDO asyncJob);

	public void deleteAsyncJobById(Integer id);

	public void updateAsyncJob(AsyncJobQueueDO asyncJob);

	public AsyncJobQueueDO getLastAsyncJobQueue(AsyncJobQueueDO asyncJob);

}
