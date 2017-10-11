package com.owl.wifi.web.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.owl.wifi.web.dao.*;
import com.owl.wifi.web.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.user.dto.ShopDto;
import com.owl.wifi.web.user.model.AddGroupModel;
import com.owl.wifi.web.user.model.GroupListModel;
import com.owl.wifi.web.user.model.ModifyGroupModel;
import com.owl.wifi.web.user.model.ShopDeviceModel;
import com.owl.wifi.web.user.model.ShopModel;
import com.owl.wifi.web.user.model.UserListModel;
import com.owl.wifi.web.user.model.UserShopModel;
import com.owl.wifi.web.user.service.UserService;

@Controller
@RequestMapping("")
public class UserController{
  
	BKLogger logger = BKLogger.getLogger(UserController.class);
	private static final Logger log= LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private IndustryMapper industryMapper;
	
	@Autowired
	private UserShopMapper userShopMapper;
	
	@Autowired
	private ShopprobeMapper shopprobeMapper;
    @Autowired
    private ShopCameraMapper shopCameraMapper;
	
	@RequestMapping("/")
    public String indexController(HttpServletRequest request,Model model) {
        return "index";
    }
	
	 @RequestMapping("/shopPage.do")
	 public String shopPage(HttpServletRequest request,Model model){
		 return "system/shop";
	 }
	 
	 @RequestMapping("/addshopPage.do")
	 public String addshopPage(HttpServletRequest request,Model model){
		 return "system/addshop";
	 }
	 
	 @RequestMapping("/userPage.do")
	 public String userPage(HttpServletRequest request,Model model){
		 return "system/user";
	 }

	 @RequestMapping("/shopprobePage.do")
	 public String shopprobePage(HttpServletRequest request,Model model){
		 return "system/shopprobe";
	 }
	
	
	 @RequestMapping("/loginPage.do")
	 public String loginPage(HttpServletRequest request,Model model){
		 return "login";
	 }
	
	 @RequestMapping("/login.do")
	 @ResponseBody
	 public Object login(HttpServletRequest request,Model model,String account,String password){
		 Result result = null;
		 try{
			 result=userService.login(request,account, password);
		 }catch(Exception ex){
			 log.error("登录列表失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("用户登录", result);
		 
	 }
	 
	 @RequestMapping("/index.do")
	 public Object indexxController(HttpServletRequest request,Model model){
		 request.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		 return "index";
	 }
	 
	 @RequestMapping("/logout.do")
	 public String logout(HttpServletRequest request, Model model){
		 request.getSession().removeAttribute("user");
		 return "login";  
	 }
	 
	 @RequestMapping("/addshop.do")
	 @ResponseBody
	 public Object addshopController(HttpServletRequest request,Model model,ShopModel shopModel){
		 Result result = null;
		 try{
			 result=userService.addshop(request,shopModel);
		 }catch(Exception ex){
			 log.error("添加门店失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("添加门店", result);
		 
	 }
	 
	 @RequestMapping("/updateshoppage.do")
	 public String updateshoppage(HttpServletRequest request,Model model,ShopModel shopModel,Integer shopId){
		 ShopDto getshopInfo = userService.getshopInfo(shopId);
		 model.addAttribute("getshopInfo", getshopInfo);
		 List<Industry> industrys = industryMapper.findByParam(null);
		 model.addAttribute("industrys", industrys);
		 return "system/updateshop";
	 }
	 
	 @RequestMapping("/updateshop.do")
	 @ResponseBody
	 public String updateshop(HttpServletRequest request,Model model,ShopModel shopModel){
		 Result result = null;
		 try{
			 result=userService.updateshop(shopModel);
		 }catch(Exception ex){
			 log.error("修改门店失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("修改门店", result);
	 }
	 
	 @RequestMapping("/shopListByUser.do")
	 @ResponseBody
	 public String shopListByUserController(HttpServletRequest request,Model model,Integer pageSize,Integer page){
		 Result result = null;
		 try{
			 result=userService.shopListByUser(request, pageSize, page);
		 }catch(Exception ex){
			 log.error("获取门店列表失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("获取门店列表", result);
	 }
	 
	 @RequestMapping("/delshop.do")
	 @ResponseBody
	 public String delshopController(HttpServletRequest request,Model model,Integer shopId){
		 Result result = null;
		 try{
			 result=userService.delshop(shopId);
		 }catch(Exception ex){
			 log.error("删除门店失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("删除门店", result);
		 
	 }
	 
	 @RequestMapping("/addUserPage.do")
	 public String addUserPage(HttpServletRequest request,Model model){
		 return "system/addUser";
	 }
	 
	 @RequestMapping("/addUser.do")
	 @ResponseBody
	 public Object addUserController(HttpServletRequest request,Model model,UserShopModel userShopModel){
		 Result result = null;
		 try{
			 result=userService.addUser(request,userShopModel);
		 }catch(Exception ex){
			 log.error("添加用户失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("添加用户", result);
	 }
	 
	 @RequestMapping("/deluser.do")
	 @ResponseBody
	 public Object deluserController(HttpServletRequest request,Model model,Integer userId){
		 Result result = null;
		 try{
			 result=userService.deluser(userId);
		 }catch(Exception ex){
			 log.error("删除用户失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("删除用户", result);
	 }
	 
	 
	 @RequestMapping("/userList.do")
	 @ResponseBody
	 public String userListController(HttpServletRequest request,Model model,UserListModel userListModel){
		 Result result = null;
		 try{
			 result=userService.userList(request,userListModel);
		 }catch(Exception ex){
			 log.error("获取门店列表失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("获取门店列表", result);
	 }
	 
	 @RequestMapping("/updateuserpage.do")
	 public String updateuserpage(HttpServletRequest request,Model model,Integer userId){
		 User user = userMapper.selectByPrimaryKey(userId);
		 UserRole group = userRoleMapper.selectRoleId(userId);
		 
		 Map<String,Object> param = new HashMap<String,Object>();
		 param.put("userId", userId);
		 List<UserShopKey> findByParam = userShopMapper.findByParam(param);
		 
		 model.addAttribute("user", user);
		 model.addAttribute("group", group);
		 return "system/updateuser";
	 }
	 
	 @RequestMapping("/updateuser.do")
	 @ResponseBody
	 public Object updateuserController(HttpServletRequest request,Model model,UserShopModel userShopModel){
		 Result result = null;
		 try{
			 result=userService.updateuser(request, userShopModel);
		 }catch(Exception ex){
			 log.error("更新用户失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("更新用户", result);
	 }
	 
	 @RequestMapping("/devicePage.do")
	 public String devicePage(HttpServletRequest request,Model model){
		 return "system/device";
	 }
	
	 @RequestMapping("/addDevicePage.do")
	 public String addDevicePage(HttpServletRequest request,Model model){
		 return "system/addDevice";
	 }
    @RequestMapping("/addCameraDevicePage.do")
    public String addCameraDevice(HttpServletRequest request,Model model){
        return "system/addCameraDevice";
    }
	 
	 @RequestMapping("/addDevice.do")
	 @ResponseBody
	 public Object addDeviceController(HttpServletRequest request,Model model,ShopDeviceModel shopDeviceModel){
		 Result result = null;
		 try{
			 result=userService.addDevice(shopDeviceModel);
		 }catch(Exception ex){
			 log.error("添加设备失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("添加设备", result);
	 }
    @RequestMapping("/addCameraDevice.do")
    @ResponseBody
    public Object addCameraDeviceController(HttpServletRequest request,Model model){
        Result result = null;
        try{
            result=userService.addCameraDevice(request);
        }catch(Exception ex){
            log.error("添加设备失败", ex);
            result = new Result ();
        }
        return logger.infobk("添加设备", result);
    }
	 @RequestMapping("/deviceList.do")
	 @ResponseBody
	 public Object deviceListController(HttpServletRequest request,Model model,Integer pageSize,Integer page) {
         Result result = null;
         try {
             result = userService.deviceList(request, pageSize, page);
         } catch (Exception ex) {
             log.error("获取设备列表失败", ex);
             result = new Result();
         }
         return logger.infobk("获取设备列表", result);
     }
    @RequestMapping("/shopCameraList.do")
    @ResponseBody
    public Object getShopCameraController(HttpServletRequest request,Model model,Integer pageSize,Integer page){
        Result result = null;
        try {
            result = userService.shopCameraList(request, pageSize, page);
        } catch (Exception ex) {
            log.error("获取设备列表失败", ex);
            result = new Result();
        }
        return logger.infobk("获取设备列表", result);

    }
	 
	 @RequestMapping("/updatedevicepage.do")
	 public String updatedevicepage(HttpServletRequest request,Model model,Integer shopprobeId){
		 Shopprobe shopprobe = shopprobeMapper.selectByPrimaryKey(shopprobeId);
		 model.addAttribute("shopprobe", shopprobe);
		 return "system/updateDevice";
	 }
	 
	 @RequestMapping("/updateDevice.do")
	 @ResponseBody
	 public Object updatedeviceController(HttpServletRequest request,Model model,ShopDeviceModel shopDeviceModel){
		 Result result = null;
		 try{
			 result=userService.updateDevice(request, shopDeviceModel);
		 }catch(Exception ex){
			 log.error("更新用户失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("更新用户", result);
	 }
	 
	 @RequestMapping("/delDevice.do")
	 @ResponseBody
	 public Object delDeviceController(HttpServletRequest request,Model model,Integer shopprobeId){
		 Result result = null;
		 try{
			 result=userService.delDevice(shopprobeId);
		 }catch(Exception ex){
			 log.error("删除设备失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("删除设备", result);
	 }

    @RequestMapping("/delCameraDevice.do")
    @ResponseBody
    public Object delCameraDeviceController(HttpServletRequest request,Model model,Integer shopCameraId){
        Result result = null;
        try{
            result=userService.delCameraDevice(shopCameraId);
        }catch(Exception ex){
            log.error("删除设备失败", ex);
            result = new Result ();
        }
        return logger.infobk("删除设备", result);
    }

    @RequestMapping("/updateCameraDevicePage.do")
    public String updateShopCameraDevicePage(HttpServletRequest request,Model model,Integer shopCameraId){
        ShopCamera camera = shopCameraMapper.selectByPrimaryKey(shopCameraId);

        model.addAttribute("camera", camera);
        return "system/updateCameraDevice";
    }

    @RequestMapping("/updateCameraDevice.do")
    @ResponseBody
    public Object updateCameradeviceController(HttpServletRequest request,Model model){
        Result result = null;
        try{
            result=userService.updateCameraDevice(request);
        }catch(Exception ex){
            log.error("更新用户失败", ex);
            result = new Result ();
        }
        return logger.infobk("更新用户", result);
    }
	 /**
	  * 获取当前登录用户菜单
	  * */
	 @RequestMapping(value = "/userMenus")
	 @ResponseBody
	 public Object userMenusController(HttpServletRequest request, Model model){
		 Result result=null;
		 try{
			 log.debug("获取当前登录用户菜单");
			 result = userService.getUserMenus(request);
		 }catch(Exception ex){
			 result = new Result();
			 log.error("获取当前登录用户菜单失败", ex);
		 }
		return (logger.infobk("获取当前登录用户菜单输出列表", result));
	 }
	 
	 
	 @RequestMapping("/groupPage")
    public String rolePage() {
        return "system/group";
    }
    @RequestMapping("/addGroupPage")
    public String addGroupPage() {
        return "system/addGroup";
    }
  //群组列表
  	 @RequestMapping(value = "/groupList")
  	 @ResponseBody
  	 public Object groupListController(HttpServletRequest request, Model model,GroupListModel groupListModel){
  		 Result result=null;
  		 try{
  			 result = userService.groupList(groupListModel);
  		 }catch(Exception ex){
  			 result = new Result();
  			 log.error("查询群组列表失败", ex);
  		 }
  		 return (logger.infobk("查询群组列输出列表", result));
  	 }
  	 
  	//管理组页菜单树
	 @RequestMapping(value = "/userMenuTree")
	 @ResponseBody
	 public Object userMenuTreeController(HttpServletRequest request, Model model,Long roleCode){
		 Result result=null;
		 try{
			 User user = (User)request.getSession().getAttribute("user");
			 result = userService.menuTree(Long.parseLong(user.getUserId()+""),roleCode);
		 }catch(Exception ex){
			 result = new Result();
			 log.error("获取菜单树失败", ex);
		 }
		 return (logger.infobk("获取菜单树输出列表", result));
	 }
	 
	//添加管理组
	 @RequestMapping(value = "/addGroup")
	 @ResponseBody
	 public Object addGroupController(HttpServletRequest request, Model model,AddGroupModel addGroupModel){
		 Result result=null;
		 try{
			 result = userService.addGroup(request,addGroupModel);
		 }catch(Exception ex){
			 result = new Result();
			 log.error("添加管理组失败", ex);
		 }
		 return (logger.infobk("添加管理组输出列表", result));
	 }
	 
	 
	 /**
	  * 跳转到修改群组页面
	  * */
	 @RequestMapping(value = "/modifyGroupPage")
	 public String modifyGroupPage(HttpServletRequest request, Model model,Long groupId){
		 request.getSession().setAttribute("groupId", groupId);
		 return "system/updateGroup";
	 }
	 @RequestMapping(value = "/roleDetail")
	 @ResponseBody
	 public Object roleDetail(HttpServletRequest request, Model model){
		 Result result=null;
		 try{
			Long groupId = (Long) request.getSession().getAttribute("groupId");
			result = userService.roleDetail(groupId );
		 }catch(Exception ex){
		 }
		 return (logger.infobk("session role", result));
	 }
	 @RequestMapping(value = "/modifyGroup")
	 @ResponseBody
	 public Object modifyGroupController(HttpServletRequest request, Model model,ModifyGroupModel modifyGroupModel){
		 Result result=null;
		 try{
			 result = userService.modifyGroup(request,modifyGroupModel);
		 }catch(Exception ex){
			 result = new Result();
			 log.error("修改管理组失败", ex);
		 }
		 return (logger.infobk("修改管理组输出列表", result));
	 }
	 
	//删除群组
	 @RequestMapping(value = "/delGroup")
	 @ResponseBody
	 public Object delGroupController(HttpServletRequest request, Model model,Long groupId){
		 Result result=null;
		 try{
			 result = userService.delGroup(groupId);
		 }catch(Exception ex){
			 result = new Result();
			 log.error("删除群组失败", ex);
		 }
		 return (logger.infobk("删除群组输出列表", result));
	 }
	 
	 //查询所有分组
	 @RequestMapping(value = "/allGroups")
	 @ResponseBody
	 public Object allGroups(HttpServletRequest request, Model model){
		 Result result = userService.groupList(null);
		return (logger.infobk("添加管理员输出列表", result));
	 }
	 
	 
	 @RequestMapping("/industry.do")
	 @ResponseBody
	 public Object industry(HttpServletRequest request,Model model,Integer pageSize,Integer page){
		 Result result = null;
		 try{
			 result=userService.industrys(request, pageSize, page);
		 }catch(Exception ex){
			 log.error("获取行业列表失败", ex);
			 result = new Result ();
		 }
		 return logger.infobk("获取行业列表", result);
	 }
}
