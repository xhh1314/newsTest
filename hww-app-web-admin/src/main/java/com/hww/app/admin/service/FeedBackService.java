package com.hww.app.admin.service;

import java.util.List;

import com.hww.app.common.dto.AppFeedBackDto;
import com.hww.app.common.entity.AppFeedBack;
import com.hww.base.common.page.Pagination;


public interface FeedBackService {
	
	
	public Pagination queryAppFeedBack(AppFeedBackDto dto ,Integer pageNo, Integer pageSize);
	
	
	

}
