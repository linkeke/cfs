package com.owl.wifi.web.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import com.owl.wifi.web.entity.VisitShop;
import com.owl.wifi.web.entity.VisitShopKey;

public interface VisitShopMapper {
        int deleteByPrimaryKey(VisitShopKey key);

        int insert(VisitShop record);

        int batchInsert(List<VisitShop> list);

        int insertSelective(VisitShop record);

        VisitShop selectByPrimaryKey(VisitShopKey key);

        int updateByPrimaryKeySelective(VisitShop record);

        int updateByPrimaryKey(VisitShop record);

        Map<String, Long> klByDate(@Param("shopId") Integer shopId, @Param("myDate") Date myDate,
                        @Param("startTime") String startTime, @Param("endTime") String endTime);

        List<Map<String, Object>> timeKlByDate(@Param("shopId") Integer shopId, @Param("myDate") Date myDate);

        Map<String, BigDecimal> stayTimeByDate(@Param("shopId") Integer shopId, @Param("myDate") Date myDate);

        Map<String, Long> stayTimeKL(@Param("shopId") Integer shopId, @Param("myDate") Date myDate,
                        @Param("startMinute") Integer startMinute, @Param("endMinute") Integer endMinute);

        List<VisitShop> findByParam(Map<String, Object> param);

        int countByParam(Map<String, Object> param);

        Map<String, BigDecimal> avgKlByEveryday(@Param("shopId") Integer shopId);

        List<Map<String, Object>> avgTimeKl(@Param("shopId") Integer shopId);

        Map<String, Long> repeatCustomer(@Param("shopId") Integer shopId, @Param("myDate") Date myDate);

        @Cacheable("countFlowUser")
        int countFlowUser(Map<String, Object> param);

        @Cacheable("countFlowUserLabel")
        int countFlowUserLabel(Map<String, Object> param);

        List<Map<String, Object>> shopMacList(@Param("shopId") Integer shopId, @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate, @Param("startRow") int startRow, @Param("endRow") int endRow);

        List<Map<String, Object>> getShopMacList(@Param("shopId") Integer shopId, @Param("myDate") Date myDate);

        long count_shopMacList(@Param("shopId") Integer shopId, @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

        long getCurrentCustomerAmount(@Param("shopId") Integer shopId, @Param("myDate") Date myDate);

        long getTotalCustomerAmount(@Param("shopId") Integer shopId, @Param("myDate") Date myDate);

        long getTodayBeforeCustomerAmount(@Param("shopId") Integer shopId, @Param("myDate") Date myDate);

        long getRepeatKL(@Param("shopId1") Integer shopId1,@Param("shopId2") Integer shopId2, @Param("date") Date date);
}