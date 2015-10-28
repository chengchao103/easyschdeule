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
                            <li><a href="listCode"><span>编码列表</span></a></li>
                            <li><a href="viewAddCode"><span>编码添加</span></a></li>
                            <li class="cur"><a href="#"><span>编码修改</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <s:form action="updateCode.action" method="post" id="updateCodeForm" theme="simple">
                    <s:token/>
                        <div class="add-wrapper">
                        	<div style="color:red;"><s:property value="error"/></div>
                            <ul class="form-box">
                                <li class="clear" style="width: 700px;">
                                    <label>KEY:</label>
                                    <s:textfield id="codekey" name="code.codekey" readonly="true"/>
                                    <span id="codekeyTip"></span>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>KEYCODE:</label>
                                    <s:textfield id="keycode" name="code.keycode" readonly="true"/>
                                    <span id="keycodeTip"></span>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>KEYNAME:</label>
                                    <s:textfield id="keyname" name="code.keyname"/>
                                    <span id="keynameTip"></span>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>KEYDESC:</label>
                                    <s:textfield id="keydesc" name="code.keydesc"/>
                                    <span id="keydescTip"></span>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>SORTNUM:</label>
                                    <s:textfield id="sortnum" name="code.sortnum"/>
                                    <span id="sortnumTip"></span>
                                </li>
                            </ul>
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
        window.location.href = "listCode.action";
    }

    $(document).ready(function() {
        $.formValidator.initConfig({formid:"updateCodeForm",onerror:function(msg) {
            alert(msg);
        }});

        $('#keyname').formValidator({
            onshow :"请输入编码KEYNAME",
            onfocus :"请输入编码KEYNAME，最多128个字符 ",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :0,
            max :128,
            onerror :"请输入编码KEYNAME，最多128个字符 "
        }).functionValidator({});
        $('#keydesc').formValidator({
            onshow :"请输入编码KEYDESC",
            onfocus :"请输入编码KEYDESC，最多128个字符 ",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :0,
            max :128,
            onerror :"请输入编码KEYDESC，最多128个字符 "
        }).functionValidator({});
        $('#sortnum').formValidator({
            onshow :"请输入编码SORTNUM",
            onfocus :"请输入编码SORTNUM，最大9999",
            oncorrect :"输入正确"
        }).functionValidator({
            fun : function(val, elem) {
                $(elem).val($.trim(val))
            }
        }).inputValidator({
            min :1,
            max :4,
            onerror :"请输入编码KEYDESC，最大9999"
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
    });
</script>
<%@include file="../include/bottom.jsp" %>