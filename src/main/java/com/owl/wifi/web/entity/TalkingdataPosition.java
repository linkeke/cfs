package com.owl.wifi.web.entity;

/**
 * Created by admin on 2017/6/13.
 */
public class TalkingdataPosition {
    private int id;

    private String mac;

    private Float lat;

    private Float lng;

    private int count;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
