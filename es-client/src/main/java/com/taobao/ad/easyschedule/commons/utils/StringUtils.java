/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

/**
 * 字符串工具类
 * 
 * @author baimei
 * 
 */
public class StringUtils {

	/**
	 * 校验为空或者""
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		if (value == null || value.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
