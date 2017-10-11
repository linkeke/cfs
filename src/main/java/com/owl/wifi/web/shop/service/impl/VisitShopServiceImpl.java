package com.owl.wifi.web.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.wifi.web.dao.VisitShopMapper;
import com.owl.wifi.web.entity.VisitShop;
import com.owl.wifi.web.shop.service.VisitShopService;
import com.owl.wifi.web.wifi.model.ProbeDeviceModel;

@Service
public class VisitShopServiceImpl implements VisitShopService {

	@Autowired
	VisitShopMapper visitShopMapper ;
	@Override
	public void addVisitShop(List<ProbeDeviceModel> list) {
		List<VisitShop> shops = new ArrayList<VisitShop>(list.size());
		for(ProbeDeviceModel model : list){
			VisitShop visitShop = convert(model);
			shops.add(visitShop);
		}		
		visitShopMapper.batchInsert(shops);
	}



    public VisitShop convert(ProbeDeviceModel model){
		VisitShop vshop = new VisitShop();
		vshop.setShopId(model.getShopId());
		vshop.setCustomerMac(model.getMac());
		Date date = new Date(model.getStime() * 1000);
		vshop.setVisitDate(date);
		vshop.setStartTime(date);
		vshop.setEndTime(new Date(model.getEtime()*1000));
		return vshop ;
	}
}
