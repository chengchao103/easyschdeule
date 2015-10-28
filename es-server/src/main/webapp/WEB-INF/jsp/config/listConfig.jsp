<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript">
    function doSearch() {
        $('#searchform').submit();
        return true;
    }
    function doReset() {
        $('#configkey').val("");
        $('#configvalue').val("");
    }
</script>
<form action="listConfig.action" method="post" id="searchform">
    <div id="content" class="content content-right">
        <div class="layout grid-default" id="grid-default">
            <!-- 主栏 -->
            <div class="col-main" id="col-main">
                <div class="main-wrap" id="main-wrap">
                    <!-- 搜索框 -->
                    <div class="search-wrapper">
                        <table>
                            <tr>
                                <th><label>参数:</label></th>
                                <td>
                                    <input type="text" name="query.configkey" value="<s:property value="query.configkey"/>" id="configkey"/>
                                </td>
                            </tr>
                            <tr>
                                <th><label>参数值:</label></th>
                                <td>
                                    <input type="text" name="query.configvalue" value="<s:property value="query.configvalue"/>" id="configvalue"/>
                                </td>
                            </tr>
                            <tr>
                                <th>&nbsp;</th>
                                <td colspan="2">
                                    <input type="button" value="查询" class="btn_search" onclick='doSearch();'>
                                    <input id="btnreset" type="button" value="重置" class="btn_search" onclick='doReset();'>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!-- 搜索框 结束 -->
                    <div class="message-wrapper" id="message-wrapper">
                        <div class="message-title">
                            <ul>
                                <li class="cur"><a href="listConfig"><span>参数列表</span></a></li>
                                <li><a href="viewAddConfig"><span>参数添加</span></a></li>
                                <li><a href="viewOtherConfig"><span>系统参数</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
                                <th>参数KEY</th>
                                <th>参数VALUE</th>
                                <th>描述</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="#request.configs" status="status">
                                <tr>
                                    <td><input type="checkbox" class="checkRow" name="checkRow"/></td>
                                    <td width="280px" nowrap><s:property value="configkey"/></td>
                                    <td width="250px" nowrap><s:property value="shortConfigvalue"/></td>
                                    <td nowrap><s:property value="shortDescription"/></td>
                                    <td nowrap>
                                        <a href="viewUpdateConfig.action?id=<s:property value="configkey" />">修改</a>
                                        <a href="deleteConfig.action?id=<s:property value="configkey" />" onclick="return confirm('确定要删除吗？')">删除</a>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <s:if test="#request.configs.size > 0">
                            <%@include file="../include/pageNav.jsp" %>
                        </s:if>
                    </div>
                    <!-- 表格 结束 -->
                </div>
            </div>
            <!-- 主栏结束 -->
        </div>
        <!-- 布局 结束 -->
    </div>
    <!-- 内容 结束-->
</form>
<%@include file="../include/bottom.jsp" %>