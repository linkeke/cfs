package com.owl.wifi.web.dao;

import org.apache.ibatis.annotations.Param;
import com.owl.wifi.web.entity.RoleMenu;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(Long nRoleMenuId);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Long nRoleMenuId);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);

	void deleteByRoleCode(@Param("roleCode")Long roleCode);
}