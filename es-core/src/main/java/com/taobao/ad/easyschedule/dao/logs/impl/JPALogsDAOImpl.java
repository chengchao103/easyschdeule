/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.logs.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;

import org.springframework.orm.jpa.JpaCallback;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.logs.ILogsDAO;
import com.taobao.ad.easyschedule.dataobject.LogsDO;

@SuppressWarnings("unchecked")
public class JPALogsDAOImpl extends JPABaseDAO implements ILogsDAO {

	public void insertLogs(LogsDO logs) {
		// logs.setOptime(new Date());
		getJpaTemplate().persist(logs);
	}

	public LogsDO getLogsById(Long id) {
		return getJpaTemplate().find(LogsDO.class, id);
	}

	public List<LogsDO> findLogss(LogsDO logs) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.opuser", logs.getOpuser());
		map.put("t.optime", logs.getOptime());
		map.put("t.opdetail", logs.getOpdetail());
		map.put("t.opsubname", logs.getOpsubname());
		map.put("t.opsubtype", logs.getOpsubtype());
		map.put("t.opname", logs.getOpname());
		map.put("t.optype", logs.getOptype());
		logs.setTotalItem(super.getQueryCount("select count(*) from es_logs t", map));
		return super.queryForListIsNotEmpty("select t from es_logs t", map, logs, "order by ID desc");
	}

	public List<LogsDO> findAllLogss(LogsDO logs) {
		return null;
	}

	public void deleteLogsById(final Long id) {
		LogsDO logs = getLogsById(id);
		if (logs != null) {
			getJpaTemplate().remove(logs);
		}
	}

	public void deleteAllLogs() {
		this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em.createQuery("delete from es_logs t where 1=1").executeUpdate();
			}
		});
	}

	public void updateLogs(LogsDO logs) {
		logs.setOptime(new Date());
		getJpaTemplate().merge(logs);
	}

	@Override
	public LogsDO getLastStartLog(final String jobGroup, final String jobName) {
		return (LogsDO) this.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em.createQuery("select t from es_logs t where t.opsubname=?1 and  t.opsubtype=?2  order by t.optime desc")
						.setParameter(1, jobGroup + "|" + jobName).setParameter(2, LogsDO.SUBTYPE_JOB_START).setMaxResults(1).getSingleResult();
			}
		});

	}

	@Override
	public Long getCountByTypeAndTime(final Long opsubtype, final Date startTime, final Date endTime) {
		return (Long) super.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em.createQuery("select count(*) from es_logs t where  t.optime<=?1 and t.optime >=?2 and t.opsubtype=?3")
						.setParameter(1, endTime, TemporalType.DATE).setParameter(2, startTime, TemporalType.DATE).setParameter(3, opsubtype).getSingleResult();

			}
		});

	}

}