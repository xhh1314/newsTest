package com.hww.sys.common.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hww.sys.common.dao.SysRoleDao;
import com.hww.sys.common.entity.SysRole;

@Repository("sysRoleDao")
public class SysRoleDaoImpl extends LocalEntityDaoImpl<Long, SysRole> implements SysRoleDao {


}
