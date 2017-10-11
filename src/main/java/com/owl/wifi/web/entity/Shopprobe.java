package com.owl.wifi.web.entity;

import java.util.Date;

public class Shopprobe {
    private Integer shopProbeId; //自增ID

    private Integer shopId; //门店ID

    private Date installDate; //安装日期

    private Byte activeInd;  //running status,0-not running,1-running

    private Integer rssiFilter; //Filter signal strength,根据这个值判断探索到的手机是否在室内

    private String probeMac; //设备MAC

    private String probeName; //设备名称

    private String probeSn; //设备序列号

    private String probeType; //设备类型

    private String probeHardwareVersion; //设备硬件版本

    private String probeSoftwareVersion; //设备软件版本

    private Date probeDatetime; //设备时间

    private Integer probeActivityInterval; //设备记录终端活动的间隔

    private Integer probeSaveInterval; //设备保存记录活动的间隔

    private Integer probeUploadInterval; //设备上传服务器的间隔

    private String probeWanIp; //广域网IP

    private String probeLanIp; //局域网IP
    
    private Date lastuploadtime; //最后上传数据时间

    public Integer getShopProbeId() {
        return shopProbeId;
    }

    public void setShopProbeId(Integer shopProbeId) {
        this.shopProbeId = shopProbeId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public Byte getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Byte activeInd) {
        this.activeInd = activeInd;
    }

    public Integer getRssiFilter() {
        return rssiFilter;
    }

    public void setRssiFilter(Integer rssiFilter) {
        this.rssiFilter = rssiFilter;
    }

    public String getProbeMac() {
        return probeMac;
    }

    public void setProbeMac(String probeMac) {
        this.probeMac = probeMac == null ? null : probeMac.trim();
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName == null ? null : probeName.trim();
    }

    public String getProbeSn() {
        return probeSn;
    }

    public void setProbeSn(String probeSn) {
        this.probeSn = probeSn == null ? null : probeSn.trim();
    }

    public String getProbeType() {
        return probeType;
    }

    public void setProbeType(String probeType) {
        this.probeType = probeType == null ? null : probeType.trim();
    }

    public String getProbeHardwareVersion() {
        return probeHardwareVersion;
    }

    public void setProbeHardwareVersion(String probeHardwareVersion) {
        this.probeHardwareVersion = probeHardwareVersion == null ? null : probeHardwareVersion.trim();
    }

    public String getProbeSoftwareVersion() {
        return probeSoftwareVersion;
    }

    public void setProbeSoftwareVersion(String probeSoftwareVersion) {
        this.probeSoftwareVersion = probeSoftwareVersion == null ? null : probeSoftwareVersion.trim();
    }

    public Date getProbeDatetime() {
        return probeDatetime;
    }

    public void setProbeDatetime(Date probeDatetime) {
        this.probeDatetime = probeDatetime;
    }

    public Integer getProbeActivityInterval() {
        return probeActivityInterval;
    }

    public void setProbeActivityInterval(Integer probeActivityInterval) {
        this.probeActivityInterval = probeActivityInterval;
    }

    public Integer getProbeSaveInterval() {
        return probeSaveInterval;
    }

    public void setProbeSaveInterval(Integer probeSaveInterval) {
        this.probeSaveInterval = probeSaveInterval;
    }

    public Integer getProbeUploadInterval() {
        return probeUploadInterval;
    }

    public void setProbeUploadInterval(Integer probeUploadInterval) {
        this.probeUploadInterval = probeUploadInterval;
    }

    public String getProbeWanIp() {
        return probeWanIp;
    }

    public void setProbeWanIp(String probeWanIp) {
        this.probeWanIp = probeWanIp == null ? null : probeWanIp.trim();
    }

    public String getProbeLanIp() {
        return probeLanIp;
    }

    public void setProbeLanIp(String probeLanIp) {
        this.probeLanIp = probeLanIp == null ? null : probeLanIp.trim();
    }

	public Date getLastuploadtime() {
		return lastuploadtime;
	}

	public void setLastuploadtime(Date lastuploadtime) {
		this.lastuploadtime = lastuploadtime;
	}

	@Override
	public String toString() {
		return "Shopprobe [shopProbeId=" + shopProbeId + ", shopId=" + shopId + ", installDate=" + installDate
				+ ", activeInd=" + activeInd + ", rssiFilter=" + rssiFilter + ", probeMac=" + probeMac + ", probeName="
				+ probeName + ", probeSn=" + probeSn + ", probeType=" + probeType + ", probeHardwareVersion="
				+ probeHardwareVersion + ", probeSoftwareVersion=" + probeSoftwareVersion + ", probeDatetime="
				+ probeDatetime + ", probeActivityInterval=" + probeActivityInterval + ", probeSaveInterval="
				+ probeSaveInterval + ", probeUploadInterval=" + probeUploadInterval + ", probeWanIp=" + probeWanIp
				+ ", probeLanIp=" + probeLanIp + "]";
	}
    
}