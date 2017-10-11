package com.owl.wifi.web.wifi.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.github.kevinsawicki.http.HttpRequest;
import com.owl.wifi.content.Status;
import com.owl.wifi.util.Help;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.wifi.service.TalkingDataApiService;

@Service
public class TalkingDataApiServiceImpl implements TalkingDataApiService {
	private static final Logger log= LoggerFactory.getLogger(TalkingDataApiServiceImpl.class);
	
	private static String token ="";
    @Autowired
    private  Environment env;
	
	@Override
	public Result userlocgeoweek(String id, String type, String week) {
		
		log.debug("周聚集区域查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("userlocgeoweek");
		String paramStr = "id="+id+"&type="+type+"&week="+week;
		
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr=HttpRequest.get(host).body();
		

	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
	}
	
	@Override
	public Result userlocgeomon(String id, String type, String month) {
		
		log.debug("月聚集区域查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("userlocgeomon");
		String paramStr = "id="+id+"&type="+type+"&month="+month;
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr=null;
		

	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
		
	}

	@Override
	public Result userlocresidenceinfo(String id, String type) {
		
		log.debug("夜间活跃区域查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("userlocresidenceinfo");
		String paramStr = "id="+id+"&type="+type;
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr=null ;
		

	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
		
	}

	@Override
	public Result userloccity(String id, String type, String month) {
		
		log.debug("常出现城市查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("userloccity");
		String paramStr = "id="+id+"&type="+type+"&month="+month;
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr=null ;
		
	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
		
	}

	@Override
	public Result usertaggame(String id, String type) {
		
		log.debug("游戏兴趣标签查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("usertaggame");
		String paramStr = "id="+id+"&type="+type;
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr=null ;
		
	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
	    
	}

	@Override
	public Result usertagconsume(String id, String type) {
		
		log.debug("线下消费偏好标签查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("usertagconsume");
		String paramStr = "id="+id+"&type="+type;
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr=null ;
		
	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
	}

	@Override
	public Result usertagdemographic(String id, String type) {
		
		log.debug("人口属性标签查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("usertagdemographic");
		String paramStr = "id="+id+"&type="+type;
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr=null ;
		
	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
		
	}

	@Override
	public Result usertagapp(String id, String type) {
		
		log.debug("应用兴趣标签查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
//			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("usertagapp");
		String paramStr = "id="+id+"&type="+type;
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr= null ;
		
	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
		
	}

	@Override
	public Result usertagfinance(String id, String type) {
		
		log.debug("金融标签查询服务");
		Result result = new Result();
		
		if(Help.isNull(id)){
			log.debug("设备id为空!");
			result.setStatus(Status.device_id_error_status);
			result.setInfo(Status.device_id_error_info);
			return result;
		}
		
		if(Help.isNull(type)){
			log.debug("设备类型为空!");
			result.setStatus(Status.device_type_error_status);
			result.setInfo(Status.device_type_error_info);
			return result;
		}
		
		String host = env.getProperty("usertagfinance");
		String paramStr = "id="+id+"&type="+type;
		
		if(Help.isNull(token)){
			getToken();
		}
		String jsonStr= null ;
		
	    System.out.println(jsonStr);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("data", jsonStr);
		result.setData(data);
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
	    return result;
	}
	
	//获取talkingdata accesstoken
	private String getToken(){
		log.debug("开始获取talkingdata的accessToken！");
		String host = env.getProperty("API_HOST");
		String param = env.getProperty("API_PARAM");
		
		String sr = null ;
        JSONObject fromObject = JSONObject.fromObject(sr);
        JSONObject data = (JSONObject) fromObject.get("data");
        token = data.get("token").toString();
        log.debug(data.get("token").toString());
		return token;
	}
	
}
