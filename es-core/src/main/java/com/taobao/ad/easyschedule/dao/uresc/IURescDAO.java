/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.uresc;

import com.taobao.ad.easyschedule.dataobject.URescDO;
import java.util.List;

public interface IURescDAO {
	public Long insertUResc(URescDO uResc);

	public URescDO getURescById(Long id);

	public List<URescDO> findURescs(URescDO uResc);

	public List<URescDO> findAllURescs(URescDO uResc);

	public void deleteURescById(Long id);

	public void updateUResc(URescDO uResc);

	public List<Integer> findAllUserRescs(String userName);

}