/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BiddingHttpContext {
    private BiddingSessionRequest  request;
    private HttpServletResponse response;
    private ServletContext        context;
    public BiddingHttpContext(HttpServletRequest request, HttpServletResponse response,
                             ServletContext context) {
    	this.context  = context;
    	this.response = response;
        this.request  = new BiddingSessionRequest(request,this);

    }

    public HttpServletRequest getRequest() {
        return  request;
    }

    public void setRequest(BiddingSessionRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

}
