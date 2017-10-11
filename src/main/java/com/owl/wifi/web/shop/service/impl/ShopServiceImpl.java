package com.owl.wifi.web.shop.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.owl.wifi.web.dao.ShopMapper;
import com.owl.wifi.web.entity.Shop;
import com.owl.wifi.web.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2017/6/19.
 */
@org.springframework.stereotype.Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopMapper shopMapper;

    @Override
    public Shop selectShopById(Integer shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }
}
