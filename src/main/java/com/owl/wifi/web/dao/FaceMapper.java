package com.owl.wifi.web.dao;

import com.owl.wifi.web.entity.Face;

public interface FaceMapper {
        int deleteByPrimaryKey(Integer faceId);

        int insert(Face record);

        Face selectByPrimaryKey(Integer faceId);

        int updateByPrimaryKeySelective(Face record);

        int updateByPrimaryKey(Face record);
}