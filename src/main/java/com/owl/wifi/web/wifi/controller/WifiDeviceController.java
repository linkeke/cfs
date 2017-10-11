package com.owl.wifi.web.wifi.controller;

import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.talkingdata.service.TalkingdataQueryService;
import com.owl.wifi.web.wifi.model.WifiDeviceModel;
import com.owl.wifi.web.wifi.service.ProbeDeviceService;
import com.owl.wifi.web.wifi.service.WifiDeviceService;

@RestController
public class WifiDeviceController {

        BKLogger logger = BKLogger.getLogger(WifiDeviceController.class);
        private static final Logger log = LoggerFactory.getLogger(WifiDeviceController.class);

        @Autowired
        private WifiDeviceService wifiDeviceService;

        @Autowired
        private ProbeDeviceService probeDeviceService;

        @Autowired
        TalkingdataQueryService talkingdataQueryService;

        @RequestMapping("/lierda-dataupload")
        public Object ledDataupload(HttpServletRequest request, Model model) {
                Result result = new Result();
                try {
                        Reader reader = new InputStreamReader(request.getInputStream());
                        JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                        log.info("利尔达数据：" + json.get("data"));
                } catch (Exception ex) {
                        result = new Result();
                        log.error("上传探测的数据失败", ex);
                }
                return logger.infobk("上传探测的数据", result);
        }

        /**
         * 设备登录连接服务器接口
         */
        @RequestMapping("/devicelogin")
        public Object devicelogin(HttpServletRequest request, Model model) {
                Result result = new Result();
                try {
                        Reader reader = new InputStreamReader(request.getInputStream());
                        WifiDeviceModel jsonToObject = new Gson().fromJson(new JsonReader(reader),
                                        WifiDeviceModel.class);
                        result = wifiDeviceService.saveWifiDevice(jsonToObject);
                } catch (Exception ex) {
                        result = new Result();
                        log.error("设备登录连接服务器失败", ex);
                }
                return logger.infobk("设备登录连接服务器", result);
        }

        /**
         * 上传数据接口
         */
        @RequestMapping("/dataupload")
        public Object dataupload(HttpServletRequest request, Model model) {
                Result result = new Result();
                try {
                        Reader reader = new InputStreamReader(request.getInputStream());
                        JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                        if (json.get("data") != null) {
                                result = probeDeviceService.saveProbeDevice(json.get("data").getAsJsonArray());
                        }
                } catch (Exception ex) {
                        result = new Result();
                        log.error("上传探测的数据失败", ex);
                }
                return logger.infobk("上传探测的数据", result);
        }

        @RequestMapping("/devicetrace")
        public Object devicetrace(HttpServletRequest request, Model model) {
                Result result = new Result();
                try {
                        Reader reader = new InputStreamReader(request.getInputStream());
                        JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                        System.out.println(json);
                } catch (Exception ex) {
                        result = new Result();
                        log.error("xxxx", ex);
                }
                return logger.infobk("xxxx", result);
        }

        @RequestMapping("/query")
        public Result query(String macs) {
                Result result = new Result();
                for (String s : macs.split("#")) {
                        try {
                                talkingdataQueryService.queryTalkingData(s);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
                return result;
        }
}
