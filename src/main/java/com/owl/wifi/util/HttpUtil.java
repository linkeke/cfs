package com.owl.wifi.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.sf.json.JSONObject;

public class HttpUtil {
    
    //private static BKLogger log = BKLogger.getLogger(HttpPostUtil.class);
    
    URL url;  
    String method;
    HttpURLConnection conn;  
    String boundary = "--------http post-------";  
    Map<String, String> textParams = new HashMap<String, String>();  
    Map<String, File> fileparams = new HashMap<String, File>();  
    DataOutputStream ds;  
    
    public HttpUtil(String url,String method) throws Exception {  
        this.url = new URL(url);  
        this.method=method;
    }  
    //重新设置要请求的服务器地址，即上传文件的地址。  
    public void setUrl(String url) throws Exception {  
        this.url = new URL(url);  
    }  
    //增加一个普通字符串数据到form表单数据中  
    public void addTextParameter(String name, String value) {  
        textParams.put(name, value);  
    }  
    //增加一个文件到form表单数据中  
    public void addFileParameter(String name, File value) {  
        fileparams.put(name, value);  
    }  
    // 清空所有已添加的form表单数据  
    public void clearAllParameters() {  
        textParams.clear();  
        fileparams.clear();  
    }  
    
    // 发送数据到服务器，返回一个字节包含服务器的返回结果的数组  
    public byte[] send() throws Exception {  
        initConnection();  
        try {  
            conn.connect();  
        } catch (SocketTimeoutException e) {  
            // something  
            throw new RuntimeException();  
        }  
        ds = new DataOutputStream(conn.getOutputStream());  
        writeFileParams();  
        writeStringParams();  
        paramsEnd();  
        InputStream in = conn.getInputStream();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int b;  
        while ((b = in.read()) != -1) {  
            out.write(b);  
        }  
        conn.disconnect();  
        return out.toByteArray();  
    }  

    //文件上传的connection的一些必须设置  
    private void initConnection() throws Exception {  
        conn = (HttpURLConnection) this.url.openConnection();  
        conn.setDoOutput(true);  
        conn.setUseCaches(false);  
        conn.setConnectTimeout(30000); //连接超时为10秒  
        conn.setRequestMethod(method);  
        conn.setRequestProperty("Content-Type",  
                "multipart/form-data; boundary=" + boundary);  
    }
    
    //文件传输类型动态设置  
    private void initConnectionType() throws Exception {  
        conn = (HttpURLConnection) this.url.openConnection();  
        conn.setDoOutput(true);  
        conn.setUseCaches(false);  
        conn.setConnectTimeout(30000); //连接超时为10秒  
        conn.setRequestMethod(method); 
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
    }
    
    //普通字符串数据  
    private void writeStringParams() throws Exception {  
        Set<String> keySet = textParams.keySet();  
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {  
            String name = it.next();  
            String value = textParams.get(name);  
            ds.writeBytes("--" + boundary + "\r\n");  
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name  
                    + "\"\r\n");  
            ds.writeBytes("\r\n");  
            ds.writeBytes(encode(value) + "\r\n");  
        }  
    } 
    
    //文件数据  
    private void writeFileParams() throws Exception {  
        Set<String> keySet = fileparams.keySet();  
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {  
            String name = it.next();  
            File value = fileparams.get(name);  
            ds.writeBytes("--" + boundary + "\r\n");  
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name  
                    + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");  
            ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");  
            ds.writeBytes("\r\n");  
            ds.write(getBytes(value));  
            ds.writeBytes("\r\n");  
        }  
    }
    
    //普通字符串数据  、文件数据  
    private void writeParams(Map<String, Object> params) throws Exception {  
        Set<String> keySet = params.keySet();  
        
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {  
            String name = it.next();  
            Object param = params.get(name);
            
            if(param instanceof File){
            
                File value = (File)param;  
                ds.writeBytes("--" + boundary + "\r\n");  
                ds.writeBytes("Content-Disposition: form-data; name=\"" + name  
                        + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");  
                ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");  
                ds.writeBytes("\r\n");  
                ds.write(getBytes(value));  
                ds.writeBytes("\r\n");  
            }else {
                
                String value = (String)param;  
                ds.writeBytes("--" + boundary + "\r\n");  
                ds.writeBytes("Content-Disposition: form-data; name=\"" + name  
                        + "\"\r\n");  
                ds.writeBytes("\r\n");  
                ds.writeBytes(encode(value) + "\r\n");
            }
        }  
    }
    
    //获取文件的上传类型，图片格式为image/png,image/jpg等。非图片为application/octet-stream  
    private String getContentType(File f) throws Exception {  
          
        //      return "application/octet-stream";  // 此行不再细分是否为图片，全部作为application/octet-stream 类型  
        ImageInputStream imagein = ImageIO.createImageInputStream(f);  
        if (imagein == null) {  
            return "application/octet-stream";  
        }  
        Iterator<ImageReader> it = ImageIO.getImageReaders(imagein);  
        if (!it.hasNext()) {  
            imagein.close();  
            return "application/octet-stream";  
        }  
        imagein.close();  
        return "image/" + it.next().getFormatName().toLowerCase();//将FormatName返回的值转换成小写，默认为大写  
  
    }  
    //把文件转换成字节数组  
    private byte[] getBytes(File f) throws Exception {  
        FileInputStream in = new FileInputStream(f);  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        byte[] b = new byte[1024];  
        int n;  
        while ((n = in.read(b)) != -1) {  
            out.write(b, 0, n);  
        }  
        in.close();  
        return out.toByteArray();  
    }  
    //添加结尾数据  
    private void paramsEnd() throws Exception {  
        ds.writeBytes("--" + boundary + "--" + "\r\n");  
        ds.writeBytes("\r\n");  
    }  
    // 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码  
    private String encode(String value) throws Exception{  
        return URLEncoder.encode(value, "UTF-8");  
    }  
    
    /**
     * 发送混合数据
     * @param params
     * @return
     * @throws Exception
     */
    public byte[] sendData(Map<String, Object> params) throws Exception {  
        initConnection();  
        try {  
            conn.connect();  
        } catch (SocketTimeoutException e) {  
            // something  
            throw new RuntimeException();  
        }  
        
        
        ds = new DataOutputStream(conn.getOutputStream());  
        writeParams(params);
        paramsEnd();  
        InputStream in = conn.getInputStream();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int b;  
        while ((b = in.read()) != -1) {  
            out.write(b);  
        } 
        out.flush();
        conn.disconnect();  
        return out.toByteArray();  
    } 
    
    /**
     * 发送Json数据
     * @param params
     * @return
     * @throws Exception
     */ 
    public String sendJsonData(String json) throws Exception {  
    	initConnectionType();  
        try {  
            conn.connect();  
        } catch (SocketTimeoutException e) {  
            // something  
            throw new RuntimeException();  
        }         
             
        OutputStream  out = conn.getOutputStream();    
        out.write(json.getBytes("utf-8"));     
        out.flush();
        out.close();
         
        InputStream in = conn.getInputStream(); 
//        byte[] data1 = new byte[in.available()];
//        in.read(data1);
        
        int count = 0;  
        while (count == 0) {  
            count = in.available();  
        }  
        byte[] data1 = new byte[count];  
        in.read(data1);  
       
        
        // 转成字符串
        String result = new String(data1,"UTF-8");
        
        conn.disconnect();  
        return result;  
    }  
    
    public static void main(String[] args) throws Exception {  
    	
    	SslUtils.ignoreSsl();
    	
    	
    	 HttpUtil u = new HttpUtil("https://api.xinyuead.cn/api/session","POST");
 		u.addTextParameter("is_inner", "2");
 		u.addTextParameter("account", "13580589643");
 		u.addTextParameter("passwd", "ceshi123");
 		byte[] tokenByte = u.send();  
         String tokenJSON = new String(tokenByte);  
         System.out.println(tokenJSON);  
         String keyWord = URLDecoder.decode("\u5fc3\u60a6\u5b50\u5ba2\u6237\u8d26\u53f7\uff08\u5ba2\u6237\u6f14\u793a\uff09","UTF-8");  
         System.out.println(keyWord);  
         
         Map<String,Object> tokenResult = (Map<String, Object>) GsonUtil.jsonToObject(tokenJSON, Map.class);
 	     String status = tokenResult.get("status").toString();
 	     
 	     double dd = Double.valueOf(status);
 	    int aa = (int)dd;
 	    
 	   if(aa == 1){
 		   System.out.println("https://dsp.xinyuead.cn/login?result=''"); 
 	   }
 	   
 	   
 	  Map<String,Object> tokenResult1 = (Map<String, Object>) GsonUtil.jsonToObject(tokenResult.get("data").toString(), Map.class);
 	 String object = tokenResult1.get("token").toString();
 	 System.out.println(object);
    	/*HttpUtil u = new HttpUtil("https://api.xinyuead.cn/api/session","POST");  
         u.addFileParameter("img", new File(  
                 "D:/Hydrangeas.jpg"));  
     
		u.addTextParameter("method", "fileAction.upload");  
		u.addTextParameter("v", "1.0");  
		u.addTextParameter("app_key", "110");  
		u.addTextParameter("key", "1.0");  
    	
    	JSONObject aa = new JSONObject();
    	aa.put("is_inner", 1);
    	aa.put("account ", "13580589643");
    	aa.put("passwd", "ceshi123");
    	aa.put("captcha", "");
    	String string = aa.toString();
    	
     String sendJsonData = u.sendJsonData(string);
     System.out.println(sendJsonData);  
     
     
     String keyWord = URLDecoder.decode("account\u4e0d\u80fd\u4e3a\u7a7a","UTF-8");  
     System.out.println(keyWord);  
   */
  
    }   
}
