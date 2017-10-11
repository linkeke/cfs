package com.owl.wifi.web.advertise.controller;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.GsonUtil;
import com.owl.wifi.util.HttpUtil;
import com.owl.wifi.util.SslUtils;
import com.owl.wifi.web.entity.User;

@Controller
@RequestMapping("")
public class AdvertiseController{
  
	BKLogger logger = BKLogger.getLogger(AdvertiseController.class);
	private static final Logger log= LoggerFactory.getLogger(AdvertiseController.class);
	
	 @RequestMapping("/ad.do")
	 public String adPage(HttpServletRequest request,Model model) throws Exception{
		 User user = (User) request.getSession().getAttribute("user");
		 model.addAttribute("userName",user.getUserName());
		 model.addAttribute("industryPic",user.getIndustryPic());
		 
		SslUtils.ignoreSsl();
    	HttpUtil u = new HttpUtil("https://api.xinyuead.cn/api/session","POST");
 		u.addTextParameter("is_inner", "2");
 		u.addTextParameter("account", "13580589643");
 		u.addTextParameter("passwd", "ceshi123");
 		byte[] tokenByte = u.send();  
        String tokenJSON = new String(tokenByte);  
         
        Map<String,Object> tokenResult = (Map<String, Object>) GsonUtil.jsonToObject(tokenJSON, Map.class);
	    Double  status  = (Double) tokenResult.get("status");
 	    int loginStatus = status.intValue();
 	    
 	   if(loginStatus == 1){
 		  model.addAttribute("xinyuead","https://dsp.xinyuead.cn/login?result="+tokenJSON);
 	   }
    	
	   return "advertisement/ad";
	 }
	 
	 @RequestMapping("/monitor.do")
	 public String monitorPage(HttpServletRequest request,Model model){
		 User user = (User) request.getSession().getAttribute("user");
		 model.addAttribute("userName",user.getUserName());
		 model.addAttribute("industryPic",user.getIndustryPic());
		 return "advertisement/monitor";
	 }
	 
	 @RequestMapping("/operate.do")
	 public String operatePage(HttpServletRequest request,Model model){
		 User user = (User) request.getSession().getAttribute("user");
		 model.addAttribute("userName",user.getUserName());
		 model.addAttribute("industryPic",user.getIndustryPic());
		 return "advertisement/operate";
	 }
	
	 
	
}
