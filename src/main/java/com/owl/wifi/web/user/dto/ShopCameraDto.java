package com.owl.wifi.web.user.dto;

import java.util.Date;

/**
 * Created by admin on 2017/7/7.
 */
public class ShopCameraDto {

    private Integer shopCameraId;
    private Integer shopId;
    private String cameraMac;//设备mac
    private String shopName;//门店名称
    private Date installDate;
    private String handwareVersion;
    private String softwareVersion;
    private Byte activeInd;

    public String getHandwareVersion() {
        return handwareVersion;
    }

    public void setHandwareVersion(String handwareVersion) {
        this.handwareVersion = handwareVersion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public Integer getShopCameraId() {
        return shopCameraId;
    }

    public void setShopCameraId(Integer shopCameraId) {
        this.shopCameraId = shopCameraId;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCameraMac() {
        return cameraMac;
    }

    public void setCameraMac(String cameraMac) {
        this.cameraMac = cameraMac;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Byte getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Byte activeInd) {
        this.activeInd = activeInd;
    }
}
