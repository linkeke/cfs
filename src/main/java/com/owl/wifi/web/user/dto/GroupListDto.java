package com.owl.wifi.web.user.dto;

public class GroupListDto {
  private Long groupId;
  private Long groupCode;
  private String groupName;
  private String groupDesc;
public Long getGroupId() {
	return groupId;
}
public void setGroupId(Long groupId) {
	this.groupId = groupId;
}
public String getGroupName() {
	return groupName;
}
public void setGroupName(String groupName) {
	this.groupName = groupName;
}
public String getGroupDesc() {
	return groupDesc;
}
public void setGroupDesc(String groupDesc) {
	this.groupDesc = groupDesc;
}
public Long getGroupCode() {
	return groupCode;
}
public void setGroupCode(Long groupCode) {
	this.groupCode = groupCode;
}
  
}
