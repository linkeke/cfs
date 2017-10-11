$(function(){
    userTagObj.initCtrl();
    var shopId= $("#shopId").val();
    var startIme= $("#LAY_demorange_s").val();
    var endTime= $("#LAY_demorange_e").val();
    var url = "game_report_data.do";
    var handler = $("#game_report");
    userTagObj.getOwlDataUser(shopId,startIme,endTime);
    userTagObj.getTagReport(url,shopId,startIme,endTime,handler);
    url = "consumption_report_data.do";
    handler = $("#consumption_report");
    userTagObj.getTagReport(url,shopId,startIme,endTime,handler);
    url = "interest_report.do"
    handler = $("#interest_report");
    userTagObj.getTagReport(url,shopId,startIme,endTime,handler);
    url = "banking_report_data.do";
    handler = $("#bank_report");
    userTagObj.getTagReport(url,shopId,startIme,endTime,handler);
    $("#search_usertag").unbind().click(function(){
        var shopId= $("#shopId").val();
        var startIme= $("#LAY_demorange_s").val();
        var endTime= $("#LAY_demorange_e").val();
        userTagObj.getOwlDataUser(shopId,startIme,endTime);
        var url = "game_report_data.do";
        var handler = $("#game_report");
        userTagObj.getTagReport(url,shopId,startIme,endTime,handler);
        url = "consumption_report_data.do";
        handler = $("#consumption_report");
        userTagObj.getTagReport(url,shopId,startIme,endTime,handler);
        url = "interest_report.do"
        handler = $("#interest_report");
        userTagObj.getTagReport(url,shopId,startIme,endTime,handler);
        url = "banking_report_data.do";
        handler = $("#bank_report");
        userTagObj.getTagReport(url,shopId,startIme,endTime,handler);
    });
    basicObject.logout();
})

var sexArr=[],ageArr=[],marryArr=[],carArr=[],positionArr=[],babyArr=[],brandArr =[],deviceArr=[],deviceFeatureArr=[],priceArr=[];
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

            brandArr=[{value: 300,name: "iphone"},{value: 200,name: "HUAWEI"},{value: 150,name: "XIAOMI"},{value: 100,name: "Samsung"},{value: 80,name: "OPPO"},{value: 70,name: "vivo"}];
            deviceArr=[200,0,0,20];
            deviceFeatureArr=[{value: 300,name: "摄影手机"},{value: 200,name: "高性价比"},{value: 150,name: "高端服务"},{value: 100,name: "美颜"},{value: 80,name: "音乐"},{value: 70,name: "其他"}];
            priceArr=[{value: 300,name: "1000-1999"},{value: 200,name: "2000-2999"},{value: 150,name: "4000以上"},{value: 100,name: "500-999"},{value: 80,name: "其他"}];
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

            if ($("#brand_pie").length) {
                var j = echarts.init(document.getElementById("brand_pie"), echartsParam);
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
                            data: brandArr
                        }
                    ]
                });
            }//

            if ($("#device_category").length) {
                var j = echarts.init(document.getElementById("device_category"), echartsParam);
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
                            data:deviceArr
                        }
                    ]
                });
            }//

            if ($("#device_feature_pie").length) {
                var j = echarts.init(document.getElementById("device_feature_pie"), echartsParam);
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
                            data:deviceFeatureArr
                        }
                    ]
                });
            }//

        });


    },


    getTagReport:function(url,shopId, startTime, endTime,handler) {
        var param = {
            shopId: shopId,
            startDate: startTime,
            endDate: endTime
        };
        // 初始化报表
        var url = url;
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
                    html += "<div class='x_content'><div id='" + id + "' class='report' style='height:350px;'></div></div></div></div>";
                    handler.append(html);
                }
                var rtReport = echarts.init(document.getElementById(id), echartsParam);
                common_pie_report(rtReport, title, labelList);
            }
        });
    }


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