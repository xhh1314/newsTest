/*package com.hww.ucenter.common.manager.impl;

import com.hww.base.common.listener.IModifyListener;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.StringUtils;
import com.hww.ucenter.common.dao.UCenterUserDao;
import com.hww.ucenter.common.dto.UCenterUserDto;
import com.hww.ucenter.common.entity.UCenterUser;
import com.hww.ucenter.common.manager.UCenterUserMng;
import com.hww.ucenter.common.vo.UCenterUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@Transactional
public class UCenterUserMngImpl extends BaseEntityMngImpl<Long, UCenterUser, UCenterUserDao> implements UCenterUserMng {

	@Autowired
    private UCenterUserDao UCenterUserDao;

	@Autowired
    public void setUcenterAdminDao(UCenterUserDao sysMemberDao) {
		super.setEntityDao(sysMemberDao);
        this.UCenterUserDao = sysMemberDao;
	}

    private List<IModifyListener<UCenterUser>> listenerList;

    public void setListenerList(List<IModifyListener<UCenterUser>> listenerList) {
		this.listenerList = listenerList;
	}

	@Override
    public UCenterUserDto getUCenterUserById(Long memberId) {
        UCenterUser UCenterUser = UCenterUserDao.getUCenterUserById(memberId);
        if (UCenterUser == null)
			return null;
        UCenterUserDto UCenterUserDto = new UCenterUserDto();
		try {
            BeanUtils.copyProperties(UCenterUserDto, UCenterUser);
		} catch (InvocationTargetException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return UCenterUserDto;
	}

	@Override
    public Pagination query(UCenterUserDto sysMemberDto) {
        Finder hql = Finder.create("from UCenterUser where 1=1");
		if (sysMemberDto.getMemberId() != null) {
			hql.append(" and memberId=:memberId");
			hql.setParam("memberId", sysMemberDto.getMemberId());
		}
		if (StringUtils.isNotBlank(sysMemberDto.getRealName())) {
			hql.append(" and realName=:realName");
			hql.setParam("realName", sysMemberDto.getRealName());
		}
		if (StringUtils.isNotBlank(sysMemberDto.getPhoneNo())) {
			hql.append(" and phoneNo=:phoneNo");
			hql.setParam("phoneNo", sysMemberDto.getPhoneNo());
		}
		if (sysMemberDto.getStatus() != null) {
			hql.append(" and status=:status");
			hql.setParam("status", sysMemberDto.getStatus());
		}
		hql.append(" order by createTime desc");
        Pagination p = UCenterUserDao.find(hql, sysMemberDto.getPageNo(), sysMemberDto.getPageSize());
		if (p != null && p.getList() != null) {
			p.setPageNo(sysMemberDto.getPageNo());
			p.setPageSize(sysMemberDto.getPageSize());
            List<UCenterUserVo> memberDtoList = BeanMapper.mapList(p.getList(), UCenterUserVo.class);
			p.setList(memberDtoList);
		}
		return p;
	}

	@Override
    public void updateUCenterUser(UCenterUserDto UCenterUserDto) {
        UCenterUser UCenterUser = find(UCenterUserDto.getMemberId());
        org.springframework.beans.BeanUtils.copyProperties(UCenterUserDto, UCenterUser);
        update(UCenterUser);
	}

	@Override
    public Pagination listUCenterUserBySqlAndPage(UCenterUserDto UCenterUserDto) {
        return UCenterUserDao.listUCenterUserBySqlAndPage(UCenterUserDto);
	}

	@Override
    public UCenterUserDto saveUCenterUser(UCenterUserDto UCenterUserDto) {
        UCenterUser UCenterUser = new UCenterUser();
        UCenterUser.setSiteId(UCenterUserDto.getSiteId());
        UCenterUser.setMemberId(UCenterUserDto.getMemberId());
        UCenterUser.setRealName(UCenterUserDto.getRealName());
        UCenterUser.setPseudonym(UCenterUserDto.getPseudonym());
        UCenterUser.setLastModifyTime(UCenterUserDto.getLastModifyTime());
        UCenterUser.setCreateTime(UCenterUserDto.getCreateTime());
        UCenterUser.setStatus(UCenterUserDto.getStatus());
        UCenterUser newUCenterUser = UCenterUserDao.save(UCenterUser);
		try {
            BeanUtils.copyProperties(UCenterUserDto, newUCenterUser);
		} catch (InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
        return UCenterUserDto;
	}

	@Override
    public void deleteUCenterUserById(Long memberId) {
        UCenterUser UCenterUser = UCenterUserDao.getUCenterUserById(memberId);
        if (UCenterUser != null)
            UCenterUserDao.delete(UCenterUser);
	}

}
*/