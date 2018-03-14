package com.hww.sys.common.util;

import javax.servlet.http.HttpServletRequest;

import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.common.dto.SysUserDto;

/**
 * 提供一些CMS系统中使用到的共用方法
 * 
 * 比如获得会员信息,获得后台站点信息
 */
public class SysUtils
{
    /**
     * 用户KEY
     */
    public static final String USER_KEY = "_user_key";
    /**
     * 站点KEY
     */
    public static final String SITE_KEY = "_site_key";

    /**
     * 获得用户
     * 
     * @param request
     * @return
     */
    public static SysUserDto getUser(HttpServletRequest request)
    {
        return (SysUserDto) request.getAttribute(USER_KEY);
    }

    /**
     * 获得用户ID
     * 
     * @param request
     * @return
     */
    public static Long getUserId(HttpServletRequest request)
    {
        SysUserDto user = getUser(request);
        if (user != null)
        {
            return user.getUserId();
        } else
        {
            return null;
        }
    }
    /**
     * 设置用户
     * 
     * @param request
     * @param user
     */
    public static void setUser(HttpServletRequest request, SysUserDto user)
    {
        request.setAttribute(USER_KEY, user);
    }

    /**
     * 获得站点
     * 
     * @param request
     * @return
     */
    public static SysSiteDto getSite(HttpServletRequest request)
    {
        return (SysSiteDto) request.getAttribute(SITE_KEY);
    }

    /**
     * 设置站点
     * 
     * @param request
     * @param site
     */
    public static void setSite(HttpServletRequest request, SysSiteDto site)
    {
        request.setAttribute(SITE_KEY, site);
    }

    /**
     * 获得站点ID
     * 
     * @param request
     * @return
     */
    public static Integer getSiteId(HttpServletRequest request)
    {
        return getSite(request).getSiteId();
    }

    public static String getUrl(HttpServletRequest request)
    {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path + "/";
        return basePath;
    }
}
