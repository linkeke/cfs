$(function(){
	
	$("#fudong").on("click",function(){
		location.href=getBasePath()+"/exportMac.do?dmac=20:28:18:a2:6b:32";
	});
	
	$("#meiji").on("click",function(){
		location.href=getBasePath()+"/exportMac.do?dmac=20:28:18:a2:6b:82";
	});

	$("#vk").on("click",function(){
		location.href=getBasePath()+"/exportMac.do?dmac=20:28:18:a2:6b:92";
	});
	
	$("#test").on("click",function(){
		location.href=getBasePath()+"/exportMac.do?dmac=20:28:18:a2:6c:72";
	});
	
});

