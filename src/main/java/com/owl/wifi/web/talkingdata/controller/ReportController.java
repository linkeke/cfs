package com.owl.wifi.web.talkingdata.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.owl.wifi.content.Status;
import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Paginator;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.entity.User;
import com.owl.wifi.web.talkingdata.model.SearchModel;
import com.owl.wifi.web.talkingdata.service.ReportService;
import com.owl.wifi.web.wifi.model.TalkingDataModel;
import com.owl.wifi.web.wifi.model.ProbeDeviceModel;
import com.owl.wifi.web.wifi.service.ProbeDeviceService;

@Controller
@RequestMapping("")
public class ReportController extends LabelDataBase {

        private BKLogger logger = BKLogger.getLogger(ReportController.class);

        private static Logger log = LoggerFactory.getLogger(ReportController.class);

        @Autowired
        private ReportService reportService;

        @Autowired
        private ProbeDeviceService proDevService;

        public void setDate(HttpServletRequest request) {
                final String DATE_FMT_STR = "yyyy-MM-dd";
                Calendar cal = Calendar.getInstance();
                {
                        cal.add(Calendar.DATE, -6);
                }
                request.setAttribute("startDate", new SimpleDateFormat(DATE_FMT_STR).format(cal.getTime()));
                request.setAttribute("endDate", new SimpleDateFormat(DATE_FMT_STR).format(new Date()));
        }

        @RequestMapping("/game_report.do")
        public String redirect(HttpServletRequest request, Model model) {
                setDate(request);
                return "/talkingData/game_report";
        }

        @RequestMapping("/consumption_report.do")
        public String redirect2(HttpServletRequest request, Model model) {
                setDate(request);
                return "/talkingData/consumption_report";
        }

        @RequestMapping("/interest_report.do")
        public String redirect3(HttpServletRequest request, Model model) {
                setDate(request);
                return "/talkingData/interest_report";
        }

        @RequestMapping("/finance_report.do")
        public String redirect4(HttpServletRequest request, Model model) {
                setDate(request);
                return "/talkingData/finance_report";
        }

        @RequestMapping("/mac_list.do")
        public String macList(HttpServletRequest request, Model model) {
                final String DATE_FMT_STR = "yyyy-MM-dd";
                Calendar cal = Calendar.getInstance();
                {
                        cal.add(Calendar.DATE, -6);
                }
                request.setAttribute("startDate", new SimpleDateFormat(DATE_FMT_STR).format(cal.getTime()));
                request.setAttribute("endDate", new SimpleDateFormat(DATE_FMT_STR).format(new Date()));
                return "/mac_list";
        }

        @RequestMapping("/userShopList.do")
        @ResponseBody
        public Object userShopsController(HttpServletRequest request) {
                Result res = new Result();
                try {
                        User user = (User) request.getSession().getAttribute("user");
                        List<Map<String, Object>> list = reportService.usershopList(user.getUserId());
                        res.setData(list);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取用户店铺出错[ReportController.userShops].", ex);
                }
                return logger.infobk("user_shop_list", res);
        }

        @SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
        public Result report(int type, Integer shopId, String startDate, String endDate) throws ParseException {
                Result res = new Result();
                final String dtfmt_str = "yyyy-MM-dd";
                Map<String, Object> tmpData = new LinkedHashMap<String, Object>();

                Map<String, String> labelsMap = (type == 1 ? this.gameLabelMap
                                : type == 2 ? this.offlineXfLabelMap
                                                : type == 3 ? this.applyInterestMap : this.bankingLabelMap);
                for (Map.Entry<String, String> entry : labelsMap.entrySet()) {
                        Map<String, Object> map = new Gson().fromJson(entry.getValue(), Map.class);
                        String title = map.entrySet().iterator().next().getKey();
                        List tmpDataList = new ArrayList();
                        // 组成二级标签值
                        List<Map<String, Object>> labelList = (List<Map<String, Object>>) map.get(title);
                        for (Map<String, Object> labelMap : labelList) {
                                String lblKey = labelMap.entrySet().iterator().next().getKey();
                                Map<String, Long> dataMap = reportService.gameDataList(shopId,
                                                new SimpleDateFormat(dtfmt_str).parse(startDate),
                                                new SimpleDateFormat(dtfmt_str).parse(endDate), lblKey);
                                long records = 0;
                                if (dataMap != null) {
                                        records = dataMap.get("labelRecords") == null ? 0L
                                                        : dataMap.get("labelRecords");
                                }
                                Map<String, Object> tmpMap = new LinkedHashMap<String, Object>();
                                {
                                        tmpMap.put("name", labelMap.get(lblKey));
                                        tmpMap.put("value", records);
                                }
                                tmpDataList.add(tmpMap);
                        }
                        tmpData.put(title, tmpDataList);
                }
                res.setData(tmpData);
                res.setStatus(Status.success_status);
                res.setInfo(Status.success_info);
                return res;
        }

        @RequestMapping("/game_report_data.do")
        @ResponseBody
        public Object gameReportDataController(HttpServletRequest request, Integer shopId, String startDate,
                        String endDate) {
                Result res = new Result();
                try {
                        res = report(1, shopId, startDate, endDate);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取游戏兴趣数据出错[ReportController.gameReportData].", ex);
                }
                return logger.infobk("game_report_data", res);
        }

        @RequestMapping("/consumption_report_data.do")
        @ResponseBody
        public Object consumptionReportDataController(HttpServletRequest request, Integer shopId, String startDate,
                        String endDate) {
                Result res = new Result();
                try {
                        res = report(2, shopId, startDate, endDate);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取线下消费数据出错[ReportController.consumptionReportData].", ex);
                }
                return logger.infobk("consumption_report_data", res);
        }

        @RequestMapping("/interest_report_data.do")
        @ResponseBody
        public Object interestReportDataController(HttpServletRequest request, Integer shopId, String startDate,
                        String endDate) {
                Result res = new Result();
                try {
                        res = report(3, shopId, startDate, endDate);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取应用兴趣数据出错[ReportController.interestReportData].", ex);
                }
                return logger.infobk("interest_report_data", res);
        }

        @RequestMapping("/banking_report_data.do")
        @ResponseBody
        public Object financeReportDataController(HttpServletRequest request, Integer shopId, String startDate,
                        String endDate) {
                Result res = new Result();
                try {
                        res = report(4, shopId, startDate, endDate);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取金融数据出错[ReportController.financeReportData].", ex);
                }
                return logger.infobk("banking_report_data", res);
        }

        final int pageSize = 20;

        @RequestMapping("/get_offline_maclist_pages.do")
        @ResponseBody
        public Object countOfflineMacListController(Integer shopId, String startDate, String endDate) {
                Result res = new Result();
                try {
                        final String DATE_FMT_STR = "yyyy-MM-dd";
                        Date sdate = new SimpleDateFormat(DATE_FMT_STR).parse(startDate);
                        Date edate = new SimpleDateFormat(DATE_FMT_STR).parse(endDate);
                        long records = reportService.count_shopMacList(shopId, sdate, edate);
                        res.setData((records - 1) / pageSize + 1);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取店铺Mac address出错[ReportController.countOfflineMacListController].", ex);
                }
                return logger.infobk("mac_list_pages", res);
        }

        @RequestMapping("/get_offline_maclist.do")
        @ResponseBody
        public Object offlineMacListController(Integer shopId, String startDate, String endDate, int page) {
                Result res = new Result();
                try {
                        final String DATE_FMT_STR = "yyyy-MM-dd";
                        Date sdate = new SimpleDateFormat(DATE_FMT_STR).parse(startDate);
                        Date edate = new SimpleDateFormat(DATE_FMT_STR).parse(endDate);
                        Paginator paginator = new Paginator(0, pageSize);
                        paginator.gotoPage(page);
                        int startRow = paginator.getStartRow();

                        List<Map<String, Object>> offLineMacList = reportService.shopCustomerList(shopId, sdate, edate,
                                        startRow, pageSize); // 离线MAC
                        res.setData(offLineMacList);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取店铺Mac address出错[ReportController.offlineMacLisController].", ex);
                }
                return logger.infobk("shop_mac_list", res);
        }

        @RequestMapping("/get_online_maclist.do")
        @ResponseBody
        public Object onlineMacListController(Integer shopId) {
                Result res = new Result();
                try {
                        List<Map<String, Object>> macList = new ArrayList<>();
                        Collection<ProbeDeviceModel> onlineMacList = proDevService.getShopMacList(shopId); // 在线MAC
                        for (ProbeDeviceModel pm : onlineMacList) {
                                Map<String, Object> map = new HashMap<>();
                                {
                                        map.put("customer_mac", pm.getMac());
                                        map.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                                                        .format(pm.getStime() * 1000));
                                }
                                macList.add(map);
                        }
                        res.setData(macList);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取店铺Mac address出错[ReportController.onlineMacListController].", ex);
                }
                return logger.infobk("shop_mac_list", res);
        }

        @RequestMapping("/userTagPage.do")
        public String userTagPage(HttpServletRequest request, Model model) {
                return "/talkingData/usertag_report";
        }

        @RequestMapping("/userDevicePage.do")
        public String userDevicePage(HttpServletRequest request, Model model) {
                return "/talkingData/userdevice_report";
        }

        @RequestMapping("/getOwlDataUser.do")
        @ResponseBody
        public Object getOwlDataUserController(HttpServletRequest request, SearchModel searchModel) {
                Result result = null;
                try {
                        result = reportService.getOwlDataUser(request, searchModel);
                } catch (Exception ex) {
                        log.error("获取用户基本属性失败", ex);
                        result = new Result();
                }
                return logger.infobk("获取用户基本属性", result);
        }

        @RequestMapping("/useronedata.do")
        public String userOnePage(HttpServletRequest request, Model model, String mac) {
                model.addAttribute("mac", mac);
                model.addAttribute("idtype", "mac");
                return "/talkingData/userone_report";
        }

        @RequestMapping("/getoneuserall.do")
        @ResponseBody
        public Object getoneuserallController(HttpServletRequest request, TalkingDataModel talkingDataModel) {
                Result result = null;
                try {
                        result = reportService.getOneUserAllData(request, talkingDataModel);
                } catch (Exception ex) {
                        log.error("获取用户基本属性失败", ex);
                        result = new Result();
                }
                return logger.infobk("获取用户基本属性", result);
        }

}
