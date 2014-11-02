<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
		<title>瑞斯课堂</title>
		<%@ include file="/common/head.jsp" %>
		<%@ include file="/common/formvalidator.jsp" %>
	</head>
	<body class="easyui-layout">
		<c:if test="${empty sessionScope.UserInfo}">
			<c:redirect url="/login.jsp" />
		</c:if>
		<!-- 头部 -->
		<div id="toparea" data-options="region:'north',border:false,height:40">
			<div id="topmenu" class="easyui-panel" data-options="fit:true,border:false">
				<a class="logo">瑞斯课堂</a>
				<ul class="nav">
					<c:forEach items="${menuList}" var="node">
						<li>
							<c:choose>
								<c:when test="${node.menuId == 1}">
									<a class="focus" href="javascript:void(0);" onclick="getLeft(${node.menuId},'${node.menuName}', this)">${node.menuName}</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" onclick="getLeft(${node.menuId},'${node.menuName}', this)">${node.menuName}</a>
								</c:otherwise>
							</c:choose>
						</li>
					</c:forEach>
				</ul>
				<ul class="nav-right">
					<li>
						<span>您好！ ${sessionScope.UserInfo.realName}  |
							<a href="javascript:logout();">[退出]</a>
						</span> |
						<select id="themeswitchcombobox" class="easyui-combobox" data-options="editable:false,panelHeight:'auto',onChange:onChangeTheme,formatter:themeInit">
							<option value='default' selected="selected">
								Default
							</option>
							<option value='gray'>
								Gray
							</option>
							<option value='bootstrap'>
								Bootstrap
							</option>
							<option value='metro'>
								Metro
							</option>
						</select>
					</li>
				</ul>
			</div>
		</div>

		<!-- 左侧菜单 -->
		<div id="leftarea" data-options="iconCls:'icons-other-house',region:'west',title:'加载中...',split:true,width:190">
			<div id="leftmenu" class="easyui-accordion" data-options="fit:true,border:false"></div>
		</div>

		<!-- 内容 -->
		<div id="mainarea" data-options="region:'center'">
			<div id="pagetabs" class="easyui-tabs" data-options="tabPosition:'bottom',fit:true,border:false,plain:false">
				<div title="后台首页" href="/main.jsp" data-options="cache:false"></div>
			</div>
		</div>

		<!-- 右键菜单 -->
		<div id="rightmenu" class="easyui-menu"
			data-options="onClick:rightMenuHandler">
			<div
				data-options="name:'home',iconCls:'icons-application-application_home'">
				访问前台
			</div>
			<div class="menu-sep"></div>
			<div data-options="name:'exit'">
				退出登录
			</div>
		</div>

		<script type="text/javascript">
$(function(){
	getLeft(1, '课程信息');
	$(document).bind('contextmenu',function(e){
		e.preventDefault();
		$('#rightmenu').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
	});
	$.messager.show({			
		title:'登录提示',
		msg:'您好！${sessionScope.UserInfo.realName} 欢迎回来！<br/>最后登录时间：${sessionScope.UserInfo.lastDate}<br/>最后登录IP：${sessionScope.UserInfo.userIp}',
		timeout:5000,
		showType:'slide'
	});
});


function rightMenuHandler(item){

	if(!item.name) return;
	switch(item.name){
		case 'home':
			window.open('index.jsp');
			break;
		case 'refresh': //刷新后台
			window.location.href = window.location.href;
			break;
		case 'exit': //退出登录
			logout();
			break;
	}

}

function themeInit(row){
	if(row.value == theme) row.selected = true;
	var opts = $('#themeswitchcombobox').combobox('options');
	return row[opts.textField];
}


function onChangeTheme(theme){
	$('link[rel*=style][title]').each(function(i){
		this.disabled = true;
		if (this.getAttribute('title') == theme) this.disabled = false;
	});
	$('iframe').contents().find('link[rel*=style][title]').each(function(i){
		this.disabled = true;
		if (this.getAttribute('title') == theme) this.disabled = false;
	});
	$.cookie('theme', theme, {path:'/', expires:3650});
}

function logout(){
	$.messager.confirm('提示信息', '确定要退出登录吗？', function(result){
		if(result) window.location.href = '/login.do?method=logout';
	});
}

function getLeft(menuid, title, that){
	var options = $('body').layout('panel', 'west').panel('options');
	if(title == options.title) return false;
	$.ajax({
		type: 'POST',
		url: '/login.do?method=menuLeft',
		data: {menuId: menuid},
		cache: false,
		beforeSend: function(){
			removeLeft();
			$('body').layout('panel', 'west').panel({title: title});
			var loading = '<div class="panel-loading">Loading...</div>';
			$("#leftmenu").accordion("add", {content: loading});
		},
		dataType: "json",
		success: function(data){
			removeLeft();
			$.each(data, function(i, menu) {
				var content = '';
				if(menu.son){
					var treedata = $.toJSON(menu.son);
					content = '<ul class="easyui-tree" data-options=\'data:' + treedata + ',animate:true,lines:true,onClick:function(node){openUrl(node.url, node.text)}\'></ul>';
				}
				$("#leftmenu").accordion("add", {title: menu.name, content: content, iconCls:'icons-folder-folder_go'});
			});
		}
	});
	if(that){
		$('#topmenu .nav li').each(function(){
			$(this).children().removeClass('focus');
		})
		$(that).addClass('focus');
	}
}

function removeLeft(stop){
	var pp = $("#leftmenu").accordion("panels");
	$.each(pp, function(i, n) {
		if(n){
			var t = n.panel("options").title;
			$("#leftmenu").accordion("remove", t);
		}
    });
	var pp = $('#leftmenu').accordion('getSelected');
    if(pp) {
        var t = pp.panel('options').title;
        $('#leftmenu').accordion('remove', t);
    }
    if(!stop) removeLeft(true)
}

function openUrl(url, title){
	if($('#pagetabs').tabs('exists', title)){
		$('#pagetabs').tabs('select', title);
	}else{
		$('#pagetabs').tabs('add',{
			title: title,
			//href: url,
			closable: true,
			cache: false,
			content : '<iframe name="'+title+' "src="'+url+'" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>'
		});
	}
}

</script>

	</body>
</html>
