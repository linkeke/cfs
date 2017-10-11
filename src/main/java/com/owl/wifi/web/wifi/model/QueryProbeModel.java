package com.owl.wifi.web.wifi.model;

import java.util.Date;

public class QueryProbeModel {
	private String dmac;
	private Date stime;
	private Date etime;
	public String getDmac() {
		return dmac;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public void setDmac(String dmac) {
		this.dmac = dmac;
	}
	
	
	
}
