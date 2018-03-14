package com.hww.framework.web.cas;

//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.cas.CasFilter;
//import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
//import org.apache.shiro.web.util.WebUtils;
//
//public class PortalCasFilter extends CasFilter {
//
//    @Override
//    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
//    	//前端页面在请求的时候在header中带上请求这个接口的url。这样便将登录成功后需要跳转的地址绑定到了对应的Subject对象中。以便于在登录以后跳转到这个页面
//        String successUrl = ((ShiroHttpServletRequest) request).getHeader("page-url");
//        if (StringUtils.isBlank(successUrl)) {
//            WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
//        } else {
//            WebUtils.redirectToSavedRequest(request, response, successUrl);
//        }
//    }
//}
