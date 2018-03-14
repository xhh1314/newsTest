package com.hww.sns.webadmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
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

import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.SnsForumVo;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.sns.webadmin.service.SnsForumService;
import com.hww.sns.webadmin.service.SnsTopicService;
import com.hww.sys.common.dto.SysUserDto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.vo.BaseTreeVo;
import com.hww.base.util.BeanMapper;
import com.hww.framework.web.Constants;
import com.hww.framework.web.mvc.controller.AbsBaseController;

@Controller
@RequestMapping("/sns/forum")
public class SnsForumController
	extends
		AbsBaseController {

	private static final Log log = LogFactory.getLog(SnsForumController.class);
	@Resource
	SnsForumService snsforumService;
	@Resource
	SnsTopicService snsTopicService;
	
	/**
	 * 保存或更新
	 */
	@RequestMapping(value = "save.do",method = RequestMethod.POST)
	@ResponseBody
	public String saveforum(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsForumVo vo) {
		String reuslt = "success";
		
		Subject subject = SecurityUtils.getSubject();
		Session shiroSession = subject.getSession();
		if(shiroSession!=null) {
			SysUserDto loginUser = (SysUserDto) shiroSession.getAttribute(Constants.SESSION_USER);
			if(loginUser!=null) {
				vo.setCreator(loginUser.getUsername());
			}
		}
		if(vo.getForumName()==null) {
			reuslt = "板块名称不能为空!";
		}
		else {
			snsforumService.saveForum(vo);
		}
		return reuslt;
	}
	
	@RequestMapping(value = "list.do")
	public String listforum(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			SnsForumVo vo) {
		int pageNo = 1;
		int pageSize = 10;
		if(vo.getPageNo()!=null) {
			pageNo = vo.getPageNo();
		}
		Pagination pagination = snsforumService.list(vo, pageNo, pageSize);
		map.addAttribute("page", pagination);
		//搜素条件
		map.addAttribute("searchVo", vo);
		return "forum/forumlist";
	}
	
	@RequestMapping(value = "list_all.do")
	@ResponseBody
	public String listAllSnsForum(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsForumVo vo) {
		List<SnsForumVo> vos = snsforumService.listAllSnsForum();
		
		String result = "";
		
		if(vos!=null&&vos.size()>0) {
			result = JSON.toJSONString(vos);
		}
		return result;
	}
	
	@RequestMapping(value = "allMenuJson.do")
	@ResponseBody
	public List<SnsForumVo> allMenuJson(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			 String cMenuIds,SnsForumVo vo) {
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
		List<SnsForumVo> list = snsforumService.listSnsForumByVo(vo);
		if(list!=null) {
			if(list.size()>0) {
				for (SnsForumVo menuDto : list) {
					menuDto.setId(menuDto.getForumId());
					menuDto.setName(menuDto.getName());
					menuDto.setIsParent(false);
					if (menuDto.getParentId() == null) {
						menuDto.setpId(-1L);
					}else {
						menuDto.setpId(menuDto.getParentId());
					}
					if (menuIdSet != null) {
						menuDto.setChecked(true);
					} else {
						menuDto.setChecked(false);
					}
				}
			}
		}else {
			list = new LinkedList<SnsForumVo>();
		}	
		//添加根节点
		SnsForumVo rootNode = new SnsForumVo();
		rootNode.setId(-1L);
		rootNode.setName("根节点");
		rootNode.setIsParent(true);
		rootNode.setChecked(true);
		list.add(rootNode);
		return list;
	}
	
	@RequestMapping(value = "delete.do")
	@ResponseBody
	public String deleteCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsForumVo vo) {
		String result = "success";
		if(vo.getForumId()==0 && vo.getForumId()==1 && vo.getForumId()==2){
			result = "删除失败!";
		}else{
		try {
			snsforumService.deleteForum(vo);
		
		}catch(Exception e){
			e.printStackTrace();
			result = "删除失败!";
		}
		}
		return result;
	}
	
	
	
	
	
	@RequestMapping(value = "del.do")
	@ResponseBody
	public String delForum(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsForumVo vo) {
		String result = "success";
		if(vo.getForumId()==0 && vo.getForumId()==1 && vo.getForumId()==2){
			result = "删除失败!";
		}else{
		try {
			snsforumService.updateForum(vo);
			List<SnsTopic> snstopiclist= snsTopicService.findByForumId(vo.getForumId());
			if(snstopiclist.size()>0) {
			List<SnsTopicVo> vlist= BeanMapper.mapList(snstopiclist,SnsTopicVo.class);
			for(SnsTopicVo s: vlist) {
				snsTopicService.updateSnsTopic(s);
				}
			
			}
			result = "删除成功!";
		}catch(Exception e){
			e.printStackTrace();
			result = "删除失败!";
		}
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "batchdelete.do")
	@ResponseBody
	public String batchDeleteCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			@RequestBody SnsForumVo[] vos) {
		String result = "success";
		try {
			snsforumService.batchDeleteForum(Arrays.asList(vos));
		}catch(Exception e){
			e.printStackTrace();
			result = "批量删除失败!";
		}
		return result;
	}
	
	@RequestMapping(value = "toedit.do")
	public String updateforum(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsForumVo vo) {
		String forumId = request.getParameter("forumId");
		Long id=Long.valueOf(forumId);
		if(id==0 && id==1 && id==2){
			map.addAttribute("forum", "此模块不允许修改");
		}else{
			if(forumId!=null) {
				try {
					SnsForumVo forum = snsforumService.findById(Long.parseLong(forumId));
					if(forum!=null) {
						map.addAttribute("forum",forum);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		return "forum/forum_add";
	}
	
	
	//显示tree列表
	@RequestMapping(value = "allMenuJsonForum.do")
	@ResponseBody
	public List<BaseTreeVo> allMenuJsonForum(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			SnsForumVo vo,Integer node) {
		log.info("打开菜单列表页");
		if (node == null) {
			node = 0;
		}
		List<BaseTreeVo> tree = new ArrayList<BaseTreeVo>();
		List<SnsForumVo> snsForumlist = snsforumService.listAllSnsForum();
		BaseTreeVo root=new BaseTreeVo();
		root.setId(-1L);
		root.setName("根节点");
		root.setIsParent(true);
		tree.add(root);
	for (SnsForumVo snsForumVo : snsForumlist) {
				BaseTreeVo baseTreeVo = new BaseTreeVo();
				baseTreeVo.setId(snsForumVo.getForumId());
				baseTreeVo.setChkDisabled(false);
				if  (snsForumVo.getParentId() != null) {
					baseTreeVo.setIsParent(false);
				} else {
					baseTreeVo.setIsParent(true);
				}
				
				if (snsForumVo.getParent() != null) {
					baseTreeVo.setpId(snsForumVo.getParent().getForumId());
				} else {
					baseTreeVo.setpId(Long.parseLong("-1"));
					baseTreeVo.setIsParent(true);
				}
				baseTreeVo.setName(snsForumVo.getForumName());
				baseTreeVo.setAccessPath("sns/forum/list.do");
				baseTreeVo.setChecked(false);
				tree.add(baseTreeVo);
			}
		return tree;

	}
	

	
	
	
	
	
	
	
	
}
