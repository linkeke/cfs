package com.owl.wifi.web.user.dto;

public class UserShopDto {
   private Integer shopId;
   private String shopName;
   private Integer businessStartTime;
   private Integer businessEndTime;
   private String contact;
   private String tel;
   private String address;
   private String industryName;
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
   
}
