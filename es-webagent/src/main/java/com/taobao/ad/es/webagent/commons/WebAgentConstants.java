/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.webagent.commons;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.easyschedule.commons.utils.StringUtils;

public class WebAgentConstants {
	
	final static Logger logger = LoggerFactory.getLogger(WebAgentConstants.class);
	
	public static String DEPLOY_MODE = "dev";
	public static String DATASOURCE_CONTEXT_PATH = "";

	static {
		try {
			Properties props = new Properties();
			InputStream is = WebAgentConstants.class.getResourceAsStream("/webagentconstants.properties");
			Reader reader = new InputStreamReader(is, "utf-8");
			props.load(reader);
			WebAgentConstants constants = new WebAgentConstants();
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
			logger.error("WebAgentConstants加载失败", e);
			System.exit(-1);
		}
	}
}