package com.hww.app.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hww.app.admin.service.GeoCountryService;
import com.hww.app.admin.service.GeoProvinceService;
import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.dto.GeoProvinceDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.app.common.entity.AppGeoCountry;
import com.hww.app.common.entity.AppGeoProvince;
import com.hww.app.common.manager.GeoCityMng;
import com.hww.app.common.manager.GeoProvinceMng;
import com.hww.app.common.vo.AppGeoCountryVo;
import com.hww.base.util.R;

@Controller
@RequestMapping("/country")
public class GeoCountryController {

	@Autowired
	private GeoCountryService geoCountryService;
	@Autowired
	private GeoProvinceMng geoProvinceMng;
	@Autowired
	private GeoCityMng geoCityMng;
	@Autowired
	private GeoProvinceService geoProvinceService;
	/**
	 * 列表显示
	 * @param request
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "v_list.do")
	public String countryList(HttpServletRequest request,ModelMap model, AppGeoCountryVo vo){
		Long  countryId = vo.getCountryId();
		vo.setCountryId(null);
		List<AppGeoCountryVo> countryList = geoCountryService.CountryListFindByName(vo);
		GeoProvinceDto geoProvinceDto = new GeoProvinceDto();
		if(countryId!=null){
			geoProvinceDto.setCountryId(countryId);
		}
		if(vo.getProvinceName()!=null){
			geoProvinceDto.setProvinceName(vo.getProvinceName());
		}
		List<AppGeoProvince> provinceList = geoProvinceMng.provinceList(geoProvinceDto);
		
		GeoCityDto geoCityDto = new GeoCityDto();
		if(vo.getProvinceId()!=null){
			geoCityDto.setProvinceId(vo.getProvinceId());
		}
		if(vo.getCityName()!=null){
			geoCityDto.setCityName(vo.getCityName());
		}
		List<AppGeoCity> cityList = geoCityMng.cityList(geoCityDto);
		model.addAttribute("countryList",countryList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList",cityList);
		return "ThreeLinkage/list";
	}
	/**
	 * 国家信息修改
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "v_edit.do")
	@ResponseBody 
	public R edit(HttpServletRequest request, HttpServletResponse response, AppGeoCountryVo vo, ModelMap model) {
		try {
			AppGeoCountry entity = geoCountryService.findById(vo);
			return R.ok().put("country", entity);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("请求异常");
		}	
		
			
	}
	/**
	 * 国家信息添加，修改
	 * @param request
	 * @param response
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "v_saveOrUpdate.do")
	@ResponseBody
	public R saveOrUpdate(HttpServletRequest request, HttpServletResponse response, AppGeoCountryVo vo) {
		
			try {
				if(vo.getCountryId()!=null) {
					geoCountryService.updateCountry(vo);
				}else {
					geoCountryService.saveCountry(vo.getCountryName(),vo.getLongitude(),vo.getLatitude(),vo.getRadius());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return R.error("操做失败");
			}
			return R.ok("操做成功");
	}
	
	/**
	 * 删除国家信息
	 * @param request
	 * @param response
	 * @param vo
	 * @param map
	 * @return
	 */
	@RequestMapping("del.do")
	@ResponseBody
	public String delRecomm(HttpServletRequest request, HttpServletResponse response, AppGeoCountryVo vo,ModelMap map) {
		String result="success";
		List<AppGeoProvince> list= geoProvinceService.findByCountry(vo.getCountryId());
		if(list.size()>0) {
			result="含有省份信息，无法删除";
		}else {
		AppGeoCountry vos=geoCountryService.findById(vo);
		geoCountryService.delCountry(vos);
		result="删除成功";
		}
		return result;	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
