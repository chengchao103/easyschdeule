<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<div id="content" class="content content-right">
    <div class="layout grid-default" id="grid-default">
        <!-- 主栏 -->
        <div class="col-main" id="col-main">
            <div class="main-wrap" id="main-wrap">
                <div class="message-wrapper" id="message-wrapper">
                    <table>
                        <tr>
                            <td width="70px">
                            </td>
                            <td width="70px">
                                <img src="<%=request.getContextPath()%>/share/images/icon_error.gif" border="0">
                            </td>
                            <td>
                                操作错误。   
                            </td>
                            <td width="250px">
                            </td>
                        </tr>
                    </table>
                    <table>
                        <tr>
                            <td width="70px">
                            </td>
                            <td>
                            <s:actionerror cssStyle="color:red;"/>
                                <s:property value="exception.message"/><br>
                                <s:property value="exceptionStack"/>
                            </td>
                        </tr>
                    </table>
                    <div class="clearing"></div>
                </div>
                <!-- 表格 结束 -->
            </div>
        </div>
        <!-- 主栏结束 -->
    </div>
    <!-- 布局 结束 -->
</div>
<!-- 内容 结束-->
<%@include file="../include/bottom.jsp" %>