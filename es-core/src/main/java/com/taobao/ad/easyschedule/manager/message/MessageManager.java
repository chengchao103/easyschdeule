/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.manager.message;

public interface MessageManager {

	/**
	 * @param jobName
	 * @param jobGroup
	 * @param message
	 * @param content
	 */
	public void sendSuccessMessage(String jobName, String jobGroup, String message, String content);

	/**
	 * @param jobName
	 * @param jobGroup
	 * @param message
	 * @param content
	 */
	public void sendWarningMessage(String jobName, String jobGroup, String message, String content);

	/**
	 * @param jobName
	 * @param jobGroup
	 * @param message
	 * @param content
	 */
	public void sendFailMessage(String jobName, String jobGroup, String message, String content);

	/**
	 * @param jobName
	 * @param jobGroup
	 * @param message
	 * @param content
	 * @param repeatNum
	 */
	public void sendFailMessage(String jobName, String jobGroup, String message, String content, int repeatNum);
}
