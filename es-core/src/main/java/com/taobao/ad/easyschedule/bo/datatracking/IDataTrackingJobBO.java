/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.datatracking;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IDataTrackingJobBO {

	/**
	 * 查询一个监控数据
	 * 
	 * @param trackingsql
	 * @return
	 */
	public Double getDataTrackingData(String trackingsql);

}