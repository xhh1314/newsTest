package com.hww.cms.webadmin.controller;

//import com.hww.app.common.dto.HSearchDto;
import com.hww.base.util.R;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.dto.HJPushDataDto;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.webadmin.push.JPushService;
import com.hww.cms.webadmin.service.CmsContentService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/jPush")
public class CmsContentPushController {

	private static final Log log = LogFactory.getLog(CmsContentPushController.class);

	@Autowired
	private CmsContentService cmsContentService;
	
	@Autowired
	JPushService jPushService;
	@RequestMapping(value = "jPush.do", method = RequestMethod.POST)
	public R jPush(Long contentId,String title) {
		//CmsContentDataVo cmsContent = cmsContentService.loadNewsDetail(new HCmsQueryDto().setContentId(contentId));
		CmsContentData cmsContentData = cmsContentService.getContentById(contentId);
		CmsContentVo cmsContent = cmsContentService.findCmsContentById(contentId, null);
		HJPushDataDto pushDataDto=new HJPushDataDto()
				.setNewsID(cmsContent.getContentId()).setNewsTitle(title)
				.setNewsUrl(cmsContent.getLinkUrl()).setNewsType(cmsContent.getContenttypeId().intValue())
				.setAlertContent(cmsContent.getTitle());
		 jPushService.sendPush(pushDataDto);
		return R.ok().put("data", cmsContent);
	}
	
}
