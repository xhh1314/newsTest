package com.hww.app.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.admin.service.FeedBackService;
import com.hww.app.common.dto.AppFeedBackDto;
import com.hww.app.common.entity.AppFeedBack;
import com.hww.app.common.manager.FeedBackMng;
import com.hww.base.common.page.Pagination;

@Service("feedBackService")
public class FeedBackServiceImpl implements FeedBackService {

	@Autowired
	FeedBackMng feedBackMng;

	@Override
	public Pagination queryAppFeedBack(AppFeedBackDto dto, Integer pageNo, Integer pageSize) {
		
		return feedBackMng.queryAppFeedBack(dto, pageNo, pageSize);
	}
	
	
	
	
	
	

}
