package com.hww.sns.webadmin.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hww.sns.common.dto.SnsTopicToCmsDto;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.ucenter.api.UcenterFeignClient;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.R;
import com.hww.cms.api.CmsFeignClient;
import com.hww.framework.web.mvc.controller.AbsBaseController;
import com.hww.framework.web.session.SessionProvider;
@Controller
@RequestMapping("/sns/topic/tocontent")
public class SnsTopicToContentController extends AbsBaseController {

	@Resource
	SnsTopicMng snsTopicMng;
	@Autowired
	CmsFeignClient cmsFeignClient;
	@Resource
	private SessionProvider session;
	@Autowired
	UcenterFeignClient ucenterFeignClient;
	
	@RequestMapping(value = "toCmsContent.do", method = RequestMethod.POST)
	@ResponseBody
	public R toCmsContent(HttpServletRequest request,@RequestParam("title")String title, @RequestParam("topicId")Long topicId) {
		if(!StringUtils.hasText(title)) {
			return R.error("请输入标题");
		}
		SnsTopic topic=snsTopicMng.find(topicId);
		if(topic==null) {
			return R.error("帖子已经被删除");
		}
		if(topic.getStatus()==null||topic.getStatus().intValue()!=1) {
			return R.error("帖子已经被删除，或审核未通过");
		}
		if(topic.getShowStatus()==null||topic.getShowStatus().intValue()!=1) {
			return R.error("帖子已经被删除，或审核未通过");
		}
		
		SnsTopicToCmsDto tocmsdto=new SnsTopicToCmsDto();
		try {
			BeanUtils.copyProperties(tocmsdto, topic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SysUserDto user = (SysUserDto) session.getAttribute(request, "login_user");
		tocmsdto.setOperatorUser(user.getUserId());
		tocmsdto.setOperatorUserName(user.getUsername());
		tocmsdto.setTopicId(topicId);
		tocmsdto.setTitle(title);
		UCenterMemberDto author=ucenterFeignClient.userInfoInFeginApi(topic.getMemberId());
		tocmsdto.setAuthorName(author==null?"":author.getNickName());
		
		R res=cmsFeignClient.toCmsContent(tocmsdto);
		
		return res;
	}
	
	
}
