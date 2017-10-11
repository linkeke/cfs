package com.owl.wifi.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * @Description 系统全局变量Mapper
 * @author 汤清
 * @date 20150528
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class SysContent {  
    private static ThreadLocal<HttpServletRequest> requestLocal= new ThreadLocal<HttpServletRequest>();  
    private static ThreadLocal<HttpServletResponse> responseLocal= new ThreadLocal<HttpServletResponse>();  
      
    /**
	 * (获取与当前请求线程绑定的Request.)
	 * 
	 * @version 1.0
	 * @createTime 20150528
	 * @updateTime 20150528
	 * @createAuthor 汤清
	 * @updateAuthor 汤清
	 */
   public static HttpServletRequest getRequest() {  
       return (HttpServletRequest)requestLocal.get();  
   }  
   /**
 	 * (设定全局变量Request.)
 	 * 
 	 * @version 1.0
 	 * @createTime 20150528
 	 * @updateTime 20150528
 	 * @createAuthor 汤清
 	 * @updateAuthor 汤清
 	 */
   public static void setRequest(HttpServletRequest request) {  
       requestLocal.set(request);  
   }  
   /**
 	 * (获取与当前请求线程绑定的Response.)
 	 * 
 	 * @version 1.0
 	 * @createTime 20150528
 	 * @updateTime 20150528
 	 * @createAuthor 汤清
 	 * @updateAuthor 汤清
 	 */
   public static HttpServletResponse getResponse() {  
       return (HttpServletResponse)responseLocal.get();  
   }  
   /**
	 * (设定全局变量Response.)
	 * 
	 * @version 1.0
	 * @createTime 20150528
	 * @updateTime 20150528
	 * @createAuthor 汤清
	 * @updateAuthor 汤清
	 */
   public static void setResponse(HttpServletResponse response) { 
	   response.setCharacterEncoding("UTF-8");
       responseLocal.set(response);  
   }  
   public static HttpSession getSession() {  
       return (HttpSession)((HttpServletRequest)requestLocal.get()).getSession();  
   }    
   
  

}