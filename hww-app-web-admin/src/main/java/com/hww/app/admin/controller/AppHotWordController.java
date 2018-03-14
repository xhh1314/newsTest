package com.hww.app.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.hww.app.admin.service.AppHotWordService;
import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.vo.AppHotWordVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanMapper;

import com.hww.sys.common.util.SysUtils;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "hotword")
public class AppHotWordController {

    @Autowired
    private AppHotWordService appHotWordService;

    @RequestMapping(value = "list.do")
    public String list(HttpServletRequest request, AppHotWordVo vo, ModelMap modelMap) {
        Integer siteId = SysUtils.getSiteId(request);
        AppHotWordDto searchDto = BeanMapper.map(vo, AppHotWordDto.class);
        searchDto.setSiteId(siteId);
        Pagination p = appHotWordService.list(searchDto, vo.getPageNo(), 10);
        if (p != null && p.getList() != null) {
            List<AppHotWordVo> appHotWordVoList = BeanMapper.mapList(p.getList(), AppHotWordVo.class);
            p.setList(appHotWordVoList);
        }
        modelMap.addAttribute("page", p);
        modelMap.addAttribute("form", vo);
        return "hotword/config";
    }

    @RequestMapping(value = "save.do")
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response, Session session, AppHotWordVo vo) {
       // Integer siteId = SiteIdUtil.getSiteId(request, response, session).getSiteId();
        Integer siteId=1;
        AppHotWordDto appHotWordDto = BeanMapper.map(vo, AppHotWordDto.class);
        appHotWordDto.setSiteId(siteId);
        boolean flag = appHotWordService.queryHotWordByHotWordAndType(appHotWordDto);
        if (flag) {
            return JSONObject.toJSONString("同一类型的热词不能重复保存！");
        }
        appHotWordService.saveHotWord(appHotWordDto);
        return JSONObject.toJSONString("保存成功");
    }

    @RequestMapping(value = "edit.do")
    @ResponseBody
    public AppHotWord edit(AppHotWordVo vo) {
        AppHotWord appHotWord = appHotWordService.queryAppHotWordById(vo.getHotId());
        return  appHotWord;
    }

    @RequestMapping(value = "update.do")
    @ResponseBody
    public String update(AppHotWordVo vo) {
        AppHotWordDto appHotWordDto = BeanMapper.map(vo, AppHotWordDto.class);
        appHotWordService.updateAppHotWord(appHotWordDto);
        return JSONObject.toJSONString("修改成功");
    }

    @RequestMapping(value = "handleStatu.do")
    @ResponseBody
    public String handleStatu(AppHotWordVo vo) {
        AppHotWordDto appHotWordDto = BeanMapper.map(vo, AppHotWordDto.class);
        appHotWordService.handleStatu(appHotWordDto);
        return JSONObject.toJSONString("操作成功");
    }

    @RequestMapping(value = "delete.do")
    @ResponseBody
    public String delete(AppHotWordVo vo) {
        AppHotWordDto appHotWordDto = BeanMapper.map(vo, AppHotWordDto.class);
        appHotWordService.deleteAppHotWord(appHotWordDto);
        return JSONObject.toJSONString("操作成功");
    }
}
