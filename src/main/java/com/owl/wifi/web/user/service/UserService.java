package com.owl.wifi.web.user.service;

import javax.servlet.http.HttpServletRequest;

import com.owl.wifi.util.Result;
import com.owl.wifi.web.user.dto.ShopDto;
import com.owl.wifi.web.user.model.AddGroupModel;
import com.owl.wifi.web.user.model.GroupListModel;
import com.owl.wifi.web.user.model.ModifyGroupModel;
import com.owl.wifi.web.user.model.ShopDeviceModel;
import com.owl.wifi.web.user.model.ShopModel;
import com.owl.wifi.web.user.model.UpdateUserShopModel;
import com.owl.wifi.web.user.model.UserListModel;
import com.owl.wifi.web.user.model.UserShopModel;

public interface UserService {
	/**
	 * 用户登录
	 * */
  public Result login(HttpServletRequest request,String account,String password);
  
  //门店相关操作
  public Result shopListByUser(HttpServletRequest request,Integer pageSize ,Integer page);
  public Result addshop(HttpServletRequest request,ShopModel shopModel);
  public Result updateshop(ShopModel shopModel);
  public ShopDto getshopInfo(Integer shopId);
  public Result delshop(Integer shopId);
  
  //用户相关操作
  public Result userList(HttpServletRequest request,UserListModel userListModel);
  public Result addUser(HttpServletRequest request,UserShopModel userShopModel);
  public Result updateuser(HttpServletRequest request,UserShopModel userShopModel);
  public Result deluser(Integer userId);
  
  //设备相关操作
  public Result addDevice(ShopDeviceModel shopDeviceModel);
  public Result deviceList(HttpServletRequest request,Integer pageSize ,Integer page);
  public Result updateDevice(HttpServletRequest request,ShopDeviceModel shopDeviceModel);
  public Result delDevice(Integer shopprobeId);
  //摄像头设备相关操作
  public Result shopCameraList(HttpServletRequest request,Integer pageSize ,Integer page);
  public Result addCameraDevice(HttpServletRequest request);
  public Result updateCameraDevice(HttpServletRequest request);
  public Result delCameraDevice(Integer shopId);
  /**
   * 获取导航按钮
   * @param request
   * @return
   */
  public Result getUserMenus(HttpServletRequest request);
  
  /**
   * 获取角色分组
   * @param groupListModel
   * @return
   */
  public Result groupList(GroupListModel groupListModel);
  
  /**
   * 角色下的模块树
   * @param getnUserId
   * @param roleCode
   * @return
   */
  public Result menuTree(Long getnUserId, Long roleCode);
  
  /**
   * 获取角色名称具体信息
   * @param groupId
   * @return
   */
  public Result roleDetail(Long groupId);
  
  /**
   * 添加分组
   * @param request
   * @param addGroupModel
   * @return
   */
  public Result addGroup(HttpServletRequest request,AddGroupModel addGroupModel);
  
  /**
   * 修改分组
   * @param request
   * @param modifyGroupModel
   * @return
   */
  public Result modifyGroup(HttpServletRequest request,ModifyGroupModel modifyGroupModel);
  
  /**
   * 删除群组
   * @param groupId
   * @return
   */
  public Result delGroup(Long groupId);
  
  /**
   * 获取行业列表
   * @param request
   * @param pageSize
   * @param page
   * @return
   */
  public Result industrys(HttpServletRequest request,Integer pageSize ,Integer page);


}
