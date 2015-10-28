/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.common.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.taobao.ad.easyschedule.commons.utils.StringUtils;
import com.taobao.ad.easyschedule.dataobject.JobData;

/**
 * 动态数据源工厂
 * 
 * @author baimei
 * 
 */
public class DataSourceFactory {

	private static final DataSourceFactory factory = new DataSourceFactory();

	public static DataSourceFactory getInstance() {
		return factory;
	}

	public Connection getConnection(String contextPath, int dsType, String dataSource) throws BeansException, SQLException, NamingException {
		Connection connection = null;
		switch (dsType) {
		case JobData.JOBDATA_DATA_DATASOURCE_TYPE_DYNAMIC:
			// BeanFactoryLocator sysCtxLocator =
			// SingletonBeanFactoryLocator.getInstance(WebAgentConstants.DATASOURCE_CONTEXT);
			// BeanFactoryReference brf =
			// sysCtxLocator.useBeanFactory(WebAgentConstants.DATASOURCE_CONTEXT_KEY);
			// connection = ((DataSource)
			// brf.getFactory().getBean(dataSource)).getConnection();
			AbstractXmlApplicationContext context = null;
			if (StringUtils.isEmpty(contextPath)) {
				context = new ClassPathXmlApplicationContext("classpath:es-agent-ds.xml");
			} else {
				context = new FileSystemXmlApplicationContext(contextPath);
			}
			connection = ((DataSource) context.getBean(dataSource)).getConnection();
			break;
		default:
			Context cxt = new InitialContext();
			Context envCtx = (Context) cxt.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/" + dataSource);
			connection = ds.getConnection();
			break;
		}
		return connection;
	}
}
