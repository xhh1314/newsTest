package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.base.common.util.Finder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppMemberBehaviorCountDaoImpl extends LocalEntityDaoImpl<Long, AppMemberBehaviorCount>
		implements AppMemberBehaviorCountDao {

	@Override
	public AppMemberBehaviorCount loadByContentIdAndBevType(Long contentId, Integer bevType, Integer plateType) {
		Finder f = Finder.create("from AppMemberBehaviorCount bean");
		f.append(" where bean.contentId=:contentId");

		f.append(" and bean.bevType=:bevType");
		f.append(" and plateType =:plateType ");

		f.setParam("contentId", contentId);
		f.setParam("plateType", plateType);
		f.setParam("bevType", bevType);
		List<?> list = find(f);
		if (list == null || list.size() == 0) {
			return null;
		}
		return (AppMemberBehaviorCount) (list.get(0));

	}

	@Override
	public List<AppMemberBehaviorCount> loadByContentId(Long contentId, Integer plateType) {
		Finder f = Finder.create("from AppMemberBehaviorCount");
		f.append(" where  contentId=:contentId");
		f.append(" and plateType =:plateType ");
		f.setParam("plateType", plateType);
		f.setParam("contentId", contentId);
		List<AppMemberBehaviorCount> res = (List<AppMemberBehaviorCount>) find(f);
		if (res == null || res.isEmpty()) {
			return new ArrayList<AppMemberBehaviorCount>(0);
		}
		return res;
	}

	@Override
	public Integer getCountByBehaviorAndPlate(Long contentId, Integer behaviorType, Integer plateType) {
		Finder f = Finder.create("from AppMemberBehaviorCount ");
		f.append(" where  contentId=:contentId");
		f.append(" and plateType =:plateType ");
		f.append(" and bevType=:bevType");
		f.setParam("plateType", plateType);
		f.setParam("contentId", contentId);
		f.setParam("bevType", behaviorType);
		List<AppMemberBehaviorCount> appMemberBehaviorCounts = (List<AppMemberBehaviorCount>) find(f);
		if (appMemberBehaviorCounts == null || appMemberBehaviorCounts.isEmpty())
			return null;
		return appMemberBehaviorCounts.get(0).getBevCount();
	}
	// @Override
	// public List<AppMemberBehaviorCount> loadByContentIdsAndBevType(List<Long>
	// contentIds, Integer bevType,Integer plateType) {
	// Finder f = Finder.create("from AppMemberBehaviorCount bean");
	// f.append(" where bean.contentId in (:contentIds) ");
	// f.append(" and bean.plateType =:plateType ");
	//
	// f.setParamList("contentIds", contentIds);
	// f.setParam("plateType", plateType);
	//
	// if(bevType!=null) {
	// f.append(" and bean.bevType=:bevType");
	// f.setParam("type", bevType);
	// }
	// return (List<AppMemberBehaviorCount>) find(f);
	// }
}
