package com.owl.wifi.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.owl.wifi.web.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User login(@Param("userName")String userName,@Param("userPwd")String userPwd);
    List<User> findByParam(Map<String,Object> param);
    int countByParam(Map<String,Object> param);
    
    User selectUserName(@Param("userName")String userName);
}