<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/share/js/my97datepicker/wdatepicker.js"
        encoding="UTF-8"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $.formValidator.initConfig({formid:"addHttpJob",onerror:function(msg) {
            alert(msg);
        }});
        $('#参数KEY').formValidator({
            onshow :"请输入参数KEY",
            onfocus :"请输入参数KEY，最多64个字符",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :64,
            onerror :"请输入参数KEY，最多64个字符"
        });

    });
</script>
<div data-role="navbar" data-grid="d">
	<ul>
		<li><a href="listJob" class="ui-btn-active">任务</a></li>
		<li><a href="listSub">订阅</a></li>
		<li><a href="listLog">历史</a></li>
		<li><a href="listAsyncJobQueues">队列</a></li>
		<li><a href="listUser">用户</a></li>
	</ul>
</div><!-- /navbar -->
<form id="addJob" name="addJob" method="post" action="addJob.action">
<div data-role="content">
<!--属性和参数部分开始-->
<%@include file="addJobParameter.jsp"%>
<!--属性和参数部分结束-->
<h4>触发条件</h4>
<table width="100%">
    <tbody>
    <tr>
        <td>条件类型：<br>
            <input type="radio" id="triggerType1" name="triggerType" value="Simple"
                   <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_SIMPLE">checked</s:if> onclick="displaySimple();"/><label
                for="triggerType1">简单</label>
            <input type="radio" id="triggerType2" name="triggerType" value="Cron"
                   <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_CRON">checked</s:if> onclick="displayCron();"/><label
                for="triggerType2">Cron表达式</label>
        </td>
    </tr>
    <tr>
        <td>开始时间：<br>
            <input id="startTime" type="text" class="date"
                   onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
                   name="startTimeAsDate" 
                   value="<s:if test='#disabled == "disabled" or @com.taobao.ad.easyschedule.commons.utils.DateTimeUtil@compareDay(startTimeAsDate, 0)'><s:date name="startTimeAsDate" format="yyyy-MM-dd" /></s:if>" readonly/>
        </td>
    </tr>
    <tr>
        <td>结束时间：<br>
            <input id="stopTime" type="text" class="date"
                   onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
                   name="stopTimeAsDate" 
                   value="<s:date name="stopTimeAsDate" format="yyyy-MM-dd" />" readonly/>
        </td>
    </tr>
    <tr id="trcronExpression"
        <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_SIMPLE">style="display:none;"</s:if> >
        <td>触发规则：<br>
        	<input type="text" name="cronExpression" value="<s:property value="cronExpression"/>"/>
			<a href="toViewTriggerHelp.action" target="_blank">表达式说明</a>
        </td>
    </tr>
    <tr id="trrepeatCount"
        <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_CRON">style="display:none;"</s:if> >
        <td>执行次数：<br>
        	<input type="text" name="repeatCount" value="<s:property value="repeatCount" />"/>
        </td>
    </tr>
    <tr id="trrepeatInterval"
        <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_CRON">style="display:none;"</s:if> >
        <td>执行间隔：毫秒<br>
        	<input type="text" name="repeatInterval" value="<s:property value="repeatInterval" />"/>
        </td>
    </tr>
    <tr id="trpriority">
        <td>触发优先级：<br>
        	<input type="text" name="priority" value="<s:property value="priority" />"/>
        </td>
    </tr>
    </tbody>
</table>
<br>
<input id="action" name="action" type="hidden" value="<s:property value="action"/>"/>
<s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_ADD">
    <input type="button" class="btn1" value=" 新增任务 " onclick="return doSubmit();"/>
</s:if>
<s:else>
    <input type="button" class="btn1" value=" 修改任务 " onclick="return doSubmit();"/>
</s:else>
<br>
</div>
</form>
<script type="text/javascript">
    function doSubmit() {
    	if(document.getElementById("jobDetail.group").value == "") {
    		alert("请选择任务组");
    		return false;
    	}
    	if(document.getElementById("jobDetail.name").value == "") {
    		alert("请输入任务名称");
    		return false;
    	}
    	if (!/^(?!_)(?!.*?_$)[a-zA-Z0-9_-]+$/.test(document.getElementById("jobDetail.name").value)) {
			alert("任务名称只允许字母(a-zA-Z)、数字(0-9)、连接符(-)和下划线(_)");
			return false;
		}
        $('#addJob').submit();
        return true;
    }
</script>
<%@include file="../include/bottom.jsp" %>