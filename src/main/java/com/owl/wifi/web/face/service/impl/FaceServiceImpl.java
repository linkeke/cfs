package com.owl.wifi.web.face.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zdst.fireAlarm.push.ZdstPush;

import com.owl.wifi.content.Status;
import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.GsonUtil;
import com.owl.wifi.util.Help;
import com.owl.wifi.util.MUtil;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.dao.FaceLipsCodeMapper;
import com.owl.wifi.web.dao.FaceMapper;
import com.owl.wifi.web.dao.FaceRaceCodeMapper;
import com.owl.wifi.web.dao.ShopCameraMapper;
import com.owl.wifi.web.dao.ShopFaceMapper;
import com.owl.wifi.web.dao.ShopMapper;
import com.owl.wifi.web.entity.Face;
import com.owl.wifi.web.entity.FaceLipsCode;
import com.owl.wifi.web.entity.FaceRaceCode;
import com.owl.wifi.web.entity.Shop;
import com.owl.wifi.web.entity.ShopCamera;
import com.owl.wifi.web.entity.ShopFace;
import com.owl.wifi.web.face.dto.FaceDto;
import com.owl.wifi.web.face.dto.ShopFaceDto;
import com.owl.wifi.web.face.model.UserModel;
import com.owl.wifi.web.face.service.FaceService;
import com.owl.wifi.web.user.service.impl.UserServiceImpl;

@Service
@Transactional
public class FaceServiceImpl implements FaceService {

	  BKLogger logger = BKLogger.getLogger(UserServiceImpl.class);
      private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
      
      @Autowired
      private ShopFaceMapper shopFaceMapper;
      
      @Autowired
      private  FaceMapper faceMapper;
      
      @Autowired
      private FaceLipsCodeMapper faceLipsCodeMapper;
      
      @Autowired
      private FaceRaceCodeMapper faceRaceCodeMapper;
      
      @Autowired
      private  ShopMapper shopMapper;
      
      @Autowired
      private  ShopCameraMapper shopCameraMapper;
      
	@Override
	public Result PushFaceToUser(UserModel userModel) throws ParseException {
		
		Result result = new Result();
		if(Help.isNull(userModel)){
			log.debug("参数错误！！");
			result.setStatus(Status.param_error_status);
			result.setInfo(Status.param_error_info);
			return result;
		}
		
		Integer shopId = userModel.getShopId();
		if(Help.isNull(shopId)){
			log.debug("门店ID为空！！shopId="+shopId);
			result.setStatus(Status.shopid_null_error_status);
			result.setInfo(Status.shopid_null_error_info);
			return result;
		}
		
		Integer faceId = userModel.getFaceId();
		if(Help.isNull(faceId)){
			log.debug("人脸ID为空！！faceId="+faceId);
			result.setStatus(Status.faceid_null_error_status);
			result.setInfo(Status.faceid_null_error_info);
			return result;
		}
		
		
		String facetime = userModel.getTime();
		if(Help.isNull(facetime)){
			log.debug("人脸补货时间为空！！facetime="+facetime);
			result.setStatus(Status.facetime_null_error_status);
			result.setInfo(Status.facetime_null_error_info);
			return result;
		}
		ZdstPush zdstPush = new ZdstPush("localhost","8124");
		Map<String, Object> pushMsgDto = new HashMap<String, Object>();
		
		Face face = faceMapper.selectByPrimaryKey(faceId);
		Byte ageVal = face.getAgeVal();
		Byte maleInd = face.getMaleInd();
		String imagesTxt = face.getImagesTxt();
		Byte glassesInd = face.getGlassesInd();
		
		Short lipsId = face.getLipsId();
		FaceLipsCode faceLipsCode = faceLipsCodeMapper.selectByPrimaryKey(lipsId);
		String lipsTxt = faceLipsCode.getLipsTxt();
		
		Short raceId = face.getRaceId();
		FaceRaceCode race = faceRaceCodeMapper.selectByPrimaryKey(raceId);
		String raceTxt = race.getRaceTxt();
		
		pushMsgDto.put("userName", shopId);
		pushMsgDto.put("faceImg", imagesTxt);
		pushMsgDto.put("sex", maleInd);
		pushMsgDto.put("ageVal", ageVal);
		pushMsgDto.put("glassesInd", glassesInd);
		pushMsgDto.put("lipsTxt", lipsTxt);
		pushMsgDto.put("raceTxt", raceTxt);
		pushMsgDto.put("realTime", facetime);
		
		  Map<String, Object> param = new HashMap<String, Object>();
		 param.put("faceId", faceId);
	
	     param.put("startRow", 0);
	     param.put("pageSize", 3);
	     List<ShopFace> shopFaces = shopFaceMapper.findByParam(param);
	     List<ShopFaceDto> shopFaceDtos = new ArrayList<ShopFaceDto>();
	     if(Help.isNotNull(shopFaces)){
				for(ShopFace shopFace:shopFaces){
					ShopFaceDto shopFaceDto = new ShopFaceDto();
					
					shopFaceDto.setFaceId(Long.parseLong(shopFace.getFaceId()+""));
					
					Face selectByPrimaryKey = faceMapper.selectByPrimaryKey(shopFace.getFaceId());	
					String imagesTxt2 = selectByPrimaryKey.getImagesTxt();
					if(Help.isNotNull(imagesTxt2)){
						String[] split = imagesTxt2.split(",");
						shopFaceDto.setImgone("facePhotos/"+split[0]);
						List<String> imgs = new ArrayList<String>();
						for(String img:split){
							imgs.add("facePhotos/"+img);
						}
						shopFaceDto.setImages(imgs);
					}
					
					Integer shopCameraId = shopFace.getShopCameraId();
					ShopCamera shopCamera = shopCameraMapper.selectByPrimaryKey(shopCameraId);
					Integer shopid = shopCamera.getShopId();
					Shop shop = shopMapper.selectByPrimaryKey(shopid);
					shopFaceDto.setShopName(shop.getNameTxt());
					
					String visitdate = MUtil.formDateFormat(shopFace.getVisitDate(), "yyyy-MM-dd") + " " +MUtil.formDateFormat(shopFace.getVisitTime(), "HH:mm:ss");
					shopFaceDto.setVisitdate(visitdate);
					shopFaceDtos.add(shopFaceDto);
				} 
			}
	     pushMsgDto.put("shopFaces", GsonUtil.objectToJson(shopFaceDtos));
		
		
		List<Map<String, Object>> shopFaceList = shopFaceMapper.shopFaceList(shopId, null, null, 0, 10);
		List<FaceDto> faces = new ArrayList<FaceDto>();
		if(Help.isNotNull(shopFaceList)){
			for(Map<String, Object> shopFace:shopFaceList){
				FaceDto faceDto = new FaceDto();
				
				Long tempfaceId = (Long) shopFace.get("face_id");
				String images = (String) shopFace.get("images_txt");
				Integer age = (Integer) shopFace.get("age_val");
				Integer male = (Integer) shopFace.get("male_ind");
				String visitdate = shopFace.get("visit_date") + " " +shopFace.get("visit_time");
				
				faceDto.setFaceId(tempfaceId);
				faceDto.setAge(age);
				faceDto.setVisitdate(visitdate);
				faceDto.setMaleInd(male);
				if(Help.isNotNull(images)){
					String[] split = images.split(",");
					List<String> imgs = new ArrayList<String>();
					for(String img:split){
						imgs.add("facePhotos/"+img);
					}
					faceDto.setImages(imgs);
				}
				
				faces.add(faceDto);
			} 
		}
		
		pushMsgDto.put("recentFace", GsonUtil.objectToJson(faces));
		zdstPush.sendMsg(pushMsgDto );
		
		
		log.debug("推送数据到页面："+new Date());
		
		result.setInfo(Status.success_info);
		result.setStatus(Status.success_status);
		return result;
	}

      
	  public long countShopFaceList(Integer shopId, Date startDate, Date endDate) { 
          return shopFaceMapper.count_shopFaceList(shopId, startDate, endDate);
	  }

	  public List<Map<String, Object>> shopFaceList(Integer shopId, Date startDate, Date endDate, int startRow,
                  int pageSize) {
          return shopFaceMapper.shopFaceList(shopId, startDate, endDate, startRow, pageSize);
  	  }
      
}
