package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.entity.AppMemberBehavior;
import com.hww.base.common.util.Finder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppMemberBehaviorDaoImpl extends LocalEntityDaoImpl<Long, AppMemberBehavior>
		implements AppMemberBehaviorDao {

	@Override
	public List<Long> listMemberIdsByContentIdAndBehaviorType(Long contentId, Integer behaviorType) {
		Finder f = Finder.create(
				"select a.member_id as memberId from app_member_behavior a WHERE a.content_id= :contentId and a.bev_type= :behaviorType");
		f.setParam("contentId", contentId);
		f.setParam("behaviorType", behaviorType);
		List<Long> memberId = (List<Long>) findJoin(f, AppMemberBehavior.class);
		return memberId;
	}
}
