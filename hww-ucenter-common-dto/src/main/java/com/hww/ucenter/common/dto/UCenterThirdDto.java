package com.hww.ucenter.common.dto;

import com.hww.base.common.dto.AbsBaseDto;

import java.sql.Timestamp;

/**
 * 会员DTO
 */
public class UCenterThirdDto extends AbsBaseDto<Long> {

    private Long memberId;
    private String memberName;// 登录账号
 
    private String weChatNo;
    private String qqNo;
    private String weiBoNo;
    private String weChatUuid;//三方登陆
    private String qqUuid;
    private String weiBoUuid;
    private String password;
    private String phoneNo;// 手机号码
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getWeChatNo() {
		return weChatNo;
	}
	public void setWeChatNo(String weChatNo) {
		this.weChatNo = weChatNo;
	}
	public String getQqNo() {
		return qqNo;
	}
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	public String getWeiBoNo() {
		return weiBoNo;
	}
	public void setWeiBoNo(String weiBoNo) {
		this.weiBoNo = weiBoNo;
	}
	public String getWeChatUuid() {
		return weChatUuid;
	}
	public void setWeChatUuid(String weChatUuid) {
		this.weChatUuid = weChatUuid;
	}
	public String getQqUuid() {
		return qqUuid;
	}
	public void setQqUuid(String qqUuid) {
		this.qqUuid = qqUuid;
	}
	public String getWeiBoUuid() {
		return weiBoUuid;
	}
	public void setWeiBoUuid(String weiBoUuid) {
		this.weiBoUuid = weiBoUuid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
   

  
}
