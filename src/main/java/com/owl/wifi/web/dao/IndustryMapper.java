package com.owl.wifi.web.dao;

import java.util.List;
import java.util.Map;

import com.owl.wifi.web.entity.Industry;

public interface IndustryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Industry record);

    int insertSelective(Industry record);

    Industry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Industry record);

    int updateByPrimaryKey(Industry record);
    
    List<Industry> findByParam(Map<String,Object> param);
    int countByParam(Map<String,Object> param);
}