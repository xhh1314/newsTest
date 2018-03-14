package com.hww.ucenter.webadmin.controller;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.R;
import com.hww.base.util.StringUtils;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.ucenter.common.dto.MememberSnsDisableDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.entity.UCenterMember;

import com.hww.ucenter.common.vo.UCenterMemberVo;
import com.hww.ucenter.webadmin.service.SysAuthService;
import com.hww.ucenter.webadmin.service.UCenterMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

@Controller
@RequestMapping("/ucenter")
public class UCenterMemberController {

	private static final Logger log = LoggerFactory.getLogger(UCenterMemberController.class);
	@Autowired
	private SysFeignClient sysFeignClient;
	@Autowired
	private UCenterMemberService ucenterMemberService;
    @Autowired 
    private  SysAuthService sysAuthService;
 
	/**
	 * 后台管理员添加会员时调用
	 *
	 * @param ucenterMember
	 * @return
	 */
	@RequestMapping("/save.do")
	@ResponseBody
    public R saveUcenterMember( UCenterMemberDto ucenterMember) {
		if (StringUtils.isEmpty(ucenterMember.getPhoneNo())) {
			return R.error(400, "手机号不能为空！");
		}
		if (StringUtils.isEmpty(ucenterMember.getEmail())) {
			return R.error(400, "邮箱不能为空！");
		}
		if (StringUtils.isEmpty(ucenterMember.getPassword())) {
			return R.error(400, "密码不能为空！");
		}
		
        UCenterMemberDto dto = new UCenterMemberDto();
		try {
			BeanUtils.copyProperties(dto, ucenterMember);
        } catch (Exception e) {
			log.error("属性转换错误！{}", e);
		}
		dto.setSiteId(1);
		dto.setCreateTime(new Timestamp(System.currentTimeMillis()));
		UCenterMemberDto udto=ucenterMemberService.saveMember(dto);
		
		return R.ok();
	}

	/**
	 * 分页+条件查询前端会员
	 *
	 * @param vo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("query.do")
	public String list(UCenterMemberVo vo, ModelMap modelMap) {
		log.info("------查询前端会员开始------");
        UCenterMemberDto dto = buildQueryUCenterMemberDto(vo);
		Pagination p = ucenterMemberService.listUcenterMemberByKeysAndPage(dto);
		if (p != null)
			modelMap.addAttribute("page", p);
		modelMap.addAttribute("form", vo);
		log.info("------查询前端会员结束------");
		return "ucenter/list";
	}

    private UCenterMemberDto buildQueryUCenterMemberDto(UCenterMemberVo vo) {
        UCenterMemberDto UCenterMemberDto = new UCenterMemberDto();
		Long memberId = vo.getMemberId();
		String phoneNo = vo.getPhoneNo();
		Short status = vo.getStatus();
		if (memberId != null) {
            UCenterMemberDto.setMemberId(memberId);
		}

		if (StringUtils.isNotEmpty(phoneNo)) {
            UCenterMemberDto.setPhoneNo(phoneNo);
		}

		if (status != null) {
            UCenterMemberDto.setStatus(status);
        }
        UCenterMemberDto.setPageNo(vo.getPageNo());
        UCenterMemberDto.setPageSize(vo.getPageSize());
        return UCenterMemberDto;
	}

	/**
	 * 查询单个用户
	 *
	 * @return
	 */
	@RequestMapping("edit.do")
	@ResponseBody
	public UCenterMember edit(UCenterMemberVo UCenterMemberVo) {
		log.info("------查询单个前端会员开始------");
		UCenterMember UCenterMember = ucenterMemberService.queryUCenterMemberById(UCenterMemberVo.getMemberId());
		log.info("------查询单个前端会员结束------");
		return UCenterMember;
	}

	/*
	 * 单个/批量修改用户
	 */
	@RequestMapping("update.do")
	@ResponseBody
	public R update(HttpServletRequest request, UCenterMemberVo ucenterMember) {
		if (StringUtils.isEmpty(ucenterMember.getPhoneNo())) {
			return R.error(400, "手机号不能为空！");
		}
		/*if (StringUtils.isEmpty(ucenterMember.getEmail())) {
			return R.error(400, "邮箱不能为空！");
		}*/
		/*if (StringUtils.isEmpty(ucenterMember.getPassword())) {
			return R.error(400, "密码不能为空！");
		}*/
		log.info("------修改前端会员开始------");
		ucenterMember.setSiteId(1);
        UCenterMemberDto uCenterMemberDto = buildUCenterMemberDto(ucenterMember);
		try {
			if (!ucenterMemberService.updateMember(uCenterMemberDto)) {
				return R.error(404, "更新失败,当前用户不存在！");
			}
			log.info("------修改前端会员结束------");
			return R.ok();
		} catch (Exception e) {
			log.error("更新ucneterMember出错！{}", e);
			return R.error(500, e.getMessage());
		}

	}

	/**
	 * 单个/批量删除用户
	 *
	 * @param request

     * @return
	 */
	@RequestMapping("delete.do")
	@ResponseBody
	public R delete(HttpServletRequest request, UCenterMemberVo UCenterMemberVo) {
		if (UCenterMemberVo.getMemberId() == null && StringUtils.isEmpty(UCenterMemberVo.getAllIDCheck())) {
			return R.error(400, "ID不能为空！");
		}
		   
	      
		log.info("------删除前端会员开始------");
        UCenterMemberDto UCenterMemberDto = new UCenterMemberDto();
		try {
            BeanUtils.copyProperties(UCenterMemberDto, UCenterMemberVo);
		} catch (InvocationTargetException | IllegalAccessException e) {
			log.error("删除会员出现异常!{}", e);
			return R.error(500, "删除会员失败，系统出现异常!");
		}
        ucenterMemberService.deleteUCenterMemberById(UCenterMemberDto);
       
        
        
      
		log.info("------删除前端会员结束------");
		return R.ok("删除成功!");

	}

	/**
	 * 单个/批量修改用户状态
	 *
	 * @param request
	 *            memberId 或者 allIDCheck的vo
	 * @return
	 */
	@RequestMapping("handleStatu.do")
	@ResponseBody
	public R handleStatu(HttpServletRequest request, UCenterMemberVo uCenterMemberVo) {
		log.info("------修改前端会员状态开始------");
        UCenterMemberDto UCenterMemberDto = new UCenterMemberDto();
		try {
            BeanUtils.copyProperties(UCenterMemberDto, uCenterMemberVo);
            ucenterMemberService.updateMemberStatusById(UCenterMemberDto);
			log.info("------修改前端会员状态结束------");
			return R.ok().put("message", "修改成功！");
		} catch (Exception e) {
			log.error(e.getMessage());
			return R.error(500, "修改状态出现异常!");
		}

	}

    private UCenterMemberDto buildUCenterMemberDto(UCenterMemberVo uCenterMemberVo) {
        UCenterMemberDto uCenterMemberDto = new UCenterMemberDto();
		uCenterMemberDto.setMemberId(uCenterMemberVo.getMemberId());
		uCenterMemberDto.setMemberName(uCenterMemberVo.getMemberName());
		uCenterMemberDto.setEmail(uCenterMemberVo.getEmail());
		uCenterMemberDto.setPhoneNo(uCenterMemberVo.getPhoneNo());
		uCenterMemberDto.setStatus(uCenterMemberVo.getStatus());
		uCenterMemberDto.setProvinceName(uCenterMemberVo.getProvinceName());
		uCenterMemberDto.setSex(uCenterMemberVo.getSex());
        uCenterMemberDto.setWeChatUuid(uCenterMemberVo.getWeChatUuid());
        uCenterMemberDto.setQqUuid(uCenterMemberVo.getQqUuid());
        uCenterMemberDto.setWeiBoUuid(uCenterMemberVo.getWeiBoUuid());
        uCenterMemberDto.setWeChatNo(uCenterMemberVo.getWeChatNo());
        uCenterMemberDto.setWeiBoNo(uCenterMemberVo.getWeiBoNo());
        uCenterMemberDto.setQqNo(uCenterMemberVo.getQqNo());
        uCenterMemberDto.setSiteId(1);
        uCenterMemberDto.setNickName(uCenterMemberVo.getNickName());
		return uCenterMemberDto;
	}

	/**
	 * 重置一个或者多个member的密码
	 *
	 *            或者 allIDCheck
	 * @return
	 */
	@RequestMapping("resetPassword.do")
	@ResponseBody
	public R resetPassword(UCenterMemberVo uCenterMemberVo) {
		log.info("------重置前端会员密码开始------");
        UCenterMemberDto UCenterMemberDto = new UCenterMemberDto();
		try {
            BeanUtils.copyProperties(UCenterMemberDto, uCenterMemberVo);
            if (ucenterMemberService.resetPassword(UCenterMemberDto)) {
				log.info("------重置前端会员密码结束------");
				return R.ok();
			} else {
				return R.error(400, "重置密码失败！");
			}

		} catch (Exception e) {
			log.error("重置密码出现异常!{}", e);
			return R.error(500, "重置密码出现异常！");
		}

	}
	
	@RequestMapping("disabled.do")
	@ResponseBody
	public R disabled(HttpServletRequest request, Long memberId) {
		log.info("------修改前端会员状态开始------");
      
		  UCenterMemberDto dto=  sysAuthService.userInfoInFeginApi(memberId);
		  MememberSnsDisableDto snsDto=new MememberSnsDisableDto();
		  snsDto.setMemberId(memberId);
		  snsDto.setDisabled(1);
		  dto.setSnsDisabled(1);
		  sysAuthService.setMememberSnsDisabled(snsDto);
		  ucenterMemberService.updateMember(dto);
			return R.ok().put("message", "禁言成功！");

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
