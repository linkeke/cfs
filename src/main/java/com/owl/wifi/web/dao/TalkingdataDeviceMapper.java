package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.TalkingdataDevice;


public interface TalkingdataDeviceMapper {
    int deleteByPrimaryKey(String mac);

    int insert(TalkingdataDevice record);

    int insertSelective(TalkingdataDevice record);

    TalkingdataDevice selectByPrimaryKey(String mac);

    int updateByPrimaryKeySelective(TalkingdataDevice record);

    int updateByPrimaryKey(TalkingdataDevice record);
}