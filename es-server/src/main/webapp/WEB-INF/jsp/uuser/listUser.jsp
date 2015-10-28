<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>
<script type="text/javascript">
    function doSearch() {
        $('#searchform').submit();
        return true;
    }
    function doReset() {
        $('#username').val("");
        $('#descn').val("");
    }
</script>
<form action="listUser.action" method="post" id="searchform">
    <div id="content" class="content content-right">
        <div class="layout grid-default" id="grid-default">
            <!-- 主栏 -->
            <div class="col-main" id="col-main">
                <div class="main-wrap" id="main-wrap">
                    <!-- 搜索框 -->
                    <div class="search-wrapper">
                        <table>
                            <tr>
                                <th><label>用户名:</label></th>
                                <td>
                                    <input type="text" id="username" name="query.username" value="<s:property value="query.username" />" >
                                </td>
                            </tr>
                            <tr>
                                <th><label>旺旺:</label></th>
                                <td>
                                    <input type="text" id="descn" name="query.descn" value="<s:property value="query.descn" />" >
                                </td>
                            </tr>
                            <tr>
                                <th>&nbsp;</th>
                                <td colspan="2">
                                    <input type="button" value="查询" class="btn_search" onclick='doSearch();'>
                                    <input id="btnreset" type="button" value="重置" class="btn_search" onclick='doReset();'>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!-- 搜索框 结束 -->

                    <div class="message-wrapper" id="message-wrapper">
                        <div class="message-title">
                            <ul>
                                <li class="cur"><a href="listUser"><span>用户列表</span></a></li>
                                <li><a href="viewAddUser"><span>用户添加</span></a></li>
                            </ul>
                        </div>
                        <div class="clearing"></div>
                        <table class="message-list" id="message-list">
                            <thead>
                            <tr>
                                <th width="10px"><input type="checkbox" class="checkAll" id="checkAll"/></th>
								<th>ID</th>
								<th>用户名</th>
								<th>旺旺</th>
								<th>邮箱</th>
								<th>手机</th>
								<th>角色</th>
								<th>状态</th>
								<th>创建时间</th>
								<th>修改时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
								<s:iterator value="#request.uUsers">
									<tr>
										<td><input type="checkbox" class="checkRow"
											name="checkRow" /></td>
										<td><s:property value="id" /></td>
										<td><s:property value="username" /></td>
										<td><s:property value="descn" /></td>
										<td><s:property value="email" />&nbsp;</td>
										<td><s:property value="mobile" />&nbsp;</td>
										<td><s:iterator value="#request.userXRole">
												<s:if test="id==userId">
													<s:iterator value="#request.uRoles">
														<s:if test="id==roleId">
															<s:property value="descn" />,</s:if>
													</s:iterator>
												</s:if>
											</s:iterator>
										</td>
										<td><s:property
												value="@com.taobao.ad.easyschedule.commons.Constants@getUserStateDesc(status)" />
										</td>
										<td nowrap><s:date name="createTime"
												format="yyyy-MM-dd HH:mm:ss" /></td>
										<td nowrap><s:date name="updateTime"
												format="yyyy-MM-dd HH:mm:ss" /></td>
										<td nowrap>
										<s:if test="#request.isAdmin==true">
												<a href="viewUpdateUser.action?id=<s:property value="id" />">修改</a>
												<a href="deleteUser.action?id=<s:property value="id" />"
													onclick="return confirm('确定要删除？');">删除</a>
												<s:if test="username==#request.curUserName">
														<a href="viewUpdateCurrentUser.action?id=<s:property value="id" />">修改密码</a>
													</s:if>
											</s:if> 
											<s:else>
												<div>
													<s:if test="username==#request.curUserName">
														<a href="viewUpdateCurrentUser.action?id=<s:property value="id" />">修改</a>
													</s:if>
												</div>
											</s:else>
										</td>
									</tr>
								</s:iterator>
							</tbody>
                        </table>
                        <s:if test="#request.uUsers.size > 0">
                            <%@include file="../include/pageNav.jsp" %>
                        </s:if>
                    </div>
                    <!-- 表格 结束 -->
                </div>
            </div>
            <!-- 主栏结束 -->
        </div>
        <!-- 布局 结束 -->
    </div>
    <!-- 内容 结束-->
</form>
<%@include file="../include/bottom.jsp" %>