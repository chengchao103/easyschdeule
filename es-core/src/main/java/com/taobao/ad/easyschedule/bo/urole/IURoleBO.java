/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.urole;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.URoleDO;

@Transactional
public interface IURoleBO {

	public Long insertURole(URoleDO uRole);

	public URoleDO getURoleById(Long id);

	public List<URoleDO> findURoles(URoleDO uRole);

	public void deleteURoleById(Long id);

	public void updateURole(URoleDO uRole);

	public List<URoleDO> findURoles(String userName);
}