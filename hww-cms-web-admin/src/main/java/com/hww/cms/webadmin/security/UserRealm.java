package com.hww.cms.webadmin.security;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;

import com.hww.cms.webadmin.service.SysAuthService;
import com.hww.framework.web.Constants;
import com.hww.sys.api.SysFeignClient;
import com.hww.sys.common.dto.SysMenuDto;
import com.hww.sys.common.dto.SysRoleDto;
import com.hww.sys.common.dto.SysUserDto;

/**
 * 用户授权信息域
 * 
 * @author coderhuang
 * 
 */
public class UserRealm extends CasRealm {
	
	private static Log log = LogFactory.getLog(UserRealm.class);
	
	@Resource
	private SysAuthService sysAuthService;

	protected final Map<String, SimpleAuthorizationInfo> roles = new ConcurrentHashMap<String, SimpleAuthorizationInfo>();
	
	/**
	 * 设置角色和权限信息
	 * 此方法调用 hasRole,hasPermission的时候才会进行回调.
	 * 
	 * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		log.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 获取单点登陆后的用户名，也可以从session中获取，因为在认证成功后，已经将用户名放到session中去了
		String account = (String) principals.getPrimaryPrincipal();
		SysUserDto userInfo = sysAuthService.findUserByName(account);
//		//加载角色下菜单权限
//		//List<SysRoleDto> roleDto= sysFeignClient.findRoleByUserId(user.getUserId());
//		authorizationInfo.addRole(userInfo.getDefaultRole().toString());
//		List<SysMenuDto> sysMenulist=sysFeignClient.findByRole(userInfo.getDefaultRole());
		/*for(SysMenuDto s:sysMenulist) {
			authorizationInfo.addStringPermission(s.getUrl());
		}*/
		
		
		
		//加载所有角色
		List<Long> roleIdList=userInfo.getRoleIdList();
		if(roleIdList!=null&&!roleIdList.isEmpty()) {
			authorizationInfo.addRoles(roleIdList.stream().map(val->String.valueOf(val)).collect(Collectors.toList()));
		}
		if(userInfo.getDefaultRole()!=null) {
			authorizationInfo.addRole(userInfo.getDefaultRole().toString());
		}
		
		//加载菜单（权限）
		if(roleIdList!=null&&!roleIdList.isEmpty()) {
			roleIdList.forEach(roleId->{
				List<SysMenuDto> sysMenulist=sysAuthService.findsMenusByRoles(roleId);
				for(SysMenuDto s:sysMenulist) {
					authorizationInfo.addStringPermission(s.getUrl());
				}
			});
		}
		if(userInfo.getDefaultRole()!=null) {
			List<SysMenuDto> sysMenulist=sysAuthService.findsMenusByRoles(userInfo.getDefaultRole());
			for(SysMenuDto s:sysMenulist) {
				authorizationInfo.addStringPermission(s.getUrl());
			}
		}
		
		List<SysMenuDto> sysMenulist=sysAuthService.findsMenusByRoles(userInfo.getDefaultRole());
		for(SysMenuDto s:sysMenulist) {
			authorizationInfo.addStringPermission(s.getUrl());
		}
		
		//admin管理员加上所有的权限
				if(userInfo.getIsAdmin()==1){
					authorizationInfo.addStringPermission("*");
				}
				authorizationInfo.addStringPermission("/console.do");
//		authorizationInfo.addStringPermission("*");
		roles.put(account, authorizationInfo);
		return authorizationInfo;
	}	
	/**
	 * 1、CAS认证 ,验证用户身份 
	 * 2、将用户基本信息设置到会话中,方便获取
	 * 3、该方法可以直接使用CasRealm中的认证方法，此处仅用作测试
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

		CasToken casToken=(CasToken) token;
		// 调用父类中的认证方法，CasRealm已经为我们实现了单点认证。
		AuthenticationInfo authc = super.doGetAuthenticationInfo(casToken);
		// 获取登录的账号，cas认证成功后，会将账号存起来
		String account = (String) authc.getPrincipals().getPrimaryPrincipal();
//		SysUserDto user = userService.findUserByNameD(account);
		SysUserDto user = sysAuthService.findUserByName(account);
		
		// 将用户信息存入session中,方便程序获取,此处可以将根据登录账号查询出的用户信息放到session中
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER, user);

		return authc;
	}
	 
	//清除缓存
	public void clearAuthz(){  
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());  
    }  


	

}
