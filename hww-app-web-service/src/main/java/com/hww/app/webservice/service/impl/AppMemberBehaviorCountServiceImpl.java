package com.hww.app.webservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.app.common.dto.AppBehaviorCountDto;
import com.hww.app.common.manager.AppMemberBehaviorCountMng;
import com.hww.framework.common.tool.HwwConsts;
import com.hww.app.webservice.service.AppMemberBehaviorCountService;

@Service("appappMemberBehaviorCountService")
@Transactional
public class AppMemberBehaviorCountServiceImpl implements AppMemberBehaviorCountService {

	@Autowired
	private AppMemberBehaviorCountMng appMemberBehaviorCountMng;

	
	@Override
	public void addBehaviorCount(Long contentId,Integer bevType,Integer plateType,int count) {
		appMemberBehaviorCountMng.addBehaviorCount(contentId, bevType,plateType, count);
		
	}

	@Override
	public Map<String,Integer> behaviorCount(Long contentId,Integer plateType) {
		if(contentId==null){
			 return HwwConsts.userBeahavCount;
		}
		List<AppBehaviorCountDto> dtoList = appMemberBehaviorCountMng.loadByContentId(contentId,plateType);
		Map<String,Integer> counts = new HashMap<String,Integer>();
		if(dtoList==null||dtoList.isEmpty()){
			counts=HwwConsts.userBeahavCount;
			 return counts;
		}else{
			for(AppBehaviorCountDto  dto : dtoList){
				counts.put(""+dto.getBevType(), dto.getBevCount());
			}
			//覆盖数据库没有的值为零
			for (Map.Entry<String,Integer> entry : HwwConsts.userBeahavCount.entrySet()) {
				counts.putIfAbsent(entry.getKey(), 0);
			}
		}
		return counts;
	}
	

	
	

}
