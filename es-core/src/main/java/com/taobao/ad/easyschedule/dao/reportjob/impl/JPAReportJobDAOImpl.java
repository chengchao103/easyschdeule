/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.reportjob.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dao.reportjob.ReportJobDAO;
import com.taobao.ad.easyschedule.dataobject.ReportJobDO;

/**
 * Job的天维度报表DAOImpl
 * 
 * @author bolin.hbc
 * 
 */
public class JPAReportJobDAOImpl extends JPABaseDAO implements ReportJobDAO {

	@Override
	public void insertReportJob(ReportJobDO reportJob) {
		reportJob.setStatus(Constants.STATUS_YES);
		reportJob.setCreateTime(new Date());
		super.getJpaTemplate().persist(reportJob);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportJobDO> findReportJob(final ReportJobDO reportJob) {
		return (List<ReportJobDO>) super.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {

				return em.createQuery("select t from es_report_job t where  t.reportTime<=?1 and t.reportTime >=?2 order by t.reportTime ")
						.setParameter(1, reportJob.getEndTime()).setParameter(2, reportJob.getStartTime())
						.getResultList();

			}
		});

	}

	@Override
	public ReportJobDO getReportJobByTime(Date reportTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.reportTime", reportTime);
		return (ReportJobDO) super.executeSingleIsNotEmpty("select t from es_report_job t", map);
	}

	@Override
	public void updateReportJob(ReportJobDO reportJob) {
		this.getJpaTemplate().merge(reportJob);
	}

}
