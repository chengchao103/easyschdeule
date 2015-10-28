/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.base;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PageInfo extends BaseDO {

	public PageInfo() {
		toPage = 1;
		perPageSize = 10;
		totalItem = 0;
		returnCount = 0;
	}

	public boolean isFirstPage() {
		return toPage == 1;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public int getToPage() {
		if (totalItem == 0 && (returnCount > 0 || toPage > 1))
			return toPage;
		if (toPage > getTotalPage())
			return getTotalPage();
		else
			return toPage;
	}

	public void setToPage(int toPage) {
		this.toPage = toPage;
	}

	public int getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public int getPreviousPage() {
		int back = toPage - 1;
		if (back <= 0)
			back = 1;
		return back;
	}

	public boolean isLastPage() {
		return getTotalPage() == toPage;
	}

	public int getNextPage() {
		int back = toPage + 1;
		if (back > getTotalPage())
			back = getTotalPage();
		return back;
	}

	public int getTotalPage() {
		if (totalItem == 0)
			return 0;
		int result = totalItem / perPageSize;
		if (totalItem % perPageSize != 0)
			result++;
		return result;
	}

	public int validateToPage(int toPage) {
		int pageNum = toPage;
		if (pageNum < 1)
			pageNum = 1;
		if (pageNum > getTotalPage())
			pageNum = getTotalPage();
		return pageNum;
	}

	public void buildPageInfo(Map<Object, Object> params) {
		String pageSize = (String) params.get("perPageSize");
		String toPage = (String) params.get("toPage");
		String orderStr = (String) params.get("orderStr");
		if (StringUtils.isNotBlank(pageSize))
			setPerPageSize(Integer.parseInt(pageSize));
		if (StringUtils.isNotBlank(toPage)) {
			int page = 1;
			try {
				page = Integer.parseInt(toPage);
			} catch (Exception e) {
			}
			setToPage(page);
		}
		if (StringUtils.isNotBlank(orderStr))
			setOrderStr(orderStr);
	}

	public void validatePageInfo() {
		setToPage(validateToPage(toPage));
	}

	public Integer getEndRow() {
		if (toPage > 0 && perPageSize > 0)
			return toPage * perPageSize;
		else
			return 10;
	}

	public Integer getStartRow() {
		/*
		 * baimei: 
		 * ibatis:maysql=Integer((toPage - 1) * perPageSize);
		 * ibatis:oralce=Integer((toPage - 1) * perPageSize + 1);
		 */
		if (toPage > 0 && perPageSize > 0)
			return ((toPage - 1) * perPageSize);
		else
			return 0;
	}

	protected String getSQLBlurValue(String value) {
		if (value == null)
			return null;
		else
			return (new StringBuilder()).append(value).append('%').toString();
	}

	protected String formatDate(String datestring) {
		if (datestring == null || datestring.equals(""))
			return null;
		else
			return (new StringBuilder()).append(datestring).append(" 00:00:00").toString();
	}

	protected String addDateEndPostfix(String datestring) {
		if (datestring == null || datestring.equals(""))
			return null;
		else
			return (new StringBuilder()).append(datestring).append(" 23:59:59").toString();
	}

	protected String addDateStartPostfix(String datestring) {
		if (datestring == null || datestring.equals(""))
			return null;
		else
			return (new StringBuilder()).append(datestring).append(" 00:00:00").toString();
	}

	public int getDesiredPage() {
		return toPage;
	}

	public int getReturnCount() {
		return returnCount;
	}

	public void setReturnCount(int returnCount) {
		this.returnCount = returnCount;
	}

	private static final long serialVersionUID = 3258128059449226041L;
	private String orderStr;
	private int toPage;
	private int perPageSize;
	private int totalItem;
	private int returnCount;
}
