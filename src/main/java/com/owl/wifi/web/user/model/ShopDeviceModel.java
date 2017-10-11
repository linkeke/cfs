package com.owl.wifi.web.user.model;

public class ShopDeviceModel {
	private Integer shopprobeId;
	private String probesn; //设备序列号
	private String probemac; //设备mac
    private Integer shopId; //门店ID
    
	public Integer getShopprobeId() {
		return shopprobeId;
	}
	public void setShopprobeId(Integer shopprobeId) {
		this.shopprobeId = shopprobeId;
	}
	public String getProbesn() {
		return probesn;
	}
	public void setProbesn(String probesn) {
		this.probesn = probesn;
	}
	public String getProbemac() {
		return probemac;
	}
	public void setProbemac(String probemac) {
		this.probemac = probemac;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
}
