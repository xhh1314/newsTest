package com.hww.sys.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.common.entity.SysUserRRole;

public interface SysUserRRoleDao extends IBaseEntityDao<Long, SysUserRRole> {
	
	List<SysRole> queryByUserId(Long userId);
	
	List<SysUserRRole> findByUserId(Long userId);

}
