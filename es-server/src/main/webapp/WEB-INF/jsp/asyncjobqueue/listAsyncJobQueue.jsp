<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript">
    function doSearch() {
        $('#searchform').submit();
        return true;
    }
    function doReset() {
        $('#jobname').val("");
        $('#jobgroup').val("");
    }
</script>
<form action="listAsyncJobQueues.action" method="post" id="searchform">

    <div id="content" class="content content-right">
        <div class="layout grid-default" id="grid-default">
            <!-- 主栏 -->
            <div class="col-main" id="col-main">
                <div class="main-wrap" id="main-wrap">
                    <!-- 搜索框 -->
                    <div class="search-wrapper">
                        <table>
                             <tr>
                                <th><label>组:</label></th>
                                 <td>
                                    <select id="jobgroup"
                                            name="query.jobgroup">
                                        <option value="">--请选择--</option>
                                        <s:iterator value="groups">
                                            <option value="<s:property value="keycode"/>" <s:if test='keycode==query.jobgroup'>selected</s:if> ><s:property value="keyname"/></option>
                                        </s:iterator>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th><label>名称:</label></th>
                                <td>
                                    <input type="text" name="query.jobname" value="<s:property value="query.jobname"/>" id="jobname"/>
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
                                <li class="cur"><a href="listCode"><span>异步任务队列列表</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
                                <th>组</th>
                                <th>名称</th>
                                <th>等待回调数</th>
                                <th>任务开始时间</th>
                                <th>最晚完成时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="#request.asyncJobQueues" status="status">
                                <tr>
                                    <td><input type="checkbox" class="checkRow" name="checkRow"/></td>
                                     <td nowrap>
                                         <s:iterator value="groups">
                                            <s:if test='keycode==jobgroup'><s:property value="keyname"/></s:if>
                                        </s:iterator>
                                    </td> 
                                    <td><s:property value="jobname"/></td>
                                    <td><s:property value="waitCallNum"/></td>
                                    <td> <s:date name="sTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
                                    <td> <s:date name="cTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <s:if test="#request.asyncJobQueues.size > 0">
                            <%@include file="../include/pageNav.jsp" %>
                        </s:if>
						<s:else>
							当前没有执行中的异步任务
						</s:else>
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