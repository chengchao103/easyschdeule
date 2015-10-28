/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.uresc;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.URescDO;
@Transactional
public interface IURescBO{
	
	public Long insertUResc(URescDO uResc);
	
	public URescDO getURescById(Long id);
	
	public List<URescDO> findURescs(URescDO uResc);
	
	public void deleteURescById(Long id);
	
	public void updateUResc(URescDO uResc);
	
	public List<Integer> findAllUserRescs(String userName);
}