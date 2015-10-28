/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.code;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.CodeDO;

@Transactional
public interface ICodeBO {

	public Long insertCode(CodeDO code);

	public void updateCode(CodeDO code);

	public void deleteCodeByKeyAndKeyCode(CodeDO code);

	public List<CodeDO> getCodesByKey(String key);

	public List<CodeDO> findCodes(CodeDO code);

	public CodeDO getCodeByKeyAndKeyCode(CodeDO code);
	
	public String getNameByCode(String code);

}