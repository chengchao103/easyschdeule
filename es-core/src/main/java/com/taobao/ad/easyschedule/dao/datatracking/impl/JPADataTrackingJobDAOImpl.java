/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.datatracking.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.datatracking.IDataTrackingJobDAO;

public class JPADataTrackingJobDAOImpl extends JPABaseDAO implements IDataTrackingJobDAO {

	public Double getDataTrackingData(final String trackingsql) {
		Object obj = this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em.createNativeQuery(trackingsql).getSingleResult();
			}
		});
		return new Double(obj.toString());
	}
}