<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>用户管理</title>
		<%@ include file="/common/head.jsp" %>
		<%@ include file="/common/formvalidator.jsp" %>
		<style type="text/css">
			#fm{
				margin:0;
				padding:10px 30px;
			}
			.ftitle{
				font-size:14px;
				font-weight:bold;
				color:#666;
				padding:5px 0;
				margin-bottom:10px;
				border-bottom:1px solid #ccc;
			}
			.fitem{
				margin-bottom:5px;
			}
			.fitem label{
				display:inline-block;
				width:80px;
			}
		</style>
		<script type="text/javascript">
			var url;
			function newUser(){
				$('#dlg').dialog('open').dialog('setTitle','New User');
				$('#fm').form('clear');
				url = 'save_user.php';
			}
			function editUser(){
				var row = $('#dg').datagrid('getSelected');
				if (row){
					$('#dlg').dialog('open').dialog('setTitle','Edit User');
					$('#fm').form('load',row);
					url = 'update_user.php?id='+row.id;
				}
			}
			function saveUser(){
				$('#fm').form('submit',{
					url: url,
					onSubmit: function(){
						return $(this).form('validate');
					},
					success: function(result){
						var result = eval('('+result+')');
						if (result.success){
							$('#dlg').dialog('close');		
							$('#dg').datagrid('reload');	
						} else {
							$.messager.show({
								title: 'Error',
								msg: result.msg
							});
						}
					}
				});
			}
			function removeUser(){
				var row = $('#dg').datagrid('getSelected');
				if (row){
					$.messager.confirm('提示','您确定要移除当前选中用户吗?',function(r){
						if (r){
							$.post('remove_user.php',{id:row.id},function(result){
								if (result.success){
									$('#dg').datagrid('reload');	
								} else {
									$.messager.show({	
										title: '错误',
										msg: result.msg
									});
								}
							},'json');
						}
					});
				}
			}
		</script>
	</head>
	<body>
		<table id="dg" title="My Users" class="easyui-datagrid" style="width:700px;height:250px" url="get_users.php" toolbar="#toolbar" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="firstname" width="50">First Name</th>
					<th field="lastname" width="50">Last Name</th>
					<th field="phone" width="50">Phone</th>
					<th field="email" width="50">Email</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增用户</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改用户</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除用户</a>
		</div>
		<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
				closed="true" buttons="#dlg-buttons">
			<div class="ftitle">User Information</div>
			<form id="fm" method="post" novalidate>
				<div class="fitem">
					<label>First Name:</label>
					<input name="firstname" class="easyui-validatebox" required="true">
				</div>
				<div class="fitem">
					<label>Last Name:</label>
					<input name="lastname" class="easyui-validatebox" required="true">
				</div>
				<div class="fitem">
					<label>Phone:</label>
					<input name="phone">
				</div>
				<div class="fitem">
					<label>Email:</label>
					<input name="email" class="easyui-validatebox" validType="email">
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
	</body>
</html>