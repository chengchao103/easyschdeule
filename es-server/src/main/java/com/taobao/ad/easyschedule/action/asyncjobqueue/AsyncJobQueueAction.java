/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.action.asyncjobqueue;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.taobao.ad.easyschedule.bo.asyncjob.AsyncJobBO;
import com.taobao.ad.easyschedule.bo.code.ICodeBO;
import com.taobao.ad.easyschedule.dataobject.AsyncJobQueueDO;
import com.taobao.ad.easyschedule.dataobject.CodeDO;

public class AsyncJobQueueAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private AsyncJobQueueDO query;
	private AsyncJobBO asyncJobBO;
	private List<CodeDO> groups;
	private ICodeBO codeBO;

	public String listAsyncJobQueues() {
		if (query == null) {
			query = new AsyncJobQueueDO();
		}
		groups = codeBO.getCodesByKey(CodeDO.CODE_KEY_JOB_GROUP);
		List<AsyncJobQueueDO> asyncJobQueues = asyncJobBO.findAsyncJobs(query);
		ActionContext.getContext().put("asyncJobQueues", asyncJobQueues);
		return SUCCESS;
	}

	public void setAsyncJobBO(AsyncJobBO asyncJobBO) {
		this.asyncJobBO = asyncJobBO;
	}

	public void setCodeBO(ICodeBO codeBO) {
		this.codeBO = codeBO;
	}

	public AsyncJobQueueDO getQuery() {
		return query;
	}

	public void setQuery(AsyncJobQueueDO query) {
		this.query = query;
	}

	public List<CodeDO> getGroups() {
		return groups;
	}

	public void setGroups(List<CodeDO> groups) {
		this.groups = groups;
	}

}