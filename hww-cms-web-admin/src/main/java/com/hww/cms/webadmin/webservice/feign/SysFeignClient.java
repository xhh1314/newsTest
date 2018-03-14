//package com.hww.cms.webadmin.webservice.feign;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.hww.base.util.R;
//import com.hww.cms.webadmin.webservice.feign.fallback.SysFeignClientFallback;
//import com.hww.sys.common.dto.SysMenuDto;
//import com.hww.sys.common.dto.SysModuleDto;
//import com.hww.sys.common.dto.SysRoleDto;
//import com.hww.sys.common.dto.SysSiteDto;
//import com.hww.sys.common.dto.SysUserDto;
//
///**
// * cms调用sys系统
// * 
// * @author hewei url = "http://localhost:7900"
// */
//@FeignClient(name = "hww-sys-web-service-consumer")
//public interface SysFeignClient {
//	@RequestMapping(value = "sys/role/v_all_list.do", method = RequestMethod.POST)
//	@ResponseBody
//	public List<SysRoleDto> allList();
//
//	@RequestMapping(value = "/sys/user/find_user.do")
//	SysUserDto findUser(@RequestParam(value = "username") String username);
//
//	@RequestMapping(value = "/sys/site/find_site.do")
//	SysSiteDto findSiteById(@RequestParam(value = "siteId") Integer siteId);
//
//	@RequestMapping(value = "/sys/site/find_sitelist.do")
//	List<SysSiteDto> findSitelist(@RequestBody SysSiteDto sysSiteDto);
//
//	@RequestMapping(value = "/sys/role/menu_list.do")
//	List<SysMenuDto> findByRole(@RequestParam(value = "roleId") Long roleId);
//	
//	@RequestMapping(value="/sys/user/updateUserPassword.do")
//	public R updateUserPassword(@RequestBody SysUserDto dto);
//	
//	
//	@RequestMapping("/sys/user/updateUserByAdmin.do")
//	@ResponseBody
//	public R updateUserByAdmin(@RequestBody SysUserDto dto);
//	
//	@RequestMapping("/sys/role/roleListByUserId.do")
//	@ResponseBody
//	public List<SysRoleDto> findRoleByUserId( @RequestParam(value="userId") Long usereId);
//	
//	
//	@RequestMapping("/module/list.do")
//	@ResponseBody
//	public List<SysModuleDto> list();
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//		
//}
