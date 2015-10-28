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
            jobCommand:$('#jobCommand').val(),
            token:$('#jobToken').val(),
            signTime:$('#signTime').val(),
            sync:$('#sync').val(),
            callBackUrl:$('#callBackUrl').val(),
            jobId:$('#signTime').val()
        },
                function (data) //回传函数
                {
                    var returnData = '';
                    $('#returnData').val(data);
                });
    }

    function generateToken() {
        $('#jobToken').val("");
        $('#signTime').val("");
        $.post(
                'generateToken.action',
        {
            jobGroup:$('#jobGroup').val(),
            jobName:$('#jobName').val()
        },
                function (data) //回传函数
                {
                    var returnData = '';
                    eval('returnData=' + data + ';');
                    $('#jobToken').val(returnData.resultMap.token);
                    $('#signTime').val(returnData.resultMap.signTime);
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
                            <li class="cur"><a href="testRequestAsynJob"><span>异步任务请求测试</span></a></li>
                            <li><a href="testCallbackAsynJob"><span>异步任务回调测试</span></a></li>
                            <li><a href="testDataTrackingJob"><span>数据监控请求测试</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <form name="completeJob.action" method="post"
                          action='<%=request.getContextPath()%>/doShellJob.action'>
                        <div class="add-wrapper">
                            <table>
                                <tr>
                                    <td>远程地址：</td>
                                    <td><input type="text" id="baseUrl" name="baseUrl" style="width:300px;" size="60"
                                               value="/esagent/doShellJob"/>
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
                                    <td>执行命令：</td>
                                    <td><input type="text" id="jobCommand" name="jobCommand" style="width:300px;" size="60"
                                               value="wscript.exe d:\a\vbs.vbs"/>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>时间令牌：</td>
                                    <td><input type="text" id="jobToken" name="jobToken" style="width:300px;" size="60" value=""/>
                                    </td>
                                    <td><input class="btn1" type="button" value="获取令牌"
                                               onclick="return generateToken();"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>请求时间：</td>
                                    <td><input type="text" id="signTime" name="signTime" style="width:300px;" size="60" value=""/>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>执行方式：</td>
                                    <td>
                                        <select id="sync" name="sync" style="width:304px;">
                                            <option value="false">异步</option>
                                            <option value="true">同步</option>
                                        </select>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>回调地址：</td>
                                    <td><input type="text" id="callBackUrl" name="callBackUrl" style="width:300px;" size="60" value="<s:property value="callBackUrl"/>"/>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>返回结果：</td>
                                    <td><textarea id="returnData" style="width:302px;height:100px;" name="returnData" rows="6"
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