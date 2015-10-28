/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.job;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.taobao.ad.easyschedule.dataobject.JobDO;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-9-1 下午07:46:05 类说明:job的操作
 */

public interface JobDAO {
	
	/**
	 * 分页获取任务
	 * 
	 * @param jobDO
	 * @return
	 * @throws DataAccessException
	 */
	public List<JobDO> jobList(JobDO jobDO) throws DataAccessException;
	
	/**
	 * 获取当前总共存在的任务数
	 * 
	 * @return
	 */
	public Integer getCurrentJobNum() throws DataAccessException;
}
