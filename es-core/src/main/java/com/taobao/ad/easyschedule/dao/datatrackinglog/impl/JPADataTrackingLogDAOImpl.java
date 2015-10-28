/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.datatrackinglog.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.datatrackinglog.IDataTrackingLogDAO;
import com.taobao.ad.easyschedule.dataobject.DatatrackingLogDO;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-8-13 下午02:43:05 类说明:
 */

public class JPADataTrackingLogDAOImpl extends JPABaseDAO implements IDataTrackingLogDAO {

	public void insertDataTrackingLog(DatatrackingLogDO datatrackingLogDO) {
		datatrackingLogDO.setCreateTime(new Date());
		getJpaTemplate().persist(datatrackingLogDO);
	}

	@SuppressWarnings("unchecked")
	public List<DatatrackingLogDO> getDataTrackingByGroupAndName(final DatatrackingLogDO datatrackingLog) throws DataAccessException {

		return (List<DatatrackingLogDO>) this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em
						.createQuery(
								"select t from es_datatracking_log t where t.jobName=?1 and t.jobGroup=?2  and t.createTime>=?3 and t.createTime<=?4 order by t.createTime desc")
						.setParameter(1, datatrackingLog.getJobName()).setParameter(2, datatrackingLog.getJobGroup())
						.setParameter(3, datatrackingLog.getQueryStartTime()).setParameter(4, datatrackingLog.getQueryEndTime());
				List<DatatrackingLogDO> dataList = query.getResultList();
				if (dataList != null && !dataList.isEmpty()) {
					Collections.sort(dataList, new Comparator<DatatrackingLogDO>() {
						@Override
						public int compare(DatatrackingLogDO o1, DatatrackingLogDO o2) {
							return o1.getCreateTime().compareTo(o2.getCreateTime());
						}
					});
				}
				return dataList;
			}
		});

	}

}
