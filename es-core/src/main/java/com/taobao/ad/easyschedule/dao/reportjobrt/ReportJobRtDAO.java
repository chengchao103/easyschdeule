/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.reportjobrt;

import java.util.Date;
import java.util.List;

import com.taobao.ad.easyschedule.dataobject.ReportJobRtDO;

/**
 * Job的详细RT报表DAO
 * 
 * @author bolin.hbc
 * 
 */
public interface ReportJobRtDAO {

	/**
	 * 插入Job的详细RT报表
	 * 
	 * @param reportJobRt
	 */
	public void insertReportJobRt(ReportJobRtDO reportJobRt);

	/**
	 * 根据开始时间和结束时间查询相关的详细报表
	 * 
	 * @param reportJobRt
	 * @return
	 */
	public List<ReportJobRtDO> findReportJobRt(ReportJobRtDO reportJobRt);
	
	/**
	 * 获取最近一条任务
	 * 
	 * 
	 * @param jobGroup
	 * @param jobName
	 * @return
	 */
	public ReportJobRtDO getLastReportJobRt(String jobGroup,String jobName);
	
	/**
	 * 更新表
	 * 
	 * @param reportJobRt
	 */
	public void updateReportJobRt(ReportJobRtDO reportJobRt);
	
	/**
	 * 根据startTime,jobName,jobGroup获取单个数据
	 * 
	 * @param reportJobRt
	 * @return
	 */
	public ReportJobRtDO getReportJobRt(ReportJobRtDO reportJobRt);
	
	/**
	 * 根据同步/异步状态,任务完成状态获取总数
	 * 
	 * @param sync
	 * @param status
	 * @return
	 */
	public Integer getSyncAndStatusCount(String sync,int status);
	
	/**
	 * 根据开始时间和结束时间查询相关的分页详细报表
	 * 
	 * @param reportJobRt
	 * @return
	 */
	public List<ReportJobRtDO> findReportJobRtPageList(ReportJobRtDO reportJobRt);
	
	/**
	 * 根据状态,开始时间,结束时间获取总数
	 * 
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getCount(int status,Date startTime,Date endTime);
	
	/**
	 * 根据开始时间和结束时间获取平均RT
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double getAverageRt(Date startTime,Date endTime);
}
