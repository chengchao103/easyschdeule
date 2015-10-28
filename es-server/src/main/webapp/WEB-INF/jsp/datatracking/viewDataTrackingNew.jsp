<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="../include/header.jsp"%>
<SCRIPT >
	$(function() {
		Highcharts.setOptions({
			lang : {
				downloadJPEG : "下载 JPEG文件",
				shortMonths : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月",
						"9月", "10月", "11月", "12月" ],
				rangeSelectorZoom : "缩放"
			},
			global : {
				useUTC : true
			}
		});

	});

	function drowChart() {
		var url = 'viewTrackingDisplayJsonValue.action?filename=aapl-c.json&callback=?'
				+ '&query.jobName=${query.jobName}&query.jobGroup=${query.jobGroup}'
			url += '&query.queryStartTime=' + $("#queryStartTime").val()
					+ '&query.queryEndTime=' + $("#queryEndTime").val();
		$.getJSON(url, function(data) {
			chart = new Highcharts.StockChart({
				chart : {
					renderTo : 'container'
				},
				title: {
					text: '${query.jobName}'
				},
				tooltip: {  
		            formatter: function() {  
		            	return getFormatData(this.x)+"<br/>"
		            	+"监控值  "+Highcharts.numberFormat(this.y, 2);  
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
					name : '',
					data : data,
					tooltip: {
						valueDecimals: 2
					}
				}]
			});
		});
	}

	function doSearch() {
		drowChart();
	}
	function getFormatData(value){
		    // 声明变量。
		    var d, s;
		    // 创建 Date 对象。
		    d = new Date(value);
		    s = d.getFullYear() + "-";
		    s += ("0"+(d.getMonth()+1)).slice(-2) + "-";
		    s += ("0"+d.getDate()).slice(-2) + " ";
		    s += ("0"+d.getHours()).slice(-2) + ":";
		    s += ("0"+d.getMinutes()).slice(-2) + ":";
		    s += ("0"+d.getSeconds()).slice(-2) + "";
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
							<th><label>开始时间:</label>
							</th>
							<td><input id="queryStartTime" type="text"
								style="width: 200px;" class="date"
								onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
								name="query.queryStartTime" size="50"
								value="<s:date name="query.queryStartTime" format="yyyy-MM-dd" />"
								readonly <s:property value="disabled"/> /></td>

						</tr>
						<tr>
							<th><label>结束时间:</label>
							</th>
							<td><input id="queryEndTime" type="text"
								style="width: 200px;" class="date"
								onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
								name="query.queryEndTime" size="50"
								value="<s:date name="query.queryEndTime" format="yyyy-MM-dd" />"
								readonly <s:property value="disabled"/> /></td>

						</tr>
						<tr>
							<th>&nbsp;</th>
							<td><input type="button" value="查询" class="btn_search"
								onclick='return doSearch();'></td>
						</tr>
					</table>
				</div>
				<!-- 搜索框 结束 -->
				<div class="message-wrapper" id="message-wrapper">
					<div class="message-title">
						<ul>
							<li><a href="listJob"><span>任务列表</span>
							</a>
							</li>
							<li class="cur"><a href="#"><span>监控数据</span>
							</a>
							</li>
						</ul>
					</div>
					<div class="clearing"></div>
					<div id="container" style="height: 500px; min-width: 500px"></div>
				</div>
				<!-- 表格 结束 -->
			</div>
		</div>
		<!-- 主栏结束 -->
	</div>
	<!-- 布局 结束 -->
</div>
<!-- 内容 结束-->
<SCRIPT >
	drowChart();
</SCRIPT>
<script src="<%=request.getContextPath() %>/highchart/js/highstock.js"></script>
<script src="<%=request.getContextPath() %>/highchart/js/exporting.js"></script>
<%@include file="../include/bottom.jsp"%>