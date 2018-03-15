package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.dto.AppBehaviorCountDto;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.app.common.manager.AppMemberBehaviorCountMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.util.BeanMapper;
import com.hww.framework.common.tool.HwwConsts;
import com.hww.framework.common.tool.JedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service("appMemberBehaviorCountMngImpl")
public class AppMemberBehaviorCountMngImpl extends BaseEntityMngImpl<Long, AppMemberBehaviorCount, AppMemberBehaviorCountDao> 
	implements AppMemberBehaviorCountMng {

    private AppMemberBehaviorCountDao appMemberBehaviorCountDao;
	
		@Autowired
	    public void setAppMemberBehaviorCountDao(AppMemberBehaviorCountDao appMemberBehaviorCountDao) {
	        super.setEntityDao(appMemberBehaviorCountDao);
	        this.appMemberBehaviorCountDao = appMemberBehaviorCountDao;
	    }
		
	@Override
	public List<AppBehaviorCountDto> loadByContentId(Long contentId,Integer plateType) {
		List<AppBehaviorCountDto> appMemberBehaviorCountDtoList = new ArrayList<AppBehaviorCountDto>();
		if(contentId==null) {
			return appMemberBehaviorCountDtoList;
		}
		List<AppMemberBehaviorCount>  appMemberBehaviorCountList = appMemberBehaviorCountDao.loadByContentId(contentId,plateType);
		if(appMemberBehaviorCountList!=null&&appMemberBehaviorCountList.size()>0) {
			appMemberBehaviorCountDtoList = BeanMapper.mapList(appMemberBehaviorCountList, AppBehaviorCountDto.class);
		}
		return appMemberBehaviorCountDtoList;
	}


	@Override
	public void addBehaviorCount(Long contentId, Integer bevType,Integer plateType, int count) {
			Jedis conn=null;
			conn= JedisPoolUtil.getConnection();
			if(plateType==(HwwConsts.PlateType.topic)){
				if(bevType==HwwConsts.Behavior.b1_dz){

				}
			}
		AppMemberBehaviorCount behaviorCount=appMemberBehaviorCountDao.loadByContentIdAndBevType(contentId, bevType,plateType);
		if(behaviorCount==null) {
			AppMemberBehaviorCount behaviorCount2 =new AppMemberBehaviorCount();
			behaviorCount2.setContentId(contentId);
			behaviorCount2.setBevType(bevType);
			behaviorCount2.setBevCount(count);
			behaviorCount2.setPlateType(plateType);
			appMemberBehaviorCountDao.save(behaviorCount2);
		}else {
			int c=behaviorCount.getBevCount()==null?0:behaviorCount.getBevCount();
			int finalCount=c+count;
			if(finalCount<0) {
				finalCount=0;
			}
			behaviorCount.setBevCount(finalCount);
			appMemberBehaviorCountDao.update(behaviorCount);
			
		}
		
	}

//	@Override
//	public AppBehaviorCountDto loadByContentIdAndBevType(Long contentId, Integer bevType,Integer plateType) {
//		AppBehaviorCountDto appMemberBehaviorCountDto = new AppBehaviorCountDto();
//		if(contentId==null||bevType==null) {
//			return appMemberBehaviorCountDto;
//		}
//		
//		AppMemberBehaviorCount appMemberBehaviorCount= appMemberBehaviorCountDao.loadByContentIdAndBevType( contentId,  bevType,plateType);
//		if(appMemberBehaviorCount!=null) {
//			BeanMapper.copy(appMemberBehaviorCount, appMemberBehaviorCountDto);
//		}
//		return appMemberBehaviorCountDto;
//	}
//	@Override
//	public List<AppBehaviorCountDto> loadByContentIdsAndBevType(List<Long> contentIds, Integer bevType,Integer plateType) {
//		if(contentIds==null||contentIds.isEmpty()) {
//			return new ArrayList<AppBehaviorCountDto>(0);
//		}
//		List<AppMemberBehaviorCount> appMemberBehaviorCountList= appMemberBehaviorCountDao.loadByContentIdsAndBevType( contentIds,  bevType,plateType);
//		
//		List<AppBehaviorCountDto> vos = new ArrayList<AppBehaviorCountDto>();
//		if(appMemberBehaviorCountList!=null&&appMemberBehaviorCountList.size()>0) {
//			vos = BeanMapper.mapList(appMemberBehaviorCountList, AppBehaviorCountDto.class);
//		}
//		return vos;
//	}
}
