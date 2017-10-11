package com.owl.wifi.web.entity;

import java.math.BigDecimal;

public class Shop {
    private Integer shopId;

    private String nameTxt;

    private Integer industryId;

    private Integer businessStartTime;

    private Integer businessEndTime;

    private String contact;

    private String tel;

    private String address;

    private BigDecimal lat;

    private BigDecimal lng;
    
    private Integer talkingdataflag;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getNameTxt() {
        return nameTxt;
    }

    public void setNameTxt(String nameTxt) {
        this.nameTxt = nameTxt == null ? null : nameTxt.trim();
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public Integer getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(Integer businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public Integer getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(Integer businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

	public Integer getTalkingdataflag() {
		return talkingdataflag;
	}

	public void setTalkingdataflag(Integer talkingdataflag) {
		this.talkingdataflag = talkingdataflag;
	}
    
}