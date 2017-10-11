$(function(){
	
	
	$("#addBtn").click(function(){
		layer.open({
			  type: 2, //page层
			  area: ['650px', '600px'],
			  title: '添加设备',
			  shade: 0.6, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  content: getBasePath()+"/addDevicePage.do"
			}); 
	});

    $("#addCameraBtn").click(function(){
        layer.open({
            type: 2, //page层
            area: ['650px', '600px'],
            title: '添加设备',
            shade: 0.6, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            content: getBasePath()+"/addCameraDevicePage.do"
        });
    });

    $("#flushBtn").click(function(){
		deviceObj.getDeviceList(devicePage,devicePageSize);

	});
    $("#flushCameraBtn").click(function(){
        deviceObj.getCameraDeviceList(devicePage,devicePageSize);
    });
    deviceObj.getCameraDeviceList(devicePage,devicePageSize);
    deviceObj.getDeviceList(devicePage,devicePageSize);
	basicObject.initIndustryPic();
})
var devicePage=1;
var devicePageSize=10;
var devicePageCount=1;
var deviceObj = {
		initCtrl:function(){
			layui.use('form', function(){
				  var $ = layui.jquery, form = layui.form();
				  //全选
				  form.on('checkbox(allChoose)', function(data){
				    var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
				    child.each(function(index, item){
				      item.checked = data.elem.checked;
				    });
				    form.render('checkbox');
				  });
				  
				});
			
			$(".updateBtn").unbind().click(function(){
				var that = this;
				var deviceId = $(that).attr("id").split("_")[1];
				layer.open({
					  type: 2, //page层
					  area: ['650px', '600px'],
					  title: '修改设备',
					  shade: 0.6, //遮罩透明度
					  moveType: 1, //拖拽风格，0是默认，1是传统拖动
					  shift: 1, //0-6的动画形式，-1不开启
					  content: getBasePath()+"/updatedevicepage.do?shopprobeId="+deviceId
					}); 
			});	
			
			$(".delBtn").unbind().click(function(){
				var that = this;
				var shopprobeId = $(that).attr("id").split("_")[1];
				var param = {};
				param.shopprobeId=shopprobeId;
				layer.confirm('确认删除该设备吗？', {
					  btn: ['确认','取消'] //按钮
					}, function(){
						owl.ajaxRequest("/delDevice.do",param,function(e){
							layer.msg(e.info);
							window.top.location=getBasePath()+"/devicePage.do"; 
						});
					}, function(){});
			});

            $(".delCameraBtn").unbind().click(function(){
                var that = this;
                var shopCameraId = $(that).attr("id").split("_")[1]
                var param = {};
                param.shopCameraId=shopCameraId;
                layer.confirm('确认删除该设备吗？', {
                    btn: ['确认','取消'] //按钮
                }, function(){
                    owl.ajaxRequest("/delCameraDevice.do",param,function(e){
                        layer.msg(e.info);
                        window.top.location=getBasePath()+"/devicePage.do";
                    });
                }, function(){});
            });

            $(".updateCameraDeviceBtn").unbind().click(function(){
                var that = this;
                var shopCameraId = $(that).attr("id").split("_")[1];
                layer.open({
                    type: 2, //page层
                    area: ['650px', '650px'],
                    title: '修改设备',
                    shade: 0.6, //遮罩透明度
                    moveType: 1, //拖拽风格，0是默认，1是传统拖动
                    shift: 1, //0-6的动画形式，-1不开启
                    content: getBasePath()+"/updateCameraDevicePage.do?shopCameraId="+shopCameraId
                });
            });
        },
		saveData:function(){
			
		},
		getDeviceList:function(page,pageSize){
			var param={};
			param.page=page;
			param.pageSize=pageSize;
			owl.ajaxRequest("/deviceList.do",param,function(e){
//				console.log(e);
				devicePageCount = e.data.pageCount;
				$("#device-tbody").empty("");
                //$("#device-tbody1").empty("");
				$("#deviceListTemplate").tmpl( e.data ).appendTo("#device-tbody");
                //$("#deviceListTemplate1").tmpl( e.data ).appendTo("#device-tbody1");
				deviceObj.initCtrl();
				layui.use(['laypage', 'layer'], function(){
					  var laypage = layui.laypage
					  ,layer = layui.layer;
					  
					  laypage({
						    cont: 'devicepage'
						    ,pages: devicePageCount //总页数
						    ,groups: 5 //连续显示分页数
						    ,curr: devicePage || 1,
						    jump: function(e, first){ //触发分页后的回调			    	
						        if(!first){ //一定要加此判断，否则初始时会无限刷新
						        	var pageNow = e.curr;	
						        	devicePage =pageNow;
						        	deviceObj.getDeviceList(pageNow,devicePageSize);
						        }
						    }
						  });
					  
					});

			});	
			
		},
        getCameraDeviceList:function(page,pageSize){
        var param={};
        param.page=page;
        param.pageSize=pageSize;
        owl.ajaxRequest("/shopCameraList.do",param,function(e){
//				console.log(e);
            devicePageCount = e.data.pageCount;
            $("#device-tbody1").empty("");
            $("#deviceListTemplate1").tmpl( e.data ).appendTo("#device-tbody1");
            deviceObj.initCtrl();
            layui.use(['laypage', 'layer'], function(){
                var laypage = layui.laypage
                    ,layer = layui.layer;

                laypage({
                    cont: 'devicepage'
                    ,pages: devicePageCount //总页数
                    ,groups: 5 //连续显示分页数
                    ,curr: devicePage || 1,
                    jump: function(e, first){ //触发分页后的回调
                        if(!first){ //一定要加此判断，否则初始时会无限刷新
                            var pageNow = e.curr;
                            devicePage =pageNow;
                            deviceObj.getDeviceList(pageNow,devicePageSize);
                        }
                    }
                });

            });

        });

    },
		updateDevice:function(){
			
		}
}
