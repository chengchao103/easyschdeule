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
                            <li class="cur"><a href="#"><span>用户添加</span></a></li>
                            <li><a href="viewUpdateCurrentUser"><span>修改密码</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <s:form action="addUser.action" method="post" id="addUserForm" theme="simple">
                    <s:token/>
                        <div class="add-wrapper">
                            <div style="color:red;"><s:property value="error"/></div>
                            <ul class="form-box">
                                <li class="clear" style="width: 700px;">
                                    <label>用户名:</label>
                                    <s:textfield name="user.username" id="username" maxlength="32"/>
                                    <div id="usernameTip"></div>
                                </li>
                                                                
                                <li class="clear" style="width: 700px;">
                                    <label>邮箱:</label>
                                    <s:textfield name="user.email" id="email" maxlength="32" />
                                    <div id="emailTip"></div>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>手机号码:</label>
                                    <s:textfield name="user.mobile" id="mobile" maxlength="32"/>
                                    <div id="mobileTip"></div>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>密码:</label>
                                    <s:password name="user.password" id="password" maxlength="32"/>
                                    <div id="passwordTip"></div>
                                </li>
                                <li>
                                    <label>旺旺:</label>
                                    <s:textfield name="user.descn" id="descn" maxlength="64"/>
                                    <div id="descnTip"></div>
                                </li>
                                <li>
                                	<label>角色:<br><br><br></label>
                                    <s:select id="roles" name="roles" multiple="true" list="#request.uRoles" listKey="id"  listValue="descn" size="8" cssStyle="height:100px;width:242px;"/> 
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
        $.formValidator.initConfig({formid:"addUserForm",onerror:function(msg) {
            alert(msg);
        }});
        $('#username').formValidator({
            onshow :"请输入用户名",
            onfocus :"请输入用户名，最多32个字符",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :32,
            onerror :"请输入用户名，最多32个字符"
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
                if(val == ""){
                	return true;
                }
                if (numStr == "NaN" || val == "") {
                    return "请必须输入数字";
                } else {
                    if (numStr.indexOf(".") > -1) {
                        return "请输入11位数字的手机号码";
                    }
                }
                return true;
            }
        });
        
        $('#password').formValidator({
            onshow :"请输入密码",
            onfocus :"请输入密码，最多32个字符",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :32,
            onerror :"请输入密码，最多32个字符"
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
        
        $('#roles').formValidator({
            onshow :"请选择用户角色",
            onfocus :"请选择用户角色，可以选择一个或多个角色 ，最多10个",
            oncorrect :"输入正确"
        }).inputValidator({
            min :1,
            onerror :"请选择用户角色，可以选择一个或多个角色 ，最多10个"
        }).functionValidator({});
    });
</script>
<%@include file="../include/bottom.jsp" %>