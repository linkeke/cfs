package com.owl.wifi.web.wifi.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owl.wifi.content.Status;
import com.owl.wifi.util.Help;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.dao.ShopprobeMapper;
import com.owl.wifi.web.entity.Shopprobe;
import com.owl.wifi.web.wifi.model.WifiDeviceModel;
import com.owl.wifi.web.wifi.service.WifiDeviceService;

@Service
@Transactional
public class WifiDeviceServiceImpl implements WifiDeviceService {
	
	private static final Logger log= LoggerFactory.getLogger(WifiDeviceServiceImpl.class);

	 @Autowired
	 private ShopprobeMapper shopprobeMapper;
	 
	@Override
	public Result saveWifiDevice(WifiDeviceModel wifiDeviceModel) throws ParseException {

		// TODO Auto-generated method stub
		log.debug("开始添加设备基本信息");
		Result result = new Result();
		
		Map<String, Object> param = new HashMap<String,Object>();
		String deviceMac = wifiDeviceModel.getDeviceMac().replace(":", "");
		param.put("probemac", deviceMac);
		int countByParam = shopprobeMapper.countByParam(param);
		if(countByParam<0){
			log.debug("设备还没有添加到门店");
			result.setStatus(Status.device_exist_error_status);
			result.setInfo(Status.device_exist_error_info);
			return result;
		}
		
		Shopprobe selectByProbemac = shopprobeMapper.selectByProbemac(deviceMac);
		if(Help.isNotNull(selectByProbemac)){
			selectByProbemac.setProbeActivityInterval(wifiDeviceModel.getDeviceActivityInterval());
			selectByProbemac.setProbeHardwareVersion(wifiDeviceModel.getDeviceHardwareVersion());
			 SimpleDateFormat reader = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy",Locale.US);
			 Date read = reader.parse(wifiDeviceModel.getDeviceDatetime());
//			 SimpleDateFormat writer = new SimpleDateFormat("yyyyMMdd HH-mm-ss");
			 selectByProbemac.setProbeDatetime(read);
			 selectByProbemac.setProbeLanIp(wifiDeviceModel.getDeviceLanIp());
			 selectByProbemac.setProbeWanIp(wifiDeviceModel.getDeviceWanIp());
			 selectByProbemac.setProbeName(wifiDeviceModel.getDeviceName());
			 selectByProbemac.setProbeType(wifiDeviceModel.getDeviceType());
			 selectByProbemac.setProbeUploadInterval(wifiDeviceModel.getDeviceUploadInterval());
			 selectByProbemac.setProbeSaveInterval(wifiDeviceModel.getDeviceSaveInterval());
			
			shopprobeMapper.updateByPrimaryKeySelective(selectByProbemac);
			log.debug("设备信息上传成功！");
			result.setStatus(Status.success_status);
			result.setInfo(Status.success_info);
		}
		
		return result;
	}
}
