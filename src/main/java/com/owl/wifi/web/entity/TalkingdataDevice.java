package com.owl.wifi.web.entity;

public class TalkingdataDevice {
    private String mac; //MAC地址

    private String imei; //国际移动设备标识

    private String tdid; //此设备在TalkingData平台的唯一设备ID

    private String idfa; //苹果体系内设备唯一标识

    private String androidId; //安卓体系内设备唯一标识

    private String standardBrand; //标准品牌

    private String standardModel; //标准机型

    private String deviceType; //设备类型，包含手机、平板、智能电视、智能手表、模拟器

    private String screen; //屏幕尺寸，尺寸分布： 6.0英寸以上 6.0-5.6英寸 5.5-5.1英寸 5.0-4.6英寸 4.5-3.1英寸 3英寸及以下

    private String price; //价格区间，区间分布： 1～499 500～999 1000~1999 2000~3999 4000以上

    private String functionType; //功能特性，包含音乐手机、美颜手机、老人机、摄影手机、高端商务手机、高性价比手机等，可包含多个特征，中间用逗号分隔

    private String hardwareType; //硬件特性，包含8核芯片，陀螺仪，NFC芯片，双卡双待，指纹等，可包含多个特征，中间用逗号分隔

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getTdid() {
        return tdid;
    }

    public void setTdid(String tdid) {
        this.tdid = tdid == null ? null : tdid.trim();
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa == null ? null : idfa.trim();
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId == null ? null : androidId.trim();
    }

    public String getStandardBrand() {
        return standardBrand;
    }

    public void setStandardBrand(String standardBrand) {
        this.standardBrand = standardBrand == null ? null : standardBrand.trim();
    }

    public String getStandardModel() {
        return standardModel;
    }

    public void setStandardModel(String standardModel) {
        this.standardModel = standardModel == null ? null : standardModel.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen == null ? null : screen.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType == null ? null : functionType.trim();
    }

    public String getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(String hardwareType) {
        this.hardwareType = hardwareType == null ? null : hardwareType.trim();
    }

	@Override
	public String toString() {
		return "TalkingdataDevice [mac=" + mac + ", imei=" + imei + ", tdid=" + tdid + ", idfa=" + idfa + ", androidId="
				+ androidId + ", standardBrand=" + standardBrand + ", standardModel=" + standardModel + ", deviceType="
				+ deviceType + ", screen=" + screen + ", price=" + price + ", functionType=" + functionType
				+ ", hardwareType=" + hardwareType + "]";
	}   
}