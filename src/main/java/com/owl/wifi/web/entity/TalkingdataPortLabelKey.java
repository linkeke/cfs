package com.owl.wifi.web.entity;

public class TalkingdataPortLabelKey {
    private String label;

    private Short portId;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public Short getPortId() {
        return portId;
    }

    public void setPortId(Short portId) {
        this.portId = portId;
    }
}