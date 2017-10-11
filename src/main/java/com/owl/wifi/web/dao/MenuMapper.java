package com.owl.wifi.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.owl.wifi.web.entity.Menu;


public interface MenuMapper {
    int deleteByPrimaryKey(Long nMenuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long nMenuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    List<Menu> findUserMenusByUserId(@Param("userId") Long userId);
    List<Menu> findUserMenusByRoleCode(@Param("roleCode") Long roleCode);
    List<Menu> findAll();
}