/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.uresc;

import com.taobao.ad.easyschedule.dataobject.URescDO;
import com.taobao.ad.easyschedule.bo.uresc.IURescBO;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

public class URescAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private URescDO query;
	private URescDO resc;
	private IURescBO rescBO;

	public String listResc() {
		if (query == null) {
			query = new URescDO();
		}
		List<URescDO> uRescs = rescBO.findURescs(query);
		ActionContext.getContext().put("uRescs", uRescs);
		return SUCCESS;
	}

	public String viewAddResc() {
		return SUCCESS;
	}

	public String addResc() {
		rescBO.insertUResc(resc);
		return SUCCESS;
	}

	public String viewUpdateResc() {
		resc = rescBO.getURescById(id);
		return SUCCESS;
	}

	public String updateResc() {
		URescDO uRescDO = this.rescBO.getURescById(this.resc.getId());
		if (resc != null) {
			uRescDO.setId(this.resc.getId());
			uRescDO.setName(this.resc.getName());
			uRescDO.setResType(this.resc.getResType());
			uRescDO.setResString(this.resc.getResString());
			uRescDO.setPriority(this.resc.getPriority());
			uRescDO.setDescn(this.resc.getDescn());
			uRescDO.setCreateTime(this.resc.getCreateTime());
			uRescDO.setUpdateTime(this.resc.getUpdateTime());
			this.rescBO.updateUResc(uRescDO);
		}
		return SUCCESS;
	}

	public String deleteResc() {
		this.rescBO.deleteURescById(id);
		return SUCCESS;
	}

	public void setRescBO(IURescBO rescBO) {
		this.rescBO = rescBO;
	}

	public URescDO getQuery() {
		return this.query;
	}

	public void setQuery(URescDO uResc) {
		this.query = uResc;
	}

	public URescDO getResc() {
		return this.resc;
	}

	public void setResc(URescDO resc) {
		this.resc = resc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}