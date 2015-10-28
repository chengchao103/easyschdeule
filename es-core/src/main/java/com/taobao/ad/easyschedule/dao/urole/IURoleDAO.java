/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.urole;

import com.taobao.ad.easyschedule.dataobject.URoleDO;
import java.util.List;

public interface IURoleDAO {
	public Long insertURole(URoleDO uRole);

	public URoleDO getURoleById(Long id);

	public List<URoleDO> findURoles(URoleDO uRole);

	public List<URoleDO> findAllURoles(URoleDO uRole);

	public void deleteURoleById(Long id);

	public void updateURole(URoleDO uRole);

	public List<URoleDO> findURoles(String userName);

}