package com.owl.wifi.web.talkingdata.service.impl;

import com.owl.wifi.web.dao.TalkingdataPositionMapper;
import com.owl.wifi.web.talkingdata.service.HeatPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/19.
 */
@Service
public class HeatPositionServiceImpl implements HeatPositionService {
    @Autowired
    TalkingdataPositionMapper talkingdataPositionMapper;
    @Override
    public List<Map<String, Object>> getLngLatCount(Integer shopId) {
        return talkingdataPositionMapper.getLngLatCount(shopId);
    }
}
