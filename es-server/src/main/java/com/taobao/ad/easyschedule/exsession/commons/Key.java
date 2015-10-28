/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */

package com.taobao.ad.easyschedule.exsession.commons;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;

public class Key
{

    public Key()
    {
    }

    public static SecretKey loadKey()
        throws Exception
    {
        SecretKey key = null;
        java.io.InputStream fis = (Key.class).getResourceAsStream("/user.key");
        if(fis != null)
        {
            BASE64Decoder d = new BASE64Decoder();
            byte b[] = d.decodeBuffer(fis);
            DESKeySpec dks = new DESKeySpec(b);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            key = keyFactory.generateSecret(dks);
        }
        return key;
    }
}
