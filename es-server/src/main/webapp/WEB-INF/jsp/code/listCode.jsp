<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript">
    function doSearch() {
        $('#searchform').submit();
        return true;
    }
    function doReset() {
        $('#codekey').val("");
        $('#keycode').val("");
    }
</script>
<form action="listCode.action" method="post" id="searchform">

    <div id="content" class="content content-right">
        <div class="layout grid-default" id="grid-default">
            <!-- 主栏 -->
            <div class="col-main" id="col-main">
                <div class="main-wrap" id="main-wrap">
                    <!-- 搜索框 -->
                    <div class="search-wrapper">
                        <table>
                            <tr>
                                <th><label>KEY:</label></th>
                                <td>
                                    <input type="text" name="query.codekey" value="<s:property value="query.codekey"/>" id="codekey"/>
                                </td>
                            </tr>
                            <tr>
                                <th><label>KEYCODE:</label></th>
                                <td>
                                    <input type="text" name="query.keycode" value="<s:property value="query.keycode"/>" id="keycode"/>
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
                                <li class="cur"><a href="listCode"><span>编码列表</span></a></li>
                                <li><a href="viewAddCode"><span>编码添加</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
                                <th>CODEKEY</th>
                                <th>KEYCODE</th>
                                <th>KEYNAME</th>
                                <th>KEYDESC</th>
                                <th>SORTNUM</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="#request.codes" status="status">
                                <tr>
                                    <td><input type="checkbox" class="checkRow" name="checkRow"/></td>
                                    <td><s:property value="codekey"/></td>
                                    <td><s:property value="keycode"/></td>
                                    <td><s:property value="keyname"/></td>
                                    <td><s:property value="keydesc"/></td>
                                    <td><s:property value="sortnum"/></td>
                                    <td>
                                        <a href="viewUpdateCode.action?code.codekey=<s:property value="codekey" />&code.keycode=<s:property value="keycode" />">修改</a>
                                        <a href="deleteCode.action?code.codekey=<s:property value="codekey" />&code.keycode=<s:property value="keycode" />" onclick="return confirm('确定删除吗？')">删除</a>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <s:if test="#request.codes.size > 0">
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