/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.taobao.ad.easyschedule.base.BaseAction;
import com.taobao.ad.easyschedule.base.CheckMobile;
import com.taobao.ad.easyschedule.bo.config.IConfigBO;
import com.taobao.ad.easyschedule.bo.urole.IURoleBO;
import com.taobao.ad.easyschedule.bo.uuser.IUUserBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.commons.utils.Md5Encrypt;
import com.taobao.ad.easyschedule.dataobject.ConfigDO;
import com.taobao.ad.easyschedule.dataobject.URoleDO;
import com.taobao.ad.easyschedule.dataobject.UUserDO;
import com.taobao.ad.easyschedule.security.ActionSecurityBean;

/**
 * 登录管理Action
 * 
 * @author baimei
 * 
 */
public class AdminAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Integer adminId;
	private String adminName;
	private String adminPassword;
	private ActionSecurityBean securityBean;
	private List<ActionSecurityBean.MenuItem> menu;
	private List<URoleDO> roles;
	private IURoleBO roleBO;
	private IConfigBO configBO;
	private String topNotifyMsg;
	private String operName;
	private String debug;
    private IUUserBO userBO;

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * 进入登陆界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminToLogin() throws Exception {return SUCCESS;}

	/**
	 * 显示顶部
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewTop() throws Exception {
		operName = getNickname();
		roles = (List<URoleDO>) roleBO.findURoles(getLogname());
		String notify = configBO.getStringValue(ConfigDO.CONFIG_KEY_es_TOP_NOTIFY);
		if ("1".equals(debug) || (notify != null && "true".equals(notify))) {
			ConfigDO config = configBO.getConfigById(ConfigDO.CONFIG_KEY_es_TOP_NOTIFY_MSG);
			if (config != null) {
				topNotifyMsg = config.getConfigvalue() + config.getDescription();
			}
		}
		return SUCCESS;
	}

	/**
	 * 显示菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewMenu() throws Exception {
		return SUCCESS;
	}

	/**
	 * 显示中间条
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewMiddle() throws Exception {
		return SUCCESS;
	}

	/**
	 * 登陆系统
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminLogin() throws Exception {

		if (StringUtils.isBlank(adminName) || StringUtils.isBlank(adminPassword)) {
			return ERROR;
		}

		UUserDO user = userBO.getUUserByUsername(adminName);
		if (user != null && user.getPassword().equals(Md5Encrypt.md5(adminPassword))) {
			this.session.put(Constants.NICKNAME, user.getDescn());
			this.session.put(Constants.LOGNAME, user.getUsername());
		}
		return SUCCESS;
	}

	/**
	 * 退出系统
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminLogout() throws Exception {

        this.session.remove(Constants.NICKNAME);
        this.session.remove(Constants.LOGNAME);
		return SUCCESS;
	}

	public String index() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (CheckMobile.checkMobile(request.getHeader("user-agent"))) {
			return "mobile";
		}
		return SUCCESS;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public ActionSecurityBean getSecurityBean() {
		return securityBean;
	}

	public void setSecurityBean(ActionSecurityBean securityBean) {
		this.securityBean = securityBean;
	}

	public List<ActionSecurityBean.MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(List<ActionSecurityBean.MenuItem> menu) {
		this.menu = menu;
	}

	public List<URoleDO> getRoles() {
		return roles;
	}

	public void setRoles(List<URoleDO> roles) {
		this.roles = roles;
	}

	public IURoleBO getRoleBO() {
		return roleBO;
	}

	public void setRoleBO(IURoleBO roleBO) {
		this.roleBO = roleBO;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public void setConfigBO(IConfigBO configBO) {
		this.configBO = configBO;
	}

	public String getTopNotifyMsg() {
		return topNotifyMsg;
	}

	public void setTopNotifyMsg(String topNotifyMsg) {
		this.topNotifyMsg = topNotifyMsg;
	}

	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	public void setUserBO(IUUserBO userBO) {
		this.userBO = userBO;
	}
	
}
