$(function(){
	userTagObj.initCtrl();
	userTagObj.getOwlDataUser();
	$("#search_usertag").unbind().click(function(){
		var shopId= $("#shopId").val();
		var startIme= $("#LAY_demorange_s").val();
		var endTime= $("#LAY_demorange_e").val();
		userTagObj.getOwlDataUser(shopId,startIme,endTime);
	});
	basicObject.logout();
})

var sexArr=[],ageArr=[],marryArr=[],carArr=[],positionArr=[],babyArr=[];
var userTagObj = {
		initCtrl:function(){
			layui.use('laydate', function(){
				  var laydate = layui.laydate;
				  
				  var start = {
				    min: '2000-06-16 23:59:59'
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
				    end.elem = this;
				    laydate(end);
				  }
				  
				});
			
			basicObject.getShopList();
			basicObject.initIndustryPic();
			
		},
		getOwlDataUser:function(shopId,startTime,endTime){
			var param={};
			param.shopId=shopId;
			param.startTime=startTime;
			param.endTime=endTime;
			owl.ajaxRequest("/getOwlDataUser.do",param,function(e){
				console.log(e);
				var dataO = e.data;
				sexArr=[{value: dataO.countmale,name: "男性"},{value: dataO.countfemale,name: "女性"}];
				ageArr=[dataO.countageF,dataO.countageA,dataO.countageB,dataO.countageC,dataO.countageD,dataO.countageE];
				marryArr=[{value: dataO.countmarried,name: "已婚"},{value: dataO.countFlowUser-dataO.countmarried,name: "未知"}];
				carArr=[{value: dataO.counthavecar,name: "有车"},{value: dataO.countFlowUser-dataO.counthavecar,name: "未知"}];
				positionArr=[dataO.countstudent,dataO.countdoctor,(dataO.countFlowUser-dataO.countdoctor-dataO.countstudent)];
				babyArr=[{value: dataO.countfathermathor,name: "结婚"},{value: dataO.counthavebaby,name: "有宝宝"},{value: dataO.countfirstbaby,name: "有大宝宝"},{value: dataO.countsecondbaby,name: "有二宝宝"}];
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
		                        "男",
		                        "女"
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
		            	            data : [ '<19','19-25', '26-35', '36-45', '46-55', '>55'],
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
		            	        data:['已婚','未知']
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
				
				if ($("#cars_pie").length) {
		            var j = echarts.init(document.getElementById("cars_pie"), echartsParam);
		            j.setOption({
		                tooltip: {
		                    trigger: "item",
		                    formatter: "{a} <br/>{b} : {c} ({d}%)"
		                },
		                legend: {
		                    x: "center",
		                    y: "bottom",
		                    data: [
		                        "有车",
		                        "未知"
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
		                        name: "有车比例",
		                        type: "pie",
		                        radius: "55%",
		                        center: [
		                            "50%",
		                            "48%"
		                        ],
		                        data: carArr
		                    }
		                ]
		            });
		        }//
				
				if ($("#position_category").length) {
		            var j = echarts.init(document.getElementById("position_category"), echartsParam);
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
		            	            data : [ '大学生','医生', '未知'],
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
		            	            name:'职业分布',
		            	            type:'bar',
		            	            barWidth: '60%',
		            	            data:positionArr
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
		            	        data:['已结婚','有宝宝','有大宝宝','有二宝宝']
		            	    },
		            	    series: [
		            	        {
		            	            name:'宝宝情况',
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
			
		}
}
