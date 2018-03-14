/*package com.hww.ucenter.webadmin.service.impl;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.*;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterUserDto;
import com.hww.ucenter.common.manager.UCenterMemberMng;
import com.hww.ucenter.common.manager.UCenterUserMng;
import com.hww.ucenter.common.vo.UCenterUserVo;
import com.hww.ucenter.webadmin.service.UCenterMemberService;
import com.hww.ucenter.webadmin.service.UCenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@Transactional
public class UCenterUserServiceImpl implements UCenterUserService {

    @Resource
    private UCenterUserMng UCenterUserMng;

    @Autowired
    private UCenterMemberService ucenterMemberService;

    @Autowired
    private UCenterMemberMng UCenterMemberMng;

    *//**
     * 查找UCenterUser
     * 需要根据条件从member表和user表进行联查，先把user的数据全部返回，再根据id到member表取出数据，再在service层进行人工拼接
     *
     * @param searchDto
     * @return
     *//*
    @Override
    public Pagination listUCenterUserByKeysAndPage(UCenterUserDto searchDto) {
        Pagination p = UCenterUserMng.listUCenterUserBySqlAndPage(searchDto);
        List<UCenterUserDto> UCenterUserDtos = (List<UCenterUserDto>) p.getList();
        Long[] memberIds = new Long[UCenterUserDtos.size()];
        int i = 0;
        for (UCenterUserDto UCenterUserDto : UCenterUserDtos) {
            memberIds[i++] = UCenterUserDto.getMemberId();
        }
        List<UCenterMemberDto> UCenterMemberDtos = UCenterMemberMng.listUCenterMemberByIds(memberIds);
        // 遍历，把ucenterMember的属性全部复制到UCenterUser中
        for (UCenterUserDto UCenterUserDto : UCenterUserDtos) {
            for (UCenterMemberDto UCenterMemberDto : UCenterMemberDtos) {
                if (UCenterMemberDto.getMemberId().equals(UCenterUserDto.getMemberId())) {
                    // 这里手工指定具体要赋值哪些属性
                    UCenterUserDto.setEmail(UCenterMemberDto.getEmail());
                    UCenterUserDto.setNickName(UCenterMemberDto.getNickName());
                    UCenterUserDto.setPhoneNo(UCenterMemberDto.getPhoneNo());
                    break;
                }
            }
        }

        return p;
    }

    @Override
    public void updateUCenterUserStatusById(UCenterUserVo UCenterUserVo) {
        String ids = UCenterUserVo.getAllIDCheck();
        if (StringUtils.isBlank(ids)) {
            UCenterUserDto UCenterUserDto = UCenterUserMng.getUCenterUserById(UCenterUserVo.getMemberId());
            UCenterUserDto.setStatus(UCenterUserVo.getStatus());
            UCenterUserMng.updateUCenterUser(UCenterUserDto);
            return;
        }
        ids.substring(0, ids.length() - 1);
        String[] mIdArray = ids.split(",");
        if (mIdArray.length == 0)
            return;
        for (int i = 0; i < mIdArray.length; i++) {
            if (StringUtils.isNumeric(mIdArray[i])) {
                UCenterUserDto UCenterUserDto = UCenterUserMng.getUCenterUserById(Long.parseLong(mIdArray[i]));
                UCenterUserDto.setStatus(UCenterUserVo.getStatus());
                UCenterUserMng.updateUCenterUser(UCenterUserDto);
            }
        }
    }

    @Override
    public UCenterUserVo getUCenterUserById(Long memberId) {
        UCenterUserDto ucenterUserDto = UCenterUserMng.getUCenterUserById(memberId);
        UCenterMemberDto uCenterMemberDto = UCenterMemberMng.getUCenterMemberById(memberId);
        ucenterUserDto.setEmail(uCenterMemberDto.getEmail());
        ucenterUserDto.setNickName(uCenterMemberDto.getNickName());
        ucenterUserDto.setMembername(uCenterMemberDto.getMemberName());
        ucenterUserDto.setPhoneNo(uCenterMemberDto.getPhoneNo());
        ucenterUserDto.setCreateTime(uCenterMemberDto.getCreateTime());
        UCenterUserVo vo = new UCenterUserVo();
        try {
            BeanUtils.copyProperties(vo, ucenterUserDto);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return vo;
    }

    @Override
    public void updateUCenterUser(UCenterUserVo UCenterUserVo) {
        UCenterUserDto UCenterUserDto = new UCenterUserDto();
        try {
            BeanUtils.copyProperties(UCenterUserDto, UCenterUserVo);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        UCenterUserDto UCenterUserDto1 = UCenterUserMng.getUCenterUserById(UCenterUserVo.getMemberId());
        if (UCenterUserDto1 == null)
            return;
        UCenterUserDto.setCreateTime(UCenterUserDto1.getCreateTime());
        UCenterUserDto.setLastModifyTime(TimeUtils.getDateToTimestamp());
        UCenterUserMng.updateUCenterUser(UCenterUserDto);
        UCenterMemberDto UCenterMemberDto = UCenterMemberMng.getUCenterMemberById(UCenterUserVo.getMemberId());
        // 在member表里更新哪些属性，取决于前端表单列示了哪些属性,
        UCenterMemberDto.setNickName(UCenterUserVo.getNickName());
        UCenterMemberDto.setPhoneNo(UCenterUserVo.getPhoneNo());
        UCenterMemberDto.setEmail(UCenterUserVo.getEmail());
        UCenterMemberDto.setStatus(UCenterUserVo.getStatus());
        UCenterMemberDto.setLastModifyTime(TimeUtils.getDateToTimestamp());
        if (UCenterUserVo.getMembername() != null)
            UCenterMemberDto.setMemberName(UCenterUserVo.getMembername());
        UCenterMemberMng.updateUCenterMember(UCenterMemberDto);
    }

    @Override
    public void deleteUCenterUser(UCenterUserVo UCenterUserVo) {
        String ids = UCenterUserVo.getAllIDCheck();
        if (StringUtils.isBlank(ids)) {
            UCenterUserMng.deleteUCenterUserById(UCenterUserVo.getMemberId());
            return;
        }
        ids.substring(0, ids.length() - 1);
        String[] mIdArray = ids.split(",");
        if (mIdArray.length == 0) {
            return;
        }
        for (int i = 0; i < mIdArray.length; i++) {
            if (StringUtils.isNumeric(mIdArray[i])) {
                UCenterUserMng.deleteUCenterUserById(Long.parseLong(mIdArray[i]));
            }
        }
    }

    @Override
    public void resetPassword(UCenterUserVo UCenterUserVo) {
        String memberIdsStr = UCenterUserVo.getAllIDCheck();
        if (StringUtils.isBlank(memberIdsStr)) {
            UCenterMemberDto UCenterMemberDto = UCenterMemberMng.getUCenterMemberById(UCenterUserVo.getMemberId());
            if (UCenterMemberDto == null)
                return;
            UCenterMemberDto.setPassword(new Md5PwdEncoder().encodePassword(UCenterUserVo.getPassword()));
            UCenterMemberMng.updateUCenterMember(UCenterMemberDto);
        }
        memberIdsStr.substring(0, memberIdsStr.length() - 1);
        String[] mIdArray = memberIdsStr.split(",");
        if (mIdArray.length == 0) {
            return;
        }
        for (int i = 0; i < mIdArray.length; i++) {
            if (StringUtils.isNumeric(mIdArray[i])) {
                UCenterMemberDto UCenterMemberDto = UCenterMemberMng.getUCenterMemberById(Long.parseLong(mIdArray[i]));
                if (UCenterMemberDto == null)
                    return;
                UCenterMemberDto.setPassword(new Md5PwdEncoder().encodePassword(UCenterUserVo.getPassword()));
                UCenterMemberMng.updateUCenterMember(UCenterMemberDto);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public R saveUCenterUser(UCenterUserVo vo) throws InvocationTargetException, IllegalAccessException {
        UCenterMemberDto ucenterMember = ucenterMemberService.getUcenterMemberByPhoneNumber(vo.getPhoneNo());
        UCenterUserDto UCenterUser = new UCenterUserDto();
        R r = R.ok();
        // 会员为空，则先在会员里插入，再插入admin表
        if (ucenterMember == null) {
            UCenterMemberDto memberdto = new UCenterMemberDto();
            BeanUtils.copyProperties(memberdto, vo);
            UCenterMemberDto UCenterMemberDto = ucenterMemberService.saveMember(memberdto);
            UCenterUser.setMemberId(UCenterMemberDto.getMemberId());
            UCenterUser.setSiteId(UCenterMemberDto.getSiteId());
            r.put("message", "该手机号已经存在，前台会员转管理员！");
        } else {
            // 再查一下数据里UCenterUser是否已经存在
            if (UCenterUserMng.getUCenterUserById(ucenterMember.getMemberId()) != null) {
                return R.error(400, "该管理员已经存在(手机号已经被注册！)");
            }
            UCenterUser.setMemberId(ucenterMember.getMemberId());
            UCenterUser.setSiteId(vo.getSiteId());
            // 后台管理员的几个字段也更新到member表里
            if (org.springframework.util.StringUtils.hasText(vo.getEmail()))
                ucenterMember.setEmail(vo.getEmail());
            if (org.springframework.util.StringUtils.hasText(vo.getNickName()))
                ucenterMember.setNickName(vo.getNickName());
            if (org.springframework.util.StringUtils.hasText(vo.getMembername()))
                ucenterMember.setMemberName(vo.getMembername());
            UCenterMemberDto UCenterMemberDto = new UCenterMemberDto();
            BeanUtils.copyProperties(UCenterMemberDto, ucenterMember);
            UCenterMemberMng.updateUCenterMember(UCenterMemberDto);
        }
        UCenterUser.setStatus(vo.getStatus());
        UCenterUser.setRealName(vo.getRealName());
        UCenterUser.setPseudonym(vo.getPseudonym());
        UCenterUser.setLastModifyTime(TimeUtils.getDateToTimestamp());
        UCenterUser.setCreateTime(TimeUtils.getDateToTimestamp());
        UCenterUserDto newUCenterUserDto = UCenterUserMng.saveUCenterUser(UCenterUser);
        r.put("message", "用户添加成功！");
        return r;

    }

}
*/