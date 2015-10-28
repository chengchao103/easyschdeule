<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<script type="text/javascript">
    function displayCron() {
        document.getElementById('trcronExpression').style.display = '';//'block';
        document.getElementById('trrepeatCount').style.display = 'none';
        document.getElementById('trrepeatInterval').style.display = 'none';
    }
    function displaySimple() {
        document.getElementById('trcronExpression').style.display = 'none';
        document.getElementById('trrepeatCount').style.display = '';//'block';
        document.getElementById('trrepeatInterval').style.display = '';//'block';
    }
 	function addPP() {
		var t = document.createElement("select")
		type = document.createElement("select"),
		value = document.createElement("input"),
		container = document.createElement("div");
		del = document.createElement("button");

		t.id = "parameterInputOutPutT";
		t.name="parameterInputOutPutT"
		t.options.add(new Option("输入","1"));
		t.options.add(new Option("输出","2"));


		type.id = "parameterInputOutPutType";
		type.name="parameterInputOutPutType"
		type.options.add(new Option("VARCHAR","1"));
		type.options.add(new Option("NUMBER","2"));
		type.options.add(new Option("DATE","3"));

		value.id = "parameterInputOutPutValue";
		value.name="parameterInputOutPutValue"
		value.type="text";

		del.innerHTML = '删除';
		container.appendChild(t);
		container.appendChild(type);
		container.appendChild(value);
		container.appendChild(del);
		document.getElementById("addP").appendChild(container);
    }
</script>
<s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_ADD">
    <h3>新增任务</h3>
</s:if>
<s:else>
	<h3>修改任务</h3>
</s:else>

<h4>任务属性</h4>
任务组：<br>
<select id="jobDetail.group" name="jobDetail.group" <s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">disabled</s:if>>
    <option value=""></option>
    <s:iterator value="groups">
        <option value="<s:property value="keycode"/>"
                <s:if test='keycode==jobDetail.group'>selected</s:if> ><s:property
                value="keyname"/></option>
    </s:iterator>
</select>
<s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">
    <input type="hidden" name="jobDetail.group" value="<s:property value="jobDetail.group" />"/>
</s:if>
<br>
任务名称：<br>
<input type="text" id="jobDetail.name" name="jobDetail.name" maxlength="40" value="<s:property value="jobDetail.name" />"
       <s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">readonly disabled</s:if>/>
<s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">
    <input type="hidden" name="jobDetail.name" value="<s:property value="jobDetail.name" />"/>
</s:if>
<br>

任务类：<br>
<input type="text" name="className" id="className" value="<s:property value="jobDetail.jobClass.name" />" disabled readonly/>
<input type="hidden" name="className" id="className" value="<s:property value="jobDetail.jobClass.name" />"/>
<br>
任务描述：<br>
<textarea name="jobDetail.description" id="jobDetail.description" rows="2" cols="46" style="height:60px;"><s:property value="jobDetail.description"/></textarea>
<br>
可重做标志：<br>
<input type="checkbox" id="requestsRecovery" name="jobDetail.requestsRecovery" value="true" <s:if test="jobDetail.requestsRecovery()"> checked="true" </s:if> />
<label for="requestsRecovery">任务非正常结束后重新执行</label>
<br>

<h4>任务参数</h4>
<s:iterator value="codes" status="status">
        <s:property value="keycode"/>：
            <input type="hidden" name="parameterNames" value="<s:property value="keycode"/>" readonly/><br>
        <s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_ADD">
        	<s:set name="tkey" value="keycode"/>
        	<s:set name="tval" value="keyname"/>
        </s:if>
        <s:else>
        	<s:set name="tkey" value="keycode"/>
        	<s:set name="tval" value="jobDetail.jobDataMap.get(keycode)"/>
        </s:else>
    	<s:if test="#tval == 'true' || #tval == 'false'">
			<select name="parameterValues">
		        <option value="true"  <s:if test='#tval=="true"'>selected</s:if> >是</option>
		        <option value="false" <s:if test='#tval=="false"'>selected</s:if> >否</option>
			</select>
		</s:if>
		<s:elseif test="#tkey == 'runType' && (#tval == 'concurrent' || #tval == 'hash')">
            <select name="parameterValues">
		        <option value="hash" <s:if test='#tval=="hash"'>selected</s:if> >随机选择一个节点</option>
		        <option value="concurrent" <s:if test='#tval=="concurrent"'>selected</s:if> >并发执行所有节点</option>
			</select>
        </s:elseif>
        <s:elseif test="#tkey == 'connectionType'">
            <select name="parameterValues" >
                <option value="0" <s:if test='#tval=="0"' >selected</s:if> >短连接</option>
		        <option value="1" <s:if test='#tval=="1"' >selected</s:if> >长连接</option>
			</select>
        </s:elseif>
        <s:elseif test="#tkey == 'completeTime' ">
            <select name="parameterValues">
		        <option value="10000"    <s:if test='#tval=="10000"'   >selected</s:if> >10秒</option>
		        <option value="30000"    <s:if test='#tval=="30000"'   >selected</s:if> >30秒</option>
		        <option value="60000"    <s:if test='#tval=="60000"'   >selected</s:if> >1分钟</option>
		        <option value="300000"   <s:if test='#tval=="300000"'  >selected</s:if> >5分钟</option>
		        <option value="600000"   <s:if test='#tval=="600000"'  >selected</s:if> >10分钟</option>
		        <option value="1200000"  <s:if test='#tval=="1200000"' >selected</s:if> >20分钟</option>
		        <option value="1800000"  <s:if test='#tval=="1800000"' >selected</s:if> >30分钟</option>
		        <option value="3600000"  <s:if test='#tval=="3600000"' >selected</s:if> >1小时</option>
		        <option value="7200000"  <s:if test='#tval=="7200000"' >selected</s:if> >2小时</option>
		        <option value="14400000" <s:if test='#tval=="14400000"'>selected</s:if> >4小时</option>
		        <option value="28800000" <s:if test='#tval=="28800000"'>selected</s:if> >8小时</option>
		        <option value="36000000" <s:if test='#tval=="36000000"'>selected</s:if> >10小时</option>
			</select>
        </s:elseif>
        <s:elseif test="#tkey == 'dataSourceType' ">
            <select name="parameterValues">
		        <option value="1"    <s:if test='#tval=="1"'   >selected</s:if> >ES-DS</option>
		        <option value="2"    <s:if test='#tval=="2"'   >selected</s:if> >JNDI</option>
			</select>
        </s:elseif>
        <s:elseif test="#tkey == 'connTimeout' ">
            <select name="parameterValues">
		        <option value="10000"  <s:if test='#tval=="10000"' >selected</s:if> >10秒</option>
		        <option value="30000"  <s:if test='#tval=="30000"' >selected</s:if> >30秒</option>
		        <option value="60000"  <s:if test='#tval=="60000"' >selected</s:if> >1分钟</option>
		        <option value="300000" <s:if test='#tval=="300000"'>selected</s:if> >5分钟</option>
			</select>
        </s:elseif>
        <s:elseif test="#tkey == 'retries' ">
            <select name="parameterValues">
		        <option value="0" <s:if test='#tval=="0"'>selected</s:if> >0</option>
		        <option value="1" <s:if test='#tval=="1"'>selected</s:if> >1</option>
		        <option value="2" <s:if test='#tval=="2"'>selected</s:if> >2</option>
		        <option value="3" <s:if test='#tval=="3"'>selected</s:if> >3</option>
		        <option value="4" <s:if test='#tval=="4"'>selected</s:if> >4</option>
		        <option value="5" <s:if test='#tval=="5"'>selected</s:if> >5</option>
		        <option value="6" <s:if test='#tval=="6"'>selected</s:if> >6</option>
		        <option value="7" <s:if test='#tval=="7"'>selected</s:if> >7</option>
		        <option value="8" <s:if test='#tval=="8"'>selected</s:if> >8</option>
		        <option value="9" <s:if test='#tval=="9"'>selected</s:if> >9</option>
		        <option value="10" <s:if test='#tval=="10"'>selected</s:if> >10</option>
			</select>
        </s:elseif>
        <s:elseif test="#tkey == 'clientRetries' ">
            <select name="parameterValues">
		        <option value="0" <s:if test='#tval=="0"'>selected</s:if> >0</option>
		        <option value="1" <s:if test='#tval=="1"'>selected</s:if> >1</option>
		        <option value="2" <s:if test='#tval=="2"'>selected</s:if> >2</option>
		        <option value="3" <s:if test='#tval=="3"'>selected</s:if> >3</option>
		        <option value="4" <s:if test='#tval=="4"'>selected</s:if> >4</option>
		        <option value="5" <s:if test='#tval=="5"'>selected</s:if> >5</option>
		        <option value="6" <s:if test='#tval=="6"'>selected</s:if> >6</option>
		        <option value="7" <s:if test='#tval=="7"'>selected</s:if> >7</option>
		        <option value="8" <s:if test='#tval=="8"'>selected</s:if> >8</option>
		        <option value="9" <s:if test='#tval=="9"'>selected</s:if> >9</option>
		        <option value="10" <s:if test='#tval=="10"'>selected</s:if> >10</option>
			</select>
        </s:elseif>
        <s:elseif test="#tkey == 'repeatAlarmNum' ">
            <select name="parameterValues">
		        <option value="0" <s:if test='#tval=="0"'>selected</s:if> >0</option>
		        <option value="1" <s:if test='#tval=="1"'>selected</s:if> >1</option>
		        <option value="2" <s:if test='#tval=="2"'>selected</s:if> >2</option>
		        <option value="3" <s:if test='#tval=="3"'>selected</s:if> >3</option>
		        <option value="4" <s:if test='#tval=="4"'>selected</s:if> >4</option>
		        <option value="5" <s:if test='#tval=="5"'>selected</s:if> >5</option>
		        <option value="6" <s:if test='#tval=="6"'>selected</s:if> >6</option>
		        <option value="7" <s:if test='#tval=="7"'>selected</s:if> >7</option>
		        <option value="8" <s:if test='#tval=="8"'>selected</s:if> >8</option>
		        <option value="9" <s:if test='#tval=="9"'>selected</s:if> >9</option>
		        <option value="10" <s:if test='#tval=="10"'>selected</s:if> >10</option>
		        <option value="20" <s:if test='#tval=="20"'>selected</s:if> >20</option>
		        <option value="30" <s:if test='#tval=="30"'>selected</s:if> >30</option>
			</select>
        </s:elseif>
        <s:else>
        	<input type="text" name="parameterValues" value="<s:property value="tval"/>"/>
        </s:else>
        <br>
</s:iterator>
		
<div id="addP">
<a name="addP" id="addInputOutput" title ="VARCHAR为字符串 &nbsp; NUMBER为数字&nbsp; DATE是时间，格式为yyyy-MM-dd "style="display:none;" onclick="addPP()" > 
               添加存储过程参数</a>
</div>

<input id="cloneTime" type="text" class="date"
                   onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
                   name="cloneTime" 
                   readonly/>

<script>
 var p='<s:property value="jobDetail.jobDataMap.get('parameter')"/>';
      String.prototype.replaceAll  =  function (s1,s2){    
    	  return   this .replace( new  RegExp(s1, "gm" ),s2);    
      }
     if(document.getElementById("className").value=="com.taobao.ad.easyschedule.job.StoredProcedureJob") {
                document.getElementById('addInputOutput').style.display = '';
                //将手动设置的存储过程参数恢复
                if(p!=null&&p!=""){
                	p=p.replaceAll("&quot;","\"");
                var pp=eval(p);
                for(var i=0;i<pp.length;i++){
                	var tt=pp[i].t;
                	var ttype=pp[i].type;
                	var tvalue=pp[i].value;
                	var t = document.createElement("select")
        			type = document.createElement("select"),
        			value = document.createElement("input"),
        			container = document.createElement("div");
        			del = document.createElement("button");

        		t.id = "parameterInputOutPutT";
        		t.name="parameterInputOutPutT"
        		t.options.add(new Option("输入","1")); //这个兼容IE与firefox
        		t.options.add(new Option("输出","2"));
        		if(tt=="1"){
        		   t.options[0].selected=true;
        		   }
        		else{
        			t.options[1].selected=true;	
        			tvalue='';
        		}
        		type.id = "parameterInputOutPutType";
        		type.name="parameterInputOutPutType"
        		type.options.add(new Option("VARCHAR","1")); //这个兼容IE与firefox
        		type.options.add(new Option("NUMBER","2"));
        		type.options.add(new Option("DATE","3"));
        		value.value=tvalue;
        		if(ttype=="1"){
        		   type.options[0].selected=true;
        		   }
        		else if(ttype=="2"){
        			type.options[1].selected=true;	
        		}else if(ttype=="3"){
        		    value=document.getElementById("cloneTime").cloneNode(false);
        			type.options[2].selected=true;	
        			value.value=tvalue;
        		}
        		value.id = "parameterInputOutPutValue";
        		value.name="parameterInputOutPutValue"
        		del.innerHTML = '删除';
        		container.appendChild(t);
        		container.appendChild(type);
        		container.appendChild(value);
        		container.appendChild(del);
        		document.getElementById("addP").appendChild(container);
                }
                }
     }


	function bindListener(){
		var _test = document.getElementById('addP');
		_test.onclick = function(e){
			handle_del(e);
		}
	}

	function handle_del(event){
		var e = event ? event : window.event;
		var srcTar = e.target || e.srcElement;
		if(srcTar.nodeName.toLowerCase() == 'button'){
			srcTar.parentNode.parentNode.removeChild(srcTar.parentNode);
		}
	}

	bindListener();
	  document.getElementById('cloneTime').style.display = 'none';

</script>