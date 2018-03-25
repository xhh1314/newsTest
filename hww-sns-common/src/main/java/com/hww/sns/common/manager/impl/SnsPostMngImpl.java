package com.hww.sns.common.manager.impl;

import com.google.common.collect.Lists;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.sns.common.dao.SnsPostDao;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.SnsPostDto;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.manager.SnsPostMng;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.vo.PostListVo;
import com.hww.sns.common.vo.TopPostListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

@Service("snsPostMng")
public class SnsPostMngImpl extends BaseEntityMngImpl<Long, SnsPost, SnsPostDao> implements SnsPostMng {

	SnsPostDao snsPostDao;

	@Autowired
	public void setSnsPostDao(SnsPostDao snsPostDao) {
		super.setEntityDao(snsPostDao);
		this.snsPostDao = snsPostDao;
	}

	@Autowired
	SnsTopicMng snsTopicMng;
	// @Override
	// public List<SnsPost> myPosts(SnsTopic myTopicVo) {
	// List<SnsPost> myPosts = snsPostDao.myPosts(myTopicVo);
	// return myPosts;
	// }

	// @Override
	// public List<SnsPost> smallPostList(PostListVo vo) {
	//
	// return snsPostDao.smallPostList(vo);
	// }


	/**
	 * 过期时间 7天
	 */
	private final int expireTime=7*24*60;
	@Override
	public SnsPostDto saveSnsPost(SnsPost post) {
		SnsPost newPost = snsPostDao.save(post);
		SnsPostDto postDto = new SnsPostDto();
		BeanUtils.copyProperties(newPost, postDto);
		Jedis conn = null;
		try {
			conn = JedisPoolUtil.getConnection();
			conn.hmset(RedisKey.SnsTopic.getValue() + post.getPostId(), BeanMapper.mapBeanToStringMap(postDto));
			conn.expire(RedisKey.SnsTopic.getValue() + post.getPostId(),expireTime);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn!=null)
				conn.close();
		}
		return postDto;
	}

	@Override
	public List<SnsPost> loadPostListByTopicId(HBaseSnsQueryDto queryDto) {
		return snsPostDao.loadPostListByTopicId(queryDto);
	}

	// @Override
	// public List<SnsPost> smPostList(PostListVo vo) {
	//
	// return snsPostDao.smallPostList(vo);
	// }

	@Override
	public List<SnsPost> loadChildPostList(HBaseSnsQueryDto queryDto) {
		if (queryDto == null || queryDto.getPostId() == null) {
			return Lists.newArrayList();
		}

		return snsPostDao.loadChildPostList(queryDto);
	}

	@Override
	public SnsPost loadAuthorAndParentById(Long postId) {
		Finder f = Finder.create(
				"select member_id as memberId,parent_id as parentId,topic_id as topicId from sns_post where 1=1");
		f.append(" and post_id=:postId");
		f.setParam("postId", postId);
		SnsPost list = (SnsPost) snsPostDao.findJoin(f, SnsPost.class).get(0);
		return list;
	}

	@Override
	public void deletePost(Long postId) {
		// snsTopic.setAuditStatus(3);
		// //show_status 0不可见 1可见
		// snsTopic.setShowStatus(0);

		Finder f = Finder.create(" update SnsPost s ");
		f.append(" set");
		f.append(" s.showStatus = 0,");
		f.append(" s.status = 0 ");
		f.append(" where s.postId = :postId ");
		f.setParam("postId", postId);
		snsPostDao.update(f);

	}

	@Override
	public void deletePostByTopicId(Long topicId) {
		Finder f = Finder.create(" update SnsPost s ");
		f.append(" set");
		f.append(" s.showStatus =0,");
		f.append(" s.status = 0 ");
		f.append(" where s.topicId = :topicId ");
		f.setParam("topicId", topicId);
		snsPostDao.update(f);

	}

	@Override
	public Integer loadCountForNews(Long newsId, Integer showStatus) {
		Long topicId = snsTopicMng.loadTopicIdForQueryPostByNewsId(newsId);
		if (topicId == null) {
			return 0;
		}
		return loadCountForTopic(topicId, showStatus);
	}

	@Override
	public Integer loadCountForTopic(Long topicId, Integer showStatus) {
		Finder f = Finder.create("SELECT count(1) as cu");
		f.append(" FROM sns_post ");
		f.append(" where topic_id = :topic_id");
		// f.append(" and show_status =1 ");
		f.append("  and status =1 ");
		f.append("  and parent_id is null ");
		if (showStatus != null) {
			f.append("  and show_status =:showStatus ");
			f.setParam("showStatus", showStatus);
		}
		f.setParam("topic_id", topicId);
		List<Map<String, Object>> list = (List<Map<String, Object>>) snsPostDao.findJoin(f, Map.class);
		if (list == null) {
			return 0;
		}
		return Integer.parseInt(list.get(0).get("cu").toString());
	}

	@Override
	public Integer loadCountForPost(Long postId, Integer showStatus) {
		Finder f = Finder.create("SELECT count(1) as cu");
		f.append(" FROM sns_post ");
		f.append(" where parent_id = :parent_id");
		f.append("  and status =1 ");
		if (showStatus != null) {
			f.append("  and show_status =:showStatus ");
			f.setParam("showStatus", showStatus);
		}

		f.setParam("parent_id", postId);
		List<Map<String, Object>> list = (List<Map<String, Object>>) snsPostDao.findJoin(f, Map.class);
		if (list == null) {
			return 0;
		}
		return Integer.parseInt(list.get(0).get("cu").toString());
	}

	@Override
	public void updateLikesAndCommentNumToDataBase(Long postId, Integer upNum, Integer commentNum) {
		Finder f=Finder.create("update SnsPost set upNum=:upNum,commentNum=:commentNum where postId=:postId");
		f.setParam("commentNum",commentNum);
		f.setParam("upNum",upNum);
		f.setParam("postId",postId);
		update(f);
	}

	@Override
	public SnsPost getSnsPostFromDatabase(Long postId) {
		return find(postId);
	}
}
