package com.owl.wifi.web.wifi.model;

import java.io.Serializable;

public class ProbeDeviceModel implements Serializable{
	private String id ;
	private String mac;
	private long stime;
	private long etime;
	private String dmac;
	private int shopId ;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public long getStime() {
		return stime;
	}
	public void setStime(long stime) {
		this.stime = stime;
	}
	public long getEtime() {
		return etime;
	}
	public void setEtime(long etime) {
		this.etime = etime;
	}
	public String getDmac() {
		return dmac;
	}
	public void setDmac(String dmac) {
		this.dmac = dmac;
	}

	@Override
	public String toString() {
		return "ProbeDeviceModel [id=" + id + ", mac=" + mac + ", stime=" + stime + ", etime=" + etime + ", dmac="
				+ dmac + ", shopId=" + shopId + "]";
	}
	
}
