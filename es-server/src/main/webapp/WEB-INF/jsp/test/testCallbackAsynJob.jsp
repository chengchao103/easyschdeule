<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript">
    function doJob() {
        $('#returnData').val("");
        $.post(
                $('#baseUrl').val(),
        {
            jobGroup:$('#jobGroup').val(),
            jobName:$('#jobName').val(),
            success:$('#success').val()
        },
                function (data) //回传函数
                {
                    var returnData = '';
                    $('#returnData').val(data);
                });
    }
</script>
<title>触发一个任务</title>

<div id="content" class="content content-right">
    <div class="layout grid-default" id="grid-default">
        <!-- 主栏 -->
        <div class="col-main" id="col-main">
            <div class="main-wrap" id="main-wrap">
                <div class="message-wrapper" id="message-wrapper">
                    <div class="message-title">
                        <ul>
                            <li><a href="listJob"><span>任务列表</span></a></li>
                            <li><a href="testRequestAsynJob"><span>异步任务请求测试</span></a></li>
                            <li class="cur"><a href="testCallbackAsynJob"><span>异步任务回调测试</span></a></li>
                            <li><a href="testDataTrackingJob"><span>数据监控请求测试</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <form name="completeJob.action" method="post" action="completeJob.action">
                        <div class="add-wrapper">
                            <table>
                                <tr>
                                    <td>远程地址：</td>
                                    <td><input type="text" id="baseUrl" name="baseUrl" style="width:300px;" size="60"
                                               value="/es/completeJob"/>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="110px">任务组：</td>
                                    <td width="110px">
                                        <select id="jobGroup" name="jobGroup" style="width:304px;">
                                            <s:iterator value="jobGroups">
                                                <option value="<s:property value="keycode"/>"
                                                        <s:if test='keycode==jobGroup'>selected</s:if> >
                                                    <s:property value="keyname"/></option>
                                            </s:iterator>
                                        </select>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>任务名称：</td>
                                    <td><input type="text" id="jobName" name="jobName" style="width:300px;" size="60"
                                               value="<s:property value="jobName" />"/>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>标志：</td>
                                    <td><input type="text" id="success" name="success" style="width:300px;" size="60" value="true"/>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>返回结果：</td>
                                    <td><textarea id="returnData" style="height:100px;width:302px;" name="returnData" rows="6"
                                                  cols="57"></textarea>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            <br>
                            <input class="btn1" type="button" value="  执行任务  " onclick="doJob();"/>
                            <br>
                            <br>
                            <br>
                            <br>
                        </div>
                    </form>
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