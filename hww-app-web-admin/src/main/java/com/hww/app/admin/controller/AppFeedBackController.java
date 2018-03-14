package com.hww.app.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hww.app.admin.service.FeedBackService;
import com.hww.app.common.dto.AppFeedBackDto;
import com.hww.app.common.vo.AppFeedBackVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanMapper;

@Controller
@RequestMapping("feedback")
public class AppFeedBackController {
	
	@Autowired
	FeedBackService feedBackService;
	
	
	
	@RequestMapping("list.do")
	public String findlist(AppFeedBackVo vo ,ModelMap map) {
	AppFeedBackDto dto=BeanMapper.map(vo, AppFeedBackDto.class);
	Pagination p=	feedBackService.queryAppFeedBack(dto, vo.getPageNo(), vo.getPageSize());
	map.addAttribute("page", p);
	map.addAttribute("form", vo.getMemberId());	
		
		return "feedback/list";
		
	}

}
