package com.owl.wifi.web.user.model;

public class ShopModel {
	private Integer shopId;
   private String shopName;
   private Integer industryId; // 行业ID
   private Integer businessStarttime;
   private Integer businessEndtime;
   private String contact; // 联系人
   private String tel; // 联系电话
   private String address; // 详细地址
   private String latlng;
   
   private Integer talkingdataFlag;

	public String getShopName() {
		return shopName;
	}
	
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}

	public Integer getBusinessStarttime() {
		return businessStarttime;
	}

	public void setBusinessStarttime(Integer businessStarttime) {
		this.businessStarttime = businessStarttime;
	}

	public Integer getBusinessEndtime() {
		return businessEndtime;
	}

	public void setBusinessEndtime(Integer businessEndtime) {
		this.businessEndtime = businessEndtime;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatlng() {
		return latlng;
	}

	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}

	public Integer getTalkingdataFlag() {
		return talkingdataFlag;
	}

	public void setTalkingdataFlag(Integer talkingdataFlag) {
		this.talkingdataFlag = talkingdataFlag;
	}


}
