package com.hww.ucenter.webservice.controller;

import com.hww.app.api.AppFeignClient;
import com.hww.app.common.dto.AppSearchDto;
import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.base.util.Md5PwdEncoder;
import com.hww.base.util.R;
import com.hww.ucenter.common.dto.MememberSnsDisableDto;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.MySignDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UserMessageQueryDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.vo.UCenterMemberVo;
import com.hww.ucenter.webservice.service.ConcernService;
import com.hww.ucenter.webservice.service.MessageService;
import com.hww.ucenter.webservice.service.SignService;
import com.hww.ucenter.webservice.service.UCenterMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/ucenter")
public class UCenterController {

    private static final Logger log = LoggerFactory.getLogger("ucenter module:UcenterController");

    @Autowired
    private UCenterMemberService UCenterMemberService;

    @Autowired
    private SignService signService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConcernService concernService;
    @Autowired
    AppFeignClient appFeignClient;
    /**
     * 更新用户信息,比如更新用户的昵称，签名信息,微博号，地址
     * 第三方绑定手机号时，也可以调用该接口更新手机号
     */
    @RequestMapping(value = "updateUserInfo.do", method = { RequestMethod.POST})
    public R updateUserInfo(UCenterMemberVo vo) {
        if (vo.getMemberId() == null)
            return R.error(400, "memberId不能为空!");
        try {
        	log.debug("====updateUserInfo-vo:||"+vo.toString());
            UCenterMemberVo userVo = UCenterMemberService.getUCenterMemberById(vo.getMemberId());
            if (userVo == null) {
                return R.error(400, "用户不存在!");
            }
            if (vo.getNickName() != null) {
                userVo.setNickName(vo.getNickName());
            }
            if (vo.getSex() != null) {
                userVo.setSex(vo.getSex());
            }
            if (vo.getAvatar() != null) {
                userVo.setAvatar(vo.getAvatar());
            }
            if (vo.getAvatarId() != null) {
                userVo.setAvatarId(vo.getAvatarId());
            }
            
            //=========================================
            if (vo.getCityName() != null) {
                userVo.setCityName(vo.getCityName());
            }else {
            	 userVo.setCityName(null);
            }
            if (vo.getCityId() != null) {
            	  userVo.setCityId(vo.getCityId());
            }else {
            	 userVo.setCityId(null);
            }
            if (vo.getProvinceName() != null) {
                userVo.setProvinceName(vo.getProvinceName());
                
            }else {
            	  userVo.setProvinceName(null);
            }
            if (vo.getProvinceId() != null) {
            	  userVo.setProvinceId(vo.getProvinceId());
            }else {
            	 userVo.setProvinceId(null);
            }
            if (vo.getCountryName() != null) {
                userVo.setCountryName(vo.getCountryName());
            }else {
            	 userVo.setCountryName(null);
            }
           
            if (vo.getCountryId() != null) {
            	 userVo.setCountryId(vo.getCountryId());
            }else {
            	// userVo.setCountryId(null);
            }
          //=========================================
              
            if (vo.getSignature() != null) {
                userVo.setSignature(vo.getSignature());
            }
            if (vo.getWeChatUuid() != null) {
            	UCenterMember mm=UCenterMemberService.loadByThirdPartWeChatUuid(vo.getWeChatUuid());
            	if(mm!=null&&!vo.getMemberId().equals(mm.getMemberId())) {
            		return R.error(500, "该微信号已经被绑定");
            	}
                userVo.setWeChatUuid(vo.getWeChatUuid());
                userVo.setWeChatNo(vo.getWeChatNo());
                
            }
            if (vo.getWeiBoUuid() != null) {
            	UCenterMember mm=UCenterMemberService.loadByThirdPartWeiBoUuid(vo.getWeiBoUuid());
            	if(mm!=null&&!vo.getMemberId().equals(mm.getMemberId())) {
            		return R.error(500, "该微博号已经被绑定");
            	}
                userVo.setWeiBoUuid(vo.getWeiBoUuid());
                userVo.setWeiBoNo(vo.getWeiBoNo());
            }
            if (vo.getQqUuid() != null) {
            	UCenterMember mm=UCenterMemberService.loadByThirdPartQQUuid(vo.getQqUuid());
            	if(mm!=null&&!vo.getMemberId().equals(mm.getMemberId())) {
            		return R.error(500, "该QQ号已经被绑定");
            	}
                userVo.setQqUuid(vo.getQqUuid());
                userVo.setQqNo(vo.getQqNo());
            }
            UCenterMemberVo user = UCenterMemberService.updateUCenterMember(userVo);
            return R.ok().put("data", user);
        } catch (Exception e) {
            log.error("修改用户信息失败!{}", e);
            return R.error(500, "更新失败");
        }
    }

    /**
     * 重置密码接口
     */
    @RequestMapping(value = "/resetPassword.do", method = {RequestMethod.POST})
    public R resetPassword(String phoneNo, @RequestParam("password") String password, String code, Long contryId) {
        if (phoneNo == null)
            return R.error(400, "手机号不能为空！").put("data",0);
        if (!StringUtils.hasText(password)) {
            return R.error(400, "新的密码不能为空").put("data",0);
        }
        UCenterMemberVo member = UCenterMemberService.getUCenterMemberByPhoneNumber(phoneNo,contryId);
        if (member == null) {
            return R.error("用户不存在！").put("data",0);
        }
        boolean flag = UCenterMemberService.verificationCodeIsCorrect(phoneNo, code);
        if (!flag) {
        	return R.error("输入的验证码不匹配!").put("data",0);
        }
        //不可为原密码
        String oldPwd= member.getPassword();
        if(new Md5PwdEncoder().encodePassword(password).equals(oldPwd)) {
        	return R.error(400, "新密码不可为原密码！");
        }
        
        boolean r = UCenterMemberService.resetPassword(member.getMemberId(), password);
        return r ? R.ok("修改密码成功").put("data", 1) : R.error(400, "修改密码失败！").put("data", 0);
    }

    /**
     * 使用手机号,注册一个用户
     * 手机号 phoneNo 密码 password 不能为空
     * 手机验证码通过之后，调用该接口保存用户信息
     *
     * @param UCenterMemberVo 用户信息
     * @return data字段ucenterMemberVo对象
     */
    @RequestMapping(value = "/register.do", method = { RequestMethod.POST})
    public R register(UCenterMemberDto UCenterMemberVo, String code) {
        if (UCenterMemberVo.getSiteId() == null) {
            UCenterMemberVo.setSiteId(1);
        }
        if (!StringUtils.hasText(UCenterMemberVo.getPhoneNo())) {
            return R.error(400, "手机号不能为空");
        }
        if (!StringUtils.hasText(UCenterMemberVo.getPassword())) {
            return R.error(400, "密码不能为空!");
        }
        if (!StringUtils.hasText(code)) {
            return R.error(400, "验证码不能为空!");
        }
        if (!StringUtils.hasText(UCenterMemberVo.getNickName())) {
            UCenterMemberVo.setNickName("海客用户");
        }
        boolean flag = UCenterMemberService.verificationCodeIsCorrect(UCenterMemberVo.getPhoneNo(), code);
        if (!flag) {
            return R.error("输入的验证码不匹配!").put("data", null);
        }
        if(UCenterMemberVo.getCountryId()==null) {
        	return R.error("国家ID不能为空").put("data", null);
        }
        Long countryId=Long.valueOf(UCenterMemberVo.getCountryId());
        
        if (UCenterMemberService.verifyPhoneNumberIsExist(UCenterMemberVo.getPhoneNo(),countryId))
            return R.error(400, UCenterMemberVo.getPhoneNo() + "手机号已经被注册！");
        UCenterMemberVo.setCountryId(Integer.valueOf(""+countryId));
        UCenterMemberVo register = UCenterMemberService.register(UCenterMemberVo);
        if (register == null)
            return R.error(500, "注册失败！");
        return R.ok("注册成功!").put("data", register);
    }

    /**
     * 注册时，发送验证码接口
     *
     * @param nationCode 国家代码
     * @param phoneNo    手机号码
     * @return data字段true表示发送成功，false表示一些其他原因未发送，比如手机号已经被注册,验证码在有效期内
     */
    @RequestMapping(value = "getCode.do", method = {RequestMethod.POST})
    public R getVerificationCode(@RequestParam("nationCode") String nationCode,
                                 @RequestParam("phoneNo") String phoneNo,
                                 @RequestParam("countryId") Long countryId) {
        if (!StringUtils.hasText(phoneNo)) {
            return R.error(400, "请输入手机号!");
        }
        if (!StringUtils.hasText(nationCode)) {
            nationCode = "86";
        }
        // 根据手机号查询帐号是否存在
        UCenterMemberVo UCenterMemberVo = UCenterMemberService.getUCenterMemberByPhoneNumber(phoneNo,countryId);
        if (UCenterMemberVo != null) {
            return R.ok("该手机号已经被注册！").put("data", false);
        }
        return UCenterMemberService.sendVerificationCodeOfPhoneNumber(nationCode, phoneNo);
    }

    /**
     * 重置密码时，发送验证码接口，重置密码时不判断手机号是否已经被注册
     *
     * @param nationCode 国家代码
     * @param phoneNo    手机号码
     * @return data字段true表示发送成功，false表示一些其他原因未发送,如验证码在有效期内
     */
    @RequestMapping(value = "getCodeNoExistCheck.do", method = {RequestMethod.POST})
    public R getVerificationCodeWhenResetPassword(@RequestParam(value = "nationCode", required = false) String nationCode,
                                                  @RequestParam(value = "phoneNo", required = false) String phoneNo) {

        if (!StringUtils.hasText(phoneNo)) {
            return R.error(400, "手机号参数无效!");
        }
        if (!StringUtils.hasText(nationCode)) {
            nationCode = "86";
        }
        return UCenterMemberService.sendVerificationCodeOfPhoneNumber(nationCode, phoneNo);
    }

    /**
     * 验证输入的验证码是否正确
     *
     * @param phoneNo 手机号
     * @param code    验证码
     * @return data字段，true表示验证码正确，false表示不匹配
     */
//    @RequestMapping(value = "/verificationPhoneNumber", method = {RequestMethod.GET, RequestMethod.POST})
//    public R verificationPhoneNumber(@RequestParam("phoneNo") String phoneNo, @RequestParam("code") String code) {
//        if (!StringUtils.hasText(phoneNo) || !StringUtils.hasText(code))
//            return R.error(400, "参数缺失,请传入正确的参数!");
//        boolean flag = UCenterMemberService.verificationCodeIsCorrect(phoneNo, code);
//        return flag ? R.ok("验证码匹配正确!").put("data", true) : R.ok("输入的验证码不匹配!").put("data", false);
//    }

    /**
     * 验证手机号是否已经被注册
     *
     * @param phoneNo 手机号不能为空
     * @return data字段 true or false,true 表示已经被注册
     */
    @RequestMapping(value = "/verifyPhoneNumberIsExist.do", method = { RequestMethod.POST})
    public R verifyPhoneNumberIsExist(@RequestParam(value = "phoneNo", required = false) String phoneNo,@RequestParam(value = "countryId", required = false) Long countryId) {
        if (!StringUtils.hasText(phoneNo))
            return R.error(400, "手机号参数不能为空！");
        if (UCenterMemberService.verifyPhoneNumberIsExist(phoneNo,countryId)) {
            return R.ok().put("data", true);
        } else {
            return R.ok().put("data", false);
        }

    }


    /**
     * 查询用户
     */
    @RequestMapping(value = "userDetail.do", method = { RequestMethod.POST})
    public R userDetail(UCenterMemberVo UCenterMemberVo, Long myId) {
        if (UCenterMemberVo.getMemberId() == null) {
            return R.error(1, "用户不能为空");
        }
        // 查询用户信息
        try {
            Integer concernType = 0;

            if (myId != null) {
            	UCenterFollow f=concernService.isConcern(myId,UCenterMemberVo.getMemberId());
                concernType = f.getConcernType();
            }

            MyConcernDto dto = new MyConcernDto();
            dto.setMemberId(UCenterMemberVo.getMemberId());
            UCenterMemberVo userVo = UCenterMemberService.getUCenterMemberById(UCenterMemberVo.getMemberId());
            if (userVo == null) {
                return R.error(1, "用户不存在");
            }
            Integer messageCount =0;
            if(myId!=null&&myId.equals(UCenterMemberVo.getMemberId())) {//是自己的时候，才会架子啊消息
            	   // 消息数量
                messageCount = messageService.loadUserMessageCount(new UserMessageQueryDto().setMemberId(UCenterMemberVo.getMemberId()).setIsRead("n"));
            }
         
            // 粉丝数量
            Integer fCount = UCenterMemberService.concernMyCount(dto);
            // 关注数量
            Integer gCount = UCenterMemberService.concernUserCount(dto);
            // 新鲜事数量
            Integer tCount = UCenterMemberService.topicCountByUser(dto);

            // 签到数据
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String now = format.format(c.getTime());
            c.add(Calendar.MONTH, -1);
            String start = format.format(c.getTime());
            MySignDto dt = new MySignDto();
            dt.setCurrentDate(now);
            dt.setOneMonthDate(start);
            dt.setMemberId(UCenterMemberVo.getMemberId());
            List<UCenterSign> myUCenterSigns = signService.mySign(dt);

            if (myId != null) {
//            	UCenterFollow f2=concernService.isConcern(UCenterMemberVo.getMemberId(),myId);
//            	Integer  concernType2 = f2.getConcernType();
//            	userVo.setConcernType(concernType2);
            }
            
            // 签到数量
            Integer count = signService.cuOneSignCount(dt);

            return R.ok().put("data", userVo).put("fCount", fCount).put("gCount", gCount)
                    .put("tCount", tCount).put("signs", myUCenterSigns).put("messageCount", messageCount)
                    .put("signCount", count).put("concernType", concernType);
        } catch (Exception e) {
            log.error("查询用户详情失败!{}", e);
            return R.error(1, "用户查询失败");
        }
    }

    /**
     * ========================================================调用
     */
    
    
    
    
//    @RequestMapping(value = "searchPeople.do", method = {RequestMethod.POST})
//    public List<UCenterMemberVo> searchPeople(@RequestBody AppSearchDto search) {
//        if (search != null && StringUtils.isEmpty(search.getKeywords())) {
//            return new ArrayList<>();
//        }
//        return UCenterMemberService.searchMembers(search);
//    }

    
    @RequestMapping(value = "searchPeople.do", method = {RequestMethod.GET, RequestMethod.POST})
    public R searchPeople(AppSearchDto search) {
        try {
            if (StringUtils.isEmpty(search.getKeywords())) {
                return R.error(1, "搜索昵称不能为空");
            }

            List<UCenterMemberVo> list = UCenterMemberService.searchMembers(search);
            
            try {
    			saveSearchHis(SearchHistoryDto.newForCreate(search.getMemberId(), search.getImei(),
    					search.getKeywords(), 5));
    		} catch (Exception e) {
    		}
            
            return R.ok().put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询用户失败");
        }
    }
    @Async
	public void saveSearchHis(SearchHistoryDto historyDto) {
		appFeignClient.addSearchHistory(historyDto);
	}
    @RequestMapping(value = "setMememberSnsDisabledFeginApi.do", method = { RequestMethod.POST})
    public R setMememberSnsDisabled(@RequestBody MememberSnsDisableDto dto) {
    	UCenterMemberService.setMememberSnsDisabled(dto.getMemberId(), dto.getDisabled());
    	return R.ok();
    }
    
    @RequestMapping(value = "userInfoInFeginApi.do", method = {RequestMethod.GET, RequestMethod.POST})
    public UCenterMemberDto userInfoInFeginApi(@RequestParam(value = "memberId", required = false) Long memberId) {
        // 查询用户信息
        if (memberId == null) {
            return null;
        }
        UCenterMemberVo memberVo = UCenterMemberService.getUCenterMemberById(memberId);
        if (memberVo == null) {
            return null;
        }
        UCenterMemberDto memberDto = new UCenterMemberDto();
        BeanUtils.copyProperties(memberVo, memberDto);
        return memberDto;
    }

//    @RequestMapping(value = "searchMembers.do", method = {RequestMethod.GET, RequestMethod.POST})
//    public List<UCenterMemberVo> searchMembers(@RequestBody AppSearchDto search) {
//        if (search != null && StringUtils.isEmpty(search.getKeywords())) {
//            return new ArrayList<>();
//        }
//        return UCenterMemberService.searchMembers(search);
//    }
    
    


}
