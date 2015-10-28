/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.uuser;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.bo.logs.ILogsBO;
import com.taobao.ad.easyschedule.bo.urole.IURoleBO;
import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.Md5Encrypt;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.dataobject.LogsDO;
import com.taobao.ad.easyschedule.dataobject.URoleDO;
import com.taobao.ad.easyschedule.dataobject.UUserDO;
import com.taobao.ad.easyschedule.dataobject.UUserXRoleDO;
import com.taobao.ad.easyschedule.security.ActionSecurity;

public class UUserAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private UUserDO query;
	private UUserDO user;
	private IUUserBO userBO;
	private IURoleBO roleBO;
	private Long[] roles;
	private Long[] groups;
	private ILogsBO logsBO;
	private ICodeBO codeBO;
	private boolean isAdmin; //当前用户是否是管理员角色

	public String listUser() {
		ActionContext.getContext().put("curUserName", this.getLogname());

		URoleDO roleDO = new URoleDO();
		roleDO.setPerPageSize(100);
		List<URoleDO> allRoles = roleBO.findURoles(roleDO);
		ActionContext.getContext().put("uRoles", allRoles);
		ActionContext.getContext().put("userXRole", userBO.findUUserXRoles(id)); //ID为空,查询所有的角色
		UUserDO user = userBO.getUUserByUsername(getLogname());

		List<UUserXRoleDO> currentUserRole = userBO.findUUserXRoles(user.getId());

		for (UUserXRoleDO userRole : currentUserRole) {
			for (URoleDO role : allRoles) {

				if (role.getName().equals(Constants.ROLE_ADMIN) && role.getId().equals(userRole.getRoleId())) {
					isAdmin = true;
				}
			}
		}

		if (query == null) {
			query = new UUserDO();
		}
		List<UUserDO> uUsers = userBO.findUUsers(query);
		ActionContext.getContext().put("uUsers", uUsers);
		return SUCCESS;
	}

	public String viewAddUser() {
		URoleDO roleDO = new URoleDO();
		roleDO.setPerPageSize(100);
		ActionContext.getContext().put("uRoles", roleBO.findURoles(roleDO));
		ActionContext.getContext().put("uGroups", codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP));
		return SUCCESS;
	}

	@ActionSecurity(module = 11)
	public String addUser() {
		if (user != null && roles != null && roles.length > 0 ) {
			if (StringUtils.isNotEmpty(user.getPassword())) {
				user.setPassword(Md5Encrypt.md5(user.getPassword()));
			}
			user.setStatus(Long.valueOf(Constants.STATUS_YES));
			userBO.insertUUser(user, roles, groups);
			StringBuilder detail = new StringBuilder();
			detail.append("roles:").append(ArrayUtils.toString(roles));
			detail.append(";groups:").append(ArrayUtils.toString(groups));
			logsBO.insertSuccess(LogsDO.SUBTYPE_USER_ADD, user.getUsername() + "|" + (user.getDescn() == null ? "" : user.getDescn()), detail.toString(),
					getLogname());
		}
		return SUCCESS;
	}

	public String viewUpdateUser() {
		URoleDO roleDO = new URoleDO();
		roleDO.setPerPageSize(100);
		ActionContext.getContext().put("uRoles", roleBO.findURoles(new URoleDO()));
		ActionContext.getContext().put("uGroups", codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP));
		ActionContext.getContext().put("userXRole", userBO.findUUserXRoles(id));
		ActionContext.getContext().put("userXGroup", userBO.findUUserXGroups(id));

		ActionContext.getContext().put("curUserName", this.getLogname());
		user = userBO.getUUserById(id);
		user.setPassword(null);
		return SUCCESS;
	}

	@ActionSecurity(module = 29)
	public String updateUser() {
		UUserDO uUserDO = this.userBO.getUUserById(this.user.getId());
		if (user != null && roles != null && roles.length > 0) {
			uUserDO.setId(this.user.getId());
			uUserDO.setUsername(this.user.getUsername());
			uUserDO.setStatus(this.user.getStatus());
			uUserDO.setDescn(this.user.getDescn());
			uUserDO.setMobile(this.user.getMobile());
			uUserDO.setEmail(this.user.getEmail());
			uUserDO.setUpdateTime(null);
			this.userBO.updateUUser(uUserDO, roles, groups);
			StringBuilder detail = new StringBuilder();
			detail.append("roles:").append(ArrayUtils.toString(roles));
			detail.append(";groups:").append(ArrayUtils.toString(groups));
			logsBO.insertSuccess(LogsDO.SUBTYPE_USER_MOD, user.getUsername() + "|" + (user.getDescn() == null ? "" : user.getDescn()), detail.toString(),
					getLogname());
		}
		return SUCCESS;
	}

	public String viewUpdateCurrentUser() {
		ActionContext.getContext().put("curUserName", this.getLogname());
		if (id != null) {
			user = userBO.getUUserById(id);
			if (!user.getUsername().equals(this.getLogname())) {
				return ERROR;
			}
		} else {
			user = userBO.getUUserByUsername(this.getLogname());
		}
		user.setPassword(null);
		return SUCCESS;
	}

	public String updateCurrentUser() {
		// UserDetails userDetails = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UUserDO uUserDO = this.userBO.getUUserById(this.user.getId());
		if (user != null) {
			if (!uUserDO.getUsername().equals(this.getLogname())) {
				addActionError("您无法修改他人的密码");
				return ERROR;
			}
			uUserDO.setId(this.user.getId());
			uUserDO.setUsername(this.user.getUsername());
			uUserDO.setPassword(this.user.getPassword());
			uUserDO.setMobile(this.user.getMobile());
			uUserDO.setDescn(this.user.getDescn());
			uUserDO.setEmail(this.user.getEmail());
			if (StringUtils.isNotEmpty(uUserDO.getPassword())) {
				uUserDO.setPassword(Md5Encrypt.md5(uUserDO.getPassword()));
			}
			uUserDO.setUpdateTime(null);
			userBO.updateUUser(uUserDO);
			logsBO.insertSuccess(LogsDO.SUBTYPE_USER_MOD, "updateCurrentUser|" + user.getUsername() + "|" + (user.getDescn() == null ? "" : user.getDescn()),
					null, getLogname());
		}
		return SUCCESS;
	}

	@ActionSecurity(module = 12)
	public String deleteUser() {
		user = userBO.getUUserById(id);
		if (user != null) {
			this.userBO.deleteUUserById(id);
			logsBO.insertSuccess(LogsDO.SUBTYPE_USER_DEL, user.getUsername(), null, getLogname());
		}
		return SUCCESS;
	}

	public void setUserBO(IUUserBO userBO) {
		this.userBO = userBO;
	}

	public void setRoleBO(IURoleBO roleBO) {
		this.roleBO = roleBO;
	}

	public UUserDO getQuery() {
		return this.query;
	}

	public void setQuery(UUserDO uUser) {
		this.query = uUser;
	}

	public UUserDO getUser() {
		return this.user;
	}

	public void setUser(UUserDO user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getRoles() {
		return roles;
	}

	public void setRoles(Long[] roles) {
		this.roles = roles;
	}

	public void setLogsBO(ILogsBO logsBO) {
		this.logsBO = logsBO;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public Long[] getGroups() {
		return groups;
	}

	public void setGroups(Long[] groups) {
		this.groups = groups;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}