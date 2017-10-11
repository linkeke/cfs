package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.Owldata;


public interface OwldataMapper {
    int deleteByPrimaryKey(String mac);

    int insert(Owldata record);

    int insertSelective(Owldata record);

    Owldata selectByPrimaryKey(String mac);

    int updateByPrimaryKeySelective(Owldata record);

    int updateByPrimaryKey(Owldata record);
}