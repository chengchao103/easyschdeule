/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.logs;

import com.taobao.ad.easyschedule.dataobject.LogsDO;

import java.util.Date;
import java.util.List;

public interface ILogsDAO{
	
	public void insertLogs(LogsDO logs);

	public LogsDO getLogsById(Long id);

	public List<LogsDO> findLogss(LogsDO logs);

	public List<LogsDO> findAllLogss(LogsDO logs);

	public void deleteLogsById(Long id);

	public void deleteAllLogs();

	public void updateLogs(LogsDO logs);
	
	/**
	 * 获取最近的这条任务开始信息
	 * 
	 * @param jobGroup
	 * @param jobName
	 * @return
	 */
	public LogsDO getLastStartLog(String jobGroup,String jobName);
	
	
	/**
	 * 获取某段时间内任务的某个状态下的数目
	 * 
	 * 
	 * @param opsubtype
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Long getCountByTypeAndTime(Long opsubtype,Date startTime,Date endTime);
	
}