/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.uuser.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.commons.utils.Md5Encrypt;
import com.taobao.ad.easyschedule.dao.uuser.IUUserDAO;
import com.taobao.ad.easyschedule.dataobject.UUserDO;
import com.taobao.ad.easyschedule.dataobject.UUserXGroupDO;
import com.taobao.ad.easyschedule.dataobject.UUserXRoleDO;

public class UUserBOImpl implements IUUserBO {

	private IUUserDAO userDAO;

	public void insertUUser(UUserDO uUser) {
		userDAO.insertUUser(uUser);
	}

	@Transactional
	public void insertUUser(UUserDO uUser, Long[] roles, Long[] groups) {
		Long userid = userDAO.insertUUser(uUser);
		if (roles != null && roles.length > 0) {
			userDAO.deleteUUserXRoleByUserId(userid);
			for (int i = 0; i < roles.length; i++) {
				userDAO.insertUUserXRole(userid, roles[i]);
			}
		}
		if (groups != null && groups.length > 0) {
			userDAO.deleteUUserXGroupByUserId(userid);
			for (int i = 0; i < groups.length; i++) {
				userDAO.insertUUserXGroup(userid, groups[i]);
			}
		}
	}

	public UUserDO getUUserById(Long id) {
		return userDAO.getUUserById(id);
	}

	public UUserDO getUUserByUsername(String username) {
		return userDAO.getUUserByUsername(username);
	}

	public List<UUserDO> findUUsers(UUserDO uUser) {
		return userDAO.findUUsers(uUser);
	}

	public void deleteUUserById(Long id) {
		userDAO.deleteUUserXRoleByUserId(id);
		userDAO.deleteUUserById(id);
	}

	public void updateUUser(UUserDO uUser) {
		userDAO.updateUUser(uUser);
	}

	@Transactional
	public void updateUUser(UUserDO uUser, Long[] roles, Long[] groups) {
		userDAO.updateUUser(uUser);
		if (roles != null && roles.length > 0) {
			userDAO.deleteUUserXRoleByUserId(uUser.getId());
			for (int i = 0; i < roles.length; i++) {
				userDAO.insertUUserXRole(uUser.getId(), roles[i]);
			}
		}
		if (groups != null && groups.length > 0) {
			userDAO.deleteUUserXGroupByUserId(uUser.getId());
			for (int i = 0; i < groups.length; i++) {
				userDAO.insertUUserXGroup(uUser.getId(), groups[i]);
			}
		}
	}

	public void updateUUserStatus(UUserDO uUser) {
		userDAO.updateUUserStatus(uUser);
	}

	public void setUserDAO(IUUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public List<UUserXRoleDO> findUUserXRoles(Long userid) {
		UUserXRoleDO uUserXRole = new UUserXRoleDO();
		uUserXRole.setUserId(userid);
		return userDAO.findUUserXRoles(uUserXRole);
	}

	public List<UUserXGroupDO> findUUserXGroups(Long userid) {
		UUserXGroupDO uUserXGroup = new UUserXGroupDO();
		uUserXGroup.setUserId(userid);
		return userDAO.findUUserXGroups(uUserXGroup);
	}

	@Override
	public UUserDO checkLogin(String name, String passwd) {
		UUserDO userDO = getUUserByUsername(name);
		if (userDO == null || !Md5Encrypt.md5(passwd).equalsIgnoreCase(userDO.getPassword())) {
			return null;
		}
		return userDO;
	}

}