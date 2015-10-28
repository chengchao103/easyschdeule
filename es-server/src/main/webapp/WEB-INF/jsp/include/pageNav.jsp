<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="com.taobao.ad.easyschedule.commons.Constants" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="message-footer">
    <div class="paging"><font size="1px" color="#E2EBF5"><s:property value="easyscheduler.schedulerInstanceId"/>:<%=request.getRemoteAddr()%>:<%=request.getServerPort()%></font>
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

        总共：${query.totalItem}条 &nbsp;
        <s:if test="query == null || query.toPage <= 1">
            <img src="<%=request.getContextPath()%>/share/images/fy_first2.gif" width="16" height="16" align="absmiddle"
                 alt="首页"/><font color="#999999">首页</font>
            <img src="<%=request.getContextPath()%>/share/images/fy_back2.gif" width="16" height="16" align="absmiddle"
                 alt="上页"/><font color="#999999">上页</font>
        </s:if>
        <s:else>
            <img src="<%=request.getContextPath()%>/share/images/fy_first.gif" width="16" height="16" align="absmiddle"
                 alt="首页"/><a href="#" onClick="changePage(1)">首页</a>
            <img src="<%=request.getContextPath()%>/share/images/fy_back.gif" width="16" height="16" align="absmiddle"
                 alt="上页"/><a href="#" onClick="changePage('<s:property value="#prev" />')">上页</a>
        </s:else>

        <img src="<%=request.getContextPath()%>/share/images/sx2_b.gif" width="2" height="13" align="absmiddle"/>
        第<input type="text" id="inputToPage" style="line-height:15px;height:15px;" size="1" value="${query.toPage}"/>页&nbsp;&nbsp;共${query.totalPage}页
        <img src="<%=request.getContextPath()%>/share/images/sx2_b.gif" width="2" height="13" align="absmiddle"/>

        <s:if test="query == null || query.toPage == query.totalPage">
            <font color="#999999">下页</font><img src="<%=request.getContextPath()%>/share/images/fy_forward2.gif"
                                                 width="16"
                                                 height="16" align="absmiddle" alt="下页"/>
            <font color="#999999">末页</font><img src="<%=request.getContextPath()%>/share/images/fy_last2.gif" width="16"
                                                height="16" align="absmiddle" alt="末页"/>
        </s:if>
        <s:else>
            <a href="#" onClick="changePage('<s:property value="#next" />')">下页</a><img
                src="<%=request.getContextPath()%>/share/images/fy_forward.gif" width="16" height="16" align="absmiddle"
                alt="下页"/>
            <a href="#" onClick="changePage('<s:property value="query.totalPage" />')">末页</a><img
                src="<%=request.getContextPath()%>/share/images/fy_last.gif" width="16" height="16" align="absmiddle"
                alt="末页"/>
        </s:else>
        每页<select id="sel_pn_rownum" name="query.perPageSize">
        <option value="8"<s:if test="query.perPageSize == 8">selected</s:if> >8</option>
        <option value="10"<s:if test="query.perPageSize == 10">selected</s:if> >10</option>
        <option value="20"<s:if test="query.perPageSize == 20">selected</s:if> >20</option>
        <option value="50"<s:if test="query.perPageSize == 50">selected</s:if> >50</option>
        <option value="100"<s:if test="query.perPageSize == 100">selected</s:if> >100</option>
    </select>行

        <img src="<%=request.getContextPath()%>/share/images/sx2_b.gif" width="2" height="13" align="absmiddle"/>
    </div>
</div>
<script language="javascript">
    $(document).ready(function() {
        $('#sel_pn_rownum').change(onchangePageBysel);
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