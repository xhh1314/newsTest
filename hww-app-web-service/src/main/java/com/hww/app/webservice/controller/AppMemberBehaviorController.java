package com.hww.app.webservice.controller;

import com.google.common.collect.Lists;
import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.app.common.vo.AppMemberBehaviorVo;
import com.hww.framework.common.tool.HwwConsts;
import com.hww.framework.common.tool.HwwConsts.BehaviorValue;
import com.hww.app.webservice.service.AppMemberBehaviorService;
import com.hww.base.util.R;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app/behavior")
public class AppMemberBehaviorController {
	private static final Log log = LogFactory.getLog(AppMemberBehaviorController.class);
	
  @Autowired
  private AppMemberBehaviorService appMemberBehaviorService;
  


	@RequestMapping(value = "/createBehavior.do", method = RequestMethod.POST)
	public R createBehavior(@RequestBody AppBehaviorDto behaviorDto) {
	  try{
		  if(behaviorDto.getContentId()==null) {
			  return R.error("请选择对应新闻或者新鲜事");
		  }
		  if(behaviorDto.getMemberId()==null) {
			  return R.error("当前用户未登录");
		  }
		  if(behaviorDto.getBevType()==null||!HwwConsts.userBeahavList.contains(behaviorDto.getBevType())) {
			  return R.error("用户行为类型错误");
		  }
		  if(behaviorDto.getPlateType()==null||!HwwConsts.userBeahavPlateList.contains(behaviorDto.getPlateType())) {
			  return R.error("所属类型类型错误");
		  }
		  if(behaviorDto.getBevValue()==null) {
			  return R.error("操作值错误");
		  }
		  appMemberBehaviorService.createBehavior(behaviorDto);
		  return R.ok("操作成功");
	  }catch(Exception e){
		  e.printStackTrace();
		  return R.error("操作失败");
	  }
  }


	@RequestMapping(value = "/behaviorExist.do", method = RequestMethod.POST)
//  @ResponseBody
	public String behaviorExist(@RequestBody AppBehaviorDto behaviorDto) {
	  if(behaviorDto.getMemberId()==null||behaviorDto.getContentId()==null||behaviorDto.getBevType()==null){
		  return "n";
	  }
	  AppMemberBehaviorVo appMemberBehaviorVo = appMemberBehaviorService.loadUserBehaviorForContentByType(
			  behaviorDto.getMemberId(),
			  behaviorDto.getContentId(),
			  behaviorDto.getBevType(),
			  BehaviorValue.bev1,
			  behaviorDto.getPlateType());
	  
	  if(appMemberBehaviorVo==null){
		  return "n";
	  }else{
		  Integer bevValue = appMemberBehaviorVo.getBevValue();
		  if(BehaviorValue.bev1.intValue()==bevValue.intValue()){
			  return "y";
		  }
		  return "n";
	  }
  }
  
  @RequestMapping(value = "/loadcontentids.do", method = RequestMethod.POST)
//  @ResponseBody
  public List<Long> loadBehaviorContentIds(@RequestBody AppBehaviorDto appBehaviorDto){
	  if(appBehaviorDto.getMemberId()==null||appBehaviorDto.getBevType()==null) {
		  return Lists.newArrayList();
	  }
	  
	  List<Long> contentIds = appMemberBehaviorService.loadContentIdsByUserAndBevType(appBehaviorDto.getMemberId(), appBehaviorDto.getBevType(),appBehaviorDto.getPlateType());
	  if(contentIds==null){
		  return new ArrayList<Long>();
	  }
	  return contentIds;
	}
}




