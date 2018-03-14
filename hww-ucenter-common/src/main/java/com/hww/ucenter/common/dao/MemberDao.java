package com.hww.ucenter.common.dao;

import com.hww.app.common.dto.AppSearchDto;
import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.base.common.page.Pagination;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterThirdDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.vo.ThirdMemberVo;
import com.hww.ucenter.common.vo.UCenterMemberVo;

import java.util.List;

public interface MemberDao extends IBaseEntityDao<Long, UCenterMember> {

    UCenterMember selectByMemberName(String memberName);

    boolean updateUserLocation(UCenterSign UCenterSign);

    String concernUser(UCenterMember UCenterMember);

    Integer concernUserCount(MyConcernDto dto);

    Integer concernMyCount(MyConcernDto dto);

    List<UCenterMemberVo> searchMembers(AppSearchDto search);

    UCenterMember thirdLogin(UCenterThirdDto thirdDto);

    UCenterMember ucenterMemberByPhone(ThirdMemberVo vo);

    List<UCenterMember> listUcenterMemberByIdS(Long[] ids);


    Pagination listUcenterMemberByKeysAndPage(UCenterMemberDto UCenterMemberDto);

    UCenterMember getUcenterMemberByPhoneNumber(String phoneNo,Long countryId);
}
