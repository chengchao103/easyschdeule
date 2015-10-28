/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.interceptor;

import com.taobao.ad.easyschedule.dataobject.UUserDO;

public class ThreadLocalManager {
	private static final ThreadLocal<UUserDO> manager = new ThreadLocal<UUserDO>();

	public static void setLoginUser(UUserDO userObj) {
		UUserDO user = getLoginUser();
		if (user != null) {
			manager.remove();
		}
		manager.set(userObj);
	}

	public static UUserDO getLoginUser() {
		return manager.get();
	}

	public static void clear() {
		manager.remove();
	}
}
