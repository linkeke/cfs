package com.owl.wifi.web.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.wifi.util.Help;
import com.owl.wifi.web.dao.PermissionMapper;
import com.owl.wifi.web.dao.RoleMapper;
import com.owl.wifi.web.dao.UserMapper;
import com.owl.wifi.web.entity.User;
import com.owl.wifi.web.user.service.PermissionService;


@Service
public class PermissionServiceImpl implements PermissionService{
	private static final Logger log= LoggerFactory.getLogger(PermissionServiceImpl.class);
	@Autowired
	private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
	@Override
	public Map<String,Object> authorization(Long userId) {
		// TODO Auto-generated method stub
		Map<String,Object>  resultMap = new HashMap<String,Object> ();
		if(Help.isNull(userId)){
			return resultMap;
		}
		User userAccount = userMapper.selectByPrimaryKey(Integer.parseInt(userId+""));
		if(Help.isNotNull(userAccount)){
			List<String> roleNames = roleMapper.findRoleNamesByUserId(userId);
			List<String> permissions = permissionMapper.findPermissionsByUserId(userId);
			permissions.add("/userMenus");
			permissions.add("/userMenuTree");
			resultMap.put("roleNames", roleNames);
			resultMap.put("permissions", permissions);
		}
		
		return resultMap;
	}
      
}
