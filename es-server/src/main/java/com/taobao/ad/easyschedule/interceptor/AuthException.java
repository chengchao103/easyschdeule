/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.interceptor;

import java.io.PrintStream;
import java.io.PrintWriter;


public class AuthException extends RuntimeException {

	private static final long serialVersionUID = 5654004314800296844L;
	private Throwable cause;
	/**
     * 构造函数
     * @param msg 指定异常描述信息
     */
    public AuthException(String msg) {
		super(msg);
	}

    /**
     * 构造函数
     * @param ex 异常对象
     */
    public AuthException(Throwable ex) {
		this.cause = ex;
	}

    /**
     * 构造函数
     * @param msg 指定异常描述信息
     * @param ex  异常对象
     */
	public AuthException(String msg, Throwable ex) {
		super(msg);
		this.cause = ex;
	}

    /**
     * 得到异常对象
     * @return 异常对象
     */
	public Throwable getCause() {
		return (this.cause == this ? null : this.cause);
	}

    /**
     * 得到异常描述信息
     * @return 异常描述信息
     */
	public String getMessage() {
		if (this.cause == null || this.cause == this) {
			return super.getMessage();
		}
		else {
			return super.getMessage() + "; Schedule exception is " + this.cause.getClass().getName() +
					": " + this.cause.getMessage();
		}
	}

    /**
     * 打印异常堆栈信息
     * @param ps    输出Stream
     */
	public void printStackTrace(PrintStream ps) {
		if (this.cause == null || this.cause == this) {
			super.printStackTrace(ps);
		}
		else {
			ps.println(this);
			this.cause.printStackTrace(ps);
		}
	}

    /**
     * 打印异常堆栈信息
     * @param pw    输出Writter
     */
	public void printStackTrace(PrintWriter pw) {
		if (this.cause == null || this.cause == this) {
			super.printStackTrace(pw);
		}
		else {
			pw.println(this);
			this.cause.printStackTrace(pw);
		}
	}

}
