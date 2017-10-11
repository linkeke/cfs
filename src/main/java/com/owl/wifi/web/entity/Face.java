package com.owl.wifi.web.entity;

public class Face {
        private Integer faceId;

        private Byte maleInd;

        private Byte ageVal;

        private Short raceId;

        private Short lipsId;

        private Byte glassesInd;

        private String imagesTxt;

        public Integer getFaceId() {
                return faceId;
        }

        public void setFaceId(Integer faceId) {
                this.faceId = faceId;
        }

        public Byte getMaleInd() {
                return maleInd;
        }

        public void setMaleInd(Byte maleInd) {
                this.maleInd = maleInd;
        }

        public Byte getAgeVal() {
                return ageVal;
        }

        public void setAgeVal(Byte ageVal) {
                this.ageVal = ageVal;
        }

        public Short getRaceId() {
                return raceId;
        }

        public void setRaceId(Short raceId) {
                this.raceId = raceId;
        }

        public Short getLipsId() {
                return lipsId;
        }

        public void setLipsId(Short lipsId) {
                this.lipsId = lipsId;
        }

        public Byte getGlassesInd() {
                return glassesInd;
        }

        public void setGlassesInd(Byte glassesInd) {
                this.glassesInd = glassesInd;
        }

        public String getImagesTxt() {
                return imagesTxt;
        }

        public void setImagesTxt(String imagesTxt) {
                this.imagesTxt = imagesTxt;
        }
}