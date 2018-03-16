package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.dto.AppBehaviorCountDto;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.app.common.manager.AppMemberBehaviorCountMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.util.BeanMapper;
import com.hww.framework.common.constant.HwwConsts;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.sns.api.SnsFeignClient;
import com.hww.sns.common.dto.SnsTopicDto;
import com.hww.sns.common.vo.SnsTopicVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("appMemberBehaviorCountMngImpl")
public class AppMemberBehaviorCountMngImpl
		extends BaseEntityMngImpl<Long, AppMemberBehaviorCount, AppMemberBehaviorCountDao>
		implements AppMemberBehaviorCountMng {
	private static final Logger log= LoggerFactory.getLogger("AppMemberBehaviorCountMngImpl.class");

	@Autowired
	private AppMemberBehaviorCountDao appMemberBehaviorCountDao;

	@Autowired
	private SnsFeignClient snsFeignClient;
	@Autowired
	private AppMemberBehaviorDao appMemberBehaviorDao;

	/**
	 * 过期时间
	 */
	private final int expireTime = 2 * 24 * 60 * 60;

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
	public void addBehaviorCount(Long contentId, Integer bevType, Integer plateType, int count, Long memberId) {
		// 点赞操作走redis
		if (bevType == HwwConsts.Behavior.b1_dz) {
			addLikeCount(contentId, bevType, plateType, count, memberId);

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

	/**
	 * 点赞redis实现
	 * @param contentId
	 * @param bevType
	 * @param plateType
	 * @param count
	 * @param memberId
	 */
	private void addLikeCount(Long contentId, Integer bevType, Integer plateType, int count, Long memberId) {
		Jedis conn = null;

		try {
			conn = JedisPoolUtil.getConnection();
			String topicKey = RedisKey.SnsTopic.getValue()+ contentId;
			String upnum = conn.hget(topicKey, "upNum");
			// 缓存里没有则查询数据库
			if (upnum == null) {
                synchronized (this) {
                    if (upnum == null) {
                        // 主题
                        if (plateType == (HwwConsts.PlateType.topic)) {
                            SnsTopicVo snsTopicVo = snsFeignClient.topicDetail(contentId);
                            SnsTopicDto snsTopicDto = new SnsTopicDto();
                            org.springframework.beans.BeanUtils.copyProperties(snsTopicVo, snsTopicDto);
                            conn.hmset(topicKey, BeanMapper.mapBeanToStringMap(snsTopicDto));
                            conn.expire(topicKey, expireTime);
                            upnum = snsTopicDto.getUpNum().toString();
                        }
                    }
                }
            }
			conn.hincrBy(topicKey, "upNum", count);
			// 记录被点赞过的文章，用一个定时任务不停的刷新数据到数据库
			conn.sadd(RedisKey.LikeHistory.getValue(), contentId+":"+bevType);
			String likeCollectionKey = RedisKey.LikeCollection.getValue() + contentId;
			// 文章被点赞的集合很有可能过期，所以这里采用的策略是，每次定时任务刷到数据库之后，就从缓存里清除
			// 当这里再次用到的时候再从数据库去查询，以保证数据一致性
			if (conn.exists(likeCollectionKey)) {
                if (count == 1)
                    conn.sadd(likeCollectionKey, memberId.toString());
                else
                    conn.srem(likeCollectionKey, memberId.toString());
                conn.expire(likeCollectionKey, expireTime);
            }
			{
                synchronized (this) {
                    if (!conn.exists(likeCollectionKey)) {
                        List<Long> memberIds = appMemberBehaviorDao.listMemberIdsByContentIdAndBehaviorType(contentId,
                                bevType);
                        if (memberIds != null && memberIds.size() > 0) {
                            List<String> memberString = memberIds.stream().map(val -> val.toString())
                                    .collect(Collectors.toList());
                            String[] memberArray = new String[memberString.size()];
                            memberString.toArray(memberArray);
                            conn.sadd(likeCollectionKey, memberArray);
                        }
                    }
                }
            }
		} catch (BeansException e) {
			log.error("error!:{}",e);
		} finally {
			if(conn!=null)
				conn.close();
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
