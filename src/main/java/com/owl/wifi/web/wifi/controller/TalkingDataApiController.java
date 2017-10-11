package com.owl.wifi.web.wifi.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.wifi.model.TalkingDataModel;
import com.owl.wifi.web.wifi.service.TalkingDataApiService;

@Controller
@RequestMapping("")
public class TalkingDataApiController{
	
	BKLogger logger = BKLogger.getLogger(TalkingDataApiController.class);
	private static final Logger log= LoggerFactory.getLogger(TalkingDataApiController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private TalkingDataApiService talkingDataApiService;
	
	 @RequestMapping("/userlocgeoweekPage.do")
	 public String indexPage(HttpServletRequest request,Model model){
		 return "talkingData/form1";
	 }
	
	@RequestMapping(value = "/userlocgeoweek.do")
	@ResponseBody
    public String userlocgeoweek(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	Calendar cal = Calendar.getInstance();
        	int week = cal.get(Calendar.WEEK_OF_YEAR);
        	int year = cal.get(Calendar.YEAR);
        	result = talkingDataApiService.userlocgeoweek(id, type, year+""+week);
        }
        catch (Exception e) { 
        	log.error("获取周聚集区域查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("周聚集区域查询服务", result);
    } 
	
	@RequestMapping("/userlocgeomonPage.do")
	 public String userlocgeomonPage(HttpServletRequest request,Model model){
		 return "talkingData/form2";
	 }
	@RequestMapping(value = "/userlocgeomon.do")
	@ResponseBody
    public String userlocgeomon(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	Calendar cal = Calendar.getInstance();
        	int month = cal.get(Calendar.MONTH)+1;
        	String monthStr=month<10?("0"+month):(month+"");
        	int year = cal.get(Calendar.YEAR);
        	result = talkingDataApiService.userlocgeoweek(id, type, year+monthStr);
        }
        catch (Exception e) { 
        	log.error("获取月聚集区域查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("月聚集区域查询服务", result);
    } 
	
	@RequestMapping("/userlocresidenceinfoPage.do")
	 public String userlocresidenceinfoPage(HttpServletRequest request,Model model){
		 return "talkingData/form3";
	 }
	@RequestMapping(value = "/userlocresidenceinfo.do")
	@ResponseBody
    public String userlocresidenceinfo(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	result = talkingDataApiService.userlocresidenceinfo(id, type);
        }
        catch (Exception e) { 
        	log.error("获取夜间活跃区域查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("夜间活跃区域查询服务", result);
    } 
	
	@RequestMapping("/userloccityPage.do")
	 public String userloccityPage(HttpServletRequest request,Model model){
		 return "talkingData/form4";
	 }
	@RequestMapping(value = "/userloccity.do")
	@ResponseBody
    public String userloccity(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	
        	Calendar cal = Calendar.getInstance();
        	int month = cal.get(Calendar.MONTH)+1;
        	String monthStr=month<10?("0"+month):(month+"");
        	int year = cal.get(Calendar.YEAR);
        	
        	result = talkingDataApiService.userloccity(id, type,year+monthStr);
        }
        catch (Exception e) { 
        	log.error("获取常出现城市查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("常出现城市查询服务", result);
    } 
	
	@RequestMapping("/usertaggamePage.do")
	 public String usertaggamePage(HttpServletRequest request,Model model){
		 return "talkingData/form5";
	 }
	@RequestMapping(value = "/usertaggame.do")
	@ResponseBody
    public String usertaggame(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	result = talkingDataApiService.usertaggame(id, type);
        }
        catch (Exception e) { 
        	log.error("获取游戏兴趣标签查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("游戏兴趣标签查询服务", result);
    } 
	
	@RequestMapping("/usertagconsumePage.do")
	 public String usertagconsumePage(HttpServletRequest request,Model model){
		 return "talkingData/form6";
	 }
	@RequestMapping(value = "/usertagconsume.do")
	@ResponseBody
    public String usertagconsume(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	result = talkingDataApiService.usertagconsume(id, type);
        }
        catch (Exception e) { 
        	log.error("获取线下消费偏好标签查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("线下消费偏好标签查询服务", result);
    } 
	
	@RequestMapping("/usertagdemographicPage.do")
	 public String usertagdemographicPage(HttpServletRequest request,Model model){
		 return "talkingData/form7";
	 }
	@RequestMapping(value = "/usertagdemographic.do")
	@ResponseBody
    public String usertagdemographic(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	result = talkingDataApiService.usertagdemographic(id, type);
        }
        catch (Exception e) { 
        	log.error("获取人口属性标签查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("人口属性标签查询服务", result);
    } 
	
	@RequestMapping("/usertagappPage.do")
	 public String usertagappPage(HttpServletRequest request,Model model){
		 return "talkingData/form8";
	 }
	@RequestMapping(value = "/usertagapp.do")
	@ResponseBody
    public String usertagapp(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	result = talkingDataApiService.usertagapp(id, type);
        }
        catch (Exception e) { 
        	log.error("获取应用兴趣标签查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("应用兴趣标签查询服务", result);
    } 
	
	@RequestMapping("/usertagfinancePage.do")
	 public String usertagfinancePage(HttpServletRequest request,Model model){
		 return "talkingData/form9";
	 }
	@RequestMapping(value = "/usertagfinance.do")
	@ResponseBody
    public String usertagfinance(HttpServletRequest request,HttpServletResponse response,TalkingDataModel talkingDataModel) {
		Result result =null;
        try {
        	String id = talkingDataModel.getId();
        	String type = talkingDataModel.getType();
        	result = talkingDataApiService.usertagfinance(id, type);
        }
        catch (Exception e) { 
        	log.error("获取金融标签查询服务命令失败", e);
			result = new Result ();
        }        
        return logger.infobk("金融标签查询服务", result);
    } 
	
	
}
