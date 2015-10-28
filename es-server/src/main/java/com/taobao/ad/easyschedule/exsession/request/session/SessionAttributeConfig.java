/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;


public class SessionAttributeConfig {
    public static String NAME        = "key";
    public static String NICK_NAME   = "nickKey";
    public static String IS_ENCRYPT  = "isEncrypt";
    public static String IS_BASE64   = "isBase64";
    public static String DOMAIN      = "domain";
    public static String LIFE_CYCLE   = "lifeCycle";
    public static String COOKIE_PATH  = "cookiePath";
    private String       name;
    private String       nickName;
    private String       storeKey;
    private boolean      isEncrypt   = false; //是否需要加密
    private boolean      isBase64    = false; //是否做BASE64
    private String       domain;
    private boolean      isDirty     = false;
    private int          lifeTime	 = -1;
    private String       cookiePath  ="/";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }


    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isBase64() {
        return isBase64;
    }

    public void setBase64(boolean isBase64) {
        this.isBase64 = isBase64;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

	public int getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getCookiePath() {
		return cookiePath;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

}
