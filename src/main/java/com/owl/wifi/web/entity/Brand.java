package com.owl.wifi.web.entity;

public class Brand {
        
        private Integer id; // 主键ID
        
        private Integer industryId; // 外键[ 行业ID ]
        
        private String name; // 品牌名称 

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public Integer getIndustryId() {
                return industryId;
        }

        public void setIndustryId(Integer industryId) {
                this.industryId = industryId;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
        
}
