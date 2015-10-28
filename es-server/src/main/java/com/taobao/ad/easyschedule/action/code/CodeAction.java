/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.code;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.commons.Constants;
import com.taobao.ad.easyschedule.dataobject.CodeDO;
import com.taobao.ad.easyschedule.security.ActionSecurity;

public class CodeAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private CodeDO query;
	private CodeDO code;
	private ICodeBO codeBO;
	private String action;
	private String error;

	public String listCode() {
		if (query == null) {
			query = new CodeDO();
		}
		List<CodeDO> codes = codeBO.findCodes(query);
		ActionContext.getContext().put("codes", codes);
		return SUCCESS;
	}

	public String viewAddCode() {
		action = "add";
		return SUCCESS;
	}

	@ActionSecurity(module = 21)
	public String addCode() {

		error = Constants.ERROR_STATUS.get(codeBO.insertCode(code));
		if (error == null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String viewUpdateCode() {
		code = codeBO.getCodeByKeyAndKeyCode(code);
		return SUCCESS;
	}

	@ActionSecurity(module = 23)
	public String updateCode() {
		codeBO.updateCode(code);
		return SUCCESS;
	}

	@ActionSecurity(module = 24)
	public String deleteCode() {
		codeBO.deleteCodeByKeyAndKeyCode(code);
		return SUCCESS;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public CodeDO getQuery() {
		return this.query;
	}

	public void setQuery(CodeDO code) {
		this.query = code;

	}

	public CodeDO getCode() {
		return this.code;
	}

	public void setCode(CodeDO code) {
		this.code = code;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}