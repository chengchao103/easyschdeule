/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA. User: baimei Date: 2010-8-9 Time: 19:57:07
 */
public class Md5Encrypt {
	public static String BASE_CHARSET = "utf-8";

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param text
	 *            明文
	 * @return 密文
	 */
	public static String md5(String text) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		msgDigest.update(text.getBytes());
		byte[] bytes = msgDigest.digest();
		byte tb;
		char low;
		char high;
		char tmpChar;
		String md5Str = "";
		for (int i = 0; i < bytes.length; i++) {
			tb = bytes[i];
			tmpChar = (char) ((tb >>> 4) & 0x000f);
			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}
			md5Str += high;
			tmpChar = (char) (tb & 0x000f);
			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}
			md5Str += low;
		}
		return md5Str;
	}
}
