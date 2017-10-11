$(function(){
	basicObject.initIndustryPic();
	userTagObj.initCtrl();
	
})


var sexArr=[],ageArr=[],marryArr=[],carArr=[],positionArr=[],babyArr=[];
var userTagObj = {
		initCtrl:function(){
			layui.use('laydate', function(){
				  var laydate = layui.laydate;
				  
				  var start = {
				    min: laydate.now()
				    ,max: '2099-06-16 23:59:59'
				    ,istoday: false
				    ,choose: function(datas){
				      end.min = datas; //开始日选好后，重置结束日的最小日期
				      end.start = datas //将结束日的初始值设定为开始日
				    }
				  };
				  
				  var end = {
				    min: laydate.now()
				    ,max: '2099-06-16 23:59:59'
				    ,istoday: false
				    ,choose: function(datas){
				      start.max = datas; //结束日选好后，重置开始日的最大日期
				    }
				  };
				  
				  document.getElementById('LAY_demorange_s').onclick = function(){
				    start.elem = this;
				    laydate(start);
				  }
				  document.getElementById('LAY_demorange_e').onclick = function(){
				    end.elem = this
				    laydate(end);
				  }
				  
				});
			
			owl.ajaxRequest("/shopListByUser.do","",function(e){
				$("#shopId").empty("");
				$("#shopListTemplate").tmpl( e.data ).appendTo("#shopId");	
				layui.use('form', function(){
				   var form = layui.form();
				   form.render();
				});
			});	
			
			owl.ajaxRequest("/getOwlDataUser.do","",function(e){
//				console.log(e);
				var dataO = e.data;
				
				sexArr=[{value: 300,name: "iphone"},{value: 200,name: "HUAWEI"},{value: 150,name: "XIAOMI"},{value: 100,name: "Samsung"},{value: 80,name: "OPPO"},{value: 70,name: "vivo"}];
				ageArr=[200,0,0,20];
				marryArr=[{value: 300,name: "摄影手机"},{value: 200,name: "高性价比"},{value: 150,name: "高端服务"},{value: 100,name: "美颜"},{value: 80,name: "音乐"},{value: 70,name: "其他"}];
				babyArr=[{value: 300,name: "1000-1999"},{value: 200,name: "2000-2999"},{value: 150,name: "4000以上"},{value: 100,name: "500-999"},{value: 80,name: "其他"}];
				
				
				
				if ($("#sex_pie").length) {
		            var j = echarts.init(document.getElementById("sex_pie"), echartsParam);
		            j.setOption({
		                tooltip: {
		                    trigger: "item",
		                    formatter: "{a} <br/>{b} : {c} ({d}%)"
		                },
		                legend: {
		                    x: "center",
		                    y: "bottom",
		                    data: [
		                        "iphone",
		                        "HUAWEI",
		                        "XIAOMI",
		                        "Samsung",
		                        "OPPO",
		                        "vivo"
		                    ]
		                },
		                toolbox: {
		                    show: !0,
		                    feature: {
		                        magicType: {
		                            show: !0,
		                            type: [
		                                "pie",
		                                "funnel"
		                            ],
		                            option: {
		                                funnel: {
		                                    x: "25%",
		                                    width: "50%",
		                                    funnelAlign: "left",
		                                    max: 1548
		                                }
		                            }
		                        },
		                        restore: {
		                            show: !0,
		                            title: "Restore"
		                        },
		                        saveAsImage: {
		                            show: !0,
		                            title: "Save Image"
		                        }
		                    }
		                },
		                calculable: !0,
		                series: [
		                    {
		                        name: "性别比例",
		                        type: "pie",
		                        radius: "55%",
		                        center: [
		                            "50%",
		                            "48%"
		                        ],
		                        data: sexArr
		                    }
		                ]
		            });
		        }//
				
				if ($("#age_category").length) {
		            var j = echarts.init(document.getElementById("age_category"), echartsParam);
		            j.setOption({
		            	 tooltip : {
		            	        trigger: 'axis',
		            	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		            	        }
		            	    },
		            	    grid: {
		            	        left: '3%',
		            	        right: '4%',
		            	        bottom: '3%',
		            	        containLabel: true
		            	    },
		            	    xAxis : [
		            	        {
		            	            type : 'category',
		            	            data : [ '手机','平板', '智能电视','未知'],
		            	            axisTick: {
		            	                alignWithLabel: true
		            	            }
		            	        }
		            	    ],
		            	    yAxis : [
		            	        {
		            	            type : 'value'
		            	        }
		            	    ],
		            	    series : [
		            	        {
		            	            name:'年龄分布',
		            	            type:'bar',
		            	            barWidth: '60%',
		            	            data:ageArr
		            	        }
		            	    ]
		            });
		        }//
				
				if ($("#marry_pie").length) {
		            var j = echarts.init(document.getElementById("marry_pie"), echartsParam);
		            j.setOption({
		            	 tooltip: {
		            	        trigger: 'item',
		            	        formatter: "{a} <br/>{b}: {c} ({d}%)"
		            	    },
		            	    legend: {
		            	        orient: 'vertical',
		            	        x: 'left',
		            	        data:['摄影手机','音乐','高性价比','美颜','高端服务']
		            	    },
		            	    series: [
		            	        {
		            	            name:'婚姻',
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
		            	            data:marryArr
		            	        }
		            	    ]
		            });
		        }//
				
				
				if ($("#baby_category").length) {
		            var j = echarts.init(document.getElementById("baby_category"), echartsParam);
		            j.setOption({
		            	 tooltip: {
		            	        trigger: 'item',
		            	        formatter: "{a} <br/>{b}: {c} ({d}%)"
		            	    },
		            	    legend: {
		            	        orient: 'vertical',
		            	        x: 'left',
		            	        data:['1000-1999','2000-2999','4000以上','500-999','其他']
		            	    },
		            	    series: [
		            	        {
		            	            name:'价格分布',
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
		            	            data:babyArr
		            	        }
		            	    ]
		            });
		        }//
			});	
			
			
			
		},
		saveData:function(){
			
		},
		getOwlDataUser:function(shopId,startTime,endTime){
			var param={};
			param.shopId=shopId;
			param.startTime=startTime;
			param.endTime=endTime;
			owl.ajaxRequest("/userTagListByUser.do",param,function(e){
//				console.log(e);
				userTagPageCount = e.data.pageCount;
				$("#userTag-tbody").empty("");
				$("#userTagListTemplate").tmpl( e.data ).appendTo("#userTag-tbody");	
				
				userTagObj.initCtrl();
				layui.use(['laypage', 'layer'], function(){
					  var laypage = layui.laypage
					  ,layer = layui.layer;
					  
					  laypage({
						    cont: 'userTagpage'
						    ,pages: userTagPageCount //总页数
						    ,groups: 5 //连续显示分页数
						    ,curr: userTagPage || 1,
						    jump: function(e, first){ //触发分页后的回调			    	
						        if(!first){ //一定要加此判断，否则初始时会无限刷新
						        	var pageNow = e.curr;	
						        	userTagPage =pageNow;
						        	userTagObj.getShopList(pageNow,userTagPageSize);
						        }
						    }
						  });
					  
					});

			});	
			
		},
		updateShop:function(){
			var userTagId = $("#userTagId").val();
			var param = {};
			param.userTagId = userTagId;
			var title = $("#userTag-title").val();
			if(title == ""){
				layer.msg("课堂标题不能为空！");
				return false;
			}else{
				param.title=title;	
			}
			var intro = $("#userTag-intro").val();
			if(intro == ""){
				layer.msg("课堂介绍不能为空！");
				return false;
			}else{
				param.intro=intro;	
			}
			
			var authImgSize = $("#userTagImg-div").children("img").size();
			if(authImgSize == 0){
				layer.msg("你还没有上传课堂图片!");
				return false;
			}else{
				var imgStr = "";
				var imgEleArr = $("#userTagImg-div").children("img");
				for(var i=0;i<authImgSize;i++){
					imgStr = imgStr+ $(imgEleArr[i]).attr("src") + "#" ;					
				}
				imgStr = imgStr.substring(0,imgStr.length-1);
				param.imgs=imgStr;	
			}
	
			var beginTime = $("#dtp_input1").val();
			if(beginTime == ""){
				layer.msg("请填上课堂的开始时间！");
				return false;
			}else if(Date.parse(beginTime) < Date.parse(new Date())){
				layer.msg("课堂已经是过去式了，不允许修改！");
				return false;
			}else{
				param.beginTime=beginTime;	
			}
			
			var endTime = $("#dtp_input2").val();
			if(endTime == ""){
				layer.msg("请填上课堂的结束时间！");
				return false;
			}else{
				param.endTime=endTime;	
			}
			
			if(Date.parse(endTime) < Date.parse(beginTime)){
				layer.msg("开始时间不能大于结束时间！");
				return false;
			}
		
			var type = $("input[name='radChl']:checked").val();
			if(type == "" || typeof type =="undefined"){
				layer.msg("请选择课堂类别！");
				return false;
			}else{
				param.type=type;	
			}
						
			help.ajaxRequest("/userTag/modifyuserTag.do",param,function(e){
//				console.log(JSON.stringify(e));		
				location.href=getBasePath()+"/userTag/userTagListPage.do";
			});	
		}
}
