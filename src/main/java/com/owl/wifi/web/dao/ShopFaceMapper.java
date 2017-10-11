package com.owl.wifi.web.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.owl.wifi.web.entity.ShopFace;
import com.owl.wifi.web.entity.ShopFaceKey;

public interface ShopFaceMapper {
        int deleteByPrimaryKey(ShopFaceKey key);

        int insert(ShopFace record);

        ShopFace selectByPrimaryKey(ShopFaceKey key);

        int updateByPrimaryKeySelective(ShopFace record);

        int updateByPrimaryKey(ShopFace record);

        long count_shopFaceList(@Param("shopId") Integer shopId, @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);

        List<Map<String, Object>> shopFaceList(@Param("shopId") Integer shopId, @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate, @Param("startRow") int startRow,
                        @Param("pageSize") int pageSize);
        
        List<ShopFace> findByParam(Map<String,Object> param);
        int countByParam(Map<String,Object> param);
}