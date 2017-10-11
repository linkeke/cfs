$(function(){
	
	
	$("#addGroup").click(function(){
		layer.open({
			  type: 2, //page层
			  area: ['850px', '800px'],
			  title: '添加管理组',
			  shade: 0.6, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  content: getBasePath()+"/addGroupPage.do"
			}); 
	});	
	
	
  groupList.queryGroupList(groupList.page,groupList.pageSize);
  
});

var groupList={};
groupList.page=1;
groupList.pageSize=20;
groupList.queryGroupList=function(page,pageSize){
	var param = {};
	param.page=page;
	param.pageSize=pageSize;
	groupList.page=page;
	groupList.pageSize=pageSize;
	owl.ajaxRequest("/groupList",param,function(e){
  	$("#groupList").html("");
  	$("#groupListTotal").html(e.data.count);
  	$("#groupTemplate").tmpl( e.data ).appendTo("#groupList"); 
  	groupList.modifyGroup();
  	groupList.delGroup();
  	
  	layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage
		  ,layer = layui.layer;
		  
		  laypage({
			    cont: 'pageDiv'
			    ,pages: e.data.pageCount //总页数
			    ,groups: 5 //连续显示分页数
			    ,curr: page || 1,
			    jump: function(e, first){ //触发分页后的回调			    	
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var pageNow = e.curr;	
			        	userPage =pageNow;
			        	groupList.queryGroupList(e.curr,pageSize);
			        }
			    }
			  });
		  
		});
  	
    });
};
groupList.modifyGroup=function(){
	$(".updateRole").click(function(){
		var groupId=$(this).attr("groupId");
		layer.open({
			  type: 2, //page层
			  area: ['850px', '800px'],
			  title: '修改管理组',
			  shade: 0.6, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  content: getBasePath()+"/modifyGroupPage?groupId="+groupId
			}); 
	});
};
groupList.delGroup=function(){
	$(".delRole").click(function(){
		var groupId =$(this).attr("groupId");
		layer.confirm('确认删除该群组吗？', {
			  btn: ['确认','取消'] //按钮
			}, function(){
				var param={};
				param.groupId=groupId;
				owl.ajaxRequest("/delGroup",param,function(e){
					layer.msg(e.info);
					 groupList.queryGroupList(groupList.page,groupList.pageSize);
				});
			}, function(){});
		
	});
};