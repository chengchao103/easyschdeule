<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="../include/header.jsp"%>

<script type="text/javascript">
    function doSearch() {
        $('#searchForm').submit();
        return true;
    }
    function orderSub(orderby) {
    	$('#successRateOrderBy').val(orderby);
        $('#searchForm').submit();
        return true;
    }
</script>



<form action="listReportJobRt.action" method="post" id="searchForm">


<input type="hidden" name="query.successRateOrderBy" id="successRateOrderBy">

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
								<td><input id="queryStartTime" type="text" style="width: 300px;"
									class="date" onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
									name="query.queryStartTime" size="50"
									value="<s:date name="query.queryStartTime" format="yyyy-MM-dd" />"
									readonly <s:property value="disabled"/> />
								</td>

							</tr>
							<tr>
								<th><label>结束时间:</label></th>
								<td><input id="queryEndTime" type="text" style="width: 300px;"
									class="date" onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
									name="query.queryEndTime" size="50"
									value="<s:date name="query.queryEndTime" format="yyyy-MM-dd" />" readonly
									<s:property value="disabled"/> />
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
								<li class="cur"><a href="listLog"><span>任务报表</span> </a></li>
							</ul>
						</div>
						<div class="clearing"></div>
						<table class="message-list" id="message-list">
							<thead>
								<tr>
									<th width="10px"><input type="checkbox" class="checkAll"
										id="checkAll" /></th>
									<th nowrap>任务组</th>
									<th nowrap>任务名称</th>
									<th nowrap style="text-align: center">总执行数</th>
									<th nowrap style="text-align: center">成功数</th>
									<th nowrap style="text-align: center">
									<s:if test='query.successRateOrderBy==true'><a href="#" onclick="orderSub(false)" >成功率</a></s:if>
									<s:if test='query.successRateOrderBy!=true'><a href="#" onclick="orderSub(true)" >成功率</a></s:if>
									</th>
									<th nowrap style="text-align: center">失败数</th>
									<th nowrap style="text-align: center">失败率</th>
									<th nowrap style="text-align: center">平均RT(ms)</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="#request.jobRtList" status="status">
									<tr>
										<td><input type="checkbox" class="checkRow"
											name="checkRow" /></td>
										<td>
											<s:iterator value="groups">
                                            	<s:if test='keycode==jobGroup'><s:property value="keyname"/></s:if>
                                       		</s:iterator>
										</td>
										<td nowrap style="padding: 1px 9px 1px;"><a href="listLog.action?query.opsubname=<s:property value="jobGroup" />|<s:property value="jobName" />"><s:property value="jobName" /></a>
										</td>
										<td nowrap style="text-align: right"><s:property value="totalNum" />
										</td>
										<td nowrap style="text-align: right"><s:property value="successNum" />
										</td>
										<td nowrap style="text-align: right"><s:property value="successRate" />%
										</td>
										<td nowrap style="text-align: right"><s:property value="errorNum" />
										</td>
										<td nowrap style="text-align: right"><s:property value="errorRate" />%
										</td>
										<td nowrap style="text-align: right"><s:property value="showRt" />
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<s:if test="#request.jobRtList.size > 0">
							<%@include file="../include/pageNav.jsp"%>
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
<%@include file="../include/bottom.jsp"%>

