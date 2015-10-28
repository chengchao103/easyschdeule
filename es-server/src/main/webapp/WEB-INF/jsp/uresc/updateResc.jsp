<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@include file="../include/header.jsp"%>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>list</title>
	<script type="text/javascript">
		
		
	</script>
</head>
<body>
	<div>修改uresc</div>
	<div>
		<form action="updateResc.action" method="post" id="searchform">
			<input type="hidden" name="uResc.id" value="<s:property value="uResc.id" />" />
			<div>
				<div>name:<input type="text" id="name" name="uResc.name" value="<s:property value="uResc.name" />" ></div>
				<div>resType:<input type="text" id="resType" name="uResc.resType" value="<s:property value="uResc.resType" />" ></div>
				<div>resString:<input type="text" id="resString" name="uResc.resString" value="<s:property value="uResc.resString" />" ></div>
				<div>priority:<input type="text" id="priority" name="uResc.priority" value="<s:property value="uResc.priority" />" ></div>
				<div>descn:<input type="text" id="descn" name="uResc.descn" value="<s:property value="uResc.descn" />" ></div>
				<div class="submit-box">
					<input type="submit" value="修改" class="btn1"/>
					<input id="btnreset" type="reset" value="重置" class="btn1" />
				</div>
			</div>
		</form>
	</div>
</body>
