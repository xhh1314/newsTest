package com.hww.cms.webadmin.security;

//import static com.hww.sys.webadmin.web.Constants.SESSION_SITE;
//import static com.hww.sys.webadmin.web.Constants.SESSION_USER;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.util.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.hww.sys.common.dto.SysSiteDto;
//import com.hww.sys.common.dto.SysUserDto;
//import com.hww.sys.webadmin.service.SysSiteService;
//import com.hww.sys.webadmin.service.SysUserService;
//import com.hww.sys.webadmin.web.SysThreadVariable;
//
//public class SysAuthorizingRealm
//	extends
//		AuthorizingRealm {
//	private static final Logger log = LoggerFactory.getLogger(SysAuthorizingRealm.class);
//
//	@Autowired
//	SysUserService sysUserService;
//	@Autowired
//	SysSiteService sysSiteService;
//
//	/**
//	 * 授权
//	 */
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		// TODO Auto-generated method stub
//		
//		String username = (String) principals.getPrimaryPrincipal();
//		SysUserDto userDto = sysUserService.findUserByName(username);
//		SysSiteDto siteDto = SysThreadVariable.getSite();// site可能为空
//		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
//		if (userDto != null) {
//			Set<String> viewPermissionSet = new HashSet<String>();
//			Set<String> perms = userDto.getPerms(userDto.getSiteId(), viewPermissionSet);
//			if (!CollectionUtils.isEmpty(perms)) {
//				// 权限加入AuthorizationInfo认证对象
//				auth.setStringPermissions(perms);
//			}
//		}
//		return auth;
//	}
//
//	/**
//	 * 登录认证
//	 */
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
//			throws AuthenticationException {
//		// TODO Auto-generated method stub
//		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//
//		SysUserDto userDto = sysUserService.findUserByName(token.getUsername());
//
//		if (userDto != null) {
//			log.info("登录验证数据库中username:" + userDto.getUsername());
//			if (userDto.getStatus().shortValue() == -1) {
//				return null;
//			}
//			Subject currentUser = SecurityUtils.getSubject();
//			Session session = currentUser.getSession();
//			session.setAttribute(SESSION_USER, userDto);
//			// user.getSysSite().getSiteName();
//			SysSiteDto siteDto = sysSiteService.findSiteById(userDto.getSiteId());
//			session.setAttribute(SESSION_SITE, siteDto);
//			return new SimpleAuthenticationInfo(userDto.getUsername(), userDto.getPassword(), getName());
//		} else {
//			return null;
//		}
//	}
//
//}
