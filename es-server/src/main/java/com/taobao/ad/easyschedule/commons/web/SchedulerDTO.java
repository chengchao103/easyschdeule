/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.web;


public class SchedulerDTO {
	private String schedulerInstanceId;
	private String schedulerName;
	private String runningSince;
	private String numJobsExecuted;
	private String persistenceType;
	private String threadPoolSize;
	private String version;
	private String state;
	private int stateCode;
	private String summary;

	/**
	 * Returns the numJobsExecuted.
	 * 
	 * @return String
	 */
	public String getNumJobsExecuted() {
		return numJobsExecuted;
	}

	/**
	 * Returns the persistenceType.
	 * 
	 * @return String
	 */
	public String getPersistenceType() {
		return persistenceType;
	}

	/**
	 * Returns the runningSince.
	 * 
	 * @return String
	 */
	public String getRunningSince() {
		return runningSince;
	}

	/**
	 * Returns the schedulerName.
	 * 
	 * @return String
	 */
	public String getSchedulerName() {
		return schedulerName;
	}

	/**
	 * Returns the state.
	 * 
	 * @return String
	 */
	public String getState() {
		return state;
	}

	/**
	 * Returns the threadPool.
	 * 
	 * @return String
	 */
	public String getThreadPoolSize() {
		return threadPoolSize;
	}

	/**
	 * Returns the version.
	 * 
	 * @return String
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the numJobsExecuted.
	 * 
	 * @param numJobsExecuted
	 *            The numJobsExecuted to set
	 */
	public void setNumJobsExecuted(String numJobsExecuted) {
		this.numJobsExecuted = numJobsExecuted;
	}

	/**
	 * Sets the persistenceType.
	 * 
	 * @param persistenceType
	 *            The persistenceType to set
	 */
	public void setPersistenceType(String persistenceType) {
		this.persistenceType = persistenceType;
	}

	/**
	 * Sets the runningSince.
	 * 
	 * @param runningSince
	 *            The runningSince to set
	 */
	public void setRunningSince(String runningSince) {
		this.runningSince = runningSince;
	}

	/**
	 * Sets the schedulerName.
	 * 
	 * @param schedulerName
	 *            The schedulerName to set
	 */
	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 *            The state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Sets the threadPool.
	 * 
	 * @param threadPool
	 *            The threadPool to set
	 */
	public void setThreadPoolSize(String threadPool) {
		this.threadPoolSize = threadPool;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            The version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Returns the summary.
	 * 
	 * @return String
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 * 
	 * @param summary
	 *            The summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSchedulerInstanceId() {
		return schedulerInstanceId;
	}

	public void setSchedulerInstanceId(String schedulerInstanceId) {
		this.schedulerInstanceId = schedulerInstanceId;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

}
