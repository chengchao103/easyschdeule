/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.dataobject;

import java.util.Date;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.taobao.ad.easyschedule.base.PageInfo;

import static com.taobao.ad.easyschedule.commons.utils.StringUtil.bSubstring;

@SuppressWarnings("serial")
@Entity(name="es_logs")
public class LogsDO extends PageInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public LogsDO(){}
	public int hashCode() {
		return super.hashCode();
	}

	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	// OPTYPE
	public static final Long TYPE_SUCCESS = 1L;                       // 成功日志
	public static final Long TYPE_FAILE = 2L;                         // 失败日志
	public static final Long TYPE_WARNING = 3L;                       // 警告日志

	// OPSUBTYPE
	//public static final Long SUBTYPE_ADD_HTTPJOB = 201L;              // 添加http任务
	//public static final Long SUBTYPE_ADD_HSFJOB = 203L;               // 添加Hsf任务
	//public static final Long SUBTYPE_ADD_SHELLJOB = 204L;             // 添加Shell任务
	//public static final Long SUBTYPE_ADD_DATATRACKINGJOB = 205L;      // 添加数据监控任务
    //public static final Long SUBTYPE_ADD_STOREDPROCEDUREJOB = 207L;   // 添加存储过程任务
    //public static final Long SUBTYPE_ADD_LINECOUNTJOB = 208L;         // 添加文件监控任务
	//public static final Long SUBTYPE_ADD_OTHERJOB = 221L;             // 添加其它任务

	//public static final Long SUBTYPE_MOD_HTTPJOB = 301L;              // 修改http任务
	//public static final Long SUBTYPE_MOD_HSFJOB = 303L;               // 修改Hsf任务
	//public static final Long SUBTYPE_MOD_SHELLJOB = 304L;             // 修改Shell任务
	//public static final Long SUBTYPE_MOD_DATATRACKINGJOB = 305L;      // 修改数据监控任务
    //public static final Long SUBTYPE_MOD_STOREDPROCEDUREJOB = 307L;   // 修改存储过程任务
    //public static final Long SUBTYPE_MOD_LINECOUNTJOB = 308L;         // 修改文件监控任务
	//public static final Long SUBTYPE_MOD_OTHERJOB = 321L;             // 修改其它任务

	//JOBACTION
	public static final Long SUBTYPE_ADD_JOB = 502L;                  // 添加任务
	public static final Long SUBTYPE_MOD_JOB = 503L;                  // 修改任务
	public static final Long SUBTYPE_DELETEJOB = 504L;                // 删除任务
	public static final Long SUBTYPE_TRIGGERJOB = 506L;               // 立即执行任务

	public static final Long SUBTYPE_TRIGGER_RESUME = 604L;           // 恢复触发器
	public static final Long SUBTYPE_TRIGGER_PAUSE = 605L;            // 暂停触发器
	
	//INSTANCEACTION
	public static final Long SUBTYPE_SCHEDULE_START = 701L;           // 启动实例
	public static final Long SUBTYPE_SCHEDULE_PAUSE = 702L;           // 暂停实例
	public static final Long SUBTYPE_SCHEDULE_STOP = 703L;            // 停止实例
	public static final Long SUBTYPE_SCHEDULE_SAFESTOP = 704L;        // 安全停止实例

	public static final Long SUBTYPE_JOB_START = 901L;                // 任务开始
	public static final Long SUBTYPE_JOB_START_RECOVERING = 902L;     // 过期任务开始
	public static final Long SUBTYPE_JOB_START_ASYN = 905L;           // 异步任务开始
	public static final Long SUBTYPE_JOB_START_RECOVERING_ASYN = 907L;// 过期的异步任务开始
	public static final Long SUBTYPE_JOB_END = 921L;                  // 任务结束
	public static final Long SUBTYPE_JOB_END_RECOVERING = 922L;       // 过期任务结束
    public static final Long SUBTYPE_JOB_END_ASYN = 925L;             // 异步任务结束
    public static final Long SUBTYPE_JOB_END_RECOVERING_ASYN = 927L;  // 过期的异步任务结束
	public static final Long SUBTYPE_JOB_FAILED = 931L;               // 任务失败
	public static final Long SUBTYPE_JOB_FAILED_RECOVERING = 932L;    // 过期任务失败
    public static final Long SUBTYPE_JOB_FAILED_ASYN = 935L;          // 异步任务失败
    public static final Long SUBTYPE_JOB_FAILED_RECOVERING_ASYN = 937L;// 过期的异步任务失败


	public static final Long SUBTYPE_JOB_DATATRACKING_WARNING = 1001L;// 监控任务告警
	public static final Long SUBTYPE_JOB_DATATRACKING_FAILED = 1005L;// 监控任务失败
	
	public static final Long SUBTYPE_USER_ADD = 1101L;// 用户创建
	public static final Long SUBTYPE_USER_MOD = 1103L;// 用户修改
	public static final Long SUBTYPE_USER_DEL = 1105L;// 用户删除
	
	public static final Long SUBTYPE_CONFIG_MOD = 1203L;// 配置修改

	// 日志type和name对应关系
	public static TreeMap<Long, String> TYPE_NAME = new TreeMap<Long, String>() {
		{
/*			put(SUBTYPE_ADD_HTTPJOB, "添加http任务");
			put(SUBTYPE_ADD_HSFJOB, "添加hsf任务");
			put(SUBTYPE_ADD_SHELLJOB, "添加shell任务");
			put(SUBTYPE_ADD_DATATRACKINGJOB, "添加数据监控任务");
			put(SUBTYPE_ADD_LINECOUNTJOB, "添加文件监控任务");
			put(SUBTYPE_ADD_STOREDPROCEDUREJOB, "添加存储过程任务");
			put(SUBTYPE_ADD_OTHERJOB, "添加其它任务");
			
			put(SUBTYPE_MOD_HTTPJOB, "修改http任务");
			put(SUBTYPE_MOD_HSFJOB, "修改hsf任务");
			put(SUBTYPE_MOD_SHELLJOB, "修改shell任务");
			put(SUBTYPE_MOD_DATATRACKINGJOB, "修改数据监控任务");
			put(SUBTYPE_MOD_LINECOUNTJOB, "修改文件监控任务");
			put(SUBTYPE_MOD_STOREDPROCEDUREJOB, "修改存储过程任务");
			put(SUBTYPE_MOD_OTHERJOB, "修改其它任务");*/

			put(SUBTYPE_ADD_JOB, "添加任务");
			put(SUBTYPE_MOD_JOB, "修改任务");
			put(SUBTYPE_DELETEJOB, "删除任务");
			put(SUBTYPE_TRIGGERJOB, "立即执行任务");
			put(SUBTYPE_TRIGGER_RESUME, "恢复触发器");
			put(SUBTYPE_TRIGGER_PAUSE, "暂停触发器");
			
			put(SUBTYPE_SCHEDULE_START, "启动实例");
			put(SUBTYPE_SCHEDULE_PAUSE, "暂停实例");
			put(SUBTYPE_SCHEDULE_STOP,  "停止实例");
			put(SUBTYPE_SCHEDULE_SAFESTOP, "安全停止实例");

			put(SUBTYPE_JOB_START, "任务开始");
			put(SUBTYPE_JOB_START_RECOVERING, "过期的任务开始");
			put(SUBTYPE_JOB_START_ASYN, "异步任务开始");
			put(SUBTYPE_JOB_START_RECOVERING_ASYN, "过期的异步任务开始");
			put(SUBTYPE_JOB_END, "任务结束");
			put(SUBTYPE_JOB_END_RECOVERING, "过期的任务结束");
            put(SUBTYPE_JOB_END_ASYN, "异步任务结束");
            put(SUBTYPE_JOB_END_RECOVERING_ASYN, "过期的异步任务结束");
			put(SUBTYPE_JOB_FAILED, "任务失败");
			put(SUBTYPE_JOB_FAILED_RECOVERING, "过期的任务失败");
            put(SUBTYPE_JOB_FAILED_ASYN, "异步任务失败");
            put(SUBTYPE_JOB_FAILED_RECOVERING_ASYN, "过期的异步任务失败");

			put(SUBTYPE_JOB_DATATRACKING_WARNING, "监控任务告警");
			put(SUBTYPE_JOB_DATATRACKING_FAILED, "监控任务失败");
			
			put(SUBTYPE_USER_ADD, "用户创建");
			put(SUBTYPE_USER_MOD, "用户修改");
			
			put(SUBTYPE_USER_DEL, "用户删除");
			put(SUBTYPE_CONFIG_MOD, "配置修改");

		}
	};

	/**
	 * USER_SYS
	 */
	public static final String USER_SYS = "SYS"; // 系统用户

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mseq")
//	@SequenceGenerator(name = "mseq", sequenceName = "SEQ_es_LOGS", allocationSize = 1)
	private Long id;
	/**
	 * OPTYPE
	 */
	@Column(name="OPTYPE")
	private Long optype;
	/**
	 * OPNAME
	 */
	@Column(name="OPNAME")
	private String opname;
	/**
	 * OPSUBTYPE
	 */
	@Column(name="OPSUBTYPE")
	private Long opsubtype;
	/**
	 * OPSUBNAME
	 */
	@Column(name="OPSUBNAME")
	private String opsubname;
	/**
	 * OPDETAIL
	 */
	@Column(name="OPDETAIL")
	private String opdetail;
	/**
	 * OPTIME
	 */
	@Column(name="OPTIME")
	private Date optime;
	/**
	 * OPUSER
	 */
	@Column(name="OPUSER")
	private String opuser;

    public String getShortOpname() {
        if (opname != null && opname.getBytes().length > 20) {
            return bSubstring(opname,19) + "...";
		}
		return this.opname;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOptype() {
		return this.optype;
	}

	public void setOptype(Long optype) {
		this.optype = optype;
	}

	public String getOpname() {
		return this.opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}

	public Long getOpsubtype() {
		return this.opsubtype;
	}

	public void setOpsubtype(Long opsubtype) {
		this.opsubtype = opsubtype;
	}

	public String getOpsubname() {
		return this.opsubname;
	}

	public void setOpsubname(String opsubname) {
		this.opsubname = opsubname;
	}

	public String getOpdetail() {
		return this.opdetail;
	}

	public void setOpdetail(String opdetail) {
		if (opdetail != null && opdetail.getBytes().length > 1024) {
			this.opdetail = bSubstring(opdetail,1023);
		} else {
			this.opdetail = opdetail;
		}
	}

	public Date getOptime() {
		return this.optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public String getOpuser() {
		return opuser;
	}

	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}

	public static TreeMap<Long, String> getTYPE_NAME() {
		return TYPE_NAME;
	}

	public static void setTYPE_NAME(TreeMap<Long, String> tYPENAME) {
		TYPE_NAME = tYPENAME;
	}

}
