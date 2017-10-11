package com.owl.wifi.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.owl.wifi.web.entity.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Long nRoleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long nRoleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<String> findRoleNamesByUserId(@Param("userId")Long userId);
    List<Role> findByUserId(@Param("userId")Long userId);
    Role findByName(@Param("roleName")String roleName);
    
    Integer maxRoleCode();

	List<Role> findAdminGroupByParam(Map<String,Object> param);
	
	int countAdminGroupByParam();
}