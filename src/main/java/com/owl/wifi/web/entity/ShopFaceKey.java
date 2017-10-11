package com.owl.wifi.web.entity;

import java.util.Date;

public class ShopFaceKey {
    private Date visitDate;

    private Integer visitId;

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