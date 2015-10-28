<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
    $(function() {
        /*  自适应高度  */
        var mainwrap_el = $("#main-wrap");
        var contentLeft = parent.document.getElementById("rightframe");
        var trueHeight = mainwrap_el.height();
        function adjustHeight() {
            try {
                var adjustHeight = contentLeft.offsetHeight - 42;
                if (adjustHeight > trueHeight) {
                    mainwrap_el.css("height", adjustHeight + "px");
                }
            }
            catch(e) {
            }
        }
        adjustHeight();
        $(window).resize(function() {
            adjustHeight();
        });
    });
    
    //全选与取消全选
	$('#checkAll').click(function(){
		var chk = $(this).attr("checked");
		$('input[type=checkbox]').each(function(){
			$(this).attr("checked",chk);
		});
	});

</script>
</body>
</html>