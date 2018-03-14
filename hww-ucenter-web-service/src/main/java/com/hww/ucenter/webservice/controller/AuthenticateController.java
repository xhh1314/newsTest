package com.hww.ucenter.webservice.controller;

import com.hww.base.util.R;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterThirdDto;
import com.hww.ucenter.common.vo.UCenterMemberVo;
import com.hww.ucenter.webservice.service.UCenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ucenter/member/auth")
public class AuthenticateController {
    @Autowired
    private UCenterMemberService UCenterMemberService;

    /**
     * 使用手机号登录接口
     *
     * @return data字段，用户的基本信息
     */
//    @RequestMapping(value = "/loginByAccount.do", method = {RequestMethod.POST, RequestMethod.GET})
//    public R resetPassword(HttpServletRequest request, @RequestParam("memberName") String memberName, @RequestParam("password") String password) {
//        if (!StringUtils.hasText(memberName))
//            return R.error(400, "账号不能为空！");
//        if (!StringUtils.hasText(password)) {
//            return R.error(400, "密码不能为空");
//        }
//        UCenterMemberVo UCenterMemberVo = new UCenterMemberVo();
//        UCenterMemberVo.setMemberName(memberName);
//        UCenterMemberVo.setPassword(password);
//        UCenterMemberVo.setLastLoginIp(request.getRemoteAddr());
//        return UCenterMemberService.login(UCenterMemberVo);
//    }

    /**
     * 使用手机号登录接口
     *
     * @return data字段，用户的基本信息
     */
    @RequestMapping(value = "/loginByPhoneNo.do", method = {RequestMethod.POST})
    public R login(@RequestParam("phoneNo") String phoneNo, 
    				@RequestParam("password") String password,
    				@RequestParam(value="countryId",required=false) Long countryId,
                   HttpServletRequest request) {
        if (!StringUtils.hasText(phoneNo))
            return R.error(400, "手机号不能为空！");
        if (!StringUtils.hasText(password))
            return R.error(400, "密码不能为空！");
        UCenterMemberDto UCenterMemberVo = new UCenterMemberDto();
        UCenterMemberVo.setPhoneNo(phoneNo);
        UCenterMemberVo.setPassword(password);
        UCenterMemberVo.setLastLoginIp(request.getRemoteAddr());
        
        return UCenterMemberService.login(UCenterMemberVo,null,countryId);
    }

    /**
     * 第三方登陆接口
     *
     * @param vo 第三方登陆信息  第三方提供的用户唯一标识,thirdUserUuid(必须)  ,头像、昵称、性别等信息
     * @return data字段 存储用户用户信息
     */
    @RequestMapping(value = "loginThirdPart.do", method = { RequestMethod.POST})
    @ResponseBody
    public R thirdLogin(UCenterThirdDto thirdDto) {
        //第三方登录逻辑是前端提供一个uuid，后台查这个thirdUserUuid是否存在
        // 如果不存在，则新插入一个记录存储用户的相关信息
        // 如果存在，则直接把原来的信息返回 不作更新
        //        if (StringUtils.hasText(vo.getQqUuid())) {
        //
        //        }else if (StringUtils.hasText(vo.getWeChatUuid())){
        //
        //        }else if (StringUtils.hasText(vo.getWeiBoUuid())){
        //
        //        }else {
        //            return R.error(400, "第三方登陆失败");
        //        }
        UCenterMemberVo memberVo = UCenterMemberService.thirdLogin(thirdDto);
        // 判断是否为空
        if (memberVo != null) {
            return R.ok().put("data", memberVo).put("goRegister", false);
        } else {
            return R.ok().put("goRegister", true);
        }

    }
    @RequestMapping(value = "unbundThirdPart.do", method = { RequestMethod.POST})
    @ResponseBody
    public R unbundThirdPart(UCenterThirdDto thirdDto){
    	try {
    		UCenterMemberVo vo=UCenterMemberService.unbundThirdPart(thirdDto);
    		 return R.ok().put("data", vo);
    	}catch (Exception e) {
    		return R.error(e.getLocalizedMessage());
		}
    }
}
