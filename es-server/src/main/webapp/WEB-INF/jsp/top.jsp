<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>任务管理平台</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/share/css/base.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/share/css/style.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/share/js/jquery/jquery.min.js"></script>
</head>
<body>

<!--浮层公告开始-->
<s:property escape="false" value="topNotifyMsg"/>
<!--浮层公告结束-->
<div id="page" class="union orange">

    <!-- 页头 -->
    <div id="header" class="header">
        <!-- 头部left  -->
        <div class="header-left">
            <div id="page-info" class="page-info">
                <h1><img src="<%= request.getContextPath() %>/share/img/logo_b.png" alt="<s:property value="@com.taobao.ad.easyschedule.commons.Constants@DEPLOY_MODE" />"/>
                </h1>
            </div>
        </div>
        <!-- 头部left end -->
        <s:if test="@com.taobao.ad.easyschedule.commons.Constants@DEPLOY_MODE != 'real'">
			<font size="6" color="#FF0000"><s:property value="@com.taobao.ad.easyschedule.commons.Constants@DEPLOY_MODE" /> mode</font>
		</s:if>
        <!-- 头部right start -->
        <div class="header-right">
            <div id="quick-link" class="quick-link" style="visibility:hidden;">
                <ul>
                    <li><a href="http://bugfree.corp.taobao.com/" target="_blank;" title="http://bugfree.corp.taobao.com/"><span>Bugfree</span></a></li>
                    <li class="cur"><a href="#" title=""><span>任务管理平台</span></a></li>
                    <li><a href="http://bo.alimama.com/" target="_blank;" title="http://bo.alimama.com/"><span>BO系统</span></a></li>
                    <li><a href="http://crm.simba.taobao.com/" target="_blank;" title="http://crm.simba.taobao.com/"><span>辛巴后台</span></a></li>
                </ul>
                <i class="i_l"></i>
                <i class="i_r"></i>
            </div>
            <div class="clearing"></div>
            <div id="about-user" class="about-user">
            
            <a href="<%= request.getContextPath() %>/m/listJob" target="_blank">移动版</a>
                     <!--   <a href="http://es.taobao.com:9999/" target="_blank">线上</a> -->
            
            
                <span>您好，${operName}！</span>
					<span><b>[</b>角色：
                        <select>
                            <s:iterator value="roles">
                                <option><s:property value="descn"/></option>
                            </s:iterator>
                        </select><b>|</b>
	                    <a href="<%= request.getContextPath() %>/adminLogout" target="_parent">退出</a><b>]</b>
					</span>
            </div>
        </div>
        <!-- 头部right end -->
        <div class="clearing"></div>
    </div>
    <!-- 页头 结束 -->
</div>
</body>
</html>