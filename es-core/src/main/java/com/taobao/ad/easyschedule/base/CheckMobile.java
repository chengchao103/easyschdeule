/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.base;

public class CheckMobile {

	static final String[] MOBILE_SPECIFIC_SUBSTRING = { /* "ipad", */"iphone",
			"android", "midp", "opera mobi", "opera mini", "blackberry",
			"hp ipaq", "iemobile", "msiemobile", "windows phone", "htc", "lg",
			"mot", "nokia", "symbian", "fennec", "maemo", "tear", "midori",
			"armv", "windows ce", "windowsce", "smartphone", "240x320",
			"176x220", "320x320", "160x160", "webos", "palm", "sagem",
			"samsung", "sgh", "siemens", "sonyericsson", "mmp", "ucweb" };

	public static boolean checkMobile(String userAgent) {
		boolean isMobile = false;
		String userAgentLowerCase = userAgent.toLowerCase();
		for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
			if (userAgentLowerCase.contains(mobile.toLowerCase())) {
				isMobile = true;
			}
		}
		return isMobile;
	}
}
