<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<?php
//针对后台ajax请求特殊处理
if(IS_AJAX) send_http_status(200);
if (IS_AJAX && IS_POST){
	header('Content-Type:application/json; charset=utf-8');
	$data = array('info'=>'系统发生错误', 'status'=>0, 'total'=>0, 'rows'=>array(), 'err'=>'系统发生错误', 'msg'=>'');
	exit(json_encode($data));
}
?>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>系统发生错误</title>
</head>
<body style="font-family:'微软雅黑';color:#333;margin:0;padding:0">
<div class="easyui-panel error" data-options="fit:true,title:'<?php if(isset($_GET['menuid'])) echo '系统发生错误';?>',border:false" style="font-size:16px;padding:12px 48px">
	<div style="font-size:100px;font-weight:normal;line-height:120px;margin-bottom:12px">:(</div>
	<h1 style="font-size:32px;line-height:48px"><?php echo strip_tags($e['message']);?></h1>
	<div style="padding-top:10px">
	<?php if(isset($e['file'])) {?>
		<div style="margin-bottom:12px">
			<div style="margin-bottom:3px">
				<h3 style="color:#000;font-weight:700;font-size:16px">错误位置</h3>
			</div>
			<div style="line-height:24px">
				<p>FILE: <?php echo $e['file'] ;?> &#12288;LINE: <?php echo $e['line'];?></p>
			</div>
		</div>
	<?php }?>
	<?php if(isset($e['trace'])) {?>
		<div style="margin-bottom:12px">
			<div style="margin-bottom:3px">
				<h3 style="color:#000;font-weight:700;font-size:16px">TRACE</h3>
			</div>
			<div style="line-height:24px">
				<p><?php echo nl2br($e['trace']);?></p>
			</div>
		</div>
	<?php }?>
	</div>
</div>
</body>
</html>