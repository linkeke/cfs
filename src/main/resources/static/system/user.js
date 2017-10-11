$(function(){
	
	$("#addBtn").click(function(){
		layer.open({
			  type: 2, //page层
			  area: ['650px', '600px'],
			  title: '添加用户',
			  shade: 0.6, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  content: getBasePath()+"/addUserPage.do"
			}); 
	});	
	
	$("#flushBtn").click(function(){
		userObj.getUserList(userPage,userPageSize);
	});	
	
	userObj.getUserList(userPage,userPageSize);
	basicObject.initIndustryPic();
})
var userPage=1;
var userPageSize=10;
var userPageCount=1;
var userObj = {
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
			
			$(".updateBtn").click(function(){
				var that = this;
				var userId = $(that).attr("id").split("_")[1];
				layer.open({
					  type: 2, //page层
					  area: ['650px', '600px'],
					  title: '修改用户',
					  shade: 0.6, //遮罩透明度
					  moveType: 1, //拖拽风格，0是默认，1是传统拖动
					  shift: 1, //0-6的动画形式，-1不开启
					  content: getBasePath()+"/updateuserpage.do?userId="+userId
					}); 
			});	
			
			$(".delBtn").click(function(){
				var that = this;
				var userId = $(that).attr("id").split("_")[1];
				var param = {};
				param.userId=userId;
				layer.confirm('确认删除该用户吗？', {
					  btn: ['确认','取消'] //按钮
					}, function(){
						owl.ajaxRequest("/deluser.do",param,function(e){
							layer.msg(e.info);
							window.top.location=getBasePath()+"/userPage.do"; 
						});
					}, function(){});
			});	
			
			
		},
		saveData:function(){
			
		},
		getUserList:function(page,pageSize){
			var param={};
			param.page=page;
			param.pageSize=pageSize;
			owl.ajaxRequest("/userList.do",param,function(e){
//				console.log(e);
				userPageCount = e.data.pageCount;
				$("#user-tbody").empty("");
				$("#userListTemplate").tmpl( e.data ).appendTo("#user-tbody");	
				
				userObj.initCtrl();
				layui.use(['laypage', 'layer'], function(){
					  var laypage = layui.laypage
					  ,layer = layui.layer;
					  
					  laypage({
						    cont: 'userpage'
						    ,pages: userPageCount //总页数
						    ,groups: 5 //连续显示分页数
						    ,curr: userPage || 1,
						    jump: function(e, first){ //触发分页后的回调			    	
						        if(!first){ //一定要加此判断，否则初始时会无限刷新
						        	var pageNow = e.curr;	
						        	userPage =pageNow;
						        	userObj.getUserList(pageNow,userPageSize);
						        }
						    }
						  });
					  
					});

			});	
			
		},
		updateUser:function(){
			
		}
}
