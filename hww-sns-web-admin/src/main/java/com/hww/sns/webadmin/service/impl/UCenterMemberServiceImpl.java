package com.hww.sns.webadmin.service.impl;

/*import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hww.base.util.BeanMapper;
import com.hww.sns.webadmin.service.UCenterMemberService;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.manager.UCenterMemberMng;

@Service("ucenterMemberService")
public class UCenterMemberServiceImpl implements UCenterMemberService {

	@Resource
	UCenterMemberMng ucenterMemberMng;
	
	@Override
	public UCenterMemberDto findMemberById(Long id) {
		UCenterMember entity = ucenterMemberMng.find(id);
		UCenterMemberDto dto = BeanMapper.map(entity, UCenterMemberDto.class);
		return dto;
	}


	public UCenterMemberDto findMemberByName(String name) {
		String memberName = "memberName";
		String[] values= {name};
		List<UCenterMember> entity = ucenterMemberMng.find(memberName,values);
		UCenterMemberDto dto = BeanMapper.map(entity.get(0), UCenterMemberDto.class);
		return dto;
	}
}
*/