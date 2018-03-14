package com.hww.cms.webadmin.controller;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.vo.BaseTreeVo;
import com.hww.base.util.R;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;
import com.hww.cms.webadmin.service.CmsContentAuditFlowService;
import com.hww.cms.webadmin.service.CmsContentAuditFlowStepsService;
import com.hww.framework.web.mvc.controller.AbsBaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/contentAuditFlow")
public class CmsContentAuditFlowController extends AbsBaseController {

	private static final Logger log = LoggerFactory.getLogger(CmsContentAuditFlowController.class);

	@Autowired
	CmsContentAuditFlowService cmsContentAuditFlowService;
	@Autowired
	CmsContentAuditFlowStepsService cmsContentAuditFlowStepsService;
	
	@Autowired
	CmsCategoryMng cmsCategoryMng;

	@RequestMapping(value = "list.do")
	public String ContentAuditFlowList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Integer page, CmsContentAuditFlowVo vo) {
		List<CmsContentAuditFlow> list = cmsContentAuditFlowService.getList(vo);
		if (list == null) {
			list = new ArrayList<>();
		}
		if (page == null) {
			page = 1;
		}
		List<CmsContentAuditFlow> listR = new ArrayList<CmsContentAuditFlow>();
		if (list.size() < page * 10 - 10) {
			page = 1;
		}
		for (int i = 10 * (page - 1); i < (10 * page < list.size() ? 10 * page : list.size()); i++) {
			listR.add(list.get(i));
		}
		Pagination p = new Pagination(page, 10, list.size());
		p.setList(listR);
		model.addAttribute("page", p);
		return "audit/audit_flow";
	}

	/**
	 * 审核流程添加
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "save.do")
	@ResponseBody
	public R ContentAuditFlowSave(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentAuditFlowVo vo) {
		
			return cmsContentAuditFlowService.save(vo);
		
	}

	/**
	 * 审核流程删除
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "delete.do")
	@ResponseBody
	public R delete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentAuditFlowVo vo) {
		Long flow = vo.getFlowId();
		try {
			cmsContentAuditFlowService.delete(flow);
			cmsContentAuditFlowStepsService.deleteSteps(flow);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("删除异常");
		}
		return R.ok("删除成功");
	}
	/**
	 * 审核流程修改
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "update.do")
	@ResponseBody
	public R update(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentAuditFlowVo vo) {
			return cmsContentAuditFlowService.update(vo);
	}
	
	@RequestMapping(value = "edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentAuditFlowVo vo,Integer pageNo) {
		model.addAttribute("vo", vo);
		model.addAttribute("pageNo", pageNo);
		return "audit/audit_flow_edit";
	}
	

	/*@RequestMapping("allCategoryJson.do")
	@ResponseBody
	public List<BaseTreeVo> systemC(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Integer node) {
		if (node == null) {
			node = 0;
		}
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		// Integer siteId = SysUtils.getSiteId(request);
		Integer siteId = 1;
		// Long userId = SysUtils.getUserId(request);
		Long userId = 1L;
		List<CmsCategory> categoryList = cmsCategoryMng.getRetrievingFullTree(userId, siteId, null);
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
			// m.setAccessPath("category/v_list.do?categoryId=" +
			// cmsCategory.getCategoryId());
			m.setChecked(false);
			tree.add(m);
		}
		return tree;
	}
*/
}
