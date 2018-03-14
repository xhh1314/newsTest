package com.hww.sys.common.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sys.common.dao.SysUserRRoleDao;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.common.entity.SysUserRRole;

@Service("sysUserRRoleMng")
public interface SysUserRRoleMng extends IBaseEntityMng<Long, SysUserRRole, SysUserRRoleDao> {

	public List<SysRole> queryByUserId(Long userId);

	public List<SysUserRRole> findByUserId(Long userId);

}
