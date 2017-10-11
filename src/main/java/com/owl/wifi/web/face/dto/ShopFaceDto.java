package com.owl.wifi.web.face.dto;

import java.util.Date;
import java.util.List;

public class ShopFaceDto {
	private Long faceId;
	private List<String> images;
    private String shopName;
    private String visitdate;
    private String imgone;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getVisitdate() {
		return visitdate;
	}
	public void setVisitdate(String visitdate) {
		this.visitdate = visitdate;
	}
	public Long getFaceId() {
		return faceId;
	}
	public void setFaceId(Long faceId) {
		this.faceId = faceId;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getImgone() {
		return imgone;
	}
	public void setImgone(String imgone) {
		this.imgone = imgone;
	}
	
	
	
}
