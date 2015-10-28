/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.bo.trigger;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.taobao.ad.easyschedule.dataobject.TriggerDO;

/**
 * @author 作者 :huangbaichuan.pt
 * @version 创建时间:2010-9-2 下午09:04:36 类说明:TriggerBO
 */
@Transactional
public interface TriggerBO {
	public List<TriggerDO> getTriggerList(TriggerDO triggerDO);
}
