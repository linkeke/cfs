package com.owl.wifi.web.entity;

import java.util.Date;

public class Role {
    private Long nRoleId;
    
    private Long nRoleCode;

    private String cRoleName;
    
    private String cRoleDesc;

    private Integer nSystemRole;

    private Date tCreateTime;

    private Date tUpdateTime;

    private Integer nStatus;

    private Long nCreateBy;

    private Long nUpdateBy;

    public Long getnRoleId() {
        return nRoleId;
    }

    public void setnRoleId(Long nRoleId) {
        this.nRoleId = nRoleId;
    }

    public String getcRoleName() {
        return cRoleName;
    }

    public void setcRoleName(String cRoleName) {
        this.cRoleName = cRoleName == null ? null : cRoleName.trim();
    }

    public String getcRoleDesc() {
		return cRoleDesc;
	}

	public void setcRoleDesc(String cRoleDesc) {
		this.cRoleDesc = cRoleDesc;
	}

	public Long getnRoleCode() {
		return nRoleCode;
	}

	public void setnRoleCode(Long nRoleCode) {
		this.nRoleCode = nRoleCode;
	}

	public Integer getnSystemRole() {
        return nSystemRole;
    }

    public void setnSystemRole(Integer nSystemRole) {
        this.nSystemRole = nSystemRole;
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

    public Integer getnStatus() {
        return nStatus;
    }

    public void setnStatus(Integer nStatus) {
        this.nStatus = nStatus;
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