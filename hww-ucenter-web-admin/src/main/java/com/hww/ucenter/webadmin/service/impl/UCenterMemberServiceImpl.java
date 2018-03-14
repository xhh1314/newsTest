package com.hww.ucenter.webadmin.service.impl;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.Md5PwdEncoder;
import com.hww.base.util.SnowFlake;
import com.hww.base.util.StringUtils;
import com.hww.base.util.TimeUtils;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterUserDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.manager.UCenterMemberMng;
import com.hww.ucenter.webadmin.service.UCenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UCenterMemberServiceImpl implements UCenterMemberService {

	@Resource
    UCenterMemberMng UCenterMemberMng;
	

	@Override
    public UCenterMemberDto saveMember(UCenterMemberDto dto) {
		String password = dto.getPassword();
		password = new Md5PwdEncoder().encodePassword(password);
		dto.setPassword(password);
		dto.setLastModifyTime(TimeUtils.getDateToTimestamp());
		dto.setCreateTime(TimeUtils.getDateToTimestamp());
//		SnowFlake snowFlake = new SnowFlake(1, 1);
//		dto.setMemberId(snowFlake.nextId());
		dto.setMemberName(dto.getPhoneNo());
        return UCenterMemberMng.saveUCenterMember(dto);
	}

	@Override
    public boolean updateMember(UCenterMemberDto dto) {
        UCenterMemberDto UCenterMemberDto = UCenterMemberMng.getUCenterMemberById(dto.getMemberId());
        if (UCenterMemberDto == null) {
			return false;
		}
        UCenterMemberDto Udto=new UCenterMemberDto();
        BeanUtils.copyProperties(dto, Udto);
        Udto.setLastModifyTime(TimeUtils.getDateToTimestamp());
		// 把密码再放回去
        Udto.setPassword(UCenterMemberDto.getPassword());
        Udto.setSiteId(1);
        UCenterMemberMng.updateUCenterMember(Udto);
        return true;
	}

	@Override
    public void updateMemberStatusById(UCenterMemberDto UCenterMemberDto) {
        String ids = UCenterMemberDto.getAllIDCheck();
		if (StringUtils.isBlank(ids)) {
            UCenterMemberDto entity = UCenterMemberMng.getUCenterMemberById(UCenterMemberDto.getMemberId());
            entity.setStatus(UCenterMemberDto.getStatus());
            UCenterMemberMng.updateUCenterMember(entity);
			return;
		}
		ids.substring(0, ids.length() - 1);
		String[] mIdArray = ids.split(",");
		if (mIdArray.length == 0)
			return;
		for (int i = 0; i < mIdArray.length; i++) {
			if (StringUtils.isNumeric(mIdArray[i])) {
                UCenterMemberDto entity = UCenterMemberMng.getUCenterMemberById(Long.parseLong(mIdArray[i]));
                entity.setStatus(UCenterMemberDto.getStatus());
                UCenterMemberMng.updateUCenterMember(entity);
			}
		}
	}

//	@Override
//    public UCenterMemberDto getUcenterMemberByPhoneNumber(String phoneNo) {
//        return UCenterMemberMng.getUCenterMemberByPhoneNumber(phoneNo);
//	}

	@Override
    public Pagination listUcenterMemberByKeysAndPage(UCenterMemberDto UCenterMemberDto) {
        return UCenterMemberMng.listUCenterMemberBySqlAndPage(UCenterMemberDto);
	}

	@Override
	public UCenterMember queryUCenterMemberById(Long memberId) {
        return UCenterMemberMng.find(memberId);
	}

	@Override
    public boolean deleteUCenterMemberById(UCenterMemberDto UCenterMemberDto) {
        String ids = UCenterMemberDto.getAllIDCheck();
		if (StringUtils.isBlank(ids)) {
            deleteUcenterMemberImpl(UCenterMemberDto.getMemberId());
			return true;
		}
		ids.substring(0, ids.length() - 1);
		String[] mIdArray = ids.split(",");
		if (mIdArray.length == 0)
			return false;
		for (int i = 0; i < mIdArray.length; i++) {
			if (StringUtils.isNumeric(mIdArray[i])) {
				deleteUcenterMemberImpl(Long.parseLong(mIdArray[i]));
			}
		}
		return true;
	}

	/**
	 * 删除member的时候，把管理员表的用户也删除
	 *
	 * @param memberId
	 */
	private void deleteUcenterMemberImpl(Long memberId) {
        UCenterMemberDto ucenterMember = UCenterMemberMng.getUCenterMemberById(memberId);
        UCenterMemberMng.deleteUCenterMemberById(ucenterMember.getMemberId());
		

	}

	@Override
    public boolean resetPassword(UCenterMemberDto UCenterMemberDto) {
        String memberIdsStr = UCenterMemberDto.getAllIDCheck();
		if (StringUtils.isBlank(memberIdsStr)) {
            UCenterMemberDto newUCenterMemberDto = UCenterMemberMng
                    .getUCenterMemberById(UCenterMemberDto.getMemberId());
            if (newUCenterMemberDto == null)
				return false;
            newUCenterMemberDto.setPassword(new Md5PwdEncoder().encodePassword(UCenterMemberDto.getPassword()));
            UCenterMemberMng.updateUCenterMember(newUCenterMemberDto);
			return true;
		}
		memberIdsStr.substring(0, memberIdsStr.length() - 1);
		String[] mIdArray = memberIdsStr.split(",");
		if (mIdArray.length == 0)
			return false;
		for (int i = 0; i < mIdArray.length; i++) {
			if (StringUtils.isNumeric(mIdArray[i])) {
                UCenterMemberDto newUCenterMemberDto = UCenterMemberMng
                        .getUCenterMemberById(Long.parseLong(mIdArray[i]));
                if (newUCenterMemberDto == null)
					return false;
                newUCenterMemberDto.setPassword(new Md5PwdEncoder().encodePassword(UCenterMemberDto.getPassword()));
                UCenterMemberMng.updateUCenterMember(newUCenterMemberDto);
			}
		}
		return true;
	}
}
