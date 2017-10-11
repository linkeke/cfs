package com.owl.wifi.web.entity;

import java.util.Date;

public class Permission {
    private Long nPermissionId;

    private String cPermissionName;

    private String cPermissionUrl;

    private Date tCreateTime;

    private Date tUpdateTime;

    private Long nCreateBy;

    private Long nUpdateBy;

    private Boolean nStatus;

    public Long getnPermissionId() {
        return nPermissionId;
    }

    public void setnPermissionId(Long nPermissionId) {
        this.nPermissionId = nPermissionId;
    }

    public String getcPermissionName() {
        return cPermissionName;
    }

    public void setcPermissionName(String cPermissionName) {
        this.cPermissionName = cPermissionName == null ? null : cPermissionName.trim();
    }

    public String getcPermissionUrl() {
        return cPermissionUrl;
    }

    public void setcPermissionUrl(String cPermissionUrl) {
        this.cPermissionUrl = cPermissionUrl == null ? null : cPermissionUrl.trim();
    }

    public Date gettCreateTime() {
        return tCreateTime;
    }

    public void settCreateTime(Date tCreateTime) {
        this.tCreateTime = tCreateTime;
    }

    public Date gettUpdateTime() {
        return tUpdateTime;
    }

    public void settUpdateTime(Date tUpdateTime) {
        this.tUpdateTime = tUpdateTime;
    }

    public Long getnCreateBy() {
        return nCreateBy;
    }

    public void setnCreateBy(Long nCreateBy) {
        this.nCreateBy = nCreateBy;
    }

    public Long getnUpdateBy() {
        return nUpdateBy;
    }

    public void setnUpdateBy(Long nUpdateBy) {
        this.nUpdateBy = nUpdateBy;
    }

    public Boolean getnStatus() {
        return nStatus;
    }

    public void setnStatus(Boolean nStatus) {
        this.nStatus = nStatus;
    }
}