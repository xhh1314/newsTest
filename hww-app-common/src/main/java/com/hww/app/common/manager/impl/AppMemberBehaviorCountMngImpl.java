package com.hww.app.common.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import com.hww.base.common.util.Finder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.dto.AppBehaviorCountDto;
import com.hww.app.common.entity.AppMemberBehavior;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.app.common.manager.AppMemberBehaviorCountMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.TimeUtils;
import com.hww.framework.common.constant.HwwConsts;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.sns.api.SnsFeignClient;
import com.hww.sns.common.dto.SnsPostDto;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class AppMemberBehaviorCountMngImpl
		extends BaseEntityMngImpl<Long, AppMemberBehaviorCount, AppMemberBehaviorCountDao>
		implements AppMemberBehaviorCountMng {
	private static final Logger log = LoggerFactory.getLogger("AppMemberBehaviorCountMngImpl.class");

	@Autowired
	private AppMemberBehaviorCountDao appMemberBehaviorCountDao;

	@Autowired
	private SnsFeignClient snsFeignClient;
	@Autowired
	private AppMemberBehaviorDao appMemberBehaviorDao;

	private Lock lockOfComment = new ReentrantLock();

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
			return;

		}
		// 评论操作
		if (bevType == HwwConsts.Behavior.b3_pl) {
			addCommentCount(contentId, bevType, plateType, count, memberId);
			return;
		}
		AppMemberBehaviorCount behaviorCount = appMemberBehaviorCountDao.loadByContentIdAndBevType(contentId, bevType,
				plateType);
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

	/**
	 * 把点赞数量耦合在主题里，虽然数据冗余能提高查询效率，但是这种方式开发起来相当复杂，这种方式不具备扩展性，没增加一个类型，就需要重新去写对应类型的实现
	 * 点赞redis实现
	 * @param contentId
	 * @param bevType
	 * @param plateType
	 * @param count
	 * @param memberId
	 */
	private void addLikeCount(Long contentId, Integer bevType, Integer plateType, Integer count, Long memberId) {
		Jedis conn = null;
		try {
			conn=JedisPoolUtil.getConnection();
			String likedHistoryCollectionKey = RedisKey.LikedHistoryCollection.getValue();
			String memberKey = contentId + ":" + plateType;
			Double number = conn.zscore(likedHistoryCollectionKey, memberKey);
			if (number == null) {
				synchronized (memberKey) {
					if (number == null) {
						Integer numberFromDataBase = appMemberBehaviorCountDao.getCountByBehaviorAndPlate(contentId,
								bevType, plateType);
						if (numberFromDataBase == null)
							conn.zadd(likedHistoryCollectionKey, 0, memberKey);
						else
							number = numberFromDataBase.doubleValue();
					}
				}
			}
			if (number == 0 && count == -1) {
				return;
			}
			conn.zincrby(likedHistoryCollectionKey, number.intValue() + count, memberKey);
			writeMemberBehaviorToCache(conn, contentId, bevType, plateType, count, memberId);
			if (count == 1)
				conn.sadd(RedisKey.ContentLikedCollection.getValue() + memberKey, memberId.toString());
			if (count == -1)
				conn.srem(RedisKey.ContentLikedCollection.getValue() + memberKey, memberId.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	/**
	 * 用户评论行为存储到redis实现
	 * @param contentId
	 * @param bevType
	 * @param plateType
	 * @param count
	 * @param memberId
	 */
	private void addCommentCount(Long contentId, Integer bevType, Integer plateType, int count, Long memberId) {
		Jedis conn = null;
		try {
			conn=JedisPoolUtil.getConnection();
			String commentHistoryCollectionKey = RedisKey.CommentedHistoryCollection.getValue();
			String memberKey = contentId + ":" + plateType;
			Double number = conn.zscore(commentHistoryCollectionKey, memberKey);
			if (number == null) {
                synchronized (memberKey) {
                    if (number == null) {
                        Integer numberFromDataBase = appMemberBehaviorCountDao.getCountByBehaviorAndPlate(contentId,
                                bevType, plateType);
                        if (numberFromDataBase == null)
                            conn.zadd(commentHistoryCollectionKey, 0, memberKey);
                        else
                            number = numberFromDataBase.doubleValue();
                    }
                }
            }
			if (count != 1)
                throw new RuntimeException("评论数量不能为非1的值！");
			conn.zincrby(commentHistoryCollectionKey, number.intValue() + count, memberKey);
			writeMemberBehaviorToCache(conn, contentId, bevType, plateType, count, memberId);
		} catch (RuntimeException e) {
			throw new RuntimeException("增加评论数发生异常！");
		} finally {
			if (conn!=null)
				conn.close();
		}

	}

	private void writeMemberBehaviorToCache(Jedis conn, Long contentId, Integer bevType, Integer plateType, int count,
			Long memberId) {
		AppMemberBehavior memberBehavior = new AppMemberBehavior();
		memberBehavior.setBevType(bevType);
		memberBehavior.setPlateType(plateType);
		memberBehavior.setBevValue(count);
		memberBehavior.setContentId(contentId);
		memberBehavior.setMemberId(memberId);
		memberBehavior.setCreateTime(TimeUtils.getDateToTimestamp());
		conn.lpush(RedisKey.UserBehavior.getValue(), JSON.toJSONString(memberBehavior));
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
