package com.owl.wifi.oss.util;

import java.io.InputStream;
import java.util.Properties;

public class OSSConfigure {
	private String endPoint;
	private String accessKeyId;
	private String accessKeySecret;
	private String bucketName;
	private String hostUrl;

	public OSSConfigure(String storageConfName) throws Exception{
		InputStream resourceAsStream = OSSConfigure.class.getResourceAsStream("/"+storageConfName);
		Properties prop = new Properties();  
        prop.load(resourceAsStream);  
  
        endPoint = prop.getProperty("endPoint").trim();  
        accessKeyId = prop.getProperty("accessKeyId").trim();  
        accessKeySecret = prop.getProperty("accessKeySecret").trim();  
        bucketName = prop.getProperty("bucketName").trim();  
        hostUrl = prop.getProperty("hostUrl").trim();  
	}
	
	public OSSConfigure(String endPoint, String accessKeyId,
			String accessKeySecret, String bucketName,String hostUrl) {
		super();
		this.endPoint = endPoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.bucketName = bucketName;
		this.hostUrl = hostUrl;
	}


	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}
	
	
	
	
	
	
}
