<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style type="text/css">
#index_public_main .bar {border:1px solid #999999; background:#FFFFFF; height:5px; font-size:2px; width:89%; margin:2px 0 5px 0;padding:1px;overflow: hidden;}
#index_public_main .bar_1 {border:1px dotted #999999; background:#FFFFFF; height:5px; font-size:2px; width:89%; margin:2px 0 5px 0;padding:1px;overflow: hidden;}
#index_public_main .barli_red{background:#ff6600; height:5px; margin:0px; padding:0;}
#index_public_main .barli_blue{background:#0099FF; height:5px; margin:0px; padding:0;}
#index_public_main .barli_green{background:#36b52a; height:5px; margin:0px; padding:0;}
#index_public_main .barli_1{background:#999999; height:5px; margin:0px; padding:0;}
#index_public_main .barli{background:#36b52a; height:5px; margin:0px; padding:0;}
</style>
<div id="index_public_main" class="easyui-panel" data-options="fit:true,title:'后台首页',border:false">
	<table width="100%" cellspacing="5" border="0">
		<tr>
			<!-- 个人信息 -->
			<td width="50%" valign="top">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title">我的个人信息</div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8">
						您好，tiankang<br />
						所属角色： <br />
						最后登录时间： <br />
						最后登录IP： <br />
					</div>
				</div>
			</td>
			<!-- 安全提示 -->
			<td width="50%" valign="top">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title">安全提示</div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8">&nbsp;</div>
				</div>
			</td>
		</tr>
		<tr>
			<!-- 服务器参数 -->
			<td valign="top">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title">服务器参数</div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8">
						服务器域名/IP地址：localhost(192.168.110.22) <br />
						服务器标识：123456 <br />
						服务器操作系统：windows &nbsp;内核版本：windows NT<br />
						服务器解译引擎：weblogic <br />
						服务器语言：中文 <br />
						服务器端口：80 <br />
						服务器主机名：leo<br />
						管理员邮箱：dangdangzone@163.com <br />
						绝对路径：/ <br />
						上传文件最大限制（upload_max_filesize）：20 <br />
					</div>
				</div>
			</td>
			<!-- 服务器实时数据 -->
			<td valign="top">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title">服务器实时数据</div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8">
						无法获取当前服务器实时数据
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<!-- 网络使用状况 -->
			<td valign="top">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title">网络使用状况</div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8">
						无法获取当前服务器网络使用信息
					</div>
				</div>
			</td>
			<!-- 系统说明 -->
			<td valign="top">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title">系统说明</div>
						<div class="panel-tool"></div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8">
						jQuery EasyUI：1.3.5<br />
						jQuery formValidator：4.1.3<br />
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>