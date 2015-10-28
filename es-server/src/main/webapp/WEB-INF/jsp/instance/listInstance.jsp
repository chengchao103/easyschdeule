<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<div id="content" class="content content-right">
    <div class="layout grid-default" id="grid-default">
        <!-- 主栏 -->
        <div class="col-main" id="col-main">
            <div class="main-wrap" id="main-wrap">
                <!-- 搜索框 -->
                <div class="search-wrapper">
                	当前实例：<s:property value="easyscheduler.schedulerInstanceId"/>
                </div>
                <!-- 搜索框 结束 -->
                <div class="message-wrapper" id="message-wrapper">
                    <div class="message-title">
                        <ul>
                            <li class="cur"><a href="#"><span>实例列表</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <table class="message-list" id="message-list">
                        <thead>
                        <tr>
                            <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
                            <th nowrap>实例ID</th>
                            <th nowrap>实例名称</th>
                            <th nowrap>启动时间</th>
                            <th nowrap>任务数</th>
                            <th nowrap>持久类型</th>
                            <th nowrap>线程数</th>
                            <th nowrap>版本</th>
                            <th nowrap>状态</th>
                            <th nowrap>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="schedulerList" status="status">
                            <tr class="row1" onMouseOver="this.className='m1'" onMouseOut="this.className='row1'">
                                <td><input type="checkbox" class="checkRow" name="checkRow"/></td>
                                <td nowrap><s:property value="schedulerInstanceId"/></td>
                                <td nowrap><s:property value="schedulerName"/></td>
                                <td nowrap><s:property value="runningSince"/></td>
                                <td nowrap><s:property value="numJobsExecuted"/></td>
                                <td nowrap><s:property value="persistenceType"/><br></td>
                                <td nowrap><s:property value="threadPoolSize"/><br></td>
                                <td nowrap><s:property value="version"/><br></td>
                                <td nowrap><img
                                        src="<%=request.getContextPath()%>/share/images/state_<s:property value="stateCode"/>.png"
                                        width="25" height="25" border="0" style="vertical-align:middle;"
                                        alt="<s:property value="state"/>"><s:property value="state"/></td>
                                <td nowrap>
                                        <a href="startInstance?instance=<s:property value="schedulerInstanceId"/>" onclick="return confirm('确认要启动？')">启动</a>
                                        <a href="pauseInstance?instance=<s:property value="schedulerInstanceId"/>" onclick="return confirm('确认要暂停？')">暂停</a>
                                </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
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