package com.owl.wifi.web.talkingdata.controller;


import com.owl.wifi.content.Status;
import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.entity.Shop;
import com.owl.wifi.web.entity.User;
import com.owl.wifi.web.shop.service.ShopService;
import com.owl.wifi.web.talkingdata.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2017/6/20.
 */

@Controller("")
@RequestMapping
public class BranchAnalysisController {
    private static Logger log = LoggerFactory.getLogger(BranchAnalysisController.class);

    private BKLogger logger = BKLogger.getLogger(BranchAnalysisController.class);

    @Autowired
    private ReportService reportService;

    @Autowired
    ShopService shopService;
    @RequestMapping("/analysis.do")
    public String analysisPage(HttpServletRequest request,Model model){
        User user = (User) request.getSession().getAttribute("user");
        Date date =  new Date();
        String edate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(cal.DATE,-15);//把日期往后增加一天.整数往后推,负数往前移动
        date = cal.getTime();
        String sdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        model.addAttribute("userName",user.getUserName());
        model.addAttribute("industryPic",user.getIndustryPic());
        model.addAttribute("sdate", sdate);
        model.addAttribute("edate",edate);
        return "talkingData/user_analysis_report";

    }
    public long getLengthBetweenTwoDay(String start,String end){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date sDate=null;
        Date eDate=null;
        try {
            sDate=df.parse(start);
            eDate=df.parse(end);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long betweendays=(long) ((eDate.getTime()-sDate.getTime())/(1000 * 60 * 60 *24)+0.5);//天数间隔
        return betweendays+1;
    }

    public String[]  getDateInterval(String sdate,String edate){
        int length = (int) getLengthBetweenTwoDay(sdate,edate);
        String[] xaxis = new String[length];
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = null;
        try {
            sDate=df.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        for(int i=0;i<length;i++){
            xaxis[i] = df.format(sDate);
            c.setTime(sDate);
            c.add(Calendar.DATE, 1); // 日期加1天
            sDate = c.getTime();
        }
        return xaxis;
    }

    /**
     *店铺差异比较
     * @param shopId1
     * @param shopId2
     * @param sdate
     * @param edate
     * @return
     */
    @RequestMapping("/getAllShopKL.do")
    @ResponseBody
    public Object getKL(Integer shopId1,Integer shopId2,String sdate,String edate){
        Result result = new Result();
        if(shopId1==null||shopId2==null){
            result.setStatus(Status.param_error_status);
            return result;
        }
        String[] xaxis = getDateInterval(sdate,edate);
        long intervalLength = getLengthBetweenTwoDay(sdate,edate);
        long[] shop1KL = new long[(int) intervalLength];
        long[] shop2KL = new long[(int) intervalLength];

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = null;
        try {
                 sDate = df.parse(sdate);
                 Calendar cal = Calendar.getInstance();
                 {
                    cal.setTime(sDate);
                 }
                for(int i=0;i<intervalLength;i++)
                {
                    // 客流量
                    Date date = cal.getTime();
                    Map<String, Long> dataMap1 = reportService.klByDate(shopId1, date, "00:00:00",
                        "23:59:59");
                    long KL1 = dataMap1.get("countKL") == null ? 0L : dataMap1.get("countKL");
                    Map<String, Long> dataMap2 = reportService.klByDate(shopId2, cal.getTime(), "00:00:00",
                        "23:59:59");
                    long KL2 = dataMap2.get("countKL") == null ? 0L : dataMap2.get("countKL");
                    shop1KL[i] = KL1;
                    shop2KL[i] = KL2;
                    cal.add(Calendar.DATE, 1); // 日期加1天
                }
                Shop shop1 = shopService.selectShopById(shopId1);
                Shop shop2 = shopService.selectShopById(shopId2);
                Map<String, Object> dataMap = new HashMap<String, Object>();
                {
                    dataMap.put("xaxis", xaxis);
                    dataMap.put("shop1KL", shop1KL);
                    dataMap.put("shop2KL", shop2KL);
                    dataMap.put("shop1Name",shop1.getNameTxt());
                    dataMap.put("shop2Name",shop2.getNameTxt());
                }
                result.setData(dataMap);
                result.setStatus(Status.success_status);
                result.setInfo(Status.success_info);
            } catch (Exception e) {
                e.printStackTrace();
                result.setStatus(-1);
                log.error("获取服务器出错[BranchAnalysisController.getKLController].", e);
            }
        return logger.infobk("time_kl", result);
    }

    /**
     * 重复客流分析
     * @param shopId1
     * @param shopId2
     * @param sdate
     * @param edate
     * @return
     */
    @RequestMapping("/getRepeatKL.do")
    @ResponseBody
    public Object getRepeatKL(Integer shopId1,Integer shopId2,String sdate,String edate){
        Result result = new Result();
        if(shopId1==null||shopId2==null){
            result.setStatus(Status.param_error_status);
            return result;
        }
        String[] xaxis = getDateInterval(sdate,edate);
        long intervalLength = getLengthBetweenTwoDay(sdate,edate);
        long[] repeatKL = new long[(int) intervalLength];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = null;
        Date eDate = null;
        try{
            sDate = df.parse(sdate);
            eDate = df.parse(edate);
            Calendar cal = Calendar.getInstance();
            {
                cal.setTime(sDate);
            }
            for(int i=0;i<intervalLength;i++){
                // 客流量
                Date date = cal.getTime();
                long repeatCount = reportService.getRepeatKL(shopId1,shopId2,date);
                repeatKL[i] = repeatCount;
                cal.add(Calendar.DATE, 1); // 日期加1天
            }
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("xaxis", xaxis);
            resultMap.put("repeatKL", repeatKL);
            result.setData(resultMap);
            result.setStatus(Status.success_status);
            result.setInfo(Status.success_info);

        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(-1);
            log.error("获取服务器出错[BranchAnalysisController.getRepeatKL].", e);
        }
        return logger.infobk("repeat_kl", result);
    }

    /**
     *
     * @param request
     * @param dateStr
     * @return
     */
    @RequestMapping("/getKLCount.do")
    @ResponseBody
    public Object getAllShopTodayKL(HttpServletRequest request,String dateStr) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute("user");
        List<Map<String, Object>> list = reportService.usershopList(user.getUserId());
        if(list==null||list.size()==0){
            result.setStatus(Status.param_error_status);
            return result;
        }
        Date date = null;
        int total = 0;
        long min  = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        int index_max = 1;
        int index_min = 1;
        try {
            final String DATE_FMT_STR = "yyyy-MM-dd";
            date = new SimpleDateFormat(DATE_FMT_STR).parse(dateStr);
            // 当前时间所有店铺的客流
            for(int i=0;i<list.size();i++){
                long shopId = (long) list.get(i).get("shopId");
                Map<String, Long> dataMap = reportService.klByDate((int)shopId, date, "00:00:00", "23:59:59");
                long todayKL = dataMap.get("countKL") == null ? 0L : dataMap.get("countKL");
                if(todayKL>max){
                    max = todayKL;
                    index_max = (int) shopId;
                }
                if(todayKL<min){
                    min = todayKL;
                    index_min = (int) shopId;
                }
                total+= todayKL;
            }
            Map<String,Object> resutltMap = new HashMap<>();
            Shop minKLshop = shopService.selectShopById(index_min);
            Shop maxKLshop = shopService.selectShopById(index_max);
            resutltMap.put("minShopName",minKLshop.getNameTxt());
            resutltMap.put("maxShopName",maxKLshop.getNameTxt());
            resutltMap.put("maxCount",max);
            resutltMap.put("minCount",min);
            resutltMap.put("totalCount",total);
            resutltMap.put("avgCount",total/ list.size());
            result.setData(resutltMap);
            result.setStatus(Status.success_status);
            result.setInfo(Status.success_info);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.setStatus(-1);
            log.error("获取用户店铺出错[BranchAnalysisController.AllShopTodayKL].", ex);
        }
        return logger.infobk("repeat_kl", result);
    }
}
