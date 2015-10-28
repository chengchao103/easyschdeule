/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

import com.taobao.ad.easyschedule.commons.Const;

/**
 * 令牌验证工具类
 * 
 * @author baimei
 */
public class TokenUtils {

	private static long defaultValidTime = 60;

	/**
	 * 令牌创建
	 * 
	 * @return
	 */
	public static String generateToken(String signTime) {
		return Md5Encrypt.md5(Const.TOKENBASEKEY + signTime);
	}

	/**
	 * 带baseKey的令牌创建
	 * 
	 * @return
	 */
	public static String generateToken(String baseKey, String signTime) {
		return Md5Encrypt.md5(baseKey + signTime);
	}

	/**
	 * 令牌验证
	 * 
	 * @return
	 */
	public static boolean validateToken(String token, String signTime) {
		return validateToken(Const.TOKENBASEKEY, token, signTime, defaultValidTime);
	}

	/**
	 * 带baseKey的令牌验证
	 * 
	 * @return
	 */
	public static boolean validateToken(String baseKey, String token, String signTime) {
		return validateToken(baseKey, token, signTime, defaultValidTime);
	}

	/**
	 * 带baseKey和失效时间的令牌验证
	 * 
	 * @return
	 */
	public static boolean validateToken(String baseKey, String token, String signTime, long validTim) {
		try {
			if (token != null && signTime != null) {
				String result = Md5Encrypt.md5(baseKey + signTime);
				if (result.equalsIgnoreCase(token)) {
					return Math.abs(System.currentTimeMillis() / 1000 - Long.parseLong(signTime)) <= validTim;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void setDefaultValidTime(long defaultValidTime) {
		TokenUtils.defaultValidTime = defaultValidTime;
	}

}
