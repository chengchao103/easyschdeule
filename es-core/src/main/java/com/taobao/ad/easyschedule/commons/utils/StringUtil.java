/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

import java.io.UnsupportedEncodingException;

/**
 * 有关字符串处理的工具类。
 * 
 */
public class StringUtil {

	/**
	 * 按字节长度截取字符串
	 * 
	 * @param s
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String bSubstring(String s, int length) {
		byte[] bytes = new byte[0];
		try {
			bytes = s.getBytes("Unicode");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始
		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1) {
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0)
				i = i - 1;
			// 该UCS2字符是字母或数字，则保留该字符
			else
				i = i + 1;
		}
		try {
			return new String(bytes, 0, i, "Unicode");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 格式化HTML文本
	 * 
	 * @param content
	 * @return
	 */
	public static String html(String content) {
		if (StringUtils.isEmpty(content))
			return "";
		String html = content;
		html = html.replaceAll("'", "&apos;");
		html = html.replaceAll("\"", "&quot;");
		html = html.replaceAll("\t", "&nbsp;&nbsp;");
		html = html.replaceAll(" ", "&nbsp;");
		html = html.replaceAll("<", "&lt;");
		html = html.replaceAll(">", "&gt;");
		return html;
	}
}
