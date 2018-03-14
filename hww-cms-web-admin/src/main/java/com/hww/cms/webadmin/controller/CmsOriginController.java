package com.hww.cms.webadmin.controller;

import com.hww.base.common.page.Pagination;
import com.hww.base.util.SnowFlake;
import com.hww.base.util.StringUtils;
import com.hww.base.util.TimeUtils;
import com.hww.cms.common.dto.CmsOriginDto;
import com.hww.cms.common.entity.CmsOrigin;
import com.hww.cms.common.vo.CmsOriginVo;
import com.hww.cms.webadmin.service.CmsOriginService;
import com.hww.sys.common.util.SysUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/origin")
public class CmsOriginController {

    @Autowired
    private CmsOriginService cmsOriginService;

    @RequestMapping(value = "list.do")
    public String list(CmsOriginVo vo, ModelMap map) {
        CmsOriginDto dto = buildCmsOriginDto(vo);
        Pagination p = cmsOriginService.list(dto);
        map.addAttribute("page", p);
        map.addAttribute("form", vo);
        return "origin/list";
    }

    private CmsOriginDto buildCmsOriginDto(CmsOriginVo vo) {
        CmsOriginDto dto = new CmsOriginDto();
        if (StringUtils.isNotBlank(vo.getOriginName())) {
            dto.setOriginName(vo.getOriginName());
        }
        if (StringUtils.isNotBlank(vo.getOriginUrl())) {
            dto.setOriginUrl(vo.getOriginUrl());
        }
        if (StringUtils.isNotBlank(vo.getLink())) {
            dto.setLink(vo.getLink());
        }
        if (vo.getStatus() != null) {
            dto.setStatus(vo.getStatus());
        }
        dto.setPageNo(vo.getPageNo());
        dto.setPageSize(vo.getPageSize());
        return dto;
    }

    @RequestMapping(value = "save.do")
    @ResponseBody
    public Boolean save(HttpServletRequest request, CmsOriginVo vo) {
        Integer siteId = SysUtils.getSiteId(request);
        CmsOriginDto dto = buildCmsOriginDtoForSave(siteId, vo);
        cmsOriginService.saveOrigin(dto);
        return true;
    }

    @RequestMapping("edit.do")
    public String edit(CmsOriginVo vo, ModelMap model) {
        CmsOrigin entity = cmsOriginService.findById(vo.getOriginId());
        model.addAttribute("entity", entity);
        return "origin/add";
    }

    @RequestMapping(value = "update.do")
    @ResponseBody
    public Boolean update(CmsOriginVo vo) {
        CmsOriginDto dto = buildCmsOriginDtoForUpdate(vo);
        cmsOriginService.updateOrigin(dto);
        return true;
    }

    @RequestMapping(value = "delete.do")
    @ResponseBody
    public Boolean delete(CmsOriginVo vo) {
        CmsOrigin cmsOrigin = cmsOriginService.findById(vo.getOriginId());
        cmsOriginService.deleteOrigin(cmsOrigin);
        return true;
    }

    private CmsOriginDto buildCmsOriginDtoForUpdate(CmsOriginVo vo) {
        CmsOriginDto dto = new CmsOriginDto();
        dto.setOriginId(vo.getOriginId());
        dto.setOriginName(vo.getOriginName());
        dto.setOriginUrl(vo.getOriginUrl());
        dto.setLink(vo.getLink());
        dto.setStatus(vo.getStatus());
        dto.setWord(vo.getWord());
        dto.setIcon(vo.getIcon());
        dto.setLastModifyTime(TimeUtils.getDateToTimestamp());
        return dto;
    }

    private CmsOriginDto buildCmsOriginDtoForSave(Integer siteId, CmsOriginVo vo) {
        CmsOriginDto dto = new CmsOriginDto();
        SnowFlake snowFlake = new SnowFlake(1,1);
        dto.setSiteId(siteId);
        dto.setOriginId(snowFlake.nextId());
        dto.setOriginName(vo.getOriginName());
        dto.setOriginUrl(vo.getOriginUrl());
        dto.setLink(vo.getLink());
        dto.setStatus(vo.getStatus());
        dto.setWord(vo.getWord());
        dto.setIcon(vo.getIcon());
        return dto;
    }
}
