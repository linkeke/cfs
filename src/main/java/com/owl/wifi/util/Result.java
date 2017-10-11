package com.owl.wifi.util;

import java.util.HashMap;

public class Result {
	private int status = 99999;
	private String info = "操作失败";
	private Object data = new HashMap<String, Object>();
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public void setInfo(String info){
		this.info = info;
	}
	
	public void setData(Object data){
		if(data == null){
			return;
		}
		this.data = data;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public String getInfo(){
		return this.info;
	}
	
	public Object getData(){
		return this.data;
	}
	
}
