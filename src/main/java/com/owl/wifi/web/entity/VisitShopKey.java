package com.owl.wifi.web.entity;

import java.util.Date;

public class VisitShopKey {
    private Date visitDate; //进入日期（yyyy-MM-dd）

    private Integer visitId; //自增ID

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }
}