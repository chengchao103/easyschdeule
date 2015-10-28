/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.uuser;

import java.util.List;

import com.taobao.ad.easyschedule.dataobject.UUserDO;
import com.taobao.ad.easyschedule.dataobject.UUserXGroupDO;
import com.taobao.ad.easyschedule.dataobject.UUserXRoleDO;

public interface IUUserDAO {
	public Long insertUUser(UUserDO uUser);

	public UUserDO getUUserById(Long id);

	public UUserDO getUUserByUsername(String username);

	public List<UUserDO> findUUsers(UUserDO uUser);

	public List<UUserDO> findAllUUsers(UUserDO uUser);

	public void deleteUUserById(Long id);

	public void updateUUser(UUserDO uUser);

	public void updateUUserStatus(UUserDO uUser);

	public List<UUserXRoleDO> findUUserXRoles(UUserXRoleDO uUserXRole);

	public List<UUserXGroupDO> findUUserXGroups(UUserXGroupDO uUserXGroup);

	public void insertUUserXRole(Long userid, Long roleid);

	public void deleteUUserXRoleByUserId(Long userid);

	public void insertUUserXGroup(Long userid, Long Group);

	public void deleteUUserXGroupByUserId(Long userid);

}