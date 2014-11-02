<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>title</title>
	</head>
	<body>
		<script type="text/javascript" src="/pub/js/CuPlayer/swfobject.js"></script>
		<div class="video" id="CuPlayer"><strong>提示：您的Flash Player版本过低，请升级您的Flash插件</a>！</strong></div>
		<script type="text/javascript">
			var so = new SWFObject("/pub/js/CuPlayer/CuPlayerMiniV4.swf","CuPlayerV4","1000","500","9","#000000");
			so.addParam("allowfullscreen","true");
			so.addParam("allowscriptaccess","always");
			so.addParam("wmode","opaque");
			so.addParam("quality","high");
			so.addParam("salign","lt");
			so.addVariable("CuPlayerSetFile","/pub/js/CuPlayer/CuPlayerSetFile.jsp?pageUrl=/movie/${param.player}");
			so.addVariable("CuPlayerFile","/movie/${param.player}"); //视频文件地址
			so.addVariable("CuPlayerImage","/pub/js/CuPlayer/images/start.jpg");//视频略缩图,本图片文件必须正确
			so.addVariable("CuPlayerWidth","1000"); //视频宽度
			so.addVariable("CuPlayerHeight","500"); //视频高度
			so.addVariable("CuPlayerAutoPlay","yes"); //是否自动播放
			so.write("CuPlayer");
		</script>
	</body>
</html>