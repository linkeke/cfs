package com.owl.wifi.web.entity;

import java.util.Date;

public class TalkingdataQuery extends TalkingdataQueryKey {
    private Date queryDatetime; //查询接口时间

    public Date getQueryDatetime() {
        return queryDatetime;
    }

    public void setQueryDatetime(Date queryDatetime) {
        this.queryDatetime = queryDatetime;
    }
}