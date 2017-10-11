package com.owl.wifi.web.entity;

public class TalkingdataQueryKey {
    private String mac; //mac地址

    private Short portId; //接口名

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public Short getPortId() {
        return portId;
    }

    public void setPortId(Short portId) {
        this.portId = portId;
    }
}