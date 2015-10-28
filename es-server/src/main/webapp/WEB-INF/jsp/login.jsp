<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/login.css"/>
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/share/images/es.ico" type="image/x-icon" /> 
    
</head>

<body class="login_bg">
<form name="adminLoginForm" action="adminLogin" method="post">
<div class="login_crm">
	<table class="login_tb">
		<tr>
			<td><font size="2"  >用户名: </font></td>
			<td><input name="adminName" type="text" class="login_ip" value="<s:if test="@com.taobao.ad.easyschedule.commons.Constants@DEPLOY_MODE != 'real'">admin</s:if>" tabindex="1" /></td>
			<td rowspan="2"><input type="submit" value="" class="login_sm" tabindex="3" /></td>
		</tr>
		<tr>
			<td><font size="2"  >密    码:</font></td>
			<td><input name="adminPassword" type="password" class="login_ip" tabindex="2" value="<s:if test="@com.taobao.ad.easyschedule.commons.Constants@DEPLOY_MODE != 'real'">aaa111</s:if>"  autocomplete="off" /></td>
		</tr>
        <tr >
            <td></td>
            <td></td>
            <td></td>
        </tr>
	</table>
	<div class="login_cp">Copyright(c) 2009 www.taobao.com all rights reserved<br />Powered by es</div>

</div>
</form>
</body>
<script type="text/javascript"> 
	document.adminLoginForm.j_username.focus();
</script>
</html>
