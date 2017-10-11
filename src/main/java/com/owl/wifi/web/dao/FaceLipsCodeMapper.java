package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.FaceLipsCode;

public interface FaceLipsCodeMapper {
        int deleteByPrimaryKey(Short lipsId);

        int insert(FaceLipsCode record);

        FaceLipsCode selectByPrimaryKey(Short lipsId);

        int updateByPrimaryKeySelective(FaceLipsCode record);

        int updateByPrimaryKey(FaceLipsCode record);
}