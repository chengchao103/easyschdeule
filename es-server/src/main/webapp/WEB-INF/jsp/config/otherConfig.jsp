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
                            <li class="cur"><a href="#"><span>系统参数</span></a></li>
                        </ul>
                    </div>
                    <div class="clearing"></div>
                    <s:form action="updateOtherConfig.action" method="post" id="updateOtherConfigForm" theme="simple">
                        <div class="add-wrapper">
                            <div style="color:red;"><s:property value="error"/></div>
                            <ul class="form-box">
                                <li class="clear" style="width: 700px;">
                                    <label>全局邮件发送开关:</label>
                                    <select id="sendMail" name="sendMail" style="width:304px;">
                                        <option value="true"  <s:if test='"true" ==sendMail'>selected</s:if>>发送</option>
                                        <option value="false" <s:if test='"false"==sendMail'>selected</s:if>>不发送</option>
                                    </select>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>邮件发送指令:</label>
                                    <input type="text" name="mailSendCommand" style="width:300px;" value="<s:property value="mailSendCommand"/>"/>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>全局短信发送开关:</label>
                                    <select id="sendSms" name="sendSms" style="width:304px;">
                                        <option value="true"  <s:if test='"true" ==sendSms'>selected</s:if>>发送</option>
                                        <option value="false" <s:if test='"false"==sendSms'>selected</s:if>>不发送</option>
                                    </select>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>短信发送指令:</label>
                                    <input type="text" name="smsSendCommand" style="width:300px;" value="<s:property value="smsSendCommand"/>"/>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>全局旺旺发送开关:</label>
                                    <select id="sendWangWang" name="sendWangWang" style="width:304px;">
                                        <option value="true"  <s:if test='"true" ==sendWangWang'>selected</s:if>>发送</option>
                                        <option value="false" <s:if test='"false"==sendWangWang'>selected</s:if>>不发送</option>
                                    </select>
                                </li>
                                <li class="clear" style="width: 700px;">
                                    <label>旺旺发送指令:</label>
                                    <input type="text" name="wangwangSendCommand" style="width:300px;" value="<s:property value="wangwangSendCommand"/>"/>
                                </li>
                            </ul>
                            <div style="text-align: center; color: red;" id="fieldError"></div>
                            <div class="submit-box">
                                <input id="btnsave" type="submit" value="保存" class="btn1"/>
                                <input id="backs" type="button" value="返回" class="btn1" onclick='goback();'/>
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
</script>
<%@include file="../include/bottom.jsp" %>