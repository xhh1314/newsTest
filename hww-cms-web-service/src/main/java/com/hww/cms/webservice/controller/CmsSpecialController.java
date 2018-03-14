package com.hww.cms.webservice.controller;

import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import com.hww.app.common.dto.HSearchDto;
import com.hww.base.util.R;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.common.vo.CmsSpecialVo;
import com.hww.cms.common.vo.HCmsSpecialVo;
import com.hww.cms.webservice.service.CmsContentService;
import com.hww.cms.webservice.service.CmsSpecialService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cms/special")
public class CmsSpecialController {

	private static final Log log = LogFactory.getLog(CmsSpecialController.class);

	@Autowired
	private Environment env;
	@Autowired
	private CmsSpecialService cmsSpecialService;
	@Autowired
	private CmsContentService cmsContentService;
	// ===========================feginAPI=============================

	@ApiOperation(value = "专题详情---分享页专用", notes = "")
	@RequestMapping(value = "specialShareDetail.do", method = RequestMethod.POST)
	@ResponseBody
	public R specialShareDetail(HCmsQueryDto queryDto) {
		if (queryDto == null) {
			return R.error();
		}
		
		HCmsSpecialVo cmsSpecialVo = cmsSpecialService.loadSpecialDetailForShare(queryDto);
		
		if (Objects.isNull(cmsSpecialVo)) {//null 
			return R.error();
		}
		log.info("----------APP端专题详情结束----------");
		return R.ok().put("msg", cmsSpecialVo).put("filedomain", env.getProperty("fileservice.domain"));
	}
	
    @ApiOperation(value = "查询指定专题的新闻", notes ="")
    @RequestMapping(value = "loadContentBySpecialId.do", method = RequestMethod.POST)
    public R loadContentBySpecialId(HCmsQueryDto cmsQueryDto) {
        log.info("----------loadNewsBySpecialId----------");
    	List<CmsContentVo> cmsContentList =  cmsContentService.loadCmsContentBySpecilId(cmsQueryDto);
        log.info("----------loadNewsBySpecialId---------");
        return R.ok().put("data", cmsContentList);
    } 

}
