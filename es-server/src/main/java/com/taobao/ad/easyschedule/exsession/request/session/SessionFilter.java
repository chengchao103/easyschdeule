/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.exsession.request.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionFilter implements Filter {
	private FilterConfig filterConfig;

	/**
	 * 执行filter.
	 * 
	 * @param request
	 *            HTTP请求
	 * @param response
	 *            HTTP响应
	 * @param chain
	 *            filter链
	 * 
	 * @throws IOException
	 *             处理filter链时发生输入输出错误
	 * @throws ServletException
	 *             处理filter链时发生的一般错误
	 */
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 对于重入的filter，不消化exception。
		// 在weblogic中，servlet forward到jsp时，jsp仍会调用此filter，而jsp抛出的异常就会被该filter捕获。
		if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse) || (request.getAttribute(getClass().getName()) != null)) {
			chain.doFilter(request, response);

			return;
		}
		// 防止重入.
		request.setAttribute(getClass().getName(), Boolean.TRUE);
		// 执行子类的doFilter
		HttpServletRequest req = (HttpServletRequest) request;

		HttpServletResponse res = (HttpServletResponse) response;

		doFilter(req, res, chain);

	}

	/**
	 * 取得filter的配置信息。
	 * 
	 * @return <code>FilterConfig</code>对象
	 */
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	/**
	 * 取得servlet容器的上下文信息。
	 * 
	 * @return <code>ServletContext</code>对象
	 */
	public ServletContext getServletContext() {
		return getFilterConfig().getServletContext();
	}

	/**
	 * 初始化filter。
	 * 
	 * @throws ServletException
	 *             如果初始化失败
	 */
	public void init() throws ServletException {
	}

	/**
	 * 清除filter.
	 */
	public void destroy() {
		filterConfig = null;
	}

	/**
	 * 初始化filter。
	 * 
	 * @param filterConfig
	 *            filter的配置信息
	 * 
	 * @throws ServletException
	 *             如果初始化失败
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		init();
	}

	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		BiddingHttpContext context = null;
		try {
			ServletContext servletContext = ((HttpServletRequest) request).getSession().getServletContext();

			context = new BiddingHttpContext(request, response, servletContext);

			chain.doFilter(context.getRequest(), context.getResponse());
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
