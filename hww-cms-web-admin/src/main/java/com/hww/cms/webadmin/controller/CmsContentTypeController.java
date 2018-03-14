package com.hww.cms.webadmin.controller;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.ArrayUtils;
import com.hww.base.util.R;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.Constants;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.manager.CmsContentTypeMng;
import com.hww.cms.common.vo.CmsContentTypeVo;
import com.hww.cms.webadmin.service.CmsContentTypeService;
import com.hww.framework.web.mvc.controller.AbsBaseController;

@Controller
@RequestMapping("/contentType")
public class CmsContentTypeController
	extends
		AbsBaseController {
	@Autowired
	private CmsContentTypeMng cmsContentTypeMng;
	@Autowired
	CmsContentTypeService cmsContentTypeService;

	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return "contenttype/index";
	}

	@RequestMapping("v_list.do")
	public String list(HttpServletRequest request, CmsContentTypeVo form, ModelMap model) {
		List<Long> ids=new ArrayList<>();
		ids.add(2L);
		ids.add(5L);
		ids.add(6L);
		Finder hql = Finder.create("from ");
		hql.append(CmsContentType.class.getName());
		hql.append(" where 1=1 and status=1");
		if (StringUtils.isNotBlank(form.getContentTypeName())) {
			hql.append(" and contentTypeName like :contentTypeNameP").setParam("contentTypeNameP",
					"%" + form.getContentTypeName() + "%");
		}
		Pagination p = cmsContentTypeMng.find(hql, form.getPageNo(), 10);
		List<CmsContentType> list=(List<CmsContentType>) p.getList();
		List<CmsContentType> listL=new ArrayList<>();
		List<CmsContentType> listR=new ArrayList<>();
		for(CmsContentType c:list) {
			if(c.getContentTypeId()==2||c.getContentTypeId()==5||c.getContentTypeId()==6) {
				listL.add(c);
			}else {
				listR.add(c);
			}
		}
		listL.addAll(listR);
		p.setList(listL);
		
		model.addAttribute("page", p);
		model.addAttribute("form", form);
		return "contenttype/list";
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request) {
		return "contenttype/add";
	}

	@RequestMapping("v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, CmsContentTypeVo form,
			ModelMap model) {
		CmsContentType entity = cmsContentTypeMng.find(form.getContentTypeId());
		model.addAttribute("entity", entity);
		return "contenttype/add";
	}

	@RequestMapping("o_save.do")
	@ResponseBody
	public R save(@Valid @ModelAttribute("form") CmsContentTypeVo form, BindingResult errors) {
		if (errors.hasErrors()) {
			return R.error(errors.getAllErrors().get(0).getDefaultMessage());
		}
		CmsContentType entity = new CmsContentType();
		try {
			BeanUtils.copyProperties(entity, form);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entity.setStatus(new Short("1"));
		entity.setCreateTime(new Timestamp(new Date().getTime()));
		cmsContentTypeMng.save(entity);
		return R.ok("操作成功");
	}

	@RequestMapping("o_update.do")
	@ResponseBody
	public R update(@Valid @ModelAttribute("form") CmsContentTypeVo form, BindingResult errors) {
		if (errors.hasErrors()) {
			return R.error(errors.getAllErrors().get(0).getDefaultMessage());
		}
			CmsContentType entity = cmsContentTypeMng.find(form.getContentTypeId());
			entity.setContentTypeName(form.getContentTypeName());
			entity.setLastModifyTime(new Timestamp(new Date().getTime()));
			System.out.println("......................."+entity.getContentTypeName());
			cmsContentTypeMng.save(entity);
			return R.ok("操作成功");
	}

	@RequestMapping("o_delete.do")
	@ResponseBody
	public R delete(HttpServletRequest request, HttpServletResponse response, CmsContentTypeVo form) {
		
		Collection<Long> contentTypeIds = null;
		if (form != null) {
			if (StringUtils.isNotBlank(form.getAllIDCheck())) {
				String[] ids = StringUtils.split(form.getAllIDCheck(), ",");
				if (ArrayUtils.isNotEmpty(ids)) {
					contentTypeIds = new ArrayList<Long>(ids.length);
				}
				for (String str : ids) {
					if (StringUtils.isNumeric(str)) {
						contentTypeIds.add(Long.parseLong(str));
						
					}
				}
			}
			if (form.getContentTypeId() != null) {
				contentTypeIds = new ArrayList<Long>(1);
				contentTypeIds.add(form.getContentTypeId());
			}
		}
		if (contentTypeIds != null) {
			cmsContentTypeMng.updateStatusByProperty(Constants.CONTENT_TPL_STATUS_DELETED, "contentTypeIdsP",
					contentTypeIds);
		}
		return R.ok("操作成功");
	}
	
	/**
	 * 返回所有新闻分类，最多50个
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping("getAll.do")
	@ResponseBody
	public List<CmsContentTypeVo> getAllContentTyps(HttpServletRequest request, HttpServletResponse response) {
		List<CmsContentTypeVo>  vos = new LinkedList<CmsContentTypeVo>();
		try {
			vos = cmsContentTypeMng.getAllContentTypes();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return vos;
	}
}
