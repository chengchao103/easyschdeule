/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;

import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.taobao.ad.easyschedule.exsession.commons.StringUtil;


public class SessionDecode {
	 private static final Logger fLog = Logger.getLogger(StringUtil.getShortName(SessionEncode.class));
	 
    public static String decode(String encoded) {
        String result = null;

        if (StringUtils.isNotBlank(encoded)) {
            try {
                byte[] decoded = Base64.decodeBase64(encoded.getBytes());

                result = new String(decoded);
            } catch (Exception e) {
                fLog.info(e.getMessage());
                result = "";
            }
        }

        return result;
    }
	
}
