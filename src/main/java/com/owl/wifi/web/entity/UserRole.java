package com.owl.wifi.web.entity;

public class UserRole {
    private Long nUserRoleId;

    private Long nUserId;

    private Integer nRoleCode;

    public Long getnUserRoleId() {
        return nUserRoleId;
    }

    public void setnUserRoleId(Long nUserRoleId) {
        this.nUserRoleId = nUserRoleId;
    }

    public Long getnUserId() {
        return nUserId;
    }

    public void setnUserId(Long nUserId) {
        this.nUserId = nUserId;
    }

	public Integer getnRoleCode() {
		return nRoleCode;
	}

	public void setnRoleCode(Integer nRoleCode) {
		this.nRoleCode = nRoleCode;
	}

}