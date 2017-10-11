package com.owl.wifi.web.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.owl.wifi.web.dao.*;
import com.owl.wifi.web.entity.*;
import com.owl.wifi.web.user.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.wifi.content.FinVal;
import com.owl.wifi.content.Status;
import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Help;
import com.owl.wifi.util.MUtil;
import com.owl.wifi.util.Paginator;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.user.model.AddGroupModel;
import com.owl.wifi.web.user.model.GroupListModel;
import com.owl.wifi.web.user.model.ModifyGroupModel;
import com.owl.wifi.web.user.model.ShopDeviceModel;
import com.owl.wifi.web.user.model.ShopModel;
import com.owl.wifi.web.user.model.UserListModel;
import com.owl.wifi.web.user.model.UserShopModel;
import com.owl.wifi.web.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
        BKLogger logger = BKLogger.getLogger(UserServiceImpl.class);
        private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

        @Autowired
        private UserMapper userMapper;

        @Autowired
        private ShopMapper shopMapper;

        @Autowired
        private UserShopMapper userShopMapper;

        @Autowired
        private ShopprobeMapper shopprobeMapper;

        @Autowired
        private MenuMapper menuMapper;
        
        @Autowired
        private RoleMapper roleMapper;
        
        @Autowired
        private RoleMenuMapper roleMenuMapper;
        
        @Autowired
        private MenuPermissionMapper menuPermissionMapper;
        
        @Autowired
        private RolePermissionMapper rolePermissionMapper;
        
        @Autowired
        private UserRoleMapper userRoleMapper;
        
        @Autowired
        private IndustryMapper industryMapper;

        @Autowired
        private ShopCameraMapper shopCameraMapper;

        @Override
        public Result login(HttpServletRequest request, String userName, String userPwd) {
                // TODO Auto-generated method stub
                Result result = new Result();
//                User login = userMapper.login(userName, userPwd);
                 User login = userMapper.login(userName, MUtil.md5(userPwd));
                if (Help.isNotNull(login)) {
                        setSessionUser(request, login);
                        result.setStatus(Status.success_status);
                        result.setInfo(Status.success_info);
                        // Menu
                        request.getSession().setAttribute("sys_menu", getMenus(login.getUserId()));
                        // End
                        return result;
                }
                result.setStatus(Status.login_error_status);
                result.setInfo(Status.login_error_info);
                return result;
        }

        private static User getSessionUser(HttpServletRequest request) {
                return (User) request.getSession().getAttribute("user");
        }

        private void setSessionUser(HttpServletRequest request, User user) {
                request.getSession().setAttribute("user", user);
        }

        @Override
        public ShopDto getshopInfo(Integer shopId) {
                Shop selectByPrimaryKey = shopMapper.selectByPrimaryKey(shopId);

                ShopDto shopDto = new ShopDto();
                shopDto.setShopId(selectByPrimaryKey.getShopId());
                shopDto.setShopName(selectByPrimaryKey.getNameTxt());
                
                shopDto.setIndustryId(selectByPrimaryKey.getIndustryId());
                shopDto.setBusinessStarttime(selectByPrimaryKey.getBusinessStartTime());
                shopDto.setBusinessEndtime(selectByPrimaryKey.getBusinessEndTime());
                shopDto.setAddress(selectByPrimaryKey.getAddress());
                shopDto.setContact(selectByPrimaryKey.getContact());
                shopDto.setTel(selectByPrimaryKey.getTel());
                
                
                BigDecimal lng= selectByPrimaryKey.getLng();
                BigDecimal lat=selectByPrimaryKey.getLat();
                shopDto.setLatlng(lng.toString()+", "+lat.toString());

                return shopDto;
        }

        @Override
        public Result addshop(HttpServletRequest request, ShopModel shopModel) {
                Result result = new Result();
                if (Help.isNotNull(shopModel)) {
                        Shop shop = new Shop();
                        shop.setNameTxt(shopModel.getShopName());
                        shop.setIndustryId(shopModel.getIndustryId());
                        shop.setBusinessStartTime(shopModel.getBusinessStarttime());
                        shop.setBusinessEndTime(shopModel.getBusinessEndtime());
                        shop.setAddress(shopModel.getAddress());
                        shop.setContact(shopModel.getContact());
                        shop.setTel(shopModel.getTel());
                        
                        if(Help.isNotNull(shopModel.getLatlng())){
                        	String[] split = shopModel.getLatlng().trim().split(", ");
                        	BigDecimal lng=new BigDecimal(split[0]); 
                        	BigDecimal lat=new BigDecimal(split[1]); 
                        	shop.setLng(lng);
                        	shop.setLat(lat);
                        }
                        
                        shopMapper.insert(shop);

                        // 添加门店关联用户
                        User user = getSessionUser(request);
                        UserShopKey userShopKey = new UserShopKey();
                        userShopKey.setShopId(shop.getShopId());
                        userShopKey.setUserId(user.getUserId());
                        userShopMapper.insert(userShopKey);

                        result.setStatus(Status.success_status);
                        result.setInfo(Status.success_info);
                }
                return result;
        }

        @Override
        public Result shopListByUser(HttpServletRequest request, Integer pageSize, Integer page) {
                Result result = new Result();
                page = page == null ? FinVal.DEFAULT_PAGE : page;
                pageSize = pageSize == null ? FinVal.DEFAULT_PAGESIZE : pageSize;

                User sessionUser = getSessionUser(request);

                if (Help.isNull(sessionUser)) {
                        result.setStatus(Status.user_not_exist_error_status);
                        result.setInfo(Status.user_not_exist_error_info);
                        return result;
                }

                Map<String, Object> param = new HashMap<String, Object>();

                Integer userId = sessionUser.getUserId();
                if (userId != 1) {
                        param.put("userId", userId);
                }

                List<UserShopKey> userShops = userShopMapper.findByParam(param);

                List<Integer> shopIds = new ArrayList<Integer>();
                if (Help.isNotNull(userShops)) {
                        for (UserShopKey UserShopKey : userShops) {
                                shopIds.add(UserShopKey.getShopId());
                        }
                }
                logger.debug("shopIds==" + shopIds);

                param.clear();
                param.put("shopIds", shopIds);
                int countByParam = shopMapper.countByParam(param);
                Paginator paginator = new Paginator(0, pageSize);
                paginator.gotoPage(page);
                int pageCount = paginator.calcPageCount(countByParam); // 总页数

                param.put("startRow", paginator.getStartRow());
                param.put("pageSize", paginator.getPageSize());
                List<Shop> list = shopMapper.findByParam(param);

                List<UserShopDto> userShopDtos = new ArrayList<UserShopDto>();
                if (Help.isNotNull(list)) {
                        for (Shop shop : list) {
                                UserShopDto userShopDto = new UserShopDto();
                                userShopDto.setShopId(shop.getShopId());
                                userShopDto.setShopName(shop.getNameTxt());
                                userShopDto.setAddress(shop.getAddress());
                                userShopDto.setBusinessEndTime(shop.getBusinessEndTime());
                                userShopDto.setBusinessStartTime(shop.getBusinessStartTime());
                                userShopDto.setContact(shop.getContact());
                                userShopDto.setTel(shop.getTel());
                                Industry insutry = industryMapper.selectByPrimaryKey(shop.getIndustryId());
                                if(Help.isNotNull(insutry)){
                                	userShopDto.setIndustryName(insutry.getName());
                                }
                                userShopDtos.add(userShopDto);
                        }
                }

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("dataList", userShopDtos);
                data.put("page", page);
                data.put("pageSize", pageSize);
                data.put("pageCount", pageCount);
                data.put("total", countByParam);
                result.setStatus(Status.success_status);
                result.setInfo(Status.success_info);
                result.setData(data);
                return result;
        }

        @Override
        public Result updateshop(ShopModel shopModel) {
                Result result = new Result();
                if (Help.isNotNull(shopModel)) {
                        Shop shop = new Shop();
                        shop.setShopId(shopModel.getShopId());
                        shop.setNameTxt(shopModel.getShopName());
                        
                        shop.setIndustryId(shopModel.getIndustryId());
                        shop.setBusinessStartTime(shopModel.getBusinessStarttime());
                        shop.setBusinessEndTime(shopModel.getBusinessEndtime());
                        shop.setAddress(shopModel.getAddress());
                        shop.setContact(shopModel.getContact());
                        shop.setTel(shopModel.getTel());
                        if(Help.isNotNull(shopModel.getLatlng())){
                        	String[] split = shopModel.getLatlng().trim().split(", ");
                        	BigDecimal lng=new BigDecimal(split[0]); 
                        	BigDecimal lat=new BigDecimal(split[1]); 
                        	shop.setLng(lng);
                        	shop.setLat(lat);
                        }
                        shop.setTalkingdataflag(shopModel.getTalkingdataFlag());
                      
                        shopMapper.updateByPrimaryKeySelective(shop);
                        result.setStatus(Status.success_status);
                        result.setInfo(Status.success_info);
                        return result;
                }
                result.setStatus(Status.shop_adderror_status);
                result.setInfo(Status.shop_adderror_info);
                return result;
        }

        @Override
        public Result addUser(HttpServletRequest request,UserShopModel userShopModel) {
        	Result result = new Result();
    		if(Help.isNull(userShopModel)){
    			log.debug("参数错误！！");
    			result.setStatus(Status.param_error_status);
    			result.setInfo(Status.param_error_info);
    			return result;
    		}
    		String userAccount = userShopModel.getUserName();
    		if(Help.isNull(userAccount)){
    			log.debug("用户名为空！！userName="+userAccount);
    			result.setStatus(Status.useraccount_empty_status);
    			result.setInfo(Status.useraccount_empty_info);
    			return result;
    		}
    		String password = userShopModel.getPassword();
    		if(Help.isNull(password)){
    			log.debug("密码为空！！password="+password);
    			result.setStatus(Status.pwd_error_status);
    			result.setInfo(Status.pwd_error_info);
    			return result;
    		}
    		if (Help.isNull(userShopModel.getShopId())) {
                result.setStatus(Status.shop_noselecterror_status);
                result.setInfo(Status.shop_noselecterror_info);
                return result;
    		}
    		String roleCodes = userShopModel.getRoleCodes();
    		if(Help.isNull(roleCodes)){
    			log.debug("roleCodes为空，roleCodes="+roleCodes);
    			result.setStatus(Status.role_empty_error_status);
    			result.setInfo(Status.role_empty_error_info);
    			return result;
    		}
    		
    		Map<String, Object> param = new HashMap<String,Object>();
    		param.put("userName", userAccount);
    		User account = userMapper.selectUserName(userAccount );
    		if(Help.isNotNull(account)){
    			result.setStatus(Status.useraccount_exist_status);
    			result.setInfo(Status.useraccount_exist_info);
    			return result;
    		}
    		User user=new User();
    		user.setUserName(userAccount);
    		user.setUserPwd(MUtil.md5(password));
    		
    		User sessionUser = getSessionUser(request);
    		user.setnCreateId(sessionUser.getUserId());
    		user.settCreateTime(new Date());
    		
    		userMapper.insertSelective(user);
    		Long userId = Long.parseLong(user.getUserId()+"");
    		
    		UserShopKey usershop = new UserShopKey();
            usershop.setShopId(userShopModel.getShopId());
            usershop.setUserId(user.getUserId());
            userShopMapper.insert(usershop);
    		
    		List<String> roles = MUtil.convertStringToList(roleCodes);
    		for(String roleCode:roles){
    			UserRole userRole = new UserRole();
    			userRole.setnUserId(userId);
    			userRole.setnRoleCode(Integer.parseInt(roleCode));
    			userRoleMapper.insertSelective(userRole );
    		}
    		result.setStatus(Status.success_status);
    		result.setInfo(Status.success_info);
    		return result;
        }

        @Override
        public Result userList(HttpServletRequest request,UserListModel userListModel) {
    		Result result = new Result();
    		
    		User sessionUser = getSessionUser(request);
            if (Help.isNull(sessionUser)) {
                    result.setStatus(Status.user_not_exist_error_status);
                    result.setInfo(Status.user_not_exist_error_info);
                    return result;
            }
    		
    		Map<String, Object> param = new HashMap<String,Object>();
    		 Integer userId = sessionUser.getUserId();
             if (userId != 1) {
            	 	param.put("userId", userId);
                    param.put("nCreateId", userId);
             }
    		param.put("keyword", userListModel.getKeywords());
    		int count = userMapper.countByParam(param);
    		/**** 分页判断begin *****/
    		Paginator paginator = new Paginator(0, userListModel.getPageSize());
    		paginator.gotoPage(userListModel.getPage());
    		int pageCount = paginator.calcPageCount(count); // 总页数
    		/**** 分页判断end *****/
    		param.put("startRow", paginator.getStartRow());
    		param.put("pageSize", paginator.getPageSize());
    		
    		
    		List<User> users = userMapper.findByParam(param );
    		List<UserListDto> adminListDtos=new ArrayList<UserListDto>();
    		if(Help.isNotNull(users)){
    			for(User user:users){
    				UserListDto userListDto=new UserListDto();
    				userListDto.setUserAccount(user.getUserName());
    				userListDto.setUserId(user.getUserId());
    				List<Role> roles = roleMapper.findByUserId(Long.parseLong(user.getUserId()+""));
    				List<GroupListDto> groupListDtos = new ArrayList<GroupListDto>();
    				if(Help.isNotNull(roles)){
    					for(Role role:roles){
    						GroupListDto groupListDto = new GroupListDto();
    						groupListDto.setGroupCode(role.getnRoleCode());
    						groupListDto.setGroupDesc(role.getcRoleDesc());
    						groupListDto.setGroupName(role.getcRoleName());
    						groupListDto.setGroupId(role.getnRoleId());
    						groupListDtos.add(groupListDto);
    					}
    				}
    				userListDto.setGroupList(groupListDtos);
    				Map<String, Object> shopParam = new HashMap<String,Object>();
    				shopParam.put("userId", user.getUserId());
    				List<UserShopKey> userShopKeys = userShopMapper.findByParam(shopParam);
    				List<UserShopListDto> userShopListDtos = new ArrayList<UserShopListDto>();
    				if(Help.isNotNull(userShopKeys)){
    					for(UserShopKey userShopKey:userShopKeys){
    						  UserShopListDto userShopListDto = new UserShopListDto();
                              Shop shop = shopMapper.selectByPrimaryKey(userShopKey.getShopId());
                              userShopListDto.setShopName(shop.getNameTxt());
                              userShopListDto.setShopId(userShopKey.getShopId());
                              userShopListDtos.add(userShopListDto);
    					}
    				}
    				userListDto.setUserShopList(userShopListDtos);
    				adminListDtos.add(userListDto);
    			}
    		}
    		result.setStatus(Status.success_status);
    		result.setInfo(Status.success_info);
    		Map<String, Object> data = new HashMap<String, Object>();
    		data.put("page", userListModel.getPage());
    		data.put("pageSize", userListModel.getPageSize());
    		data.put("pageCount", pageCount);
    		data.put("dataList", adminListDtos);
    		result.setData(data);
    		return result;
        }

        @Override
        public Result updateuser(HttpServletRequest request, UserShopModel userShopModel) {
        	Result result = new Result();
    		if(Help.isNull(userShopModel)){
    			log.debug("参数错误！！");
    			result.setStatus(Status.param_error_status);
    			result.setInfo(Status.param_error_info);
    			return result;
    		}
    		String userAccount = userShopModel.getUserName();
    		if(Help.isNull(userAccount)){
    			log.debug("用户名为空！！userName="+userAccount);
    			result.setStatus(Status.useraccount_empty_status);
    			result.setInfo(Status.useraccount_empty_info);
    			return result;
    		}
    		String password = userShopModel.getPassword();
    		if(Help.isNull(password)){
    			log.debug("密码为空！！password="+password);
    			result.setStatus(Status.pwd_error_status);
    			result.setInfo(Status.pwd_error_info);
    			return result;
    		}
    		if (Help.isNull(userShopModel.getShopId())) {
                result.setStatus(Status.shop_noselecterror_status);
                result.setInfo(Status.shop_noselecterror_info);
                return result;
    		}
    		String roleCodes = userShopModel.getRoleCodes();
    		if(Help.isNull(roleCodes)){
    			log.debug("roleCodes为空，roleCodes="+roleCodes);
    			result.setStatus(Status.role_empty_error_status);
    			result.setInfo(Status.role_empty_error_info);
    			return result;
    		}
    		
    		User user = userMapper.selectByPrimaryKey(userShopModel.getUserId());
    		
    		if(!"********".equals(password))
    		user.setUserPwd(MUtil.md5(password));
    		
    		User sessionUser = getSessionUser(request);
    		user.setnUpdateId(sessionUser.getUserId());
    		user.settUpdateTime(new Date());
    		
    		userMapper.updateByPrimaryKey(user);
    		Long userId = Long.parseLong(user.getUserId()+"");
    		
    		UserShopKey usershop = new UserShopKey();
            usershop.setShopId(userShopModel.getShopId());
            usershop.setUserId(user.getUserId());
            userShopMapper.updateByUserId(usershop);
    		
        	userRoleMapper.deleteByUserId(userId);
    		List<String> roles = MUtil.convertStringToList(roleCodes);
    		for(String roleCode:roles){
    			UserRole userRole = new UserRole();
    			userRole.setnUserId(userId);
    			userRole.setnRoleCode(Integer.parseInt(roleCode));
    			userRoleMapper.insertSelective(userRole );
    		}
    		result.setStatus(Status.success_status);
    		result.setInfo(Status.success_info);
    		return result;
        }

        @Override
        public Result addDevice(ShopDeviceModel shopDeviceModel) {
                Result result = new Result();
                if (Help.isNotNull(shopDeviceModel)) {

                        // 判断添加的设备是否已经存在
                        Map<String, Object> probeMacParam = new HashMap<String, Object>();
                        String probemac = shopDeviceModel.getProbemac().replace(":", "");
                        probeMacParam.put("probemac", probemac);
                        int countByParam = shopprobeMapper.countByParam(probeMacParam);
                        if (countByParam > 0) {
                                result.setStatus(Status.device_existerror_status);
                                result.setInfo(Status.device_existerror_info);
                                return result;
                        }

                        Shopprobe shopprobe = new Shopprobe();
                        shopprobe.setProbeMac(probemac);
                        shopprobe.setProbeSn(shopDeviceModel.getProbesn());
                        shopprobe.setShopId(shopDeviceModel.getShopId());
                        shopprobe.setInstallDate(new Date());
                        shopprobe.setActiveInd(FinVal.DEFAULT_ACTIVE_IND);
                        shopprobe.setRssiFilter(FinVal.DEFAULT_RSSI_FILTER);

                        shopprobeMapper.insertSelective(shopprobe);
                        result.setStatus(Status.success_status);
                        result.setInfo(Status.success_info);
                }
                return result;
        }
    @Override
    public Result addCameraDevice(HttpServletRequest request) {
        Result result = new Result();
        String shopId = request.getParameter("shopId");
        String deviceId = request.getParameter("deviceId");
        String cameraHandwareVersion = request.getParameter("handwareVersion");
        String cameraSoftwareVersion = request.getParameter("softwareVersion");
        //判断deviceId是否存在
        Map<String,Object> param = new HashMap<>();
        param.put("device_id",deviceId);
        int count = shopCameraMapper.countByParam(param);
        if(count>0){
            result.setStatus(Status.device_existerror_status);
            result.setInfo(Status.device_existerror_info);
            return result;
        }
        ShopCamera camera = new ShopCamera();
        camera.setCameraHardwareVersion(cameraHandwareVersion);
        camera.setCameraSoftwareVersion(cameraSoftwareVersion);
        camera.setShopId(Integer.parseInt(shopId));
        camera.setDeviceId(deviceId);
        camera.setInstallDate(new Date());
        camera.setActiveStartTime(new Date());
        camera.setActiveEndTime(new Date());
        camera.setActiveInd((byte) 0);
        shopCameraMapper.insert(camera);
        result.setStatus(Status.success_status);
        result.setInfo(Status.success_info);
        return result;
    }

    @Override
    public Result updateCameraDevice(HttpServletRequest request) {
        Result result = new Result();

        String deviceId = request.getParameter("deviceId");
        if(Help.isNull(deviceId)){
            log.debug("设备MAC地址为空！！deviceId="+deviceId);
            result.setStatus(Status.device_macerror_status);
            result.setInfo(Status.device_macerror_info);
            return result;
        }
        ShopCamera camera = new ShopCamera();
        String shopCameraId = request.getParameter("shopCameraId");
        camera.setShopCameraId(Integer.parseInt(shopCameraId));
        String shopId = request.getParameter("shopId");
        String cameraHandwareVersion = request.getParameter("handwareVersion");
        String cameraSoftwareVersion = request.getParameter("softwareVersion");

        camera.setCameraHardwareVersion(cameraHandwareVersion);
        camera.setCameraSoftwareVersion(cameraSoftwareVersion);
        camera.setShopId(Integer.parseInt(shopId));
        camera.setDeviceId(deviceId);
        camera.setInstallDate(new Date());
        camera.setActiveEndTime(new Date());
        camera.setActiveInd((byte) 0);
        camera.setActiveStartTime(new Date());
        shopCameraMapper.updateByPrimaryKey(camera);
        result.setStatus(Status.success_status);
        result.setInfo(Status.success_info);
        return result;
    }

    @Override
        public Result deviceList(HttpServletRequest request, Integer pageSize, Integer page) {
        	  	Result result = new Result();
	        	 User sessionUser = getSessionUser(request);
	             if (Help.isNull(sessionUser)) {
	                     result.setStatus(Status.user_not_exist_error_status);
	                     result.setInfo(Status.user_not_exist_error_info);
	                     return result;
	             }
              
                page = page == null ? FinVal.DEFAULT_PAGE : page;
                pageSize = pageSize == null ? FinVal.DEFAULT_PAGESIZE : pageSize;
                Map<String, Object> param = new HashMap<String, Object>();
                Integer userId = sessionUser.getUserId();
                if (userId != 1) {
                	 param.put("userId", userId);
                }
                List<UserShopKey> userShops = userShopMapper.findByParam(param);
                List<Integer> shopIds = new ArrayList<Integer>();
                if (Help.isNotNull(userShops)) {
                        for (UserShopKey UserShopKey : userShops) {
                                shopIds.add(UserShopKey.getShopId());
                        }
                }
                logger.debug("shopIds==" + shopIds);
                param.put("shopIds", shopIds);
                int countByParam = shopprobeMapper.countByParam(param);
                Paginator paginator = new Paginator(0, pageSize);
                paginator.gotoPage(page);
                int pageCount = paginator.calcPageCount(countByParam); // 总页数

                param.put("startRow", paginator.getStartRow());
                param.put("pageSize", paginator.getPageSize());
                List<Shopprobe> findByParam = shopprobeMapper.findByParam(param);

                List<ShopprobeDto> shopprobeDtos = new ArrayList<ShopprobeDto>();
                if (Help.isNotNull(findByParam)) {
                        for (Shopprobe shopprobe : findByParam) {
                                ShopprobeDto shopprobeDto = new ShopprobeDto();
                                shopprobeDto.setProbeMac(shopprobe.getProbeMac());
                                shopprobeDto.setProbeName(shopprobe.getProbeName());
                                shopprobeDto.setProbeSn(shopprobe.getProbeSn());
                                shopprobeDto.setShopId(shopprobe.getShopId());
                                Shop selectByPrimaryKey = shopMapper.selectByPrimaryKey(shopprobe.getShopId());
                                shopprobeDto.setShopName(selectByPrimaryKey.getNameTxt());
                                shopprobeDto.setShopprobeId(shopprobe.getShopProbeId());
                                shopprobeDtos.add(shopprobeDto);
                        }
                }

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("dataList", shopprobeDtos);
                data.put("page", page);
                data.put("pageSize", pageSize);
                data.put("pageCount", pageCount);
                data.put("total", countByParam);
                result.setStatus(Status.success_status);
                result.setInfo(Status.success_info);
                result.setData(data);
                return result;
        }

        @Override
        public Result updateDevice(HttpServletRequest request, ShopDeviceModel shopDeviceModel) {
        	Result result = new Result();
    		if(Help.isNull(shopDeviceModel)){
    			log.debug("参数错误！！");
    			result.setStatus(Status.param_error_status);
    			result.setInfo(Status.param_error_info);
    			return result;
    		}
    		String probeSn = shopDeviceModel.getProbesn();
    		if(Help.isNull(probeSn)){
    			log.debug("设备序列号为空！！probeSn="+probeSn);
    			result.setStatus(Status.device_snerror_status);
    			result.setInfo(Status.device_snerror_info);
    			return result;
    		}
    		String probeMac = shopDeviceModel.getProbemac();
    		if(Help.isNull(probeMac)){
    			log.debug("设备MAC地址为空！！probeMac="+probeMac);
    			result.setStatus(Status.device_macerror_status);
    			result.setInfo(Status.device_macerror_info);
    			return result;
    		}
    		
    		Shopprobe shopprobe = new Shopprobe();
    		shopprobe.setShopProbeId(shopDeviceModel.getShopprobeId());
    		shopprobe.setProbeMac(shopDeviceModel.getProbemac());
    		shopprobe.setProbeSn(shopDeviceModel.getProbesn());
    		shopprobe.setShopId(shopDeviceModel.getShopId());
    		
    		shopprobeMapper.updateByPrimaryKeySelective(shopprobe);
    		
    		result.setStatus(Status.success_status);
    		result.setInfo(Status.success_info);
    		return result;
        }

        @Override
        public Result delshop(Integer shopId) {
                Result result = new Result();

                if(Help.isNull(shopId)){
                	 result.setStatus(Status.shop_no_exist_status);
                     result.setInfo(Status.shop_no_exist_info);
                     return result;
                }
                
                // 删除门店相关用户
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("shopId", shopId);
                userShopMapper.deleteByShopOrUserId(param);
                shopMapper.deleteByPrimaryKey(shopId);

                result.setStatus(Status.success_status);
                result.setInfo(Status.success_info);
                return result;
        }
        
        @Override
		public Result delDevice(Integer shopprobeId) {
        	 Result result = new Result();

             if(Help.isNull(shopprobeId)){
             	 result.setStatus(Status.device_noexisterror_status);
                  result.setInfo(Status.device_noexisterror_info);
                  return result;
             }
             
             shopprobeMapper.deleteByPrimaryKey(shopprobeId);
             result.setStatus(Status.success_status);
             result.setInfo(Status.success_info);
             return result;
		}

    @Override
    public Result shopCameraList(HttpServletRequest request, Integer pageSize, Integer page) {
        Result result = new Result();
        User sessionUser = getSessionUser(request);
        if (Help.isNull(sessionUser)) {
            result.setStatus(Status.user_not_exist_error_status);
            result.setInfo(Status.user_not_exist_error_info);
            return result;
        }

        page = page == null ? FinVal.DEFAULT_PAGE : page;
        pageSize = pageSize == null ? FinVal.DEFAULT_PAGESIZE : pageSize;
        Map<String, Object> param = new HashMap<String, Object>();
        Integer userId = sessionUser.getUserId();
        if (userId != 1) {
            param.put("userId", userId);
        }
        List<UserShopKey> userShops = userShopMapper.findByParam(param);
        List<Integer> shopIds = new ArrayList<Integer>();
        if (Help.isNotNull(userShops)) {
            for (UserShopKey UserShopKey : userShops) {
                shopIds.add(UserShopKey.getShopId());
            }
        }
        logger.debug("shopIds==" + shopIds);
        param.put("shopIds", shopIds);
        int countByParam = shopCameraMapper.countByParam(param);
        Paginator paginator = new Paginator(0, pageSize);
        paginator.gotoPage(page);
        int pageCount = paginator.calcPageCount(countByParam); // 总页数

        param.put("startRow", paginator.getStartRow());
        param.put("pageSize", paginator.getPageSize());
        List<ShopCamera> shopCameraList = shopCameraMapper.findByParam(param);

        List<ShopCameraDto> shopCameraDtoList = new ArrayList<ShopCameraDto>();
        if (Help.isNotNull(shopCameraList)) {
            for (ShopCamera camera : shopCameraList) {
                ShopCameraDto shopCameraDto = new ShopCameraDto();
                shopCameraDto.setCameraMac(camera.getDeviceId());
                shopCameraDto.setShopCameraId(camera.getShopCameraId());
                shopCameraDto.setInstallDate(camera.getInstallDate());
                shopCameraDto.setShopId(camera.getShopId());
                Shop selectByPrimaryKey = shopMapper.selectByPrimaryKey(camera.getShopId());
                shopCameraDto.setShopName(selectByPrimaryKey.getNameTxt());
                shopCameraDto.setShopCameraId(camera.getShopCameraId());
                shopCameraDto.setHandwareVersion(camera.getCameraHardwareVersion());
                shopCameraDto.setSoftwareVersion(camera.getCameraSoftwareVersion());
                shopCameraDtoList.add(shopCameraDto);
                shopCameraDto.setActiveInd(camera.getActiveInd());
            }
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("dataList", shopCameraDtoList);
        data.put("page", page);
        data.put("pageSize", pageSize);
        data.put("pageCount", pageCount);
        data.put("total", countByParam);
        result.setStatus(Status.success_status);
        result.setInfo(Status.success_info);
        result.setData(data);
        return result;
    }


    @Override
    public Result delCameraDevice(Integer shopCameraId) {
        Result result = new Result();

        if(Help.isNull(shopCameraId)){
            result.setStatus(Status.device_noexisterror_status);
            result.setInfo(Status.device_noexisterror_info);
            return result;
        }

        shopCameraMapper.deleteByPrimaryKey(shopCameraId);
        result.setStatus(Status.success_status);
        result.setInfo(Status.success_info);
        return result;
    }
    @Override
        public Result getUserMenus(HttpServletRequest request) {
                // TODO Auto-generated method stub
                Result result = new Result();
                User loginUser = getSessionUser(request);
                List<MenuDto> menus = null;
                if (Help.isNotNull(loginUser)) {
                        Integer id = loginUser.getUserId();
                        menus = getMenus(id);
                }
                result.setStatus(Status.success_status);
                result.setInfo(Status.success_info);
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("menus", menus);
                result.setData(data);
                return result;
        }

        private List<MenuDto> getMenus(Integer userId) {
                Map<Object, Object> map = new HashMap<Object, Object>();
                List<Menu> userMenus = null;
                if (Help.isNotNull(userId)) {
                	userMenus = menuMapper.findUserMenusByUserId(Long.parseLong(userId+""));
                }
                if (Help.isNotNull(userMenus)) {
                        for (Menu menu : userMenus) {
                                Long parentMenuId = menu.getnParentMenuId();
                                List<Menu> menus = (List<Menu>) map.get(parentMenuId);
                                if (Help.isNull(menus)) {
                                        menus = new ArrayList<Menu>();
                                }
                                menus.add(menu);
                                map.put(parentMenuId, menus);
                        }
                }
                return convertMenus(userId,map, 0l);
        }

        private List<MenuDto> convertMenus(Integer userId,Map<Object, Object> menuMap, Long parentMenuId) {
                List<MenuDto> menuDtos = new ArrayList<MenuDto>();
                if (Help.isNotNull(menuMap)) {
                        List<Menu> menus = (List<Menu>) menuMap.get(parentMenuId);
                        if (Help.isNotNull(menus)) {
                                for (Menu menu : menus) {
                                    MenuDto menuDto = new MenuDto();
                                    menuDto.setMenuIcon(menu.getcMenuIcon());
                                    menuDto.setMenuName(menu.getcMenuName());
                                    menuDto.setMenuUrl(menu.getcMenuUrl());
                                    menuDto.setSubMenus(convertMenus(userId,menuMap, menu.getnMenuId()));
                                    menuDtos.add(menuDto);
                                }
                        }
                }
                return menuDtos;
        }

		@Override
		public Result groupList(GroupListModel groupListModel) {

			Result result = new Result();
			Map<String, Object> param= new HashMap<String,Object>();
			int pageCount =1;
			List<GroupListDto> groupListDtos = new ArrayList<GroupListDto>();
			Map<String, Object> data = new HashMap<String, Object>();
			int count = 0;
			if(Help.isNotNull(groupListModel)){
			roleMapper.countAdminGroupByParam();
			count = roleMapper.countAdminGroupByParam();
			/**** 分页判断begin *****/
			Paginator paginator = new Paginator(0, groupListModel.getPageSize());
			paginator.gotoPage(groupListModel.getPage());
			pageCount = paginator.calcPageCount(count); // 总页数
			
			/**** 分页判断end *****/
			param.put("startRow", paginator.getStartRow());
			param.put("pageSize", paginator.getPageSize());
			
			data.put("page", groupListModel.getPage());
			data.put("pageSize", groupListModel.getPageSize());
			}
			List<Role> roles = roleMapper.findAdminGroupByParam(param);
			
			if(Help.isNotNull(roles)){
				for(Role role:roles){
					GroupListDto groupListDto = new GroupListDto();
					groupListDto.setGroupDesc(role.getcRoleDesc());
					groupListDto.setGroupName(role.getcRoleName());
					groupListDto.setGroupId(role.getnRoleId());
					groupListDto.setGroupCode(role.getnRoleCode());
					groupListDtos.add(groupListDto);
				}
			}
			
			result.setStatus(Status.success_status);
			result.setInfo(Status.success_info);
			
			data.put("pageCount", pageCount);
			data.put("dataList", groupListDtos);
			data.put("count", count);
			result.setData(data);
			return result;
		
		}

		@Override
		public Result menuTree(Long getnUserId, Long roleCode) {
			Result result = new Result();
			List<Menu> userMenus = null;
			if(Help.isNotNull(getnUserId)){
				userMenus = menuMapper.findUserMenusByUserId(getnUserId);
			}else{
				userMenus = menuMapper.findAll();
			}
			List<MenuTreeDto> menuTreeDtos = new ArrayList<MenuTreeDto>();
			List<Menu> roleMenus =null;
			if(Help.isNotNull(roleCode)){
				roleMenus = menuMapper.findUserMenusByRoleCode(roleCode);
			}
			if(Help.isNotNull(userMenus)){
				for(Menu userMenu:userMenus){
					MenuTreeDto menuTreeDto = new MenuTreeDto();
					menuTreeDto.setId(userMenu.getnMenuId());
					menuTreeDto.setpId(userMenu.getnParentMenuId());
					menuTreeDto.setName(userMenu.getcMenuName());
					if(Help.isNotNull(roleMenus)&&roleMenus.contains(userMenu)){
						menuTreeDto.setChecked(true);
					}
					menuTreeDto.setOpen(true);
					menuTreeDtos.add(menuTreeDto);
				}
			}
			result.setStatus(Status.success_status);
			result.setInfo(Status.success_info);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("dataList", menuTreeDtos);
			result.setData(data);
			return result;
		}

		@Override
		public Result roleDetail(Long groupId) {
			Result result= new Result();
			Role role = roleMapper.selectByPrimaryKey(groupId);
			if(Help.isNull(role)){
				result.setStatus(Status.role_not_exist_status);
				result.setInfo(Status.role_not_exist_info);
				return result;
			}
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("roleDetail", role);
			result.setData(data);
			result.setStatus(Status.success_status);
			result.setInfo(Status.success_info);
			return result;
		}

		@Override
		public Result addGroup(HttpServletRequest request,
				AddGroupModel addGroupModel) {
			// TODO Auto-generated method stub
			Result result = new Result();
			log.debug("参数=="+addGroupModel);
			if(Help.isNull(addGroupModel)){
				log.debug("参数错误！！");
				result.setStatus(Status.param_error_status);
				result.setInfo(Status.param_error_info);
				return result;
			}
			String groupName = addGroupModel.getGroupName();
			if(Help.isNull(groupName)){
				log.debug("groupName为空，groupName="+groupName);
				result.setStatus(Status.group_name_empty_status);
				result.setInfo(Status.group_name_empty_info);
				return result;
			}
			String groupDesc = addGroupModel.getGroupDesc();
			if(Help.isNull(groupDesc)){
				log.debug("groupDesc为空，groupDesc="+groupDesc);
				result.setStatus(Status.group_desc_empty_status);
				result.setInfo(Status.group_desc_empty_info);
				return result;
			}
			String menuIdsStr = addGroupModel.getMenuIdsStr();
			List<String> menuIds = MUtil.convertStringToList(menuIdsStr);
			if(Help.isNull(menuIds)){
				log.debug("menuIds为空，menuIds="+menuIds);
				result.setStatus(Status.group_permission_empty_status);
				result.setInfo(Status.group_permission_empty_info);
				return result;
			}
			Role role = roleMapper.findByName(groupName);
			if(Help.isNotNull(role)){
				log.debug("groupName已经存在，groupName="+groupName);
				result.setStatus(Status.group_name_exist_status);
				result.setInfo(Status.group_name_exist_info);
				return result;
			}
			log.debug("验证参数成功，插入群组信息");
			role=new Role();
			role.setcRoleDesc(groupDesc);
			role.setcRoleName(groupName);
			Integer maxRoleCode = roleMapper.maxRoleCode();
			if(Help.isNull(maxRoleCode)){
				maxRoleCode=100;
			}else{
				maxRoleCode=maxRoleCode+1;
			}
			role.setnRoleCode(Long.parseLong(maxRoleCode.toString()));
			User sessionUser = getSessionUser(request);
			Long userId = Long.parseLong(sessionUser.getUserId()+"");
			Date now = new Date();
			role.setnCreateBy(userId);
			role.setnUpdateBy(userId);
			role.settCreateTime(now);
			role.settUpdateTime(now);
			role.setnSystemRole(0);
			roleMapper.insertSelective(role);
			Long roleCode = role.getnRoleCode();
			log.debug("插入群组信息成功");
			log.debug("开始添加权限信息。。。");
			addGroupPermissoin(menuIds, userId, roleCode);
			result.setStatus(Status.success_status);
			result.setInfo(Status.success_info);
			return result;
		}
		//添加权限
	  	private void addGroupPermissoin(List<String> menuIds,Long userId,Long roleCode){
	  		for(String menuId:menuIds){
	  			RoleMenu roleMenu = new RoleMenu();
	  			roleMenu.setnCreateBy(userId);
	  			roleMenu.setnRoleCode(roleCode);
	  			roleMenu.setnMenuId(Long.parseLong(menuId));
	  			roleMenu.setnStatus(1);
	  			roleMenu.setnUpdateBy(userId);
	  			roleMenu.settCreateTime(new Date());
	  			roleMenu.settUpdateTime(new Date());
	  			roleMenuMapper.insertSelective(roleMenu);
	  			
	  			List<MenuPermission> menuPermissions = menuPermissionMapper.findByMenuId(Long.parseLong(menuId));
	  			if(Help.isNotNull(menuPermissions)){
	  				for(MenuPermission menuPermission:menuPermissions){
	  					Long permissionId = menuPermission.getnPermissionId();
	  				    RolePermission rolerPermission = new RolePermission();
	  				    rolerPermission.setnPermissionId(permissionId);
	  				    rolerPermission.setnRoleCode(roleCode);
	  					rolePermissionMapper.insertSelective(rolerPermission);
	  				}
	  			}
	  		}
	  	}

		@Override
		public Result delGroup(Long groupId) {

			// TODO Auto-generated method stub
			Result result = new Result();
			if(Help.isNull(groupId)){
				log.debug("groupId=="+groupId);
				result.setStatus(Status.group_no_exist_status);
				result.setInfo(Status.group_no_exist_info);
				return result;
			}
			Role role = roleMapper.selectByPrimaryKey(groupId);
			if(Help.isNull(role)){
				result.setStatus(Status.group_no_exist_status);
				result.setInfo(Status.group_no_exist_info);
				return result;
			}
			Integer systemRole = role.getnSystemRole();
			if(systemRole==1){
				result.setStatus(Status.system_role_opera_error_status);
				result.setInfo(Status.system_role_opera_error_info);
				return result;
			}
			Long roleCode = role.getnRoleCode();
			roleMapper.deleteByPrimaryKey(groupId);
			roleMenuMapper.deleteByRoleCode(roleCode );
			rolePermissionMapper.deleteByRoleCode(roleCode);
			userRoleMapper.deleteByRoleCode(roleCode);
			result.setStatus(Status.success_status);
			result.setInfo(Status.success_info);
			return result;
		
		}

		@Override
		public Result modifyGroup(HttpServletRequest request,
				ModifyGroupModel modifyGroupModel) {
			// TODO Auto-generated method stub
			log.debug("modifyGroupModel=="+modifyGroupModel);
			Result result = new Result();
			if(Help.isNull(modifyGroupModel)){
				log.debug("参数错误！！");
				result.setStatus(Status.param_error_status);
				result.setInfo(Status.param_error_info);
				return result;
			}
			Long groupId = modifyGroupModel.getGroupId();
			if(Help.isNull(groupId)){
				log.debug("groupId=="+groupId);
				result.setStatus(Status.group_no_exist_status);
				result.setInfo(Status.group_no_exist_info);
				return result;
			}
			Role role = roleMapper.selectByPrimaryKey(groupId);
			if(Help.isNull(role)){
				result.setStatus(Status.group_no_exist_status);
				result.setInfo(Status.group_no_exist_info);
				return result;
			}
			Integer systemRole = role.getnSystemRole();
			if(systemRole==1){
				result.setStatus(Status.system_role_opera_error_status);
				result.setInfo(Status.system_role_opera_error_info);
				return result;
			}
			String groupName = modifyGroupModel.getGroupName();
			if(Help.isNull(groupName)){
				log.debug("groupName为空，groupName="+groupName);
				result.setStatus(Status.group_name_empty_status);
				result.setInfo(Status.group_name_empty_info);
				return result;
			}
			String groupDesc = modifyGroupModel.getGroupDesc();
			if(Help.isNull(groupDesc)){
				log.debug("groupDesc为空，groupDesc="+groupDesc);
				result.setStatus(Status.group_desc_empty_status);
				result.setInfo(Status.group_desc_empty_info);
				return result;
			}
			String menuIdsStr = modifyGroupModel.getMenuIdsStr();
			List<String> menuIds = MUtil.convertStringToList(menuIdsStr);
			if(Help.isNull(menuIds)){
				log.debug("menuIds为空，menuIds="+menuIds);
				result.setStatus(Status.group_permission_empty_status);
				result.setInfo(Status.group_permission_empty_info);
				return result;
			}
			Role r = roleMapper.findByName(groupName);
			if(Help.isNotNull(r)&&!r.getnRoleId().equals(role.getnRoleId())){
				log.debug("groupName已经存在，groupName="+groupName);
				result.setStatus(Status.group_name_exist_status);
				result.setInfo(Status.group_name_exist_info);
				return result;
			}
			role.setcRoleName(groupName);
			role.setcRoleDesc(groupDesc);
			roleMapper.updateByPrimaryKeySelective(role);
			Long roleCode = role.getnRoleCode();
			log.debug("删除roleCode的关联关系=="+roleCode);
			roleMenuMapper.deleteByRoleCode(roleCode);
			rolePermissionMapper.deleteByRoleCode(roleCode);
			User sessionUser = getSessionUser(request);
			addGroupPermissoin(menuIds, Long.parseLong(sessionUser.getUserId()+""), roleCode);
			result.setStatus(Status.success_status);
			result.setInfo(Status.success_info);
			return result;
		}

		@Override
		public Result industrys(HttpServletRequest request, Integer pageSize,
				Integer page) {

			Result result = new Result();
			Map<String, Object> param= new HashMap<String,Object>();
			int pageCount =1;
			List<IndustryDto> industryDtos = new ArrayList<IndustryDto>();
			Map<String, Object> data = new HashMap<String, Object>();
			int count = 0;
			count = industryMapper.countByParam(param);
			/**** 分页判断begin *****/
			Paginator paginator = new Paginator(0, pageSize);
			paginator.gotoPage(page);
			pageCount = paginator.calcPageCount(count); // 总页数
			
			/**** 分页判断end *****/
			param.put("startRow", paginator.getStartRow());
			param.put("pageSize", paginator.getPageSize());
			
			data.put("page", page);
			data.put("pageSize", pageSize);
			List<Industry> industrys = industryMapper.findByParam(param);
			
			if(Help.isNotNull(industrys)){
				for(Industry industry:industrys){
					IndustryDto industryDto = new IndustryDto();
					industryDto.setIndustryId(industry.getId());
					industryDto.setName(industry.getName());
					industryDtos.add(industryDto);
				}
			}
			
			result.setStatus(Status.success_status);
			result.setInfo(Status.success_info);
			
			data.put("pageCount", pageCount);
			data.put("dataList", industryDtos);
			data.put("count", count);
			result.setData(data);
			return result;
		
		}

		@Override
		public Result deluser(Integer userId) {
            Result result = new Result();

            if(Help.isNull(userId)){
            	 result.setStatus(Status.user_not_exist_error_status);
                 result.setInfo(Status.user_not_exist_error_info);
                 return result;
            }
            
            // 删除门店相关用户
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", userId);
            userShopMapper.deleteByShopOrUserId(param);
            userMapper.deleteByPrimaryKey(userId);

            result.setStatus(Status.success_status);
            result.setInfo(Status.success_info);
            return result;
    }

		


}
