package com.hww.ucenter.webservice.service;

import com.hww.app.common.dto.AppSearchDto;
import com.hww.base.util.R;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterThirdDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.vo.UCenterMemberVo;

import java.util.List;

public interface UCenterMemberService {
	
	 void setMememberSnsDisabled(Long memberId,Integer disabled);

    R login(UCenterMemberDto login,String countryCode,Long countryId);

    UCenterMemberVo getUCenterMemberById(Long memberId);

    UCenterMemberVo register(UCenterMemberDto UCenterMemberDto);

    UCenterMemberVo updateUCenterMember(UCenterMemberVo UCenterMemberVo);

    String concernUser(UCenterMember UCenterMember);

    Integer concernUserCount(MyConcernDto dto);

    Integer concernMyCount(MyConcernDto dto);

    Integer topicCountByUser(MyConcernDto dto);

//    List<SnsTopicVo> myTopics(Long memberId);

    List<UCenterMemberVo> searchMembers(AppSearchDto vo);

    UCenterMemberVo thirdLogin(UCenterThirdDto thirdDto);

    UCenterMemberVo getUCenterMemberByPhoneNumber(String phoneNumber,Long countryId);

    boolean resetPassword(Long memberId, String password);

    /**
     * 验证手机号是否已经被注册
     *
     * @param phoneNo
     * @return
     */
    boolean verifyPhoneNumberIsExist(String phoneNo,Long countryId);

    /**
     * 发送手机验证码服务
     *
     * @param nationCode
     * @param phoneNumber
     * @return
     */
    R sendVerificationCodeOfPhoneNumber(String nationCode, String phoneNumber);

    /**
     * 验证手机验证码是否正确
     *
     * @param phoneNumber
     * @param code
     * @return
     */
    boolean verificationCodeIsCorrect(String phoneNumber, String code);

    UCenterMemberVo unbundThirdPart(UCenterThirdDto thirdDto);
    
    
	UCenterMember loadByThirdPartQQUuid(String qqUuid);

	UCenterMember loadByThirdPartWeChatUuid(String weChatUuid);

	UCenterMember loadByThirdPartWeiBoUuid(String weiBoUuid);
	Long saveUser(UCenterMember member); 

}
