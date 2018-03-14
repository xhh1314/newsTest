package com.hww.app.admin.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.app.admin.service.GeoCityService;
import com.hww.app.common.dto.GeoCityDto;
import com.hww.app.common.entity.AppGeoCity;
import com.hww.base.util.R;

@Controller
@RequestMapping("/city")
public class GeoCityController {
	@Autowired
	GeoCityService cityService;
	
	/**
	 * 保存或修改城市
	 * @param request
	 * @param response
	 * @param vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "v_save.do")
	@ResponseBody
	public R add(HttpServletRequest request, HttpServletResponse response, GeoCityDto vo) {
		try {
			if(vo.getCityId()!=null) {
				cityService.updateGeoCity(vo);
			}else {
				cityService.saveGeoCity(vo);
			}
		} catch (Exception e) {
			return R.error("操做失败!");
		}
		return R.ok("操做成功!");
	}
	/**
	 * 修改时查询单个的信息
	 * @param request
	 * @param response
	 * @param vo
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "v_edit.do")
	@ResponseBody
	public R edit(HttpServletRequest request, HttpServletResponse response, GeoCityDto vo, ModelMap model) {
		String cityId = request.getParameter("cityId");
			try {
				AppGeoCity entity = cityService.findGeoCityById(vo.getCityId());				
				return R.ok().put("city", entity);		
			}catch(Exception e) {
				e.printStackTrace();
				return R.error("请求异常");			
		}
	}
	
	
	
	/**
	 * 删除城市
	 * @param request
	 * @param response
	 * @param vo
	 * @return
	 */
	
	
	@RequestMapping("v_del.do")
	@ResponseBody
	public String del(HttpServletRequest request, HttpServletResponse response, GeoCityDto vo) {
		String result="success";
		AppGeoCity entity=cityService.findGeoCityById(vo.getCityId());
		cityService.delGeoCity(entity);
		return result;
	}
	
	
	
	
	
	
}
