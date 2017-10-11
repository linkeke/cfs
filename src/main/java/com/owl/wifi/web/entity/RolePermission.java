package com.owl.wifi.web.entity;

public class RolePermission {
    private Long nRolePermission;

    private Long nRoleCode;

    private Long nPermissionId;

    public Long getnRolePermission() {
        return nRolePermission;
    }

    public void setnRolePermission(Long nRolePermission) {
        this.nRolePermission = nRolePermission;
    }

    public Long getnRoleCode() {
        return nRoleCode;
    }

    public void setnRoleCode(Long nRoleCode) {
        this.nRoleCode = nRoleCode;
    }

    public Long getnPermissionId() {
        return nPermissionId;
    }

    public void setnPermissionId(Long nPermissionId) {
        this.nPermissionId = nPermissionId;
    }
}