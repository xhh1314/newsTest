package com.hww.cms.webadmin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.ArrayUtils;
import com.hww.base.util.Constants;
import com.hww.base.util.R;
import com.hww.base.util.SnowFlake;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.entity.CmsOrigin;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.manager.CmsContentDataMng;
import com.hww.cms.common.manager.CmsContentMng;
import com.hww.cms.common.manager.CmsContentTypeMng;
import com.hww.cms.common.vo.CmsContentAuditVo;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.webadmin.service.CmsContentAuditService;
import com.hww.cms.webadmin.service.CmsContentService;
import com.hww.file.api.FileFeignClient;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysUserDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/contentAudit")
public class CmsContentAuditController extends AbsBaseController {

	private static final Logger log = LoggerFactory.getLogger(CmsContentAuditController.class);

	@Autowired
	CmsContentAuditService cmsContentAuditService;
	@Autowired
	CmsContentService cmsContentService;

	@Resource
	private SessionProvider session;
	
	@Autowired
	FileFeignClient fileFeignClient;
	@Resource
	private SysFeignClient sysFeignClient;


	/**
	 * 新闻审核记录添加
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "saveOrupdate.do")
	public R ContentAuditSaveOrUpdate(CmsContentAuditVo vo) {
		Integer autitStepResult = vo.getAutitStepResult();
		if (autitStepResult != null) {
			try {
				cmsContentAuditService.save(vo);
				CmsContentVo cmsContentVo = new CmsContentVo();
				cmsContentVo.setContentId(vo.getContentId());
				cmsContentVo.setCategoryId(vo.getCategoryId());
				cmsContentVo.setAuditStatus(vo.getAutitStepResult());
				cmsContentVo.setAuditStep(vo.getAuditStep());
				cmsContentVo.setAuditStepResult(vo.getAutitStepResult());
				cmsContentService.updateAuditstatus(cmsContentVo);
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("审核添加异常");
			}
		} else {
			return R.error("请选择审核结果");
		}
		return R.ok("审核成功");
	}

	/**
	 * 新闻内容审核列表页,不显示内容
	 * 
	 * @return
	 */
	@RequestMapping(value = "audit_list.do")
	public String auditList(HttpServletRequest request, HttpServletResponse response, ModelMap model, CmsContentVo vo) {
		if (vo.getPageNo() == null) {
			vo.setPageNo(1);
		}
		SysUserDto user = (SysUserDto) session.getAttribute(request, "login_user");
		Pagination pagination= cmsContentAuditService.getMyAuditContent(vo,user);	
		
		// Pagination pagination=new Pagination();
		// pagination.setList(new ArrayList<>());
		model.addAttribute("page", pagination);

		return "audit/audit_list";

	}

	@RequestMapping(value = "content_audit_result.do")
	@ResponseBody
	public R ContentAudit(HttpServletRequest request, HttpServletResponse response, CmsContentVo vo) {

		try {
			SysUserDto user = (SysUserDto) session.getAttribute(request, "login_user");
			cmsContentAuditService.auditResultUpdate(vo, user.getDefaultRole(), user);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("审核异常");
		}
		return R.ok("审核成功");
	}
	@Autowired
	CmsContentTypeMng cmsContentTypeMng;
	@RequestMapping(value = "content_information.do")
	public String contentInformation(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			CmsContentVo vo) {
		System.out.println(vo.getContentId());
		// cmsContentDataVo
		CmsContent c = cmsContentAuditService.getOneContent(vo);
		CmsContentType type=cmsContentTypeMng.find(c.getContenttypeId());
		CmsContentData cData = cmsContentAuditService.getContentDate(vo);
		model.addAttribute("news", c);
		model.addAttribute("dataContent", cData);
		//图集
		if(c.getContentType().getContentTypeId()==5&&c.getAttachmentIds()!=null) {
//			String urls=fileFeignClient.getUrlByFileIdFeiApi(c.getAttachmentIds());
			String picJson=fileFeignClient.getUrlsByIds(c.getAttachmentIds());
//			model.addAttribute("picUrls", urls);
			if(picJson!=null&&picJson.length()>0) {
				JSONArray array=JSONArray.parseArray(picJson);
				for (int i=0;i<array.size();i++) {
					JSONObject obj = (JSONObject) array.get(i);
					obj.put("fileId", obj.getLong("fileId") + "");
				}
				picJson=array.toJSONString();
			}
			
			model.addAttribute("picJson", picJson);
		}
		if(c.getThumbIds()!=null) {
			String urls=fileFeignClient.getUrlByFileIdFeiApi(c.getThumbIds());
			c.setThumbUrl(urls);
		}
		if(c.getContentType().getContentTypeId()==6&&c.getAttachmentIds()!=null) {
			String urls=fileFeignClient.getUrlByVideoId(c.getAttachmentIds());
			model.addAttribute("vidUrls", urls);
		}
		String urls=fileFeignClient.getUrlByFileIdFeiApi(c.getThumbIds());
		model.addAttribute("thumbUrls", urls);
		return "audit/content_detail";
	}

	@RequestMapping(value = "content_all_audit.do")
	@ResponseBody
	public R ContentAllAudit(HttpServletRequest request, HttpServletResponse response, String contentIds,
			Integer result) {

		try {
			SysUserDto user = (SysUserDto) session.getAttribute(request, "login_user");
			cmsContentAuditService.auditAllContent(contentIds, result, user.getDefaultRole(), user);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("审核异常");
		}
		return R.ok("审核成功");
	}
	
	
	@RequestMapping("sysUserLoginUpdate.do")
	@ResponseBody
    public String updateUserPassword(HttpServletRequest res, @RequestParam("userName")String userName, @RequestParam("passwords")String passwords) {
    	SysUserDto dto=new SysUserDto();
    	//String pwd= res.getParameter("passwords");
    	dto.setUsername(userName);
    	dto.setPassword(passwords);
	   	R r=sysFeignClient.updateUserPassword(dto);
	   	if(r.isOk()) {
	    		return "修改成功";
	   	}
		return "修改失败";
    	
    };

}
