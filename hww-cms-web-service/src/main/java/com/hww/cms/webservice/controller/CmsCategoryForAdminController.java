package com.hww.cms.webservice.controller;

import com.hww.base.common.vo.BaseTreeVo;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.webservice.service.CmsCategoryForAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cms/category/admin")
public class CmsCategoryForAdminController {

	@Autowired
	CmsCategoryForAdminService cmsCategoryForAdminService;
	@RequestMapping(value = "allCategoryJson.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<BaseTreeVo> allCategoryJson() {
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		List<CmsCategory> categoryList = cmsCategoryForAdminService.getRetrievingFullTree();
		for (CmsCategory cmsCategory : categoryList) {
			BaseTreeVo m = new BaseTreeVo();
			m.setId(cmsCategory.getCategoryId());
			m.setChkDisabled(false);
			if (cmsCategory.getParentId() != null) {
				m.setIsParent(false);
			} else {
				m.setIsParent(true);
			}
			if (cmsCategory.getParent() != null) {
				m.setpId(cmsCategory.getParent().getCategoryId());
			} else {
				m.setpId(Long.parseLong("0"));
			}
			m.setName(cmsCategory.getCategoryName());
			m.setChecked(false);
			tree.add(m);
		}
		return tree;
	}
}
