package com.hww.app.common.manager.impl;

import com.google.common.collect.Lists;
import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.entity.AppMemberBehavior;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.app.common.manager.AppMemberBehaviorMng;
import com.hww.app.common.vo.AppMemberBehaviorVo;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.framework.common.constant.HwwConsts;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service("appMemberBehaviorMngImpl")
@Transactional
public class AppMemberBehaviorMngImpl extends BaseEntityMngImpl<Long, AppMemberBehavior, AppMemberBehaviorDao>
		implements AppMemberBehaviorMng {

	@Autowired
	private AppMemberBehaviorCountDao appMemberBehaviorCountDao;

	private AppMemberBehaviorDao appMemberBehaviorDao;

	@Autowired
	public void setAppMemberBehaviorDao(AppMemberBehaviorDao appMemberBehaviorDao) {
		super.setEntityDao(appMemberBehaviorDao);
		this.appMemberBehaviorDao = appMemberBehaviorDao;
	}

	@Override
	public void updateBevValue(Long behaviorId, Integer bevValue) {
		Finder f = Finder.create(" update AppMemberBehavior s ");
		f.append(" set");
		f.append(" s.bevValue = :bevValue ");
		f.append(" where s.behaviorId = :behaviorId");
		f.setParam("bevValue", bevValue);
		f.setParam("behaviorId", behaviorId);
		appMemberBehaviorCountDao.update(f);
	}

	@Override
	public AppMemberBehaviorVo
    loadUserBehaviorForContentByType(Long memberId, Long contentId, Integer bevType,
			Integer bevValue, Integer plateType) {

		Finder f = Finder.create("from AppMemberBehavior bean");
		f.append(" where bean.contentId=:contentId");
		f.append(" and bean.memberId=:memberId");
		f.append(" and bean.bevType=:bevType");
		f.append(" and bean.plateType=:plateType");

		f.setParam("contentId", contentId);
		f.setParam("memberId", memberId);
		f.setParam("plateType", plateType);
		f.setParam("bevType", bevType);

		if (bevValue != null) {
			f.append(" and bean.bevValue=:bevValue ");
			f.setParam("bevValue", bevValue);
		}
		List<?> res = find(f);
		if (res == null || res.isEmpty()) {
			return null;
		}
		AppMemberBehavior appMemberBehavior = (AppMemberBehavior) (res.get(0));

		if (appMemberBehavior == null) {
			return null;
		}
		AppMemberBehaviorVo appMemberBehaviorVo = new AppMemberBehaviorVo();
		BeanMapper.copy(appMemberBehavior, appMemberBehaviorVo);
		return appMemberBehaviorVo;
	}

	@Override
	public List<AppMemberBehaviorVo> loadContentIdsByUserAndBevType(Long memberId, Integer bevType, Integer plateType) {
		if (memberId == null) {
			return new ArrayList<AppMemberBehaviorVo>(0);
		}
		Finder f = Finder.create("from AppMemberBehavior bean");
		f.append(" where bean.memberId=:memberId");
		f.append(" and bean.bevType=:bevType");
		f.append(" and bean.bevValue=1 ");
		f.append(" and bean.plateType=:plateType");

		f.setParam("bevType", bevType);
		f.setParam("memberId", memberId);
		f.setParam("plateType", plateType);

		List<?> res = find(f);
		if (res == null || res.isEmpty()) {
			return Lists.newArrayList();
		}
		List<AppMemberBehavior> appMemberBehaviorList = (List<AppMemberBehavior>) (res);

		if (appMemberBehaviorList == null || appMemberBehaviorList.isEmpty()) {
			return new ArrayList<AppMemberBehaviorVo>(0);
		}
		return BeanMapper.mapList(appMemberBehaviorList, AppMemberBehaviorVo.class);
	}

	@Override
	public boolean getIsUserInLikedCollection(Long contentId, Integer plateType, Long memberId) {
		Jedis conn = null;
		conn = JedisPoolUtil.getConnection();
		String key = RedisKey.ContentLikedCollection.getValue() + contentId + ":" + plateType;
		Boolean keyExist = conn.exists(key);
		if (keyExist == true) {
			Boolean flag = conn.sismember(key, memberId.toString());
			return flag;
		} else {// 缓存中没有key,查数据库,直接查数据库，理论上是有数据不一致可能性的，但是按理说，如果用户最近点过赞，那么缓存中应该就有数据，不会去查数据库
			Integer count = appMemberBehaviorDao.getUserBehaviorCountValue(contentId, HwwConsts.Behavior.b1_dz,
					plateType, memberId);
			if (count == null) {
				conn.sadd(key, "-1");
				return false;
			} else {
				return count >= 1;
			}

		}

	}
}
