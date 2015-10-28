/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.reportjob;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.ReportJobDO;

/**
 * Job的天维度报表 BO
 * 
 * @author bolin.hbc
 * 
 */
@Transactional
public interface ReportJobBO {

	/**
	 * 插入或更新Job的天威读报表,保证一天一天
	 * 
	 * @param reportJob
	 */
	public void saveOrUpdateReportJob(ReportJobDO reportJob);

	/**
	 * 根据开始时间和结束时间查询相关的详细报表
	 * 
	 * @param reportJob
	 * @return
	 */
	public List<ReportJobDO> findReportJob(ReportJobDO reportJob);

/*	*//**
	 * 周报发送
	 * 
	 * @param startTime
	 * @param endTime
	 * @return 错误信息
	 * @throws Exception
	 *//*
	public String sendReportMail(Date startTime,Date endTime)throws Exception;*/
	
}
