package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.TalkingdataPortLabelKey;

public interface TalkingdataPortLabelMapper {
    int deleteByPrimaryKey(TalkingdataPortLabelKey key);

    int insert(TalkingdataPortLabelKey record);

    int insertSelective(TalkingdataPortLabelKey record);
}