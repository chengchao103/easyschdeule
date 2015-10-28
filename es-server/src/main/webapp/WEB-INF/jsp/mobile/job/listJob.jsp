<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>

<div data-role="navbar" data-grid="d">
	<ul>
		<li><a href="listJob" class="ui-btn-active">任务</a></li>
		<li><a href="listSub">订阅</a></li>
		<li><a href="listLog">历史</a></li>
		<li><a href="listAsyncJobQueues">队列</a></li>
		<li><a href="listUser">用户</a></li>
	</ul>
</div><!-- /navbar -->

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

<form action="listJob.action" method="post" id="searchform">
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
		<s:bean name="com.taobao.ad.easyschedule.action.job.JobAction" id="jobAction"/>
		<s:iterator value="jobList" status="status">
			<s:set name="statecode" value="#jobAction.getTriggerStateCode(jobname, jobgroup)"></s:set>
            <s:set name="statename" value="#jobAction.getTriggerStateName(jobname, jobgroup)"></s:set>
			<div data-role="collapsible" data-iconpos="right">
			<h3>
				<s:iterator value="groups">
		            <s:if test='keycode==jobgroup'><s:property value="keyname"/></s:if>
		        </s:iterator> 
		        - <s:property value="jobname"/>
	        </h3>
			<p>
	         <s:if test='userGroupsMap.get(jobgroup)==true'>
                <s:if test='#statecode.toString()=="1"'>
                <a href="resumeJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
                   onclick="return confirm('确定要恢复任务吗？\n\n\n注意：普通重复执行的任务恢复后默认自动会立即触发执行一次任务，\n如果需要避免自动触发，请先修改任务开始时间为当前时间以后再恢复任务。');">恢复</a>
                   <a style="color:#ccc;" onclick="javascript:void(0);" href="#">修改</a>
                </s:if>
                <s:if test='#statecode.toString()=="0"'>
                <a href="pauseJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
                   onclick="return confirm('确定要暂停？');">暂停</a>
                   <a href="toUpdateJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />" data-ajax="false">修改</a>
                </s:if>
	            <a href="runJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
	               onclick="return confirm('确定要立即执行？');" data-ajax="false">立即执行</a>
	            <a href="deleteJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
	               onclick="return confirm('确定要删除？');" data-ajax="false">删除</a>
	         </s:if>
	         <s:else>
					<s:if test='#statecode.toString()=="1"'>
					    <a style="color:#ccc;" onclick="javascript:void(0);" href="#">恢复</a>
					</s:if>
					<s:if test='#statecode.toString()=="0"'>
					    <a style="color:#ccc;" onclick="javascript:void(0);" href="#">暂停</a>
					</s:if>
					<a style="color:#ccc;" onclick="javascript:void(0);" href="#">修改</a>
					<a style="color:#ccc;" onclick="javascript:void(0);" href="#">立即执行</a>
					<a style="color:#ccc;" onclick="javascript:void(0);" href="#">删除</a>
	        </s:else>
	        <a href="listLog.action?query.opsubname=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />|<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />" data-ajax="false">历史</a>
	        <s:if test="jobclassname=='com.taobao.ad.easyschedule.job.DataTrackingJob'">
	            <a href="viewDataTracking.action?query.jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&query.jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />" data-ajax="false">查看数据</a>
	        </s:if>
	        <br><br>
	        <b>任务分组：</b><s:property value="jobgroup"/><br>
	        <b>任务名称：</b><s:property value="jobname"/><br>
	        <b>任务描述：</b><s:property value="description"/><br>
	        <b>任务状态：</b><s:property value="statename"/><br>
                <%--<img src="<%=request.getContextPath()%>/share/images/state_<s:property value="#statecode.toString()"/>.png"
                     width="20" height="20" border="0" style="vertical-align:middle;"
                     alt="<s:property value="statename"/>" title="<s:property value="statename"/>">--%>
	        <b>触发规则：</b>
	        	<s:if test="repeatCount">执行<s:property value="repeatCount"/>次<br></s:if>
                <s:if test="repeatInterval">每<s:property value="repeatInterval"/>毫秒一次<br></s:if>
                <s:if test="cronExpression != null"><s:property value="cronExpression"/></s:if><br>
	        <b>上次触发：</b><s:date name="prevfiretime" format="yyyy-MM-dd HH:mm:ss"/><br>
	        <b>下次触发：</b><s:date name="nextfiretime" format="yyyy-MM-dd HH:mm:ss"/><br>
			<b>开始时间：</b><s:date name="starttime" format="yyyy-MM-dd"/><br>
   			<b>结束时间：</b><s:date name="endtime" format="yyyy-MM-dd"/><br>
   			<b>重做标志：</b><s:if test='requestrecovery==0'>否</s:if><s:if test='requestrecovery==1'>是</s:if><br>
            <b>优先级：</b><s:property value="priority"/><br>
            <!--<b>任务类：</b><s:property value="jobclassname"/><br>-->
			<b>任务详情：</b><br>
	            <table>
	            <tr><th align="left">key</th><th align="left">value</th></tr>
	            <s:iterator value="jobDataMap">
	                <tr><td><s:property value="key"/></td><td><s:property value="value"/></td></tr>
	            </s:iterator>
	            </table>
			</p>
			</div>
		</s:iterator>
	</div>
</div>
<s:if test="#request.jobList.size > 0">
    <%@include file="../include/pageNav.jsp" %>
</s:if>
</form>
<%@include file="../include/bottom.jsp" %>