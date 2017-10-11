package com.owl.wifi.web.user.dto;

import java.util.List;

public class UserListDto {
 private Integer userId;
 private String userAccount;
 private String userName;
 private List<GroupListDto> groupList;
 private List<UserShopListDto> UserShopList;
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public String getUserAccount() {
	return userAccount;
}
public void setUserAccount(String userAccount) {
	this.userAccount = userAccount;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public List<GroupListDto> getGroupList() {
	return groupList;
}
public void setGroupList(List<GroupListDto> groupList) {
	this.groupList = groupList;
}
public List<UserShopListDto> getUserShopList() {
	return UserShopList;
}
public void setUserShopList(List<UserShopListDto> userShopList) {
	UserShopList = userShopList;
}
 
}
