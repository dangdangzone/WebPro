<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="/pub/js/xheditor/xheditor-iframe.min.js"></script>
<script type="text/javascript" src="/pub/js/xheditor/xheditor_lang/zh-cn.js"></script>
<style type="text/css">
.btnMap {width:50px !important;background:transparent url(/pub/js/xheditor/plugins/googlemap/map.gif) no-repeat center center;}
.btnCode {background:transparent url(/pub/js/xheditor/plugins/prettify/code.gif) no-repeat 16px 16px;background-position:2px 2px;}
</style>
<script type="text/javascript">
var xheditorMarkdownCSS = '<style type="text/css">*{margin:0;padding:0;}p {margin:1em 0;line-height:1.5em;}table {font-size:inherit;font:100%;margin:1em;}table th{border-bottom:1px solid #bbb;padding:.2em 1em;}table td{border-bottom:1px solid #ddd;padding:.2em 1em;}input[type=text],input[type=password],input[type=image],textarea{font:99% helvetica,arial,freesans,sans-serif;}select,option{padding:0 .25em;}optgroup{margin-top:.5em;}img{border:0;max-width:100%;}abbr{border-bottom:none;}a{color:#4183c4;text-decoration:none;}a:hover{text-decoration:underline;}a code,a:link code,a:visited code{color:#4183c4;}h2,h3{margin:1em 0;}h1,h2,h3,h4,h5,h6{border:0;}h1{font-size:170%;border-bottom:4px solid #aaa;padding-bottom:.5em;margin-top:1.5em;}h1:first-child{margin-top:0;padding-top:.25em;border-top:none;}h2{font-size:150%;margin-top:1.5em;border-bottom:4px solid #e0e0e0;padding-bottom:.5em;}h3{margin-top:1em;}hr{border:1px solid #ddd;}ul{margin:1em 0 1em 2em;}ol{margin:1em 0 1em 2em;}ul li,ol li{margin-top:.5em;margin-bottom:.5em;}ul ul,ul ol,ol ol,ol ul{margin-top:0;margin-bottom:0;}blockquote{margin:1em 0;border-left:5px solid #ddd;padding-left:.6em;color:#555;}dt{font-weight:bold;margin-left:1em;}dd{margin-left:2em;margin-bottom:1em;}pre{margin-left:2em;border-left:3px solid #CCC;padding:0 1em;}</style>';
var xheditorPlugins={
    Code:{c:'btnCode',t:'插入代码',h:1,e:function(){
		var _this=this;
		var htmlCode='<div><select id="xheCodeType"><option value="html">HTML/XML</option><option value="js">Javascript</option><option value="css">CSS</option><option value="php">PHP</option><option value="java">Java</option><option value="py">Python</option><option value="pl">Perl</option><option value="rb">Ruby</option><option value="cs">C#</option><option value="c">C++/C</option><option value="vb">VB/ASP</option><option value="">其它</option></select></div><div><textarea id="xheCodeValue" wrap="soft" spellcheck="false" style="width:300px;height:100px;" /></div><div style="text-align:right;"><input type="button" id="xheSave" value="确定" /></div>';			var jCode=$(htmlCode),jType=$('#xheCodeType',jCode),jValue=$('#xheCodeValue',jCode),jSave=$('#xheSave',jCode);
		jSave.click(function(){
			_this.loadBookmark();
			_this.pasteHTML('<pre class="prettyprint lang-'+jType.val()+'">'+_this.domEncode(jValue.val())+'</pre>');
			_this.hidePanel();
			return false;	
		});
		_this.saveBookmark();
		_this.showDialog(jCode);
	}},
    map:{c:'btnMap',t:'插入Google地图',e:function(){
        var _this=this;
        _this.saveBookmark();
        _this.showIframeModal('Google 地图','/pub/js/xheditor/plugins/googlemap/googlemap.html',function(v){
            _this.loadBookmark();
            _this.pasteHTML('<img src="'+v+'" />');
        },538,404);
    }}
};
var xheditorSetting = {
	skin:       'nostyle',
	plugins:    xheditorPlugins,
	loadCSS:    xheditorMarkdownCSS,
	tools:      'Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Link,Unlink,Anchor,Img,Flash,Media,Hr,Emot,Table,Code,map,|,Source,Preview,Print,Fullscreen',
	upLinkUrl:  '/Upload/link',
	upLinkExt:  'zip,rar,tar,gz,7z,pdf,doc,docx,xls,xlsx,ppt,pptx,txt',
	upImgUrl:   '/Upload/img',
	upImgExt:   'jpg,jpeg,gif,png',
	upFlashUrl: '/Upload/flash',
	upFlashExt: 'swf',
	upMediaUrl: '/Upload/media',
	upMediaExt: 'avi'
}
</script>