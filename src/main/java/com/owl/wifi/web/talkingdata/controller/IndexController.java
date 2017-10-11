package com.owl.wifi.web.talkingdata.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.owl.wifi.web.dao.TalkingdataMoreMapper;
import com.owl.wifi.web.entity.TalkingdataMore;
import com.owl.wifi.web.entity.User;
import com.owl.wifi.web.wifi.model.ProbeDeviceModel;
import com.owl.wifi.web.wifi.service.ProbeDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.owl.wifi.content.Status;
import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.talkingdata.service.ReportService;

@Controller
@RequestMapping
public class IndexController {
        private BKLogger logger = BKLogger.getLogger(IndexController.class);

        private static Logger log = LoggerFactory.getLogger(IndexController.class);

        @Autowired
        private ReportService reportService;

        @Autowired
        private ProbeDeviceService proDevService;

        @Autowired
        private TalkingdataMoreMapper talkingdataMoreMapper;

        @RequestMapping("/getServerTime.do")
        @ResponseBody
        public Object currentTime() {
                Result res = new Result();
                try {
                        res.setData(new Date().getTime());
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取服务器出错[IndexController.currentTime].", ex);
                }
                return logger.infobk("server_time", res);
        }

        public double calculateRadio(double todayAmount, double yesterdayAmount) {
                double ratio = 0;
                try {
                        ratio = (todayAmount - yesterdayAmount) / yesterdayAmount;
                } catch (Exception e) {
                        ratio = todayAmount;
                        e.printStackTrace();

                }

                return ratio;

        }

        /**
         * 今日客流
         * 
         * @param shopId
         * @param myDate
         * @return
         */
        @RequestMapping("/todayKL.do")
        @ResponseBody
        public Object todayKLController(Integer shopId, String myDate) {
                Result res = new Result();
                try {
                        Date date = new Date();
                        final String DATE_FMT_STR = "yyyy-MM-dd";
                        date = new SimpleDateFormat(DATE_FMT_STR).parse(myDate);

                        // 当前时间的客流
                        Map<String, Long> dataMap = reportService.klByDate(shopId, date, "00:00:00", "23:59:59");
                        long todayKL = dataMap.get("countKL") == null ? 0L : dataMap.get("countKL");

                        // 昨天客流
                        Calendar cal = Calendar.getInstance();
                        {
                                cal.setTime(date);
                                cal.add(Calendar.DATE, -1);
                        }
                        dataMap = reportService.klByDate(shopId, cal.getTime(), "00:00:00", "23:59:59");
                        long yesterdayKL = dataMap.get("countKL") == null ? 0L : dataMap.get("countKL");

                        // 平均停留时间
                        Map<String, BigDecimal> stayDataMap = reportService.stayTimeByDate(shopId, date);
                        double stayTime = 0.0d;
                        if (stayDataMap != null) {
                                stayTime = stayDataMap.get("minutes") == null ? 0L
                                                : stayDataMap.get("minutes").doubleValue();
                        }
                        stayTime = stayTime < 0 ? 0.0d : stayTime;
                        DecimalFormat df = new DecimalFormat("######0.0");
                        // 重复访客
                        dataMap = reportService.repeatCustomer(shopId, date);
                        long repeatCustomer = dataMap.get("kl_count") == null ? 0L : dataMap.get("kl_count");

                        // 历史访客
                        long vistedCustomerAmount = reportService.getTotalCustomerAmount(shopId, date);
                        // 新增顾客
                        long currentNewCustomer = reportService.getTodayNewCustomerAmount(shopId, date);

                        Map<String, String> data = new HashMap<String, String>();
                        // 百分比计算--今日客流比
                        double todayKLRatio;
                        if (yesterdayKL == 0) {
                                todayKLRatio = todayKL;
                        } else {
                                todayKLRatio = (todayKL - yesterdayKL) / (double) yesterdayKL;
                        }
                        data.put("todayKLRatio", String.valueOf(df.format(todayKLRatio * 100)));
                        // 昨日平均停留时间
                        Map<String, BigDecimal> yestodayStayDataMap = reportService.stayTimeByDate(shopId,
                                        cal.getTime());
                        double yesterdayStayTime = 0.0;
                        if (yestodayStayDataMap != null) {
                                yesterdayStayTime = yestodayStayDataMap.get("minutes") == null ? 0L
                                                : yestodayStayDataMap.get("minutes").doubleValue();
                        }
                        yesterdayStayTime = yesterdayStayTime < 0 ? 0.0d : yesterdayStayTime;
                        double stayTimeRatio = 0;
                        if (yesterdayStayTime == 0) {
                                stayTimeRatio = stayTime;
                        } else {
                                stayTimeRatio = (stayTime - yesterdayStayTime) / (double) yesterdayStayTime;
                        }
                        data.put("stayTimeRatio", String.valueOf(df.format(stayTimeRatio * 100)));
                        // repeatCustomer重复访客率： 重复访问人数/今日流量
                        double repeatCustomerRatio = 0;
                        if (todayKL == 0) {
                                repeatCustomerRatio = 0.0;
                        } else {
                                repeatCustomerRatio = repeatCustomer / (double) todayKL;
                        }

                        data.put("repeatCustomerRatio", String.valueOf(df.format(repeatCustomerRatio * 100)));
                        double newCustomerRatio = 0;
                        // 新增占比
                        if (currentNewCustomer != 0) {
                                if (currentNewCustomer == vistedCustomerAmount) {
                                        newCustomerRatio = currentNewCustomer;
                                } else {
                                        newCustomerRatio = currentNewCustomer
                                                        / (double) (vistedCustomerAmount - currentNewCustomer);
                                }
                        }
                        data.put("newCustomerRatio", String.valueOf(df.format(newCustomerRatio * 100)));

                        {
                                data.put("today_kl", new DecimalFormat("#,##0").format(todayKL));
                                data.put("yesterday_kl", new DecimalFormat("#,##0").format(yesterdayKL));
                                data.put("stayTime", new DecimalFormat("#,##0.00").format(stayTime));
                                data.put("repeatCustomer", new DecimalFormat("#,##0").format(repeatCustomer));
                                data.put("vistedCustomerAmount", String.valueOf(vistedCustomerAmount));
                        }
                        res.setData(data);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取服务器出错[IndexController.todayKL].", ex);
                }
                return logger.infobk("today_kl", res);
        }

        /**
         * 时段客流
         * 
         * @return
         */
        @RequestMapping("/time_kl_data.do")
        @ResponseBody
        public Object timeKLByDateController(Integer shopId, String myDate) {
                Result res = new Result();
                try {
                        Date date = new Date();
                        final String DATE_FMT_STR = "yyyy-MM-dd";
                        date = new SimpleDateFormat(DATE_FMT_STR).parse(myDate);

                        long[] timekl = new long[24];
                        long[] avgTimeKl = new long[24];
                        List<Map<String, Object>> timeKlList = reportService.timeKlByDate(shopId, date);
                        List<Map<String, Object>> avgTimeKlList = reportService.avgTimeKl(shopId);
                        for (int i = 0; i < 24; i++) {
                                long timeKL = 0;
                                if (timeKlList != null && timeKlList.size() > 0) {
                                        for (Map<String, Object> map : timeKlList) {
                                                int hour = Integer.parseInt((String) map.get("hour"));
                                                long kl = (long) map.get("klCount");
                                                if (i == hour) {
                                                        timeKL = kl;
                                                        break;
                                                }
                                        }
                                }
                                timekl[i] = timeKL;

                                long timeAvgKl = 0;
                                if (timeKlList != null && timeKlList.size() > 0) {
                                        for (Map<String, Object> map : avgTimeKlList) {
                                                int hour = Integer.parseInt((String) map.get("hour"));
                                                long kl = map.get("kl") == null ? 0L
                                                                : ((BigDecimal) map.get("kl")).longValue();
                                                if (i == hour) {
                                                        timeAvgKl = kl;
                                                        break;
                                                }
                                        }
                                }
                                avgTimeKl[i] = timeAvgKl;
                        }
                        Map<String, Object> dataMap = new HashMap<String, Object>();
                        {
                                dataMap.put("timekl", timekl);
                                dataMap.put("avg_time_kl", avgTimeKl);
                        }
                        res.setData(dataMap);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取服务器出错[IndexController.timeKLByDate].", ex);
                }
                return logger.infobk("time_kl", res);
        }

        /**
         * 最近30天的客流量
         * 
         * @param shopId
         * @param myDate
         * @return
         */
        @RequestMapping("/weekKL.do")
        @ResponseBody
        public Object weekKLController(Integer shopId, String myDate) {
                Result res = new Result();
                try {
                        Date date = new Date();
                        final String DATE_FMT_STR = "yyyy-MM-dd";
                        date = new SimpleDateFormat(DATE_FMT_STR).parse(myDate);
                        Calendar cal = Calendar.getInstance();
                        {
                                cal.setTime(date);
                        }
                        int days = 30;
                        String[] xaxis = new String[30];
                        long[] kl = new long[30];
                        long[] avg_kl = new long[30];
                        long[] repeat_kl = new long[30];
                        int n = 0;
                        int m = 0;
                        int k = 0;
                        int h = 0;

                        Map<String, BigDecimal> avgKlMap = reportService.avgKlByEveryday(shopId);
                        long edayKl = 0;
                        if (avgKlMap != null) {
                                edayKl = avgKlMap.get("avg_kl") == null ? 0L : avgKlMap.get("avg_kl").longValue();
                        }

                        for (int i = days - 1; i >= 0; i--) {
                                cal.setTime(date);
                                cal.add(Calendar.DATE, -i);
                                // X轴显示标签
                                xaxis[n++] = new SimpleDateFormat("MM-dd (E)", Locale.SIMPLIFIED_CHINESE)
                                                .format(cal.getTime()).replace("星期", "周");

                                // 客流量
                                Map<String, Long> dataMap = reportService.klByDate(shopId, cal.getTime(), "00:00:00",
                                                "23:59:59");
                                long todayKL = dataMap.get("countKL") == null ? 0L : dataMap.get("countKL");
                                kl[m++] = todayKL;
                                avg_kl[k++] = edayKl;

                                // 重复访客
                                dataMap = reportService.repeatCustomer(shopId, cal.getTime());
                                long repeatCustomer = dataMap.get("kl_count") == null ? 0L : dataMap.get("kl_count");
                                repeat_kl[h++] = repeatCustomer;
                        }

                        Map<String, Object> dataMap = new HashMap<String, Object>();
                        {
                                dataMap.put("xaxis", xaxis);
                                dataMap.put("kl", kl);
                                dataMap.put("avg_kl", avg_kl);
                                dataMap.put("repeat_kl", repeat_kl);
                        }
                        res.setData(dataMap);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取服务器出错[IndexController.weekKL].", ex);
                }
                return logger.infobk("today_kl", res);
        }

        /**
         * 人数停留时间的统计
         * 
         * @return
         */
        @RequestMapping("/stayTimeKL.do")
        @ResponseBody
        public Object stayTimeKLController(Integer shopId, String myDate) {
                Result res = new Result();
                try {
                        long[] klArray = new long[7];
                        final String DATE_FMT_STR = "yyyy-MM-dd";
                        Date date = new SimpleDateFormat(DATE_FMT_STR).parse(myDate);
                        for (int i = 0; i <= 6; i++) {
                                int start = i * 10;
                                int end = i == 6 ? 1000 : (start + 10);
                                Map<String, Long> dataMap = reportService.stayTimeKL(shopId, date, start, end);
                                long kl = dataMap.get("count_kl") == null ? 0L : dataMap.get("count_kl");
                                klArray[i] = kl;
                        }
                        res.setData(klArray);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取服务器出错[IndexController.weekKL].", ex);
                }
                return logger.infobk("today_kl", res);
        }

        /**
         * 用户属性
         * 
         * @param shopId
         * @param myDate
         * @return
         */
        @SuppressWarnings("unchecked")
        @RequestMapping("/personProperties.do")
        @ResponseBody
        public Object personPropertiesController(Integer shopId, String myDate) {
                Result res = new Result();
                try {
                        Map<String, Object> dataMap = new HashMap<String, Object>();

                        String title = null;
                        Map<String, Object> map = null;

                        Map<String, String> labelsMap = LabelDataBase.personPropertiesMap;
                        {
                                title = labelsMap.get("0301"); // 性别
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("sex", report(shopId, myDate, map));

                                title = labelsMap.get("0302"); // 年龄
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("age", report(shopId, myDate, map));

                                title = labelsMap.get("0303"); // 身份与家庭
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("identityFamily", report(shopId, myDate, map));
                        }

                        labelsMap = LabelDataBase.applyInterestMap;
                        {
                                title = labelsMap.get("020101"); // 网购
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("shopping", report(shopId, myDate, map));

                                title = labelsMap.get("020103"); // 阅读
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("reading", report(shopId, myDate, map));

                                title = labelsMap.get("020104"); // 资讯
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("information", report(shopId, myDate, map));

                                title = labelsMap.get("020105"); // 社交
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("contact", report(shopId, myDate, map));

                                title = labelsMap.get("020111"); // 生活
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("life", report(shopId, myDate, map));

                                title = labelsMap.get("020114"); // 金融理财
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("manageBanking", report(shopId, myDate, map));

                                title = labelsMap.get("020115"); // 房产
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("estate", report(shopId, myDate, map));
                        }

                        labelsMap = LabelDataBase.offlineXfLabelMap;
                        {
                                title = labelsMap.get("040102"); // 服饰鞋帽
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("wearing", report(shopId, myDate, map));

                                title = labelsMap.get("040108"); // 文化教育
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("education", report(shopId, myDate, map));

                                title = labelsMap.get("040112"); // 休闲娱乐
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("xxyl", report(shopId, myDate, map));

                                title = labelsMap.get("040115"); // 汽车服务
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("carservice", report(shopId, myDate, map));

                                title = labelsMap.get("0402"); // 消费定位
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("xfdw", report(shopId, myDate, map));
                        }

                        labelsMap = LabelDataBase.bankingLabelMap;
                        {
                                title = labelsMap.get("0807"); // 金融
                                map = new Gson().fromJson(title, Map.class);
                                dataMap.put("financial", report(shopId, myDate, map));
                        }
                        res.setData(dataMap);
                        res.setStatus(Status.success_status);
                        res.setInfo(Status.success_info);
                } catch (Exception ex) {
                        ex.printStackTrace();
                        res.setStatus(-1);
                        log.error("获取服务器出错[IndexController.weekKL].", ex);
                }
                return logger.infobk("today_kl", res);
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public Map<String, Object> report(Integer shopId, String myDate, Map<String, Object> titleMap)
                        throws ParseException {
                Map<String, Object> tmpData = new LinkedHashMap<String, Object>();
                String title = titleMap.entrySet().iterator().next().getKey();
                final String dtfmt_str = "yyyy-MM-dd";
                List tmpDataList = new ArrayList();
                List<Map<String, Object>> labelList = (List<Map<String, Object>>) titleMap.get(title);

                for (Map<String, Object> labelMap : labelList) {
                        String lblKey = labelMap.entrySet().iterator().next().getKey();
                        Map<String, Long> dataMap = reportService.gameDataList(shopId,
                                        new SimpleDateFormat(dtfmt_str).parse(myDate),
                                        new SimpleDateFormat(dtfmt_str).parse(myDate), lblKey);
                        long records = 0;
                        if (dataMap != null) {
                                records = dataMap.get("labelRecords") == null ? 0L : dataMap.get("labelRecords");
                        }
                        Map<String, Object> tmpMap = new LinkedHashMap<String, Object>();
                        {
                                tmpMap.put("name", labelMap.get(lblKey));
                                tmpMap.put("value", records);
                        }
                        tmpDataList.add(tmpMap);
                }
                tmpData.put(title, tmpDataList);
                return tmpData;
        }

        /**
         * 得到当前店铺在线和离开ProbeDeviceModel，先在缓存中查找，有记录直接返回， 没有记录查找数据表返回
         * 
         * @param shopId
         * @param myDate
         * @return
         */
        @RequestMapping("/getProbeDeviceModel.do")
        @ResponseBody
        public Object getProbeDeviceModel(Integer shopId, String myDate) {
                Result res = new Result();
                // 当前在线MAC，在缓存中查找
                List<ProbeDeviceModel> onlineMacList = (List<ProbeDeviceModel>) proDevService.getShopMacList(shopId);
                // 对当前在线的ProbeDeviceModel根据进来的时间排序，最新进来的排前
                Collections.sort(onlineMacList, new Comparator<ProbeDeviceModel>() {
                        public int compare(ProbeDeviceModel o1, ProbeDeviceModel o2) {
                                if (o1.getStime() > o2.getStime()) {
                                        return -1;
                                } else if (o1.getStime() < o2.getStime()) {
                                        return 1;
                                } else {
                                        return 0;
                                }

                        }
                });
                // 将mac_address存入list集合
                List<String> onlineList = new ArrayList<>();
                if (onlineMacList != null && !onlineMacList.isEmpty()) {
                        for (ProbeDeviceModel probeDeviceModel : onlineMacList) {
                                onlineList.add(probeDeviceModel.getMac());
                        }
                }
                List<Map<String, Object>> offLineMapList = null;
                // 数据库中查询离线数据（包括了当前在线MAC）
                List<String> offlineList = new ArrayList<>();
                try {
                        Date queryDate = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
                        offLineMapList = reportService.getShopCustomerList(shopId, queryDate); // 在线离线MAC
                        // 将线上存在的数据从集合中剔除
                        if (offLineMapList != null && !offLineMapList.isEmpty()) {
                                for (Map<String, Object> map : offLineMapList) {
                                        String macAddress = (String) map.get("customer_mac");
                                        if (!onlineList.contains(macAddress)) {
                                                offlineList.add(macAddress);
                                        }
                                }
                        }
                        // 线下Mac地址可能有重复做去重处理
                        if (offlineList != null && !offlineList.isEmpty()) {
                                for (int i = 0; i < offlineList.size(); i++) {
                                        for (int j = i + 1; j < offlineList.size(); j++) {
                                                if (offlineList.get(i).equals(offlineList.get(j))) {
                                                        offlineList.remove(j);
                                                        j--;
                                                }
                                        }
                                }

                        }

                } catch (ParseException e) {
                        e.printStackTrace();
                }
                Map<String, List> results = new HashMap<>();
                results.put("onlinList", onlineList);
                results.put("offlinList", offlineList);
                res.setData(results);
                res.setStatus(Status.success_status);
                res.setInfo(Status.success_info);
                return logger.infobk("shop_mac_data", res);
        }

        @RequestMapping("getPersonData.do")
        @ResponseBody
        public Object getPersonData(String mac) {
                Result res = new Result();
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("mac", mac);
                param.put("portid", 6);
                List<TalkingdataMore> talkingdataMoreList = talkingdataMoreMapper.selectByMACgroupByPort(param);
                param.put("portid", 7);
                List<TalkingdataMore> appTalkingdataList = talkingdataMoreMapper.selectByMACgroupByPort(param);
                HashMap<String, List> map = new HashMap<>();
                map.put("personInfo", talkingdataMoreList);
                map.put("appList", appTalkingdataList);
                res.setStatus(Status.success_status);
                res.setInfo(Status.success_info);
                res.setData(map);
                return logger.infobk("basicinfo", res);
        }

        @RequestMapping("/getDeviceUploadTime.do")
        @ResponseBody
        public Object deviceUploadTime(HttpServletRequest request) throws ParseException {
                Result res = new Result();
                User user = (User) request.getSession().getAttribute("user");
                if (user == null) {
                        return logger.infobk("devList", null);
                }
                List<Map<String, Object>> devList = reportService.deviceStatus(user.getUserId());
                res.setStatus(Status.success_status);
                res.setInfo(Status.success_info);
                res.setData(devList);
                return logger.infobk("devList", res);
        }
}
