/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.urole.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.urole.IURoleDAO;
import com.taobao.ad.easyschedule.dataobject.URoleDO;

public class JPAURoleDAOImpl extends JPABaseDAO implements IURoleDAO {

	public Long insertURole(URoleDO uRole) {
		getJpaTemplate().persist(uRole);
		return uRole.getId();
	}

	public URoleDO getURoleById(Long id) {
		return this.getJpaTemplate().find(URoleDO.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<URoleDO> findURoles(URoleDO uRole) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.name", uRole.getName());
		map.put("t.descn", uRole.getDescn());
		uRole.setTotalItem(super.getQueryCount("select count(*) from es_u_role t", map));
		return super.queryForListIsNotEmpty("select t from es_u_role t", map, uRole, "order by t.id");
	}

	public List<URoleDO> findAllURoles(URoleDO uRole) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<URoleDO> findURoles(final String userName) {
		return (List<URoleDO>) super.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				List<URoleDO> list = null;
				List<Object> listArray = em
						.createNativeQuery(
								" select  r.id as id, r.descn as descn from es_u_user u,es_u_user_role ur, es_u_role r where u.username=?1 and u.id=ur.user_id and r.id=ur.role_id")
						.setParameter(1, userName).getResultList();
				for (int i = 0; i < listArray.size(); i++) {
					Object[] temp = (Object[]) listArray.get(i);
					URoleDO uRoleDO = new URoleDO();
					uRoleDO.setId(Long.valueOf(temp[0].toString()));
					uRoleDO.setDescn(temp[1].toString());
					list = new ArrayList<URoleDO>(listArray.size());
					list.add(uRoleDO);
				}
				return list;
			}
		});
	}

	public void deleteURoleById(final Long id) {
		URoleDO r = getURoleById(id);
		if (r != null) {
			getJpaTemplate().remove(r);
		}
	}

	public void updateURole(URoleDO uRole) {
		getJpaTemplate().merge(uRole);
	}

}