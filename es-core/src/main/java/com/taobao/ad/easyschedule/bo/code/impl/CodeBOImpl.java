/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.code.impl;

import java.util.List;

import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.dao.code.ICodeDAO;
import com.taobao.ad.easyschedule.dataobject.CodeDO;

public class CodeBOImpl implements ICodeBO {

	private ICodeDAO codeDAO;

	public Long insertCode(CodeDO code) {
		// 检测重复
		CodeDO getCode = codeDAO.getCodeByKeyAndKeyCode(code);
		if (getCode != null) {
			return -1L;
		}
		codeDAO.insertCode(code);
		return 1L;
	}

	public List<CodeDO> getCodesByKey(String key) {
		return codeDAO.getCodesByKeyFromCache(key);
	}

	public List<CodeDO> findCodes(CodeDO code) {
		return codeDAO.findCodes(code);
	}

	@Override
	public String getNameByCode(String code) {
		CodeDO c = new CodeDO();
		c.setKeycode(code);
		c = getCodeByKeyAndKeyCode(c);
		return c == null ? null : c.getKeyname();
	}

	public void updateCode(CodeDO code) {
		codeDAO.updateCode(code);
	}

	public void setCodeDAO(ICodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}

	public CodeDO getCodeByKeyAndKeyCode(CodeDO code) {
		return codeDAO.getCodeByKeyAndKeyCode(code);
	}

	public void deleteCodeByKeyAndKeyCode(CodeDO code) {
		codeDAO.deleteCodeByKeyAndKeyCode(code);
	}

}