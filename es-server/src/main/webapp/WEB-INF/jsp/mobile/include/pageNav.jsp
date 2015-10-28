<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="com.taobao.ad.easyschedule.commons.Constants" %>
<s:if test="query == null">
<s:bean name="com.taobao.ad.easyschedule.base.PageInfo" id="query">
    <s:param name="toPage" value="0"/>
    <s:param name="perPageSize" value="10"/>
    <s:param name="totalItem" value="0"/>
</s:bean>
</s:if>
<s:else>
<input id="toPage" name="query.toPage" type="hidden" value="<s:property value="query.toPage" />"/>
<input id="totalItem" name="query.totalItem" type="hidden" value="<s:property value="query.totalItem" />"/>
</s:else>

<s:set name="next" value="query.toPage + 1"/>
<s:set name="prev" value="query.toPage - 1"/>

&nbsp;${query.totalItem}条${query.totalPage}页
<s:if test="query == null || query.toPage <= 1">
<font color="#999999">首页</font>
<font color="#999999">上页</font>
</s:if>
<s:else>
<a href="#" onClick="changePage(1)">首页</a>
<a href="#" onClick="changePage('<s:property value="#prev" />')">上页</a>
</s:else>
<input type="text" id="inputToPage" style="line-height:15px;height:15px;" size="1" data-role="none" value="${query.toPage}"/>
<s:if test="query == null || query.toPage == query.totalPage">
<font color="#999999">下页</font>
<font color="#999999">末页</font>
</s:if>
<s:else>
<a href="#" onClick="changePage('<s:property value="#next" />')">下页</a>
<a href="#" onClick="changePage('<s:property value="query.totalPage" />')">末页</a>
</s:else>
<select id="sel_pn_rownum" name="query.perPageSize" data-role="none" onchange="doSearch();" data-native-menu="false">
	<option value="10"<s:if test="query.perPageSize == 10">selected</s:if> >10</option>
	<option value="20"<s:if test="query.perPageSize == 20">selected</s:if> >20</option>
	<option value="50"<s:if test="query.perPageSize == 50">selected</s:if> >50</option>
	<option value="90"<s:if test="query.perPageSize == 90">selected</s:if> >90</option>
</select>

<script language="javascript">
    $(document).ready(function() {
        $('#inputToPage').keydown(onInputPageNum);
    });

    function changePage(page) {
        if (checknumber(page) == false || page <= 0) {
            page = 1;
        }
        $("#toPage").val(page);
        doSearch();
    }

    function onchangePageBysel() {
        $('#perPageSize').val($('#sel_pn_rownum').val());
        changePage(1);
    }

    function checknumber(str)
    {
        var reg = /^[0-9]*$/;
        return (reg.test(str));
    }
    function onInputPageNum(e) {
        if (e.keyCode == 13) {
            changePage($(this).val());
        }
    }
    function onInputPageNumBlur() {
        changePage($('#inputToPage').val());
    }

    function initPageNum() {
        $("#toPage").val(1);
    }

</script>