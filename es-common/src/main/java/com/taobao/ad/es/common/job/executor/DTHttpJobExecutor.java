/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.common.job.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.es.common.datasource.DataSourceFactory;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.JobExecutor;

/**
 * 数据监控任务执行器
 * 
 * @author baimei
 * 
 */
public class DTHttpJobExecutor implements JobExecutor {

	final Logger logger = LoggerFactory.getLogger(DTHttpJobExecutor.class);
	private String dataSourceContextPath = null;

	public DTHttpJobExecutor(String dataSourceContextPath) {
		this.dataSourceContextPath = dataSourceContextPath;
	}

	@Override
	public JobResult execute(JobData jobData) throws NumberFormatException, NamingException, SQLException {
		JobResult jobResult = JobResult.succcessResult();
		Double result = getDataTrackingValue(Integer.parseInt(jobData.getData().get(JobData.JOBDATA_DATA_DATASOURCE_TYPE)), jobData.getData().get(
				JobData.JOBDATA_DATA_DATASOURCE), jobData.getData().get(JobData.JOBDATA_DATA_TRACKINGSQL));
		if (result == null) {
			jobResult = JobResult.errorResult(JobResult.RESULTCODE_JOBRESULT_ILLEGAL, "处理数据监控发生异常！");
			return jobResult;
		} else {
			jobResult.setResultMsg(result.toString());
		}
		return jobResult;
	}

	private Double getDataTrackingValue(int dsType, String dataSource, String trackingSql) throws NamingException, SQLException {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		Double data = null;
		try {
			con = DataSourceFactory.getInstance().getConnection(dataSourceContextPath, dsType, dataSource);
			if (con == null) {
				logger.error(dataSource + "数据源不存在", dataSource);
				return null;
			} else {
				statement = con.createStatement();
				statement.setMaxRows(1);
				rs = statement.executeQuery(trackingSql);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				if (columnCount > 0) {
					while (rs.next()) {
						data = Double.parseDouble(rs.getString(1));
						break;
					}
				}
			}
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return data;
	}
}
