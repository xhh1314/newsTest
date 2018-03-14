package com.hww.cms.webservice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.cms.webservice.service.CmsContentService;

@Controller
@RequestMapping("cms/share")
public class CmsForShareController {
	@Resource
	private CmsContentService cmsContentService;
	
	@RequestMapping("/pshare/{CategoryId}/content_{contentId}_{type}.html")
	public String shareCms(@PathVariable Long CategoryId, @PathVariable Long contentId, @PathVariable Long type,ModelMap map ) {
		CmsContentDataVo contentDateVo=cmsContentService.loadNewsDetailForShare(contentId);
		map.addAttribute("contentDateVo", contentDateVo);
		return "share/a";
		
	}

}
