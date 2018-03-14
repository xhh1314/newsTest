package com.hww.app.webservice.controller;

import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.webservice.service.AppRecommConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/app/recommend")
public class AppRecommendController {
	private static final Log log = LogFactory.getLog(AppRecommendController.class);

  @Autowired AppRecommConfigService appRecommConfigService;
  
	 @RequestMapping(value = "/loadAllFeginApi.do", method = RequestMethod.POST)
	 public List<AppRecommConfigDto> loadAllRecomm(){
		
		 List<AppRecommConfigDto> res= appRecommConfigService.loadAllRecomm();
		 
		 return res;
	 }
	
  
}




