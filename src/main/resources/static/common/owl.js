var owl={};
function getBasePath(){
	 //获取当前网址，如： http://localhost:8083/proj/meun.jsp  
   var curWwwPath = window.document.location.href;  
   //获取主机地址之后的目录，如： proj/meun.jsp  
   var pathName = window.document.location.pathname;  
   var pos = curWwwPath.indexOf(pathName);  
   //获取主机地址，如： http://localhost:8083  
   var localhostPath = curWwwPath.substring(0, pos);  
   //获取带"/"的项目名，如：/proj  
   var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);  
   return localhostPath + projectName;
};
owl.ajaxRequest=function(requestPath,param,fn){
	 var load=layer.load();
	$.ajax({
		url:getBasePath()+requestPath,
		type:"post",
		dataType:"json",			
		data:param,
		success:function(e){			
			layer.close(load);
			if(e.status==10000)
			fn(e);
			if(e.status==10030){
				top.location.href=getBasePath();
			}
		},
		error:function(){
			/*alert("服务器异常");*/
			layer.close(load);
		}
		
	});
};
owl.ajaxRequestContentType=function(requestPath,param,contentType,fn){
	 var load=layer.load();
	$.ajax({
		url:getBasePath()+requestPath,
		type:"post",
		dataType:"json",
		contentType:contentType,
		data:param,
		success:function(e){			
			layer.close(load);
			if(e.status==10000)
			fn(e);
			if(e.status==10030){
				top.location.href=getBasePath()+"/";
			}
		},
		error:function(){
			/*alert("服务器异常");*/
			layer.close(load);
		}
		
	});
};
//打开页面
owl.openPage=function(obj,url,tabName){	
	$(obj).attr("_href",getBasePath()+url);
	$(obj).attr("data-title",tabName);
	  Hui_admin_tab(obj);
};
//关闭页面
owl.closePage=function(){
	removeIframe();
}

// 添加动态时间
function set_time(){
	if(servertime == null) {
		servertime = new Date();	
	}
	servertime.setTime(servertime.getTime() + 1000);
	var year = servertime.getFullYear();
	var month = servertime.getMonth() + 1;
	var day = servertime.getDate();
	var hour = servertime.getHours();
	hour = hour < 10 ? '0' + hour : hour;
	var minute = servertime.getMinutes();
	minute = minute < 10 ? '0' + minute : minute;
	var second = servertime.getSeconds();
	second = second < 10 ? '0' + second : second;
	var weeks = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	if($("#current-time").length == 0) {
		$(".top_nav ul.nav").append(htmlStr);
	}
	$("#current-time").html(year + "年" + month + "月" + day + "日 " + weeks[servertime.getDay()] + " " + hour + ":" + minute + ":" + second);
}

var servertime = null;
var htmlStr = "<li><a id='current-time' href='javascript:void(0);' class='user-profile' aria-expanded='false' style='font-size:12pt; background:url(images/timer.png) no-repeat; background-size:20px 20px; background-position:left; padding-left:25px;'>#</a></li>";
$(document).ready(function(e) {
	$(".top_nav ul.nav").append(htmlStr);	
	// 初始化时间
	owl.ajaxRequest("/getServerTime.do", "", function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		servertime = new Date();
		servertime.setTime(result.data);
		set_time(servertime);
	});
	var timer = window.setInterval("set_time()", 1000);
	
	// 异常消息
	owl.ajaxRequest("/getDeviceUploadTime.do", "", function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		var data = result.data;
		if(data.length > 0) {
			var msgObj = $(".sidebar-footer .glyphicon-comment");
			window.setInterval(function(){msgObj.fadeOut(200).fadeIn(200, function(){msgObj.removeClass("glyphicon-comment"); msgObj.addClass("glyphicon-comment");});}, 500);
			var html = "<table class='layui-table' style='margin:10px; width:780px'>";
				html += "<colgroup><col width='30'><col width='180'><col width='120'><col width='100'><col width='180'><col><col width='170'><col></colgroup>";
				html += "<thead><tr><th></th><th>案场名称</th><th>警告源类型</th><th style='text-align:center;'>警告等级</th><th>警告触发时间</th><th>警告描述</th></tr></thead>";
				html += "<tbody>";
				for(var i = 0; i < data.length; i++) {
					html += "<tr>";
					html += "<td align='center'>" + (i + 1) + "</td>";
					html += "<td>" + data[i].name_txt + "</td>";
					html += "<td>设备数据</td>";
					html += "<td align='center'><span style='background-color:#FF7800; color:#FFF; padding:5px 15px 5px 15px;'>警告</span></td>";
					html += "<td>" + (data[i].lastupload_time == null ? ' - ' : data[i].lastupload_time) + "</td>";
					html += "<td>设备由于<span style='color:#F00;'>连接状态</span>异常引起警告</td>";
					html += "</tr>";	
				}
				html += "</tbody>";
				html += "</table>";
			msgObj.bind("click", function(){
				layer.open({
					type: 1,
					title: '最新警告',
					area:['800px'],
					shade: 0.5,
					shift : 6,
					content: html
				});
			});
		}
	});
	
	// 数字远程加链接
	if($("#hik_account").length == 0) {
		return;	
	}
	var hik = $("#hik_account").val();
	var uname = hik.split("#")[0];
	var pwd = hik.split("#")[1];
	var redirect = "";
	if(hik.length != 0) {
		redirect = "http://112.74.73.220/rmp/home/index.action?param[uname=" + uname + "#pwd=" + pwd + "]";
	}
	var child_menus = $(".child_menu a");
	for(var i = 0; i < child_menus.length; i++) {
		var lk = child_menus[i];
		if(('' + lk).indexOf("monitor.do") != -1) {
			$(lk).attr("href", "http://www.daas-auto.com/supermarket_data_De/285.html");
			$(lk).attr("target", "_blank");
		}if(lk == "javascript:void(0);") {
			if(redirect != "") {
				$(lk).attr("href", redirect);
			} else {
				$(lk).click(function() {
					alert("暂未开启数字化远程视频，请联系管理员！");
					return false;
				});	
			}
		}
	}
});