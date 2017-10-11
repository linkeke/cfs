$(function(){
	
	flowDataCount.getCountInfo();
})


var flowDataCount = {
	getCountInfo:function(type,startDate,endDate){		
		owl.ajaxRequest("/getDataCount.do","",function(e){	
			
			console.log(e);
			
			$("#macMonth").html(e.data.countData.macMonCount);
			$("#macWeek").html(e.data.countData.macWeekCount);
			$("#macYesterday").html(e.data.countData.macDayCount);
			
		});
	}
	
}

