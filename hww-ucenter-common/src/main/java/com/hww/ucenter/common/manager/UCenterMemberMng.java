package com.hww.ucenter.common.manager;

import com.hww.app.common.dto.AppSearchDto;
import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.page.Pagination;
import com.hww.ucenter.common.dao.MemberDao;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterThirdDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.vo.UCenterMemberVo;

import java.util.List;

public interface UCenterMemberMng extends IBaseEntityMng<Long, UCenterMember, MemberDao> {

	
    UCenterMemberDto loadByMemberName(String memberName);

    UCenterMember getUCenterMemberById(UCenterMember member);
    
    List<UCenterMember> loadUCenterMemberByIds(List<Long> memberIds);

    boolean updateUserLocation(UCenterSign UCenterSign);

    String concernUser(UCenterMember UCenterMember);

    Integer concernUserCount(MyConcernDto dto);

    Integer concernMyCount(MyConcernDto dto);

    List<UCenterMemberVo> searchMembers(AppSearchDto search);

    UCenterMemberDto getUCenterMemberByPhoneNumber(String phoneNo,Long countryId);

    void resetPassword(UCenterMemberDto UCenterMemberDto);

    UCenterMemberDto thirdLogin(UCenterThirdDto thirdDto);

    List<UCenterMemberDto> listUCenterMemberByIds(Long[] ids);


    /**
     * 保存一个对象
     *
     * @param UCenterMemberDto
     * @return
     */
    UCenterMemberDto saveUCenterMember(UCenterMemberDto UCenterMemberDto);

    /**
     * 根据memberId查询一个对象
     *
     * @param memberId
     * @return
     */
    UCenterMemberDto getUCenterMemberById(Long memberId);

    /**
     * 更新一个member对象
     *
     * @param ucenterMember
     * @return
     */
    UCenterMemberVo updateUCenterMember(UCenterMemberVo ucenterMember);

    void updateUCenterMember(UCenterMemberDto UCenterMemberVo);

    UCenterMemberVo refreshLoginRecord(UCenterMemberDto uCenterMemberDto);

    /**
     * 通过id 删除一个member对象
     *
     * @param memberDto
     */
    void deleteUCenterMemberById(Long memberId);

    /**
     * 根据sql语句分页查询member
     *
     * @param finder
     * @param pageNo
     * @param pageSize
     * @return
     */
    Pagination listUCenterMemberBySqlAndPage(UCenterMemberDto UCenterMemberDto);

	void unbundThirdPart(UCenterThirdDto thirdDto);

	void setMememberSnsDisabled(Long memberId, Integer disabled);

	UCenterMember loadByThirdPartQQUuid(String qqUuid);

	UCenterMember loadByThirdPartWeChatUuid(String weChatUuid);

	UCenterMember loadByThirdPartWeiBoUuid(String weiBoUuid);

    
    
    
}
