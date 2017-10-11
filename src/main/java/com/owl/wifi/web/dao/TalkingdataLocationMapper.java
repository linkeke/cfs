package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.TalkingdataLocation;


public interface TalkingdataLocationMapper {
    int deleteByPrimaryKey(String mac);

    int insert(TalkingdataLocation record);

    int insertSelective(TalkingdataLocation record);

    TalkingdataLocation selectByPrimaryKey(String mac);

    int updateByPrimaryKeySelective(TalkingdataLocation record);

    int updateByPrimaryKey(TalkingdataLocation record);
}