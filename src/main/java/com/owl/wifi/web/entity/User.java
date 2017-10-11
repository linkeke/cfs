package com.owl.wifi.web.entity;

import java.util.Date;

public class User {
    private Integer userId; //用户ID

    private String userName; //用户账号

    private String userPwd; //用户密码
    
    private String industryPic; //用户登录对应的行业图片
    
    private String hikAccount; // 海康系统用户名&密码
    
    private Integer nCreateId;

    private Date tCreateTime;

    private Integer nUpdateId;

    private Date tUpdateTime;
    

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

	public String getIndustryPic() {
		return industryPic;
	}

	public void setIndustryPic(String industryPic) {
		this.industryPic = industryPic;
	}

        public String getHikAccount() {
                return hikAccount;
        }

        public void setHikAccount(String hikAccount) {
                this.hikAccount = hikAccount;
        }

		public Integer getnCreateId() {
			return nCreateId;
		}

		public void setnCreateId(Integer nCreateId) {
			this.nCreateId = nCreateId;
		}

		public Date gettCreateTime() {
			return tCreateTime;
		}

		public void settCreateTime(Date tCreateTime) {
			this.tCreateTime = tCreateTime;
		}

		public Integer getnUpdateId() {
			return nUpdateId;
		}

		public void setnUpdateId(Integer nUpdateId) {
			this.nUpdateId = nUpdateId;
		}

		public Date gettUpdateTime() {
			return tUpdateTime;
		}

		public void settUpdateTime(Date tUpdateTime) {
			this.tUpdateTime = tUpdateTime;
		}
    
    
}