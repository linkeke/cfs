package com.owl.wifi.web.talkingdata.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.wifi.content.FinVal;
import com.owl.wifi.content.Status;
import com.owl.wifi.util.Help;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.dao.ShopprobeMapper;
import com.owl.wifi.web.dao.TalkingdataMoreMapper;
import com.owl.wifi.web.dao.UserShopMapper;
import com.owl.wifi.web.dao.VisitShopMapper;
import com.owl.wifi.web.entity.TalkingdataMore;
import com.owl.wifi.web.entity.User;
import com.owl.wifi.web.entity.UserShopKey;
import com.owl.wifi.web.talkingdata.model.SearchModel;
import com.owl.wifi.web.talkingdata.service.ReportService;
import com.owl.wifi.web.wifi.model.TalkingDataModel;

@Service
public class ReportServiceImpl implements ReportService {

        @Autowired
        private UserShopMapper ushopMapper;

        @Autowired
        private TalkingdataMoreMapper tdataMapper;

        @Autowired
        private VisitShopMapper vsMapper;

        @Autowired
        private TalkingdataMoreMapper talkingdataMoreMapper;

        @Autowired
        private ShopprobeMapper deveceMapper;

        public List<Map<String, Object>> usershopList(Integer userId) {
                return ushopMapper.shopListByUser(userId);
        }

        public Map<String, Long> gameDataList(Integer shopId, Date startDate, Date endDate, String label) {
                return tdataMapper.gameReportByLabel(shopId, startDate, endDate, label);
        }

        public Map<String, Long> klByDate(Integer shopId, Date myDate, String startTime, String endTime) {
                return vsMapper.klByDate(shopId, myDate, startTime, endTime);
        }

        public List<Map<String, Object>> timeKlByDate(Integer shopId, Date myDate) {
                return vsMapper.timeKlByDate(shopId, myDate);
        }

        public Map<String, BigDecimal> stayTimeByDate(Integer shopId, Date myDate) {
                return vsMapper.stayTimeByDate(shopId, myDate);
        }

        public Map<String, Long> stayTimeKL(Integer shopId, Date myDate, Integer startMinute, Integer endMinute) {
                return vsMapper.stayTimeKL(shopId, myDate, startMinute, endMinute);
        }

        @Override
        public Result getOwlDataUser(HttpServletRequest request, SearchModel searchModel) {
                Result result = new Result();
                User sessionUser = getSessionUser(request);

                if (Help.isNull(sessionUser)) {
                        result.setStatus(Status.user_not_exist_error_status);
                        result.setInfo(Status.user_not_exist_error_info);
                        return result;
                }

                Integer shopId = null;
                String startTime = "";
                String endTime = "";
                Map<String, Object> param = new HashMap<String, Object>();
                if (Help.isNull(searchModel.getShopId())) {
                        param.put("userId", sessionUser.getUserId());
                        List<UserShopKey> findByParam = ushopMapper.findByParam(param);
                        shopId = findByParam.get(0).getShopId();
                } else {
                        shopId = searchModel.getShopId();
                        startTime = searchModel.getStartTime();
                        endTime = searchModel.getEndTime();

                }

                param.put("shopId", shopId);
                param.put("startDate", startTime);
                param.put("endDate", endTime);
                int countFlowUser = vsMapper.countFlowUser(param);
                param.put("label", FinVal.male);
                int countmale = vsMapper.countFlowUserLabel(param);

                param.put("label", FinVal.female);
                int countfemale = vsMapper.countFlowUserLabel(param);

                param.put("label", FinVal.ageA);
                int countageA = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.ageB);
                int countageB = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.ageC);
                int countageC = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.ageD);
                int countageD = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.ageE);
                int countageE = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.ageF);
                int countageF = vsMapper.countFlowUserLabel(param);

                param.put("label", FinVal.married);
                int countmarried = vsMapper.countFlowUserLabel(param);

                param.put("label", FinVal.havecar);
                int counthavecar = vsMapper.countFlowUserLabel(param);

                param.put("label", FinVal.fathermathor);
                int countfathermathor = vsMapper.countFlowUserLabel(param);

                param.put("label", FinVal.havebaby);
                int counthavebaby = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.firstbaby);
                int countfirstbaby = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.secondbaby);
                int countsecondbaby = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.doctor);
                int countdoctor = vsMapper.countFlowUserLabel(param);
                param.put("label", FinVal.student);
                int countstudent = vsMapper.countFlowUserLabel(param);

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("countFlowUser", countFlowUser);
                data.put("countmale", countmale);
                data.put("countfemale", countfemale);
                data.put("countageA", countageA);
                data.put("countageB", countageB);
                data.put("countageC", countageC);
                data.put("countageD", countageD);
                data.put("countageE", countageE);
                data.put("countageF", countageF);
                data.put("countmarried", countmarried);
                data.put("counthavecar", counthavecar);
                data.put("countfathermathor", countfathermathor);
                data.put("counthavebaby", counthavebaby);
                data.put("countfirstbaby", countfirstbaby);
                data.put("countsecondbaby", countsecondbaby);
                data.put("countdoctor", countdoctor);
                data.put("countstudent", countstudent);
                result.setData(data);
                result.setStatus(Status.success_status);
                result.setInfo(Status.success_info);
                return result;
        }

        private static User getSessionUser(HttpServletRequest request) {
                return (User) request.getSession().getAttribute("user");
        }

        public Map<String, BigDecimal> avgKlByEveryday(Integer shopId) {
                return vsMapper.avgKlByEveryday(shopId);
        }

        public List<Map<String, Object>> avgTimeKl(Integer shopId) {
                return vsMapper.avgTimeKl(shopId);
        }

        public Map<String, Long> repeatCustomer(Integer shopId, Date myDate) {
                return vsMapper.repeatCustomer(shopId, myDate);
        }

        @Override
        public Result getOneUserAllData(HttpServletRequest request, TalkingDataModel talkingDataModel) {
                Result result = new Result();

                if (Help.isNull(talkingDataModel)) {
                        result.setInfo(Status.device_id_error_info);
                        result.setStatus(Status.device_id_error_status);
                        return result;
                }

                // 应用兴趣标签查询数据
                String mac = talkingDataModel.getId();

                Map<String, Object> param = new HashMap<String, Object>();
                param.put("mac", mac.replace(":", ""));
                param.put("portid", 4);
                List<TalkingdataMore> gameTag = talkingdataMoreMapper.selectByMACgroupByPort(param);

                param.put("portid", 5);
                List<TalkingdataMore> consumeTag = talkingdataMoreMapper.selectByMACgroupByPort(param);

                param.put("portid", 6);
                List<TalkingdataMore> demographicTag = talkingdataMoreMapper.selectByMACgroupByPort(param);

                param.put("portid", 7);
                List<TalkingdataMore> appTag = talkingdataMoreMapper.selectByMACgroupByPort(param);

                param.put("portid", 8);
                List<TalkingdataMore> financeTag = talkingdataMoreMapper.selectByMACgroupByPort(param);

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("appTag", appTag);
                data.put("consumeTag", consumeTag);
                data.put("demographicTag", demographicTag);
                data.put("financeTag", financeTag);
                data.put("gameTag", gameTag);

                result.setStatus(Status.success_status);
                result.setInfo(Status.success_info);
                result.setData(data);
                return result;
        }

        public List<Map<String, Object>> shopCustomerList(Integer shopId, Date startDate, Date endDate, int startRow,
                        int endRow) {
                return vsMapper.shopMacList(shopId, startDate, endDate, startRow, endRow);
        }

        public List<Map<String, Object>> getShopCustomerList(Integer shopId, Date myDate) {

                return vsMapper.getShopMacList(shopId, myDate);

        }

        public long count_shopMacList(Integer shopId, Date startDate, Date endDate) {
                return vsMapper.count_shopMacList(shopId, startDate, endDate);
        }

        @Override
        public long getTotalCustomerAmount(Integer shopId, Date myDate) {
                return vsMapper.getTotalCustomerAmount(shopId, myDate);
        }

        @Override
        public long getTodayNewCustomerAmount(Integer shoId, Date myDate) {
                return vsMapper.getTotalCustomerAmount(shoId, myDate)
                                - vsMapper.getTodayBeforeCustomerAmount(shoId, myDate);
        }

        @Override
        public long getRepeatKL(Integer shopId1, Integer shopId2, Date date) {
                return vsMapper.getRepeatKL(shopId1, shopId2, date);
        }

        public List<Map<String, Object>> deviceStatus(Integer userId) {
                return deveceMapper.deviceStatus(userId);
        }
}
