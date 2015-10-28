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

public class SessionEncode {
    private static final Logger fLog = Logger.getLogger(StringUtil.getShortName(SessionEncode.class));

    public static String encode(String aStr) {
        String result = null;

        try {
            if (StringUtils.isNotBlank(aStr)) {
                result = new String(Base64.encodeBase64(aStr.getBytes()));
            }
        } catch (Exception ex) {
        	fLog.info(ex.getMessage());
        }

        return result;
    }
}
