package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.ShopCamera;

import java.util.List;
import java.util.Map;

public interface ShopCameraMapper {
    int deleteByPrimaryKey(Integer shopCameraId);

    int insert(ShopCamera record);

    ShopCamera selectByPrimaryKey(Integer shopCameraId);

    int updateByPrimaryKeySelective(ShopCamera record);

    int updateByPrimaryKey(ShopCamera record);

    int countByParam(Map<String, Object> param);

    List<ShopCamera> findByParam(Map<String, Object> param);
}