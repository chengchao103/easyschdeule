<%@ page language="java"  pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="" />
    <meta name="keywords" content="" />
	<title>定时任务平台</title>
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/share/images/es.ico" type="image/x-icon" /> 
</head>
<frameset rows="87,*" border="0" frameborder="0">
  <frame id="top" name="top" src="top" scrolling="no">
  <frameset id="mainFrame" name="mainFrame" frameborder="0" border="0" framespacing="0" cols="196,12,*">
    <frame id="menu" name="menu" src="menu" scrolling="no">
	<frame id="dragZoom" name="dragZoom" src="middle" border="0" frameborder="0" scrolling="no">
    <frame id="rightframe" name="rightframe" src="listJob">
  </frameset>
</frameset>
<noframes>
<body>
你老的浏览器版本太老啦,赶快换新吧!
</body>
</noframes>
</html>