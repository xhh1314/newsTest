package com.hww.ucenter.webadmin.service;

import com.hww.base.common.page.Pagination;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.entity.UCenterMember;

public interface UCenterMemberService {

    UCenterMemberDto saveMember(UCenterMemberDto dto);

	/**
	 * 更新一个member
	 *
	 * @param dto
	 * @return
	 */
    boolean updateMember(UCenterMemberDto dto);

    void updateMemberStatusById(UCenterMemberDto dto);

//    UCenterMemberDto getUcenterMemberByPhoneNumber(String phoneNo);

    Pagination listUcenterMemberByKeysAndPage(UCenterMemberDto UCenterMemberDto);

	UCenterMember queryUCenterMemberById(Long memberId);

	/**
	 * 删除一个或多个member对象
	 *
     * @param UCenterMemberDto
	 * @return
	 */
    boolean deleteUCenterMemberById(UCenterMemberDto UCenterMemberDto);

	/**
	 * 重置一个member或多个member的密码
	 *
     * @param UCenterMemberDto
	 * @return
	 */
    boolean resetPassword(UCenterMemberDto UCenterMemberDto);
}
