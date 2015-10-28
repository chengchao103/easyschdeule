<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@include file="../include/header.jsp"%>
<html>
  <head>
  <script src="<%=request.getContextPath()%>/share/js/jquery/fancyzoom.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/share/js/my97datepicker/wdatepicker.js" encoding="UTF-8"></script>
  <script type="text/javascript">
  </script>
  <title>节点监控</title>
  </head>
  <body >
<form name="nodeMonitor" method="post" action="nodeMonitor">
<table class="t1add" width="780px">
	<tr>
		<th colspan="2"></th>
	</tr>
	<tr onMouseOver="this.className='m1add'" onMouseOut="this.className=''">
		<td width="150px"> </td>
		<td>
		</td>
	</tr>
	<tr class="row1add" onMouseOver="this.className='m1add'" onMouseOut="this.className='row1add'">
		<td></td>
		<td></td>
	</tr>
	<tr onMouseOver="this.className='m1add'" onMouseOut="this.className=''">
		<td></td>
		<td></td>
	</tr>
	<tr class="row1add" onMouseOver="this.className='m1add'" onMouseOut="this.className='row1add'">
		<td></td>
		<td></td>
	</tr>
</table>
<br><br>
<br>
<br>
<br>
<br>
</form>
</body>
</html>