package com.hww.sns.webadmin.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.sns.common.Constants;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.vo.SnsAuditVo;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.webadmin.service.SnsPostService;
import com.hww.ucenter.api.UcenterFeignClient;
import com.hww.ucenter.common.dto.MememberSnsDisableDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanMapper;
import com.hww.framework.web.mvc.controller.AbsBaseController;

/**
 * 评论或回复
 * @author hewei
 *
 */
@Controller
@RequestMapping("/sns/post")
public class SnsPostController
	extends
		AbsBaseController {

	@Resource
	SnsPostService snsPostService;
	/*@Resource
	UCenterMemberService ucenterMemberService;*/
	@Autowired private UcenterFeignClient ucenterFeignClient;
	/**
	 * 爆料评论查询
	 * @return
	 */
	@RequestMapping(value = "newsbroke_comment_list.do")
	public String listNewsbrokeComment(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsPostVo vo) {
		int pageSize = 10;
		//设置评论类型
		vo.setTopicType(Constants.topicType.newsbrokeType);
		
		Pagination pagination = snsPostService.listComment(vo, vo.getPageNo(), pageSize);
		map.addAttribute("page", pagination);
		//返回查询条件
		map.addAttribute("searchvo",vo);
		return "post/newsbroke_comment_list";
	}
	
	
	
	/**
	 * 爆料回复查询
	 * @return
	 */
	@RequestMapping(value = "newsbroke_reply_list.do")
	public String listNewbrokeReply(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsPostVo vo) {
		int pageSize = 10;
		//设置评论类型
		vo.setTopicType(Constants.topicType.newsbrokeType);
		vo.setParentId(-1L);
		Pagination pagination = snsPostService.listComment(vo, vo.getPageNo(), pageSize);
		map.addAttribute("page", pagination);
		//返回查询条件
		map.addAttribute("searchvo",vo);
		return "post/newsbroke_comment_reply_list";
	}
	
	
	
	
	/**
	 * 新鲜事评论查询
	 * @return
	 */
	@RequestMapping(value = "freshnews_comment_list.do")
	public String listFreshnewsComment(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsPostVo vo) {
		int pageSize = 10;
		vo.setTopicType(Constants.topicType.newsfreshType);
		
		
		Pagination pagination = snsPostService.listComment(vo, vo.getPageNo(), pageSize);
		map.addAttribute("page", pagination);
		//返回查询条件
		map.addAttribute("searchvo",vo);
		return "post/freshnews_comment_list";
	}
	
	/**
	 * 新鲜事回复查询
	 * @return
	 */
	@RequestMapping(value = "freshnews_comment_reply_list.do")
	public String listFreshNewsReply(HttpServletRequest request, HttpServletResponse response,ModelMap map, SnsPostVo vo) {
		int pageSize = 10;
		vo.setTopicType(Constants.topicType.newsfreshType);
		vo.setParentId(-1L);
		Pagination pagination = snsPostService.listComment(vo, vo.getPageNo(), pageSize);
		map.addAttribute("page", pagination);
		//返回查询条件
		map.addAttribute("searchvo",vo);
		return "post/freshnews_comment_reply_list";
	}
	
	/**
	 * 新鲜事|爆料评论审核列表
	 * @return
	 */
	@RequestMapping(value = "freshnews_post_audit_list.do")
	public String listFreshnewsPostAudit(HttpServletRequest request, HttpServletResponse response,ModelMap map, 
			SnsPostVo vo) {
		int pageSize = 10;
//		vo.setTopicType(Constants.topicType.newsfreshType);
//		vo.setTopicType(Constants.topicType.newsbrokeType);
		/*if(vo.getMemberName()!=null && vo.getMemberName()!="") {
			UCenterMemberDto dto=ucenterMemberService.findMemberByName(vo.getMemberName());
			vo.setMemberId(dto.getMemberId());
		}*/
		Pagination pagination = snsPostService.listComment(vo, vo.getPageNo(), pageSize);
		
		List<SnsPostVo> vos=(List<SnsPostVo>) pagination.getList();
		for(SnsPostVo v:vos) {	
			if(v.getMemberId()!=null) {
				UCenterMemberDto dto=ucenterFeignClient.userInfoInFeginApi(v.getMemberId());
				if(dto!=null) {
				if(dto.getNickName()!=null) {
					v.setMemberName(dto.getNickName());
					}
				v.setDisabled(dto.getSnsDisabled());
				}
				}
			if((v.getAuditFlow()==null||v.getAuditFlow()==0)&& v.getAuditStatus()==2) {
				v.setShowStatus(0);
			}
			if(v.getAuditFlow()==1 && v.getAuditStatus()==2) {
				v.setShowStatus(1);
			}
		}
		pagination.setList(vos);
		map.addAttribute("page", pagination);
		//返回查询条件
		map.addAttribute("searchvo",vo);
		return "audit/post_freshnews_audit_list";
	}
	
	
	/*@RequestMapping(value = "tramCms.do", method = RequestMethod.POST)
	@ResponseBody
	public String tramCms(HttpServletRequest request, HttpServletResponse response,ModelMap map,
			@RequestBody SnsPostVo[] vos,BindingResult errors) {
		   List<SnsPost> snsPostVos = snsPostService.findByIDs(Arrays.asList(vos));
			List<SnsPostVo> SnsPostVolist=BeanMapper.mapList(snsPostVos, SnsPostVo.class);
			return "";
	}*/
	
	
	
	@RequestMapping("disabledUser.do")
	@ResponseBody
	public String disabled(Long memberId,Integer disabled){
		MememberSnsDisableDto dto=new MememberSnsDisableDto();
		dto.setDisabled(disabled);
		dto.setMemberId(memberId);
		ucenterFeignClient.setMememberSnsDisabled(dto);
	   return "success";
	}	
}
