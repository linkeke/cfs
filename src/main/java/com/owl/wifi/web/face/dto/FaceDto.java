package com.owl.wifi.web.face.dto;

import java.util.Date;
import java.util.List;

public class FaceDto {

    private Long faceId;
    private Integer maleInd;
    private Integer age;
    private Integer race;
    private Integer lips;
    private Integer glass;
	private List<String> images;
    
    private String visitdate;
    
    public Long getFaceId() {
		return faceId;
	}
	public void setFaceId(Long faceId) {
		this.faceId = faceId;
	}
	public Integer getMaleInd() {
		return maleInd;
	}
	public void setMaleInd(Integer maleInd) {
		this.maleInd = maleInd;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getRace() {
		return race;
	}
	public void setRace(Integer race) {
		this.race = race;
	}
	public Integer getLips() {
		return lips;
	}
	public void setLips(Integer lips) {
		this.lips = lips;
	}
	public Integer getGlass() {
		return glass;
	}
	public void setGlass(Integer glass) {
		this.glass = glass;
	}
	
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getVisitdate() {
		return visitdate;
	}
	public void setVisitdate(String visitdate) {
		this.visitdate = visitdate;
	}
	
}
