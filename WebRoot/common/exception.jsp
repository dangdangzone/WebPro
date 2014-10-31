<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>系统发生错误</title>
</head>
<body style="font-family:'微软雅黑';color:#333;margin:0;padding:0">
<div class="easyui-panel error" data-options="fit:true,title:'系统发生错误',border:false" style="font-size:16px;padding:12px 48px">
	<div style="font-size:100px;font-weight:normal;line-height:120px;margin-bottom:12px">:(</div>
	<h1 style="font-size:32px;line-height:48px">${exception.message}</h1>
	<div style="padding-top:10px"></div>
</div>
</body>
</html>