/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.commons;

import org.apache.log4j.Logger;

/**
 * 数据加密解密
 * 
 * @author bolin.hbc
 * 
 */
public class EncryptUtil {

	private static Logger log = Logger.getLogger(EncryptUtil.class);

	/**
	 * 对数据进行解密
	 * 
	 * @param dCodeString
	 *            String 需要解密的内容
	 * @return
	 * @throws Exception
	 */
	public static String dCode(String dCodeString) {
		String tmp = "";
		try {
			if (dCodeString != null) {
				tmp = Encrypt.dCode(dCodeString.getBytes());
			}
		} catch (Exception e) {
			log.error("Encrypt dCode error : ", e);
		}
		return tmp;

	}

	/**
	 * 对数据加密
	 * 
	 * @param eCodeString
	 *            String 需要加密的内容
	 * @return
	 */
	public static String eCode(String eCodeString) {
		String tmp = "";
		try {
			if (eCodeString != null) {
				tmp = Encrypt.eCode(eCodeString);
			}
		} catch (Exception e) {
			log.error("Encrypt eCode error : ", e);
		}
		return tmp;

	}

}
