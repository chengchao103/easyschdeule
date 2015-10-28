/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.urole;

import com.taobao.ad.easyschedule.dataobject.URoleDO;
import com.taobao.ad.easyschedule.bo.urole.IURoleBO;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

public class URoleAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private URoleDO query;
	private URoleDO role;
	private IURoleBO roleBO;

	public String listRole() {
		if (query == null) {
			query = new URoleDO();
		}
		List<URoleDO> uRoles = roleBO.findURoles(query);
		ActionContext.getContext().put("uRoles", uRoles);
		return SUCCESS;
	}

	public String viewAddRole() {
		return SUCCESS;
	}

	public String addRole() {
		roleBO.insertURole(role);
		return SUCCESS;
	}

	public String viewUpdateRole() {
		role = roleBO.getURoleById(id);
		return SUCCESS;
	}

	public String updateRole() {
		URoleDO uRoleDO = this.roleBO.getURoleById(this.role.getId());
		if (role != null) {
			uRoleDO.setId(this.role.getId());
			uRoleDO.setName(this.role.getName());
			uRoleDO.setDescn(this.role.getDescn());
			this.roleBO.updateURole(uRoleDO);
		}
		return SUCCESS;
	}

	public String deleteRole() {
		this.roleBO.deleteURoleById(id);
		return SUCCESS;
	}

	public void setRoleBO(IURoleBO roleBO) {
		this.roleBO = roleBO;
	}

	public URoleDO getQuery() {
		return this.query;
	}

	public void setQuery(URoleDO uRole) {
		this.query = uRole;
	}

	public URoleDO getRole() {
		return this.role;
	}

	public void setRole(URoleDO role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}