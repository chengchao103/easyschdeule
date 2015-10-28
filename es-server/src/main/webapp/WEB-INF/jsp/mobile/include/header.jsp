<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="com.taobao.ad.easyschedule.commons.Constants" %>
<!DOCTYPE html> 
<html>
	<head>
	<meta charset="utf-8">
	<title>定时任务平台</title> 
	<meta name="viewport" content="width=device-width, initial-scale=1"> 
	<link rel="stylesheet" href="<%=request.getContextPath()%>/jquery.mobile-1.1.0/jquery.mobile-1.1.0.min.css" />
	<script src="<%=request.getContextPath()%>/jquery.mobile-1.1.0/jquery-1.7.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/jquery.mobile-1.1.0/jquery.mobile-1.1.0.min.js"></script>
</head>
<body>
<div data-role="header" data-theme="c">
	<h1>定时任务平台</h1>
	<a href="#" data-icon="home" data-direction="reverse">首页</a>
	<a href="#" data-icon="grid" data-rel="dialog" data-transition="fade">帮助</a>
</div><!-- /header -->

