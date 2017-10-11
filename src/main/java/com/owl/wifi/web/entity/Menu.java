package com.owl.wifi.web.entity;

import java.util.Date;

public class Menu {
    private Long nMenuId;

    private String cMenuName;
    
    private String cMenuIcon;

    private String cMenuUrl;

    private Integer nMenuLevel;

    private Long nParentMenuId;

    private Integer nStatus;

    private Date tCreateTime;

    private Date tUpdateTime;

    private Long nCreateBy;

    private Long nUpdateBy;

    public Long getnMenuId() {
        return nMenuId;
    }

    public void setnMenuId(Long nMenuId) {
        this.nMenuId = nMenuId;
    }

    public String getcMenuName() {
        return cMenuName;
    }

    public void setcMenuName(String cMenuName) {
        this.cMenuName = cMenuName == null ? null : cMenuName.trim();
    }

    public String getcMenuUrl() {
        return cMenuUrl;
    }

    public void setcMenuUrl(String cMenuUrl) {
        this.cMenuUrl = cMenuUrl == null ? null : cMenuUrl.trim();
    }

    public Integer getnMenuLevel() {
        return nMenuLevel;
    }

    public void setnMenuLevel(Integer nMenuLevel) {
        this.nMenuLevel = nMenuLevel;
    }

    public Long getnParentMenuId() {
        return nParentMenuId;
    }

    public void setnParentMenuId(Long nParentMenuId) {
        this.nParentMenuId = nParentMenuId;
    }

    public Integer getnStatus() {
        return nStatus;
    }

    public void setnStatus(Integer nStatus) {
        this.nStatus = nStatus;
    }

    public String getcMenuIcon() {
		return cMenuIcon;
	}

	public void setcMenuIcon(String cMenuIcon) {
		this.cMenuIcon = cMenuIcon;
	}

	public Date gettCreateTime() {
        return tCreateTime;
    }

    public void settCreateTime(Date tCreateTime) {
        this.tCreateTime = tCreateTime;
    }

    public Date gettUpdateTime() {
        return tUpdateTime;
    }

    public void settUpdateTime(Date tUpdateTime) {
        this.tUpdateTime = tUpdateTime;
    }

    public Long getnCreateBy() {
        return nCreateBy;
    }

    public void setnCreateBy(Long nCreateBy) {
        this.nCreateBy = nCreateBy;
    }

    public Long getnUpdateBy() {
        return nUpdateBy;
    }

    public void setnUpdateBy(Long nUpdateBy) {
        this.nUpdateBy = nUpdateBy;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nMenuId == null) ? 0 : nMenuId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (nMenuId == null) {
			if (other.nMenuId != null)
				return false;
		} else if (!nMenuId.equals(other.nMenuId))
			return false;
		return true;
	}
}