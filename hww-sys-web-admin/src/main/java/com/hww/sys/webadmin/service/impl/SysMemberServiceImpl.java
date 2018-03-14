/*package com.hww.sys.webadmin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.PwdEncoder;
import com.hww.base.util.StringUtils;
import com.hww.sys.common.dto.SysMemberDto;
import com.hww.sys.webadmin.service.SysMemberService;
import com.hww.ucenter.common.entity.Admin;
import com.hww.ucenter.common.manager.SysMemberMng;

@Service("sysMemberService")
@Transactional
public class SysMemberServiceImpl implements SysMemberService {

	@Resource
	SysMemberMng sysMemberMng;

	@Autowired
	PwdEncoder pwdEncoder;

	@Override
	public Pagination list(SysMemberDto searchDto, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		Finder hql = Finder.create("from ");
		hql.append(Admin.class.getName());
		hql.append(" where 1=1");
		if (searchDto.getSiteId() != null) {
			hql.append(" and siteId=:siteIdP").setParam("siteIdP", searchDto.getSiteId());
		}
		if (StringUtils.isNotBlank(searchDto.getMembername())) {
			hql.append(" and membername like :memberNameP").setParam("memberNameP",
					"%" + searchDto.getMembername() + "%");
		}
		if (searchDto.getStatus() != null) {
			hql.append(" and status=:statusP").setParam("statusP", searchDto.getStatus());
		} else {
			hql.append(" and status>0");
		}

		Pagination p = sysMemberMng.find(hql, pageNo, pageSize);
		if (p != null && p.getList() != null) {
			List<SysMemberDto> memberDtoList = BeanMapper.mapList(p.getList(), SysMemberDto.class);
			p.setList(memberDtoList);
		}

		return p;
	}

	@Override
	public SysMemberDto findMemberById(Long id) {
		// TODO Auto-generated method stub
		Admin entity = sysMemberMng.find(id);
		SysMemberDto dto = BeanMapper.map(entity, SysMemberDto.class);

		return dto;
	}

	@Override
	public void updateMemberStatusByIds(Short status, Collection<Long> memberIds) {
		// TODO Auto-generated method stub
		sysMemberMng.updateStatusByProperty(status, "memberId", memberIds);
	}

	@Override
	public void saveMember(SysMemberDto dto) {
		// TODO Auto-generated method stub
		if (StringUtils.isNotBlank(dto.getPassword())) {
			dto.setPassword(pwdEncoder.encodePassword(dto.getPassword()));
		} else {
			dto.setPassword("");
		}
		Admin entity = BeanMapper.map(dto, Admin.class);

		sysMemberMng.save(entity);
	}

	@Override
	public void updateMember(SysMemberDto dto) {
		// TODO Auto-generated method stub
		Admin entity = sysMemberMng.find(dto.getMemberId());
		if (StringUtils.isNotBlank(dto.getPassword())) {
			dto.setPassword(pwdEncoder.encodePassword(dto.getPassword()));
		}
		try {
			BeanUtils.copyProperties(entity, dto);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sysMemberMng.update(entity);
	}

}
*/