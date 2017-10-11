package com.owl.wifi.web.face.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.owl.wifi.util.Result;
import com.owl.wifi.web.face.model.UserModel;

public interface FaceService {
        public Result PushFaceToUser(UserModel userModel) throws ParseException;

        public long countShopFaceList(Integer shopId, Date startDate, Date endDate);

        public List<Map<String, Object>> shopFaceList(Integer shopId, Date startDate, Date endDate, int startRow,
                        int pageSize);
}
