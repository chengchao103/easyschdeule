/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.uresc.impl;

import java.util.List;
import com.taobao.ad.easyschedule.dataobject.URescDO;
import com.taobao.ad.easyschedule.dao.uresc.IURescDAO;
import com.taobao.ad.easyschedule.bo.uresc.IURescBO;

public class URescBOImpl implements IURescBO{
	
	private IURescDAO rescDAO;
	
	public Long insertUResc(URescDO uResc){
		return rescDAO.insertUResc(uResc);
	}
	
	public URescDO getURescById(Long id){
		return rescDAO.getURescById(id);
	}
	
	public List<URescDO> findURescs(URescDO uResc){
		return rescDAO.findURescs(uResc);
	}
	
	public void deleteURescById(Long id){
		rescDAO.deleteURescById(id);
	}
	
	public void updateUResc(URescDO uResc){
		rescDAO.updateUResc(uResc);
	}
	
	@Override
	public List<Integer> findAllUserRescs(String userName) {
		return rescDAO.findAllUserRescs(userName);
	}

	public void setRescDAO(IURescDAO rescDAO){
		this.rescDAO=rescDAO;
	}
}