layui.use('laydate', function(){
  var laydate = layui.laydate;
  var date_param = {
  	 max : $("#endDate").val(),
	 istoday : true
  };
  document.getElementById("startDate").onclick = function(){
	  date_param.elem = this;
	  laydate(date_param);
  };
  document.getElementById("endDate").onclick = function(){
	  date_param.elem = this;
	  laydate(date_param);
  };
});

$(document).ready(function(e) {
	basicObject.initIndustryPic();
	
	// 初始化店铺列表			
	owl.ajaxRequest("/userShopList.do", "", function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		var list = result.data;
		for(var i = 0; i < list.length; i++) {
			if(i == 0) {
				initReport(list[i].shopId, $("#startDate").val(), $("#endDate").val());
			}
			$("#shopId").append("<option value='" + list[i].shopId + "'>" + list[i].shopName + "</option>");
		}
		basicObject.initSelect();
	});
 }); 
 
 function doSelect() {
	 var shopId = $("#shopId").val();
	 var startDate = $("#startDate").val();
	 var endDate = $("#endDate").val();
	 if(startDate == "") {
		alert("请选择开始日期！");
		$("#startDate").focus();
		return;
	 }
	 if(endDate == "") {
		alert("请选择结束日期！");
		$("#endDate").focus();
		return;
	 }
	 initReport(shopId, startDate, endDate);
 }
 
 // 初始化报表
 function initReport(shopId, startDate, endDate) {
	 var param = {
		shopId: shopId,
		startDate: startDate,
		endDate: endDate
	 };
	// 初始化报表
	var url = (rpt == 1 ? "game_report_data.do" : rpt == 2 ? "consumption_report_data.do" : rpt == 3 ? "interest_report_data.do" : "banking_report_data.do");

	owl.ajaxRequest("/" + url, param, function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		//2、遍历Json串获取其属性  
		for(var key in result.data){
			var title = key;
			var labelList = result.data[key];
			var id = title + "_report";
			var rptObj = document.getElementById(id);
			if(rptObj == null || rptObj == undefined) {
				var html = "<div class='col-md-4 col-sm-4 col-xs-12'>";
					html += "<div class='x_panel'>";
					html += "<div class='x_title'>";
					html += "<h2>" + title + "</h2>";
					html += "<ul class='nav navbar-right panel_toolbox'>";
					html += "<li><a class='collapse-link'><i class='fa fa-chevron-up'></i></a></li>";
					html += "<li class='dropdown'><a href='#' class='dropdown-toggle' data-toggle='dropdown'role='button' aria-expanded='false'>";
					html += "<i class='fa fa-wrench'></i></a>";
					html += "<ul class='dropdown-menu' role='menu'><li><a href='#'>Settings 1</a></li>";
					html += "<li><a href='#'>Settings 2</a></li></ul></li>";
					html += "<li><a class='close-link'><i class='fa fa-close'></i></a></li></ul>";
					html += "<div class='clearfix'></div></div>";
					html += "<div class='x_content'><div id='" + id + "' class='report'></div></div></div></div>";
				$(".x_content .row").append(html);
			}
			var rtReport = echarts.init(document.getElementById(id), echartsParam);
			common_pie_report(rtReport, title, labelList);
		}  
	});
 }
 
function common_pie_report(echartDom, title, dataset) {
	var category = dataset.length;
	var option = null;
	if(category < 9) {
		option = {
			tooltip: {
				formatter: "{a} <br/>{b}  : {c}人 ({d}%)"
			},
			series: [{
				name: title,
				type: "pie",
				data: dataset,
				center: ["50%", "55%"],
				itemStyle: {
					emphasis: {
						shadowBlur: 10,
						shadowOffsetX: 0,
						shadowColor: 'rgba(0, 0, 0, 0.5)'
					}
				}
			}]
		};
	} else {
		var xAxis = new Array(dataset.length);
		var idx = 0;
		for(var i = 0; i < dataset.length; i++) {
			xAxis[idx++] = dataset[i].name;	
		}
		option = {
			tooltip: {
				trigger: 'item',
				formatter: "{a} <br/>{b}  : {c}人 ({d}%)"
			},
			 legend: {
				orient: 'vertical',
				x: 'left',
				data: xAxis
			},
			series: [{
				name: title,
				type:'pie',
				radius: ['50%', '70%'],
				avoidLabelOverlap: false,
				label: {
					normal: {
						show: false,
						position: 'center'
					},
					emphasis: {
						show: true,
						textStyle: {
							fontSize: '30',
							fontWeight: 'bold'
						}
					}
				},
				labelLine: {
					normal: {
						show: false
					}
				},
				data:dataset
			}
		]};
	}	
	echartDom.setOption(option);
}