<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript">
    $(document).ready(function() {
        $.formValidator.initConfig();
        $('#query.jobgroup').formValidator().functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val));
            }
        });
        $('#query.jobname').formValidator().functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val));
            }
        });
    });
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
<!-- 内容 -->
<form action="listTrigger.action" method="post" id="searchform">
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
                                    <select id="jobgroup" name="query.jobgroup">
                                        <option value="">--请选择--</option>
                                        <s:iterator value="groups">
                                            <option value="<s:property value="keycode"/>" <s:if test='keycode==query.jobgroup'>selected</s:if> ><s:property value="keyname"/></option>
                                        </s:iterator>
                                    </select>
                                </td>
                            </tr>
                             <tr>
                            <th><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名称:</label></th>
                            <td><input id="jobname" type="text" name="query.jobname" value="<s:property value="query.jobname"/>"/></td>
                            </tr>
                            <tr>
                                <th>&nbsp;</th>
                                <td colspan="2">
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
                                <li class="cur"><a href="#"><span>触发器列表</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
                                <th nowrap>组</th>
                                <th nowrap>名称</th>
                                <th nowrap>触发规则</th>
                                <th nowrap>开始/结束时间</th>
                                <th nowrap>上次/下次触发</th>
                                <th nowrap>优先级</th>
                                <th nowrap>状态</th>
                                <th nowrap>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:bean name="com.taobao.ad.easyschedule.action.trigger.TriggerAction" id="triggerAction"/>
                            <s:iterator value="triggerList" status="status">
                                <tr>
                                    <td><input type="checkbox" class="checkRow" name="checkRow"/></td>
                                    <td nowrap>
                                        <s:iterator value="groups">
                                            <s:if test='keycode==jobgroup'><s:property value="keyname"/></s:if>
                                        </s:iterator>
                                        	<s:if test='"MANUAL_TRIGGER"==triggergroup'>手动触发</s:if>
                                    </td>
                                    <td nowrap style="padding: 0px 9px 0px;">
                                    	<s:if test='"MANUAL_TRIGGER"==triggergroup'>
                                    		<s:property value="jobname"/>
                                    	</s:if>
                                    	<s:property value="triggername"/><br>
                                    	<span style="color: rgb(200, 200, 200);"><s:property value="description"/></span>
                                    </td>
                                    <td nowrap>
                                        <s:if test="repeatCount">执行<s:property value="repeatCount"/>次<br></s:if>
                                        <s:if test="repeatInterval">每<s:property value="repeatInterval"/>毫秒一次<br></s:if>
                                        <s:if test="cronExpression != null"><s:property value="cronExpression"/></s:if>
                                    </td>
                                    <td nowrap style="padding: 0px 9px 0px;">
                                       <s:date name="starttime" format="yyyy-MM-dd"/><br>
                                       <s:date name="endtime" format="yyyy-MM-dd"/>
                                    </td>
                                    <td nowrap style="padding: 0px 9px 0px;">
                                        <s:date name="prevfiretime" format="yyyy-MM-dd HH:mm:ss"/><br>
                                        <s:date name="nextfiretime" format="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td nowrap>
                                        <center><s:property value="priority"/></center>
                                    </td>
                                    <td nowrap style="padding: 1px 9px 1px;">
                                        <s:set name="statecode"
                                               value="#triggerAction.getTriggerStateCode(jobname, jobgroup)"></s:set>
                                        <s:set name="statename"
                                               value="#triggerAction.getTriggerStateName(jobname, jobgroup)"></s:set>
                                        <img src="<%=request.getContextPath()%>/share/images/state_<s:property value="#statecode.toString()"/>.png"
                                             width="25" height="25" border="0" style="vertical-align:middle;"
                                             alt="<s:property value="statename"/>" title="<s:property value="statename"/>">
                                        <s:property value="statename"/>
                                    </td>
                                    <td nowrap style="padding: 1px 9px 1px;">
                                        <s:if test="group!='MANUAL_TRIGGER'">
                                        <s:if test='userGroupsMap.get(jobgroup)==true'>
                                        <a href="resumeTrigger.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
                                               onclick="return confirm('确定要恢复？');">恢复</a>
                                            <a href="pauseTrigger.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
                                               onclick="return confirm('确定要暂停？');">暂停</a>
                                            <a href="runTrigger.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
                                               onclick="return confirm('确定要立即执行？');">立即执行</a>
                                            <a href="listLog.action?query.opsubname=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />|<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
                                               >历史</a>
                                            </s:if>
                                     <s:else>
                                            <a style="color:#ccc;" onclick="javascript:void(0);" href="#">恢复</a>
                                            <a style="color:#ccc;" onclick="javascript:void(0);" href="#">暂停</a>
                                            <a style="color:#ccc;" onclick="javascript:void(0);" href="#">立即执行</a>
                                            <a style="color:#ccc;" onclick="javascript:void(0);" href="#">历史</a>
                                            <!--<a style="color:#ccc;" onclick="javascript:void(0);" href="#">编辑</a>
                                            <a style="color:#ccc;" onclick="javascript:void(0);" href="#">删除</a>-->
                                    </s:else>
                                        </s:if>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <s:if test="#request.triggerList.size > 0">
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
<%@include file="../include/bottom.jsp" %>
