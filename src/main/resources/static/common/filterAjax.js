(function($){  
    //备份jquery的ajax方法  
    var _ajax=$.ajax;      
    //获取当前网址，如： http://localhost:8083/proj/meun.jsp  
    var curWwwPath = window.document.location.href;  
    //获取主机地址之后的目录，如： proj/meun.jsp  
    var pathName = window.document.location.pathname;  
    var pos = curWwwPath.indexOf(pathName);  
    //获取主机地址，如： http://localhost:8083  
    var localhostPath = curWwwPath.substring(0, pos);  
    //获取带"/"的项目名，如：/proj  
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);  
    //重写jquery的ajax方法  
    $.ajax=function(opt){  
        //备份opt中error和success方法  
        var fn = {  
            error:function(XMLHttpRequest, textStatus, errorThrown){},  
            success:function(data, textStatus){}  
        }  
        if(opt.error){  
            fn.error=opt.error;  
        }  
        if(opt.success){  
            fn.success=opt.success;  
        }  
          
        //扩展增强处理  
        var _opt = $.extend(opt,{  
            error:function(XMLHttpRequest, textStatus, errorThrown){  
                //错误方法增强处理  
                  
                fn.error(XMLHttpRequest, textStatus, errorThrown);  
            },  
            success:function(data, textStatus){             	
                //成功回调方法增强处理  
                 if(data.status==10000){ 
                  fn.success(data, textStatus);
                 }else if(data.status==10081){
                 /* alert(data.info);*/
                  	layer.msg(data.info);
                    top.location.href=(localhostPath + projectName)+"/user/loginPage.do";
                 }else if(data.status==403){
                     /* alert(data.info);*/
                	 layer.msg(data.info);
                	 fn.success(data, textStatus);
                 }else{
                	 layer.msg(data.info);
                	 fn.success(data, textStatus);
                 }  
            },
            beforeSend:function(XHR){  
                //提交前回调方法  
            	
            	/*layer.open({type: 2,time: 8});*/
            
            },  
            complete:function(XHR, TS){  
                //请求完成后回调函数 (请求成功或失败之后均调用)。  
            	
            	/*layer.close();*/
             
            }    
        });  
        _ajax(_opt);  
    };  
})(jQuery); 