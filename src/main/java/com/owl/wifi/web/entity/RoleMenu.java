package com.owl.wifi.web.entity;

import java.util.Date;

public class RoleMenu {
    private Long nRoleMenuId;

    private Long nRoleCode;

    private Long nMenuId;

    private Integer nStatus;

    private Date tCreateTime;

    private Date tUpdateTime;

    private Long nCreateBy;

    private Long nUpdateBy;

    public Long getnRoleMenuId() {
        return nRoleMenuId;
    }

    public void setnRoleMenuId(Long nRoleMenuId) {
        this.nRoleMenuId = nRoleMenuId;
    }

    public Long getnRoleCode() {
        return nRoleCode;
    }

    public void setnRoleCode(Long nRoleCode) {
        this.nRoleCode = nRoleCode;
    }

    public Long getnMenuId() {
        return nMenuId;
    }

    public void setnMenuId(Long nMenuId) {
        this.nMenuId = nMenuId;
    }

    public Integer getnStatus() {
        return nStatus;
    }

    public void setnStatus(Integer nStatus) {
        this.nStatus = nStatus;
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
}