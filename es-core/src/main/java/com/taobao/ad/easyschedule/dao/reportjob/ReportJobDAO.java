/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.reportjob;

import java.util.Date;
import java.util.List;

import com.taobao.ad.easyschedule.dataobject.ReportJobDO;

/**
 * Job的天维度报表DAO
 * 
 * @author bolin.hbc
 * 
 */
public interface ReportJobDAO {

	/**
	 * 插入Job的天威读报表
	 * 
	 * @param reportJob
	 */
	public void insertReportJob(ReportJobDO reportJob);
	
	
	/**
	 * 更新某一天的的报表
	 * 
	 * @param reportJob
	 */
	public void updateReportJob(ReportJobDO reportJob);

	/**
	 * 根据开始时间和结束时间查询相关的详细报表,创建时间降序排列
	 * 
	 * @param reportJob
	 * @return
	 */
	public List<ReportJobDO> findReportJob(ReportJobDO reportJob);

	/**
	 * 根据报表时间获取某一天的数据
	 * 
	 * @param reportTime
	 */
	public ReportJobDO getReportJobByTime(Date reportTime);

}
