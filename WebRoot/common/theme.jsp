<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/pub/css/icons.css" />
<link rel="stylesheet" type="text/css" href="/pub/js/easyui/themes/default/easyui.css" title="default" />
<link rel="stylesheet" type="text/css" href="/pub/js/easyui/themes/gray/easyui.css" title="gray" />
<link rel="stylesheet" type="text/css" href="/pub/js/easyui/themes/bootstrap/easyui.css" title="bootstrap" />
<link rel="stylesheet" type="text/css" href="/pub/js/easyui/themes/metro/easyui.css" title="metro" />
<link rel="stylesheet" type="text/css" href="/pub/css/admin/default.css" title="default" />
<link rel="stylesheet" type="text/css" href="/pub/css/admin/gray.css" title="gray" />
<link rel="stylesheet" type="text/css" href="/pub/css/admin/bootstrap.css" title="bootstrap" />
<link rel="stylesheet" type="text/css" href="/pub/css/admin/metro.css" title="metro" />
<script type="text/javascript">
var theme = $.cookie('theme') || 'bootstrap';	//全局变量
$(document).ready(function(){
	$('link[rel*=style][title]').each(function(i){
		this.disabled = true;
		if (this.getAttribute('title') == theme) this.disabled = false;
	});
});
</script>