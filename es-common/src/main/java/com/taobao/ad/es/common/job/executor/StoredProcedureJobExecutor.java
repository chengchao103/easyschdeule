/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.es.common.job.executor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.ad.es.common.Constants;
import com.taobao.ad.es.common.datasource.DataSourceFactory;
import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.taobao.ad.easyschedule.executor.JobExecutor;

/**
 * 存储过程执行器
 * 
 * @author bolin.hbc
 */
public class StoredProcedureJobExecutor implements JobExecutor {
	final static Logger logger = LoggerFactory.getLogger(StoredProcedureJobExecutor.class);

	private String dataSourceContextPath = null;

	public StoredProcedureJobExecutor(String dataSourceContextPath) {
		this.dataSourceContextPath = dataSourceContextPath;
	}

	@Override
	public JobResult execute(JobData jobData) throws Exception {
		Connection con = null;
		CallableStatement statement = null;
		// ResultSet rs = null;
		JobResult result = null;
		Map<String, String> exData = jobData.getData();
		try {
			con = DataSourceFactory.getInstance().getConnection(dataSourceContextPath, Integer.parseInt(exData.get(JobData.JOBDATA_DATA_DATASOURCE_TYPE)),
					exData.get(JobData.JOBDATA_DATA_DATASOURCE));
			if (con == null) {
				logger.error("数据源不存在" + exData.get(JobData.JOBDATA_DATA_DATASOURCE));
				result = JobResult.errorResult(JobResult.RESULTCODE_PARAMETER_ILLEGAL, "无法创建连接,请检查数据源" + exData.get(JobData.JOBDATA_DATA_DATASOURCE));
				return result;
			}
			statement = con.prepareCall(exData.get(JobData.JOBDATA_DATA_STOREDPROCEDURECALL));
			// 存储输出参数的List
			List<JSONObject> outPutList = new ArrayList<JSONObject>();

			String parameter = exData.get(JobData.JOBDATA_DATA_PARAMETER);
			if (parameter != null) {
				JSONArray array = new JSONArray(parameter);

				// 处理输入
				for (int i = 0; i < array.length(); i++) {
					JSONObject object = array.getJSONObject(i);
					int type = object.getInt("type");
					if (object.getInt("t") == Constants.STOREDPROCEDUREJOB_TYPE_INPUT) {
						switch (type) {
						case Constants.STOREDPROCEDUREJOB_VARCHAR:
							statement.setString(i + 1, object.getString("value"));
							break;
						case Constants.STOREDPROCEDUREJOB_NUMBER:
							statement.setDouble(i + 1, object.getDouble("value"));
							break;
						case Constants.STOREDPROCEDUREJOB_DATE:
							statement.setDate(i + 1, new Date(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(object.getString("value")).getTime()));
							break;
						}
					} else {
						object.put("index", i + 1);
						outPutList.add(object);
						switch (type) {
						case Constants.STOREDPROCEDUREJOB_VARCHAR:
							statement.registerOutParameter(i + 1, Types.VARCHAR);
							break;
						case Constants.STOREDPROCEDUREJOB_NUMBER:
							statement.registerOutParameter(i + 1, Types.DOUBLE);
							break;
						case Constants.STOREDPROCEDUREJOB_DATE:
							statement.registerOutParameter(i + 1, Types.DATE);
							break;
						}
					}
				}
			}
			statement.execute();
			// 处理输出结果
			if (!outPutList.isEmpty()) {
				StringBuilder str = new StringBuilder();
				for (JSONObject json : outPutList) {
					switch (json.getInt("type")) {
					case Constants.STOREDPROCEDUREJOB_VARCHAR:
						str.append("{index:" + json.getInt("index") + ",type:String,result:" + statement.getString(json.getInt("index")) + "},");
						break;
					case Constants.STOREDPROCEDUREJOB_NUMBER:
						str.append("{index:" + json.getInt("index") + ",type:Number,result:" + statement.getDouble(json.getInt("index")) + "},");
						break;
					case Constants.STOREDPROCEDUREJOB_DATE:
						str.append("{index:" + json.getInt("index") + ",type:Date,result:" + statement.getDate(json.getInt("index")).getTime() + "},");
						break;
					}
				}
				if (str.length() > 0) {
					result = JobResult.succcessResult(str.substring(0, str.length() - 1));
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
		return result;
	}
}