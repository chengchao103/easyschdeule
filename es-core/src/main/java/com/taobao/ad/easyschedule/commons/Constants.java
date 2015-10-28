/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.TreeMap;

import com.taobao.ad.easyschedule.commons.utils.StringUtils;

@SuppressWarnings("serial")
public class Constants {

	public static String JOBDATA_SYNCHRONOUS = "synchronous";
	public static String JOBDATA_COMPLETETIME = "completeTime";
	public static String JOBDATA_TARGETURL = "targetUrl";
	public static String JOBDATA_TARGETBEANCLASS = "targetBeanClass";
	public static String JOBDATA_BEANVERSION = "beanVersion";
	//public static String JOBDATA_TOKENVALIDATE = "tokenValidate";
	public static String JOBDATA_CHECKRESULT = "checkResult";
	public static String JOBDATA_PARAMETER = "parameter";
	public static String JOBDATA_RETRIES = "retries";
	public static String JOBDATA_CLIENTRETRIES = "clientRetries";
	public static String JOBDATA_INVOKE_TYPE = "invokeType";
	public static String JOBDATA_REPEAT_ALARM_NUM = "repeatAlarmNum";

	public static String JOBDATA_RUNTYPE = "runType";

	public static String JOBDATA_INVOKE_METHOD_NAME = "invokeMethodName";
	public static String JOBDATA_INVOKE_CLASS_NAME = "invokeClassName";
	public static String JOBDATA_INVOKE_PARAMETER = "parameter";

	public static String JOBDATA_TRACKINGSQL = "trackingSql";
	public static String JOBDATA_DATASOURCE = "dataSource";
	public static String JOBDATA_DATASOURCETYPE = "dataSourceType";
	public static String JOBDATA_ISEQUAL_LEVEL1 = "isEqualLevel1";
	public static String JOBDATA_ISLESSEQUAL_LEVEL1 = "isLessEqualLevel1";
	public static String JOBDATA_ISGREATEREQUAL_LEVEL1 = "isGreaterEqualLevel1";
	public static String JOBDATA_ISEQUAL_LEVEL2 = "isEqualLevel2";
	public static String JOBDATA_ISLESSEQUAL_LEVEL2 = "isLessEqualLevel2";
	public static String JOBDATA_ISGREATEREQUAL_LEVEL2 = "isGreaterEqualLevel2";
	public static String JOBDATA_JOBCOMMAND = "jobCommand";
	public static String JOBDATA_FILEFULLNAME = "fileFullName";

	// 存储过程参数类型
	public static final int STOREDPROCEDUREJOB_VARCHAR = 1;
	public static final int STOREDPROCEDUREJOB_NUMBER = 2;
	public static final int STOREDPROCEDUREJOB_DATE = 3;

	// 存储过程输入输出类型
	public static final int STOREDPROCEDUREJOB_TYPE_INPUT = 1;
	public static final int STOREDPROCEDUREJOB_TYPE_OUTPUT = 2;

	// 内置参数-执行次数
	public static String JOBDATA_SYSKEY_EXECUTE_COUNT_KEY = "sysExecuteCount";
	public static long JOBDATA_SYSKEY_EXECUTE_COUNT_VAL = 0L;
	// 内置参数-手动执行任务标志
	public static String JOBDATA_SYSKEY_MANUAL_EXECUTE_KEY = "sysKeyManualExecute";
	public static String JOBDATA_SYSKEY_MANUAL_EXECUTE_VAL_TRUE = "true";
	public static String JOBDATA_SYSKEY_MANUAL_EXECUTE_VAL_FALSE = "false";
	// 任务参数-是否验证返回结果
	public static String JOBDATA_CHECKRESULT_VAL_TRUE = "true";
	public static String JOBDATA_CHECKRESULT_VAL_FALSE = "false";
	// 任务参数-任务回调方式
	public static String JOBDATA_SYNCHRONOUS_VAL_TRUE = "true";
	public static String JOBDATA_SYNCHRONOUS_VAL_FALSE = "false";

	// 触发参数-触发类型
	public static String TRIGGERTYPE_SIMPLE = "Simple";
	public static String TRIGGERTYPE_CRON = "Cron";

	// 任务创建、修改标志
	public static int JOB_ACTION_ADD = 1;
	public static int JOB_ACTION_MOD = 2;

	public static final int ES_CURRENT_VERSION = 1;

	public static final String[] jobResultJsonExcludes = new String[] { "waringMail", "failureMail", "successMail", "exData" };

	// 触发器状态
	public static final int STATE_NORMAL = 0;
	public static final int STATE_PAUSED = 1;
	public static final int STATE_COMPLETE = 2;
	public static final int STATE_ERROR = 3;
	public static final int STATE_BLOCKED = 4;
	public static final int STATE_NONE = -1;
	public static TreeMap<Integer, String> TRIGGER_STATE = new TreeMap<Integer, String>() {
		{
			put(STATE_NORMAL, "正常");
			put(STATE_PAUSED, "暂停");
			put(STATE_COMPLETE, "完成");
			put(STATE_ERROR, "错误");
			put(STATE_BLOCKED, "阻塞");
			put(STATE_NONE, "无");
		}
	};

	//会话管理
	public static final String LOGNAME="logname";
	public static final String NICKNAME="nickname";
	
	// 重试告警状态
	public static final int REPEAT_ALARM_STATUS_PAUSE = 0;
	public static final int REPEAT_ALARM_STATUS_NORMAL = 1;

	// 状态
	public static final int STATUS_YES = 1;
	public static final int STATUS_NO = 0;

	public static final String es_OPEN_DEFAULT_USERNAME = "baimei";

	// 用户状态
	public static final int USER_STATE_NORMAL = 1;
	public static final int USER_STATE_DEL = 0;

	public static TreeMap<Integer, String> USER_STATE = new TreeMap<Integer, String>() {
		{
			put(USER_STATE_NORMAL, "正常");
			put(USER_STATE_DEL, "停用");
		}
	};

	public static String getUserStateDesc(int key) {
		return USER_STATE.get(key);
	}

	// 数据监控默认展现的点数
	public static int DATATRACKINGLOG_NUMBER = 30;

	// 所有提示状态
	public static TreeMap<Long, String> ERROR_STATUS = new TreeMap<Long, String>() {
		{
			put(-1L, "对应的key和keycode已经存在");
			put(-2L, "对应的key和sortnum已经存在");
			put(-3L, "对应的configkey已经存在");
		}
	};
	public static final int INSTANCE_START = 0;
	public static final int INSTANCE_PAUSE = 1;
	public static final int INSTANCE_STOP = 4;
	public static final int INSTANCE_SAFESTOP = 2;

	// instance中的状态标识
	public static TreeMap<Integer, String> INSTANCE_STATUS = new TreeMap<Integer, String>() {
		{
			put(INSTANCE_START, "启动");
			put(INSTANCE_PAUSE, "暂停");
			put(INSTANCE_STOP, "停止");
			put(INSTANCE_SAFESTOP, "安全停止");

		}
	};

	public static String WEBAPPURL = "http://127.0.0.1:1212/es";
	// 异步任务回调地址
	public static String CALLBACKURL = "http://127.0.0.1/es/completeJob";
	public static String CALLBACKURLS = "http://127.0.0.1/es/completeJob,http://localhost/es/completeJob";

	// 邮件调用地址
	public static String MAIL_SEND_COMMAND = "http://monitor.taobao.com/monitor-report/openaction/openAlertApi.do?alertType=email&emailList=#list#&subject=#subject#&msg=#content#&userName=easyschedule";

	// 短信调用地址
	public static String SMS_SEND_COMMAND = "http://monitor.taobao.com/monitor-report/openaction/openAlertApi.do?alertType=sms&smsList=#list#&subject=#subject#&msg=#content#&userName=easyschedule";

	// 旺旺调用地址
	public static String WANGWANG_SEND_COMMAND = "http://monitor.taobao.com/monitor-report/openaction/openAlertApi.do?alertType=ww&wwList=#list#&subject=#subject#&msg=#content#&userName=easyschedule";

	// 数据任务调用地址
	public static String DEPLOY_MODE = "dev";

	// 邮件发送开关
	public static String SENDMAIL = "false";

	// 短信发送开关
	public static String SENDSMS = "false";

	// 旺旺发送开关
	public static String SENDWANGWANG = "true";

	// 任务最大重试次数
	public static int JOB_MAX_RETRIES = 5;

	// 任务最小重试次数
	public static int JOB_MIN_RETRIES = 0;

	// 任务失败重试等待毫秒数
	public static int JOB_RETRY_WAITTIME = 3000;

	// 任务最大请求超时毫秒数
	public static int JOB_MAX_CONN_TIMEOUT = 30000;

	// 任务最小请求超时毫秒数
	public static int JOB_MIN_CONN_TIMEOUT = 3000;
	
	// 任务最大读取数据超时毫秒数
	public static int JOB_MAX_SO_TIMEOUT = 60000;

	// 任务最小读取数据超时毫秒数
	public static int JOB_MIN_SO_TIMEOUT = 1000;
	

	// 通知接口请求超时毫秒数
	public static int NOTIFY_API_CONN_TIMEOUT = 4000;

	// 异步任务最大处理超时毫秒数
	public static int JOB_MAX_COMPLETE_TIMEOUT = 300000;

	// 默认单面最大记录数
	public final static int DEFAULT_PAGE_SIZE = 100;

	// 告警订阅
	public static final int IS_SUB = 1;
	public static final int SUBTYPE_WARNING = 2;
	public static final int SUBTYPE_SUCCESS = 1;
	public static final int SUBTYPE_FAILURE = 0;

	public static String RUNTYPE_HASH = "hash";
	public static String RUNTYPE_CONCURRENT = "concurrent";

	public static String DEFAULT_PASSWD = "aaa111";

	public static String ROLE_USER = "ROLE_USER";
	public static String ROLE_ADMIN = "ROLE_ADMIN";
	public static Long ROLE_USER_NUMBER = 2L;
	public static Long ROLE_ADMI_NUMBER = 1L;

	public static String REAL_MODE = "real";

	public static int SUB_TYPE_FAIL = 0;
	public static int SUB_TYPE_SUCCESS = 1;
	public static int SUB_TYPE_WARN = 2;

	public static String SUB_TYPE_WANGWANG = "wangwang";
	public static String SUB_TYPE_MOBILE = "mobile";
	public static String SUB_TYPE_EMAIL = "email";

	public static final int EMAIL_SUB = 1;
	public static final int WANGWANG_SUB = 2;
	public static final int MOBILE_SUB = 3;

	public static final String EMAIL_TEMPLATE_DIR = "/templates";
	public static final String EMAIL_TEMPLATE_FILENAME = "jobReport.ftl";

	public static final String EMAIL_TEMPLATE_MAIL_SYS = "easyschedule_mm";
	public static final String EMAIL_TEMPLATE_MAIL_TITLE = "【定时任务统计信息】 - startTime~endTime";
	public static final String EMAIL_TEMPLATE_MAIL_LIST = "bolin.hbc@taobao.com,baimei@taobao.com";
	public static final String DEFAULT_CODE = "utf-8";

	static {
		try {
			Properties props = new Properties();
			InputStream is = Constants.class.getResourceAsStream("/constants.properties");
			Reader reader = new InputStreamReader(is, "utf-8");
			props.load(reader);
			Constants constants = new Constants();
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
			if (DEPLOY_MODE.equals("${deploy.mode}")) {
				DEPLOY_MODE = "dev";
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
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
