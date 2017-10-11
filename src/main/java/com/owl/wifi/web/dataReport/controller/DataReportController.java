package com.owl.wifi.web.dataReport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owl.wifi.content.Status;
import com.owl.wifi.util.Result;
import com.owl.wifi.util.WordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.owl.wifi.log.BKLogger;
import com.owl.wifi.web.advertise.controller.AdvertiseController;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class DataReportController {
	BKLogger logger = BKLogger.getLogger(AdvertiseController.class);
	private static final Logger log= LoggerFactory.getLogger(AdvertiseController.class);
	
	@RequestMapping("/qiqi_report.do")
	public String addQiQiPage(HttpServletRequest request,Model model){
         return "dataReport/qiqi_report";
	 }
    @RequestMapping("/shengangao_report.do")
    public String addSGAPage(HttpServletRequest request,Model model){
        return "dataReport/shengangao_report";
    }
    @RequestMapping("/report.do")
    public String addReportPage(HttpServletRequest request,Model model){


        final String DATE_FMT_STR = "yyyy-MM-dd";
        Calendar cal = Calendar.getInstance();
        {
            cal.add(Calendar.DATE, -6);
        }
        request.setAttribute("startDate", new SimpleDateFormat(DATE_FMT_STR).format(cal.getTime()));
        request.setAttribute("endDate", new SimpleDateFormat(DATE_FMT_STR).format(new Date()));


        return  "dataReport/report";
    }
    @RequestMapping(value = "/uploadReport.do")
    @ResponseBody
    public Object createUploadReport(HttpServletRequest request,HttpServletResponse response,String base64Map) throws IOException {
        Result result = new Result();
        Map<String, String[]> map = request.getParameterMap();
        Map<String,Object> param = new HashMap<>();
        for(Map.Entry entry:map.entrySet()){
            String key = (String) entry.getKey();
            String[] value = (String[]) entry.getValue();
            param.put(key,value[0]);
        }
        String filePath = WordUtil.exportMillCertificateWord(request, response, param);
        result.setStatus(Status.success_status);
        result.setInfo(Status.success_info);
        result.setData(filePath);
        return result;
    }

    public  boolean base64ToImageFile(String base64, String path) throws IOException {// 对字节数组字符串进行Base64解码并生成图片
        // 生成jpeg图片
        try {
            OutputStream out = new FileOutputStream(path);
            return base64ToImageOutput(base64, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    public  boolean base64ToImageOutput(String base64, OutputStream out) throws IOException {
        if (base64 == null) { // 图像数据为空
            return false;
        }
        try {
            // Base64解码
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out.write(bytes);
            out.flush();
            return true;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
