/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.datatrackinglog;

import java.util.List;

import org.quartz.JobDetail;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.DatatrackingLogDO;
import com.taobao.ad.easyschedule.dataobject.JobResult;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-8-13 下午02:37:55 类说明:DATTRACKINGJOB BO业务处理类
 */
@Transactional
public interface IDataTrackingLogBO {

	/**
	 * 插入一条datatracking数据
	 * 
	 * @param datatrackingLogDO
	 */
	public void insertDataTrackingLog(DatatrackingLogDO datatrackingLogDO);

	/**
	 * 获取数据监控日志
	 * 
	 * @param datatrackingLogDO
	 * @return
	 */
	public List<DatatrackingLogDO> getDataTrackingByGroupAndName(DatatrackingLogDO datatrackingLogDO);

	/**
	 * 处理数据监控日志
	 * 
	 * @param jobDetail
	 * @param jobResult
	 */
	public JobResult processDataTrackingLog(JobDetail jobDetail, JobResult jobResult);
}
