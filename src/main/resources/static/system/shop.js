$(function(){
	
	
	$("#addBtn").click(function(){
		layer.open({
			  type: 2, //page层
			  area: ['750px', '800px'],
			  title: '添加门店',
			  shade: 0.6, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  content: getBasePath()+"/addshopPage.do"
			}); 
	});	
	
	$("#flushBtn").click(function(){
		shopObj.getShopList(shopPage,shopPageSize);
	});	
	
	$("#logout").attr("href",getBasePath()+"/logout.do");
	shopObj.getShopList(shopPage,shopPageSize);
	basicObject.initIndustryPic();
})
var shopPage=1;
var shopPageSize=10;
var shopPageCount=1;

var form;

var shopObj = {
		initCtrl:function(){
			layui.use('form', function(){
				  var $ = layui.jquery;form = layui.form();
				  //全选
				  form.on('checkbox(allChoose)', function(data){
				    var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
				    child.each(function(index, item){
				      item.checked = data.elem.checked;
				    });
				    form.render('checkbox');
				  });
				  
				});
			
			$(".updateBtn").click(function(){
				var that = this;
				var shopId = $(that).attr("id").split("_")[1];
				layer.open({
					  type: 2, //page层
					  area: ['750px', '800px'],
					  title: '修改门店',
					  shade: 0.6, //遮罩透明度
					  moveType: 1, //拖拽风格，0是默认，1是传统拖动
					  shift: 1, //0-6的动画形式，-1不开启
					  content: getBasePath()+"/updateshoppage.do?shopId="+shopId
					}); 
			});	
			
			$(".delBtn").click(function(){
				var that = this;
				var shopId = $(that).attr("id").split("_")[1];
				var param = {};
				param.shopId=shopId;
				layer.confirm('确认删除该门店吗？', {
					  btn: ['确认','取消'] //按钮
					}, function(){
						owl.ajaxRequest("/delshop.do",param,function(e){
							layer.msg(e.info);
							window.top.location=getBasePath()+"/shopPage.do"; 
						});
					}, function(){});
			});	
			
			form.on('switch(switchTest)', function(data){
				var that = this;
				var shopId = $(that).attr("id").split("_")[1];
				var param = {};
				param.shopId=shopId;
				var talkingdataFlag = this.checked ? 1 : 0;
				param.talkingdataFlag = talkingdataFlag;
				owl.ajaxRequest("/updateshop.do",param,function(e){
					layer.msg(e.info);
				});
				
			    layer.tips('温馨提示：开启代表设备获取的MAC地址会从talkingdata等第三方数据平台调用用户画像数据，请谨慎操作', data.othis)
			  });
			
			
		},
		saveData:function(){
			
		},
		getShopList:function(page,pageSize){
			var param={};
			param.page=page;
			param.pageSize=pageSize;
			owl.ajaxRequest("/shopListByUser.do",param,function(e){
//				console.log(e);
				shopPageCount = e.data.pageCount;
				$("#shop-tbody").empty("");
				$("#shopListTemplate").tmpl( e.data ).appendTo("#shop-tbody");	
				layui.use(['form','laypage', 'layer'], function(){
					  var laypage = layui.laypage
					  ,layer = layui.layer,
					  form = layui.form();
					  
					  shopObj.initCtrl();
					  
					  laypage({
						    cont: 'shoppage'
						    ,pages: shopPageCount //总页数
						    ,groups: 5 //连续显示分页数
						    ,curr: shopPage || 1,
						    jump: function(e, first){ //触发分页后的回调			    	
						        if(!first){ //一定要加此判断，否则初始时会无限刷新
						        	var pageNow = e.curr;	
						        	shopPage =pageNow;
						        	shopObj.getShopList(pageNow,shopPageSize);
						        }
						    }
						  });
					  
					});

			});	
			
		},
		updateShop:function(){
			var shopId = $("#shopId").val();
			var param = {};
			param.shopId = shopId;
			var title = $("#shop-title").val();
			if(title == ""){
				layer.msg("课堂标题不能为空！");
				return false;
			}else{
				param.title=title;	
			}
			var intro = $("#shop-intro").val();
			if(intro == ""){
				layer.msg("课堂介绍不能为空！");
				return false;
			}else{
				param.intro=intro;	
			}
			
			var authImgSize = $("#shopImg-div").children("img").size();
			if(authImgSize == 0){
				layer.msg("你还没有上传课堂图片!");
				return false;
			}else{
				var imgStr = "";
				var imgEleArr = $("#shopImg-div").children("img");
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
						
			help.ajaxRequest("/shop/modifyshop.do",param,function(e){
//				console.log(JSON.stringify(e));		
				location.href=getBasePath()+"/shop/shopListPage.do";
			});	
		}
}
