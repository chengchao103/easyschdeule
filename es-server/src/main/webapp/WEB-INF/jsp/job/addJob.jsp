<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script src="<%=request.getContextPath()%>/share/js/jquery/fancyzoom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/share/js/my97datepicker/wdatepicker.js"
        encoding="UTF-8"></script>
<div id="content" class="content content-right">
<div class="layout grid-default" id="grid-default">
<!-- 主栏 -->
<div class="col-main" id="col-main">
<div class="main-wrap" id="main-wrap">
<div class="message-wrapper" id="message-wrapper">
<div class="message-title">
    <ul>
        <li><a href="listJob"><span>任务列表</span></a></li>
        <li class="cur">
	        <a href="#">
	        	<span>
	        		<s:if test='"disabled" == #disabled'>任务详情</s:if>
	        		<s:else>
						<s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_ADD">新增任务</s:if>
						<s:elseif test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">修改任务</s:elseif>
	        		</s:else>
	        	</span>
	        </a>
	    </li>
    </ul>
</div>
<div class="clearing"></div>
<form id="addJob" name="addJob" method="post" action="addJob.action">
<s:token/>
<div class="add-wrapper">

<!--属性和参数部分开始-->
<%@include file="addJobParameter.jsp" %>
<!--属性和参数部分结束-->


<table class="message-add">
    <thead>
    <tr>
        <td colspan="2"><h1>触发条件</h1></td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td width="130px">条件类型：</td>
        <td width="340px">
            <input style="position:relative;top:5px;" type="radio" id="triggerType1" name="triggerType" <s:property value="disabled"/> value="Simple"  
                   <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_SIMPLE">checked</s:if> onclick="displaySimple();"/><label
                for="triggerType1">简单</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input style="position:relative;top:5px;" type="radio" id="triggerType2" name="triggerType"  <s:property value="disabled"/> value="Cron" 
                   <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_CRON">checked</s:if> onclick="displayCron();"/><label
                for="triggerType2">Cron表达式</label>
        </td>
        <td></td>
    </tr>
    <tr>
        <td width="130px">开始时间：</td>
        <td>
            <input id="startTime" type="text" style="width:300px;" class="date"
                   onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
                   name="startTimeAsDate" size="50"
                   value="<s:if test='#disabled == "disabled" or @com.taobao.ad.easyschedule.commons.utils.DateTimeUtil@compareDay(startTimeAsDate, 0)'><s:date name="startTimeAsDate" format="yyyy-MM-dd" /></s:if>" readonly <s:property value="disabled"/>/>
        </td>
        <td>为空表示马上开始</td>
    </tr>
    <tr>
        <td width="130px">结束时间：</td>
        <td>
            <input id="stopTime" type="text" style="width:300px;" class="date"
                   onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
                   name="stopTimeAsDate" size="50"
                   value="<s:date name="stopTimeAsDate" format="yyyy-MM-dd" />" readonly <s:property value="disabled"/>/>
        </td>
        <td>为空表示没有结束时间</td>
    </tr>
    <tr id="trcronExpression"
        <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_SIMPLE">style="display:none;"</s:if> >
        <td width="130px">触发规则：</td>
        <td><input type="text" name="cronExpression" style="width:300px;" size="50" value="<s:property value="cronExpression"/>" <s:property value="disabled"/>/></td>
        <td>
            <div class="show_photo">
                <a href="#photo_1">表达式说明</a>
            </div>
            <div id="photo_1">
                <!--表达式说明开始-->
                <div style="width: 600px; height: 300px; visibility: visible; overflow: auto; top: 0px; left: 0px;font-family:'新宋体','Times New Roman', Times, serif;line-height: 22px;">
                    <b>表达式说明：</b><br>
                    <div class="texttriggerhelp">
                    	<%@include file="addJobTriggerHelp.jsp" %>
                    </div>
                </div>
                <!--表达式说明结束-->
            </div>
        </td>
    </tr>
    <tr id="trrepeatCount"
        <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_CRON">style="display:none;"</s:if> >
        <td width="130px">执行次数：</td>
        <td><input type="text" name="repeatCount" style="width:300px;" size="50" value="<s:property value="repeatCount" />" <s:property value="disabled"/>/></td>
        <td>&nbsp;</td>
    </tr>
    <tr id="trrepeatInterval"
        <s:if test="triggerType == @com.taobao.ad.easyschedule.commons.Constants@TRIGGERTYPE_CRON">style="display:none;"</s:if> >
        <td width="130px">执行间隔：</td>
        <td><input type="text" name="repeatInterval" style="width:300px;" size="50" value="<s:property value="repeatInterval" />" <s:property value="disabled"/>/></td>
        <td>毫秒</td>
    </tr>
    <tr id="trpriority">
        <td width="130px">触发优先级：</td>
        <td><input type="text" name="priority" style="width:300px;" size="50" value="<s:property value="priority" />" <s:property value="disabled"/>/></td>
        <td>任务调度的优先级，最高为10，最低为1</td>
    </tr>
    </tbody>
</table>
<br>
<input id="action" name="action" type="hidden" value="<s:property value="action"/>"/>
<s:if test='"disabled" == #disabled'></s:if>
<s:else>
	<s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_ADD">
	    <input type="button" class="btn1" value=" 新增任务 " onclick="return doSubmit();"/>
	</s:if>
	<s:elseif test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">
	    <input type="button" class="btn1" value=" 修改任务 " onclick="return doSubmit();"/>
	</s:elseif>
</s:else>
<br>
<br>
<br>
</div>
</form>
<script type="text/javascript">
    $(document).ready(function() {
        $('div.show_photo a').fancyZoom({scaleImg: true, closeOnClick: true,directory:'<%=request.getContextPath()%>/share/images'});
    });
</script>

</div>
<!-- 表格 结束 -->
</div>
</div>
<!-- 主栏结束 -->
</div>
<!-- 布局 结束 -->
</div>
<!-- 内容 结束-->
<%@include file="../include/bottom.jsp" %>
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