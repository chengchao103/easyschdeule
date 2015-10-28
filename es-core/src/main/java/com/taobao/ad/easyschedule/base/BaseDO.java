/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.base;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class BaseDO implements Serializable {
    private static final long serialVersionUID = 741231858441822688L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}