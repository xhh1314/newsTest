package com.hww.sns.webadmin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hww.base.common.page.Pagination;
import com.hww.cms.api.CmsFeignClient;
import com.hww.cms.common.dto.CmsContentDto;
import com.hww.framework.web.mvc.controller.AbsBaseController;

/**
 * sns=新闻控制器
 * @author hewei
 *
 */
@Controller
@RequestMapping("/sns/news")
public class SnsNewsController
	extends
		AbsBaseController {

	private static final Log log = LogFactory.getLog(SnsNewsController.class);
	
	@Autowired
	CmsFeignClient cmsFeignClient;
	
	/**
	 * 远程调用获取新闻列表
	 * @return
	 */
	@RequestMapping(value = "list.do")
	public String auditDetail(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			CmsContentDto dto) {
		Pagination result = new Pagination(dto.getPageNo(),dto.getPageSize(),dto.getPageSize());
		try {
			result = cmsFeignClient.list(dto);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("远程调用-获取新闻列表失败!"+e.getMessage());
		}
		map.addAttribute("page", result);
		//搜素条件
		map.addAttribute("newsSearchVo",dto);
		return "audit/relatedNews";
	}
	
}
