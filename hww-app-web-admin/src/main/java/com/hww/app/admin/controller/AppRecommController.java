package com.hww.app.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.app.admin.service.AppCategoryService;
import com.hww.app.admin.service.AppRecommConfigService;
import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppRecommConfig;
import com.hww.app.common.vo.AppCategoryVo;
import com.hww.app.common.vo.AppRecommConfigVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanMapper;


@Controller
@RequestMapping(value = "/recomm")
public class AppRecommController {
		@Autowired
		AppRecommConfigService appRecommConfigService;
		@Autowired
		AppCategoryService appCategoryService;

		@RequestMapping(value = "loadAllRecommFeginApi.do", method = RequestMethod.POST)

		public List<AppRecommConfigDto> loadAllRecomm(){
				return appRecommConfigService.loadAllRecomm();
	 } 

	/*@RequestMapping("list.do")
	public String List(HttpServletRequest request, HttpServletResponse response, Session session, AppRecommConfigVo vo,  ModelMap modelMap) {
	    Pagination page= appRecommConfigService.queryRecommLists(vo);
	   
	   modelMap.addAttribute("page",page);
	    return "recomm/index";
	}*/
	
	
	@RequestMapping("del.do")
	@ResponseBody
	public String delRecomm(Long columnId,ModelMap map) {
		String result="success";
		if(columnId==-1L || columnId==0L) {
			result="该栏目无法被删除";
		}else {
			appRecommConfigService.delRecommById(columnId);
		}
		return result;	
	}
	
	@RequestMapping("update.do")
	@ResponseBody
	public String updateRecomm(AppRecommConfigVo appRecommConfigVo,ModelMap map) {
		String result="success";
		AppRecommConfigVo vo=appRecommConfigService.queryAppRecommConfig(appRecommConfigVo.getColumnId());
		vo.setRecommNum(vo.getRecommNum()+appRecommConfigVo.getRecommNum());
		appRecommConfigService.updateAppRecomm(vo);
		if(vo!=null) {
		result="success";
		}else {
			result="添加推荐失败!";
		}
		return result;	
	}
	
	
	@RequestMapping(value="addRecomms.do")
	public AppRecommConfigDto addRecomms(AppCategoryVo appCategoryVo) {
		AppCategory appCategory=BeanMapper.map(appCategoryVo, AppCategory.class);
		AppRecommConfig appRecommConfig=	appRecommConfigService.saveAppCategory(appCategory);
		AppRecommConfigDto appRecommConfigDto=BeanMapper.map(appRecommConfig, AppRecommConfigDto.class);
		return appRecommConfigDto;
	}
	
	
	
	@RequestMapping(value="addRecomm.do")
	@ResponseBody
	public String addRecomm(AppRecommConfigVo form) {
		String result="success";
		AppRecommConfigVo vos =appRecommConfigService.queryAppRecommConfig(form.getColumnId());
		if(vos!=null) {
			result="该频道已经是推荐频道";
		}else {
		AppRecommConfig appRecommConfig=BeanMapper.map(form, AppRecommConfig.class);
		appRecommConfigService.saveAppRecommConfig(appRecommConfig);
		result="推荐频道添加成功";
		}
		return result;
	}
	
	
	
	
	/*@RequestMapping(value="addRecomm.do")
	 @ResponseBody
	public List<BaseTreeVo> addRecomms(ModelMap map,Integer node) {
		 List<BaseTreeVo> appCategoryTree = appCategoryService.loadAppCategoryTree(node);
		return appCategoryTree;
	}*/
	
	/*@RequestMapping("list.do")
	public String queryList(AppCategoryVo appCategoryVo,ModelMap map, Session session) {
		AppCategory appCategory=BeanMapper.map(appCategoryVo,AppCategory.class);
		List<AppCategory> appCategorylist=appCategoryService.selectAppCategoryInfo(appCategory);
		List<AppRecommConfig> AppRecommConfiglist=new ArrayList<AppRecommConfig>();
		for(AppCategory a:appCategorylist) {
			AppRecommConfig appRecommConfig=  appRecommConfigService.queryListById(a.getColumnId());
			if(appRecommConfig!=null) {
			    appRecommConfig.setRecommNum(appRecommConfig.getRecommNum());
				AppRecommConfiglist.add(appRecommConfig);
			}else {
				AppRecommConfig appRecomm=new AppRecommConfig();
				appRecomm.setColumnName(a.getColumnName());
				appRecomm.setCreateTime(a.getCreateTime());
				appRecomm.setRecommNum(0);
				appRecomm.setColumnId(a.getColumnId());
				appRecomm.setStatus((short)1);
				appRecomm.setType(a.getSpecialType());
				AppRecommConfiglist.add(appRecomm);
			}
		}
		
		List<AppRecommConfigVo> appRecommConfigVolist=BeanMapper.mapList(AppRecommConfiglist, AppRecommConfigVo.class);
		map.addAttribute("recomm",appRecommConfigVolist);
		return "recomm/index";
	}*/
	
	
	
	
	@RequestMapping("list.do")
	public String queryList(AppCategoryVo appCategoryVo,ModelMap map,AppRecommConfigVo vo) {
		AppCategory appCategory=BeanMapper.map(appCategoryVo,AppCategory.class);
		List<AppCategory> appCategorylist=appCategoryService.selectAppCategoryInfo(appCategory);
		for(AppCategory a:appCategorylist) {
			AppRecommConfig appRecommConfig=  appRecommConfigService.queryListById(a.getColumnId());
			if(appRecommConfig!=null) {
				appRecommConfig.setStatus(a.getStatus());
				AppRecommConfigVo appRecommConfigVo=BeanMapper.map(appRecommConfig, AppRecommConfigVo.class);
				appRecommConfigService.updateAppRecomm(appRecommConfigVo);
			}
			if(appRecommConfig==null) {
				AppRecommConfig appRecomm=new AppRecommConfig();
				appRecomm.setColumnName(a.getColumnName());
				appRecomm.setCreateTime(a.getCreateTime());
				appRecomm.setRecommNum(0);
				appRecomm.setColumnId(a.getColumnId());
				appRecomm.setStatus(Short.valueOf("1"));
				appRecomm.setType(a.getSpecialType());
				appRecommConfigService.saveAppRecommConfig(appRecomm);
			}
			
		}
		Pagination page= appRecommConfigService.queryRecommLists(vo);
		   
		   map.addAttribute("page",page);
		return "recomm/index";
	}
	
	
	@RequestMapping(value="disabled.do")
	@ResponseBody
	public String disabledRecomm(Long columnId) {
		String result="success";
		if(columnId==-1L || columnId==0L) {
			result="该栏目无法被删除";
		}else {
		AppRecommConfigVo vo=appRecommConfigService.queryAppRecommConfig(columnId);
		vo.setStatus(Short.valueOf("0"));
		appRecommConfigService.updateAppRecomm(vo);
		result="禁用成功";
		}
		return result;
	}
	
	
	
	
	
	
	
}