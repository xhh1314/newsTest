package com.hww.sys.webservice.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.PwdEncoder;
import com.hww.base.util.R;
import com.hww.base.util.StringUtils;
import com.hww.sys.common.dto.SysUserDto;
import com.hww.sys.common.entity.SysGroup;
import com.hww.sys.common.entity.SysRole;
import com.hww.sys.common.entity.SysUser;
import com.hww.sys.common.entity.SysUserRRole;
import com.hww.sys.common.manager.SysGroupMng;
import com.hww.sys.common.manager.SysRoleMng;
import com.hww.sys.common.manager.SysUserMng;
import com.hww.sys.common.manager.SysUserRRoleMng;
import com.hww.sys.webservice.service.SysUserService;


@Service("sysUserService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	SysUserMng sysUserMng;
	@Autowired
	PwdEncoder pwdEncoder;
	@Resource
	SysRoleMng sysRoleMng;
	@Resource
	SysGroupMng sysGroupMng;
	@Resource
	SysUserRRoleMng sysUserRRoleMng;

	@Override
	public Pagination page(SysUserDto searchDto, Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		Finder hql = Finder.create("from ");
		hql.append(SysUser.class.getName());
		hql.append(" where 1=1");
		if (searchDto.getSiteId() != null) {
			hql.append(" and siteId=:siteIdP").setParam("siteIdP", searchDto.getSiteId());
		}
		if (StringUtils.isNotBlank(searchDto.getUsername())) {
			hql.append(" and username like :userNameP").setParam("userNameP", "%" + searchDto.getUsername() + "%");
		}
		if (searchDto.getStatus() != null) {
			hql.append(" and status=:statusP").setParam("statusP", searchDto.getStatus());
		} else {
			hql.append(" and status>0");
		}

		Pagination p = sysUserMng.find(hql, pageNo, pageSize);

		if (p != null && p.getList() != null) {
			List<SysUserDto> userDtoList = BeanMapper.mapList(p.getList(), SysUserDto.class);
			p.setList(userDtoList);
		}

		return p;
	}

	@Override
	public SysUserDto findUserById(Long id) {
		// TODO Auto-generated method stub
		SysUser entity = sysUserMng.find(id);
		SysUserDto dto = BeanMapper.map(entity, SysUserDto.class);
		return dto;
	}

	@Override
	public SysUserDto findUserByName(String username) {
		// TODO Auto-generated method stub
		SysUser entity = sysUserMng.findUserByUserName(username);
		if(entity==null) {
			return null;
		}
		
		SysUserDto dto = BeanMapper.map(entity, SysUserDto.class);
		List<SysUserRRole> userRoleList=  sysUserRRoleMng.findByUserId(entity.getUserId());
		
		if(userRoleList!=null&&!userRoleList.isEmpty()) {
			List<Long> roleIdList=userRoleList.stream().map(val->val.getRoleId()).collect(Collectors.toList());
			dto.setRoleIdList(roleIdList);
			
			String roleIds= String.join(",", roleIdList.stream().map(val->String.valueOf(val)).collect(Collectors.toList()));
			dto.setRoleIds(roleIds);
		}
		
		
		return dto;
	}

	@Override
	public SysUserDto findUserWithGroupById(Long id) {
		// TODO Auto-generated method stub
		SysUser entity = sysUserMng.find(id);
		StringBuffer groupIds = new StringBuffer();
		for (SysGroup group : entity.getSysGroups()) {
			groupIds.append(group.getGroupId());
			groupIds.append(",");
		}
		SysUserDto dto = BeanMapper.map(entity, SysUserDto.class);
		dto.setGroupIds(groupIds.toString());
		return dto;
	}

	@Override
	public SysUserDto findUserWithRoleById(Long id) {
		// TODO Auto-generated method stub
		SysUser entity = sysUserMng.find(id);
		StringBuffer roleIds = new StringBuffer();
		for (SysRole role : entity.getSysRoles()) {
			roleIds.append(role.getRoleId());
			roleIds.append(",");
		}
		SysUserDto dto = BeanMapper.map(entity, SysUserDto.class);
		dto.setRoleIds(roleIds.toString());
		return dto;
	}
	
	@Override
	@Transactional
	public void saveUser(SysUserDto dto) {
		// TODO Auto-generated method stub
		SysUser entity = BeanMapper.map(dto, SysUser.class);
		if (StringUtils.isNotBlank(dto.getPassword())) {
			entity.setPassword(pwdEncoder.encodePassword(dto.getPassword()));
		} else {
			entity.setPassword("");
		}
		if(dto.getIsSelfAdmin()==null) {
			entity.setIsSelfAdmin(Short.parseShort("0"));
		}
		if(dto.getIsAdmin()==null) {
			entity.setIsAdmin(Short.parseShort("0"));
		}
		if(dto.getUsername()==null) {
			entity.setUsername("");
		}
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setLastModifyTime(new Timestamp(System.currentTimeMillis())); 
		sysUserMng.save(entity);
	}
	

	@Override
	public void updateUser(SysUserDto dto) {
		// TODO Auto-generated method stub
		SysUser entity = sysUserMng.find(dto.getUserId());
		try {
			BeanUtils.copyProperty(entity, "", dto);
			if (StringUtils.isNotBlank(dto.getPassword())) {
				entity.setPassword(pwdEncoder.encodePassword(dto.getPassword()));
			}
			if (dto.getIsAdmin() == null) {
				entity.setIsAdmin((short) 0);
			}

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (StringUtils.isNotBlank(dto.getRoleIds())) {
			List<SysRole> roles = null;
			String[] roleIdStrArray = dto.getRoleIds().split(",");
			Object[] roleIdIntArray = new Object[roleIdStrArray.length];
			// Collection<Object> roleIdIntArray = new
			// ArrayList<Object>(roleIdStrArray.length);
			for (int i = 0; i < roleIdStrArray.length; i++) {
				if (StringUtils.isNumeric(roleIdStrArray[i])) {
					roleIdIntArray[i] = (Integer.parseInt(roleIdStrArray[i]));
				}
			}
			roles = sysRoleMng.find("roleId", roleIdIntArray);
			entity.setSysRoles(roles);
		}

		if (StringUtils.isNotBlank(dto.getGroupIds())) {
			List<SysGroup> groups = null;
			String[] groupIdStrArray = dto.getGroupIds().split(",");
			// Collection<Object> groupIdIntArray = new
			// ArrayList<Object>(groupIdStrArray.length);
			Object[] groupIdIntArray = new Object[groupIdStrArray.length];
			for (int i = 0; i < groupIdStrArray.length; i++) {
				if (StringUtils.isNumeric(groupIdStrArray[i])) {
					groupIdIntArray[i] = (Integer.parseInt(groupIdStrArray[i]));
				}
			}
			groups = sysGroupMng.find("groupId", groupIdIntArray);
			entity.setSysGroups(groups);
		}
		sysUserMng.update(entity);
	}

	@Override
	public void updateUserStatusByIds(Short status, Collection<Long> userIds) {
		// TODO Auto-generated method stub
		sysUserMng.updateStatusByProperty(status, "userId", userIds);
	}

	@Override
	public List<SysUserDto> queryUserList() {
				Finder hql = Finder.create("from ");
				hql.append(SysUser.class.getName());
				hql.append(" where 1=1");
				hql.append(" and isAdmin=:isAdmin").setParam("isAdmin", Short.valueOf("1"));
				List<SysUser> sysUserlist= (List<SysUser>) sysUserMng.find(hql);
				List<SysUserDto> dtolist=	BeanMapper.mapList(sysUserlist, SysUserDto.class);
		       return dtolist;
	}

	@Override
	public void delUser(SysUserDto dto) {
		try {
			SysUser user=sysUserMng.find(dto.getUserId());
			if(user!=null) {
			sysUserMng.delete(user);
			}
		} catch (Exception e) {
			e.getMessage();	
		}
	}

	@Override
	public R updateUserDefaultRole(Long userId,Long roleId) {
		Finder f=Finder.create("update SysUser  ");
		f.append("set defaultRole=:defaultRole");
		f.append(" where userId=:userId");
		f.setParam("defaultRole", roleId);
		f.setParam("userId", userId);
		R r=null;
		try {
			sysUserMng.update(f);
			r.isOk();
		} catch (Exception e) {
			r.error("更换角色失败");
		}
		return r;
	}

	@Override
	public R updateUserPassword(SysUserDto dto) {
		Finder f=Finder.create("update SysUser");
		f.append("set password=:password");
		f.append(" where userName=:userName ");
		f.setParam("password", dto.getPassword());
		f.setParam("userId", dto.getUsername());
		R r=null;
		try {
			sysUserMng.update(f);
			r.isOk();
		} catch (Exception e) {
			r.error("更改密码失败！");
		}
		return r;
	}

}
