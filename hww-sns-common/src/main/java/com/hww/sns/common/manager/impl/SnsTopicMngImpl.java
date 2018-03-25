package com.hww.sns.common.manager.impl;

import com.google.common.collect.Lists;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.sns.common.dao.SnsTopicDao;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.SnsTopicDto;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.manager.SnsTopicMng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service("snsTopicMng")
public class SnsTopicMngImpl extends BaseEntityMngImpl<Long, SnsTopic, SnsTopicDao> implements SnsTopicMng {

	SnsTopicDao snsTopicDao;
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	public void setSnsTopicDao(SnsTopicDao snsTopicDao) {
		super.setEntityDao(snsTopicDao);
		this.snsTopicDao = snsTopicDao;
	}
	private final int expireTimeSeven =7*24*60;

	@Override
	public SnsTopicDto saveTopic(SnsTopicDto dto) {
		SnsTopic snsTopic = new SnsTopic();
		BeanUtils.copyProperties(dto, snsTopic);
		snsTopic = snsTopicDao.save(snsTopic);
		BeanUtils.copyProperties(snsTopic, dto);
		Map<String, String> map = BeanMapper.mapBeanToStringMap(dto);
		Jedis conn = JedisPoolUtil.getConnection();
		try {
			conn.hmset(RedisKey.SnsTopic.getValue() + dto.getTopicId(), map);
			conn.expire(RedisKey.SnsTopic.getValue() + dto.getTopicId(), expireTimeSeven);
			conn.zadd(RedisKey.UserTopic.getValue() + dto.getMemberId(), dto.getCreateTime().getTime(),
					dto.getTopicId().toString() + ":" + dto.getCreateTime().getTime());
		} catch (Exception e) {
			log.error("exception:{}", e);
		} finally {
			conn.close();
		}
		return dto;
	}

	@Override
	public Long loadTopicIdForQueryPostByNewsId(Long newsId) {
		if (newsId == null) {
			return null;
		}
		Finder f = Finder.create(" SELECT topic_id  as newsId");
		f.append(" FROM sns_topic");
		f.append(" where relatednews_id = :newsId");
		f.append(" and  forum_id=2 ");
		f.setParam("newsId", newsId);
		List<Map<String, Object>> list = (List<Map<String, Object>>) snsTopicDao.findJoin(f, Map.class);
		if (list == null || list.isEmpty()) {
			return null;
		}
		Long topicId = Long.parseLong(list.get(0).get("newsId").toString());
		return topicId;
	}

	@Override
	public List<SnsTopic> loadTopicListByAuthorMemberId(HBaseSnsQueryDto queryDto) {
		List<SnsTopic> snsTopics;
		List<SnsTopicDto> snsTopicDtoList = new ArrayList<>(16);
		Long memberId = queryDto.getMemberId();
		Long authorId = queryDto.getAuthorMemberId();
		Jedis conn = null;
		Integer pageSize;
		List<SnsTopic> snsTopicListOfMiss;
		try {
			conn = JedisPoolUtil.getConnection();
			pageSize = queryDto.getPageSize();
			long start = (queryDto.getPageNo() - 1) * pageSize;
			long end = start + pageSize - 1;
			Set<String> topicIdStr = conn.zrevrange(RedisKey.UserTopic.getValue() + authorId, start, end);
			if (topicIdStr == null || topicIdStr.size() == 0) {
				queryDto.setPageSize(50);
				queryDto.setPageNo(1);
				List<SnsTopic> newTopicList = snsTopicDao.listTopicByAuthorMemberId(queryDto);
				for (SnsTopic val : newTopicList) {
					conn.zadd(RedisKey.UserTopic.getValue() + val.getMemberId(), val.getCreateTime().getTime(),
							val.getTopicId().toString() + ":" + val.getCreateTime().getTime());
				}
			}
			List<Long> topicIdMiss = new ArrayList<>(8);
			// 数据库缓存都没有查到用户的新鲜事的情况
			if (topicIdStr == null || topicIdStr.size() == 0) {
				return Lists.newArrayList();
			}
			for (String topicId : topicIdStr) {
				Map<String, String> topicBean = conn.hgetAll(RedisKey.SnsTopic.getValue() + topicId);
				if (topicBean == null) {
					topicIdMiss.add(Long.parseLong(topicId));
				} else {
					SnsTopicDto snsTopicDto = BeanMapper.mapToBean(topicBean, SnsTopicDto.class);
					snsTopicDtoList.add(snsTopicDto);
				}
			}

			snsTopicListOfMiss = snsTopicDao.listSnsTopicByIds(topicIdMiss);
			for (SnsTopic val : snsTopicListOfMiss) {
				conn.hmset(RedisKey.SnsTopic.getValue() + val.getTopicId(), BeanMapper.mapBeanToStringMap(val));
			}
		} finally {
			if (conn != null)
				conn.close();
		}

		List<SnsTopicDto> snsTopicDtoListMiss = BeanMapper.mapList(snsTopicListOfMiss, SnsTopicDto.class);
		snsTopicDtoList.addAll(snsTopicDtoListMiss);
		// 最后过滤并排序
		snsTopicDtoList = snsTopicDtoList.stream().filter((val) -> {
			if (queryDto.getMemberId() == queryDto.getAuthorMemberId())
				return val.getAuditStatus() != 0;
			else
				return val.getShowStatus() == 1;
		}).sorted((v1, v2) -> v2.getCreateTime().compareTo(v1.getCreateTime())).collect(Collectors.toList());
		snsTopics = BeanMapper.mapList(snsTopicDtoList, SnsTopic.class);
		// 如果一页内容不足10行，则递归获取数据直到凑够10页
		while (snsTopics.size() < pageSize) {
			queryDto.setPageNo(queryDto.getPageNo() + 1);
			List<SnsTopic> snsTopics1 = loadTopicListByAuthorMemberId(queryDto);
			if (snsTopics1 == null || snsTopics1.size() == 0)
				break;
			if (snsTopics1.size() + snsTopics.size() >= pageSize) {
				snsTopics.addAll(snsTopics1.subList(0, pageSize - snsTopics.size() - 1));
				break;
			}
		}
		return snsTopics;
	}

	@Override
	public List<SnsTopic> loadConcernUserTopics(HBaseSnsQueryDto snsQueryDto, List<Long> followMemberIds) {
		// 如果关注的人太多，则直接走数据库查询
		if (followMemberIds.size() > 100) {
			return snsTopicDao.loadConcernUserTopics(snsQueryDto, followMemberIds);
		}
		Jedis conn = null;
		try {
			conn = JedisPoolUtil.getConnection();
			Pipeline pipeline = conn.pipelined();
			int begin = (snsQueryDto.getPageNo() - 1) * snsQueryDto.getPageSize();
			int end = begin + snsQueryDto.getPageSize() - 1;
			Map<Long, Set<String>> responseMap = new HashMap<>(32);
			for (Long id : followMemberIds) {
				String key = RedisKey.SnsTopic.getValue() + id;
				Response<Set<String>> response = pipeline.zrevrange(key, begin, end);
				if (response != null && response.get() != null)
					responseMap.put(id, response.get());
			}
			pipeline.sync();
			List<Long> snsTopicIdMiss = new LinkedList<>();
			// 从redis查出的集合
			List<SnsTopic> topicsFromRedis = new LinkedList<>();
			for (Long id : followMemberIds) {
				Set<String> topicIds = responseMap.get(id);
				if (topicIds == null)
					snsTopicIdMiss.add(id);
				for (String str : topicIds) {
					String[] strArray = str.split(":");
					SnsTopic topic = new SnsTopic();
					topic.setTopicId(Long.parseLong(strArray[0]));
					topic.setCreateTime(new Timestamp(Long.parseLong(strArray[1])));
					topicsFromRedis.add(topic);
				}
			}
			// 确实有miss的情况再查数据库
			if (snsTopicIdMiss.size() > 0) {
				List<SnsTopic> topicMiss = snsTopicDao.listTopicByMemeberIds(snsTopicIdMiss);
				Pipeline pipeline1 = conn.pipelined();
				for (SnsTopic topic : topicMiss) {
					pipeline1.hmset(RedisKey.SnsTopic.getValue() + topic.getTopicId(),
							BeanMapper.mapBeanToStringMap(topic));
					pipeline1.zadd(RedisKey.UserTopic.getValue() + topic.getMemberId(), topic.getCreateTime().getTime(),
							topic.getTopicId() + ":" + topic.getCreateTime());
					// 数据库查出的内容也加入
					topicsFromRedis.add(topic);
				}
				pipeline1.sync();
			}
			if (topicsFromRedis.size() == 0)
				return Lists.newArrayList();
			// 排序
			List<SnsTopic> finallyTopic = topicsFromRedis.parallelStream().sorted((o1, o2) -> {
				long val1 = o1.getCreateTime().getTime();
				long val2 = o2.getCreateTime().getTime();
				if (val1 < val2)
					return 1;
				else if (val1 > val2)
					return -1;
				else
					return 0;
			}).collect(Collectors.toList()).subList(begin, end);
			return finallyTopic;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	@Override
	public Integer loadUserTopicCount(Long memberId, Integer topicType) {
		if (memberId == null) {
			return 0;
		}
		Finder f = Finder.create(" SELECT count(1) as cu");
		f.append(" FROM sns_topic");
		f.append(" where member_id = :memberId");
		f.append(" and show_status = 1 ");
		f.append(" and status = 1 ");
		f.append(" and forum_id=0 ");
		if (topicType != null) {
			f.append(" and topicType = :topicType");
			f.setParam("topicType", topicType);
		}
		f.setParam("memberId", memberId);
		List<Map<String, Object>> list = (List<Map<String, Object>>) snsTopicDao.findJoin(f, Map.class);
		Integer cu = Integer.parseInt(list.get(0).get("cu").toString());
		return cu;
	}

	@Override
	public Integer queryTopicCountsByNewId(Long newId) {
		Finder f = Finder.create("select count(topicId) from SnsTopic where 1=1");
		f.append(" and relatednewsId=:newId");
		f.append(" and forumId=0 ");// 全部是新鲜事板块
		f.append(" and status=1 ");// 状态未删除
		f.append(" and topicType =1 ");// 标记为爆料的新鲜事
		f.append(" and showStatus = 1");// 审核通过的
		f.setParam("newId", newId);
		return Integer.parseInt(find(f).get(0).toString());
	}

	@Override
	public List<SnsTopic> loadTopicListByNewsId(HBaseSnsQueryDto queryDto) {
		Finder f = Finder.create("from SnsTopic where 1=1");
		f.append(" and relatednewsId=:newId");
		f.append(" and forumId=0 ");// 全部是新鲜事板块
		f.append(" and status=1 ");// 状态未删除
		f.append(" and topicType =1 ");// 标记为爆料的新鲜事
		f.append(" and showStatus = 1 ");// 审核通过的
		f.append(" order by createTime desc");
		f.setParam("newId", queryDto.getNewsId());
		if (queryDto.getPageNo() != null && queryDto.getPageSize() != null) {
			f.setFirstResult((queryDto.getPageNo() - 1) * queryDto.getPageSize());
			f.setMaxResults(queryDto.getPageSize());
		}
		// f.setMaxResults(3);
		// f.setFirstResult(0);
		List<SnsTopic> snsTopicList = (List<SnsTopic>) find(f);
		if (null == snsTopicList || snsTopicList.size() == 0) {
			return Lists.newArrayList();
		}
		return snsTopicList;
	}

	@Override
	public void deleteTopic(Long topicId) {
		Finder f = Finder.create(" update SnsTopic s ");
		f.append(" set ");
		f.append(" s.showStatus = 0, ");
		f.append(" s.status = 0 ");
		f.append(" where s.topicId = :topicId ");
		f.setParam("topicId", topicId);
		snsTopicDao.update(f);

	}

	@Override
	public List<SnsTopic> loadTopicListForRecomm(int limit) {
		Finder f = Finder
				.create("from SnsTopic where 1=1 " + " and forumId=0 " + " and showStatus = 1 " + " and status=1 ");
		f.append(" order by createTime desc  ");
		f.setMaxResults(limit);
		f.setFirstResult(1);
		List<SnsTopic> snsTopicList = (List<SnsTopic>) find(f);
		if (null == snsTopicList || snsTopicList.size() == 0) {
			return Lists.newArrayList();
		}
		return snsTopicList;
	}

	@Override
	public List<SnsTopic> loadTopicListByIds(List<Long> topicIds, int limit) {
		Finder f = Finder.create("from SnsTopic " + " where 1=1 " + " and status=1 " + " and showStatus = 1 "
				+ "  and topicId in (:topicIds ) ");
		f.append(" order by createTime desc  ");
		f.setParam("topicIds", topicIds);
		f.setMaxResults(limit);
		List<SnsTopic> snsTopicList = (List<SnsTopic>) find(f);
		if (null == snsTopicList || snsTopicList.size() == 0) {
			return Lists.newArrayList();
		}
		return snsTopicList;
	}

	@Override
	public List<SnsTopic> loadTopicListByIds(List<Long> topicIds) {
		if (topicIds == null || topicIds.isEmpty()) {
			return Lists.newArrayList();
		}
		String topicIdsx = topicIds.stream().map(val -> String.valueOf(val)).collect(Collectors.joining(","));

		Finder f = Finder.create("from SnsTopic where " + " status=1 " + " and showStatus = 1 "
				+ " and topicId in (:topicIds )" + "  order by  find_in_set(topicId,:topicIdsx)");
		f.setParam("topicIds", topicIds);
		f.setParam("topicIdsx", topicIdsx);
		List<SnsTopic> snsTopicList = (List<SnsTopic>) find(f);
		if (null == snsTopicList || snsTopicList.size() == 0) {
			return Lists.newArrayList();
		}
		return snsTopicList;
	}

	@Override
	public List<SnsTopic> loadTopicListByIdsShowAllStatus(List<Long> topicIds) {
		if (topicIds == null || topicIds.isEmpty()) {
			return Lists.newArrayList();
		}
		String topicIdsx = topicIds.stream().map(val -> String.valueOf(val)).collect(Collectors.joining(","));

		Finder f = Finder.create("from SnsTopic where " + " status=1 " + " and (auditStatus=1 or auditStatus=2)"
				+ " and topicId in (:topicIds )" + "  order by  find_in_set(topicId,:topicIdsx)");
		f.setParam("topicIds", topicIds);
		f.setParam("topicIdsx", topicIdsx);
		List<SnsTopic> snsTopicList = (List<SnsTopic>) find(f);
		if (null == snsTopicList || snsTopicList.size() == 0) {
			return Lists.newArrayList();
		}
		return snsTopicList;
	}

	@Override
	public Long loadMemberAuthorById(Long topicId) {
		Finder f = Finder.create("select memberId from SnsTopic where 1=1");
		f.append(" and topicId=:topicId");
		f.setParam("topicId", topicId);
		return Long.parseLong(find(f).get(0).toString());
	}

	@Override
	public void updateTopicUpNum(Long topicId, Integer upNum) {
		Finder finder=Finder.create("update SnsTopic set upNum=:upNum where topicId=:topicId");
		finder.setParam("upNum",upNum);
		finder.setParam("topicId",topicId);
		update(finder);
	}

	@Override
	public void updateTopicCommentNum(Long topicId, Integer upNum,Integer commentNum) {
		Finder finder=Finder.create("update SnsTopic set commentNum=:commentNum,upNum=:upNum where topicId=:topicId");
		finder.setParam("commentNum",commentNum);
		finder.setParam("upNum",upNum);
		finder.setParam("topicId",topicId);
		update(finder);
	}

	@Override
	public SnsTopic getSnsTopicFromDataBase(Long topicId) {
		return find(topicId);
	}
}
