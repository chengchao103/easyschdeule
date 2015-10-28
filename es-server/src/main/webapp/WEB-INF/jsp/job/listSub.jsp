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
        $('#jobgroup').val("");
        $('#jobname').val("");
        return true;
    }

</script>
<title>任务列表</title>
<form id="msgform" action="sendSubMsg.action" method="post">
	<input type="hidden" id="waitingMsg" value="<s:property value="waitingMsg"/>"/>
	<input type="hidden" id="finishedMsg" value="<s:property value="finishedMsg"/>"/>
	<input type="hidden" id="jobGroup" name="jobGroup" value=""/>
	<input type="hidden" id="jobName" name="jobName" value=""/>
	<input type="hidden" id="sendMsg" name="sendMsg" value=""/>
	<input type="hidden" id="repeatStatus" name="repeatStatus" value=""/>
</form>
<form action="listSub.action" method="post" id="searchform">
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
                                    <input id="jobname" type="text" name="query.jobname" value="<s:property value="query.jobname"/>"/>
                                </td>
                                <th><label>接收人:</label></th>
                                <td>
                                	<select id="notifyUserName" name="notifyUserName">
                                		<s:iterator value="userList" status="status">
	                                        <option value="<s:property value="username" />" <s:if test='username==notifyUserName'>selected</s:if>><s:property value="descn" /></option>
                                		</s:iterator>
                                	</select>
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
                                <li class="cur"><a href="listJob" target="rightframe"><span>任务通知管理</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th nowrap>组</th>
                                <th nowrap>名称</th>
                                <th nowrap>订阅成功消息</th>
                                <th nowrap>订阅失败消息</th>
                                <th nowrap>订阅警告消息</th>
                                <th nowrap>发消息给任务订阅者</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="jobList" status="status">
                                <tr>
                                    <td nowrap>
                                        <s:iterator value="groups">
                                            <s:if test='keycode==jobgroup'><s:property value="keyname"/></s:if>
                                        </s:iterator>
                                    </td>
                                    <td style="padding: 0px 9px 0px;"><s:property value="jobname"/><span style="color: rgb(153, 153, 153);">&nbsp;<s:property value="shortDescription"/></span></td>
                                    <td nowrap>
                                    	<input <s:if test="successUserSub!=null&&successUserSub.mobile==1">checked</s:if> type="checkbox" class="checkRow" value="mobile"  onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',3,1,"<s:property value="#status.index"/>") id="1_<s:property value="#status.index"/>_3"  name="<s:property value="#status.index"/>_success"/> 短信
                                    	<input <s:if test="successUserSub!=null&&successUserSub.email==1">checked</s:if> type="checkbox" class="checkRow" value="email" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',1,1,<s:property value="#status.index"/>) id="1_<s:property value="#status.index"/>_1" name="<s:property value="#status.index"/>_success"/> 邮件
                                    	<input <s:if test="successUserSub!=null&&successUserSub.wangwang==1">checked</s:if> type="checkbox" class="checkRow" value="wangwang" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',2,1,<s:property value="#status.index"/>) id="1_<s:property value="#status.index"/>_2" name="<s:property value="#status.index"/>_success" /> 旺旺
                                    </td>
                                    <td nowrap>
                                    	<input <s:if test="failUserSub!=null&&failUserSub.mobile==1">checked</s:if> type="checkbox" class="checkRow" value="mobile" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',3,0,<s:property value="#status.index"/>) id="0_<s:property value="#status.index"/>_3" name="<s:property value="#status.index"/>_fail" /> 短信
                                    	<input <s:if test="failUserSub!=null&&failUserSub.email==1">checked</s:if> type="checkbox" class="checkRow" value="email" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',1,0,<s:property value="#status.index"/>) id="0_<s:property value="#status.index"/>_1" name="<s:property value="#status.index"/>_fail"/> 邮件
                                    	<input <s:if test="failUserSub!=null&&failUserSub.wangwang==1">checked</s:if> type="checkbox" class="checkRow" value="wangwang" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',2,0,<s:property value="#status.index"/>) id="0_<s:property value="#status.index"/>_2"  name="<s:property value="#status.index"/>_fail"/> 旺旺
                                    </td>
                                    <td nowrap>
                                    	<input <s:if test="warnUserSub!=null&&warnUserSub.mobile==1">checked</s:if> type="checkbox" class="checkRow" value="mobile" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',3,2,<s:property value="#status.index"/>) id="2_<s:property value="#status.index"/>_3" name="<s:property value="#status.index"/>_warn" /> 短信
                                    	<input <s:if test="warnUserSub!=null&&warnUserSub.email==1">checked</s:if> type="checkbox" class="checkRow" value="email" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',1,2,<s:property value="#status.index"/>) id="2_<s:property value="#status.index"/>_1" name="<s:property value="#status.index"/>_warn"/> 邮件
                                    	<input <s:if test="warnUserSub!=null&&warnUserSub.wangwang==1">checked</s:if> type="checkbox" class="checkRow" value="wangwang" onChange=save(<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />,'<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />',2,2,<s:property value="#status.index"/>) id="2_<s:property value="#status.index"/>_2" name="<s:property value="#status.index"/>_warn"/> 旺旺
                                    </td>
                                    
                                    <td align="center" nowrap>
                                      <a href="#" onclick='return sendProcessingMsg("<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />","<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')"/>")'>
                                                                           我正在处理中</a>
                                      <a href="#" onclick='return sendProcessedMsg("<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />","<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')"/>")'>
                                                                           我已处理完成</a>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <s:if test="#request.jobList.size > 0">
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

    function save(jobGroup,jobName,messageType,subType,index) {
	    var checkBox=document.getElementById(subType+"_"+index+"_"+messageType);
	    var isSub=0;
	    var msg = "消息取消成功！";
	    if(checkBox.checked){
	    	isSub=1;
	    	msg = "消息订阅成功！";
		}
		var params = {
			"jobGroup"      :jobGroup,
			"jobName"       :jobName,
			"notifyUserName":$('#notifyUserName').val(),
			"isSub"         :isSub,
			"messageType":messageType,
			"subType"       :subType
		};
		$.ajax({
		type :"post",
		dataType :"json",
		url :"<%= request.getContextPath() %>/addUserSubAjax.action",
		data :params,
		success :function(ajaxResult){ //ajax请求报名结果处理函数
			var message = ajaxResult.message;
			if (ajaxResult.resultCode == 1) {
				showTips(msg, 90, 3);
			}else{
				alert(message);
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
    
	function showTips( tips, height, time ){ 
		var windowWidth = document.documentElement.clientWidth; 
		var tipsDiv = '<div class="tipsClass">' + tips + '</div>'; 
		$( 'body' ).append( tipsDiv ); 
		$( 'div.tipsClass' ).css({ 
		'top' : height + 'px', 
		'left' : ( windowWidth / 2 - 90) - ( tips.length * 13 / 2 ) + 'px', 
		'position' : 'absolute', 
		'padding' : '3px 5px', 
		'background': '#8FBC8F', 
		'font-size' : 12 + 'px', 
		'margin' : '0 auto', 
		'text-align': 'center', 
		'width' : 'auto', 
		'color' : '#fff', 
		'opacity' : '0.8' 
		}).show(); 
		setTimeout( function(){$( 'div.tipsClass' ).fadeOut();}, ( time * 1000 ) ); 
	} 
</script>
<%@include file="../include/bottom.jsp" %>