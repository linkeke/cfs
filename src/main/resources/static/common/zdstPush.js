/*function zdstPush(userName,systemType){
	
	var websocket;
	if ('WebSocket' in window) {
	    websocket = new WebSocket("ws://localhost:8124/webSocketServer?userName="+userName+"&systemType="+systemType);
	} else if ('MozWebSocket' in window) {
	    websocket = new MozWebSocket("ws://localhost:8124/webSocketServer?userName="+userName+"&systemType="+systemType);
	} else {
	    websocket = new SockJS("http://localhost:8124/sockjs/webSocketServer?userName="+userName+"&systemType="+systemType);
	}
	
	var o = new Object();
	o.userName = userName;
	o.systemType = systemType;
	
	o.onmessage=function(fn){
		websocket.onmessage = function (evnt) {
			fn(evnt);
		};
	}
	o.onclose = function(evnt){
		websocket.onclose = function (evnt) {
		}
	}
	o.onopen = function(evnt){
		websocket.onopen = function (evnt) {
		};
	}
	o.onerror = function(evnt){
		websocket.onerror = function (evnt) {
		};
	}
	return o ;
	
}*/
function zdstPush(host,port){
	
	var websocket;
	
	var userName="";
	
    var systemType="";
	
	var o = new Object();
	
	o.onmessage=function(fn){
		websocket.onmessage = function (evnt) {
			fn(evnt);
			//console.log("发送消息回执");
			//websocket.send("xxx");
		};
	};
	o.onclose = function(evnt){
		websocket.onclose = function (evnt) {
		}
	};
	o.onopen = function(evnt){
		websocket.onopen = function (evnt) {
		};
	};
	o.onerror = function(evnt){
		websocket.onerror = function (evnt) {
		};
	};
	o.connection=function(userName,systemType){
		userName=userName;
		systemType=systemType;
		if ('WebSocket' in window) {
		    websocket = new WebSocket("ws://"+host+":"+port+"/webSocketServer?userName="+userName+"&systemType="+systemType);
		} else if ('MozWebSocket' in window) {
		    websocket = new MozWebSocket("ws://"+host+":"+port+"/webSocketServer?userName="+userName+"&systemType="+systemType);
		} else {
		    websocket = new SockJS("http://"+host+":"+port+"/sockjs/webSocketServer?userName="+userName+"&systemType="+systemType);
		}
		
	/*	websocket.onclose = function (evnt) {
			console.log("连接断开，重新连接");
			o.connection(userName,systemType);
		}*/
		websocket.onerror = function (evnt) {
			console.log("连接出错，重新连接");
			o.connection(userName,systemType);
		};
	};
	return o ;
	
}