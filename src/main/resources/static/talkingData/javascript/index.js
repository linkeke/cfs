layui.use('laydate', function(){
  var laydate = layui.laydate;
  var date_param = {
  	 max : $("#myDate").val(),
	 istoday : true
  };
  document.getElementById("myDate").onclick = function(){
	  date_param.elem = this;
	  laydate(date_param);
  };
});

$(document).ready(function(e) {
	// 初始化店铺列表			
	owl.ajaxRequest("/userShopList.do", "", function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		var list = result.data;
		for(var i = 0; i < list.length; i++) {
			if(i == 0) {
				initData(list[i].shopId, $("#myDate").val());
			}
			$("#shopId").append("<option value='" + list[i].shopId + "'>" + list[i].shopName + "</option>");
		}
		basicObject.initSelect();
		basicObject.initIndustryPic();
		
	});
	
	// 30秒刷新一次数据
	var dataTimer = window.setInterval("doSelect()", 30 * 1000);
 });
 
 function doSelect() {
	 var shopId = $("#shopId").val();
	 var myDate = $("#myDate").val();
	 initData(shopId, myDate);
 }
 
 function initData(shopId, date) {
	 var param = {
		shopId: shopId,
		myDate: date
	 };
	 // 今日客流
	owl.ajaxRequest("/todayKL.do", param, function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		$("#today_kl").html(result.data.today_kl);
       // $("#today_kl").after("<i class='glyphicon glyphicon-arrow-down' style='color: firebrick'></i>");
        $("#stayTime").html(result.data.stayTime);
		$("#repeat_kl").html(result.data.repeatCustomer);
        $("#vistedCustomerAmount").html(result.data.vistedCustomerAmount);
        var todayKLRatio = parseFloat(result.data.todayKLRatio);
        if(todayKLRatio<0){
            $("#today_kl").next().attr("class","glyphicon glyphicon-arrow-down").css("color","#1ABB9C");
        }else if(todayKLRatio>0){
            $("#today_kl").next().attr("class","glyphicon glyphicon-arrow-up").css("color","#E74C3C");
        }else{
            $("#today_kl").next().attr("class","");
        }
        $("#todayKLRatio").html(result.data.todayKLRatio+'%');
        var repeatCustomerRatio = parseFloat(result.data.repeatCustomerRatio);
        if(repeatCustomerRatio<0){
            $("#repeat_kl").next().attr("class","glyphicon glyphicon-arrow-down").css("color","#1ABB9C");
        }else if(repeatCustomerRatio>0){
            $("#repeat_kl").next().attr("class","glyphicon glyphicon-arrow-up").css("color","#E74C3C");
        }else{
            $("#repeat_kl").next().attr("class","");
        }
        $("#repeatCustomerRatio").html(result.data.repeatCustomerRatio+'%');
        var newCustomerRatio = parseFloat(result.data.newCustomerRatio);
        if(newCustomerRatio<0){
            $("#vistedCustomerAmount").next().attr("class","glyphicon glyphicon-arrow-down").css("color","").css("color","#1ABB9C");
        }else if(newCustomerRatio>0){
            $("#vistedCustomerAmount").next().attr("class","glyphicon glyphicon-arrow-up").css("color","").css("color","#E74C3C");
        }else{
            $("#vistedCustomerAmount").next().attr("class","");
        }
        $("#newCustomerRatio").html(result.data.newCustomerRatio+'%');
        var stayTimeRatio = parseFloat(result.data.stayTimeRatio);
        if(stayTimeRatio<0){
            $("#stayTime").next().attr("class","glyphicon glyphicon-arrow-down").css("color","").css("color","#1ABB9C");
        }else if(stayTimeRatio>0){
            $("#stayTime").next().attr("class","glyphicon glyphicon-arrow-up").css("color","").css("color","#E74C3C");
        }else{
            $("#stayTime").next().attr("class","");
        }
        $("#stayTimeRatio").html(result.data.stayTimeRatio+'%');
	});
     owl.ajaxRequest("/getProbeDeviceModel.do", param, function(result) {
         if(result.status == -1) {
             alert("获取数据失败！");
             return;
         }

         var offlineList = result.data.offlinList;
         var onlineList = result.data.onlinList;
         $(".span2").empty("");
         var onLength = onlineList.length;
         if(onlineList && onLength>0){
             var iterator = onLength>68?68:onLength;
             for(var i = 0; i < iterator; i++) {
                 $(".span2").append("<img class='onlineImg' src='images/person_on.gif'/>" +
                 "<span hidden>"  +onlineList[i]+
                 "</span>");
             }
         }
         if(offlineList && offlineList.length>0){
             var offLength = offlineList.length;
             var length = onLength+offLength>68?68-onLength:offLength;
             for(var i = 0; i < length; i++) {
                 $(".span2").append("<img class='onlineImg' src='images/person_off.gif'/>" +
                 "<span hidden>" +offlineList[i]+
                 "</span>");
             }
         }
         $(".onlineImg").click(function(){
             var mac = $(this).next("span").html();
             var param = {
                 mac:mac
             }
             owl.ajaxRequest("/getPersonData.do", param, function(result) {
                 $("#userTag-tbody").children().remove();
                 $("#appTag-tbody").children().remove();
                 
            	 if(result.status == -1) {
                     alert("获取数据失败！");
                     return;
                 }
                 var records = result.data.personInfo;
                 var appRecords = result.data.appList;
                 var text = "";
                 var flag = true;//男

                 if(records){
                     for(var i=0;i<records.length;i++){
                         text+="<div>"+records[i].name+"</div>";
                         if(records[i].name=="性别-女"){
                             flag = false;
                         }
                     }
                 }
                 var appText = "";

                 if (appRecords){
                     for(var i=0;i<appRecords.length;i++){
                         appText+="<div>"+appRecords[i].name+"</div>";
                     }
                 }

                 if(flag){
                     $("#avatar").attr("src","images/male.gif");
                 }else{
                     $("#avatar").attr("src","images/female.gif");
                 }

                 $("#userTag-tbody").append(text);
                 $("#appTag-tbody").append(appText);
                 
	        	 if ($('#profileSelect').is(':visible'))
	        	 {
	        		 $('#profileSelect').slideUp(400);
	        		 $('#profileData').delay(400).slideDown(400);
	        	 }
	        	 else
	        	 {
	        		 $('#profileData').slideUp(400);
	        		 $('#profileData').slideDown(400);
	        	 }
             });

         });

     });
	// 实时客流量统计图
	owl.ajaxRequest("/time_kl_data.do", param, function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		var rtReport = echarts.init(document.getElementById("realtimeReport"), echartsParam);
		var option = {
			title: {
				text: ""
			},
			tooltip: {
				trigger: "axis",
				show: true
			},
			legend:{
				data: ['客流量', '时段平均客流量']
			},
			xAxis: {
				data: ["0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00",
						 "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"]	
			},
			yAxis: {
				name: "客流量(人次)",
				nameLocation: "end"
			},
			series: [{
				name: "客流量",
				type: "bar",
				animation: true,
				animationEasing: "bounceOut",
				data: result.data.timekl
			},{
				name: "时段平均客流量",
				type: "line",
				animation: true,
				data: result.data.avg_time_kl
			}]
		};
		rtReport.setOption(option);
	});
	// END
	
	// 客流趋势
	//var stayTimeReport = echarts.init(document.getElementById("stayTimeReport"), echartsParam);
	//owl.ajaxRequest("/stayTimeKL.do", param, function(result) {
	//	if(result.status == -1) {
	//		alert("获取数据失败！");
	//		return;
	//	}
	//	var option = {
	//		title: {
	//			text: ""
	//		},
	//		tooltip: {
	//			show: true
	//		},
	//		legend:{
	//			data: ['客流量']
	//		},
	//		xAxis: {
	//			name: "停留时间(分钟)",
	//			data: ["0 - 10m", "10 - 20m", "20 - 30m", "30 - 40m", "40 - 50m", "50 - 60m", "60m以上"]
	//		},
	//		yAxis: {
	//			name: "客流量(人次)",
	//			nameLocation: "end"
	//		},
	//		series: [{
	//			name: "客流量",
	//			type: "bar",
	//			animation: true,
	//			animationEasing: "bounceOut",
	//			data: result.data
	//		}]
	//	};
	//	stayTimeReport.setOption(option);
	//});
	// END
	
	// 最近一周客流量统计图
	owl.ajaxRequest("/weekKL.do", param, function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		var wkRtReport = echarts.init(document.getElementById("weekReport"), echartsParam);
		var option = {
			title: {
				text: ""
			},
			tooltip: {
				trigger: "axis",
				show: true
			},
			legend:{
				data: ['客流量', '重复访客' , '日平均客流量']
			},
			xAxis: {
				data: result.data.xaxis,
				axisLabel: {
					rotate: 45,
					interval:0	
				}
			},
			yAxis: {
				name: "客流量(人次)",
				nameLocation: "end"
			},
			series: [{
				name: "客流量",
				type: "bar",
				animation: true,
				animationEasing: "bounceOut",
				data: result.data.kl,
				barGap:'0%'
			},
			{
				name: "重复访客",
				type: "bar",
				animation: true,
				animationEasing: "bounceOut",
				data: result.data.repeat_kl,
			},
			{
				name: "日平均客流量",
				type: "line",
				animation: true,
				data: result.data.avg_kl
			}]
		};
		wkRtReport.setOption(option);
	});
	// END
	
	// 人口属性
	owl.ajaxRequest("/personProperties.do", param, function(result) {
		if(result.status == -1) {
			alert("获取数据失败！");
			return;	
		}
		var titles = ["sex:性别", "age:年龄", "identityFamily:家庭与身份", "shopping:网购", "reading:阅读", "information:资讯", 
						"contact:社交", "life:生活", "manageBanking:金融理财", "estate:房产", "wearing:服饰鞋帽", "education:文化教育",
						 "xxyl:休闲娱乐", "carservice:汽车服务", "xfdw:消费定位", "financial:金融"];
		for(var i = 0; i < titles.length; i++) {
			var ele = titles[i].split(":");
			var dom = ele[0];
			var title = ele[1];
			var rptDom = echarts.init(document.getElementById(dom + "Rpt"), echartsParam);
			common_pie_report(rptDom, title, result.data[dom][title]);
		}
	});
 }


function common_pie_report(echartDom, title, dataset) {
	var category = dataset.length;
	var option = null;
	if(category < 9) {
		option = {
			title: {
				text: title,
				x: "center"	
			},
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
			title: {
				text: title,
				x: "center"	
			},
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

$("#logout").attr("href",getBasePath()+"/logout.do");