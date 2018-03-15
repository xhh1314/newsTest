package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.dto.AppBehaviorCountDto;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.app.common.manager.AppMemberBehaviorCountMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.framework.common.tool.HwwConsts;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.sns.api.SnsFeignClient;
import com.hww.sns.common.dto.SnsTopicDto;
import com.hww.sns.common.vo.SnsTopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service("appMemberBehaviorCountMngImpl")
public class AppMemberBehaviorCountMngImpl
		extends BaseEntityMngImpl<Long, AppMemberBehaviorCount, AppMemberBehaviorCountDao>
		implements AppMemberBehaviorCountMng {

	@Autowired
	private AppMemberBehaviorCountDao appMemberBehaviorCountDao;

	@Autowired
	private SnsFeignClient snsFeignClient;

	/**
	 * 过期时间
	 */
	private final int expireTime=24*60*60;

	@Autowired
	public void setAppMemberBehaviorCountDao(AppMemberBehaviorCountDao appMemberBehaviorCountDao) {
		super.setEntityDao(appMemberBehaviorCountDao);
		this.appMemberBehaviorCountDao = appMemberBehaviorCountDao;
	}

	@Override
	public List<AppBehaviorCountDto> loadByContentId(Long contentId, Integer plateType) {
		List<AppBehaviorCountDto> appMemberBehaviorCountDtoList = new ArrayList<AppBehaviorCountDto>();
		if (contentId == null) {
			return appMemberBehaviorCountDtoList;
		}
		List<AppMemberBehaviorCount> appMemberBehaviorCountList = appMemberBehaviorCountDao.loadByContentId(contentId,
				plateType);
		if (appMemberBehaviorCountList != null && appMemberBehaviorCountList.size() > 0) {
			appMemberBehaviorCountDtoList = BeanMapper.mapList(appMemberBehaviorCountList, AppBehaviorCountDto.class);
		}
		return appMemberBehaviorCountDtoList;
	}

	@Override
	public void addBehaviorCount(Long contentId, Integer bevType, Integer plateType, int count,Long memberId) {
		Jedis conn = null;
		conn = JedisPoolUtil.getConnection();
		// 点赞
		if (bevType == HwwConsts.Behavior.b1_dz) {
			// 主题
			if (plateType == (HwwConsts.PlateType.topic)) {
				String topicKey = "sns:topic:" + contentId;
				String upnum = conn.hget(topicKey, "upNum");
				// 缓存里没有则查询数据库
				if (upnum == null) {
					synchronized (this) {
						if (upnum == null) {
							SnsTopicVo snsTopicVo = snsFeignClient.topicDetail(contentId);
							SnsTopicDto snsTopicDto = new SnsTopicDto();
							org.springframework.beans.BeanUtils.copyProperties(snsTopicVo, snsTopicDto);
							conn.hmset(topicKey, BeanMapper.mapBeanToStringMap(snsTopicDto));
							conn.expire(topicKey,expireTime);
							upnum = snsTopicDto.getUpNum().toString();
						}
					}
				}
				conn.hincrBy(topicKey, "upNum", count);
				String likeHistory="like:history";
				//记录被点赞过的文字，用一个定时任务不停的刷新数据到数据库
				conn.sadd(likeHistory,"contentId:plateType");
				String likeCollectionKey="likeCollection:"+contentId;
				if()
				conn.sadd(likeCollectionKey,)

			}

			// 只有点赞走redis
		} else {
			AppMemberBehaviorCount behaviorCount = appMemberBehaviorCountDao.loadByContentIdAndBevType(contentId,
					bevType, plateType);
			if (behaviorCount == null) {
				AppMemberBehaviorCount behaviorCount2 = new AppMemberBehaviorCount();
				behaviorCount2.setContentId(contentId);
				behaviorCount2.setBevType(bevType);
				behaviorCount2.setBevCount(count);
				behaviorCount2.setPlateType(plateType);
				appMemberBehaviorCountDao.save(behaviorCount2);
			} else {
				int c = behaviorCount.getBevCount() == null ? 0 : behaviorCount.getBevCount();
				int finalCount = c + count;
				if (finalCount < 0) {
					finalCount = 0;
				}
				behaviorCount.setBevCount(finalCount);
				appMemberBehaviorCountDao.update(behaviorCount);

			}
		}

	}

	// @Override
	// public AppBehaviorCountDto loadByContentIdAndBevType(Long contentId, Integer
	// bevType,Integer plateType) {
	// AppBehaviorCountDto appMemberBehaviorCountDto = new AppBehaviorCountDto();
	// if(contentId==null||bevType==null) {
	// return appMemberBehaviorCountDto;
	// }
	//
	// AppMemberBehaviorCount appMemberBehaviorCount=
	// appMemberBehaviorCountDao.loadByContentIdAndBevType( contentId,
	// bevType,plateType);
	// if(appMemberBehaviorCount!=null) {
	// BeanMapper.copy(appMemberBehaviorCount, appMemberBehaviorCountDto);
	// }
	// return appMemberBehaviorCountDto;
	// }
	// @Override
	// public List<AppBehaviorCountDto> loadByContentIdsAndBevType(List<Long>
	// contentIds, Integer bevType,Integer plateType) {
	// if(contentIds==null||contentIds.isEmpty()) {
	// return new ArrayList<AppBehaviorCountDto>(0);
	// }
	// List<AppMemberBehaviorCount> appMemberBehaviorCountList=
	// appMemberBehaviorCountDao.loadByContentIdsAndBevType( contentIds,
	// bevType,plateType);
	//
	// List<AppBehaviorCountDto> vos = new ArrayList<AppBehaviorCountDto>();
	// if(appMemberBehaviorCountList!=null&&appMemberBehaviorCountList.size()>0) {
	// vos = BeanMapper.mapList(appMemberBehaviorCountList,
	// AppBehaviorCountDto.class);
	// }
	// return vos;
	// }
}
