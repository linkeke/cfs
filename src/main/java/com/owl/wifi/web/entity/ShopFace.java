package com.owl.wifi.web.entity;

import java.util.Date;

public class ShopFace extends ShopFaceKey {
    private Integer shopCameraId;

    private Date visitTime;

    private Integer faceId;

    public Integer getShopCameraId() {
        return shopCameraId;
    }

    public void setShopCameraId(Integer shopCameraId) {
        this.shopCameraId = shopCameraId;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Integer getFaceId() {
        return faceId;
    }

    public void setFaceId(Integer faceId) {
        this.faceId = faceId;
    }
}