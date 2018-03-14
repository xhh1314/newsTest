/*package com.hww.ucenter.common.dao.impl;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.ucenter.common.dao.UCenterUserDao;
import com.hww.ucenter.common.dto.UCenterUserDto;
import com.hww.ucenter.common.entity.UCenterUser;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class UCenterUserDaoImpl extends LocalEntityDaoImpl<Long, UCenterUser> implements UCenterUserDao {

	@Override
	public UCenterUser getUCenterUserById(Long memberId) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create("from UCenterUser where 1=1");
		finder.append(" and memberId=:memberId");
		finder.setParam("memberId", memberId);
		List<UCenterUser> ucenterMemberList = (List<UCenterUser>) find(finder);
		if (null == ucenterMemberList || ucenterMemberList.size() == 0) {
			return null;
		}
		return ucenterMemberList.get(0);
	}

	@Override
	public Pagination listUCenterUserBySqlAndPage(UCenterUserDto searchDto) {
		Finder hql = Finder.create("select u from UCenterMember  m,UCenterUser u");
		hql.append(" where u.memberId=m.memberId");
		if (searchDto.getSiteId() != null) {
			hql.append(" and m.siteId:siteIdP").setParam("siteIdP", searchDto.getSiteId());
		}
		if (searchDto.getMemberId() != null) {
			// hql.append(" and u.memberId=:memberIdP").setParam("memberIdP",
			// searchDto.getMemberId());
			String s = " and u.memberId like '%" + searchDto.getMemberId() + "%'";
			hql.append(s);
		}
		if (StringUtils.hasText(searchDto.getRealName())) {
//			hql.append(" and u.realName=:realNameP").setParam("realNameP", searchDto.getRealName());
			String s = " and u.realName like '%" + searchDto.getRealName() + "%'";
			hql.append(s);
		}
		if (StringUtils.hasText(searchDto.getPhoneNo())) {
//			hql.append(" and m.phoneNo=:phoneNoP").setParam("phoneNoP", searchDto.getPhoneNo());
			String s = " and m.phoneNo like '%" + searchDto.getPhoneNo() + "%'";
			hql.append(s);
		}
		if (searchDto.getStatus() != null) {
			hql.append(" and u.status=:statusP").setParam("statusP", searchDto.getStatus());
		}
		// List<UCenterUser>
		// UCenterUsers=entityManager.createNativeQuery(hql.getOrigHql(),UCenterUser.class).getResultList();
		Pagination p = this.find(hql, searchDto.getPageNo(), searchDto.getPageSize());
		if (p != null && p.getList() != null) {
			// BeanUtils.
			List<UCenterUserDto> memberDtoList = BeanMapper.mapList(p.getList(), UCenterUserDto.class);
			p.setList(memberDtoList);
			return p;
		}
		return null;
	}

}
*/