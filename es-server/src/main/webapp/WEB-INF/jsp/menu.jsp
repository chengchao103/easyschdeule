<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="com.taobao.ad.easyschedule.commons.Constants" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>任务管理平台</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/share/css/base.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/share/css/style.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/share/css/thickbox.css"/>
    <script type="text/javascript" src="<%= request.getContextPath() %>/share/js/jquery/jquery.min.js"></script>
    <script type="text/javascript">
        function addTestJob() {
            if (confirm("添加一个测试任务?")) {
                return true;
            } else {
                return false;
            }
        }

        function deleteAllLogs() {
            if (confirm("确认删除所有任务历史?")) {
                return true;
            } else {
                return false;
            }
        }
    </script>
</head>
<body>
<!-- 内容 -->
<div id="content" class="content">
    <div class="layout grid-default" id="grid-default">
        <!-- 侧栏 -->
        <div class="col-sub" id="col-sub">
            <div id="side-bar" class="side-bar">
                <div class="menu">
                    <ul id="foldinglist">
                        <li class="selected"><a href="#" target="menu" class="fl-title"><i
                                class="icon_small icon_open"></i>实例管理</a>
                            <ul class="fl-content">
                                <li><a href="listInstance" target="rightframe"><span>实例列表</span></a></li>
                            </ul>
                        </li>
                        <li class="selected"><a href="#" target="menu" class="fl-title"><i
                                class="icon_small icon_open"></i>任务管理</a>
                            <ul class="fl-content">
                                <li><a href="listJob" target="rightframe"><span>任务列表</span></a></li>
                                <li><a href="toAddHttpJob" target="rightframe"><span>　添加标准任务</span></a></li>
                                <li><a href="toAddShellJob" target="rightframe"><span>　添加SHELL任务</span></a></li>
                                <li><a href="toAddDataTrackingJob" target="rightframe"><span>　添加数据监控</span></a></li>
                                <li><a href="toAddLineCountJob" target="rightframe"><span>　添加文件监控</span></a></li>
                                <li><a href="toAddProcedureJob" target="rightframe"><span>　添加存储过程</span></a></li>
                                <li><a href="listAsyncJobQueues" target="rightframe"><span>异步任务队列</span></a></li>
                            </ul>
                        </li>
                        <li class="selected"><a href="#" target="menu" class="fl-title"><i
                                class="icon_small icon_open"></i>告警管理</a>
                            <ul class="fl-content">
                                <li><a href="listSub" target="rightframe"><span>告警信息订阅</span></a></li>
                                <li><a href="listRepeatAlarm" target="rightframe"><span>告警撤销管理</span></a></li>
                            </ul>
                        </li>
                        <li class="selected"><a href="#" target="menu" class="fl-title"><i
                                class="icon_small icon_open"></i>操作历史</a>
                            <ul class="fl-content">
                                <li><a href="listLog" target="rightframe"><span>历史列表</span></a></li>
                            </ul>
                        </li>
                        <li class="selected"><a href="#" target="menu" class="fl-title"><i
                                class="icon_small icon_close"></i>任务统计</a>
                            <ul class="fl-content" style="display: none;">
                                <li><a href="viewReportJob?reportType=3" target="rightframe"><span>任务汇总报表</span></a></li>
                                <li><a href="listReportJobRt" target="rightframe"><span>任务详细报表</span></a></li>
                            </ul>
                        </li>
                        <li class="selected"><a href="#" target="menu" class="fl-title"><i
                                class="icon_small icon_close"></i>系统配置</a>
                            <ul class="fl-content" style="display: none;">
                                <li><a href="listUser" target="rightframe"><span>用户管理</span></a></li>
                                <li><a href="listConfig" target="rightframe"><span>参数设定</span></a></li>
                                <li><a href="listCode" target="rightframe"><span>默认值设定</span></a></li>
                                <li><a href="viewOtherConfig" target="rightframe"><span>系统参数</span></a></li>
                            </ul>
                        </li>
                        <li class="selected"><a href="#" target="menu" class="fl-title"><i
                                class="icon_small icon_close"></i>调试工具</a>
                            <ul class="fl-content" style="display: none;">
                                <li><a href="testRequestAsynJob" target="rightframe"><span>异步任务请求测试</span></a></li>
                                <li><a href="testCallbackAsynJob" target="rightframe"><span>异步任务回调测试</span></a></li>
                                <li><a href="testDataTrackingJob" target="rightframe"><span>数据监控请求测试</span></a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- 侧栏 结束 -->
    </div>
    <!-- 布局 结束 -->
</div>
<!-- 内容 结束 -->
<script type="text/javascript">
    $(function() {
        /*  自适应高度  */
        var sidebar_el = $("#side-bar");
        var contentLeft = parent.document.getElementById("menu");
        var trueHeight = sidebar_el.height();

        function adjustHeight() {
            var adjustHeight = contentLeft.offsetHeight - 22;
            if (adjustHeight > trueHeight) {
                sidebar_el.css("height", adjustHeight + "px");
            }
        }

        adjustHeight();
        $(window).resize(function() {
            adjustHeight();
        });
    });

    /*  导航缩放  */
    var foldinglist = $("#foldinglist");
    foldinglist.find(".fl-title").click(function(){
       if($(this).find("i").hasClass("icon_close")){
          $(this).next('.fl-content').show();
          $(this).find('i').removeClass('icon_close');
          $(this).find('i').addClass('icon_open');   
       }else{
          $(this).next('.fl-content').hide();
          $(this).find('i').removeClass('icon_open');
          $(this).find('i').addClass('icon_close');   
       }
    });               
</script>
</body>
</html>