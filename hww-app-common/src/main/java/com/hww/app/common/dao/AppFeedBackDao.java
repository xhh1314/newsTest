package com.hww.app.common.dao;



import com.hww.app.common.dto.AppFeedBackDto;
import com.hww.app.common.entity.AppFeedBack;
import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.base.common.page.Pagination;

public interface AppFeedBackDao extends IBaseEntityDao<Long, AppFeedBack>{
	
	
	public Pagination queryAppFeedBack(AppFeedBackDto dto,Integer pageNo, Integer pageSize);
	
	

}
