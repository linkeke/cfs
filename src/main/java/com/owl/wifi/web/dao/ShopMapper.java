package com.owl.wifi.web.dao;

import java.util.List;
import java.util.Map;

import com.owl.wifi.web.entity.Shop;

public interface ShopMapper {
    int deleteByPrimaryKey(Integer shopId);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer shopId);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
    
    List<Shop> findByParam(Map<String,Object> param);
    int countByParam(Map<String,Object> param);
    
}