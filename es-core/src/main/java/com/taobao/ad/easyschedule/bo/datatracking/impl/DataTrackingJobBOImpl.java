/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.datatracking.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.bo.datatracking.IDataTrackingJobBO;
import com.taobao.ad.easyschedule.dao.datatracking.IDataTrackingJobDAO;

public class DataTrackingJobBOImpl implements IDataTrackingJobBO {

	final static Logger logger = LoggerFactory.getLogger(DataTrackingJobBOImpl.class);

	private IDataTrackingJobDAO dataTrackingJobDAO;

	@Override
	public Double getDataTrackingData(String trackingsql) {
		return dataTrackingJobDAO.getDataTrackingData(trackingsql);
	}

	public IDataTrackingJobDAO getDataTrackingJobDAO() {
		return dataTrackingJobDAO;
	}

	public void setDataTrackingJobDAO(IDataTrackingJobDAO dataTrackingJobDAO) {
		this.dataTrackingJobDAO = dataTrackingJobDAO;
	}
}