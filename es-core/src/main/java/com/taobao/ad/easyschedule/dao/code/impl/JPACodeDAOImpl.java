/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dao.code.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.ad.easyschedule.base.JPABaseDAO;
import com.taobao.ad.easyschedule.dao.code.ICodeDAO;
import com.taobao.ad.easyschedule.dataobject.CodeDO;

@SuppressWarnings("unchecked")
public class JPACodeDAOImpl extends JPABaseDAO implements ICodeDAO {

	public void insertCode(CodeDO code) {
		getJpaTemplate().persist(code);
	}

	public List<CodeDO> getCodesByKey(String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.codekey", key);
		return super.executeQueryIsNotEmpty("select t from es_code t", map, "order by t.sortnum");
	}

	public List<CodeDO> getCodesByKeyFromCache(String key) {
		return getCodesByKey(key);
	}

	public List<CodeDO> findCodes(CodeDO code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.codekey", code.getCodekey());
		map.put("t.keycode", code.getKeycode());
		map.put("t.keyname", code.getKeyname());
		code.setTotalItem(super.getQueryCount("select count(*) from es_code t", map));
		return super.queryForListIsNotEmpty("select t from es_code t", map, code, "order by t.codekey,t.sortnum");
	}

	public List<CodeDO> findAllCodes(CodeDO code) {
		return (List<CodeDO>) getJpaTemplate().find("from es_code");
	}

	public void updateCode(CodeDO code) {
		getJpaTemplate().merge(code);
	}

	public CodeDO getCodeByKeyAndKeyCode(CodeDO code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t.codekey", code.getCodekey());
		map.put("t.keycode", code.getKeycode());
		return (CodeDO) super.executeSingleIsNotEmpty("select t from es_code t", map, "");
	}

	public void deleteCodeByKeyAndKeyCode(final CodeDO code) {
		CodeDO c = getCodeByKeyAndKeyCode(code);
		if (c != null) {
			getJpaTemplate().remove(c);
		}
	}
}