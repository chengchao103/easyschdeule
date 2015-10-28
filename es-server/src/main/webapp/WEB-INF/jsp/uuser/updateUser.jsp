<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="../include/header.jsp" %>

<div id="content" class="content content-right">
    <div class="layout grid-default" id="grid-default">
        <!-- 主栏 -->
        <div class="col-main" id="col-main">
            <div class="main-wrap" id="main-wrap">
                <div class="message-wrapper" id="message-wrapper">
                    <div class="message-title">
                        <ul>
                            <li><a href="listUser"><span>用户列表</span></a></li>
                            <li><a href="viewAddUser"><span>用户添加</span></a></li>
                            <li class="cur"><a href="#"><span>用户修改</span></a></li>
                            <li><a href="viewUpdateCurrentUser"><span>修改密码</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <s:form action="updateUser.action" method="post" id="updateUserForm" theme="simple">
                    <s:token/>
                    	<input type="hidden" name="user.id" value="<s:property value="user.id" />"/>
                        <div class="add-wrapper">
                        <div style="color:red;"><s:property value="error"/></div>
                            <ul class="form-box">
                                <li class="clear" style="width: 700px;">
                                    <label>用户名:</label>
                                    <s:textfield name="user.username" id="username" maxlength="32" readonly="true"/>
                                    <div id="usernameTip"></div>
                                </li>
                                
                                <li class="clear" style="width: 700px;">
                                    <label>邮箱:</label>
                                    <s:textfield name="user.email" id="email" maxlength="32" />
                                    <div id="emailTip"></div>
                                </li>
                                
                                <li class="clear" style="width: 700px;">
                                    <label>手机:</label>
                                    <s:textfield name="user.mobile" id="mobile" maxlength="32"/>
                                    <div id="mobileTip"></div>
                                </li>
                                 
                                <li>
                                    <label>状态:</label>
                                    <s:textfield name="user.status" id="status" maxlength="1"/>
                                    <div id="statusTip"></div>
                                </li>
                                <li>
                                    <label>旺旺:</label>
                                    <s:textfield name="user.descn" id="descn" maxlength="64" readonly="true"/>
                                    <div id="descnTip"></div>
                                </li>
                                <li>
                                	<label>角色:<br><br><br></label>
                                    <select id="roles" name="roles"   style="width:242px;" >
									<s:iterator value="#request.uRoles">
										<option value="<s:property value="id" />" <s:iterator value="#request.userXRole"><s:if test="id==roleId">selected</s:if></s:iterator>><s:property value="descn" /></option>
									</s:iterator>
									</select>
									<div id="rolesTip"></div>
                                </li>
                                <li>
                                	<label>任务组:<br><br><br></label>
                                    <select id="groups" name="groups" size="8" style="height:190px;width:242px;" multiple="multiple">
									<s:iterator value="#request.uGroups">
										<option value="<s:property value="keycode" />" <s:iterator value="#request.userXGroup"><s:if test="keycode==groupId">selected</s:if></s:iterator>><s:property value="keyname" /></option>
									</s:iterator>
									</select>
									<div id="groupsTip"></div>
                                </li>
                            </ul>
                            <br><br><br><br>
                            <div style="text-align: center; color: red;" id="fieldError"></div>
                            <div class="submit-box">
                                <input id="btnsave" class="btn1" type="submit" value="保存"/>
                                <input id="backs" class="btn1" type="button" value="返回" onclick='goback();'/>
                            </div>
                            <br>
                            <br>
                            <br>
                        </div>
                    </s:form>
                </div>
                <!-- 表格 结束 -->
            </div>
        </div>
        <!-- 主栏结束 -->
    </div>
    <!-- 布局 结束 -->
</div>
<!-- 内容 结束-->
<script type="text/javascript">
    function goback() {
        window.location.href = "listUser.action";
    }
    $(document).ready(function() {
        $.formValidator.initConfig({formid:"updateUserForm",onerror:function(msg) {
            alert(msg);
        }});
		<s:if test="user.username==#request.curUserName">
        $('#password').formValidator({
            onshow :"请输入密码（不修改，请保留为空）",
            onfocus :"请输入密码（不修改，请保留为空），最多32个字符",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :0,
            max :32,
            onerror :"请输入密码（不修改，请保留为空），最多32个字符"
        });
		</s:if>
		 
       $('#status').formValidator({
            onshow :"请输入状态（0：无效，1：有效）",
            onfocus :"请输入状态（0：无效，1：有效），最多1个字符 ",
            oncorrect :"输入正确"
        }).inputValidator({
            min :1,
            max :1,
            onerror :"请输入状态（0：无效，1：有效），最多1个字符 "
        }).functionValidator({
            fun : function(val, elem) {
                var num = Number(val);
                var numStr = num.toString();
                if (numStr == "NaN" || val == "") {
                    return "请必须输入数字";
                } else {
                    if (numStr.indexOf(".") > -1) {
                        return "请输入整数";
                    }
                }
                return true;
            }
        });
        
        $('#descn').formValidator({
            onshow :"请输入旺旺",
            onfocus :"请输入旺旺，最多64个字符 ",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :64,
            onerror :"请输入旺旺，最多64个字符 "
        });
        $('#email').formValidator({
            onshow :"请输入邮箱",
            onfocus :"请输入邮箱，最多32个字符 ",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :32,
            onerror :"请输入旺旺，最多32个字符 "
        });
        
        $('#groups').formValidator({
            onshow :"请选择任务组",
            onfocus :"请选择任务组，可以选择0个或多个任务组",
            oncorrect :"输入正确"
        }).inputValidator({
            min :0,
            onerror :"请选择任务组，可以选择0个或多个任务组 "
        }).functionValidator({});
        
         $('#mobile').formValidator({
            onshow :"请输入手机号码",
            onfocus :"请输入11位数字的手机号码",
            oncorrect :"输入正确"
        }).inputValidator({
            min :0,
            max :11,
            onerror :"请输入11位数字的手机号码 "
        }).functionValidator({
            fun : function(val, elem) {
                var num = Number(val);
                var numStr = num.toString();
                if (numStr == "NaN") {
                    return "请必须输入数字";
                } else if (val == "") {
                } else {
                    if (numStr.indexOf(".") > -1) {
                        return "请输入11位数字的手机号码";
                    }
                }
                return true;
            }
        });
    });
</script>
<%@include file="../include/bottom.jsp" %>