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
<table class="message-add">
    <thead>
    <tr>
        <td colspan="3"><h1>任务属性</h1></td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td width="130px">任务组：</td>
        <td width="340px">
            <select id="jobDetail.group" name="jobDetail.group" style="width:304px;" <s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">disabled</s:if>>
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
        </td>
        <td>
            创建后不能修改
        </td>
    </tr>
    <tr>
        <td>任务名称：</td>
        <td><input type="text" id="jobDetail.name" name="jobDetail.name" style="width:300px;" maxlength="40" size="50" value="<s:property value="jobDetail.name" />"
                   <s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">readonly disabled</s:if>/>
            <s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_MOD">
                <input type="hidden" name="jobDetail.name" value="<s:property value="jobDetail.name" />"/>
            </s:if>
        </td>
        <td>
            创建后不能修改，只能使用<font color=red>英文字符</font>
        </td>
    </tr>
    <tr>
        <td>任务类：</td>
        <td>
        	<input type="text" name="className" id="className" style="width:300px;" size="60" value="<s:property value="jobDetail.jobClass.name" />" disabled readonly/>
        	<input type="hidden" name="className" id="className" value="<s:property value="jobDetail.jobClass.name" />"/>
        </td>
        <td>
            默认配置
        </td>
    </tr>
    <tr>
        <td>任务描述：</td>
        <td><textarea name="jobDetail.description" id="jobDetail.description" rows="2" cols="46" style="height:60px;width:302px;" <s:property value="disabled"/>><s:property
                value="jobDetail.description"/></textarea>
        </td>
        <td>
            任务功能简单描述
        </td>
    </tr>
    <tr>
        <td>可重做标志：</td>
        <td><input style="position:relative;top:5px;" type="checkbox" id="requestsRecovery"
                   name="jobDetail.requestsRecovery" value="true" <s:if
                test="jobDetail.requestsRecovery()"> checked="true" </s:if> <s:property value="disabled"/>/>
                <label for="requestsRecovery">任务崩溃或者意外停止后重新执行</label>
        </td>
        <td>
        </td>
    </tr>
    <!--<tr class="row1add" onMouseOver="this.className='m1add'" onMouseOut="this.className='row1add'">
        <td>可持久</td>
        <td><input type="checkbox" name="jobDetail.durability" value="true"  <s:if test="jobDetail.isDurable()"> checked="true" </s:if> />
        </td>
        <td>
        </td>
    </tr>-->
    </tbody>
</table>

<table class="message-add">
    <thead><tr><td colspan="3"><h1>任务参数</h1></td></tr></thead>
    <tbody>
		<s:iterator value="codes" status="status">
		    <tr height="30px">
		        <td width="130px">
		               <s:property value="keycode"/>：
		            <input type="hidden" name="parameterNames" value="<s:property value="keycode"/>" readonly/>
		        </td>
		        <td width="340px">
		        
		        <s:if test="action == @com.taobao.ad.easyschedule.commons.Constants@JOB_ACTION_ADD">
		        	<s:set name="tkey" value="keycode"/>
		        	<s:set name="tval" value="keyname"/>
		        </s:if>
		        <s:else>
		        	<s:set name="tkey" value="keycode"/>
		        	<s:set name="tval" value="jobDetail.jobDataMap.get(keycode)"/>
		        </s:else>
		
				<s:if test="#tkey == 'synchronous' && (#tval == 'true' || #tval == 'false')">
					<select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
				        <option value="true"  <s:if test='#tval=="true"'>selected</s:if> >同步任务</option>
				        <option value="false" <s:if test='#tval=="false"'>selected</s:if> >异步任务</option>
					</select>
				</s:if>
				<s:elseif test="#tkey == 'runType' && (#tval == 'hash' || #tval == 'concurrent')">
		            <select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
				        <option value="hash" <s:if test='#tval=="hash"'>selected</s:if> >随机选择一个节点</option>
				        <option value="concurrent" <s:if test='#tval=="concurrent"'>selected</s:if> >并发执行所有节点</option>
					</select>
		        </s:elseif>
		        <s:elseif test="#tkey == 'connectionType'">
		            <select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
		                <option value="0" <s:if test='#tval=="0"' >selected</s:if> >短连接</option>
				        <option value="1" <s:if test='#tval=="1"' >selected</s:if> >长连接</option>
					</select>
		        </s:elseif>
		        <s:elseif test="#tkey == 'completeTime' ">
		            <select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
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
		            <select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
				        <option value="1"    <s:if test='#tval=="1"'   >selected</s:if> >ES-DS</option>
				        <option value="2"    <s:if test='#tval=="2"'   >selected</s:if> >JNDI</option>
					</select>
		        </s:elseif>
		        <s:elseif test="#tkey == 'connTimeout' ">
		            <select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
				        <option value="10000"  <s:if test='#tval=="10000"' >selected</s:if> >10秒</option>
				        <option value="30000"  <s:if test='#tval=="30000"' >selected</s:if> >30秒</option>
				        <option value="60000"  <s:if test='#tval=="60000"' >selected</s:if> >1分钟</option>
				        <option value="300000" <s:if test='#tval=="300000"'>selected</s:if> >5分钟</option>
					</select>
		        </s:elseif>
		        <s:elseif test="#tkey == 'retries' ">
		            <select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
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
		            <select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
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
		            <select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
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
		    	<s:elseif test="#tval == 'true' || #tval == 'false'">
					<select name="parameterValues" style="width:304px;" <s:property value="disabled"/>>
				        <option value="true"  <s:if test='#tval=="true"'>selected</s:if> >是</option>
				        <option value="false" <s:if test='#tval=="false"'>selected</s:if> >否</option>
					</select>
				</s:elseif>
		        <s:else>
		        	<input type="text" name="parameterValues" style="width:300px;" size="50" value="<s:property value="tval"/>" <s:property value="disabled"/>/>
		        </s:else>
		        </td>
		        <td>
		            <s:property value="keydesc"/>
		        </td>
		    </tr>
		</s:iterator>
    </tbody>
</table>
<div id="addP">
<a name="addP" id="addInputOutput"    title ="VARCHAR为字符串 &nbsp; NUMBER为数字&nbsp; DATE是时间，格式为yyyy-MM-dd "style="display:none;" onclick="addPP()" > 
               添加存储过程参数</a>
</div>

<input id="cloneTime" type="text" style="width:300px;" class="date"
                   onclick="wdatepicker({dateFmt:'yyyy-MM-dd'})"
                   name="cloneTime" size="50"
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