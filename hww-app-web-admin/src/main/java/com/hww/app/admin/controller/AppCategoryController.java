package com.hww.app.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.app.admin.service.AppCategoryRCmsCategoryService;
import com.hww.app.admin.service.AppCategoryService;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppCategoryRCmsCategory;
import com.hww.app.common.manager.AppCategoryMng;
import com.hww.app.common.vo.AppCategoryVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.common.vo.BaseTreeVo;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.R;
import com.hww.base.util.StringUtils;
import com.hww.cms.api.CmsFeignClient;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;

/**
 * @author XiaoBG APP后台频道管理类controller 2018年1月9日
 */
@Controller
@RequestMapping("/category")
public class AppCategoryController extends AbsBaseController {

	@Autowired
	private AppCategoryService appCategoryService;
	@Autowired
	private AppCategoryMng appCategoryMng;
	@Autowired
	private SysFeignClient sysFeignClient;
	@Autowired
	CmsFeignClient cmsFeignClient;
	
	@Autowired
	private AppCategoryRCmsCategoryService appCategoryRCmsCategoryService;

	private static final Logger log = LoggerFactory.getLogger(AppCategoryController.class);

	/**
	 * 管理员：添加新频道
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14 param appCategoryVo
	 * @return
	 * @version v0.1
	 */
	@RequestMapping(value = "saveColumn.do")
	@ResponseBody
	public R addColumn(AppCategoryVo appCategoryVo) {

		log.info("----------APP端添加频道信息开始----------");
		if (appCategoryVo == null) {
			return R.error(500, "添加失败");
		}
		appCategoryService.saveCategory(appCategoryVo);
		log.info("----------APP端添加频道信息结束----------");
		return R.ok().put("data", "添加完成");
	}

	/**
	 * 管理员：删除频道信息
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14 param appCategory
	 * @return
	 * @version v0.1
	 */
	@RequestMapping(value = "deleteColumn.do")
	@ResponseBody
	public R deleteCategory(Long columnId) {

		log.info("----------APP端删除频道信息功能开始----------");
		if (columnId == null) {
			return R.error(500, "参数为空");
		}
		appCategoryService.deleteCategory(columnId);
		log.info("----------APP端删除频道信息功能结束----------");
		return R.ok().put("data", "删除完成");
	}

	/**
	 * 管理员：修改频道信息
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14 param appCategory
	 * @return
	 * @version v0.1
	 */
	@RequestMapping(value = "updateColumn.do")
	@ResponseBody
	public R updateCategory(AppCategoryVo appCategoryVo) {

		log.info("----------APP端修改频道信息功能开始----------");
		if (appCategoryVo == null) {
			return R.error(500, "查询失败");
		}
		if(appCategoryVo.getIsDisplay()!=null&&appCategoryVo.getIsDisplay().intValue()==1) {
			appCategoryVo.setIsDisplay(1);
		}else {
			appCategoryVo.setIsDisplay(0);
		}
		appCategoryService.updateCategory(appCategoryVo);
		log.info("----------APP端修改频道信息功能结束----------");
		return R.ok().put("data", "修改完成");
	}

	/**
	 * 管理员：查询频道信息
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14 param appCategory
	 * @return
	 * @version v0.1
	 */
	@RequestMapping(value = "selectColumn.do")
	@ResponseBody
	public R selectCategory(AppCategory appCategory) {

		log.info("----------APP端添加频道信息开始----------");
		if (appCategory == null) {
			return R.error(500, "添加失败");
		}
		List<AppCategory> selectAppCategoryInfo = appCategoryService.selectAppCategoryInfo(appCategory);
		log.info("----------APP端添加频道信息结束----------");
		return R.ok().put("data", selectAppCategoryInfo);
	}

	/**
	 * 管理员：加载频道列表树
	 *
	 * @author XiaoBG
	 * @date 2018年1月11日 下午9:45:14 param appCategory
	 * @return
	 * @version v0.1
	 */
	@RequestMapping(value = "allCategoryJson.do")
	@ResponseBody
	public List<BaseTreeVo> loadAppCategoryTree(HttpServletRequest request, HttpServletResponse response,
			ModelMap model, Integer node) {

		log.info("----------APP后台加载频道列表数树开始----------");

		List<BaseTreeVo> appCategoryTree = appCategoryService.loadAppCategoryTree(node);

		log.info("----------APP后台加载频道列表数树结束----------");
		return appCategoryTree;
	}

	// 显示列表
	@RequestMapping(value = "v_list.do")
	public String list(AppCategoryVo appCategoryVo, ModelMap model) {
		Integer siteId = 1;
		Finder hql = Finder.create("from ");
		hql.append(AppCategory.class.getName());

		hql.append(" where 1=1");

		if (appCategoryVo.getParentId() != null) {
			hql.append(" and parent.columnId=:pcolumnId ").setParam("pcolumnId", appCategoryVo.getParentId());
		}
		hql.append(" and siteId=:siteIdP").setParam("siteIdP", siteId);
		if (StringUtils.isNotBlank(appCategoryVo.getColumnName())) {
			hql.append(" and columnName like :columnNameP").setParam("columnNameP",
					"%" + appCategoryVo.getColumnName() + "%");
		}
		if (appCategoryVo.getStatus() != null) {
			hql.append(" and status=:statusP").setParam("statusP", appCategoryVo.getStatus());
		}
		Pagination p = appCategoryMng.find(hql, 1, 10);
		model.addAttribute("page", p);
		model.addAttribute("appCategoryVo", appCategoryVo);
		return "category/list";
	}

	// 添加列表
	@RequestMapping(value = "v_add.do")
	public String add(ModelMap model, AppCategoryVo vo) {
		AppCategory parent = appCategoryMng.find(vo.getParentId());
		// List<SysSiteDto> sites = (List<SysSite>) sysSiteMng.find(1, 10).getList();
		SysSiteDto sysSiteDto = new SysSiteDto();
		List<SysSiteDto> sites = sysFeignClient.findSitelist(sysSiteDto);
		model.addAttribute("sites", sites);
		// TODO
		// vo.setSiteId(SysUtils.getSiteId(request));
		vo.setSiteId(1);
		model.addAttribute("form", vo);
		model.addAttribute("parent", parent);
		return "category/add_category";

	}

	// 修改页面
	@RequestMapping(value = "v_edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response, AppCategoryVo form, ModelMap model) {
		AppCategory entity = appCategoryMng.find(form.getColumnId());

		if (entity != null) {
			// List<SysSite> sites = (List<SysSite>) sysSiteMng.find(1, 10).getList();
			SysSiteDto sysSiteDto = new SysSiteDto();
			List<SysSiteDto> sites = sysFeignClient.findSitelist(sysSiteDto);
			List<AppCategoryRCmsCategory> listR = appCategoryRCmsCategoryService.findByCategoryId(form.getColumnId());
			String RStr = "";
			for (AppCategoryRCmsCategory r : listR) {
				if (r.getStatus() > 0) {
					RStr = RStr + "," + r.getCategoryId();
				}
			}
			if (RStr.length() > 0) {
				RStr = RStr.substring(1);
			}
			model.addAttribute("entity", entity);
			model.addAttribute("sites", sites);
			model.addAttribute("form", form);
			model.addAttribute("r", RStr);

			return "category/edit";
		} else {
			return "error-404";
		}

	}

	// 显示子菜单列表
	@RequestMapping("v_findChildrenlist.do")
	public String findChildrenList(HttpServletRequest request, HttpServletResponse response, AppCategoryVo form,
			ModelMap model) {
		Finder finder = Finder.create(" from AppCategory ");
		AppCategory appCategory = BeanMapper.map(form, AppCategory.class);
		finder.append(" where parent=:columnId").setParam("columnId", appCategory);
		Pagination p = appCategoryMng.find(finder, 1, 10);

		model.addAttribute("page", p);

		return "category/child_list";

	}



	@RequestMapping(value = "allCmsCategoryJson.do")
	@ResponseBody
	public List<BaseTreeVo> loadCmsCategoryTree(HttpServletRequest request, HttpServletResponse response) {

		List<BaseTreeVo> cmsCategoryTree = cmsFeignClient.allCategoryJson();
		return cmsCategoryTree;
	}

	@RequestMapping(value = "sort.do")
	@ResponseBody
	public R sort(Integer sort) {
		if (sort == null) {
			return R.error("排序不能为空！").put("result", -1);
		}
		List<Integer> list = appCategoryService.sort(sort);
		if(list.contains(sort)) {
			String hasSort="";
			for(Integer i:list) {
				hasSort=hasSort+i+",";
			}
			hasSort=hasSort.substring(0, hasSort.length()-1);
			return R.error("该排序号已存在！").put("result", 0).put("exist", hasSort);
		}
		return R.ok("可以使用！").put("result", 1);
	}
	

	@RequestMapping("sysUserLoginUpdate.do")
	@ResponseBody
    public String updateUserPassword(String userName, String passwords) {
    	SysUserDto dto=new SysUserDto();
    	dto.setUsername(userName);
    	dto.setPassword(passwords);
    	R r=sysFeignClient.updateUserPassword(dto);
    	if(r.isOk()) {
    		return "修改成功";
    	}
		return "修改失败";
    	
    };


}