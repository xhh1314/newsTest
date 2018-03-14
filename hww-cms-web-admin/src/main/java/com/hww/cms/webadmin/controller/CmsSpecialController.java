package com.hww.cms.webadmin.controller;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.common.vo.BaseTreeVo;
import com.hww.base.util.R;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.entity.CmsSpecialRCategory;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.manager.CmsSpecialMng;
import com.hww.cms.common.manager.CmsSpecialRCategoryMng;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsSpecialVo;
import com.hww.cms.webadmin.service.CmsSpecialRCategoryService;
import com.hww.cms.webadmin.service.CmsSpecialService;
import com.hww.file.api.FileFeignClient;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
import com.hww.sys.common.dto.SysUserDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/special")
public class CmsSpecialController extends AbsBaseController {
	private static final Logger log = LoggerFactory.getLogger(CmsSpecialController.class);

	@Autowired
	CmsSpecialService cmsSpecialService;
	@Autowired
	CmsSpecialRCategoryService cmsSpecialRCategoryService;
	@Autowired
	CmsCategoryMng cmsCategoryMng;
	@Autowired
	CmsSpecialMng cmsSpecialMng;
	
	@Autowired
	CmsSpecialRCategoryMng cmsSpecialRCategoryMng;

	@Autowired
	FileFeignClient fileFeignClient;
	
	@Resource
	private SessionProvider session;
	
	@Value("${specialUrl}")
	private String specialUrl;
	/**
	 * 
	 * 保存专题
	 * 
	 * @return
	 */
	@RequestMapping(value = "saveSpecial.do")
	@ResponseBody
	public R save(HttpServletRequest request, HttpServletResponse response, ModelMap model, CmsSpecialVo vo) {
		try {
			cmsSpecialService.saveSpecialType(vo);
			// String categoryIds = vo.getCategoryIds();
			// String[] categoryIds_s = categoryIds.trim().split(",");
			// if (categoryIds_s != null) {
			// for (String categoryId : categoryIds_s) {
			// CmsSpecialRCategoryVo cmsSpecialRCategoryVo = new CmsSpecialRCategoryVo();
			// cmsSpecialRCategoryVo.setCategoryId(Long.parseLong(categoryId));
			// cmsSpecialRCategoryVo.setSpecialId(vo.getSpecialId());
			// cmsSpecialRCategoryVo.setCreateTime(new
			// Timestamp(System.currentTimeMillis()));
			// cmsSpecialRCategoryVo.setSiteId(1);
			// cmsSpecialRCategoryService.save(cmsSpecialRCategoryVo);
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("保存失败");
		}
		return R.ok("保存成功");
		/*
		 * String result = "success"; String specialContent =
		 * request.getParameter("specialContents");
		 * 
		 * try { List<CmsCategoryVo> cmsCategoryVo = new LinkedList<CmsCategoryVo>();
		 * if(specialContent!=null) { cmsCategoryVo =
		 * JSONArray.parseArray(specialContent, CmsCategoryVo.class);
		 * vo.setCmsCategoryVos(cmsCategoryVo); } vo.setSiteId(1);
		 * vo.setTypeId(Constants.categoryType.special); //专题类型
		 * cmsSpecialService.saveOrUpdate(vo); }catch(Exception e) {
		 * e.printStackTrace(); result = "保存失败!"; } return result;
		 */
	}

	/**
	 * 保存专题内容
	 * 
	 * @return
	 */
	@RequestMapping(value = "saveChildCategory.do")
	@ResponseBody
	public String saveChildCategory(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsCategoryVo vo) {
		String result = "success";
		vo.setSiteId(1);
		try {
			cmsSpecialService.saveOrUpdateChild(vo);
		} catch (Exception e) {
			e.printStackTrace();
			result = "保存专题内容失败!";
		}
		return result;
	}

	/**
	 * 专题列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "list.do")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model, CmsSpecialVo vo) {
		int pageNo = 1;
		int PageSize = 10;
		if (vo.getPageNo() != null) {
			pageNo = vo.getPageNo();
		}
		try {
			Pagination pagination = cmsSpecialService.listSpecial(vo, pageNo, PageSize);
			model.addAttribute("page", pagination);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询专题列表失败!");
		}
		List<CmsCategory> categoryList = cmsCategoryMng.getCategorysByType(Short.valueOf("3"), 1, vo.getParentId());
		model.addAttribute("list", categoryList);
		// 返回分页条件
		model.addAttribute("searchVo", vo);
		return "special/special_list2";
	}

	/**
	 * 专题审核列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "audit_list.do")
	public String listSpeicalAudit(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsSpecialVo vo) {
		int pageNo = 1;
		int PageSize = 10;
		if (vo.getPageNo() != null) {
			pageNo = vo.getPageNo();
		}
		try {
			Pagination pagination = cmsSpecialService.listSpecial(vo, pageNo, PageSize);
			model.addAttribute("page", pagination);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询专题列表失败!");
		}
		// 返回分页条件
		model.addAttribute("searchVo", vo);
		return "audit/special_audit_list";
	}

	/**
	 * 专题详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "detail.do")
	public String specialDetail(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Long specialId) {
		CmsSpecialVo result = null;
		try {
			if (specialId != null) {
				result = cmsSpecialService.getSpecialView(specialId);
				// 图片url拼接
				if (StringUtils.isNotEmpty(result.getLogo())) {

					String url = fileFeignClient.getUrlByFileIds(result.getLogo());
					List<String> logoUrls = new LinkedList<String>();
					if (url.indexOf(",") > 0) {
						logoUrls = Arrays.asList(url.split(","));
					} else {
						logoUrls.add(url);
					}
					// result.setLogoUrls(logoUrls);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null) {
			result = new CmsSpecialVo();
		}
		model.addAttribute("special", result);
		return "special/special_detail";
	}

	/**
	 * 去编辑页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param specialId
	 * @return
	 */
	@RequestMapping(value = "editSpecial.do")
	public String editSpecial(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Long specialId) {

		CmsSpecial result = null;
		CmsSpecialVo vo=new CmsSpecialVo();
		try {
			if (specialId != null) {
				// 编辑自定义栏目
				result = cmsSpecialMng.find((long) specialId);
				vo.setSpecialId(result.getSpecialId());
				if(result.getParent()!=null) {
					vo.setParentId(result.getParent().getSpecialId());
				}else {
				}
				vo.setSpecialName(result.getSpecialName());
				vo.setKeywords(result.getKeywords());
				vo.setSortNum(result.getSortNum());
				vo.setRecommPriority(result.getRecommPriority());
				
				Finder f = Finder.create("from CmsSpecialRCategory where 1=1");
				f.append("and specialId=:SpecialIdS").setParam("SpecialIdS", result.getSpecialId());
				f.append("and status>0");
				List<CmsSpecialRCategory> listR = (List<CmsSpecialRCategory>) cmsSpecialRCategoryMng.find(f);
				String cIds="";
				for(CmsSpecialRCategory c:listR) {
					cIds=cIds+c.getCategoryId()+",";
				}
				if(cIds.length()>0) {
					cIds=cIds.substring(0, cIds.length()-1);
				}
				vo.setCategoryIds(cIds);
				vo.setSummary(result.getSummary());
				vo.setLogo(result.getLogo());
				if(result.getLogo()!=null){
					String urls = fileFeignClient.getUrlByFileIdFeiApi(result.getLogo());
					vo.setLogoUrls(urls);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("specialOld", vo);
		// 如果为根节点返回专题编辑页,否则返回专题内容编辑
		return "special/special_change";

	}

	/**
	 * 去编辑页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param specialId
	 * @return
	 */
	@RequestMapping(value = "toedit.do")
	public String toEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model, Long specialId) {
		CmsSpecialVo result = null;
		try {
			if (specialId != null) {
				// 编辑自定义栏目
				result = cmsSpecialService.getSpecialView(specialId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null) {
			result = new CmsSpecialVo();
		}
		model.addAttribute("special", result);
		// 如果为根节点返回专题编辑页,否则返回专题内容编辑
		if (result.getParentId() == null) {

			System.out.println("------special/special_add--------");

			return "special/special_add";
		} else {
			System.out.println("------special/special_child_add--------");
			return "special/special_child_add";
		}

	}

	/**
	 * 添加专题下子专题(自定义栏目)
	 * 
	 * @return
	 */
	@RequestMapping("child_add.do")
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model, CmsSpecialVo vo) {
		CmsSpecialVo result = null;
		try {
			if (vo.getSpecialId() != null) {
				// 编辑自定义栏目
				result = cmsSpecialService.getSpecialView(vo.getSpecialId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null) {
			// result = new CmsSpecialVo();
		}
		model.addAttribute("special", result);

		return "special/special_child_add";
	}

	@RequestMapping(value = "delete.do")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap model, Long specialId) {
		String result = "success";
		try {
			if (specialId != null) {
				cmsSpecialService.deleteSpecial(specialId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "删除失败!";
		}
		return result;
	}

	@RequestMapping(value = "batchDelete.do")
	@ResponseBody
	public String batchDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			@RequestParam(value = "specialIds[]") Long[] specialIds) {
		String result = "success";
		try {
			if (specialIds != null && specialIds.length > 0) {
				cmsSpecialService.batchDeleteSpecial(specialIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "删除失败!";
		}
		return result;
	}

	@RequestMapping("getSpecialByJson.do")
	@ResponseBody
	public List<BaseTreeVo> getSpecialByJson(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			@RequestParam(name = "parentId", required = false) Long parentId) {
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		List<CmsSpecial> cmsSpecialList = cmsSpecialService.getSpecialByJson(parentId);
		BaseTreeVo root = new BaseTreeVo();
		root.setId(-1L);
		root.setChkDisabled(false);
		root.setIsParent(true);
		root.setName("根节点");
		root.setChecked(false);
		root.setpId(Long.parseLong("0"));
		tree.add(root);
		for (int i = 0; i < cmsSpecialList.size(); i++) {
			CmsSpecial cmsSpecial = cmsSpecialList.get(i);
			BaseTreeVo m = new BaseTreeVo();
			m.setId(cmsSpecial.getSpecialId());
			m.setChkDisabled(false);
			if (cmsSpecial.getParent() != null) {
				m.setIsParent(false);
				m.setpId(cmsSpecial.getParent().getSpecialId());
			} else {
				m.setIsParent(true);
				m.setpId(-1L);
			}
			m.setName(cmsSpecial.getSpecialName());
			m.setChecked(false);
			tree.add(m);
		}
		return tree;
	}

	/**
	 * 管理员：添加专题接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "saveSpecialType.do")
	@ResponseBody
	public R saveSpecialType(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsSpecialVo vo) {
		try {
			vo.setSpecialUrl(specialUrl);
			SysUserDto user = (SysUserDto) session.getAttribute(request, "login_user");
			vo.setCreator(user.getUsername());
			
			boolean result=cmsSpecialService.saveSpecialType(vo);
			if(!result) {
				return R.error("保存失败").put("result", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("保存失败").put("result", 0);
		}
		return R.ok("保存成功").put("result", 1);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "editSpecialType.do")
	@ResponseBody
	public R editSpecialType(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsSpecialVo vo) {
		try {
			boolean result=cmsSpecialService.updateSpecialType(vo);
			if(!result) {
				return R.error("修改失败").put("result", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("修改失败").put("result", 0);
		}
		return R.ok("修改成功").put("result", 1);
	}

	/**
	 * 管理员：删除专题接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteSpecialType.do")
	@ResponseBody
	public R deleteSpecialType(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsSpecialVo vo) {
		try {
			vo.setSiteId(1);
			vo.setStatus((short) 0);
			boolean reuslt=cmsSpecialService.deleteSpecialType(vo);
			if(!reuslt) {
				return R.error("删除失败").put("result", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return R.error("删除失败").put("result", 0);
		}
		return R.ok("删除成功").put("result", 1);
	}

	@RequestMapping("allCategoryJson.do")
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

	/**
	 * 专题列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "child_list.do")
	public String childList(HttpServletRequest request, HttpServletResponse response, ModelMap model, CmsSpecialVo vo) {
		List<CmsSpecial> list = cmsSpecialService.getChildList(vo);
		Pagination p = new Pagination(1, list.size(), list.size());
		p.setList(list);
		model.addAttribute("page", p);
		model.addAttribute("parentId",vo.getSpecialId());
		model.addAttribute("parentName",vo.getSpecialName());
		return "special/special_child";
	}

}
