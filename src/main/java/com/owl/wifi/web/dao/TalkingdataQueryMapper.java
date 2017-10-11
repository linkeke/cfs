package com.owl.wifi.web.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.owl.wifi.web.entity.TalkingdataQuery;
import com.owl.wifi.web.entity.TalkingdataQueryKey;


public interface TalkingdataQueryMapper {
    int deleteByPrimaryKey(TalkingdataQueryKey key);

    int insert(TalkingdataQuery record);

    int insertSelective(TalkingdataQuery record);

    TalkingdataQuery selectByPrimaryKey(TalkingdataQueryKey key);

    int updateByPrimaryKeySelective(TalkingdataQuery record);

    int updateByPrimaryKey(TalkingdataQuery record);
    
    @Cacheable("talkingdataQuery")
    List<TalkingdataQuery> findTalkingdataQueryByMac(String mac);
}