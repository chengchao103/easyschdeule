<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/share/js/jquery/fancyzoom.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.form.js"></script>

<script type="text/javascript">
    function doSearch() {
        $('#searchform').submit();
        return true;
    }
    function doReset() {
        $('#jobGroup').val("");
        $('#jobName').val("");
        return true;
    }
</script>
<title>告警撤销管理</title>

<form id="msgform" action="sendMsgAndProcessRepeatAlarm.action" method="post">
	<input type="hidden" id="waitingMsg" value="<s:property value="waitingMsg"/>"/>
	<input type="hidden" id="finishedMsg" value="<s:property value="finishedMsg"/>"/>
	<input type="hidden" id="jobGroup" name="jobGroup" value=""/>
	<input type="hidden" id="jobName" name="jobName" value=""/>
	<input type="hidden" id="sendMsg" name="sendMsg" value=""/>
	<input type="hidden" id="repeatStatus" name="repeatStatus" value=""/>
</form>
<form action="listRepeatAlarm.action" method="post" id="searchform">
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
                                    <select id="jobGroup"
                                            name="query.jobGroup">
                                        <option value="">--请选择--</option>
                                        <s:iterator value="groups">
                                            <option value="<s:property value="keycode"/>" <s:if test='keycode==query.jobGroup'>selected</s:if> ><s:property value="keyname"/></option>
                                        </s:iterator>
                                    </select>
                                </td>
                            </tr>
                              <tr>
                                <th><label>名称:</label></th>
                                <td>
                                    <input id="jobName" type="text" name="query.jobName" value="<s:property value="query.jobName"/>"/>
                                </td>
                                <th> </th>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                <th>&nbsp;</th>
                                <td>
                                    <input type="button" value="查询" class="btn_search" onclick='return doSearch();'>
                                    <input type="button" value="重置" class="btn_search" onclick='return doReset();'>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!-- 搜索框 结束 -->
                    <div class="message-wrapper" id="message-wrapper">
						<div class="message-title">
                            <ul>
                                <li class="cur"><a href="listJob" target="rightframe"><span>告警撤销管理</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
                                <th nowrap>组</th>
                                <th nowrap>名称</th>
                                <th nowrap>任务失败时间</th>
                                <th nowrap>剩余重复告警次数</th>
                                <th nowrap>发送状态</th>
                                <th nowrap>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="list" status="status">
                                <tr>
                                    <td><input type="checkbox" class="checkRow" name="checkRow"/></td>
                                    <td nowrap>
                                        <s:iterator value="groups">
                                            <s:if test='keycode==jobGroup'><s:property value="keyname"/></s:if>
                                        </s:iterator>
                                    </td>
                                    <td ><s:property value="jobName"/></td>
                                    <td ><s:date name="sTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
                                    <td ><s:property value="repeatAlarmNum"/></td>
                                    <td >
                                     <s:if test='status==1'>有效</s:if>
                                     <s:if test='status==0'>暂停</s:if>
                                    </td>
                                    <td align="center" nowrap>
                                      <a href="#" onclick='return sendProcessingMsg("<s:property value="@java.net.URLEncoder@encode(jobGroup,'utf-8')" />","<s:property value="@java.net.URLEncoder@encode(jobName,'utf-8')"/>")'>我正在处理中</a>
                                      <a href="#" onclick='return sendProcessedMsg("<s:property value="@java.net.URLEncoder@encode(jobGroup,'utf-8')" />","<s:property value="@java.net.URLEncoder@encode(jobName,'utf-8')"/>")'>我已处理完成</a>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <s:if test="#request.list.size > 0">
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
</form>
<script type="text/javascript">
    //处理中消息发送
    function sendProcessingMsg(jobGroup,jobName){
    	var msg = prompt("发送'处理中消息'给当前组成员", jobGroup + jobName + ":" + $('#waitingMsg').val());
    	if (msg != null && msg != "") {
    		$('#jobGroup').val(jobGroup);
    		$('#jobName').val(jobName);
    		$('#repeatStatus').val(0);
    		$('#sendMsg').val(msg);
    		$('#msgform').submit();
    		return true;
    	}
    }

    //处理成功消息发送
    function sendProcessedMsg(jobGroup,jobName){
    	var msg = prompt("发送'成功消息'给当前组成员", jobGroup + jobName + ":" + $('#finishedMsg').val());
    	if (msg != null && msg != "") {
    		$('#jobGroup').val(jobGroup);
    		$('#repeatStatus').val(1);
    		$('#jobName').val(jobName);
    		$('#sendMsg').val(msg);
    		$('#msgform').submit();
    		return true;
    	}
    }
</script>
<%@include file="../include/bottom.jsp" %>