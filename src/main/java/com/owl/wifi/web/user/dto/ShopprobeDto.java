package com.owl.wifi.web.user.dto;

public class ShopprobeDto {
   private Integer shopprobeId;
   private Integer shopId;
   private String probeName;//设备名称
   private String probeMac;//设备mac
   private String probeSn; //设备序列号
   private String shopName;//门店名称
   
	public Integer getShopprobeId() {
		return shopprobeId;
	}
	public void setShopprobeId(Integer shopprobeId) {
		this.shopprobeId = shopprobeId;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getProbeName() {
		return probeName;
	}
	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
	public String getProbeMac() {
		return probeMac;
	}
	public void setProbeMac(String probeMac) {
		this.probeMac = probeMac;
	}
	public String getProbeSn() {
		return probeSn;
	}
	public void setProbeSn(String probeSn) {
		this.probeSn = probeSn;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
   
   
}
