/*package com.hww.sns.webadmin.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.sns.common.vo.SnsForumVo;
import com.hww.sns.webadmin.service.SnsCategoryService;
import com.hww.sns.webadmin.service.SnsForumService;
import com.hww.sys.common.dto.SysUserDto;
import com.alibaba.fastjson.JSON;
import com.hww.base.common.page.Pagination;
import com.hww.framework.web.Constants;
import com.hww.framework.web.mvc.controller.AbsBaseController;

*//**
 * 分类管理
 *
 *//*
@Controller
@RequestMapping("/sns/category")
public class SnsCategoryController
	extends
		AbsBaseController {

	private static final Log log = LogFactory.getLog(SnsCategoryController.class);
	@Resource
	SnsCategoryService snsCategoryService;
	@Resource
	SnsForumService snsforumService;
	
	*//**
	 * 保存或更新
	 *//*
	@RequestMapping(value = "save.do",method = RequestMethod.POST)
	@ResponseBody
	public String saveCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			SnsCategoryVo vo) {
		String reuslt = "success";
		Subject subject = SecurityUtils.getSubject();
		Session shiroSession = subject.getSession();
		if(shiroSession!=null) {
			SysUserDto loginUser = (SysUserDto) shiroSession.getAttribute(Constants.SESSION_USER);
			if(loginUser!=null) {
				vo.setCreator(loginUser.getUsername());
			}
		}
		
		if(StringUtils.isEmpty(vo.getCategoryName())) {
			reuslt = "分类名称不能为空!";
		}else {
			snsCategoryService.saveCategory(vo);
		}
		return reuslt;
	}
	
	@RequestMapping(value = "list.do")
	public String listCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsCategoryVo vo,SnsForumVo vos) {
		int pageNo = 1;
		int pageSize = 10;
		if(vo.getPageNo()!=null) {
			pageNo = vo.getPageNo();
		}
		Pagination pagination = snsCategoryService.list(vo, pageNo, pageSize);
		map.addAttribute("page", pagination);
		Pagination paginations = snsforumService.list(vos, pageNo, pageSize);
		map.addAttribute("pages", paginations);
		//搜素条件
		map.addAttribute("searchVos", vo);
		return "category/category_list";
	}
	
	@RequestMapping(value = "allMenuJson.do")
	@ResponseBody
	public List<SnsCategoryVo> allMenuJson(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			 String cMenuIds,SnsCategoryVo vo) {
		log.info("打开菜单列表页");
		String[] menuIds = null;
		Set<Integer> menuIdSet = null;
		if (StringUtils.isNotEmpty(cMenuIds)) {
			menuIds = cMenuIds.substring(0, cMenuIds.length() - 1).split(",");
			menuIdSet = new HashSet<Integer>(menuIds.length);
			for (String menuId : menuIds) {
				menuIdSet.add(Integer.parseInt(menuId));
			}
		}
//		Integer siteId = SysUtils.getSiteId(request);
//		Long userId = SysUtils.getUserId(request);
		vo.setSiteId(1);
		vo.setUserId(1L);
		List<SnsCategoryVo> list = snsCategoryService.listSnsCategorys(vo);
		
		for (SnsCategoryVo menuDto : list) {
			menuDto.setId(menuDto.getCategoryId());
			menuDto.setName(menuDto.getCategoryName());
			menuDto.setIsParent(false);
			if (menuDto.getParentId() == null) {
				menuDto.setpId(-1L);
			}else {
				menuDto.setpId(menuDto.getParentId());
			}
			if (menuIdSet != null && menuIdSet.contains(menuDto.getCategoryId())) {
				menuDto.setChecked(true);
			} else {
				menuDto.setChecked(false);
			}
		}
		//添加根节点
		SnsCategoryVo rootNode = new SnsCategoryVo();
		rootNode.setId(-1L);
		rootNode.setName("根节点");
		rootNode.setIsParent(true);
		rootNode.setChecked(true);
		list.add(rootNode);
		return list;

	}
	
	*//**
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param vo
	 *//*
	@RequestMapping(value = "list_all.do")
	@ResponseBody
	public String listAllCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			SnsCategoryVo vo) {
		List<SnsCategoryVo> vos = snsCategoryService.listSnsCategorys(vo);
		//返回List<SnsCategoryVo>报fastjson转换失败,故用fastjson手动转换
		String result = JSON.toJSONString(vos);
		return result;
	}
	
	*//**
	 * 分类树
	 * @return 树json串
	 *//*
	@RequestMapping(value = "list_tree.do")
	@ResponseBody
	public String listCategoryTree(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			SnsCategoryVo vo) {
		
		List<SnsCategoryVo> vos = snsCategoryService.listSnsCategorys(vo);
		String jsonTree = "";
		if(vos!=null&&vos.size()>0) {
			jsonTree = snsCategoryService.getCategoryTreeJson(vos);
		}
		log.info(jsonTree);
		return jsonTree;
	}
	
	@RequestMapping(value = "delete.do")
	@ResponseBody
	public String deleteCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsCategoryVo vo) {
		String result = "success";
		try {
			snsCategoryService.deleteCategory(vo);
		}catch(Exception e){
			e.printStackTrace();
			result = "删除失败!";
		}
		return result;
	}
	
	@RequestMapping(value = "batchdelete.do")
	@ResponseBody
	public String batchDeleteCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			@RequestBody SnsCategoryVo[] vos) {
		String result = "success";
		try {
			snsCategoryService.batchDeleteCategory(Arrays.asList(vos));
		}catch(Exception e){
			e.printStackTrace();
			result = "批量删除失败";
		}
		return result;
	}
	
	@RequestMapping(value = "toedit.do")
	public String toEditCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
		String categoryId = request.getParameter("categoryId");
		if(categoryId!=null) {
			try {
				SnsCategoryVo vo = snsCategoryService.findById(Long.parseLong(categoryId));
				if(vo!=null) {
					map.addAttribute("category",vo);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "category/category_add";
	}
}
*/