package com.owl.wifi.web.entity;

import java.util.Date;

public class ShopCamera {
        private Integer shopCameraId;

        private Integer shopId;

        private String deviceId;

        private Date installDate;

        private Byte activeInd;

        private Date activeStartTime;

        private Date activeEndTime;

        private String cameraHardwareVersion;

        private String cameraSoftwareVersion;

        public Integer getShopCameraId() {
                return shopCameraId;
        }

        public void setShopCameraId(Integer shopCameraId) {
                this.shopCameraId = shopCameraId;
        }

        public Integer getShopId() {
                return shopId;
        }

        public void setShopId(Integer shopId) {
                this.shopId = shopId;
        }

        public String getDeviceId() {
                return deviceId;
        }

        public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
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

        public Date getActiveStartTime() {
                return activeStartTime;
        }

        public void setActiveStartTime(Date activeStartTime) {
                this.activeStartTime = activeStartTime;
        }

        public Date getActiveEndTime() {
                return activeEndTime;
        }

        public void setActiveEndTime(Date activeEndTime) {
                this.activeEndTime = activeEndTime;
        }

        public String getCameraHardwareVersion() {
                return cameraHardwareVersion;
        }

        public void setCameraHardwareVersion(String cameraHardwareVersion) {
                this.cameraHardwareVersion = cameraHardwareVersion;
        }

        public String getCameraSoftwareVersion() {
                return cameraSoftwareVersion;
        }

        public void setCameraSoftwareVersion(String cameraSoftwareVersion) {
                this.cameraSoftwareVersion = cameraSoftwareVersion;
        }
}