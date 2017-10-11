package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.FaceRaceCode;

public interface FaceRaceCodeMapper {
        int deleteByPrimaryKey(Short raceId);

        int insert(FaceRaceCode record);

        FaceRaceCode selectByPrimaryKey(Short raceId);

        int updateByPrimaryKeySelective(FaceRaceCode record);

        int updateByPrimaryKey(FaceRaceCode record);
}