package com.owl.wifi.web.wifi.model;

import java.util.Date;

public class WifiDeviceModel {
	private String deviceName;
	
	private String deviceMac;
	
	private String deviceSn;
	private String deviceType;
	private String deviceHardwareVersion;
	private String deviceSoftwareVersion;
	private String deviceDatetime;
	private Integer deviceRuntime;
	private Integer deviceStorage;
	private String deviceFiletype;
	private String deviceFileuploadUrl;
	private String deviceFileVersion;
	
	private Integer deviceActivityInterval;
	private Integer deviceSaveInterval;
	private Integer deviceUploadInterval;
	private String deviceWanIp;
	private String deviceLanIp;
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceHardwareVersion() {
		return deviceHardwareVersion;
	}
	public void setDeviceHardwareVersion(String deviceHardwareVersion) {
		this.deviceHardwareVersion = deviceHardwareVersion;
	}
	public String getDeviceSoftwareVersion() {
		return deviceSoftwareVersion;
	}
	public void setDeviceSoftwareVersion(String deviceSoftwareVersion) {
		this.deviceSoftwareVersion = deviceSoftwareVersion;
	}
	
	public String getDeviceDatetime() {
		return deviceDatetime;
	}
	public void setDeviceDatetime(String deviceDatetime) {
		this.deviceDatetime = deviceDatetime;
	}
	public Integer getDeviceActivityInterval() {
		return deviceActivityInterval;
	}
	public void setDeviceActivityInterval(Integer deviceActivityInterval) {
		this.deviceActivityInterval = deviceActivityInterval;
	}
	public Integer getDeviceSaveInterval() {
		return deviceSaveInterval;
	}
	public void setDeviceSaveInterval(Integer deviceSaveInterval) {
		this.deviceSaveInterval = deviceSaveInterval;
	}
	public Integer getDeviceUploadInterval() {
		return deviceUploadInterval;
	}
	public void setDeviceUploadInterval(Integer deviceUploadInterval) {
		this.deviceUploadInterval = deviceUploadInterval;
	}
	public String getDeviceWanIp() {
		return deviceWanIp;
	}
	public void setDeviceWanIp(String deviceWanIp) {
		this.deviceWanIp = deviceWanIp;
	}
	public String getDeviceLanIp() {
		return deviceLanIp;
	}
	public void setDeviceLanIp(String deviceLanIp) {
		this.deviceLanIp = deviceLanIp;
	}
	public Integer getDeviceRuntime() {
		return deviceRuntime;
	}
	public void setDeviceRuntime(Integer deviceRuntime) {
		this.deviceRuntime = deviceRuntime;
	}
	public Integer getDeviceStorage() {
		return deviceStorage;
	}
	public void setDeviceStorage(Integer deviceStorage) {
		this.deviceStorage = deviceStorage;
	}
	public String getDeviceFiletype() {
		return deviceFiletype;
	}
	public void setDeviceFiletype(String deviceFiletype) {
		this.deviceFiletype = deviceFiletype;
	}
	public String getDeviceFileuploadUrl() {
		return deviceFileuploadUrl;
	}
	public void setDeviceFileuploadUrl(String deviceFileuploadUrl) {
		this.deviceFileuploadUrl = deviceFileuploadUrl;
	}
	public String getDeviceFileVersion() {
		return deviceFileVersion;
	}
	public void setDeviceFileVersion(String deviceFileVersion) {
		this.deviceFileVersion = deviceFileVersion;
	}
	
	
}
