<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@include file="../include/header.jsp"%>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>list</title>
	<script type="text/javascript">
		
		
	</script>
</head>
<body>
	<div>修改logs</div>
	<div>
		<form action="updateLog.action" method="post" id="searchform">
			<input type="hidden" name="logs.id" value="<s:property value="logs.id" />" />
			<div>
				<div>optype:<input type="text" id="optype" name="logs.optype" value="<s:property value="logs.optype" />" ></div>
				<div>opname:<input type="text" id="opname" name="logs.opname" value="<s:property value="logs.opname" />" ></div>
				<div>opsubtype:<input type="text" id="opsubtype" name="logs.opsubtype" value="<s:property value="logs.opsubtype" />" ></div>
				<div>opsubname:<input type="text" id="opsubname" name="logs.opsubname" value="<s:property value="logs.opsubname" />" ></div>
				<div>opdetail:<input type="text" id="opdetail" name="logs.opdetail" value="<s:property value="logs.opdetail" />" ></div>
				<div class="submit-box">
					<input type="submit" value="修改" class="btn1"/>
					<input id="btnreset" type="reset" value="重置" class="btn1" />
				</div>
			</div>
		</form>
	</div>
</body>
