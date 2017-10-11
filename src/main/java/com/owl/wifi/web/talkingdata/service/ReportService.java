package com.owl.wifi.web.talkingdata.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.owl.wifi.util.Result;
import com.owl.wifi.web.talkingdata.model.SearchModel;
import com.owl.wifi.web.wifi.model.TalkingDataModel;

public interface ReportService {

        /**
         * 用户拥有的店铺
         * 
         * @param userId[登录用户ID]
         * @return
         */
        public List<Map<String, Object>> usershopList(Integer userId);

        /**
         * 游戏标签统计
         * 
         * @param shopId
         *                [店铺ID]
         * @param date
         *                [日期]
         * @param label
         * 
         * @return
         */
        public Map<String, Long> gameDataList(Integer shopId, Date startDate, Date endDate, String label);

        /**
         * 根据时间获取总客流量
         * 
         * @param shopId
         *                [店铺ID]
         * @param date
         *                [日期]
         * @return
         */
        public Map<String, Long> klByDate(Integer shopId, Date myDate, String startTime, String endTime);

        /**
         * 时间段的客流
         * 
         * @param shopId
         * @param myDate
         * @return
         */
        public List<Map<String, Object>> timeKlByDate(Integer shopId, Date myDate);

        /**
         * 平均停留时间
         * 
         * @param shopId
         *                [店铺ID]
         * @param myDate
         *                [日期]
         * @return
         */
        public Map<String, BigDecimal> stayTimeByDate(Integer shopId, Date myDate);

        /**
         * 统计客流停留的时间人数
         * 
         * @param shopId
         * @param myDate
         * @param startMinute
         * @param endMinute
         * @return
         */
        Map<String, Long> stayTimeKL(Integer shopId, Date myDate, Integer startMinute, Integer endMinute);

        /**
         * 获取人口属性
         * 
         * @param request
         * @param searchModel
         * @return
         */
        public Result getOwlDataUser(HttpServletRequest request, SearchModel searchModel);

        /**
         * 一天的平均客流
         * 
         * @param shopId
         *                [店铺ID]
         * @return
         */
        Map<String, BigDecimal> avgKlByEveryday(Integer shopId);

        /**
         * 时间平均客流量
         * 
         * @param shopId
         * @return
         */
        List<Map<String, Object>> avgTimeKl(Integer shopId);

        /**
         * 重复访客
         * 
         * @param shopId
         * @param myDate
         * @return
         */
        Map<String, Long> repeatCustomer(Integer shopId, Date myDate);

        /**
         * 获取个人多个属性接口
         * 
         * @param request
         * @param searchModel
         * @return
         */
        public Result getOneUserAllData(HttpServletRequest request, TalkingDataModel talkingDataModel);

        /**
         * 店内在线&下线Mac address
         * 
         * @param shopId
         * @param myDate
         * @return
         */
        public List<Map<String, Object>> shopCustomerList(Integer shopId, Date startDate, Date endDate, int startRow,
                        int endRow);

        public List<Map<String, Object>> getShopCustomerList(Integer shopId, Date myDate);

        public long count_shopMacList(Integer shopId, Date startDate, Date endDate);

        /**
         * 历史顾客
         * 
         * @param shopId
         * @return
         */
        public long getTotalCustomerAmount(Integer shopId, Date myDate);

        /**
         * 新增顾客
         * 
         * @param shoId
         * @param myDate
         * @return
         */

        public long getTodayNewCustomerAmount(Integer shoId, Date myDate);

        public long getRepeatKL(Integer shopId1, Integer shopId2, Date date);

        /**
         * 设备状态
         * 
         * @param userId
         * @return
         */
        public List<Map<String, Object>> deviceStatus(Integer userId);
}
