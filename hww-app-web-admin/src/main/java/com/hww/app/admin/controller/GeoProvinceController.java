package com.hww.app.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.app.admin.service.GeoCityService;
import com.hww.app.admin.service.GeoProvinceService;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.app.common.manager.GeoProvinceMng;
import com.hww.base.util.R;

@Controller
@RequestMapping("/province")
public class GeoProvinceController {

	@Autowired
	private GeoProvinceMng geoProvinceMng;
	@Autowired
	private GeoCityService geoCityService;
	@Autowired
	private GeoProvinceService geoProvinceService;
	
	@RequestMapping(value = "v_list.do")
	public R  countryList(HttpServletRequest request,ModelMap model, GeoProvinceDto vo){
		List<AppGeoProvince> list = geoProvinceMng.provinceList(vo);
		return R.ok().put("list", list);
	}
/**
 * 删除省份
 * @param request
 * @param response
 * @param vo
 * @return
 */
	@RequestMapping("v_del.do")
	@ResponseBody
	public String del(HttpServletRequest request, HttpServletResponse response, GeoProvinceDto vo) {
		String result="success";
		List<AppGeoCity> list= geoCityService.findByProvice(vo.getProvinceId());
		if(list.size()>0) {
			result="含有城市信息，无法删除";
		}else {
		AppGeoProvince entity=geoProvinceMng.find(vo.getProvinceId());
		
		geoProvinceMng.delete(entity);
		result="删除成功";
		}
		return result;
		
	}
	/**
	 * 保存或修改省份
	 * @param request
	 * @param response
	 * @param vo
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "v_save.do")
	@ResponseBody
	public R add(HttpServletRequest request, HttpServletResponse response, GeoProvinceDto vo) {
		try {
			if(vo.getProvinceId()!=null) {
				geoProvinceService.updateProvince(vo);
			}else {
				geoProvinceService.saveProvince(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("操做失败!");
		}
		
		return R.ok("操作成功!");
	}
	/**
	 * 查询单个省份
	 * @param request
	 * @param response
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "v_edit.do")
	@ResponseBody
	public R edit(HttpServletRequest request, HttpServletResponse response, GeoProvinceDto vo, ModelMap model) {
		
		try {
				AppGeoProvince entity1 = geoProvinceService.findProviceById(vo.getProvinceId());
				return R.ok().put("province", entity1);
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("请求异常");
			}	
	}
	
}
