<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改个人信息</title>
		<%@ include file="/common/head.jsp" %>
		<%@ include file="/common/formvalidator.jsp" %>
	</head>
	<body>
		<div class="easyui-panel" data-options="fit:true,title:'',border:false">
			<script type="text/javascript">
				$(function(){
					$('#admin_public_editinfo_form_submit').click(function(){$('#admin_public_editinfo_form').submit()});
					$.formValidator.initConfig({formID:"admin_public_editinfo_form",onSuccess:adminPublicEditinfoFormSubmit,submitAfterAjaxPrompt:'有数据正在异步验证，请稍等...',inIframe:true});
					$("#admin_public_editinfo_form_realname").formValidator({onShow:"请输入真实姓名",onFocus:"真实姓名应该为2-20位之间"}).inputValidator({min:2,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"姓名两边不能有空格"},onError:"真实姓名应该为2-20位之间"});
				})
				function adminPublicEditinfoFormSubmit(){
					$.post('/login.do?method=updateUserInfo', $("#admin_public_editinfo_form").serialize(), function(res){
						var obj = eval("("+res+")");
						if(!obj.status){
							$.messager.alert('提示信息', obj.info, 'error');
						}else{
							$.messager.alert('提示信息', obj.info, 'info' , function(){
								top.window.location.reload();
							});
						}
					})
				}
			</script>
			<form id="admin_public_editinfo_form">
				<table cellspacing="10">
					<tr>
						<td width="90">用户名：</td>
						<td>${sessionScope.UserInfo.userName}</td>
						<td></td>
					</tr>
					<tr>
						<td>最后登录时间</td> 
						<td>${sessionScope.UserInfo.lastDate}</td>
						<td></td>
					</tr>
					<tr>
						<td>最后登录IP</td> 
						<td>${sessionScope.UserInfo.userIp}</td>
						<td></td>
					</tr>
					<tr>
						<td>真实姓名</td>
						<td><input id="admin_public_editinfo_form_realname" type="text" name="realName" value="${sessionScope.UserInfo.realName}" style="width:180px;height:22px" /></td>
						<td><div id="admin_public_editinfo_form_realnameTip"></div></td>
					</tr>
					<tr>
						<td colspan="3"><a id="admin_public_editinfo_form_submit" class="easyui-linkbutton">提交</a></td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>