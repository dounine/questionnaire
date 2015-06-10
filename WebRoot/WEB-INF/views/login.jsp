<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/checkLogin.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/login.css" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录首页</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	overflow:hidden;
}
.STYLE3 {color: #528311; font-size: 12px; float: left}
.STYLE4 {
	color: #42870a;
	font-size: 12px;
}	
-->
*{
margin:0px;
padding:0px;
}
li{
list-style:none;
}
body{
	background:#F8F8F8;
}
#bgbg{
	margin:2% auto;
	width:1214px;
	height:725px;
	position:relative;
	background:url(${pageContext.request.contextPath}/style/images/bg1.jpg) no-repeat;
}
#bgbg ul{
	width:250px;
	height:100px;
	position: absolute;
	top:321px;
	right:140px;
}
#bgbg ul li{
	position: absolute;
}
.inputStyle{
	height:24px;
	margin-left:1px;
	width:126px;
}
#usernameId{
	top:2px;
}
#passwordId{
	top:30px;
}
#passwordId input{
	margin-top:5px;
}
#errorId{
	width:200px;
	left:100px;
	top:64px;
}
#submitId {
	top:65px;
	width:88px;
	height:20px;
	cursor:pointer;
}
</style></head>

<script language="javascript">
	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {//如果登录,直接跳转到index页面
		window.location.href='${ctx}/admin/index';
	}
	
	//进入页面光标定位在用户名输入框处
	$(document).ready(function(){
    	$("#userName").focus();
	});
	
	//按enter键直接登录
	document.onkeydown = function(e){
		var ev = window.event||e;
		if(ev.keyCode==13){
			validateLogin();
		}
	};
	$(function(){
			var navigatorName = "Microsoft Internet Explorer"; 
		   var isIE = false; 
		   if( navigator.appName == navigatorName ){ 
		    isIE = true; 
		     	$("#usernameId").css({
		     		"top":(parseInt($("#usernameId").css('top'))+4)+"px"
		     	});
		     	$("#passwordId").css({
		     		"top":(parseInt($("#passwordId").css('top'))+4)+"px"
		     	});
		     	$("#submitId").css({
		     		"top":(parseInt($("#submitId").css('top'))+4)+"px"
		     	});
		   }
	});
</script>

<body>
	<div id="bgbg">
				<ul>
                <li id="usernameId"><input class="inputStyle" type="text" id="loginname" name="loginname" value="" ></li>
                <li id="passwordId"><input class="inputStyle" type="password" id="password" name="password" value="" ></li>
                <li id="errorId"><div id="error" style="color:red;font-size: 12px;font-weight:bold;"></div></li>
                <li id="submitId" onclick="validateLogin()"></li>
                </ul>
</div>
</body>
</html>