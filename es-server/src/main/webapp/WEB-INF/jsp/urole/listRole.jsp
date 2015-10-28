<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@include file="../include/header.jsp"%>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>list</title>
	<script type="text/javascript">
		function doSearch(){
			$('#searchform').submit();
			return true;
		}
		
	</script>
</head>
<body>
	<div>
		<form action="listRole.action" method="post" id="searchform">
			<div>
				<div>name:<input type="text" id="name" name="query.name" value="<s:property value="query.name" />" ></div>
				<div>descn:<input type="text" id="descn" name="query.descn" value="<s:property value="query.descn" />" ></div>
				<div class="submit-box">
					<input type="button" value="查询" class="btn1"
						onclick="initPageNum(); doSearch();" />
					<input id="btnreset" type="button" value="重置" class="btn1" />
				</div>
			</div>

			<div class="box list">
				<div class="add-box">
					<a href="viewAddRole.action">新增</a>
				</div>
				<table class="plan-adgroup-list" cellspacing="0">
					<thead>
						<tr>
							<th>
								id
							</th>
							<th>
								name
							</th>
							<th>
								descn
							</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="#request.uRoles">
							<tr>
								<td>
									<s:property value="id" />
								</td>
								<td>
									<s:property value="name" />
								</td>
								<td>
									<s:property value="descn" />
								</td>
								<td>
									<a href="deleteURole.action?id=<s:property value="id" />">删除</a>  | <a href="viewUpdateURole.action?id=<s:property value="id" />" >修改</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>

				</table>
				<%@include file="../include/pageNav.jsp" %>
			</div>
		</form>
	</div>
</body>
