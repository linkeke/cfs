package com.owl.wifi.web.talkingdata.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owl.wifi.web.entity.TalkingdataPosition;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.owl.wifi.util.GsonUtil;
import com.owl.wifi.web.entity.TalkingdataDevice;
import com.owl.wifi.web.entity.TalkingdataMore;

@ConfigurationProperties(prefix="talkingdata")
@Component
public class Talkingdata {
	private static final Logger log = LoggerFactory.getLogger(Talkingdata.class);
	private static final String LOGIN_API = "https://api.talkingdata.com/tdmkaccount/authen/app/v2?apikey=%s&apitoken=%s";
	private static final String TALKINGDATA_API_HOST = "https://api.talkingdata.com/data/";
	private String appkey = "8ad8369cd9ba4e40979738ceecdbb66f";
	private String token ="9a51bfb32e394feb82848b6ba4ee52a4";
	private String accessToken ;
	private int expiry = 58 ;
	private long loginTime ;
	
	
	/**
	 * ID间关联关系查询
	 * @param mac
	 * @return
	 */
	public TalkingdataDevice idmapping(String mac){
		Map<String , Object> param = new HashMap<String , Object>();
		param.put("id", mac);
		param.put("type" , "mac");
		param.put("otype" , "imei|idfa");
		JsonObject json = talkingDataApi("user-idmapping2/v1" , param);
		System.out.println(json);
		if(json != null){
			TalkingdataDevice device = new TalkingdataDevice();
			device.setAndroidId(GsonUtil.getAsString(json, "androidid"));
			device.setIdfa(GsonUtil.getAsString(json, "idfa"));
			device.setImei(GsonUtil.getAsString(json, "imei"));
			device.setTdid(GsonUtil.getAsString(json, "tdid"));
			return device ;
		}else{
			return null ;
		}
	}
	
	/**
	 * 游戏兴趣标签查询服务
	 * @param mac
	 * @return
	 */
	public List<TalkingdataMore> gameTag(String mac){
		Map<String , Object> param = new HashMap<String , Object>();
		param.put("id", mac);
		param.put("type" , "mac");
		JsonObject json = talkingDataApi("user-tag-game/v1" , param);
		return parseTalkingdataTags(json);
	}
	
	/**
	 * 线下消费偏好标签查询服务
	 * @param mac
	 * @return
	 */
	public List<TalkingdataMore> consumeTag(String mac){
		Map<String , Object> param = new HashMap<String , Object>();
		param.put("id", mac);
		param.put("type" , "mac");
		JsonObject json = talkingDataApi("user-tag-consume/v1" , param);
		return parseTalkingdataTags(json);
	}
	

	/**
	 * 人口属性标签查询服务
	 * @param mac
	 * @return
	 */
	public List<TalkingdataMore> demographicTag(String mac){
		Map<String , Object> param = new HashMap<String , Object>();
		param.put("id", mac);
		param.put("type" , "mac");
		JsonObject json = talkingDataApi("user-tag-demographic/v1" , param);
		return parseTalkingdataTags(json);
	}
	
	/**
	 * 金融标签查询服务
	 * @param mac
	 * @return
	 */
	public List<TalkingdataMore> financeTag(String mac){
		Map<String , Object> param = new HashMap<String , Object>();
		param.put("id", mac);
		param.put("type" , "mac");
		JsonObject json = talkingDataApi("user-tag-finance/v1" , param);
		return parseTalkingdataTags(json);
	}
	
	/**
	 * 应用兴趣标签查询服务
	 * @param mac
	 * @return
	 */
	public List<TalkingdataMore> appTag(String mac) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", mac);
        param.put("type", "mac");
        JsonObject json = talkingDataApi("user-tag-app/v1", param);
        return parseTalkingdataTags(json);
    }
	/**
	 * 获取mac对应接口的数据
	 * @param mac
	 * @return
	 */
	public List<TalkingdataMore> selectByMACgroupByPort(String mac,String portid){
		Map<String , Object> param = new HashMap<String , Object>();
		param.put("mac", mac);
		param.put("portid" , "portid");
		JsonObject json = talkingDataApi("user-tag-app/v1" , param);
		return parseTalkingdataTags(json);
	}

    /**
     *
     * @param id TalkingData可支持的设备id，代表查询的设备，可支持多种类型	true
     * @param type id类型： imei国际移动设备标识 idfa苹果体系内设备唯一标识 tdidTalkingData设备唯一标识 androidid安卓体系内设备唯一标识 macMAC地址	true
     * @param week 希望查询的数据周，取值范围为1~52，格式为yyyyww，如201626	true
     * @return
     */
	public List<TalkingdataPosition> getMacPosition(String mac,String type){

        Map<String , Object> param = new HashMap<String , Object>();
        param.put("id", mac);
        param.put("type" , type);
        JsonObject json = talkingDataApi("user-loc-office-latlng/v1" , param);
        List<TalkingdataPosition> positionList = new ArrayList<>();
        if(json!=null){
            String latlng = json.get("latlng").getAsString();
            String[] latlngStr = latlng.split("[|]");

            if(latlngStr!=null&&latlngStr.length>0){
                for(int i=0;i<latlngStr.length;i++){
                    String position = latlngStr[i];//32.653,116.582
                    String[] results = position.split(",");
                    Float lat = Float.parseFloat(results[0]);
                    Float lng = Float.parseFloat(results[1]);
                    TalkingdataPosition talkingdataPosition = new TalkingdataPosition();
                    talkingdataPosition.setMac(mac.replaceAll(":", ""));
                    talkingdataPosition.setLat(lat);
                    talkingdataPosition.setLng(lng);
                    talkingdataPosition.setCount(1);
                    positionList.add(talkingdataPosition);
                }

            }

        }
        return positionList;
    }
	public boolean login(){
		if(System.currentTimeMillis() - loginTime < expiry){
			return true ;
		}
		String url = String.format(LOGIN_API, appkey , token);
		HttpRequest request = HttpRequest.get(url).acceptJson();
		log.info("access : " + url + " status : " + request.code());
		if(request.code() == 200){
			JsonObject json = new JsonParser().parse(request.body()).getAsJsonObject();
			if(json.get("status").getAsInt() == 200){
				String newToken = json.get("data").getAsJsonObject().get("token").getAsString();
				this.loginTime = System.currentTimeMillis();
				this.accessToken = newToken ;
				return true ;
			}else{
				return false ;
			}
		}		
		return false ;
	}
	
	private List<TalkingdataMore> parseTalkingdataTags(JsonObject json){
		if(json == null)
			return null ;
		JsonArray tags = json.get("tags").getAsJsonArray();
		List<TalkingdataMore> list = new ArrayList<TalkingdataMore>(tags.size());
		for(int i = 0 ; i < tags.size() ; i++){
			JsonObject tag = tags.get(i).getAsJsonObject();
			TalkingdataMore data = new TalkingdataMore();
			data.setLabel(tag.get("label").getAsString());
			data.setName(tag.get("name").getAsString());
			data.setWeight(tag.get("weight").getAsString());
			list.add(data);
		}
		return list ;
	}
	
	private JsonObject talkingDataApi(String url , Map<String , Object> param){
		if(login()){
			HttpRequest request = HttpRequest.get(TALKINGDATA_API_HOST + url , param , true).header("X-Access-Token" , this.accessToken);
			log.info("access : " + url + " status : " + request.code());
			if(request.code() == 200){
				JsonObject json = new JsonParser().parse(request.body()).getAsJsonObject();
				log.info("query talkingdata status : " + json.get("code"));
				if(json.get("code").getAsInt() == 2001){
					return json.get("data").getAsJsonObject();
				}else{
					return null ;
				}
			}else{
				return null ;
			}
		}else{
			return null ;
		}
	}
    @Test
	public  void test(){
		Talkingdata data = new Talkingdata();
		TalkingdataDevice device = data.idmapping("10:2A:B3:38:44:84");
		System.out.println(device);
	}
}
