<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<SCRIPT language="Javascript"
        SRC="<%=request.getContextPath()%>/share/js/FusionCharts.js"></SCRIPT>
<SCRIPT language="JavaScript">
    //animation     是否用动画显示
    //showNames		是否显示x轴的坐标值
    var strXML = "<s:property value="displayValue"/>";
    var start;
    var end;
    for (var i = 0; ;) {
        start = strXML;
        strXML = strXML.replace("&lt;", "<");
        strXML = strXML.replace("&gt;", ">");
        end = strXML;
        if (start == end) {
            break;
        }
    }
    function updateChart() {
        var chart1 = new FusionCharts("<%=request.getContextPath()%>/share/amline/FCF_Line.swf", "chart1Id", "900", "400", "0", "0");
        chart1.setDataXML(strXML);
        chart1.render("chart1div");
    }
</SCRIPT>
<!-- 内容 -->
<div id="content" class="content content-right">
    <div class="layout grid-default" id="grid-default">
        <!-- 主栏 -->
        <div class="col-main" id="col-main">
            <div class="main-wrap" id="main-wrap">
                <div class="message-wrapper" id="message-wrapper">
                    <div class="message-title">
                        <ul>
                            <li><a href="listJob"><span>任务列表</span></a></li>
                            <li class="cur"><a href="#"><span>监控数据</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <input type="hidden" name="displayValue" id="displayValue"
                           value="<s:property value="displayValue"/>">
                    <div id="chart1div">"你的浏览器不支持flash或者缺失该插件,请检查"</div>
                    <input type="button" class="btn1" value="刷新" onclick="return updateChart();"/>
                    <input id="backs" class="btn1" type="button" value="返回" onclick='goback();'/>
                </div>
                <!-- 表格 结束 -->
            </div>
        </div>
        <!-- 主栏结束 -->
    </div>
    <!-- 布局 结束 -->
</div>
<!-- 内容 结束-->
<SCRIPT language="JavaScript">
    updateChart();
    function goback() {
        window.location.href = "listJob.action";
    }
</SCRIPT>
<%@include file="../include/bottom.jsp" %>
