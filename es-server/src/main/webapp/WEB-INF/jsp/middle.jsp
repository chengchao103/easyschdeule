<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="" />
    <meta name="keywords" content="" />
	<title>任务管理平台</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/share/css/base.css" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/share/css/style.css" />
	<script type="text/javascript" src="<%= request.getContextPath() %>/share/js/jquery/jquery.min.js"></script>
</head>
<body>
		<!-- 内容 -->
		<div id="content" class="content">
			<div class="layout grid-default" id="grid-default">
				<!-- 侧栏 -->
				<div class="col-sub" id="col-sub">
					<div class="sidebar_zoom" id="sidebar_zoom">
						<a href="#" class="open"><span>收拢</span></a>
					</div>
				</div>
				<!-- 侧栏 结束 -->
			</div>
			<!-- 布局 结束 -->
		</div>
		<!-- 内容 结束 -->
		<script type="text/javascript">
			/*  侧栏缩放  */
			var sidebar_zoom_el = $("#sidebar_zoom");
			sidebar_zoom_el.click(function(){
				if(sidebar_zoom_el.find("a").attr("class") == "open"){
					parent.parent.document.getElementById("mainFrame").cols='0,12,*';
					sidebar_zoom_el.find("a").attr("class","close");
				}else{
					parent.parent.document.getElementById("mainFrame").cols='196,12,*';
					sidebar_zoom_el.find("a").attr("class","open");
				}
			});
		</script>
</body>
</html>