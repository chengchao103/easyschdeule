/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.jpa.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.ad.easyschedule.bo.asyncjob.AsyncJobBO;
import com.taobao.ad.easyschedule.dataobject.AsyncJobQueueDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springbeans-es-test-*.xml" })
public class AsyncJobTest {

	@Resource
	private AsyncJobBO asyncJobBO;

	@Test
	public void testInsertAsyncJob() {
		AsyncJobQueueDO asyncJob = new AsyncJobQueueDO();
		asyncJob.setJobgroup("110");
		asyncJob.setJobname("3");
		asyncJob.setWaitCallNum(100);
		asyncJob.setSignTime(System.currentTimeMillis());
		asyncJob.setCompleteTime(System.currentTimeMillis());
		asyncJob.setCreateTime(new Date());
		asyncJob.setUpdateTime(new Date());
		asyncJobBO.insertAsyncJob(asyncJob);
	}

	@Test
	public void testGetAsyncJob() {
		Assert.assertNotNull(asyncJobBO.findAsyncJobs(new AsyncJobQueueDO()));
	}

	@Test
	public void testUpdateAsyncJob_FOR_DELETE() {
		AsyncJobQueueDO asyncJob = new AsyncJobQueueDO();
		asyncJob.setJobgroup("110");
		asyncJob.setJobname("3");
		asyncJobBO.deleteAsyncJobIfWaitNumIsZero(asyncJob);
	}

	@Test
	public void testUpdateAsyncJob_FOR_UPDATE() {
		AsyncJobQueueDO asyncJob = new AsyncJobQueueDO();
		asyncJob.setJobgroup("141");
		asyncJob.setJobname("httptask_cpmtrans_charge");
		asyncJobBO.deleteAsyncJobIfWaitNumIsZero(asyncJob);
	}

	@Test
	public void testUpdateAsyncJob_FOR_DATA_NOT_EXIST() {
		AsyncJobQueueDO asyncJob = new AsyncJobQueueDO();
		asyncJob.setJobgroup("145");
		asyncJob.setJobname("httptask_cpmtrans_charge");
		asyncJobBO.deleteAsyncJobIfWaitNumIsZero(asyncJob);
	}
}
