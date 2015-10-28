<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/share/js/jquery/fancyzoom.js"></script>

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

<form action="listJob.action" method="post" id="searchform">
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
                                <li class="cur"><a href="listJob" target="rightframe"><span>任务列表</span></a></li>
                                <li><a href="toAddHttpJob"  ><span>添加标准任务</span></a></li>
                                <li><a href="toAddShellJob"  ><span>添加SHELL任务</span></a></li>
                                <li><a href="toAddDataTrackingJob"  ><span>添加数据监控</span></a></li>
                                <li><a href="toAddLineCountJob"  ><span>添加文件监控</span></a></li>
                                <li><a href="toAddProcedureJob"  ><span>添加存储过程</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
                                <th nowrap>组</th>
                                <th nowrap>名称</th>
                                <th nowrap>描述</th>
                                <th nowrap>触发规则</th>
                                <th nowrap>上次/下次触发</th>
                                <th nowrap>状态</th>                                
                                <th nowrap>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:bean name="com.taobao.ad.easyschedule.action.job.JobAction" id="jobAction"/>
                            <s:iterator value="jobList" status="status">
                                <tr>
                                    <td><input type="checkbox" class="checkRow" name="checkRow"/></td>
                                    <td nowrap>
                                        <s:iterator value="groups">
                                            <s:if test='keycode==jobgroup'><s:property value="keyname"/></s:if>
                                        </s:iterator>
                                    </td>
                                    <td nowrap><s:property value="jobname"/></td>
                                    <td style="padding: 0px 9px 0px;">
                                    	<s:property value="shortDescription"/>
                                    </td>
                                    <td nowrap>
                                        <s:if test="repeatCount">执行<s:property value="repeatCount"/>次<br></s:if>
                                        <s:if test="repeatInterval">每<s:property value="repeatInterval"/>毫秒一次<br></s:if>
                                        <s:if test="cronExpression != null"><s:property value="cronExpression"/></s:if>
                                    </td>
                                    <td nowrap style="padding: 0px 9px 0px;">
                                        <s:date name="prevfiretime" format="yyyy-MM-dd HH:mm:ss"/><br>
                                        <s:date name="nextfiretime" format="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td nowrap style="padding: 0px 9px 0px;">
                                        <s:set name="statecode"
                                               value="#jobAction.getTriggerStateCode(jobname, jobgroup)"></s:set>
                                        <s:set name="statename"
                                               value="#jobAction.getTriggerStateName(jobname, jobgroup)"></s:set>
                                        <img src="<%=request.getContextPath()%>/share/images/state_<s:property value="#statecode.toString()"/>.png"
                                             width="25" height="25" border="0" style="vertical-align:middle;"
                                             alt="<s:property value="statename"/>" title="<s:property value="statename"/>">
                                    </td>
                                    <td align="center" nowrap style="padding: 0px 9px 0px;">
                                     <s:if test='userGroupsMap.get(jobgroup)==true'>
                                        <s:if test='#statecode.toString()=="1"'>
	                                        <a href="resumeJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
	                                           onclick="return confirm('确定要恢复任务吗？\n\n\n注意：普通重复执行的任务恢复后默认自动会立即触发执行一次任务，\n如果需要避免自动触发，请先修改任务开始时间为当前时间以后再恢复任务。');">恢复</a>
                                        </s:if>
                                        <s:if test='#statecode.toString()=="0"'>
	                                        <a href="pauseJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
	                                           onclick="return confirm('确定要暂停？');">暂停</a>
                                        </s:if>
                                        <a href="runJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
                                           onclick="return confirm('确定要立即执行？');">立即执行</a>
	                                    <a href="toUpdateJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />">修改</a>
                                        <a href="deleteJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />"
                                           onclick="return confirm('确定要删除？');">删除</a>
                                     </s:if>
                                     <s:else>
										<s:if test='#statecode.toString()=="1"'>
										    <a style="color:#ccc;" onclick="javascript:void(0);" href="#">恢复</a>
										</s:if>
										<s:if test='#statecode.toString()=="0"'>
										    <a style="color:#ccc;" onclick="javascript:void(0);" href="#">暂停</a>
										</s:if>
										<a style="color:#ccc;" onclick="javascript:void(0);" href="#">立即执行</a>
										<a style="color:#ccc;" onclick="javascript:void(0);" href="#">修改</a>
										<a style="color:#ccc;" onclick="javascript:void(0);" href="#">删除</a>
                                    </s:else>
                                        <a href="viewJob.action?jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />">详情</a>
                                        <a href="listLog.action?query.opsubname=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />|<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />">历史</a>
                                        <s:if test="jobclassname=='com.taobao.ad.easyschedule.job.DataTrackingJob' || jobclassname=='com.taobao.ad.easyschedule.job.FileLineCountJob'">
                                            <a href="viewDataTracking.action?query.jobGroup=<s:property value="@java.net.URLEncoder@encode(jobgroup,'utf-8')" />&query.jobName=<s:property value="@java.net.URLEncoder@encode(jobname,'utf-8')" />">数据</a>
                                        </s:if>
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
<%@include file="../include/bottom.jsp" %>