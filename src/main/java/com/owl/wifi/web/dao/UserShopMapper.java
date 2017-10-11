package com.owl.wifi.web.dao;

import java.util.List;
import java.util.Map;
import com.owl.wifi.web.entity.UserShopKey;

public interface UserShopMapper {
	int deleteByPrimaryKey(UserShopKey key);

	int insert(UserShopKey record);

	int insertSelective(UserShopKey record);

	List<Map<String, Object>> shopListByUser(Integer userId);
	
	List<UserShopKey> findByParam(Map<String,Object> param);
	int countByParam(Map<String,Object> param);
	
	int updateByUserId(UserShopKey record);
	
	int deleteByShopOrUserId(Map<String,Object> param); 
	
}