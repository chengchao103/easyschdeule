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
	<div>新增uresc</div>
	<div>
		<form action="addResc.action" method="post" id="searchform">
			<div>
				<div>name:<input type="text" id="name" name="uResc.name" value="<s:property value="field.name" />" ></div>
				<div>resType:<input type="text" id="resType" name="uResc.resType" value="<s:property value="field.resType" />" ></div>
				<div>resString:<input type="text" id="resString" name="uResc.resString" value="<s:property value="field.resString" />" ></div>
				<div>priority:<input type="text" id="priority" name="uResc.priority" value="<s:property value="field.priority" />" ></div>
				<div>descn:<input type="text" id="descn" name="uResc.descn" value="<s:property value="field.descn" />" ></div>
				<div class="submit-box">
					<input type="submit" value="新增" class="btn1"/>
					<input id="btnreset" type="reset" value="重置" class="btn1" />
				</div>
			</div>
		</form>
	</div>
</body>
