<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="com.taobao.ad.easyschedule.dataobject.LogsDO" %>
<%@include file="../include/header.jsp" %>

<div data-role="navbar" data-grid="d">
	<ul>
		<li><a href="listJob">任务</a></li>
		<li><a href="listSub">订阅</a></li>
		<li><a href="listLog" class="ui-btn-active">历史</a></li>
		<li><a href="listAsyncJobQueues">队列</a></li>
		<li><a href="listUser">用户</a></li>
	</ul>
</div>

<script type="text/javascript">
    function doSearch() {
        $('#searchform').submit();
        return true;
    }
    function doReset() {
        $('#optype').val("");
        $('#opsubtype').val("");
        $('#opsubname').val("");
        $('#opuser').val("");
        return true;
    }
</script>
<form action="listLog.action" method="post" id="searchform">
<div data-role="content">
	<div data-role="fieldcontain">
		<label for="select-choice-a" class="select">状态:</label>
        <select id="optype" name="query.optype">
            <option selected value="">所有状态</option>
            <option value="1" <s:if test='1==query.optype'>selected</s:if>>成功</option>
            <option value="2" <s:if test='2==query.optype'>selected</s:if>>失败</option>
            <option value="3" <s:if test='3==query.optype'>selected</s:if>>告警</option>
        </select>
	</div>
	<div data-role="fieldcontain">
		<label for="select-choice-a" class="select">类别:</label>
	    <select id="opsubtype" name="query.opsubtype">
	        <option selected value="">所有类别</option>
	        <s:iterator value="query.TYPE_NAME">
	            <option value="<s:property value="key"/>"
	                    <s:if test='key==query.opsubtype'>selected</s:if>>
	                <s:property value="key"/>:<s:property value="value"/>
	            </option>
	        </s:iterator>
	    </select>
	</div>
	<div data-role="fieldcontain">
		<label for="select-choice-a" class="select">内容:</label>
		<input type="text" id="opsubname" name="query.opsubname" value="<s:property value="query.opsubname" />">
	</div>
	<button type="submit" data-theme="c" name="submit">查询</button>
	<div data-role="collapsible-set">
		<s:iterator value="#request.logss" status="status">
			<div data-role="collapsible" data-iconpos="right">
			<h3>
				<s:if test="optype == 1">√</s:if>
                <s:if test="optype == 2">×</s:if>
                <s:if test="optype == 3">!</s:if>
                 - 
                <s:iterator value="query.TYPE_NAME">
                    <s:if test='key==opsubtype'>
                        <s:property value="value"/>
                    </s:if>
                </s:iterator>
		         - 
		        <s:property value="opsubname"/>
	        </h3>
			<p>
				时间：<s:date name="optime" format="yyyy-MM-dd HH:mm:ss"/><br>
				操作：<s:property value="opuser"/><br>
				详情：<br>
	                instanceid:
	                <s:property value="opname"/>
	                <br>
	                <s:property escape="false" value="opdetail"/>
			</p>
			</div>
		</s:iterator>
	</div>
</div>
<s:if test="#request.logss.size > 0">
    <%@include file="../include/pageNav.jsp" %>
</s:if>
</form>
<%@include file="../include/bottom.jsp" %>