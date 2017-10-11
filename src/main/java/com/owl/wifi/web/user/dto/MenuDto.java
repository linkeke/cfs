package com.owl.wifi.web.user.dto;

import java.util.List;

public class MenuDto {
  private String menuName;
  private String menuUrl;
  private String menuIcon;
  private List<MenuDto> subMenus;
public String getMenuName() {
	return menuName;
}
public void setMenuName(String menuName) {
	this.menuName = menuName;
}
public String getMenuUrl() {
	return menuUrl;
}
public void setMenuUrl(String menuUrl) {
	this.menuUrl = menuUrl;
}
public String getMenuIcon() {
	return menuIcon;
}
public void setMenuIcon(String menuIcon) {
	this.menuIcon = menuIcon;
}
public List<MenuDto> getSubMenus() {
	return subMenus;
}
public void setSubMenus(List<MenuDto> subMenus) {
	this.subMenus = subMenus;
}
  
  
}
