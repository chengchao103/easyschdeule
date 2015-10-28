/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.uuser.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.uuser.IUUserDAO;
import com.taobao.ad.easyschedule.dataobject.UUserDO;
import com.taobao.ad.easyschedule.dataobject.UUserXGroupDO;
import com.taobao.ad.easyschedule.dataobject.UUserXRoleDO;

@SuppressWarnings("unchecked")
public class JPAUUserDAOImpl extends JPABaseDAO implements IUUserDAO {

	public Long insertUUser(UUserDO uUser) {
		uUser.setCreateTime(new Date());
		uUser.setUpdateTime(new Date());
		getJpaTemplate().persist(uUser);
		return uUser.getId();
	}

	public UUserDO getUUserById(Long id) {
		return getJpaTemplate().find(UUserDO.class, id);
	}

	public UUserDO getUUserByUsername(final String username) {
		List<UUserDO> list = getJpaTemplate().find("select t from es_u_user t where t.username=?", username);
		return list.isEmpty() ? null : (UUserDO) list.get(0);
	}

	public List<UUserDO> findUUsers(UUserDO uUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.username", uUser.getUsername());
		map.put("t.password", uUser.getPassword());
		map.put("t.status", uUser.getStatus());
		map.put("t.createtime", uUser.getCreateTime());
		map.put("t.updatetime", uUser.getUpdateTime());
		map.put("t.descn", uUser.getDescn());
		uUser.setTotalItem(super.getQueryCount("select count(*) from es_u_user t", map));
		return super.queryForListIsNotEmpty("select t from es_u_user t", map, uUser, "order by t.id	desc");
	}

	public List<UUserDO> findAllUUsers(UUserDO uUser) {
		return null;
	}

	public void deleteUUserById(Long id) {
		UUserDO r = getUUserById(id);
		if (r != null) {
			getJpaTemplate().remove(r);
		}
	}

	public void updateUUser(UUserDO uUser) {
		UUserDO user = getUUserById(uUser.getId());
		user.setUpdateTime(new Date());
		if (uUser.getDescn() != null) {
			user.setDescn(uUser.getDescn());
		}
		if (uUser.getPassword() != null) {
			user.setPassword(uUser.getPassword());
		}
		if (uUser.getStatus() != null) {
			user.setStatus(uUser.getStatus());
		}
		if (uUser.getMobile() != null) {
			user.setMobile(uUser.getMobile());
		}
		if (uUser.getEmail() != null) {
			user.setEmail(uUser.getEmail());
		}
		getJpaTemplate().merge(user);
	}

	public void updateUUserStatus(UUserDO uUser) {
		UUserDO user = getUUserById(uUser.getId());
		user.setStatus(uUser.getStatus());
		getJpaTemplate().merge(user);
	}

	public List<UUserXRoleDO> findUUserXRoles(final UUserXRoleDO uUserXRole) {
		if (uUserXRole.getUserId() == null) {
			return (List<UUserXRoleDO>) getJpaTemplate().find("select t from es_u_user_role t ");
		} else {
			return (List<UUserXRoleDO>) getJpaTemplate().find("select t from es_u_user_role t where t.userId=?1", uUserXRole.getUserId());
		}
	}

	public List<UUserXGroupDO> findUUserXGroups(final UUserXGroupDO uUserXGroup) {
		return (List<UUserXGroupDO>) getJpaTemplate().find("select t from es_u_user_group t where t.userId=?1", uUserXGroup.getUserId());
	}

	public void insertUUserXRole(Long userid, Long roleid) {
		UUserXRoleDO role = new UUserXRoleDO();
		role.setUserId(userid);
		role.setRoleId(roleid);
		getJpaTemplate().persist(role);
	}

	public void deleteUUserXRoleByUserId(final Long userid) {
		UUserXRoleDO r = new UUserXRoleDO();
		r.setUserId(userid);
		List<UUserXRoleDO> list = findUUserXRoles(r);
		for (UUserXRoleDO uUserXRoleDO : list) {
			getJpaTemplate().remove(uUserXRoleDO);
		}
	}

	public void insertUUserXGroup(Long userid, Long groupid) {
		UUserXGroupDO uUserXGroupDO = new UUserXGroupDO();
		uUserXGroupDO.setUserId(userid);
		uUserXGroupDO.setGroupId(groupid);
		getJpaTemplate().merge(uUserXGroupDO);
	}

	public void deleteUUserXGroupByUserId(final Long userid) {
		UUserXGroupDO r = new UUserXGroupDO();
		r.setUserId(userid);
		List<UUserXGroupDO> list = findUUserXGroups(r);
		for (UUserXGroupDO uUserXGroupDO : list) {
			getJpaTemplate().remove(uUserXGroupDO);
		}
	}
}