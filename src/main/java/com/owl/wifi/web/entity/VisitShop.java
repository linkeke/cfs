package com.owl.wifi.web.entity;

import java.util.Date;

public class VisitShop extends VisitShopKey {
    private String customerMac; //用户mac

    private Date startTime; //进入时间(format hh:mm:ss)

    private Date endTime; //出去时间(format hh:mm:ss)
    
    private int shopId ;

    public String getCustomerMac() {
        return customerMac;
    }

    public void setCustomerMac(String customerMac) {
        this.customerMac = customerMac == null ? null : customerMac.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
        
}