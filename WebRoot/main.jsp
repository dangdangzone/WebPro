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
<div id="index_public_main" class="easyui-panel" data-options="fit:true,title:'',border:false">
	<table width="100%" cellspacing="5" border="0" style="height:300px">
		<tr>
			<td width="70%" valign="top" rowspan="2">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title"></div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8;text-align:center;height:146px;font-family:'微软雅黑';font-size:40px;padding-top:65px">
						欢迎使用瑞斯课堂管理系统
					</div>
				</div>
			</td>
			<td valign="top">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title">我的个人信息</div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8">
						您好，${sessionScope.UserInfo.realName}<br />
						最后登录时间：${sessionScope.UserInfo.lastDate} <br />
						最后登录IP： ${sessionScope.UserInfo.userIp}<br />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td valign="top" width="30%">
				<div class="panel">
					<div class="panel-header">
						<div class="panel-title">系统说明</div>
						<div class="panel-tool"></div>
					</div>
					<div class="panel-body" style="padding:8px;line-height:1.8">
						瑞斯课堂管理系统
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>