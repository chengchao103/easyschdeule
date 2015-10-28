/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class BiddingSessionRequest extends HttpServletRequestWrapper {
    private BiddingSession biddingSession = null;

    /**
     * 返回经过包装的REQUEST
     * @param request
     */
    public BiddingSessionRequest(HttpServletRequest request,BiddingHttpContext biddingHttpContext) {
        super(request);
        biddingSession = new BiddingSession(this,biddingHttpContext);
    }

    /**
     * 返回经过封装的SESSION
     */
    public HttpSession getSession() {
        return this.biddingSession;
    }

    public HttpSession getSession(boolean create) {
    	return biddingSession;
    }

    /**
     * 如果通过proxy，servlet容器无法正确取到客户端的ip，故先尝试从header中取
     * @author wangtao
     */
	@Override
	public String getRemoteAddr() {
		String ip = getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = super.getRemoteAddr();
		}
		return ip;
	}
}
