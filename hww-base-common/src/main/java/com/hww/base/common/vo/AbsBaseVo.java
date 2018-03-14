package com.hww.base.common.vo;

public abstract class AbsBaseVo implements IBaseVo{
	private String allIDCheck;//列表里选中的id
    private Integer pageNo;//当前页号
    private Integer pageSize = 10; //分页大小,默认10
    
	public String getAllIDCheck() {
		return allIDCheck;
	}
	public void setAllIDCheck(String allIDCheck) {
		this.allIDCheck = allIDCheck;
	}
	public Integer getPageNo() {
		if(pageNo!=null) {
			return pageNo;
		}else {
			return 1;
		}
		
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
    
}
