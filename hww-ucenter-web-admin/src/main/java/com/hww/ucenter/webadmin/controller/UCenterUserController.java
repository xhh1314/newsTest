/*package com.hww.ucenter.webadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.R;
import com.hww.ucenter.common.dto.UCenterUserDto;
import com.hww.ucenter.common.vo.UCenterUserVo;
import com.hww.ucenter.webadmin.service.UCenterMemberService;
import com.hww.ucenter.webadmin.service.UCenterUserService;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

*//**
 * 后台管理员用户接口
 *
 * @author kkn
 *//*
@Controller
@RequestMapping("/sys")
public class UCenterUserController {

    private static final Logger log = LoggerFactory.getLogger(UCenterUserController.class);

   
    @Autowired
    private UCenterMemberService uCenterMemberService;

    *//**
     * 分页+条件查询后台管理用户列表
     *
     * @param vo
     * @param modelMap
     * @return
     *//*
    @RequestMapping(value = "query.do")
    public String query(UCenterUserVo vo, ModelMap modelMap) {
        log.info("------查询后台管理用户开始------");
        UCenterUserDto UCenterUser = new UCenterUserDto();
        try {
            BeanUtils.copyProperties(UCenterUser, vo);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("查询管理员发生错误!{}", e);
        }
        Pagination p = UCenterUserService.listUCenterUserByKeysAndPage(UCenterUser);
        modelMap.addAttribute("page", p);
        modelMap.addAttribute("form", vo);
        log.info("------查询后台管理用户结束------");
        return "sys/list";
    }

    *//**
     * 保存后台管理用户(若此手机号的前端会员注册过,则保存一条会员;若无则先保存会员，再保存后台管理用户)
     *
     * @param request
     * @param response
     * @param session
     * @param vo
     * @return
     *//*
    @RequestMapping(value = "save.do")
    @ResponseBody
    public R save(HttpServletRequest request, HttpServletResponse response, Session session, UCenterUserVo vo) {
        log.info("------保存后台管理用户开始------");
        // TODO 站点id先写死为1
        if (vo.getSiteId() == null)
            vo.setSiteId(1);
        try {
            return UCenterUserService.saveUCenterUser(vo);
        } catch (InvocationTargetException e) {
            log.error("保存admin出错！{}", e);
        } catch (IllegalAccessException e) {
            log.error("保存admin出错！{}", e);
        }
        return R.error("出现异常");

    }

    *//**
     * 更新后台管理用户信息
     *
     * @param vo
     * @return
     *//*
    @RequestMapping(value = "update.do")
    @ResponseBody
    public R update(UCenterUserVo vo) {
        log.info("------修改后台管理用户开始------");
        if (vo.getSiteId() == null)
            vo.setSiteId(1);
        UCenterUserService.updateUCenterUser(vo);
        log.info("------修改后台管理用户结束------");
        return R.ok().put("page", vo.getPageNo());
    }

    *//**
     * 查询单个用户信息
     *
     * @param vo
     * @return
     *//*
    @RequestMapping(value = "edit.do")
    @ResponseBody
    public String toEdit(UCenterUserVo vo) {
        // TODO 这里本应该统一修改使用R返回，现在没时间不改了
        if (vo.getMemberId() == null)
            return "参数不能为空!";
        log.info("------查询单个后台管理用户开始------");
        UCenterUserVo UCenterUser = UCenterUserService.getUCenterUserById(vo.getMemberId());
        log.info("------查询单个后台管理用户结束------");
        return JSONObject.toJSONString(UCenterUser);
    }

    *//**
     * 单个/批量删除用户
     *
     * @param sysMemberVo
     * @return
     *//*
    @RequestMapping("delete.do")
    @ResponseBody
    public R delete(UCenterUserVo sysMemberVo) {
        log.info("------删除后台管理用户开始------");
        UCenterUserService.deleteUCenterUser(sysMemberVo);
        log.info("------删除后台管理用户结束------");
        return R.ok("删除成功！");

    }

    *//**
     * 单个/批量修改用户状态
     *
     * @param UCenterUserVo
     * @return
     *//*
    @RequestMapping("handleStatu.do")
    @ResponseBody
    public R handleStatu(UCenterUserVo UCenterUserVo) {
        log.info("------修改后台管理用户状态开始------");
        UCenterUserService.updateUCenterUserStatusById(UCenterUserVo);
        log.info("------修改后台管理用户状态结束------");
        return R.ok("修改成功");
    }

    *//**
     * 重置用户密码
     *
     * @param UCenterUserVo
     * @return
     *//*
    @RequestMapping("resetPassword.do")
    @ResponseBody
    public String resetPassword(UCenterUserVo UCenterUserVo) {
        log.info("------重置后台管理用户密码开始------");
        UCenterUserService.resetPassword(UCenterUserVo);

        log.info("------重置后台管理用户密码结束------");
        return JSONObject.toJSONString("重置成功");
    }
}
*/