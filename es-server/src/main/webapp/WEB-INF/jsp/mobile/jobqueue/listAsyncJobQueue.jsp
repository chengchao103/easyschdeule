<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>

<div data-role="navbar" data-grid="d">
	<ul>
		<li><a href="listJob">任务</a></li>
		<li><a href="listSub">订阅</a></li>
		<li><a href="listLog">历史</a></li>
		<li><a href="listAsyncJobQueues" class="ui-btn-active">队列</a></li>
		<li><a href="listUser">用户</a></li>
	</ul>
</div>

<script type="text/javascript">
    function doSearch() {
        $('#searchform').submit();
        return true;
    }
    function doReset() {
        $('#jobgroup').val("");
        $('#jobname').val("");
        return true;
    }
</script>

<form action="listAsyncJobQueues.action" method="post" id="searchform">
<div data-role="content">
	<div data-role="fieldcontain">
		<label for="select-choice-a" class="select">分组:</label>
        <select id="jobgroup"
                name="query.jobgroup">
            <option value="">所有分组</option>
            <s:iterator value="groups">
                <option value="<s:property value="keycode"/>" <s:if test='keycode==query.jobgroup'>selected</s:if> ><s:property value="keyname"/></option>
            </s:iterator>
        </select>
	</div>
	<button type="submit" data-theme="c" name="submit">查询</button>
	<div data-role="collapsible-set">
		<s:iterator value="#request.asyncJobQueues" status="status">
			<div data-role="collapsible" data-iconpos="right">
			<h3>
				<s:iterator value="groups">
		            <s:if test='keycode==jobgroup'><s:property value="keyname"/></s:if>
		        </s:iterator> 
		        - <s:property value="jobname"/>
	        </h3>
			<p>
				<b>等待回调数：</b><s:property value="waitCallNum"/><br>
	            <b>任务开始时间：</b><s:date name="sTime" format="yyyy-MM-dd HH:mm:ss"/><br>
	            <b>最晚完成时间：</b><s:date name="cTime" format="yyyy-MM-dd HH:mm:ss"/><br>
			</p>
			</div>
		</s:iterator>
	</div>
</div>
<s:if test="#request.asyncJobQueues.size > 0">
    <%@include file="../include/pageNav.jsp" %>
</s:if>
<s:else>
	当前没有执行中的异步任务
</s:else>
</form>
<%@include file="../include/bottom.jsp" %>