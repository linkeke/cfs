package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.TalkingdataPosition;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/13.
 */
public interface TalkingdataPositionMapper {
    int insert(TalkingdataPosition talkingdataPosition);
    List<Map<String,Object>> getLngLatCount(Integer shopId);
}
