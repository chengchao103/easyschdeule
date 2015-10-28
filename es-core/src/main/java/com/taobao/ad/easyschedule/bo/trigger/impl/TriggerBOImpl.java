/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.trigger.impl;

import java.util.List;

import com.taobao.ad.easyschedule.bo.trigger.TriggerBO;
import com.taobao.ad.easyschedule.dao.trigger.dao.TriggerDAO;
import com.taobao.ad.easyschedule.dataobject.TriggerDO;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-9-2 下午09:05:41 类说明:
 */

public class TriggerBOImpl implements TriggerBO {

	private TriggerDAO triggerDAO;

	public List<TriggerDO> getTriggerList(TriggerDO triggerDO) {

		return triggerDAO.getTriggerList(triggerDO);
	}

	public void setTriggerDAO(TriggerDAO triggerDAO) {
		this.triggerDAO = triggerDAO;
	}

}
