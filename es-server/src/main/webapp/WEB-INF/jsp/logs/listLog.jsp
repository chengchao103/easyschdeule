<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="com.taobao.ad.easyschedule.dataobject.LogsDO" %>
<%@include file="../include/header.jsp" %>
<script  src="<%=request.getContextPath()%>/share/js/jquery/fancyzoom.js"></script>
<script  src="<%=request.getContextPath()%>/share/js/jquery/jquery.autocomplete.js" ></script>
<link    href="<%=request.getContextPath() %>/share/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
    function doSearch() {
        $('#listLogForm').submit();
        return true;
    }
    function doReset() {
        $('#optype').val("");
        $('#opsubtype').val("");
        $('#opsubname').val("");
        $('#opuser').val("");
        return true;
    }

    function deleteAllLogs() {
        if (confirm("确认删除所有任务历史?")) {
            return true;
        } else {
            return false;
        }
    }
</script>
<form action="listLog.action" method="post" id="listLogForm">
    <div id="content" class="content content-right">
        <div class="layout grid-default" id="grid-default">
            <!-- 主栏 -->
            <div class="col-main" id="col-main">
                <div class="main-wrap" id="main-wrap">
                    <!-- 搜索框 -->
                    <div class="search-wrapper">
                        <table>
                            <tr>
                                <th><label>标志:</label></th>
                                <td>
                                    <select id="optype" name="query.optype" onchange="doSearch();">
                                        <option selected value="">
                                            全部
                                        </option>
                                        <option value="1" <s:if test='1==query.optype'>selected</s:if>>
                                            成功
                                        </option>
                                        <option value="2" <s:if test='2==query.optype'>selected</s:if>>
                                            失败
                                        </option>
                                        <option value="3" <s:if test='3==query.optype'>selected</s:if>>
                                            告警
                                        </option>
                                    </select>
                                </td>
                                <th><label>类别:</label></th>
                                <td>
                                    <select id="opsubtype" name="query.opsubtype"
                                            onchange="doSearch();">
                                        <option selected value="">
                                            全部
                                        </option>
                                        <s:iterator value="query.TYPE_NAME">
                                            <option value="<s:property value="key"/>"
                                                    <s:if test='key==query.opsubtype'>selected</s:if>>
                                                <s:property value="key"/>
                                                :
                                                <s:property value="value"/>
                                            </option>
                                        </s:iterator>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th><label>名称:</label></th>
                                <td>
                                    <input type="text" id="opsubname" name="query.opsubname" value="<s:property value="query.opsubname" />">
                                </td>
                                <th><label>操作人:</label></th>
                                <td>
                                    <input type="text" id="opuser" name="query.opuser" value="<s:property value="query.opuser" />">
                                </td>
                            </tr>
                            <tr>
                                <th></th>
                                <td colspan="3">
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
                                <li class="cur"><a href="listLog"><span>历史列表</span></a></li>
                                <li><a href="deleteAllLogs" onclick="return deleteAllLogs();"><span>清除历史</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
                                <th nowrap>序号</th>
                                <th nowrap>标志</th>
                                <th nowrap>类别</th>
                                <th nowrap>实例</th>
                                <th nowrap>名称</th>
                                <th nowrap>操作时间</th>
                                <th nowrap>操作人</th>
                                <th nowrap>详情</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="#request.logss" status="status">
                                <tr>
                                    <td><input type="checkbox" class="checkRow" name="checkRow"/></td>
                                    <td>
                                        <s:property value="id"/>
                                    </td>
                                    <td nowrap style="padding: 1px 9px 1px;">
                                        <s:if test="optype == 1">
                                            <img src="<%=request.getContextPath()%>/share/images/icon_right.gif"
                                                 width="20" height="20" border="0">
                                        </s:if>
                                        <s:if test="optype == 2">
                                            <img src="<%=request.getContextPath()%>/share/images/icon_error.gif"
                                                 width="20" height="20" border="0">
                                        </s:if>
                                        <s:if test="optype == 3">
                                            <img src="<%=request.getContextPath()%>/share/images/icon_warning.gif"
                                                 width="20" height="20" border="0">
                                        </s:if>
                                    </td>
                                    <td nowrap>
                                        <s:iterator value="query.TYPE_NAME">
                                            <s:if test='key==opsubtype'>
                                                <s:property value="value"/>
                                            </s:if>
                                        </s:iterator>
                                    </td>
                                    <td nowrap>
                                        <s:property value="shortOpname"/>
                                    </td>
                                    <td nowrap>
                                        <s:property value="opsubname"/>
                                    </td>
                                    <td nowrap>
                                        <s:date name="optime" format="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td nowrap>
                                        <s:property value="opuser"/>
                                    </td>
                                    <td nowrap>
                                        <div class="show_photo">
                                            <a href="#photo_<s:property value="#status.index"/>">详情</a>
                                        </div>
                                        <div id="photo_<s:property value="#status.index"/>">
                                            <!--详情开始-->
                                            <div style="width: 480px; height: 200px; visibility: visible; overflow: auto; top: 0px; left: 0px; line-height: 22px;">
                                                <b>日志详细：</b>
                                                <br>
                                                instanceid:
                                                <s:property value="opname"/>
                                                <br>
                                                <s:property escape="false" value="opdetail"/>
                                            </div>
                                            <!--详情结束-->
                                        </div>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <s:if test="#request.logss.size > 0">
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
<script type="text/javascript">
    $(document).ready(function() {
        $('div.show_photo a').fancyZoom({scaleImg: true, closeOnClick: true,directory:'<%=request.getContextPath()%>/share/images'});
        
        $("#opsubname").autocomplete("logsAjax/findAllJobGroupAndName", {
            minChars:0,
            mustMatch:true,
            matchContains:true,
            matchSubset:true,
            matchContains:true,
            extraParams:{name:function(){return encodeURIComponent($("#opsubname").val());}},
            scroll:false,
            dataType:'json',
            parse:function (data) {
                var machines = data.resultMap.groupNames;
                var rows = [];
                for (var i = 0; i < machines.length; i++) {
                    rows[rows.length] = {
                        data:machines[i],
                        value:decodeURIComponent(machines[i]),
                        result:decodeURIComponent(machines[i])
                    };
                }
                return rows;
            },
            formatItem:function (row, i, max) {
                return decodeURIComponent(row);
            }
        });
    });
</script>
<%@include file="../include/bottom.jsp" %>

