package com.hww.ucenter.common.manager.impl;

import com.google.common.collect.Lists;
import com.hww.app.common.dto.AppSearchDto;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.TimeUtils;
import com.hww.framework.common.tool.SWBeanUtil;
import com.hww.ucenter.common.dao.MemberDao;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterThirdDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.manager.UCenterMemberMng;
import com.hww.ucenter.common.vo.UCenterMemberVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("ucenterMemberMng")
public class UCenterMemberMngImpl extends BaseEntityMngImpl<Long, UCenterMember, MemberDao>
        implements UCenterMemberMng {
	private static final Logger log = LoggerFactory.getLogger(UCenterMemberMngImpl.class);
    private MemberDao memberDao;

    public MemberDao getMemberDao() {
        return memberDao;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        super.setEntityDao(memberDao);
        this.memberDao = memberDao;
    }

	@Override
	public void setMememberSnsDisabled(Long memberId,Integer disabled) {
	    Finder f = Finder.create(" update UCenterMember s ");
        f.append(" set ");
        f.append(" s.snsDisabled = :disabled ");
        f.append(" where s.memberId = :memberId ");
        f.setParam("disabled", disabled);
        f.setParam("memberId", memberId);
        memberDao.update(f);
	}
	

	
	
    @Override
    public UCenterMemberDto loadByMemberName(String memberName) {
        UCenterMember newUCenterMember = memberDao.selectByMemberName(memberName);
        if (newUCenterMember == null)
            return null;
        UCenterMemberDto UCenterMemberDto = new UCenterMemberDto();
        BeanUtils.copyProperties(newUCenterMember, UCenterMemberDto);
        return UCenterMemberDto;
    }

    @Override
    public UCenterMember getUCenterMemberById(UCenterMember UCenterMember) {
        return memberDao.find(UCenterMember.getMemberId());
    }

    @Override
    public boolean updateUserLocation(UCenterSign UCenterSign) {
        return memberDao.updateUserLocation(UCenterSign);
    }

    @Override
    public String concernUser(UCenterMember UCenterMember) {
        return memberDao.concernUser(UCenterMember);
    }

    @Override
    public Integer concernUserCount(MyConcernDto dto) {

        return memberDao.concernUserCount(dto);
    }

    @Override
    public Integer concernMyCount(MyConcernDto dto) {

        return memberDao.concernMyCount(dto);
    }

    @Override
    public List<UCenterMemberVo> searchMembers(AppSearchDto search) {
        return memberDao.searchMembers(search);
    }

    @Override
    public UCenterMemberDto getUCenterMemberByPhoneNumber(String phoneNo,Long countryId) {
        UCenterMember UCenterMember = memberDao.getUcenterMemberByPhoneNumber(phoneNo,countryId);
        if (UCenterMember == null)
            return null;
        UCenterMemberDto UCenterMemberDto = new UCenterMemberDto();
        BeanUtils.copyProperties(UCenterMember, UCenterMemberDto);
        return UCenterMemberDto;
    }

    @Override
    public void resetPassword(UCenterMemberDto UCenterMemberDto) {
        UCenterMember UCenterMember = memberDao.find(UCenterMemberDto.getMemberId());
        UCenterMember.setPassword(UCenterMemberDto.getPassword());
        memberDao.update(UCenterMember);
    }

    @Override
    public UCenterMemberDto thirdLogin(UCenterThirdDto thirdDto) {
        UCenterMember member = memberDao.thirdLogin(thirdDto);
        if (member == null) {
            return null;
        }
        UCenterMemberDto dtox=new UCenterMemberDto();
        BeanUtils.copyProperties(member, dtox);
        return dtox;
    }

    @Override
    public List<UCenterMemberDto> listUCenterMemberByIds(Long[] ids) {
        List<UCenterMember> UCenterMembers = memberDao.listUcenterMemberByIdS(ids);
        if (UCenterMembers == null || UCenterMembers.size() == 0)
            return null;
        return BeanMapper.mapList(UCenterMembers, UCenterMemberDto.class);
    }

    @Override
    public UCenterMemberDto saveUCenterMember(UCenterMemberDto UCenterMemberDto) {
    	
    	log.debug("-----------UCenterMemberMngImpl---saveUCenterMember--------------getCountryId:"+UCenterMemberDto.getCountryId());
        UCenterMember UCenterMember = new UCenterMember();
        BeanUtils.copyProperties(UCenterMemberDto, UCenterMember);
        UCenterMember.setSnsDisabled(0);
        UCenterMember.setCreateTime(TimeUtils.getDateToTimestamp());
        UCenterMember.setLastModifyTime(TimeUtils.getDateToTimestamp());
        
        UCenterMember.setCountryId(Long.valueOf(UCenterMemberDto.getCountryId()));
        UCenterMember.setCountryName(UCenterMemberDto.getCountryName());
        log.debug("-----------UCenterMemberMngImpl---saveUCenterMember-------------UCenterMember-getCountryId:"+UCenterMember.getCountryId());
        UCenterMember newUCenterMember = memberDao.save(UCenterMember);
        log.debug("-----------UCenterMemberMngImpl---after--save-------------newUCenterMember-getCountryId:"+newUCenterMember.getCountryId());
        UCenterMemberDto newUCenterMemberDto = new UCenterMemberDto();
        BeanUtils.copyProperties(newUCenterMember, newUCenterMemberDto);
        return newUCenterMemberDto;
    }

    @Override
    public UCenterMemberDto getUCenterMemberById(Long memberId) {
        UCenterMember UCenterMember = memberDao.find(memberId);
        if (UCenterMember == null)
            return null;
        UCenterMemberDto dto = new UCenterMemberDto();
        BeanUtils.copyProperties(UCenterMember, dto);
        return dto;
    }

    @Override
    public UCenterMemberVo updateUCenterMember(UCenterMemberVo UCenterMemberVo) {
        UCenterMember member = memberDao.find(UCenterMemberVo.getMemberId());
//        SWBeanUtil.copy(UCenterMemberVo, member, true);
//        BeanUtils.copyProperties(UCenterMemberVo, member);
        
        if(UCenterMemberVo.getAddress()!=null&&StringUtils.hasText(UCenterMemberVo.getAddress())) {
        	member.setAddress(UCenterMemberVo.getAddress());
        }
        if(UCenterMemberVo.getAvatarId()!=null) {
        	member.setAvatarId(UCenterMemberVo.getAvatarId());
        }
        if(UCenterMemberVo.getAvatar()!=null&&StringUtils.hasText(UCenterMemberVo.getAvatar())) {
        	member.setAvatar(UCenterMemberVo.getAvatar());
        }
        if(UCenterMemberVo.getEmail()!=null&&StringUtils.hasText(UCenterMemberVo.getEmail())) {
        	member.setEmail(UCenterMemberVo.getEmail());
        }
        if(UCenterMemberVo.getMemberName()!=null&&StringUtils.hasText(UCenterMemberVo.getMemberName())) {
        	member.setMemberName(UCenterMemberVo.getMemberName());
        }
        if(UCenterMemberVo.getNickName()!=null&&StringUtils.hasText(UCenterMemberVo.getNickName())) {
        	member.setNickName(UCenterMemberVo.getNickName());
        }
        if(UCenterMemberVo.getSex()!=null&&StringUtils.hasText(UCenterMemberVo.getSex())) {
        	member.setSex(UCenterMemberVo.getSex());
        }
        if(UCenterMemberVo.getSignature()!=null&&StringUtils.hasText(UCenterMemberVo.getSignature())) {
        	member.setSignature(UCenterMemberVo.getSignature());
        }
        if(UCenterMemberVo.getCountryId()!=null) {
        	member.setCountryId(UCenterMemberVo.getCountryId());
        }
        if(UCenterMemberVo.getProvinceId()!=null) {
        	member.setProvinceId(UCenterMemberVo.getProvinceId());
        }
        if(UCenterMemberVo.getCityId()!=null) {
        	member.setCityId(UCenterMemberVo.getCityId());
        }
        
        if(UCenterMemberVo.getCountryName()!=null&&StringUtils.hasText(UCenterMemberVo.getCountryName())) {
        	member.setCountryName(UCenterMemberVo.getCountryName());
        }
        if(UCenterMemberVo.getProvinceName()!=null&&StringUtils.hasText(UCenterMemberVo.getProvinceName())) {
        	member.setProvinceName(UCenterMemberVo.getProvinceName());
        }
        if(UCenterMemberVo.getCityName()!=null&&StringUtils.hasText(UCenterMemberVo.getCityName())) {
        	member.setCityName(UCenterMemberVo.getCityName());
        }
        if(UCenterMemberVo.getQqUuid()!=null){
        	member.setQqUuid(UCenterMemberVo.getQqUuid());
        }
        if(UCenterMemberVo.getQqNo()!=null){
        	member.setQqNo(UCenterMemberVo.getQqNo());
        }
        if(UCenterMemberVo.getWeChatNo()!=null){
        	member.setWeChatNo(UCenterMemberVo.getWeChatNo());
        }
        if(UCenterMemberVo.getWeChatUuid()!=null){
        	member.setWeChatUuid(UCenterMemberVo.getWeChatUuid());
        }
        if(UCenterMemberVo.getWeiBoNo()!=null){
        	member.setWeiBoNo(UCenterMemberVo.getWeiBoNo());
        }
        if(UCenterMemberVo.getWeiBoUuid()!=null){
        	member.setWeiBoUuid(UCenterMemberVo.getWeiBoUuid());
        }
        
        //如果有国家的修改的话---
        if(UCenterMemberVo.getCountryId()!=null) {
        	//如果没有省---则省-市全部置空
        	if(UCenterMemberVo.getProvinceId()==null) {
        		member.setProvinceName(null);
            	member.setCityName(null);
            	member.setCityId(null);
        	}else {
        		//省不为空--
        		member.setProvinceId(UCenterMemberVo.getProvinceId());
        		member.setProvinceName(UCenterMemberVo.getProvinceName());
        		//
        		if(UCenterMemberVo.getCityId()==null) {
        			member.setCityName(null);
                	member.setCityId(null);
        		}else {
        			member.setCityId(UCenterMemberVo.getCityId());
        			member.setCityName(UCenterMemberVo.getCityName());
        		}
        		
        	}
        }
        
   
        
        
//        if (UCenterMemberVo.getCityName() == null) {
//        	member.setCityName(null);
//        	member.setCityId(null);
//        }
//        if (UCenterMemberVo.getCityId()== null) {
//        	member.setCityId(null);
//        	member.setCityName(null);
//        }else {
//        	member.setCityId(UCenterMemberVo.getCityId());
//        }
//        if (UCenterMemberVo.getProvinceName() == null) {
//        	member.setProvinceName(null);
//        	member.setProvinceId(null);
//        }
//        if (UCenterMemberVo.getProvinceId()== null) {
//        	member.setProvinceId(null);
//        	member.setProvinceName(null);
//        }else {
//        	member.setProvinceId(UCenterMemberVo.getProvinceId());
//        }
//        if (UCenterMemberVo.getCountryName() == null) {
//        	member.setCountryName(null);
//        	member.setCountryId(null);
//        }
//        if (UCenterMemberVo.getCountryId() == null) {
//        	member.setCountryId(null);
//        	member.setCountryName(null);
//        }else {
//        	member.setCountryId(UCenterMemberVo.getCountryId());
//        }
        UCenterMember update = memberDao.update(member);
        BeanUtils.copyProperties(update, UCenterMemberVo);
        return UCenterMemberVo;
    }

    @Override
    public void updateUCenterMember(UCenterMemberDto UCenterMemberVo) {
        UCenterMember member = memberDao.find(UCenterMemberVo.getMemberId());
        BeanUtils.copyProperties(UCenterMemberVo, member);
        memberDao.update(member);
    }

    @Override
    public UCenterMemberVo refreshLoginRecord(UCenterMemberDto uCenterMemberDto) {
    	
    	 UCenterMember member = memberDao.find(uCenterMemberDto.getMemberId());
    	 member.setLastLoginTime(TimeUtils.getDateToTimestamp());
    	 member.setLastLoginIp(uCenterMemberDto.getLastLoginIp());
    	  memberDao.update(member);
    	 
        UCenterMemberVo vo = new UCenterMemberVo();
        BeanUtils.copyProperties(member, vo);
        return vo;
    }

    @Override
    public void deleteUCenterMemberById(Long memberId) {
        memberDao.delete(memberId);

    }

    @Override
    public Pagination listUCenterMemberBySqlAndPage(UCenterMemberDto UCenterMemberDto) {
        return memberDao.listUcenterMemberByKeysAndPage(UCenterMemberDto);
    }

	@Override
	public void unbundThirdPart(UCenterThirdDto thirdDto) {
	    Finder f = Finder.create(" update UCenterMember s ");
        if (StringUtils.hasText(thirdDto.getQqUuid())) {
            f.append(" set s.qqUuid = null ");
          //  f.setParam("qqUuid", thirdDto.getQqUuid());
        } else if (StringUtils.hasText(thirdDto.getWeChatUuid())) {
            f.append(" set s.weChatUuid = null");
           // f.setParam("weChatUuid", thirdDto.getWeChatUuid());
        } else if (StringUtils.hasText(thirdDto.getWeiBoUuid())) {
            f.append(" set s.weiBoUuid =null ");
           // f.setParam("weiBoUuid", thirdDto.getWeiBoUuid());
        }else {
        	return;
        }
        memberDao.update(f);
	}

	
	@Override
	public UCenterMember loadByThirdPartQQUuid(String qqUuid) {
	    Finder f = Finder.create(" from UCenterMember s ");
	    f.append(" where s.qqUuid = :qqUuid ");
        f.setParam("qqUuid",qqUuid);

        List<?> res=memberDao.find(f);
        return (res==null||res.isEmpty())?null:(UCenterMember)res.get(0);
	}
	@Override
	public UCenterMember loadByThirdPartWeChatUuid(String weChatUuid) {
	    Finder f = Finder.create(" from UCenterMember s ");
	    f.append(" where s.weChatUuid = :weChatUuid ");
        f.setParam("weChatUuid",weChatUuid);

        List<?> res=memberDao.find(f);
        return (res==null||res.isEmpty())?null:(UCenterMember)res.get(0);
	}
	
	@Override
	public UCenterMember loadByThirdPartWeiBoUuid(String weiBoUuid) {
	    Finder f = Finder.create(" from UCenterMember s ");
	    f.append(" where s.weiBoUuid = :weiBoUuid ");
        f.setParam("weiBoUuid",weiBoUuid);
        
        List<?> res=memberDao.find(f);
        return (res==null||res.isEmpty())?null:(UCenterMember)res.get(0);
	}
	
	@Override
	public List<UCenterMember> loadUCenterMemberByIds(List<Long> memberIds) {
		if(memberIds==null||memberIds.isEmpty()) {
			return Lists.newArrayList();
		}
	     Finder finder = Finder.create("from UCenterMember");
	        finder.append("where memberId in :memberIds");
	        finder.setParamList("memberIds", memberIds);
	        List<UCenterMember> list= (List<UCenterMember>) memberDao.find(finder);
	        return list==null?Lists.newArrayList():list;
	}

}
