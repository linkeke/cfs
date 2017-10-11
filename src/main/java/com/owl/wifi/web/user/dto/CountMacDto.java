package com.owl.wifi.web.user.dto;

public class CountMacDto {
	private int macTotal;
	private int macMonCount;
	private int macWeekCount;
	private int macDayCount;
	private int todayCount;
	public int getMacTotal() {
		return macTotal;
	}
	public void setMacTotal(int macTotal) {
		this.macTotal = macTotal;
	}
	public int getMacMonCount() {
		return macMonCount;
	}
	public void setMacMonCount(int macMonCount) {
		this.macMonCount = macMonCount;
	}
	public int getMacWeekCount() {
		return macWeekCount;
	}
	public void setMacWeekCount(int macWeekCount) {
		this.macWeekCount = macWeekCount;
	}
	public int getMacDayCount() {
		return macDayCount;
	}
	public void setMacDayCount(int macDayCount) {
		this.macDayCount = macDayCount;
	}
	public int getTodayCount() {
		return todayCount;
	}
	public void setTodayCount(int todayCount) {
		this.todayCount = todayCount;
	}

   
}
