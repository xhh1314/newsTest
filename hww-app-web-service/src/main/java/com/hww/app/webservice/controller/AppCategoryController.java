package com.hww.app.webservice.controller;

import com.google.common.collect.Lists;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.vo.AppHomeCategoryVo;
import com.hww.app.common.vo.ZAppHomeCategoryVo;
import com.hww.app.webservice.service.AppCategoryService;
import com.hww.base.util.R;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XiaoBG
 * App频道WebServices模块对外接口
 */
@RestController
@RequestMapping("/app/category")
public class AppCategoryController {

    private static final Log log = LogFactory.getLog(AppCategoryController.class);
    @Autowired
    private AppCategoryService appCategoryService;

    /**
     * 全局：APP端首页频道列表展示
     *
     * @param userId, IMEI
     * @return
     */
    @RequestMapping(value = "appCategoryList.do", method = {RequestMethod.POST})
    @ApiOperation(value = "APP端首页频道列表展示", notes = "")
    @ResponseBody
    public R loadHomePageColumns(Long userId, String imei) {
        log.info("----------APP端我的频道查询开始----------");
        ZAppHomeCategoryVo list = appCategoryService.loadUserAppCate(userId, imei);
        log.info("----------APP端我的频道查询结束----------");
        return R.ok().put("data", list);
    }

    /**
     * 用户：我的频道排序
     */
    @RequestMapping(value = "userColumnSorting.do", method = { RequestMethod.POST})
    @ApiOperation(value = "频道排序；同时可以订阅和删除", notes = "")
    @ResponseBody
    public R userCategorySorting(String columnSorting, Long userId, String imei) {

        log.info("----------APP端频道排序开始----------");
        if (columnSorting == null) {
            return R.error(500, "排序失败，参数为空");
        }
        appCategoryService.userCategorySorting2(columnSorting, userId, imei);
        log.info("----------APP端频道排序结束----------");
        return R.ok().put("data", "保存成功");
    }

//    /**
//     * 用户：订阅频道
//     *
//     * @return
//     * @author XiaoBG
//     * @date 2018年1月11日 上午9:45:14
//     * param appCategory, userId, imei
//     * @version v0.1
//     */
//    @RequestMapping(value = "saveUserColumn.do", method = {RequestMethod.GET, RequestMethod.POST})
//    @ApiOperation(value = "订阅频道", notes = "")
//    @ResponseBody
//    public R saveUserCategory(Long columnId, Long userId, String imei) {
//
//        log.info("----------APP端订阅频道功能开始----------");
//        if (columnId == null) {
//            return R.error(500, "参数为空");
//        }
//        R list = appCategoryService.saveUserCategory(columnId, userId, imei);
//        log.info("----------APP端订阅频道功能结束----------");
//        return R.ok().put("data", list);
//
//    }
//
//    /**
//     * 用户：取消订阅
//     *
//     * @return
//     * @author XiaoBG
//     * @date 2018年1月11日 下午9:45:14
//     * param appCategory
//     * @version v0.1
//     */
//    @RequestMapping(value = "deleteUserColumn.do", method = {RequestMethod.GET, RequestMethod.POST})
//    @ApiOperation(value = "取消订阅", notes = "")
//    @ResponseBody
//    public R editUserCategory(Long columnId, Long userId, String imei) {
//
//        log.info("----------APP端取消订阅频道开始----------");
//        if (columnId == null) {
//            return R.error(500, "参数为空");
//        }
//        appCategoryService.deleteUserCategory(columnId, userId, imei);
//        log.info("----------APP端取消订阅频道结束----------");
//        return R.ok().put("data", "已删除");
//    }

    //=========================Fegin==API=======start======================================================

    /**
     * 用户：根据频道接口查询新闻Id列表
     *
     */
    @RequestMapping(value = "loadCateIdsByColumnIdFeginApi.do", method = { RequestMethod.POST})
    @ApiOperation(value = "根据频道接口查询新闻Id列表", notes = "")
    public List<Long> loadCateIdsByColumnIdFeginApi(Long columnId) {
    	if(columnId==null) {
    		return Lists.newArrayList();
    	}
    	 List<Long> categoryIds = appCategoryService.loadCateIdsByColumnId(columnId);
    	 return categoryIds==null?Lists.newArrayList():categoryIds;
    }

    @ApiOperation(value = "查询自定义频道ids", notes = "")
    @RequestMapping(value = "loadCustomColumnIdsFeginApi.do", method = {RequestMethod.POST})
    public List<Long> loadCustomColumnIdsFeginApi() {
    	 List<AppCategory> categoryIds = appCategoryService.loadCustomCategoryList();
    	 if(categoryIds==null||categoryIds.isEmpty()) {
    		 return Lists.newArrayList();
    	 }
    	 return categoryIds.stream().map(val->val.getColumnId()).collect(Collectors.toList());
    }
    
}
