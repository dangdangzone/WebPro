<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改密码</title>
		<%@ include file="/common/head.jsp" %>
		<%@ include file="/common/formvalidator.jsp" %>
	</head>
	<body>
		<div class="easyui-panel" data-options="fit:true,title:'',border:false">
			<script type="text/javascript">
				$(function(){
					$('#admin_public_editpwd_form_submit').click(function(){$('#admin_public_editpwd_form').submit()});
					$.formValidator.initConfig({formID:"admin_public_editpwd_form",onSuccess:adminPublicEditpwdFormSubmit,submitAfterAjaxPrompt:'有数据正在异步验证，请稍等...',inIframe:true});
					$("#admin_public_editpwd_form_old_password").formValidator({onShow:"不修改密码请留空",onFocus:"密码应该为6-20位之间"}).inputValidator({min:6,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"密码两边不能有空格"},onError:"密码应该为6-20位之间"});
					$("#admin_public_editpwd_form_new_password").formValidator({onShow:"不修改密码请留空",onFocus:"密码应该为6-20位之间"}).inputValidator({min:6,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"密码两边不能有空格"},onError:"密码不能为空,请确认"});
					$("#admin_public_editpwd_form_new_pwdconfirm").formValidator({onShow:"不修改密码请留空",onFocus:"请输入确认密码"}).compareValidator({desID:"admin_public_editpwd_form_new_password",operateor:"=",onError:"输入两次密码不一致"});
				})
				function adminPublicEditpwdFormSubmit(){
					$.post('/login.do?method=updateUserInfo', $("#admin_public_editpwd_form").serialize(), function(res){
						var obj = eval("("+res+")");
						if(!obj.status){
							$.messager.alert('提示信息', obj.info, 'error');
						}else{
							$.messager.alert('提示信息', obj.info, "info", function(){
								top.window.location.reload();
							});
						}
					})
				}
			</script>
			<form id="admin_public_editpwd_form">
				<table cellspacing="10">
					<tr>
						<td width="90">用户名：</td>
						<td colspan="2">${sessionScope.UserInfo.userName} (真实姓名： ${sessionScope.UserInfo.realName})</td>
					</tr>
					<tr>
						<td>新密码：</td>
						<td><input id="admin_public_editpwd_form_new_password" name="userPass" type="password" style="width:180px;height:22px" /></td>
						<td><div id="admin_public_editpwd_form_new_passwordTip"></td>
					</tr>
					<tr>
						<td>重复新密码：</td>
						<td><input id="admin_public_editpwd_form_new_pwdconfirm" type="password" style="width:180px;height:22px" /></td>
						<td><div id="admin_public_editpwd_form_new_pwdconfirmTip"></td>
					</tr>
					<tr>
						<td colspan="3"><a id="admin_public_editpwd_form_submit" class="easyui-linkbutton">提交</a></td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>