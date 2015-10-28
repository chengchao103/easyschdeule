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
                            <li><a href="listConfig"><span>参数列表</span></a></li>
                            <li><a href="viewAddConfig"><span>参数添加</span></a></li>
                            <li class="cur"><a href="#"><span>参数修改</span></a></li>
                            <li><a href="viewOtherConfig"><span>系统参数</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <s:form action="updateConfig.action" method="post" id="updateConfigForm" theme="simple">
                    <s:token/>
                        <div class="add-wrapper">
                            <ul class="form-box">
                                <li class="clear" style="width: 700px;">
                                    <label>参数KEY</label>
                                    <s:textfield name="config.configkey" id="configkey" maxlength="512"
                                                 readonly="true"/>
                                    <div id="configkeyTip"></div>
                                </li>
                                <li>
                                    <label>参数VALUE</label>
                                    <s:textfield name="config.configvalue" id="configvalue" maxlength="512"/>
                                    <div id="configvalueTip"></div>
                                </li>
                                <li>
                                    <label>描述</label>
                                    <s:textfield name="config.description" id="description" maxlength="512"/>
                                    <div id="descriptionTip"></div>
                                </li>
                            </ul>
                            <div style="text-align: center; color: red;" id="fieldError"></div>
                            <div class="submit-box">
                                <input id="btnsave" type="submit" value="保存" class="btn1"/>
                                <input id="backs" type="button" class="btn1" value="返回" onclick='goback();'/>
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
        window.location.href = "listConfig.action";
    }

    $(document).ready(function() {
        $.formValidator.initConfig({formid:"updateConfigForm",onerror:function(msg) {
            alert(msg);
        }});
        $('#参数KEY').formValidator({
            onshow :"请输入参数KEY",
            onfocus :"请输入参数KEY，最多64个字符",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :64,
            onerror :"请输入参数KEY，最多64个字符"
        });
        $('#configvalue').formValidator({
            onshow :"请输入参数VALUE",
            onfocus :"请输入参数VALUE，最多512个字符",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :512,
            onerror :"请输入参数VALUE，最多512个字符"
        });

        $('#description').formValidator({
            onshow :"请输入描述",
            onfocus :"请输入描述，最多512个字符 ",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :512,
            onerror :"请输入描述，最多512个字符 "
        });
    });
</script>
<%@include file="../include/bottom.jsp" %>