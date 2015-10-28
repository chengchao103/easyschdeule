<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>

<div data-role="navbar" data-grid="d">
	<ul>
		<li><a href="listJob">任务</a></li>
		<li><a href="listSub">订阅</a></li>
		<li><a href="listLog">历史</a></li>
		<li><a href="listAsyncJobQueues">队列</a></li>
		<li><a href="listUser" class="ui-btn-active">用户</a></li>
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

<form action="listUser.action" method="post" id="searchform">
<div data-role="content">
	<div data-role="fieldcontain">
		<label for="select-choice-a" class="select">用户:</label>
		<input type="text" id="username" name="query.username" value="<s:property value="query.username" />">
	</div>
	<div data-role="fieldcontain">
		<label for="select-choice-a" class="select">旺旺:</label>
		<input type="text" id="descn" name="query.descn" value="<s:property value="query.descn" />">
	</div>
	<button type="submit" data-theme="c" name="submit">查询</button>
	<div data-role="collapsible-set">
		<s:iterator value="#request.uUsers" status="status">
			<div data-role="collapsible" data-iconpos="right">
			<h3>
				<!%<s:property value="username" /> - %><s:property value="descn" />
				<s:if test='mobile != null && mobile !=""'> - <s:property value="mobile" /></s:if>
	        </h3>
			<p>
			用户:<s:property value="username" /><br>
			旺旺:<s:property value="descn" /><br>
			手机:<s:property value="mobile" /><br>
			角色:<s:iterator value="#request.userXRole">
					<s:if test="id==userId">
						<s:iterator value="#request.uRoles">
							<s:if test="id==roleId"><s:property value="descn" />,</s:if>
						</s:iterator>
					</s:if>
				</s:iterator><br>
			状态:<s:property value="@com.taobao.ad.easyschedule.commons.Constants@getUserStateDesc(status)" /><br>
			创建时间:<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/><br>
			修改时间:<s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/>
			</p>
			</div>
		</s:iterator>
	</div>
</div>
<s:if test="#request.uUsers.size > 0">
    <%@include file="../include/pageNav.jsp" %>
</s:if>
</form>
<%@include file="../include/bottom.jsp" %>