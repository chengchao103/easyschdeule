<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>

<SCRIPT>
 	$(function() {
		Highcharts.setOptions({
			   lang:{
			      downloadJPEG:"下载 JPEG文件",
			      shortMonths:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
			      rangeSelectorZoom:"缩放"
			   },
			   global : {
			      useUTC : true
			   }
			  });
	});
	
	function drowChart(){
	    var url =
			'viewReportJobJson.action?filename=aapl-c.json&callback=?&reportType='+${reportType}
			url+='&startTime='+$("#startTime").val()+
					'&endTime='+$("#endTime").val()	;
		 
		$.getJSON(url, function(data) {
			window.chart = new Highcharts.StockChart({
				chart : {
					renderTo : 'container'
				},
	
				rangeSelector : {
					selected : 1
				},
	
				title : {
					text : '${pageName}'
				},
				tooltip: {  
		            formatter: function() {  
		            	return getFormatData(this.x)+"<br/>"
		            	+"监控值 "+Highcharts.numberFormat(this.y, 2);  
		            }  
		        },
		    	xAxis: {

					 type: 'datetime',

					 labels: {formatter:function() {

					 var vDate = new Date(this.value);

					 return vDate.getHours()+":"+vDate.getMinutes()+":"+vDate.getSeconds();

					 }}

			    },
				series : [{
					name : '监控值',
					data : data,
					tooltip: {
						valueDecimals: 2
					}
				}]
				,credits:{enabled:true,href:"#",text:"easyschedule监控数据"}
	
			});
		});
	}
	
	function doSearch(){
		drowChart();
	}
	function getFormatData(value){
	    // 声明变量。
	    var d, s;
	    // 创建 Date 对象。
	    d = new Date(value);
	    s = d.getFullYear() + "-";
	    s += ("0"+(d.getMonth()+1)).slice(-2) + "-";
	    s += ("0"+d.getDate()).slice(-2) ;
	    return s;
	}
</SCRIPT>

<!-- 内容 -->
<div id="content" class="content content-right">
    <div class="layout grid-default" id="grid-default">
        <!-- 主栏 -->
        <div class="col-main" id="col-main">
            <div class="main-wrap" id="main-wrap">
                <!-- 搜索框 -->
                <div class="search-wrapper">
                   <table>
						<tr>
							<th><label>开始时间:</label></th>
							
							<td><input id="startTime" type="text" style="width: 200px;"
								class="date" onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
								name="startTime" size="50"
								value="<s:date name="startTime" format="yyyy-MM-dd" />"
								readonly <s:property value="disabled"/> />
							</td>
							 
						</tr>
						<tr>
							<th><label>结束时间:</label> </th>
								<td>
								<input id="endTime" type="text" style="width: 200px;"
								class="date" onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
								name="endTime" size="50"
								value="<s:date name="endTime" format="yyyy-MM-dd" />"
								readonly <s:property value="disabled"/> />
								</td>
						 
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td><input type="button" value="查询" class="btn_search"
								onclick='return doSearch();'>

							</td>
						</tr>
					</table>
                </div>
                <!-- 搜索框 结束 -->
                <div class="message-wrapper" id="message-wrapper">
                    <div class="message-title">
                        <ul>
                            <li  <s:if test="reportType==0"> class="cur"  </s:if> ><a href=<s:if test="reportType==0">#</s:if><s:else>viewReportJob?reportType=0</s:else>><span>任务总数</span></a></li>
                            <li  <s:if test="reportType==1"> class="cur"  </s:if> ><a href=<s:if test="reportType==1">#</s:if><s:else>viewReportJob?reportType=1</s:else>><span>任务成功数</span></a></li>
                            <li  <s:if test="reportType==2"> class="cur"  </s:if> ><a href=<s:if test="reportType==2">#</s:if><s:else>viewReportJob?reportType=2</s:else>><span>任务成功率</span></a></li>
                            <li  <s:if test="reportType==3"> class="cur"  </s:if> ><a href=<s:if test="reportType==3">#</s:if><s:else>viewReportJob?reportType=3</s:else>><span>任务失败数</span></a></li>
                            <li  <s:if test="reportType==4"> class="cur"  </s:if> ><a href=<s:if test="reportType==4">#</s:if><s:else>viewReportJob?reportType=4</s:else>><span>任务失败率</span></a></li>
                            <li  <s:if test="reportType==5"> class="cur"  </s:if> ><a href=<s:if test="reportType==5">#</s:if><s:else>viewReportJob?reportType=5</s:else>><span>平均响应时间</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <input type="hidden" name="displayValue" id="displayValue"
                           value="<s:property value="displayValue"/>">
                   <div id="container" style="height: 500px; min-width: 500px"></div>
                </div>
            </div>
        </div>
        <!-- 主栏结束 -->
    </div>
    <!-- 布局 结束 -->
</div>
<!-- 内容 结束-->

<script src="<%=request.getContextPath() %>/highchart/js/highstock.js"></script>
<script src="<%=request.getContextPath() %>/highchart/js/exporting.js"></script>
<script>
drowChart();
</script>
<%@include file="../include/bottom.jsp" %>
