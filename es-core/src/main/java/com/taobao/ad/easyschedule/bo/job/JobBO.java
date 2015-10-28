/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.job;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.JobDO;

/**
 * @author huangbaichuan.pt 2010 下午07:38:08
 */
@Transactional
public interface JobBO {
	public List<JobDO> jobList(JobDO jobDO);

	/**
	 * 获取当前总共存在的任务数
	 * 
	 * @return
	 */
	public Integer getCurrentJobNum() throws DataAccessException;

	/**
	 * 获取所有任务组|任务名称的字符串
	 * 
	 * @return
	 */
	public List<String> findAllJobGroupAndNames();
}
