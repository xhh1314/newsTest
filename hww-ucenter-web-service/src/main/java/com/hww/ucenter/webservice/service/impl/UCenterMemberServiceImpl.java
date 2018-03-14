package com.hww.ucenter.webservice.service.impl;

import com.google.common.collect.Lists;
import com.hww.app.common.dto.AppSearchDto;
import com.hww.base.common.util.Finder;
import com.hww.base.util.Md5PwdEncoder;
import com.hww.base.util.R;
import com.hww.base.util.TimeUtils;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.api.SnsFeignClient;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterThirdDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.entity.UCenterLogin;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.entity.UCenterShortMessage;
import com.hww.ucenter.common.manager.UCenterMemberMng;
import com.hww.ucenter.common.vo.UCenterMemberVo;
import com.hww.ucenter.webservice.service.ConcernService;
import com.hww.ucenter.webservice.service.UCenterLoginService;
import com.hww.ucenter.webservice.service.UCenterMemberService;
import com.hww.ucenter.webservice.service.UCenterSendSMS;
import com.hww.ucenter.webservice.service.UCenterVerificationCodeStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class UCenterMemberServiceImpl implements UCenterMemberService {

    private static final Logger log = LoggerFactory.getLogger(UCenterMemberServiceImpl.class);

    @Resource
    private UCenterMemberMng UCenterMemberMng;

    @Autowired
    private SnsFeignClient snsFeignClient;

    @Autowired
    private UCenterVerificationCodeStore UCenterVerificationCodeStore;

    @Autowired
    private UCenterSendSMS UCenterSendSMS;

    @Autowired
    private UCenterLoginService ucenterLoginService;
    
    
    @Autowired
    ConcernService concernService;
    
    	@Scheduled(fixedDelay = 1000*60*30)
	   @CacheEvict(value = "ucenter_getUCenterMemberById",allEntries=true)
	   public void getUCenterMemberById_delete_from_cache() {
	   }
    
    
    @CacheEvict(value = "ucenter_getUCenterMemberById",key="'getUCenterMemberById_'+#memberId")
    @Override
	public void setMememberSnsDisabled(Long memberId,Integer disabled) {
	   if(memberId!=null&&disabled!=null) {
		   UCenterMemberMng.setMememberSnsDisabled(memberId, disabled);
	   }
	}
    
    @Override
    public R login(UCenterMemberDto login,String countryCode,Long countryId) {
        String phoneN = login.getPhoneNo();
        UCenterMemberDto member;

        if (StringUtils.hasText(phoneN)) {
            member = UCenterMemberMng.getUCenterMemberByPhoneNumber(login.getPhoneNo(),countryId);
            if (member == null)
                return R.error("手机号未注册!").put("data", null);
        } else {
            member = UCenterMemberMng.loadByMemberName(login.getMemberName());
            if (member == null)
                return R.error("用户名不存在!").put("data", null);
        }
        if(member.getStatus()!=null&&member.getStatus().intValue()!=1) {
        	return R.error("用户账号已经被冻结!").put("data", null);
        }
//        if(member.getSnsDisabled()!=null&&member.getSnsDisabled()==1) {
//        	return R.error("用户被禁用!").put("data", null);
//        }
        String loginPass = new Md5PwdEncoder().encodePassword(login.getPassword());
        //7b695d3442deeb402d93cb689c06d1f9 210b48b542659fb951a80a15c5997513
        if (!member.getPassword().equals(loginPass))
            return R.error("密码不正确").put("data", null);
        member.setLastLoginTime(TimeUtils.getDateToTimestamp());
        member.setLastLoginIp(login.getLastLoginIp());
//        UCenterMemberVo UCenterMemberVo = UCenterMemberMng.refreshLoginRecord(member);
        
       try {
    	   UCenterLogin ucenterLogin = new UCenterLogin( member.getMemberId(),  login.getLastLoginIp(),  login.getLon(),  login.getLat(),  countryCode);
    	   ucenterLogin.setCountryId(countryId);
    	   ucenterLogin.setSiteId(1);
    	   ucenterLogin.setStatus(Short.valueOf("1"));
    	   ucenterLogin.setCreateTime(TimeUtils.getDateToTimestamp());
    	   ucenterLogin.setLastModifyTime(TimeUtils.getDateToTimestamp());
    	   ucenterLoginService.saveLogin(ucenterLogin);
       }catch (Exception e) {}
       UCenterMemberVo UCenterMemberVo =new UCenterMemberVo();
       try {
		com.hww.base.util.BeanUtils.copyProperties(UCenterMemberVo, member);
	} catch (Exception e) {
		e.printStackTrace();
	}
        return R.ok().put("data", UCenterMemberVo);
    }

    @Cacheable(value = "ucenter_getUCenterMemberById",key="'getUCenterMemberById_'+#memberId")
    @Override
    public UCenterMemberVo getUCenterMemberById(Long memberId) {
    	
        UCenterMemberDto UCenterMemberDto = UCenterMemberMng.getUCenterMemberById(memberId);
        if (UCenterMemberDto == null)
            return null;
        UCenterMemberVo UCenterMemberVo = new UCenterMemberVo();
        BeanUtils.copyProperties(UCenterMemberDto, UCenterMemberVo);
        return UCenterMemberVo;
    }

    @Override
    public UCenterMemberVo register(UCenterMemberDto UCenterMemberDto) {
    	log.debug("--------------register--------------:"+UCenterMemberDto.getCountryId());
        UCenterMemberDto.setPassword(new Md5PwdEncoder().encodePassword(UCenterMemberDto.getPassword()));
        UCenterMemberDto.setCreateTime(TimeUtils.getDateToTimestamp());
        UCenterMemberDto.setLastModifyTime(TimeUtils.getDateToTimestamp());
        UCenterMemberDto.setStatus(new Short("1"));
        UCenterMemberDto UCenterMemberDto1 = UCenterMemberMng.saveUCenterMember(UCenterMemberDto);
        UCenterMemberVo memberVo = new UCenterMemberVo();
        BeanUtils.copyProperties(UCenterMemberDto1, memberVo);
        return memberVo;
    }
    @CacheEvict(value = "ucenter_getUCenterMemberById",key="'getUCenterMemberById_'+#UCenterMemberVo.memberId")
    @Override
    public UCenterMemberVo updateUCenterMember(UCenterMemberVo UCenterMemberVo) {
        return UCenterMemberMng.updateUCenterMember(UCenterMemberVo);
    }

    @Override
    public String concernUser(UCenterMember UCenterMember) {
        return UCenterMemberMng.concernUser(UCenterMember);
    }

    @Override
    public Integer concernUserCount(MyConcernDto dto) {

        return UCenterMemberMng.concernUserCount(dto);
    }

    @Override
    public Integer concernMyCount(MyConcernDto dto) {

        return UCenterMemberMng.concernMyCount(dto);
    }

    @Override
    public Integer topicCountByUser(MyConcernDto dto) {
        dto.setMemberId(dto.getMemberId());

        return snsFeignClient.findCountById(dto.getMemberId(), 0);
    }

//    @Override
//    public List<SnsTopicVo> myTopics(Long memberId) {
//        return snsFeignClient.myTopics(memberId);
//    }

    @Override
    public List<UCenterMemberVo> searchMembers(AppSearchDto search) {
    	List<UCenterMemberVo> uCenterMemberVoList = UCenterMemberMng.searchMembers(search);
    	
    	if(uCenterMemberVoList==null||uCenterMemberVoList.isEmpty()) {
    		return Lists.newArrayList();
    	}
    	for(UCenterMemberVo vo: uCenterMemberVoList) {
    		UCenterFollow follow = concernService.isConcern(search.getMemberId(), vo.getMemberId());
    		if(follow!=null) {
    			Integer concernType=follow.getConcernType();
    			vo.setConcernType(concernType);
    		}else {
    			vo.setConcernType(0);
    		}
    	}
    	
        return uCenterMemberVoList;
    }

    @Override
    public UCenterMemberVo thirdLogin(UCenterThirdDto thirdDto) {
        
        UCenterMemberDto  dtoIndb= UCenterMemberMng.thirdLogin(thirdDto);
        if (dtoIndb == null) {
            return null;
        }
        UCenterMemberVo vox=new UCenterMemberVo();
        BeanUtils.copyProperties(dtoIndb, vox);
        return vox;
    }

    @Override
    public UCenterMemberVo getUCenterMemberByPhoneNumber(String phoneNumber,Long countryId) {
        UCenterMemberDto UCenterMemberDto = UCenterMemberMng.getUCenterMemberByPhoneNumber(phoneNumber,countryId);
        UCenterMemberVo UCenterMemberVo = new UCenterMemberVo();
        if (UCenterMemberDto != null) {
            BeanUtils.copyProperties(UCenterMemberDto, UCenterMemberVo);
        } else {
            return null;
        }
        return UCenterMemberVo;
    }
    @CacheEvict(value = "ucenter_getUCenterMemberById",key="'getUCenterMemberById_'+#thirdDto.memberId")
    @Override
	public UCenterMemberVo unbundThirdPart(UCenterThirdDto thirdDto) {
		if(thirdDto!=null&&thirdDto.getMemberId()!=null) {
			UCenterMember mem=UCenterMemberMng.find(thirdDto.getMemberId());
			if(mem==null) {
				throw new HServiceLogicException("未查询到该用户");
			}
			UCenterMemberVo vo=new UCenterMemberVo();
			 
			UCenterMemberMng.unbundThirdPart( thirdDto);
			BeanUtils.copyProperties(mem, vo);
			 if (StringUtils.hasText(thirdDto.getQqUuid())) {
				 vo.setQqUuid(null);
		        } else if (StringUtils.hasText(thirdDto.getWeChatUuid())) {
		          vo.setWeChatUuid(null);
		        } else if (StringUtils.hasText(thirdDto.getWeiBoUuid())) {
		        	vo.setWeChatUuid(null);
		        }
			return vo;
		}
		return null;
		
	}
    @CacheEvict(value = "ucenter_getUCenterMemberById",key="'getUCenterMemberById_'+#memberId")
    @Override
    public boolean resetPassword(Long memberId, String password) {
        UCenterMemberDto UCenterMemberDto = UCenterMemberMng.getUCenterMemberById(memberId);
        if (UCenterMemberDto == null)
            return false;
        UCenterMemberDto.setPassword(new Md5PwdEncoder().encodePassword(password));
        UCenterMemberMng.resetPassword(UCenterMemberDto);
        return true;
    }

    @Override
    public boolean verifyPhoneNumberIsExist(String phoneNo,Long countryId) {
        return UCenterMemberMng.getUCenterMemberByPhoneNumber(phoneNo,countryId) != null;
    }

    @Override
    public R sendVerificationCodeOfPhoneNumber(String nationCode, String phoneNumber) {
        boolean isExceedLimit = UCenterVerificationCodeStore.getExceedSendCountLimit(phoneNumber);
        if (isExceedLimit)
            return R.ok("发送验证码次数太多，请明天再发送!").put("data", false);
        UCenterShortMessage oldCode = UCenterVerificationCodeStore.getCode(phoneNumber);
        if (oldCode != null)
            return R.ok("验证码还在有效期内").put("data", false);
        int random = (int) (Math.random() * 9000) + 1000;
        boolean flag = UCenterSendSMS.sendMessage(nationCode, phoneNumber, random + "");
        if (!flag)
            return R.error(500, "发送验证码失败!").put("data", false);
        String newCode = UCenterVerificationCodeStore.saveCode(phoneNumber, random + "");
        if (newCode == null)
            return R.error(500, "保存验证码失败！").put("data", false);
        return R.ok("发送验证码成功！").put("data", true);
    }

    @Override
    public boolean verificationCodeIsCorrect(String phoneNumber, String code) {
        UCenterShortMessage oldCode = UCenterVerificationCodeStore.getCode(phoneNumber);
        if (oldCode != null && code.equals(oldCode.getCode())) {
            UCenterVerificationCodeStore.deleteCode(oldCode);
            return true;
        } else {
            return false;
        }
    }

    
    
	
	@Override
	public UCenterMember loadByThirdPartQQUuid(String qqUuid) {
		return UCenterMemberMng.loadByThirdPartQQUuid(qqUuid);
	}
	
	@Override
	public UCenterMember loadByThirdPartWeChatUuid(String weChatUuid) {
		 return UCenterMemberMng.loadByThirdPartWeChatUuid(weChatUuid);
	}
	
	@Override
	public UCenterMember loadByThirdPartWeiBoUuid(String weiBoUuid) {
	  
        return UCenterMemberMng.loadByThirdPartWeiBoUuid(weiBoUuid);
	}
	
	@Override
	public Long saveUser(UCenterMember member) {
		UCenterMemberMng.save(member);
		return member.getMemberId();
	}
	
	
}
