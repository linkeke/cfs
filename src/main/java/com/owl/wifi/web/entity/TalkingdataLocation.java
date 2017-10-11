package com.owl.wifi.web.entity;

public class TalkingdataLocation {
    private String mac; //MAC地址

    private String tdid; //权重，值越大可信度越大

    private String latlng; //位置经纬度

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getTdid() {
        return tdid;
    }

    public void setTdid(String tdid) {
        this.tdid = tdid == null ? null : tdid.trim();
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng == null ? null : latlng.trim();
    }
}