package com.owl.wifi.web.entity;

public class TalkingdataMore extends TalkingdataMoreKey {
    private String name; //标签名

    private String weight; //权重，值越大可信度越大

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }
}