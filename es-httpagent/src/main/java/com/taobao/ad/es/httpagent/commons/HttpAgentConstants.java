/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.httpagent.commons;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Properties;

import com.taobao.ad.easyschedule.commons.utils.StringUtils;

public class HttpAgentConstants {

	public static final String QUERYPATH_DOSHELLJOB = "/doShellJob";
	public static final String QUERYPATH_DODTJOB = "/doDTJob";
	public static final String QUERYPATH_DOLINECOUNTJOB = "/doLineCountJob";
	public static final String QUERYPATH_DOSPJOB = "/doSPJob";

	public static String DEPLOY_MODE = "dev";
	public static String DATASOURCE_CONTEXT_PATH = "";
	public static String SERVER_PORT = "9999";
	public static String SERVER_CONTEXT = "/esagent";

	static {
		try {
			Properties props = new Properties();
			InputStream is = HttpAgentConstants.class.getResourceAsStream("/httpagentconstants.properties");
			Reader reader = new InputStreamReader(is, "utf-8");
			props.load(reader);
			HttpAgentConstants constants = new HttpAgentConstants();
			Field[] fields = constants.getClass().getFields();
			// 从配置文件中从加载
			for (Field field : fields) {
				String value = props.getProperty(field.getName());
				if (!StringUtils.isEmpty(value)) {
					if (field.getType() == String.class) {
						field.set(constants, value);
					}
				}
			}
			// 从环境变量中从加载
			for (Field field : fields) {
				String value = System.getProperty(field.getName());
				if (!StringUtils.isEmpty(value)) {
					if (field.getType() == String.class) {
						field.set(constants, value);
					}
				}
			}
			is.close();
			reader.close();
			if (DEPLOY_MODE.equals("${deploy.mode}")) {
				DEPLOY_MODE = "dev";
			}
//			if (StringUtils.isEmpty(DATASOURCE_CONTEXT_PATH)) {
//				DATASOURCE_CONTEXT_PATH = constants.getClass().getClassLoader().getResource("es-agent-ds.xml").getPath();
//			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}