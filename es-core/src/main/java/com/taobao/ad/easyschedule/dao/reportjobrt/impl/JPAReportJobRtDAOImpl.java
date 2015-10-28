/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.reportjobrt.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dao.reportjobrt.ReportJobRtDAO;
import com.taobao.ad.easyschedule.dataobject.ReportJobRtDO;

/**
 * Job的详细RT报表DAOImpl
 * 
 * @author bolin.hbc
 * 
 */
public class JPAReportJobRtDAOImpl extends JPABaseDAO implements ReportJobRtDAO {

	@Override
	public void insertReportJobRt(ReportJobRtDO reportJobRt) {
		reportJobRt.setCreateTime(new Date());
		reportJobRt.setUpdateTime(new Date());
		reportJobRt.setStatus(Constants.STATUS_YES);
		getJpaTemplate().persist(reportJobRt);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportJobRtDO> findReportJobRt(final ReportJobRtDO reportJobRt) {

		return (List<ReportJobRtDO>) super.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {

				return em.createQuery("select t from es_report_job_rt t where  t.createTime<=?1 and t.createTime >=?2")
						.setParameter(1, reportJobRt.getQueryEndTime()).setParameter(2, reportJobRt.getQueryStartTime())
						.getResultList();

			}
		});

	}

	@Override
	public ReportJobRtDO getLastReportJobRt(String jobGroup, String jobName) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobName", jobName);
		map.put("t.jobGroup", jobGroup);
		return (ReportJobRtDO) super.executeSingleIsNotEmpty("select t from es_report_job_rt t", map, "order by t.createTime desc");

	}

	@Override
	public void updateReportJobRt(ReportJobRtDO reportJobRt) {
		reportJobRt.setUpdateTime(new Date());
		super.getJpaTemplate().merge(reportJobRt);

	}

	@Override
	public ReportJobRtDO getReportJobRt(ReportJobRtDO reportJobRt) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.jobName", reportJobRt.getJobName());
		map.put("t.jobGroup", reportJobRt.getJobGroup());
		map.put("t.startTime", reportJobRt.getStartTime());
		return (ReportJobRtDO) super.executeSingleIsNotEmpty("select t from es_report_job_rt t", map);
	}

	@Override
	public Integer getSyncAndStatusCount(String sync, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.sync", sync);
		map.put("t.status", status);
		return super.getQueryCount("select count(*) from es_report_job_rt t", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportJobRtDO> findReportJobRtPageList(final ReportJobRtDO reportJobRt) {
		reportJobRt.setTotalItem(((BigInteger) this.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {

				return em
						.createNativeQuery(
								"select count(distinct t.JOB_NAME ,t.JOB_GROUP) from  es_report_job_rt t where t.create_time<=?1 and t.create_time>=?2")
						.setParameter(1, reportJobRt.getQueryEndTime()).setParameter(2, reportJobRt.getQueryStartTime()).getSingleResult();

			}
		})).intValue());

		List<Object[]> result = (List<Object[]>) super.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em
						.createNativeQuery(
								"SELECT *,a.successNum*100/a.totalNum AS successRate FROM ( SELECT  AVG(rt) AS rt,COUNT(*) AS totalNum,job_group,job_name,COUNT(CASE WHEN STATUS = 2 THEN '1' ELSE NULL END) AS successNum, COUNT(CASE WHEN STATUS > 2 THEN 'x' ELSE NULL END) AS errorNum FROM es_report_job_rt where create_time<=?1 and create_time>=?2 GROUP BY job_name,job_group  ) a ORDER BY a.job_group,successRate "
										+ (reportJobRt.isSuccessRateOrderBy() ? "desc" : "")).setParameter(1, reportJobRt.getQueryEndTime())
						.setParameter(2, reportJobRt.getQueryStartTime()).setFirstResult(reportJobRt.getStartRow())
						.setMaxResults(reportJobRt.getPerPageSize()).getResultList();
			}
		});

		List<ReportJobRtDO> rtList = new ArrayList<ReportJobRtDO>(result.size());

		for (Object[] o : result) {
			ReportJobRtDO rt = new ReportJobRtDO();
			if (o[0] != null) {
				rt.setShowRt(new BigDecimal(o[0].toString()).setScale(2, RoundingMode.HALF_DOWN));
			}
			rt.setTotalNum(((BigInteger) o[1]).intValue());
			rt.setJobGroup(String.valueOf(o[2]));
			rt.setJobName(String.valueOf(o[3]));
			rt.setSuccessNum(Integer.valueOf(o[4].toString()));
			rt.setErrorNum(Integer.valueOf(o[5].toString()));
			rt.setSuccessRate(new BigDecimal(o[6].toString()).setScale(2, RoundingMode.HALF_DOWN));
			rt.setErrorRate(new BigDecimal(100).subtract(rt.getSuccessRate()).setScale(2, RoundingMode.HALF_DOWN));
			rtList.add(rt);

		}
		return rtList;

	}

	@Override
	public Double getAverageRt(final Date startTime, final Date endTime) {
		Double avg = (Double) super.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em.createQuery("select avg(t.rt) from es_report_job_rt t where  t.createTime<=?1 and t.createTime >?2")
						.setParameter(1, endTime).setParameter(2, startTime).getSingleResult();

			}
		});

		return avg == null ? 0 : avg;
	}

	@Override
	public Integer getCount(final int status, final Date startTime, final Date endTime) {
		Long count = (Long) super.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em.createQuery("select count(*) from es_report_job_rt t where  t.createTime<=?1 and t.createTime >?2 and t.status=?3")
						.setParameter(1, endTime).setParameter(2, startTime).setParameter(3, status).getSingleResult();

			}
		});
		return count == null ? 0 : count.intValue();
	}

}
