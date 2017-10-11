package com.owl.wifi.web.entity;

public class TalkingdataMoreKey {
    private String label; //标签名

    private String mac; //mac地址

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }
}