<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>提示信息</title>
<include file="Common:head"/>
<style type="text/css">
a{color:#08c;text-decoration:none}
a:hover,a:focus{color:#005580;text-decoration:underline}
</style>
</head>
<body>
<div class="easyui-window" title="提示信息" style="width:380px;height:200px" data-options="iconCls:'',modal:false,resizable:false,collapsible:false,minimizable:false,maximizable:false,closable:false">
<div style="font-size:18px;text-align:center;margin-top:40px">
	${exception.message}
	<p style="font-size:12px">
		页面自动 <a id="href" href="/login.jsp">跳转</a> 等待时间： <b id="wait">3</b>
	</p>
</div>
</div>
<script type="text/javascript">
(function(){
var wait = document.getElementById('wait'),href = document.getElementById('href').href;
var interval = setInterval(function(){
	var time = --wait.innerHTML;
	if(time <= 0) {
		location.href = href;
		clearInterval(interval);
	};
}, 1000);
})();
</script>
</body>
</html>