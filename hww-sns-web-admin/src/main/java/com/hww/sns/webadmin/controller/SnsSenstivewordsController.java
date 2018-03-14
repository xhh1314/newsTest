package com.hww.sns.webadmin.controller;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.sns.common.vo.SnsSenstiveVo;
import com.hww.sns.webadmin.service.SnsSenstiveService;
import com.alibaba.druid.util.StringUtils;
import com.hww.base.common.page.Pagination;
import com.hww.framework.web.mvc.controller.AbsBaseController;

/**
 * 敏感词/过滤词管理
 *
 */
@Controller
@RequestMapping("/sns/senstive")
public class SnsSenstivewordsController
	extends
		AbsBaseController {

	private static final Log log = LogFactory.getLog(SnsSenstivewordsController.class);
	
	@Resource
	SnsSenstiveService snsSenstiveService;
	
	/**
	 * 保存或更新
	 * @param request
	 * @param response
	 * @param map
	 * @param vo.word：内容  vo.wordType：类型（0敏感词，1过滤词）
	 * @return
	 */
	@RequestMapping("save.do")
	@ResponseBody
	public String savesenstiveword(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsSenstiveVo vo) {
		String result = "success";
		if(StringUtils.isEmpty(vo.getWord())) {
			result = "(敏感词/过滤词)不能为空!";
		}else {
			Integer wordCount = snsSenstiveService.getWordCount(vo);
			if(0 == wordCount.intValue()) {//查询该词汇是否已经存在，不存在插入新词汇
				snsSenstiveService.saveSenstive(vo);
			}else {
				result = "该词汇已存在";
			}	
		}
		return result;
	}
	/**
	 * 敏感词列表
	 * @return
	 */
	@RequestMapping("senstiveword_list.do")
	public String listSenstivewords(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			SnsSenstiveVo vo) {
		int pageNo = 1;
		int pageSize = 10;
		if(vo.getPageNo()!=null) {
			pageNo = vo.getPageNo();
		}
		Pagination pagination = snsSenstiveService.list(vo, pageNo, pageSize);
		map.addAttribute("page", pagination);
		//返回搜素条件
		map.addAttribute("searchVo", vo);
		return "sensitiveword/sensitiveword_list";
	}
	/**
	 * 敏感词编辑
	 * @return
	 */
	@RequestMapping("toeditSenstiveword.do")
	public String toEditSenstiveword(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
		String senstiveId = request.getParameter("senstiveId");
		SnsSenstiveVo vo=null;
		if(senstiveId!=null) {
			try {
				vo = snsSenstiveService.findById(Long.parseLong(senstiveId));
				if(vo!=null) {
					map.addAttribute("editVo",vo);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(vo.getWordType()==0) {
			return "sensitiveword/sensitiveword_add";
			
		}else if(vo.getWordType()==1){
			return "sensitiveword/filterword_add";
		}
		return "sensitiveword/sensitiveword_add";
	}
	
	@RequestMapping("delete.do")
	@ResponseBody
	public String deleteSenstiveword(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			SnsSenstiveVo vo) {
		String result = "success";
		try {
			snsSenstiveService.deleteSenstiveword(vo);
		}catch(Exception e){
			e.printStackTrace();
			result = "删除失败!";
		}
		return result;
	}
	
	@RequestMapping("batchdelete.do")
	@ResponseBody
	public String batchDeleteSenstiveword(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			@RequestBody SnsSenstiveVo[] vos) {
		String result = "success";
		try {
			snsSenstiveService.batchDeleteSenstiveword(Arrays.asList(vos));
		}catch(Exception e){
			e.printStackTrace();
			result = "批量删除失败!";
		}
		return result;
	}

	/**
	 * 过滤词列表
	 * @return
	 */
	@RequestMapping("filterword_list.do")
	public String listfilterword(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			SnsSenstiveVo vo) {
		int pageNo = 1;
		int pageSize = 10;
		if(vo.getPageNo()!=null) {
			pageNo = vo.getPageNo();
		}
		Pagination pagination = snsSenstiveService.list(vo, pageNo, pageSize);
		map.addAttribute("page", pagination);
		//返回搜素条件
		map.addAttribute("searchVo", vo);
		return "sensitiveword/filterword_list";
	}
	
	/**
	 * 过滤词编辑
	 * @return
	 */
	@RequestMapping("toeditFilterword.do")
	public String toEditFilterword(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
		String senstiveId = request.getParameter("senstiveId");
		if(senstiveId!=null) {
			try {
				SnsSenstiveVo vo = snsSenstiveService.findById(Long.parseLong(senstiveId));
				if(vo!=null) {
					map.addAttribute("editVo",vo);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "sensitiveword/filterword_add";
	}
	
}
