package com.owl.wifi.web.wifi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.owl.wifi.log.BKLogger;
import com.owl.wifi.web.wifi.service.ProbeDeviceService;
@Controller
public class ProbeWebController{
	
	BKLogger logger = BKLogger.getLogger(ProbeWebController.class);
	private static final Logger log= LoggerFactory.getLogger(ProbeWebController.class);
	
	@Autowired
	private ProbeDeviceService probeDeviceService;
	
	@RequestMapping("/exportMacPage.do")
	 public String exportMacPage(HttpServletRequest request,Model model){
		 return "mac";
	 }
	@RequestMapping(value = "/exportMac.do")
    public String exportMacController(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,String dmac) {
        try {
        	//probeDeviceService.exportMac(response,dmac);
        }
        catch (Exception e) { 
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        return null;
    } 	
}
