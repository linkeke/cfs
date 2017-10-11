package com.owl.wifi.web.wifi.service;

import com.owl.wifi.util.Result;


public interface TalkingDataApiService {
	 
	/**
	 * 周聚集区域查询服务
	 * @param id(TalkingData可支持的设备id，代表查询的设备，可支持多种类型)
	 * @param type(id类型： imei国际移动设备标识 idfa苹果体系内设备唯一标识 tdidTalkingData设备唯一标识 androidid安卓体系内设备唯一标识 macMAC地址)
	 * @param week(希望查询的数据周，取值范围为1~52，格式为yyyyww，如201626)
	 * @return
	 */
	public Result userlocgeoweek(String id,String type,String week);
	
	/**
	 * 月聚集区域查询服务
	 * @param id
	 * @param type
	 * @param month(查询月份，格式为yyyymm，如201608)
	 * @return
	 */
	public Result userlocgeomon(String id,String type,String month);
	
	/**
	 * 夜间活跃区域查询服务
	 * @param id
	 * @param type
	 * @return
	 */
	public Result userlocresidenceinfo(String id,String type);
	
	/**
	 * 常出现城市查询服务
	 * @param id
	 * @param type
	 * @param month
	 * @return
	 */
	public Result userloccity(String id,String type,String month);
	
	/**
	 * 游戏兴趣标签查询服务
	 * @param id
	 * @param type
	 * @return
	 */
	public Result usertaggame(String id,String type);
	
	/**
	 * 线下消费偏好标签查询服务
	 * @param id
	 * @param type
	 * @return
	 */
	public Result usertagconsume(String id,String type);
	
	/**
	 * 人口属性标签查询服务
	 * @param id
	 * @param type
	 * @return
	 */
	public Result usertagdemographic(String id,String type);
	
	/**
	 * 应用兴趣标签查询服务
	 * @param id
	 * @param type
	 * @return
	 */
	public Result usertagapp(String id,String type);
	
	/**
	 * 金融标签查询服务
	 * @param id
	 * @param type
	 * @return
	 */
	public Result usertagfinance(String id,String type);
	
	
	 
}
