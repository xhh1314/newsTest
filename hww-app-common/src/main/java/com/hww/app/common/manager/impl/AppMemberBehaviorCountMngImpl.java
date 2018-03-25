package com.hww.app.common.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
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
	 * 过期时间 7天
	 */
	private final int expireTime = 7 * 24 * 60;

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
		// 查看新闻的用户行为的情况（新闻主要是看有没有收藏,是否感兴趣等,这块先直接从数据库查询）
		if (plateType.equals(HwwConsts.PlateType.news)) {
			List<AppMemberBehaviorCount> appMemberBehaviorCountList = appMemberBehaviorCountDao
					.loadByContentId(contentId, plateType);
			if (appMemberBehaviorCountList != null && appMemberBehaviorCountList.size() > 0) {
				appMemberBehaviorCountDtoList = BeanMapper.mapList(appMemberBehaviorCountList,
						AppBehaviorCountDto.class);
			}
			return appMemberBehaviorCountDtoList;
		}
		// 查看新鲜事或者评论的用户行为情况
		AppMemberBehaviorCount commentBehaviorCount = null;
		AppMemberBehaviorCount likesBehaviorCount = null;

		Jedis conn = null;
		try {
			conn = JedisPoolUtil.getConnection();

			// ---------处理评论数---------
			Double commentNumber = conn.zscore(RedisKey.CommentedHistoryCollection.getValue(),
					contentId + ":" + plateType);
			if (commentNumber == null) {
				commentBehaviorCount = appMemberBehaviorCountDao.getAppMemeberBehaviorCount(contentId,
						HwwConsts.Behavior.b3_pl, plateType);
				if (commentBehaviorCount == null) {
					conn.zadd(RedisKey.CommentedHistoryCollection.getValue(), 0, contentId + ":" + plateType);
					commentNumber = new Double(0);
				} else {
					conn.zadd(RedisKey.CommentedHistoryCollection.getValue(), commentBehaviorCount.getBevCount(),
							contentId + ":" + plateType);
					commentNumber = new Double(commentBehaviorCount.getBevCount());
				}
			}
			// 不是从数据库查询的情况，需要new个对象
			if (commentBehaviorCount == null)
				commentBehaviorCount = new AppMemberBehaviorCount();
			commentBehaviorCount.setContentId(contentId);
			commentBehaviorCount.setBevType(HwwConsts.Behavior.b3_pl);
			commentBehaviorCount.setPlateType(plateType);
			commentBehaviorCount.setBevCount(commentNumber.intValue());

			// -------处理点赞数--------
			Double likedNumber = conn.zscore(RedisKey.LikedHistoryCollection.getValue(), contentId + ":" + plateType);
			if (likedNumber == null) {
				likesBehaviorCount = appMemberBehaviorCountDao.getAppMemeberBehaviorCount(contentId,
						HwwConsts.Behavior.b1_dz, plateType);
				if (likesBehaviorCount == null) {
					conn.zadd(RedisKey.LikedHistoryCollection.getValue(), 0, contentId + ":" + plateType);
					likedNumber = new Double(0);
				} else {
					conn.zadd(RedisKey.LikedHistoryCollection.getValue(), likesBehaviorCount.getBevCount(),
							contentId + ":" + plateType);
					likedNumber = new Double(likesBehaviorCount.getBevCount());
				}
			}
			// 不是从数据库查询的情况，需要new个对象
			if (likesBehaviorCount == null)
				likesBehaviorCount = new AppMemberBehaviorCount();
			likesBehaviorCount.setContentId(contentId);
			likesBehaviorCount.setBevType(HwwConsts.Behavior.b1_dz);
			likesBehaviorCount.setPlateType(plateType);
			likesBehaviorCount.setBevCount(likedNumber.intValue());

			// ------转换成dto------
			AppBehaviorCountDto commentAppBehaviorCountDto = new AppBehaviorCountDto();
			AppBehaviorCountDto likedAppBehaviorCountDto = new AppBehaviorCountDto();
			BeanUtils.copyProperties(commentBehaviorCount, commentAppBehaviorCountDto);
			BeanUtils.copyProperties(likesBehaviorCount, likedAppBehaviorCountDto);
			appMemberBehaviorCountDtoList.add(commentAppBehaviorCountDto);
			appMemberBehaviorCountDtoList.add(likedAppBehaviorCountDto);
			return appMemberBehaviorCountDtoList;
		} catch (BeansException e) {
			log.error("查询用户行为发生异常!{}", e);
			return Lists.newArrayList();
		} finally {
			if (conn != null)
				conn.close();
		}
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
			conn = JedisPoolUtil.getConnection();
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
			// ----------点赞历史记录存储到缓存-----------
			conn.zincrby(likedHistoryCollectionKey, number.intValue() + count, memberKey);

			// -------------用户行为存储-------------
			writeMemberBehaviorToCache(conn, contentId, bevType, plateType, count, memberId);

			// ---------------内容的点赞用户集合存储----------------
			String contentLikedCollectionKey = RedisKey.ContentLikedCollection.getValue() + memberKey;
			Boolean likedCollectionExist = conn.exists(contentLikedCollectionKey);
			// 缓存中不存在,则从数据库刷出文章被点赞的memberId,以保证数据一致性
			if (!likedCollectionExist) {
				List<Long> memberIds = appMemberBehaviorDao.listMemberIdsOfContentLiked(contentId, plateType,
						HwwConsts.Behavior.b1_dz);
				if (memberIds == null || memberIds.isEmpty()) {
					conn.sadd(contentLikedCollectionKey, "-1");
				} else {
					List<String> stringList = memberIds.parallelStream().map(val -> val.toString())
							.collect(Collectors.toList());
					conn.sadd(contentLikedCollectionKey, (String[]) stringList.toArray());

				}
				conn.expire(contentLikedCollectionKey, expireTime);
			}
			if (count == -1) {
				conn.srem(contentLikedCollectionKey, memberId.toString());
			} else if (count == 1) {
				conn.sadd(contentLikedCollectionKey, memberId.toString());
			}

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
			conn = JedisPoolUtil.getConnection();
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
			if (conn != null)
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
