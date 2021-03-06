/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.logs;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.LogsDO;

@Transactional
public interface ILogsBO {

	public void insertLogs(LogsDO logs);

	/**
	 * 插入成功日志
	 * 
	 * @param opsubtype
	 *            操作类型
	 * @param opsubname
	 *            任务名称
	 * @param opdetail
	 *            任务详情
	 * @param opuser
	 *            操作人
	 * @return
	 */
	public void insertSuccess(long opsubtype, String opsubname, String opdetail, String opuser);

	/**
	 * 插入失败日志
	 * 
	 * @param opsubtype
	 *            操作类型
	 * @param opsubname
	 *            任务名称
	 * @param opdetail
	 *            任务详情
	 * @param opuser
	 *            操作人
	 * @return
	 */
	public void insertFail(long opsubtype, String opsubname, String opdetail, String opuser);

	/**
	 * 插入警告日志
	 * 
	 * @param opsubtype
	 *            操作类型
	 * @param opsubname
	 *            任务名称
	 * @param opdetail
	 *            任务详情
	 * @param opuser
	 *            操作人
	 * @return
	 */
	public void insertWarning(long opsubtype, String opsubname, String opdetail, String opuser);

	public LogsDO getLogsById(Long id);

	public List<LogsDO> findLogss(LogsDO logs);

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