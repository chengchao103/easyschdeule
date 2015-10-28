/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;

import java.io.IOException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import com.taobao.ad.easyschedule.exsession.commons.StringUtil;

public class UniqID {
     private static char[]       digits = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    private static UniqID me       = new UniqID();
    private String        hostAddr;
    private Random        random  = new SecureRandom();
    private MessageDigest mHasher;
    private UniqTimer     timer   = new UniqTimer();

    private UniqID() {
        try {
            InetAddress addr = InetAddress.getLocalHost();

            hostAddr = addr.getHostAddress();
        } catch (IOException e) {
            hostAddr = String.valueOf(System.currentTimeMillis());
        }

        if (StringUtil.isBlank(hostAddr) || "127.0.0.1".equals(hostAddr)) {
        	hostAddr = String.valueOf(System.currentTimeMillis());
        }

  
        try {
            mHasher = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nex) {
            mHasher = null;
            }
    }

    public static UniqID getInstance() {
        return me;
    }

    public String getUniqID() {
        StringBuffer sb = new StringBuffer();
        long         t = timer.getCurrentTime();

        sb.append(t);

        sb.append("-");

        sb.append(random.nextInt(8999) + 1000);

        sb.append("-");
        sb.append(hostAddr);

        sb.append("-");
        sb.append(Thread.currentThread().hashCode());

     
        return sb.toString();
    }

    public String getUniqIDHash() {
        return hash(getUniqID());
    }
    
    public synchronized String hash(String str) {
        byte[] bt = mHasher.digest(str.getBytes());
        int    l = bt.length;

        char[] out = new char[l << 1];

        for (int i = 0, j = 0; i < l; i++) {
            out[j++]     = digits[(0xF0 & bt[i]) >>> 4];
            out[j++]     = digits[0x0F & bt[i]];
        }

          
        return new String(out);
    }

    private class UniqTimer {
        private long lastTime = System.currentTimeMillis();

        public synchronized long getCurrentTime() {
            long currTime = System.currentTimeMillis();

            lastTime = Math.max(lastTime + 1, currTime);

            return lastTime;
        }
    }
}
