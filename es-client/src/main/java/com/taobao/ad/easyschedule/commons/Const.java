package com.taobao.ad.easyschedule.commons;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Properties;

import com.taobao.ad.easyschedule.commons.utils.StringUtils;

public class Const {

	public static String TOKENBASEKEY = "123456";
	public static String DEPLOY_MODE = "dev";

	public static String JOBID = "jobId";
	public static String CLIENTRETRIES = "clientRetries";
	public static String JOBGROUP = "jobGroup";
	public static String JOBNAME = "jobName";
	public static String JOBTYPE = "jobType";
	public static String BEANID = "beanId";
	public static String JOBCOMMAND = "jobCommand";
	public static String FILEFULLNAME = "fileFullName";
	public static String TRACKINGSQL = "trackingSql";
	public static String DATASOURCE = "dataSource";
	public static String DATASOURCETYPE = "dataSourceType";
	public static String SIGNTIME = "signTime";
	public static String TOKEN = "token";
	public static String SYNC = "sync";
	public static String CALLBACKURL = "callBackUrl";
	public static String CALLBACKURLS = "callBackUrls";
	public static String PARAMETER = "parameter";
	public static String STOREDPROCEDURECALL = "storedProcedureCall";

	public static final String QUERYPATH_DOSTDJOB = "/doStdJob";
	public static final String QUERYPATH_DOSHELLJOB = "/doShellJob";
	public static final String QUERYPATH_DODTJOB = "/doDTJob";
	public static final String QUERYPATH_DOLINECOUNTJOB = "/doLineCountJob";
	public static final String QUERYPATH_DOSPJOB = "/doSPJob";

	public static String DATASOURCE_CONTEXT_PATH = "";
	public static String SERVER_PORT = "9999";
	public static String SERVER_CONTEXT = "/esagent";
	
	// 任务重试等待毫秒数
	public static Long JOB_MAX_COMPLETE_TIMEOUT = 5000L;
	
	// 任务最大重试次数
	public static int JOB_CLIENT_MAX_RETRIES = 10;

	// 任务最小重试次数
	public static int JOB_CLIENT_MIN_RETRIES = 0;
	
	static {
		try {
			Properties props = new Properties();
			InputStream is = Const.class.getResourceAsStream("/const.properties");
			Reader reader = new InputStreamReader(is, "utf-8");
			props.load(reader);
			Const constants = new Const();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
