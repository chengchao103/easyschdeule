/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

@SuppressWarnings("rawtypes")
public abstract class JPABaseDAO extends JpaDaoSupport {

	public List queryForListIsNotEmpty(String queryString, final Map<String, Object> map, final PageInfo pageInfo, String orderBy) {
		final List<String> keyList = getKeyList(map);
		final String queryStringCallBack = getQueryString(queryString, keyList, orderBy);
		return (List) this.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public List doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(queryStringCallBack);
				for (int i = 0; i < keyList.size(); i++) {
					query.setParameter((i + 1), map.get(keyList.get(i)));
				}
				// query.setFirstResult(((pageInfo.getToPage() == 0 ? 1 :
				// pageInfo.getToPage()) - 1) * pageInfo.getPerPageSize());
				query.setFirstResult(pageInfo.getStartRow());
				query.setMaxResults(pageInfo.getPerPageSize());
				return query.getResultList();
			}
		});
	}

	private String getQueryString(String queryString, List<String> keyList, String orderBy) {
		StringBuilder value = new StringBuilder();
		for (int i = 0; i < keyList.size(); i++) {
			if (i == keyList.size() - 1) {
				value.append(keyList.get(i)).append("=?").append((i + 1));
			} else {
				value.append(keyList.get(i)).append("=?").append((i + 1)).append(" and ");
			}
		}
		String w = "";
		if (!keyList.isEmpty()) {
			w = queryString.indexOf(" where ") > -1 ? " and " : " where ";
		}
		if (!keyList.isEmpty()) {
			queryString = queryString + w + value.toString();
		}
		return queryString + (orderBy == null ? " " : " " + orderBy);

	}

	private List<String> getKeyList(Map<String, Object> map) {

		if (map == null) {
			map = new HashMap<String, Object>();
		}
		Iterator<String> iterator = map.keySet().iterator();
		final List<String> keyList = new ArrayList<String>();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object obj = map.get(key);
			if (obj instanceof String) {
				if (StringUtils.isNotBlank(obj.toString())) {
					keyList.add(key);
				}
			} else if (obj != null) {
				keyList.add(key);
			}
		}
		return keyList;
	}

	public int getQueryCount(String queryString, final Map<String, Object> map) {
		final List<String> keyList = getKeyList(map);
		final String queryStringCallBack = getQueryString(queryString, keyList, null);
		return (Integer) this.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Integer doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(queryStringCallBack);
				for (int i = 0; i < keyList.size(); i++) {
					query.setParameter((i + 1), map.get(keyList.get(i)));
				}
				return ((Long) query.getSingleResult()).intValue();
			}
		});
	}

	public List executeQueryIsNotEmpty(String queryString, final Map<String, Object> map) {
		return executeQueryIsNotEmpty(queryString, map, "");
	}

	public List executeQueryIsNotEmpty(String queryString, final Map<String, Object> map, String orderBy) {

		final List<String> keyList = getKeyList(map);
		final String queryStringCallBack = getQueryString(queryString, keyList, orderBy);
		return (List) this.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public List doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(queryStringCallBack);
				for (int i = 0; i < keyList.size(); i++) {
					query.setParameter((i + 1), map.get(keyList.get(i)));
				}
				return query.getResultList();
			}
		});
	}

	public Object executeSingleIsNotEmpty(String queryString, final Map<String, Object> map) {
		return executeSingleIsNotEmpty(queryString, map, null);
	}

	public Object executeSingleIsNotEmpty(String queryString, final Map<String, Object> map, String orderBy) {

		final List<String> keyList = getKeyList(map);
		final String queryStringCallBack = getQueryString(queryString, keyList, orderBy);
		return this.getJpaTemplate().execute(new JpaCallback() {

			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(queryStringCallBack);
				for (int i = 0; i < keyList.size(); i++) {
					query.setParameter((i + 1), map.get(keyList.get(i)));
				}
				query.setMaxResults(1);
				try {
					return query.getSingleResult();
				} catch (Exception e) {
					return null;
				}
			}
		});

	}

}