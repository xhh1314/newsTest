package com.hww.cms.webadmin.controller;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.R;
import com.hww.cms.common.entity.CmsContentAuditFlowSteps;
import com.hww.cms.common.vo.CmsContentAuditFlowStepsVo;
import com.hww.cms.webadmin.service.CmsContentAuditFlowService;
import com.hww.cms.webadmin.service.CmsContentAuditFlowStepsService;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysRoleDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/contentAuditFlowSteps")
public class CmsContentAuditFlowStepsController extends AbsBaseController {

	private static final Logger log = LoggerFactory.getLogger(CmsContentAuditFlowStepsController.class);
	@Autowired
	SysFeignClient sysFeignClient;
	@Autowired
	CmsContentAuditFlowService cmsContentAuditFlowService;
	@Autowired
	CmsContentAuditFlowStepsService cmsContentAuditFlowStepsService;

	@RequestMapping(value = "list.do")
	public String ContentAuditFlowStepsList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			Long flowId) {

		List<CmsContentAuditFlowSteps> list2 = cmsContentAuditFlowStepsService.getList(flowId);
		Pagination p2 = new Pagination(1, list2.size(), list2.size(), list2);

		model.addAttribute("page", p2);
		model.addAttribute("flowId", flowId);

		return "audit/audit_flow_steps";
	}

	/**
	 * 新闻审核流程步骤添加
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "save.do")
	@ResponseBody
	public R ContentAuditStepsSave(CmsContentAuditFlowStepsVo vo) {
		if (vo.getFlowId() == null) {
			return R.error("审核流程Id不可为空");
		}
		try {
			cmsContentAuditFlowStepsService.save(vo);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("保存异常");
		}
		return R.ok("保存成功");
	}

	/**
	 * 审核流程步骤删除
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "delete.do")
	@ResponseBody
	public R updateStatus(CmsContentAuditFlowStepsVo vo) {
		Long flow = vo.getAuditFlowStepsId();
		try {
			cmsContentAuditFlowStepsService.delete(flow);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("删除异常");
		}
		return R.ok("删除成功");
	}
	

	
	@RequestMapping("all_role_list.do") 
	@ResponseBody
	public List<SysRoleDto> getAllRoleList(){
		return  sysFeignClient.allList();
	}

}
