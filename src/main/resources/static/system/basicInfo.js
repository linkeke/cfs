
var basicObject = {
	getShopList:function(){
		
		if($("#shopId").length){
			//获取门店
			owl.ajaxRequest("/shopListByUser.do","",function(e){
				$("#shopId").empty("");
				$("#shopListTemplate").tmpl( e.data ).appendTo("#shopId");	
				layui.use('form', function(){
				   var form = layui.form();
				   form.on('select(filter)', function(data){
//					   console.log(data.value); //得到被选中的值
					   var value = data.value;
					   if(value == 1){
						   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_car.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
					   }else if(value == 2){
						   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_clothes.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
					   }else if(value == 3){
						   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_car.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
					   }else if(value == 4){
						   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_beauty.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
					   }else if(value == 5){
						   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_internet.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
					   }else if(value == 6){
						   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_car.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
					   }
					   
					 });      
				   form.render();
				});
			});	
		}
		
	},
	logout:function(){
		//绑定退出
		$("#logout").attr("href",getBasePath()+"/logout.do");
	},
	initSelect:function(){
		$("#shopId").change(function(){
			var value = $(this).val();
			if(value == 1){
				   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_car.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
			   }else if(value == 2){
				   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_clothes.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
			   }else if(value == 3){
				   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_car.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
			   }else if(value == 4){
				   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_beauty.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
			   }else if(value == 5){
				   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_internet.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
			   }else if(value == 6){
				   $(".nav_menu").css({"background-image":"url('/owl_wifi/images/head_car.jpg')","background-repeat":"no-repeat","background-color":"#fff"});
			   }
		});
	},
	initIndustryPic:function(){
		var industryPic = $("#industryPic").val();
		var cssObj = "url('"+industryPic+"')";
		$(".nav_menu").css({"background-image":cssObj,"background-repeat":"no-repeat","background-color":"#fff"});
	}
	
}
