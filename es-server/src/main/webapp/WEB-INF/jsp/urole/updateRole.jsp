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
	<div>修改urole</div>
	<div>
		<form action="updateRole.action" method="post" id="searchform">
			<input type="hidden" name="uRole.id" value="<s:property value="uRole.id" />" />
			<div>
				<div>name:<input type="text" id="name" name="uRole.name" value="<s:property value="uRole.name" />" ></div>
				<div>descn:<input type="text" id="descn" name="uRole.descn" value="<s:property value="uRole.descn" />" ></div>
				<div class="submit-box">
					<input type="submit" value="修改" class="btn1"/>
					<input id="btnreset" type="reset" value="重置" class="btn1" />
				</div>
			</div>
		</form>
	</div>
</body>
