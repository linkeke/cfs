package com.owl.wifi.web.wifi.service;

import java.text.ParseException;

import com.owl.wifi.util.Result;
import com.owl.wifi.web.wifi.model.WifiDeviceModel;


public interface WifiDeviceService {
	 
	public Result saveWifiDevice(WifiDeviceModel wifiDeviceModel) throws ParseException;
	
	 
}
