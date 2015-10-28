/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.uresc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.uresc.IURescDAO;
import com.taobao.ad.easyschedule.dataobject.URescDO;

public class JPAURescDAOImpl extends JPABaseDAO implements IURescDAO {

	public Long insertUResc(URescDO uResc) {
		getJpaTemplate().persist(uResc);
		return uResc.getId();
	}

	public URescDO getURescById(Long id) {
		return this.getJpaTemplate().find(URescDO.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<URescDO> findURescs(URescDO uResc) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.name", uResc.getName());
		map.put("t.resType", uResc.getResType());
		map.put("t.resString", uResc.getResString());
		map.put("t.descn", uResc.getDescn());
		map.put("t.createTime", uResc.getCreateTime());
		map.put("t.updateTime", uResc.getUpdateTime());
		uResc.setTotalItem(super.getQueryCount("select count(*) from es_u_resc t", map));
		return super.queryForListIsNotEmpty("select t from es_u_resc t", map, uResc, "order by ID desc ");
	}

	// 查出角色ID List add(All);
	// 根据角色ID获取角色权限ID
	public List<URescDO> findAllURescs(URescDO uResc) {
		return null;
	}

	public void deleteURescById(final Long id) {
		URescDO r = getURescById(id);
		if (r != null) {
			getJpaTemplate().remove(r);
		}
	}

	public void updateUResc(URescDO uResc) {
		getJpaTemplate().merge(uResc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findAllUserRescs(final String userName) {
		return (List<Integer>) super.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em
						.createNativeQuery(
								"SELECT  rr.resc_id from es_u_resc_role rr where rr.role_id in ( select r.id from es_u_user u, es_u_user_role ur, es_u_role r where u.username=?1 and u.id=ur.user_id and r.id=ur.role_id)")
						.setParameter(1, userName).getResultList();

			}
		});
	}
}