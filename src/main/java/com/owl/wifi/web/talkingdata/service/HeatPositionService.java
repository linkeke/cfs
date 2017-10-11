package com.owl.wifi.web.talkingdata.service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/19.
 */
public interface HeatPositionService {
    List<Map<String,Object>> getLngLatCount(Integer shopId);
}
