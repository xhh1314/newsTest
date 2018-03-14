package com.hww.cms.webadmin.controller;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.common.vo.BaseTreeVo;
import com.hww.base.util.ArrayUtils;
import com.hww.base.util.R;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.Constants;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContentAuditFlow;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.manager.CmsContentMng;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentAuditFlowVo;
import com.hww.cms.webadmin.service.CmsCategoryService;
import com.hww.cms.webadmin.service.CmsContentAuditFlowService;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.util.SysUtils;
import freemarker.template.Configuration;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/category")
public class CmsCategoryController
	extends
		AbsBaseController {
	private static final Logger log = LoggerFactory.getLogger(CmsCategoryController.class);

	@Autowired
	CmsCategoryMng cmsCategoryMng;

	@Autowired
	SysFeignClient sysFeignClient;

	@Autowired
	Configuration publishFreeMarkerConfiguration;
	
	@Autowired
	CmsCategoryService cmsCategoryService;

	@Autowired
	CmsContentAuditFlowService cmsContentAuditFlowService;
	@Autowired
	CmsContentMng cmsContentMng;

	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "category/index";
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model, CmsCategoryVo vo) {
		CmsCategory parent = cmsCategoryMng.find(vo.getParentId());
//		List<SysSite> sites = (List<SysSite>) SysSiteMng.find(1, 10).getList();
		SysSiteDto sysSiteDto=new SysSiteDto();
		List<SysSiteDto> sites=sysFeignClient.findSitelist(sysSiteDto);
		model.addAttribute("sites", sites);
		//TODO 
//		vo.setSiteId(SysUtils.getSiteId(request));
		vo.setSiteId(1);
		model.addAttribute("form", vo);
		model.addAttribute("parent", parent);
		List<CmsContentAuditFlow> specialList=cmsContentAuditFlowService.getList(new CmsContentAuditFlowVo());
		if(specialList==null) {
			specialList=new ArrayList<>();
		}
		model.addAttribute("specialList", specialList);
		
		return "category/add";
	}

	@RequestMapping(value = "v_tree.do")
	public String tree(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		return "category/treemain";

	}

	@RequestMapping(value = "v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, CmsCategoryVo form, ModelMap model) {
		CmsCategory entity = cmsCategoryMng.find(form.getCategoryId());
		List<CmsContentAuditFlow> specialList=cmsContentAuditFlowService.getList(new CmsContentAuditFlowVo());
		if(specialList==null) {
			specialList=new ArrayList<>();
		}
		model.addAttribute("specialList", specialList);

		if (entity != null) {
//			List<SysSite> sites = (List<SysSite>) SysSiteMng.find(1, 10).getList();
			SysSiteDto sysSiteDto=new SysSiteDto();
			List<SysSiteDto> sites=sysFeignClient.findSitelist(sysSiteDto);
			model.addAttribute("entity", entity);
			model.addAttribute("sites", sites);
			model.addAttribute("form", form);

			return "category/edit";
		} else {
			return "error-404";
		}

	}

	@RequestMapping("o_update.do")
	@ResponseBody
	public Boolean update(@Valid @ModelAttribute("form") CmsCategoryVo form, BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		CmsCategory entity = new CmsCategory();
		CmsCategory parent = cmsCategoryMng.find(form.getParentId());
		try {
			BeanUtils.copyProperties(entity, form);

			entity.setParent(parent);
            entity.setSiteId(1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cmsCategoryMng.saveOrUpdateCategoryAndAndRelationshipWidthTpl(entity, form, false);
		return true;
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public Boolean save(HttpServletRequest request, @Valid @ModelAttribute("form") CmsCategoryVo form,
			BindingResult errors) {
		if (errors.hasErrors()) {
			return false;
		}
		CmsCategory entity = new CmsCategory();
		try {
			BeanUtils.copyProperties(entity, form);
			//TODO 暂时写死
//			Integer siteId = SysUtils.getSiteId(request);
			Integer siteId=1;
			CmsCategory parentCmsCategory = cmsCategoryMng.find(form.getParentId());
			entity.setSiteId(siteId);
			entity.setParent(parentCmsCategory);
            entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			entity.setRgt(Long.parseLong("3"));
			entity.setLft(Long.parseLong("2"));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cmsCategoryMng.saveOrUpdateCategoryAndAndRelationshipWidthTpl(entity, form, true);

        CmsCategory parentCmsCategory = cmsCategoryMng.find(form.getParentId());
        String relatedCategoryId = parentCmsCategory.getRelatedCategoryId();
        CmsCategory parentCmsCategory_entity = cmsCategoryMng.find(entity.getCategoryId());
        relatedCategoryId = relatedCategoryId + "," + parentCmsCategory_entity.getCategoryId();
        parentCmsCategory_entity.setRelatedCategoryId(relatedCategoryId);
        cmsCategoryMng.saveOrUpdateCategoryAndAndRelationshipWidthTpl(parentCmsCategory_entity, form, true);
		
		return true;
	}

	@RequestMapping(value = "v_list.do")
	public String subList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsCategoryVo form) {
//		Integer siteId = SysUtils.getSiteId(request);
		Integer siteId=1;
		Finder hql = Finder.create("from ");
		hql.append(CmsCategory.class.getName());
		
		hql.append(" where 1=1");
		
		if (form.getParentId()!=null) {
			hql.append(" and parent.categoryId=:pCategoryId ").setParam("pCategoryId", form.getParentId());
		}
		
		
		hql.append(" and siteId=:siteIdP").setParam("siteIdP", siteId);
		if (StringUtils.isNotBlank(form.getCategoryName())) {
			hql.append(" and categoryName like :categoryNameP").setParam("categoryNameP",
					"%" + form.getCategoryName() + "%");
		}
		if (form.getStatus() != null) {
			hql.append(" and status=:statusP").setParam("statusP", form.getStatus());
		}
		Pagination p = cmsCategoryMng.find(hql, 1, 10);
		model.addAttribute("page", p);
		model.addAttribute("form", form);
		return "category/list";

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param node
	 * @param cCategoryIds
	 *            被选中的项-源
	 * @param srcCategoryIdLock
	 *            true不能操作源id
	 * @param op
	 *            only parent 只显示父节点
	 */
	@RequestMapping(value = "enableCategoryJson.do")
	@ResponseBody
	public List<BaseTreeVo> allTree(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Integer node, String cCategoryIds, Integer op, Boolean srcCategoryIdLock, Integer srcCategoryId) {
		log.info("打开菜单列表页");
		if (node == null) {
			node = 0;
		}
		String[] categoryIds = null;
		Set<Integer> categoryIdSet = null;
		if (StringUtils.isNotBlank(cCategoryIds)) {
			if (cCategoryIds.lastIndexOf(",") == cCategoryIds.length()) {
				categoryIds = cCategoryIds.substring(0, cCategoryIds.length() - 1).split(",");
			}
			categoryIds = cCategoryIds.split(",");
			categoryIdSet = new HashSet<Integer>(categoryIds.length);
			for (String categoryId : categoryIds) {
				categoryIdSet.add(Integer.parseInt(categoryId));
			}
		}
//		Integer siteId = SysUtils.getSiteId(request);
		//TODO 写死
		Integer siteId=1;
		Long userId = SysUtils.getUserId(request);
		List<CmsCategory> list = null;
		if (op != null) {
			list = cmsCategoryMng.getRetrievingASinglePath(op, siteId);
		} else {
			list = cmsCategoryMng.getRetrievingFullTree(userId, siteId, null);
		}
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		for (CmsCategory cmsCategory : list) {
			BaseTreeVo m = new BaseTreeVo();
			m.setId(cmsCategory.getCategoryId());
			m.setChkDisabled(false);
			if (cmsCategory.getRgt() - cmsCategory.getLft() == 1 && cmsCategory.getParentId() != null) {
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
			m.setAccessPath("content/v_list.do?categoryId=" + cmsCategory.getCategoryId());
			if (categoryIdSet != null && categoryIdSet.contains(cmsCategory.getCategoryId())) {
				m.setChecked(true);
				if (cmsCategory.getCategoryId().equals(srcCategoryId) && srcCategoryIdLock) {
					m.setChkDisabled(true);
				}
			} else {
				m.setChecked(false);
			}
			tree.add(m);
		}
		return tree;
	}

	@RequestMapping("allCategoryJson.do")
	@ResponseBody
	public List<BaseTreeVo> systemC(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Integer node,Short status) {
		if (node == null) {
			node = 0;
		}
		
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		//Integer siteId = SysUtils.getSiteId(request);
		Integer siteId  = 1;
		//Long userId = SysUtils.getUserId(request);
		Long userId = 1L;
		List<CmsCategory> categoryList = cmsCategoryMng.getRetrievingFullTree(userId, siteId, status);
		for (CmsCategory cmsCategory : categoryList) {
			BaseTreeVo m = new BaseTreeVo();
			m.setId(cmsCategory.getCategoryId());
			m.setChkDisabled(false);
			if  (cmsCategory.getParentId() != null) {
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
			m.setAccessPath("category/v_list.do?categoryId=" + cmsCategory.getCategoryId());
			m.setChecked(false);
			tree.add(m);
		}
		return tree;
	}
	
	/**
	 * 区分单页、外部链接、列表、专题树
	 * @param typeId
	 * @param parentId 为空查全部，为0查根节点，为其他查对应树
	 * @return
	 */
	@RequestMapping("getCategoryJsonByType.do")
	@ResponseBody
	public List<BaseTreeVo> getCategoryJsonByType(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Short typeId,@RequestParam(name="parentId",required=false)Long parentId) {
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		//Integer siteId = SysUtils.getSiteId(request);
		Integer siteId  = 1;
		//Long userId = SysUtils.getUserId(request);
		
		List<CmsCategory> categoryList = cmsCategoryMng.getCategorysByType(typeId, siteId,parentId);
		for (int i=0;i< categoryList.size();i++) {
			CmsCategory cmsCategory = categoryList.get(i);
			BaseTreeVo m = new BaseTreeVo();
			m.setId(cmsCategory.getCategoryId());
			m.setChkDisabled(false);
			if (cmsCategory.getParentId() != null) {
				m.setIsParent(false);
				m.setpId(cmsCategory.getParent().getCategoryId());
			} else {
				m.setIsParent(true);
				m.setpId(Long.parseLong("0"));
			}
			m.setName(cmsCategory.getCategoryName());
			m.setChecked(false);
			tree.add(m);
		}
		return tree;
	}

	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response, CmsCategoryVo form) {
		Collection<Long> categoryIds = null;
		if (form != null) {

			if (StringUtils.isNotBlank(form.getAllIDCheck())) {
				String[] ids = StringUtils.split(form.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					categoryIds = new ArrayList<Long>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						categoryIds.add(Long.parseLong(str));
					}
				}
			}
			if (form.getCategoryId() != null) {// 单个删除
				categoryIds = new ArrayList<Long>(1);
				categoryIds.add(form.getCategoryId());
			}
		}
		if (categoryIds != null) {
			cmsCategoryMng.updateStatusByProperty(Constants.CATEGORY_STATUS_DELETED, "categoryId", categoryIds);
		}

		return "redirect:v_list.do?parentId=" + form.getParentId();
	}
	@RequestMapping(value = "v_delete_category.do")
	@ResponseBody
	public R getCmsCategoryById(HttpServletRequest request, HttpServletResponse response, CmsCategoryVo form, ModelMap model) {
		try {
			cmsCategoryService.deleteCategory(form);
		}catch(Exception e) {
			return R.error("删除失败！");
		}
		return R.ok("成功");
	}


}
