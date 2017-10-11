package com.owl.wifi.web.user.model;

public class UserShopModel {
	private Integer userId;
	private String userName;
	private String password;
    private Integer shopId;
    private String roleCodes;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getRoleCodes() {
		return roleCodes;
	}
	public void setRoleCodes(String roleCodes) {
		this.roleCodes = roleCodes;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
