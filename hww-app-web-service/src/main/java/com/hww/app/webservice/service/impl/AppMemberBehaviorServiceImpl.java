package com.hww.app.webservice.service.impl;

import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.app.common.entity.AppMemberBehavior;
import com.hww.app.common.manager.AppMemberBehaviorMng;
import com.hww.app.common.vo.AppMemberBehaviorVo;
import com.hww.framework.common.constant.HwwConsts;
import com.hww.app.webservice.service.AppMemberBehaviorCountService;
import com.hww.app.webservice.service.AppMemberBehaviorService;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.TimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("appMemberBehaviorService")
@Transactional
public class AppMemberBehaviorServiceImpl implements AppMemberBehaviorService {

	 @Autowired
	 private AppMemberBehaviorMng appMemberBehaviorMng;

	 @Autowired
	 private  AppMemberBehaviorCountService behaviorCountService;
	
	@Override
	public void createBehavior(AppBehaviorDto behaviorDto) {
		Long memberId=behaviorDto.getMemberId();
		Long contentId=behaviorDto.getContentId();
		Integer bevType=behaviorDto.getBevType();
		
		Integer bevValue =behaviorDto.getBevValue();
		
		Integer plateType = behaviorDto.getPlateType();
		
		
		AppMemberBehaviorVo vo = loadUserBehaviorForContentByType( memberId,  contentId, bevType,null,plateType);
		if(vo!=null) {
			//已经是点赞。。。。等操作  0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5不感兴趣( 不感兴趣，6内容太水，7看过了)
			if(HwwConsts.Behavior.b3_pl!=bevType.intValue()&&HwwConsts.Behavior.b4_bl!=bevType.intValue()) {//评论，爆料 需要继续加一
				if(HwwConsts.BehaviorValue.bev1.equals(vo.getBevValue())&&HwwConsts.BehaviorValue.bev1.equals(bevValue)) {
					return;
				}
			}
			if(HwwConsts.Behavior.b3_pl!=bevType.intValue()&&HwwConsts.Behavior.b4_bl!=bevType.intValue()) {//评论，爆料 需要继续加一
				//已经是 取消点赞。。。等操作
				if(HwwConsts.BehaviorValue.bev0.equals(vo.getBevValue())&&HwwConsts.BehaviorValue.bev0.equals(bevValue)) {
					return;
				}
			}
		
			AppMemberBehavior appMemberBehavior=new AppMemberBehavior();
			BeanMapper.copy(vo, appMemberBehavior);
			appMemberBehavior.setBevValue(bevValue);
			appMemberBehavior.setReadedTime(TimeUtils.getDateToTimestamp());
			appMemberBehaviorMng.updateBevValue(vo.getBehaviorId(),bevValue);
			
			
			if(HwwConsts.BehaviorValue.bev0.equals(bevValue)){
				behaviorCountService.addBehaviorCount(contentId, bevType,plateType, -1,memberId);
			}else {
				behaviorCountService.addBehaviorCount(contentId, bevType,plateType, 1,memberId);
			}
			
		}else {
			AppMemberBehavior appMemberBehavior=new AppMemberBehavior();
			BeanMapper.copy(behaviorDto, appMemberBehavior);
			appMemberBehavior.setBevValue(bevValue);
			appMemberBehavior.setCreateTime(TimeUtils.getDateToTimestamp());
			appMemberBehavior.setReadedTime(TimeUtils.getDateToTimestamp());
			appMemberBehaviorMng.save(appMemberBehavior);
			
			if(HwwConsts.BehaviorValue.bev0.equals(bevValue)){
				//第一次点击肯定不会出现直接取消点赞的情况
				//behaviorCountService.addBehaviorCount(contentId, bevType,plateType, -1);
			}else {
				behaviorCountService.addBehaviorCount(contentId, bevType,plateType, 1,memberId);
			}
		}
		
	}

	@Override
	public AppMemberBehaviorVo loadUserBehaviorForContentByType(Long memberId, Long contentId, Integer bevType,Integer bevValue,Integer plateType) {
		AppMemberBehaviorVo appMemberBehaviorVo = appMemberBehaviorMng.loadUserBehaviorForContentByType(memberId,  contentId, bevType,bevValue,plateType);
		return appMemberBehaviorVo;
	}


	@Override
	public List<Long> loadContentIdsByUserAndBevType(Long memberId, Integer bevType,Integer plateType) {
		List<Long> contentIds = new ArrayList<Long>();
		if(memberId==null||bevType==null) {
			return contentIds;
		}
		List<AppMemberBehaviorVo> vo_list= appMemberBehaviorMng.loadContentIdsByUserAndBevType(memberId,bevType,plateType);
		
		//过滤
//		vo_list=vo_list.stream().filter(val->HwwConsts.BehaviorValue.bev1.equals(val.getBevValue())).collect(Collectors.toList());
		
		if(vo_list!=null){
			contentIds =vo_list.stream().map(val->val.getContentId()).collect(Collectors.toList());
		}
		return contentIds;
	}






}
