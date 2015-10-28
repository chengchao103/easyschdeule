<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>

<div data-role="navbar" data-grid="d">
	<ul>
		<li><a href="listJob">任务</a></li>
		<li><a href="listSub" class="ui-btn-active">订阅</a></li>
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
<form id="msgform" action="sendSubMsg.action" method="post">
	<input type="hidden" id="waitingMsg" value="<s:property value="waitingMsg"/>"/>
	<input type="hidden" id="finishedMsg" value="<s:property value="finishedMsg"/>"/>
	<input type="hidden" id="jobGroup" name="jobGroup" value=""/>
	<input type="hidden" id="jobName" name="jobName" value=""/>
	<input type="hidden" id="sendMsg" name="sendMsg" value=""/>
	<input type="hidden" id="repeatStatus" name="repeatStatus" value=""/>
</form>
<form action="listSub.action" method="post" id="searchform">
<div data-role="content">
	<div data-role="fieldcontain">
		<label for="select-choice-a" class="select">分组:</label>
        <select id="jobgroup"
                name="query.jobgroup">
            <option value="">分组</option>
            <s:iterator value="groups">
                <option value="<s:property value="keycode"/>" <s:if test='keycode==query.jobgroup'>selected</s:if> ><s:property value="keyname"/></option>
            </s:iterator>
        </select>
	</div>
	<div data-role="fieldcontain">
		<label for="select-choice-a" class="select">用户:</label>
        <select id="notifyUserName" name="notifyUserName">
    		<s:iterator value="userList" status="status">
                <option value="<s:property value="username" />" <s:if test='username==notifyUserName'>selected</s:if>><s:property value="descn" /></option>
    		</s:iterator>
    	</select>
	</div>
	<button type="submit" data-theme="c" name="submit">查询</button>
	<div data-role="collapsible-set">
		<s:iterator value="jobList" status="status">
			<div data-role="collapsible" data-iconpos="right">
			<h3>
				<s:iterator value="groups">
		            <s:if test='keycode==jobgroup'><s:property value="keyname"/></s:if>
		        </s:iterator> 
		        - <s:property value="jobname"/>
	        </h3>
			<p>
				名称:<s:property value="jobname"/><span style="color: rgb(153, 153, 153);">&nbsp;<s:property value="description"/></span></td>
				<br>
				订阅消息:<br>
				成功:<input <s:if test="successUserSub!=null&&successUserSub.mobile==1">checked</s:if> type="checkbox" class="checkRow" value="mobile"  onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',3,1,"<s:property value="#status.index"/>") id="1_<s:property value="#status.index"/>_3"  name="<s:property value="#status.index"/>_success" data-role="none"/> 短信
				    <input <s:if test="successUserSub!=null&&successUserSub.email==1">checked</s:if> type="checkbox" class="checkRow" value="email" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',1,1,<s:property value="#status.index"/>) id="1_<s:property value="#status.index"/>_1" name="<s:property value="#status.index"/>_success" data-role="none"/> 邮件
				    <input <s:if test="successUserSub!=null&&successUserSub.wangwang==1">checked</s:if> type="checkbox" class="checkRow" value="wangwang" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',2,1,<s:property value="#status.index"/>) id="1_<s:property value="#status.index"/>_2" name="<s:property value="#status.index"/>_success" data-role="none"/> 旺旺
				<br>
				失败:<input <s:if test="failUserSub!=null&&failUserSub.mobile==1">checked</s:if> type="checkbox" class="checkRow" value="mobile" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',3,0,<s:property value="#status.index"/>) id="0_<s:property value="#status.index"/>_3" name="<s:property value="#status.index"/>_fail" data-role="none"/> 短信
				    <input <s:if test="failUserSub!=null&&failUserSub.email==1">checked</s:if> type="checkbox" class="checkRow" value="email" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',1,0,<s:property value="#status.index"/>) id="0_<s:property value="#status.index"/>_1" name="<s:property value="#status.index"/>_fail" data-role="none"/> 邮件
				    <input <s:if test="failUserSub!=null&&failUserSub.wangwang==1">checked</s:if> type="checkbox" class="checkRow" value="wangwang" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',2,0,<s:property value="#status.index"/>) id="0_<s:property value="#status.index"/>_2"  name="<s:property value="#status.index"/>_fail" data-role="none"/> 旺旺
				<br>
				警告:<input <s:if test="warnUserSub!=null&&warnUserSub.mobile==1">checked</s:if> type="checkbox" class="checkRow" value="mobile" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',3,2,<s:property value="#status.index"/>) id="2_<s:property value="#status.index"/>_3" name="<s:property value="#status.index"/>_warn" data-role="none"/> 短信
				    <input <s:if test="warnUserSub!=null&&warnUserSub.email==1">checked</s:if> type="checkbox" class="checkRow" value="email" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',1,2,<s:property value="#status.index"/>) id="2_<s:property value="#status.index"/>_1" name="<s:property value="#status.index"/>_warn" data-role="none"/> 邮件
				    <input <s:if test="warnUserSub!=null&&warnUserSub.wangwang==1">checked</s:if> type="checkbox" class="checkRow" value="wangwang" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',2,2,<s:property value="#status.index"/>) id="2_<s:property value="#status.index"/>_2" name="<s:property value="#status.index"/>_warn" data-role="none"/> 旺旺
				<br><br>
				发消息给任务订阅者:<br>
				<a href="#" onclick='return sendProcessingMsg("<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />","<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')"/>")'>
                                                               我正在处理中</a>&nbsp;
                <a href="#" onclick='return sendProcessedMsg("<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />","<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')"/>")'>
                                                               我已处理完成</a>
			</p>
			</div>
		</s:iterator>
	</div>
</div>
<s:if test="#request.jobList.size > 0">
    <%@include file="../include/pageNav.jsp" %>
</s:if>
</form>
<script type="text/javascript">
    function save(jobGroup,jobName,messageType,subType,index) {
	    var checkBox=document.getElementById(subType+"_"+index+"_"+messageType);
	    var isSub=0;
	    var msg="取消成功";
	    if(checkBox.checked){
    			isSub=1;
    			msg="订阅成功";
		}
		var params = {
			"jobGroup":jobGroup,
			"jobName":jobName,
			"notifyUserName":$('#notifyUserName').val(),
			"isSub":isSub,
			"messageType":messageType,
			"subType":subType
		};
		$.ajax({
		type :"post",
		dataType :"json",
		url :"<%= request.getContextPath() %>/addUserSubAjax.action",
		data :params,
		success :function(ajaxResult){ //ajax请求报名结果处理函数
			var message = ajaxResult.message;
			if (ajaxResult.resultCode == 1) {
            	alert(msg);
			}else{
				alert("订阅失败：" + message);
			}
		}
    	});
    }
    
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
    		$('#jobName').val(jobName);
    		$('#repeatStatus').val(1);
    		$('#sendMsg').val(msg);
    		$('#msgform').submit();
    		return true;
    	}
    }
</script>
<%@include file="../include/bottom.jsp" %>