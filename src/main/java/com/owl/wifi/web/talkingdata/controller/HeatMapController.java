package com.owl.wifi.web.talkingdata.controller;

import com.owl.wifi.content.Status;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.dao.ShopMapper;
import com.owl.wifi.web.dao.TalkingdataPositionMapper;
import com.owl.wifi.web.dao.VisitShopMapper;
import com.owl.wifi.web.entity.Shop;
import com.owl.wifi.web.entity.User;
import com.owl.wifi.web.shop.service.ShopService;
import com.owl.wifi.web.talkingdata.service.HeatPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/14.
 */
@Controller
@RequestMapping("")
public class HeatMapController {
    @Autowired
    HeatPositionService heatPositionService;

    @Autowired
    ShopService shopService;

    @RequestMapping("/heatPosition.do")
    public String heatPositionPage(HttpServletRequest request,Model model){
        return "talkingData/user_position_report";
    }
    @RequestMapping("/heatMap.do")
    @ResponseBody
    public Object getCustomerPosition(Integer shopId){
        //根据shopId检索mac地址
        Result result = new Result();
        List<Map<String,Object>> list = heatPositionService.getLngLatCount(shopId);
        result.setData(list);
        result.setStatus(Status.success_status);
        result.setInfo(Status.success_info);
        return result;
    }
    @RequestMapping("/getShopDetail.do")
    @ResponseBody
    public Object getShopInfo(Integer shopId){
        Result result = new Result();
        Shop shop = shopService.selectShopById(shopId);
        result.setData(shop);
        result.setStatus(Status.success_status);
        result.setInfo(Status.success_info);
        return result;
    }


}
