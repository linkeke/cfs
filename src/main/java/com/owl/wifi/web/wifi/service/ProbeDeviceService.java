package com.owl.wifi.web.wifi.service;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import com.google.gson.JsonArray;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.wifi.model.ProbeDeviceModel;

public interface ProbeDeviceService {
	 
	public Result saveProbeDevice(JsonArray  json) throws ParseException;
	
	public List<ProbeDeviceModel> findLeaveMac(int interval);
	
	public Collection<ProbeDeviceModel> getShopMacList(int shopId);
}
