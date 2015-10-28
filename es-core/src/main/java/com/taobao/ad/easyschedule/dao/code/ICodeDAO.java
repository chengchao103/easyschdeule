/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.code;

import java.util.List;

import com.taobao.ad.easyschedule.dataobject.CodeDO;

public interface ICodeDAO {
	public void insertCode(CodeDO code);

	public void updateCode(CodeDO code);

	public void deleteCodeByKeyAndKeyCode(CodeDO code);

	public List<CodeDO> getCodesByKeyFromCache(String key);

	public List<CodeDO> findCodes(CodeDO code);

	public List<CodeDO> findAllCodes(CodeDO code);

	public CodeDO getCodeByKeyAndKeyCode(CodeDO code);

}