package com.owl.wifi.content;
//*****************************************************************************
/**
 * <p>Title:Global</p>
 * <p>Description:静态常量类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: </p>
 * @author 汤清
 * @version v1.0 2016-03-14
 */
//*****************************************************************************
public final class Global {
	
	
	
	public static final String UPLOAD_SAVE_PATH = "/usr/local/temp";
	public static final String UPLOAD_URL = SystemGlobals.getInstance().getProperty("upload.url");

    public static final String JSON_SUCCESS = "{\"status\":\"10000\",\"info\":\"SUCCESS\",\"data\":{}}";
    public static final String JSON_ERROR = "{\"status\":\"99999\",\"info\":\"ERROR\",\"data\":{}}";
    
    public static final String APP_KEY = SystemGlobals.getInstance().getProperty("APP_KEY");
	
	
	public static final String SMS_URL = SystemGlobals.getInstance().getProperty("sms.api.url");
	public static final String SMS_PORT = SystemGlobals.getInstance().getProperty("sms.api.port");
	public static final String SMS_ACCOUNT_ID = SystemGlobals.getInstance().getProperty("sms.api.accountSid");
	public static final String SMS_ACCOUNT_TOKEN = SystemGlobals.getInstance().getProperty("sms.api.accountToken");
	public static final String SMS_APP_ID = SystemGlobals.getInstance().getProperty("sms.api.AppId");
	public static final String SMS_TEMPLATE_ID = SystemGlobals.getInstance().getProperty("sms.api.templateId");
	public static final String SMS_NOTIFY_TEMPLATE_ID = SystemGlobals.getInstance().getProperty("sms.api.notifytempId");
	
	public static final String HEAD_IMG_PATH=SystemGlobals.getInstance().getProperty("headImgPath");
	public static final String INDUSTRY_IMG_PATH=SystemGlobals.getInstance().getProperty("industryIcon");
	
	public static final String BP_FILE_PATH=SystemGlobals.getInstance().getProperty("bpFile");		
	public static final String LICENSE_FILE_PATH=SystemGlobals.getInstance().getProperty("licenseFile");
	public static final String BP_IMG_PATH=SystemGlobals.getInstance().getProperty("bpImg");
	public static final String IDENTIFY_FILE_PATH=SystemGlobals.getInstance().getProperty("identifyFilePath");
	public static final String LOGO_FILE_PATH=SystemGlobals.getInstance().getProperty("projectIcon");
	
	public static final String TEMP_FILE_PATH=SystemGlobals.getInstance().getProperty("tempPath");
	
	public static final String APP_STATU=SystemGlobals.getInstance().getProperty("APP_STATU");
	public static final String NEWS_DETAIL_URL=SystemGlobals.getInstance().getProperty("newsdetail.url");
	public static final String SHARE_PROJECT_URL=SystemGlobals.getInstance().getProperty("shareproject.url");
	/**
	 * IP
	 * */
	public static final String IP=SystemGlobals.getInstance().getProperty("localip");
	public static final String DEFAULT_IMG=SystemGlobals.getInstance().getProperty("defaultImgUrl");
	public static final String SYNCHOST=SystemGlobals.getInstance().getProperty("syncHostUrl");
	
}