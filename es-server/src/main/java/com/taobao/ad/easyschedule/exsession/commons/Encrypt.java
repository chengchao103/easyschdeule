/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.commons;

import java.io.ByteArrayOutputStream;
import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encrypt {

	public Encrypt() {
	}

	public static String eCode(String needEncrypt) throws Exception {
		byte result[] = null;
		try {
			Cipher enCipher = Cipher.getInstance("DES");
			javax.crypto.SecretKey key = Key.loadKey();
			enCipher.init(1, key);
			result = enCipher.doFinal(needEncrypt.getBytes());
			BASE64Encoder b = new BASE64Encoder();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			b.encode(result, bos);
			result = bos.toByteArray();
		} catch (Exception e) {
			throw e;
		}
		return new String(result);
	}

	public static String dCode(byte result[]) throws Exception {
		String s = null;
		try {
			Cipher deCipher = Cipher.getInstance("DES");
			deCipher.init(2, Key.loadKey());
			BASE64Decoder d = new BASE64Decoder();
			result = d.decodeBuffer(new String(result));
			byte strByte[] = deCipher.doFinal(result);
			s = new String(strByte);
		} catch (Exception e) {
			throw e;
		}
		return s;
	}
}
