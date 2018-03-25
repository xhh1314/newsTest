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
	public List<Long> listMemberIdsOfContentLiked(Long contentId, Integer plateType, Integer behaviorType) {
		Finder f = Finder.create(
				"select a.member_id as memberId from app_member_behavior a WHERE a.content_id= :contentId and a.bev_type= :behaviorType and a.plate_type=:plateType");
		f.setParam("contentId", contentId);
		f.setParam("behaviorType", behaviorType);
		f.setParam("plateType", plateType);
		List<Long> memberId = (List<Long>) findJoin(f, AppMemberBehavior.class);
		return memberId;
	}

	@Override
	public Integer getUserBehaviorCountValue(Long contentId, Integer behaviorType, Integer plateType, Long memberId) {
		Finder f = Finder.create("select  sum(bev_value) as bevValue from app_member_behavior where ");
		f.append(
				" member_id=:memberId and content_id=:contentId and plate_type=:plateType and bev_type=:behaviorType GROUP BY member_id");
		f.setParam("contentId", contentId);
		f.setParam("behaviorType", behaviorType);
		f.setParam("plateType", plateType);
		f.setParam("memberId", memberId);
		List<Integer> number = (List<Integer>) findJoin(f, AppMemberBehavior.class);
		if (number != null || number.size() > 0)
			return number.get(0);
		else
			return null;
	}
}
