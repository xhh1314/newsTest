package com.hww.sys.webservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.sys.common.dto.SysSiteDto;
import com.hww.sys.webservice.service.SysSiteService;

@Controller
@RequestMapping("/sys/site")
public class SysSiteController{
	@Autowired
	private SysSiteService sysSiteService;
	@RequestMapping("find_site.do")
	@ResponseBody
	public SysSiteDto findSiteById(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="siteId") Integer siteId) {
		SysSiteDto sysSIteDto=sysSiteService.findSiteById(siteId);
		return sysSIteDto;
	}

	@RequestMapping("find_sitelist.do")
	@ResponseBody
	public List<SysSiteDto> findSitelist(HttpServletRequest request, HttpServletResponse response, @RequestBody SysSiteDto sysSiteDto) {
		List<SysSiteDto> p=	sysSiteService.list(sysSiteDto, 1, 1);
		return p;
	}
}
