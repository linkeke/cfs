package com.owl.wifi.web.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.owl.wifi.web.entity.TalkingdataMore;
import com.owl.wifi.web.entity.TalkingdataMoreKey;

public interface TalkingdataMoreMapper {
        int deleteByPrimaryKey(TalkingdataMoreKey key);

        int insert(TalkingdataMore record);

        int insertSelective(TalkingdataMore record);

        TalkingdataMore selectByPrimaryKey(TalkingdataMoreKey key);

        int updateByPrimaryKeySelective(TalkingdataMore record);

        int updateByPrimaryKey(TalkingdataMore record);

        Map<String, Long> gameReportByLabel(@Param("shopId") Integer shopId, @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate, @Param("label") String label);

        List<TalkingdataMore> selectByMACgroupByPort(Map<String, Object> param);
}