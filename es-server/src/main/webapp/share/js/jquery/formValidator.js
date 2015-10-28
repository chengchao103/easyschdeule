//check系统中所有ID
function checkID(id){
	if(id != ""){
		if(!isIntege(id) || id>100000000000){
			return false;
		}
	}
	return true;
}
//是否是整数
function isIntege(str){
	return new RegExp(regexEnum.intege).test(str);
}

//只能是数字、26个英文字母或者下划线
function isValidatText(str){
	return new RegExp(regexEnum.text).test(str);
}

//是否是中文
function isChinese(str){
	return new RegExp(regexEnum.chinese).test(str);
}

//是否是邮箱
function isEmail(str){
	return new RegExp(regexEnum.email).test(str);
}

//是否是邮编
function isZip(str){
	return new RegExp(regexEnum.zipcode).test(str);
}

//是否是手机
function isMobile(str){
	return new RegExp(regexEnum.mobile).test(str);
}

//是否是手机
function isPhone(str){
	return new RegExp(regexEnum.tel).test(str);
}

//是否是密码
function isPassword(str){
	return new RegExp(regexEnum.password).test(str);
}

//是否是旺旺
function isCustIM(str){
	return new RegExp(regexEnum.chineseorletter).test(str);
}

//是否是标题
function isSubject(str){
	return new RegExp(regexEnum.extraorletter).test(str);
}

//是否是空
function isNotempty(str){
	return new RegExp(regexEnum.notempty).test(str);
}
//淘宝的宝贝
function isItemid(str){
	return new RegExp(regexEnum.itemid).test(str);
}
//淘宝的宝贝URL
function isTaobaoItemUrl(str){
	return new RegExp(regexEnum.taobaoitemurl).test(str);
}
//店铺ID
function isShopid(str) {
	return new RegExp(regexEnum.shopid).test(str);
}
//店铺url
function isShopUrl(str) {
	return (new RegExp(regexEnum.shopurl1).test(str) || new RegExp(regexEnum.shopurl2).test(str) || new RegExp(regexEnum.shopurl3).test(str));
}
//淘宝的URL
function isTaobaoUrl(str){
	return new RegExp(regexEnum.taobaourl).test(str);
}
//alimamaURL
function isMmURL(str){
	return new RegExp(regexEnum.mmurl).test(str);
}

function isNotNull(str){
	if (trim(str).length == 0)
		return false;
	return true;
}
/**
 * 检验一个金额的字段是否合法：满足整数或者是一位或两位小数且非负
 * @param amount
 * @return
 */
function validateAmount(amount){
	var amountPattern = /^\d{1,18}(\.\d{1,2})?$/;//
	return amountPattern.test(amount);
}

//只含有汉字、数字、字母、下划线不能以下划线开头和结尾
function chineseorletter(str){
return new RegExp(regexEnum.chineseorletter).test(str);
}
function checkCharacter(str,min,max){

	var c = str.length;
	if(c>max || c<min){
		return false;
	}
	return true;
}

function checkLength(str,min,max){

	var c = getLength(str);
	if(c>max || c<min){
		return false;
	}
	return true;
}
function getLength(strTemp)
{
    var i,sum;
    sum = 0;
    for(i=0;i<strTemp.length;i++)
    {
        if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255))
            sum = sum + 1;
        else
            sum = sum + 2;
    }
    return sum;
}

function initDate(createStartTime, createEndTime){
	var startTime = new Date();
	var endTime = new Date();
	startTime.setMonth(startTime.getMonth() - 3);
	
	var sy = startTime.getFullYear();
	var sm = startTime.getMonth();
	if(sm < 9){ 
		sm = "0"+(sm + 1); 
	}else{ 
		sm = sm + 1; 
	} 
	var sd = startTime.getDate() + 1;
	if(sd < 10){ 
		sd = "0" + sd ; 
	}else{ 
		sd = sd; 
	}
	
	var ey = endTime.getFullYear();
	var em = endTime.getMonth();
	if(em < 9){ 
		em = "0"+(em + 1); 
	}else{ 
		em = em + 1; 
	} 
	var ed = endTime.getDate();
	if(ed < 10){ 
		ed = "0" + ed ; 
	}else{ 
		ed = ed; 
	}
	
	$('#'+createStartTime+'').val(sy + "-" + sm + "-" + sd);
	$('#'+createEndTime+'').val(ey + "-" + em + "-" + ed);
}

function initDateByDaysOne(createStartTime, days){
	var startTime = new Date();
	startTime.setDate(startTime.getDate() - days);

	var sy = startTime.getFullYear();
	var sm = startTime.getMonth();
	var sd = startTime.getDate() + 1;
	if(sd < 10){ 
		sd = "0" + sd ; 
	}else{
		if (sd == 32) {
			sd = "01";
			sm = sm + 1; 
		} else {
			sd = sd;
		}
	}
	if(sm < 9){ 
		sm = "0"+(sm + 1); 
	}else{ 
		sm = sm + 1; 
	}
		
	var ed = startTime.getDate();
	if(ed < 10){ 
		ed = "0" + ed ; 
	}else{ 
		ed = ed; 
	}

	if ((sd == "30" || sd == "31" || (sm == "02" && sd == "29")) && ed == "07") {
		sd = "01";
		sm = parseInt(sm) + 1;
		if(sm < 9){ 
			sm = "0" + sm; 
		}
	}
	$('#'+createStartTime+'').val(sy + "-" + sm + "-" + sd);
}


function initDateByDays(createStartTime, createEndTime, days){
	var startTime = new Date();
	var endTime = new Date();
	startTime.setDate(startTime.getDate() - days);

	var sy = startTime.getFullYear(); //年
	var sm = startTime.getMonth(); //月
	var sd = startTime.getDate(); //日
	if(sd < 10){ 
		sd = "0" + sd ; 
	}else{
		if (sd == 32) {
			sd = "01";
			sm = sm + 1; 
		} else {
			sd = sd;
		}
	}
	if(sm < 9){ 
		sm = "0"+(sm + 1); 
	}else{ 
		sm = sm + 1; 
	}
	
	var ey = endTime.getFullYear();
	var em = endTime.getMonth();
	if(em < 9){ 
		em = "0"+(em + 1); 
	}else{ 
		em = em + 1; 
	} 
	var ed = endTime.getDate();
	if(ed < 10){ 
		ed = "0" + ed ; 
	}else{ 
		ed = ed; 
	}

	if ((sd == "30" || sd == "31" || (sm == "02" && sd == "29")) && ed == "07") {
		sd = "01";
		sm = parseInt(sm) + 1;
		if(sm < 9){ 
			sm = "0" + sm; 
		}
	}
	
	$('#'+createStartTime+'').val(sy + "-" + sm + "-" + sd);
	$('#'+createEndTime+'').val(ey + "-" + em + "-" + ed);
}

/**
* 判断查询条件中开始日期与结束日期相差是否小于等于90天
*
*/
function checkSearchDateCommon(createStartTime, createEndTime, dayCount, message) {

	var startTime;
	var endTime;

	if (createStartTime.length > 0 && createStartTime.split("-").length == 3) {
		createStartTime = createStartTime.split("-");
		startTime = new Date(createStartTime[0], createStartTime[1], createStartTime[2]);
	} else {
		return "起始时间不能为空！";
	}
	if (createEndTime.length > 0 && createEndTime.split("-").length == 3) {
		createEndTime = createEndTime.split("-");
		endTime = new Date(createEndTime[0], createEndTime[1], createEndTime[2]);
	} else {
		return "结束时间不能为空！";
	}

	if (startTime - endTime > 0) {
		return "起始时间不能晚于结束时间！";
	}

	endTime.setDate(endTime.getDate() - dayCount);
	if ((endTime.getMonth() == 4 ||
		endTime.getMonth() == 6 ||
		endTime.getMonth() == 9 ||
		endTime.getMonth() == 11) && endTime.getDate() == 31) {
		endTime.setDate(30);
	}
	if (endTime.getMonth() == 2 && endTime.getDate() == 31) {
		if (endTime.getYear() % 4 == 0) {
			endTime.setDate(29);
		} else {
			endTime.setDate(28);
		}
	}

	if (startTime - endTime < 0) {
		return message;
	}

	return "";
}
function checkSearchDate(createStartTime, createEndTime) {
	return checkSearchDateCommon(createStartTime, createEndTime, 93, "起止时间范围不能超过3个月！");
}
function checkSearchDateWeek(createStartTime, createEndTime) {
	return checkSearchDateCommon(createStartTime, createEndTime, 7, "不录入“旺旺名称”或“客户ID”时，起止时间范围不能超过7天！");
}
function checkSearchDateWeekMsg(createStartTime, createEndTime, msg) {
	return checkSearchDateCommon(createStartTime, createEndTime, 6, msg);
}
function checkSearchDateAlert(createStartTime, createEndTime) {
	var message = checkSearchDate(createStartTime,createEndTime);
	if (message.length > 0) {
		alert(message);
		return false;
	}
	return true;
}
function checkcharacter(str)
{
    var reg = /^[`~!@#$%^&*()=\'%<>&,.?;:|\"～！◎＃￥％……※×（）——＋§]*$/;
    return (reg.test(str));
}

function trim(string)
{
	if (string != null && string != undefined && string.length > 0)
	{
		return string.replace(/(^\s*)|(\s*$)/g, "");
	}
	return "";
}

function trimString(str)
{
  var i,j;

  if(str == "") return "";

  for(i=0;i<str.length;i++)
    if(str.charAt(i) != ' ') break;
  if(i >= str.length) return "";

  for(j=str.length-1;j>=0;j--)
    if(str.charAt(j) != ' ') break;

  return str.substring(i,j+1);
}

function CheckValidChar( sm_name, max_sm_name_len )
{
	var i;
	sm_name = trimString(sm_name);
	if(sm_name == "")return 1;
	if(sm_name.length > max_sm_name_len) return 2;
	
	if(sm_name.indexOf("'") != -1) return 3;
	
	for(i=0; i<sm_name.length;i++) {
		if(navigator.appName=="Netscape") {
		  if(sm_name.charCodeAt(i)<0) return 0;
		}else if(sm_name.charAt(i) > String.fromCharCode(0x7f)) return 0;
	}
	
	return 0;
}

function getDate(strDT){
    var tmpDT = strDT.split("-");
    if(tmpDT.length != 3){
	  alert("不是有效的日期格式");
	  return;
	}
	var dt = new Date(tmpDT[0],tmpDT[1]-1,tmpDT[2]);
	if(dt == 'NaN'){
	  alert("不是有效的日期格式");
	}
	
	return dt;
  }
  function getDateAfterDay(dt,dayNum){
    var times = dt.getTime();
	var newDate = new Date();
	times = times+(dayNum*24*60*60*1000);
	newDate.setTime(times);
	return newDate;
  }
  
  function checkHtml(targetStr)
{
  var valid ="<>&'\\\"#";
  var flag=false;
  for (i = 0;i<=(targetStr.length-1);i++)
  {
    if (valid.indexOf(targetStr.charAt(i))>=0)
    {
      flag=true;
      break;
    }
  }
  return flag;
}

  function checkForFSH(targetStr)
{
  var valid ="~!@#$%^&*:\"[]{}+-<>?|\'_,，;./～！◎＃￥％……※×——＋§";
  var flag=false;
  for (i = 0;i<=(targetStr.length-1);i++)
  {
    if (valid.indexOf(targetStr.charAt(i))>=0)
    {
      flag=true;
      break;
    }
  }
  return flag;
}
  //验证查询中所有日期条件（3个月内）
  /*
  function checkQueryDate(strStartDate,strEndDate){
    var dtStartDate = getDate(strStartDate);
	var dtEndDate = getDate(strEndDate);
	var dt = getDateAfterDay(dtEndDate,-93);
	if(dtStartDate.getTime() < dt.getTime()){
	  alert("查询日期必须在三个月内");
	  return false;
	}
    return true;
  }
  */