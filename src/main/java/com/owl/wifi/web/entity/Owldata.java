package com.owl.wifi.web.entity;

public class Owldata {
    private String mac;  //mac地址

    private String imei; //国际移动设备标志

    private String idfa; //苹果体系内设备唯一标识

    private String androidId; //安卓体系内设备唯一标识

    private Byte sexInd;  //0=Male 1=Female 性别

    private String ageTxt;

    private String workTxt;

    private Byte marriedInd;

    private Byte carInd;

    private String babyTxt;

    private String usualPlaceTypeTxt;  //常去场所类型查询服务

    private String usualNightAddressTxt; //夜间活跃区域查询服务

    private String leisureActivityTxt; //游戏兴趣标签查询服务

    private String consumptionHabitsTxt; //线下消费偏好标签查询服务

    private String interestsTxt; //应用兴趣标签查询服务

    private String financialProductsTxt; //金融标签查询服务

    private String deviceBrand;  //标准品牌

    private String deviceModel; //标准机型

    private String deviceType;  //设备类型，包含手机、平板、智能电视、智能手表、模拟器

    private String deviceScreen; //屏幕尺寸，尺寸分布： 6.0英寸以上 6.0-5.6英寸 5.5-5.1英寸 5.0-4.6英寸 4.5-3.1英寸 3英寸及以下

    private String devicePrice; //价格区间，区间分布： 1～499 500～999 1000~1999 2000~3999 4000以上

    private String deviceSpecialFunction; //功能特性，包含音乐手机、美颜手机、老人机、摄影手机、高端商务手机、高性价比手机等，可包含多个特征，中间用逗号分隔

    private String deviceHardware; //硬件特性，包含8核芯片，陀螺仪，NFC芯片，双卡双待，指纹等，可包含多个特征，中间用逗号分隔

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

    public Byte getSexInd() {
        return sexInd;
    }

    public void setSexInd(Byte sexInd) {
        this.sexInd = sexInd;
    }

    public String getAgeTxt() {
        return ageTxt;
    }

    public void setAgeTxt(String ageTxt) {
        this.ageTxt = ageTxt == null ? null : ageTxt.trim();
    }

    public String getWorkTxt() {
        return workTxt;
    }

    public void setWorkTxt(String workTxt) {
        this.workTxt = workTxt == null ? null : workTxt.trim();
    }

    public Byte getMarriedInd() {
        return marriedInd;
    }

    public void setMarriedInd(Byte marriedInd) {
        this.marriedInd = marriedInd;
    }

    public Byte getCarInd() {
        return carInd;
    }

    public void setCarInd(Byte carInd) {
        this.carInd = carInd;
    }

    public String getBabyTxt() {
        return babyTxt;
    }

    public void setBabyTxt(String babyTxt) {
        this.babyTxt = babyTxt == null ? null : babyTxt.trim();
    }

    public String getUsualPlaceTypeTxt() {
        return usualPlaceTypeTxt;
    }

    public void setUsualPlaceTypeTxt(String usualPlaceTypeTxt) {
        this.usualPlaceTypeTxt = usualPlaceTypeTxt == null ? null : usualPlaceTypeTxt.trim();
    }

    public String getUsualNightAddressTxt() {
        return usualNightAddressTxt;
    }

    public void setUsualNightAddressTxt(String usualNightAddressTxt) {
        this.usualNightAddressTxt = usualNightAddressTxt == null ? null : usualNightAddressTxt.trim();
    }

    public String getLeisureActivityTxt() {
        return leisureActivityTxt;
    }

    public void setLeisureActivityTxt(String leisureActivityTxt) {
        this.leisureActivityTxt = leisureActivityTxt == null ? null : leisureActivityTxt.trim();
    }

    public String getConsumptionHabitsTxt() {
        return consumptionHabitsTxt;
    }

    public void setConsumptionHabitsTxt(String consumptionHabitsTxt) {
        this.consumptionHabitsTxt = consumptionHabitsTxt == null ? null : consumptionHabitsTxt.trim();
    }

    public String getInterestsTxt() {
        return interestsTxt;
    }

    public void setInterestsTxt(String interestsTxt) {
        this.interestsTxt = interestsTxt == null ? null : interestsTxt.trim();
    }

    public String getFinancialProductsTxt() {
        return financialProductsTxt;
    }

    public void setFinancialProductsTxt(String financialProductsTxt) {
        this.financialProductsTxt = financialProductsTxt == null ? null : financialProductsTxt.trim();
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand == null ? null : deviceBrand.trim();
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel == null ? null : deviceModel.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getDeviceScreen() {
        return deviceScreen;
    }

    public void setDeviceScreen(String deviceScreen) {
        this.deviceScreen = deviceScreen == null ? null : deviceScreen.trim();
    }

    public String getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(String devicePrice) {
        this.devicePrice = devicePrice == null ? null : devicePrice.trim();
    }

    public String getDeviceSpecialFunction() {
        return deviceSpecialFunction;
    }

    public void setDeviceSpecialFunction(String deviceSpecialFunction) {
        this.deviceSpecialFunction = deviceSpecialFunction == null ? null : deviceSpecialFunction.trim();
    }

    public String getDeviceHardware() {
        return deviceHardware;
    }

    public void setDeviceHardware(String deviceHardware) {
        this.deviceHardware = deviceHardware == null ? null : deviceHardware.trim();
    }
}