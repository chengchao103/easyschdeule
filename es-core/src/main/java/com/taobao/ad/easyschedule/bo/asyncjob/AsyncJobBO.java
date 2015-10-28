/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.asyncjob;

import java.util.List;

import org.quartz.JobDetail;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.AsyncJobQueueDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;

@Transactional
public interface AsyncJobBO {

	/**
	 * 异步任务完成回调处理
	 * 
	 * @return
	 */
	public JobResult completeJob(JobDetail jobDetail, JobResult jobResult);

	/**
	 * 检查异步任务队列
	 * 
	 * @return
	 */
	public JobResult checkAsyncJobQueue();

	/**
	 * 插入异步队列数据
	 * 
	 */
	public void insertAsyncJob(AsyncJobQueueDO asyncJob);

	/**
	 * 翻页查询异步任务
	 * 
	 * @return
	 */
	public List<AsyncJobQueueDO> findAsyncJobs(AsyncJobQueueDO asyncJob);

	/**
	 * 如果waitNum是<=1则执行删除,其余操作则将waitNum减1
	 * 
	 * @return
	 */
	public void deleteAsyncJobIfWaitNumIsZero(AsyncJobQueueDO asyncJob);
	
	public void deleteAsyncJob(AsyncJobQueueDO asyncJob);
}
