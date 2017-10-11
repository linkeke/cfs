package com.owl.wifi.web.face.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.owl.wifi.content.Status;
import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Paginator;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.face.model.UserModel;
import com.owl.wifi.web.face.service.FaceService;

@Controller
@RequestMapping("")
public class FaceController {

        BKLogger logger = BKLogger.getLogger(FaceController.class);
        private static final Logger log = LoggerFactory.getLogger(FaceController.class);

        @Autowired
        private FaceService faceService;

        @RequestMapping("/facePage")
        public String facePage(HttpServletRequest request, Model model) {
                return "face/face";
        }

        @RequestMapping("/faceHistoryVisitor")
        public String historyVisitorPage(HttpServletRequest request) {
                final String DATE_FMT_STR = "yyyy-MM-dd";
                Calendar cal = Calendar.getInstance();
                {
                        cal.add(Calendar.DATE, -6);
                }
                request.setAttribute("startDate", new SimpleDateFormat(DATE_FMT_STR).format(cal.getTime()));
                request.setAttribute("endDate", new SimpleDateFormat(DATE_FMT_STR).format(new Date()));
                return "face/history_visitor";
        }

        @RequestMapping("/pushFaceToUser")
        @ResponseBody
        public String pushFaceToUser(HttpServletRequest request, Model model, UserModel userModel) throws ParseException {
        	
        		log.debug("接收到摄像机信息=="+new Date());
                faceService.PushFaceToUser(userModel);
                return "";
        }

        final int pageSize = 60;

        @RequestMapping("/get_shopface_list_pages.do")
        @ResponseBody
        public Object countShopFaceList(Integer shopId, String startDate, String endDate) {
                Result res = new Result();
                try {
                        final String DATE_FMT_STR = "yyyy-MM-dd";
                        Date sdate = new SimpleDateFormat(DATE_FMT_STR).parse(startDate);
                        Date edate = new SimpleDateFormat(DATE_FMT_STR).parse(endDate);
                        long records = this.faceService.countShopFaceList(shopId, sdate, edate);
                        res.setData((records - 1) / pageSize + 1);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取照片列表页数出错[FaceController.countShopFaceListController].", ex);
                }
                return logger.infobk("shopface_list_pages", res);
        }

        @RequestMapping("/get_shopface_list.do")
        @ResponseBody
        public Object shopFaceListController(Integer shopId, String startDate, String endDate, int page) {
                Result res = new Result();
                try { 
                        final String DATE_FMT_STR = "yyyy-MM-dd";
                        Date sdate = new SimpleDateFormat(DATE_FMT_STR).parse(startDate);
                        Date edate = new SimpleDateFormat(DATE_FMT_STR).parse(endDate);
                        Paginator paginator = new Paginator(0, pageSize);
                        paginator.gotoPage(page);
                        int startRow = paginator.getStartRow();

                        List<Map<String, Object>> shopFaceList = this.faceService.shopFaceList(shopId, sdate, edate,
                                        startRow, pageSize);
                        res.setData(shopFaceList);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取店铺照片列表出错[FaceController.shopFaceListController].", ex);
                }
                return logger.infobk("shopface_list", res);
        }
        
        @RequestMapping("/get_recentshopface_list.do")
        @ResponseBody
        public Object getrecentshopfaceList(Integer shopId) {
                Result res = new Result();
                try { 

                        List<Map<String, Object>> shopFaceList = this.faceService.shopFaceList(shopId, null, null,
                                        0, 10);
                        res.setData(shopFaceList);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取店铺照片列表出错[FaceController.shopFaceListController].", ex);
                }
                return logger.infobk("shopface_list", res);
        }

}
