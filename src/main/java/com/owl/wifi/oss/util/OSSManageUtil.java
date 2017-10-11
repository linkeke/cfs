package com.owl.wifi.oss.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;

public class OSSManageUtil {
	  /** 
     * 上传OSS服务器文件 
    * @Title: uploadFile  
    * @Description:  
    * @param @param ossConfigure 
    * @param @param file 
    * @param @param remotePath 
    * @param @return 
    * @param @throws Exception    设定文件  
    * @return String    返回类型  
    * @throws 
     */  
    public static String uploadFile(OSSConfigure ossConfigure,File file,String remotePath) throws Exception{  
        InputStream fileContent=null;  
        fileContent=new FileInputStream(file);  
          
        OSSClient ossClient=new OSSClient(ossConfigure.getEndPoint(), ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());  
         String remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\","/")+"/";  
        //创建上传Object的Metadata  
        ObjectMetadata objectMetadata=new ObjectMetadata();  
        objectMetadata.setContentLength(fileContent.available());  
        objectMetadata.setCacheControl("no-cache");  
        objectMetadata.setHeader("Pragma", "no-cache");  
        String contentType = contentType(file.getName().substring(file.getName().lastIndexOf(".")+1));
        objectMetadata.setContentType(contentType);  
        objectMetadata.setContentDisposition("inline;filename=" + file.getName());  
        //上传文件  
        ossClient.putObject(ossConfigure.getBucketName(), remoteFilePath + file.getName(), fileContent, objectMetadata);  
        ossClient.shutdown();   
       return "http://"+ossConfigure.getHostUrl() + "/" +remoteFilePath  + file.getName();
    }  
      
      
    /** 
     * 根据key删除OSS服务器上的文件 
    * @Title: deleteFile  
    * @Description:  
    * @param @param ossConfigure 
    * @param @param filePath    设定文件  
    * @return void    返回类型  
    * @throws 
     */  
    public static void deleteFile(OSSConfigure ossConfigure,String filePath){  
        OSSClient ossClient = new OSSClient(ossConfigure.getEndPoint(),ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());  
        ossClient.deleteObject(ossConfigure.getBucketName(), filePath);  
        ossClient.shutdown();  
    }  
    
    /**
     * 下载OSS服务器文件到本地
     * @Title: downloadFile 
     * @param @param ossConfigure 
     * @param @param filePath    设定文件  
     */
    public static void downloadFile(OSSConfigure ossConfigure,String filePath,String downloadPath){
    	OSSClient ossClient = new OSSClient(ossConfigure.getEndPoint(), ossConfigure.getAccessKeyId(), ossConfigure.getAccessKeySecret());
    	ossClient.getObject(new GetObjectRequest(ossConfigure.getBucketName(), filePath), new File(downloadPath));
    	ossClient.shutdown();  
    }
    
      
     /** 
     * Description: 判断OSS服务文件上传时文件的contentType 
     * @Version1.0 
     * @param FilenameExtension 文件后缀 
     * @return String  
     */  
     public static String contentType(String FilenameExtension){  
        if(FilenameExtension.equals("BMP")||FilenameExtension.equals("bmp")){return "image/bmp";}  
        if(FilenameExtension.equals("GIF")||FilenameExtension.equals("gif")){return "image/gif";}  
        if(FilenameExtension.equals("JPEG")||FilenameExtension.equals("jpeg")||  
           FilenameExtension.equals("JPG")||FilenameExtension.equals("jpg")||     
           FilenameExtension.equals("PNG")||FilenameExtension.equals("png")){return "image/jpeg";}  
        if(FilenameExtension.equals("HTML")||FilenameExtension.equals("html")){return "text/html";}  
        if(FilenameExtension.equals("TXT")||FilenameExtension.equals("txt")){return "text/plain";}  
        if(FilenameExtension.equals("VSD")||FilenameExtension.equals("vsd")){return "application/vnd.visio";}  
        if(FilenameExtension.equals("PPTX")||FilenameExtension.equals("pptx")||  
            FilenameExtension.equals("PPT")||FilenameExtension.equals("ppt")){return "application/vnd.ms-powerpoint";}  
        if(FilenameExtension.equals("DOCX")||FilenameExtension.equals("docx")||  
            FilenameExtension.equals("DOC")||FilenameExtension.equals("doc")){return "application/msword";}  
        if(FilenameExtension.equals("PDF")||FilenameExtension.equals("pdf")){return "application/pdf";}  
        if(FilenameExtension.equals("XML")||FilenameExtension.equals("xml")){return "text/xml";}  
        return "text/html";  
     }  
}
