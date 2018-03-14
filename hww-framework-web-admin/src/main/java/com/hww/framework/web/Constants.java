package com.hww.framework.web;

/**
 * web常量
 */
public abstract class Constants
{
    /**
     * 路径分隔符
     */
    public static final String SPT = "/";
    /**
     * 索引页
     */
    public static final String INDEX = "index";
    /**
     * 默认模板
     */
    public static final String DEFAULT = "default";
    /**
     * UTF-8编码
     */
    public static final String UTF8 = "UTF-8";
    /**
     * 提示信息
     */
    public static final String MESSAGE = "message";
    /**
     * cookie中的JSESSIONID名称
     */
    public static final String JSESSION_COOKIE = "JSESSIONID";
    /**
     * url中的jsessionid名称
     */
    public static final String JSESSION_URL = "jsessionid";
    /**
     * HTTP POST请求
     */
    public static final String POST = "POST";
    /**
     * HTTP GET请求
     */
    public static final String GET = "GET";
    /**
     * 类--错误国际化信息配置
     */
    public static String CLASS_ERROR_CODE = "/WEB-INF/config/classes-error.properties";
    
    /**
     * 用户session
     */
    public static final String SESSION_USER = "login_user";

    public static final String SESSION_SITE = "current_site";

    public static final String SITE_PARAM = "_site_id_param";
    public static final String SITE_COOKIE = "_site_id_cookie";
}
