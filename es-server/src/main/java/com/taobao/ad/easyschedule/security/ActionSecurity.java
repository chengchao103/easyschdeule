/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
@Inherited
public @interface ActionSecurity {
	long module() default 0; // 模块ID，在Class上标注

	String operation() default "缺省操作权限注释，请自定义!"; // 操作功能名称，在Method上标注

	boolean enable() default true; // 是否使用

	public enum Role {
		SYSTEM_ADMIN, // 系统管理员，拥有所有权限
		USER
		// 普通用户权限
	}

	Role roleLevel() default Role.USER;
}
