package com.owl.wifi.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import com.owl.wifi.web.entity.Shopprobe;

public interface ShopprobeMapper {
        int deleteByPrimaryKey(Integer shopProbeId);

        int insert(Shopprobe record);

        int insertSelective(Shopprobe record);

        Shopprobe selectByPrimaryKey(Integer shopProbeId);

        int updateByPrimaryKeySelective(Shopprobe record);

        int updateByPrimaryKey(Shopprobe record);

        /**
         * 根据设备的mac地址查询设备所属门店
         * 
         * @param mac
         * @return
         */
        @Cacheable("shopDevices")
        Shopprobe findShopShopIdByDevice(String mac);

        List<Shopprobe> findByParam(Map<String, Object> param);

        int countByParam(Map<String, Object> param);

        Shopprobe selectByProbemac(String probemac);

        List<Map<String, Object>> deviceStatus(@Param("userId") Integer userId);
}