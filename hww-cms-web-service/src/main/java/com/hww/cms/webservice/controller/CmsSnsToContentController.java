package com.hww.cms.webservice.controller;
//import com.hww.app.common.dto.HSearchDto;
import com.hww.base.util.R;
import com.hww.cms.webservice.service.CmsToContentService;
import com.hww.sns.common.dto.SnsTopicToCmsDto;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/tocontent")
public class CmsSnsToContentController {

	private static final Log log = LogFactory.getLog(CmsSnsToContentController.class);

	@Autowired
	private CmsToContentService cmsToContentService;
	
	@ApiOperation(value = "转化为新闻--新闻内容化", notes ="")
	@RequestMapping(value = "snsToCmsContentFeginApi.do", method = RequestMethod.POST)
	public R toCmsContent(@RequestBody SnsTopicToCmsDto snsTopicToCmsDto){
		if (snsTopicToCmsDto == null) {
			return R.error(500, "参数为空");
		}
		cmsToContentService.snsToCmsContent(snsTopicToCmsDto);
		return R.ok();
	}
	
	
}
