package com.hww.sns.webadmin.controller;

import com.hww.base.util.BeanMapper;
import com.hww.base.util.R;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.entity.ESContent;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;
import com.hww.sns.common.util.EsContentCovertUtil;
import com.hww.sns.common.vo.SnsAuditVo;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.sns.webadmin.service.SnsAuditService;
import com.hww.sns.webadmin.service.SnsTopicEsSyncFailService;
import com.hww.sns.webadmin.service.SnsTopicService;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysUserDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 审核(爆料审核/新鲜事审核)
 * @author hewei
 *
 */
@Controller
@RequestMapping("/sns/audit")
public class SnsAuditController
	extends
		AbsBaseController {

	private static final Log log = LogFactory.getLog(SnsAuditController.class);
	@Resource
	SnsAuditService snsAuditService;
	@Autowired
	ElsFeignClient elsFeignClient;
	@Autowired
	SnsTopicEsSyncFailService snsTopicEsSyncFailService;
	@Autowired
	SnsTopicService snsTopicService;
	@Autowired
	private SysFeignClient sysFeignClient;
	
	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	@ResponseBody
	public String saveTopic(HttpServletRequest request, HttpServletResponse response,ModelMap map,
			@RequestBody SnsAuditVo vo,BindingResult errors) {
		String result = "success";
		if(errors.hasErrors()) {
			result = getErrorMessage(errors);
			return result;
		}else {
			try {
				snsAuditService.save(vo);
			//snsTopicEsSyncFailService.doAync();
				
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
			@RequestBody SnsAuditVo[] vos) {
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
	 * 爆料审核详情
	 * @return
	 */
	@RequestMapping(value = "newsbroke_audit_detail.do")
	public String newbrokeAuditDetail(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			@RequestBody SnsTopicVo vo,BindingResult errors) {
		
		List<SnsAuditVo> auditVos = null;
		if(errors.hasErrors()) {
            String result = getErrorMessage(errors);
            log.error(result);
		}else {
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
		}
		
		
		return "socialdata/newsbroke_audit_detail";
	}
	
	/**
	 * 新鲜事审核详情
	 * @return
	 */
	@RequestMapping(value = "newsfresh_audit_detail.do")
	public String newsfreshAuditDetail(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			@RequestBody SnsTopicVo vo,BindingResult errors) {
		
		List<SnsAuditVo> auditVos = null;
		if(errors.hasErrors()) {
			 String result = getErrorMessage(errors);
	         log.error(result);
		}else {
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
		}
		
		return "audit/audit_detail";
	}
	
	/**
	 * 评论/回复-通用审核详情
	 * @return
	 */
	@RequestMapping(value = "audit_detail.do")
	public String auditDetail(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			@RequestBody SnsTopicVo vo,BindingResult errors) {
		List<SnsAuditVo> auditVos = null;
		
		if(errors.hasErrors()) {
			 String result = getErrorMessage(errors);
	         log.error(result);
		}else {
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
		}
		
		return "common/audit_detail";
	}
	
	
	@RequestMapping(value = "cacheAdd.do", method = RequestMethod.POST)
	public void cacheAdd() {
	
		List<SnsTopic> topic= snsTopicService.alllist();
		List<SnsTopicVo> topicVo=	BeanMapper.mapList(topic, SnsTopicVo.class);
		for(SnsTopicVo vo:topicVo) {
			//snsAuditService.save(vo);
		}
		
		
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


