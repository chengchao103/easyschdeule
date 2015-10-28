/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.urole.impl;

import java.util.List;
import com.taobao.ad.easyschedule.dataobject.URoleDO;
import com.taobao.ad.easyschedule.dao.urole.IURoleDAO;
import com.taobao.ad.easyschedule.bo.urole.IURoleBO;

public class URoleBOImpl implements IURoleBO {

	private IURoleDAO roleDAO;

	public Long insertURole(URoleDO uRole) {
		return roleDAO.insertURole(uRole);
	}

	public URoleDO getURoleById(Long id) {
		return roleDAO.getURoleById(id);
	}

	public List<URoleDO> findURoles(URoleDO uRole) {
		return roleDAO.findURoles(uRole);
	}

	public void deleteURoleById(Long id) {
		roleDAO.deleteURoleById(id);
	}

	public void updateURole(URoleDO uRole) {
		roleDAO.updateURole(uRole);
	}

	public List<URoleDO> findURoles(String userName) {
		return roleDAO.findURoles(userName);
	}

	public void setRoleDAO(IURoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
}