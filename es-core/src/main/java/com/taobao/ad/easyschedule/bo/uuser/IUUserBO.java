/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.uuser;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.UUserDO;
import com.taobao.ad.easyschedule.dataobject.UUserXGroupDO;
import com.taobao.ad.easyschedule.dataobject.UUserXRoleDO;
@Transactional
public interface IUUserBO{
	
	public void insertUUser(UUserDO uUser);
	
	public void insertUUser(UUserDO uUser, Long[] roles, Long[] groups);
	
	public UUserDO getUUserById(Long id);
	
	public UUserDO getUUserByUsername(String username);
	
	public List<UUserDO> findUUsers(UUserDO uUser);
	
	public void deleteUUserById(Long id);
	
	public void updateUUser(UUserDO uUser);
	
	public void updateUUser(UUserDO uUser, Long[] roles, Long[] groups);
	
	public void updateUUserStatus(UUserDO uUser);
	
	public List<UUserXRoleDO> findUUserXRoles(Long userid);
	
	public List<UUserXGroupDO> findUUserXGroups(Long userid);
	
	public UUserDO checkLogin(String name,String passwd);

}