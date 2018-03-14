package com.hww.app.api;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.app.common.dto.AppMemberNearbyDto;
import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.base.util.R;
@FeignClient(name = "hww-app-web-service-consumer")
public interface AppFeignClient {
	
	
    @RequestMapping(value = "/app/behavior/behaviorExist.do", method = RequestMethod.POST)
    String behaviorExist(@RequestBody AppBehaviorDto appBehaviorDto);
	
	@RequestMapping(value = "/app/behaviorCount/behaviorCount.do/{contentId}/{plateType}", method = RequestMethod.POST)
    Map<String, Integer> behaviorCount(@PathVariable("contentId")Long contentId,@PathVariable("plateType")Integer plateType);
	
	@RequestMapping(value = "/app/behavior/loadcontentids.do", method = RequestMethod.POST)
    List<Long> loadBehaviorContentIds(@RequestBody AppBehaviorDto appBehaviorDto);
	

    @RequestMapping(value = "/app/behavior/createBehavior.do", method = RequestMethod.POST)
    R createBehavior(@RequestBody AppBehaviorDto behaviorDto);
	
    @RequestMapping(value = "/app/search/history/addSearchHisFeignApi.do", method = RequestMethod.POST)
    SearchHistoryDto addSearchHistory(@RequestBody SearchHistoryDto historyDto);
    
	
    
    
    
    @RequestMapping(value = "/app/category/loadCateIdsByColumnIdFeginApi.do", method = RequestMethod.POST)
    List<Long> loadCmsCateIdsByColumnId(@RequestParam("columnId")Long columnId);
    

    @RequestMapping(value = "/app/search/nearPeopleFeginApi.do", method = RequestMethod.POST)
    R searchNearsPeaople(@RequestBody AppMemberNearbyDto dto);
    
    
    @RequestMapping(value = "/app/recommend/loadAllFeginApi.do", method = RequestMethod.POST)
    List<AppRecommConfigDto>  loadAllRecomm();
	
}
