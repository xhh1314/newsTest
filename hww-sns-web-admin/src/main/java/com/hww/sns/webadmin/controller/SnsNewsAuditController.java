package com.hww.sns.webadmin.controller;

import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.sns.common.vo.SnsAuditVo;
import com.hww.sns.webadmin.service.SnsAuditService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 新闻审核
 * @author hewei
 *
 */
@Controller
@RequestMapping("/sns/news/audit")
public class SnsNewsAuditController
	extends
		AbsBaseController {

	private static final Log log = LogFactory.getLog(SnsNewsAuditController.class);
	@Resource
	SnsAuditService snsAuditService;
	
	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	@ResponseBody
	public String saveTopic(HttpServletRequest request, HttpServletResponse response,ModelMap map,
			@Valid @RequestBody SnsAuditVo vo,BindingResult errors) {
		String result = "success";
		if(errors.hasErrors()) {
			result = getErrorMessage(errors);
			return result;
		}else {
			try {
				snsAuditService.save(vo);
			}catch(Exception e) {
				e.printStackTrace();
                result = "saveUCenterMember audit error!";
			}
			return result;
		}
	}
	
	/**
	 * 批量审核
	 */
	@RequestMapping(value = "batch_save.do")
	@ResponseBody
	public String batchDeleteCategory(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			@Valid @RequestBody SnsAuditVo[] vos) {
		boolean result = true;
		try {
			snsAuditService.batchSaveAudit(Arrays.asList(vos));
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}
		if(result) {
			return "success";
		}else {
			return "批量审核通过-失败";
		}
	}
	
	
	/**
	 * 通用审核详情
	 * @return
	 */
	@RequestMapping(value = "audit_detail.do")
	public String auditDetail(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			@RequestBody SnsAuditVo vo,BindingResult errors) {
		List<SnsAuditVo> auditVos = null;
		try {
			if(vo.getTopicId()!=null) {
				auditVos = snsAuditService.queryAuditDetail(vo.getTopicId());
			}
			if(auditVos==null) {
				auditVos = new LinkedList<SnsAuditVo>();
			}
			map.addAttribute("audits", auditVos);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "common/audit_detail";
	}
	
}
