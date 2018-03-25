/*
package com.hww.app.common.manager.impl;

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
import com.hww.sns.common.dto.SnsTopicDto;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
//@Service("appMemberBehaviorCountMngImpl")
public class AppMemberBehaviorCountMngImpl0323
		extends BaseEntityMngImpl<Long, AppMemberBehaviorCount, AppMemberBehaviorCountDao>
		implements AppMemberBehaviorCountMng {
	private static final Logger log = LoggerFactory.getLogger("AppMemberBehaviorCountMngImplD.class");

	@Autowired
	private AppMemberBehaviorCountDao appMemberBehaviorCountDao;

	@Autowired
	private SnsFeignClient snsFeignClient;
	@Autowired
	private AppMemberBehaviorDao appMemberBehaviorDao;

	private Lock lockOfComment = new ReentrantLock();

	*/
/**
	 * 过期时间
	 *//*

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

	*/
/**
	 * 把点赞数量耦合在主题里，虽然数据冗余能提高查询效率，但是这种方式开发起来相当复杂，这种方式不具备扩展性，没增加一个类型，就需要重新去写对应类型的实现
	 * 点赞redis实现
	 * @param contentId
	 * @param bevType
	 * @param plateType
	 * @param count
	 * @param memberId
	 *//*

	private void addLikeCount(Long contentId, Integer bevType, Integer plateType, int count, Long memberId) {
		Jedis conn = null;
		String likedHistoryIncrementKey = RedisKey.LikedHistoryIncrement.getValue();
		String likedHistoryCollectionKey = RedisKey.LikedHistoryCollection.getValue();
		String contentIdStr = contentId.toString();
		try {
			conn = JedisPoolUtil.getConnection();
			String topicKey = RedisKey.SnsTopic.getValue() + contentIdStr;
			String upnum = conn.hget(topicKey, "upNum");
			// 缓存里没有则查询数据库
			if (upnum == null) {
				Double score = conn.zscore(likedHistoryIncrementKey, contentIdStr);
				// 第一次点赞，查数据库
				if (score == null) {
					SnsTopic snsTopic = getSnsTopicFromDataBase(contentId);
					if (snsTopic == null)
						return;
					snsTopic.setUpNum(snsTopic.getUpNum() + count);
					Double commentNumber = conn.zscore(RedisKey.CommentedHistoryIncrement.getValue(), contentIdStr);
					if (commentNumber != null) {
						// 重新刷入缓存时需要把评论数一并刷新，不然会出现数据不一致
						snsTopic.setCommentNum(snsTopic.getCommentNum() + commentNumber.intValue());
					}
					conn.hmset(topicKey, BeanMapper.mapBeanToStringMap(snsTopic));
					conn.zadd(likedHistoryCollectionKey, snsTopic.getUpNum(), contentIdStr);
					conn.zadd(likedHistoryIncrementKey, count, contentIdStr);
				} else {// 有点赞记录，说明丢数据数据，查数据库，再加上增量,再刷回缓存
					SnsTopic snsTopic = getSnsTopicFromDataBase(contentId);
					if (snsTopic == null)
						return;
					snsTopic.setUpNum(snsTopic.getUpNum() + score.intValue());
					Double commentNumber = conn.zscore(RedisKey.CommentedHistoryIncrement.getValue(), contentIdStr);
					if (commentNumber != null) {
						// 重新刷入缓存时需要把评论数一并刷新，不然会出现数据不一致
						snsTopic.setCommentNum(snsTopic.getCommentNum() + commentNumber.intValue());
					}
					conn.hmset(topicKey, BeanMapper.mapBeanToStringMap(snsTopic));
					conn.zincrby(likedHistoryCollectionKey, count, contentIdStr);
					conn.zincrby(likedHistoryIncrementKey, count, contentIdStr);

				}

			} else {// 缓存里有数据，进行下一波判断
				Double score = conn.zscore(likedHistoryCollectionKey, contentIdStr);
				// 有点赞记录
				if (score != null) {
					// 点赞数相等，没有丢数据
					if (score.equals(Double.parseDouble(upnum))) {
						conn.zincrby(likedHistoryCollectionKey, count, contentIdStr);
						conn.zincrby(likedHistoryIncrementKey, count, contentIdStr);
						conn.hincrBy(topicKey, "upNum", count);
					} else {//点赞数不相等，说明中间数据过期过
						Double incrementNumber = conn.zscore(likedHistoryIncrementKey, contentIdStr);
						SnsTopic snsTopic = getSnsTopicFromDataBase(contentId);
						if (snsTopic == null)
							return;
						snsTopic.setUpNum(snsTopic.getUpNum() + incrementNumber.intValue());
						Double commentNumber = conn.zscore(RedisKey.CommentedHistoryIncrement.getValue(), contentIdStr);
						if (commentNumber != null) {
							// 重新刷入缓存时需要把评论数一并刷新，不然会出现数据不一致
							snsTopic.setCommentNum(snsTopic.getCommentNum() + commentNumber.intValue());
						}
						conn.hmset(topicKey, BeanMapper.mapBeanToStringMap(snsTopic));
						conn.zincrby(likedHistoryCollectionKey, count, contentIdStr);
						conn.zincrby(likedHistoryIncrementKey, count, contentIdStr);

					}
				} else {// 没有点赞记录
					conn.zincrby(likedHistoryCollectionKey, Double.parseDouble(upnum), contentIdStr);
					conn.zincrby(likedHistoryIncrementKey, count, contentIdStr);
					conn.hincrBy(topicKey, "upNum", count);
				}

			}
			//用户行为刷入缓存
			writeMemberBehaviorToCache(conn,contentId, bevType, plateType, count, memberId);
			String likeCollectionKey = RedisKey.ContentLikedCollection.getValue() + contentId;
			// 文章被点赞的集合很有可能过期，所以这里采用的策略是，每次定时任务刷到数据库之后，就从缓存里清除
			// 当这里再次用到的时候再从数据库去查询，以保证数据一致性
			if (conn.exists(likeCollectionKey)) {
				if (count == 1)
					conn.sadd(likeCollectionKey, memberId.toString());
				else
					conn.srem(likeCollectionKey, memberId.toString());
				//conn.expire(likeCollectionKey, expireTime);
			}
			{
				synchronized (contentIdStr) {
					if (!conn.exists(likeCollectionKey)) {
						List<Long> memberIds = appMemberBehaviorDao.listMemberIds 1ByContentIdAndBehaviorType(contentId,
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
			log.error("error!:{}", e);
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	*/
/**
	 * 用户评论行为存储到redis实现
	 * @param contentId
	 * @param bevType
	 * @param plateType
	 * @param count
	 * @param memberId
	 *//*

	private void addCommentCount(Long contentId, Integer bevType, Integer plateType, int count, Long memberId) {
		Jedis conn = null;
		try {
			conn = JedisPoolUtil.getConnection();
			String commentKey = RedisKey.SnsComment.getValue() + contentId;
			String oldCount = conn.hget(commentKey, "commentNum");
			if (oldCount == null) {
				lockOfComment.lock();
				try {
					if (oldCount == null) {
						SnsPostVo postVo = snsFeignClient.postDetail(contentId);
						if (postVo == null) {
							conn.hset(commentKey, "commentNum", "0");
							conn.expire(commentKey, 2);
							oldCount = "0";
						} else {
							SnsPostDto postDto = new SnsPostDto();
							BeanUtils.copyProperties(postVo, postDto);
							conn.hmset(commentKey, BeanMapper.mapBeanToStringMap(postDto));
							oldCount = postDto.getCommentNum().toString();
						}
					}
				} catch (BeansException e) {
					throw new RuntimeException(e);
				} finally {
					lockOfComment.unlock();
				}

			}
			if (count == -1 && Integer.parseInt(oldCount) == 0)
				return;
			conn.hincrBy(commentKey, "commentNum", count);
			// 记录被评论过的文章，方便定时任务把数据刷新到数据库
			conn.sadd(RedisKey.LikedHistoryCollection.getValue(), contentId + ":" + bevType + ":" + plateType);
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	private SnsTopic getSnsTopicFromDataBase(Long contentId) {
		SnsTopicVo snsTopicVo = snsFeignClient.topicDetail(contentId);
		if (snsTopicVo == null)
			return null;
		SnsTopic snsTopic = new SnsTopic();
		BeanUtils.copyProperties(snsTopicVo, snsTopic);
		return snsTopic;
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
*/
