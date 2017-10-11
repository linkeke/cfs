package com.owl.wifi.web.entity;

public class MenuPermission {
    private Long nMenuPermissionId;

    private Long nMenuId;

    private Long nPermissionId;

    public Long getnMenuPermissionId() {
        return nMenuPermissionId;
    }

    public void setnMenuPermissionId(Long nMenuPermissionId) {
        this.nMenuPermissionId = nMenuPermissionId;
    }

    public Long getnMenuId() {
        return nMenuId;
    }

    public void setnMenuId(Long nMenuId) {
        this.nMenuId = nMenuId;
    }

    public Long getnPermissionId() {
        return nPermissionId;
    }

    public void setnPermissionId(Long nPermissionId) {
        this.nPermissionId = nPermissionId;
    }
}