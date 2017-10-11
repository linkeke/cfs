var bmapObj = {
		loadMap:function(){
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "http://api.map.baidu.com/api?v=2.0&ak=136a0c1063fcd5642df9618ddf29a973&callback=init";
			document.body.appendChild(script);
		},
		initMap:function(container,size,lat,lng){
			var map = new BMap.Map(container);            // 创建Map实例
			var point = new BMap.Point(lat, lng); // 创建点坐标
			map.centerAndZoom(point,size);                 
			map.enableScrollWheelZoom();   
			return map;
		}
		
}
