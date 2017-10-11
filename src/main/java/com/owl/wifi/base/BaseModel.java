package com.owl.wifi.base;

import com.owl.wifi.content.FinVal;

public class BaseModel {
	private Integer page=FinVal.DEFAULT_PAGE;
	private Integer pageSize=FinVal.DEFAULT_PAGESIZE;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
 
 
}
