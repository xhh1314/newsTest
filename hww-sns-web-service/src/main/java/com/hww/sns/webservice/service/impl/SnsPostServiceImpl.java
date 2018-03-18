package com.hww.sns.webservice.service.impl;

import com.google.common.collect.Lists;
import com.hww.app.api.AppFeignClient;
import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.Constants;
import com.hww.base.util.R;
import com.hww.base.util.TimeUtils;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.HSnsPostCreateDto;
import com.hww.sns.common.entity.SnsForum;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.manager.SnsForumMng;
import com.hww.sns.common.manager.SnsPostMng;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.LocationUtils;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.webservice.consts.SnsConsts;
import com.hww.sns.webservice.service.SnsPostService;
import com.hww.ucenter.api.UcenterFeignClient;
import com.hww.ucenter.common.dto.HUCenterFollowDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UserMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service("snsPostService")
@Transactional
public class SnsPostServiceImpl implements SnsPostService {

	@Autowired
	SnsPostMng snsPostMng;
	@Autowired
	SnsTopicMng snsTopicMng;
	@Autowired
	UcenterFeignClient userFeignClient;
	@Autowired
	AppFeignClient appFeignClient;

	@Autowired
	SnsForumMng snsForumMng;

	@Async
	private void sendMsg(UserMessageDto userMessageDto) {

		try {
			userFeignClient.messageSave(userMessageDto);
		} catch (Exception e) {

		}
	}

	@Override
	public SnsPostVo createTopicPost(HSnsPostCreateDto postCreateDto) {
		SnsPost snsPost = new SnsPost();
		try {
			BeanUtils.copyProperties(snsPost, postCreateDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		snsPost.setCommentNum(0L);
		snsPost.setUpNum(0L);
		// 0新提交未审核1审核通过3删除 === 0:新鲜事1:爆料2:新闻评论
		Integer topicType = postCreateDto.getTopicType();
		if (!SnsConsts.topicTypeList.contains(topicType)) {
			throw new HServiceLogicException("类型错误");
		}
		snsPost.setTopicType(topicType);
		snsPost.setCreateTime(TimeUtils.getDateToTimestamp());
		snsPost.setLastModifyTime(TimeUtils.getDateToTimestamp());
		SnsForum snsForum = snsForumMng.find(Long.valueOf(2));
		// autditFlow 0 先发后审 1 先审后发 ,,show_status 0不可见 1可见
		Integer autditFlow = snsForum.getAuditFlow();
		if (0 == autditFlow.intValue()) {// 设置可见状态
			snsPost.setShowStatus(1);
		} else {
			snsPost.setShowStatus(0);
		}
		// auditstatus 0审核未通过 1 审核通过 2 新提交
		snsPost.setAuditStatus(2);
		snsPost.setAuditFlow(snsForum.getAuditFlow());
		snsPost.setSiteId(Constants.siteId);
		snsPost.setStatus(Short.valueOf("1"));
		snsPostMng.save(snsPost);

		SnsPostVo snsPostVo = new SnsPostVo();
		try {
			BeanUtils.copyProperties(snsPostVo, snsPost);
		} catch (Exception e1) {
			// e1.printStackTrace();
		}
		UCenterMemberDto postAuthor = userFeignClient.findById(snsPost.getMemberId());
		if (postAuthor != null) {
			snsPostVo.setNickName(postAuthor.getNickName());
			snsPostVo.setSex(postAuthor.getSex());
			snsPostVo.setAvatar(postAuthor.getAvatar());
			if (1 == postAuthor.getSnsDisabled())
				throw new HServiceLogicException("用户被禁言！");
		}

		SnsTopic snsTopic = snsTopicMng.find(snsPost.getTopicId());
		// 0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5不感兴趣（不 感兴趣，内容太水，看过了）
		// 0 新闻 1 帖子/新鲜事 2 评论
		// 判断parent_id是否为空---判断跟帖或回复,如果parentid=postId，则为针对postId的回复
		if (postCreateDto.getParentId() != null) {// 评论的---评论
			// 评论回复行为加1
			AppBehaviorDto behaviorDto2 = new AppBehaviorDto(postCreateDto.getMemberId(), postCreateDto.getParentId(),
					AppBehaviorDto.BevType.b3_pl, AppBehaviorDto.BevValue.b1_ok, AppBehaviorDto.PlatType.b2_post);
			R res2 = appFeignClient.createBehavior(behaviorDto2);
			if (!res2.isOk()) {
				throw new HServiceLogicException(res2.getMsg());
			}

			try {
				SnsPost parentPost = snsPostMng.find(postCreateDto.getParentId());

				UserMessageDto msgDto = new UserMessageDto().setMemberId(parentPost.getMemberId())
						.setSendMemberId(snsPost.getMemberId()).setResourceId(postCreateDto.getTopicId())
						.setCommonId(snsPost.getId()).setSubjectContentId(snsTopic.getTopicId())
						.setBevType(UserMessageDto.BevType.b3_pl).setTargetType(UserMessageDto.TargetType.b2_pl);
				// 2018-0209-自己发的不再有爆料，全部为新鲜事--后台人员自己设置爆料
				msgDto.setSubjectType(UserMessageDto.SubjectType.b0_xxs);

				// if(0==snsTopic.getTopicType().intValue()) {
				// msgDto.setSubjectType(UserMessageDto.SubjectType.b0_xxs);
				// }else {
				// msgDto.setSubjectType(UserMessageDto.SubjectType.b1_bl);
				// }
				sendMsg(msgDto);
			} catch (Exception e) {
			}

		} else {// 直接是评论
			// 添加用户行为--评论
			AppBehaviorDto behaviorDto = new AppBehaviorDto(postCreateDto.getMemberId(), postCreateDto.getTopicId(),
					AppBehaviorDto.BevType.b3_pl, AppBehaviorDto.BevValue.b1_ok, AppBehaviorDto.PlatType.b1_topic);
			R res = appFeignClient.createBehavior(behaviorDto);
			if (!res.isOk()) {
				throw new HServiceLogicException(res.getMsg());
			}
			try {
				int topicTypex = snsTopic.getTopicType().intValue();
				UserMessageDto msgDto = new UserMessageDto().setMemberId(snsTopic.getMemberId())
						.setSendMemberId(snsPost.getMemberId()).setResourceId(snsTopic.getTopicId())// ResourceId===SubjectContentId
						.setCommonId(snsPost.getId()).setSubjectContentId(snsTopic.getTopicId())// ResourceId===SubjectContentId
						.setBevType(UserMessageDto.BevType.b3_pl);
				if (topicTypex == 0) {// 主题类型0:新鲜事1:爆料
					msgDto.setTargetType(UserMessageDto.TargetType.b0_xxs);
					msgDto.setSubjectType(UserMessageDto.SubjectType.b0_xxs);
				} else if (topicTypex == 1) {// 新鲜事
					msgDto.setTargetType(UserMessageDto.TargetType.b1_bl);
					msgDto.setSubjectType(UserMessageDto.SubjectType.b1_bl);
				}
				sendMsg(msgDto);
			} catch (Exception e) {
			}
		}
		return snsPostVo;
	}

	@Override
	public SnsPostVo createNewsPost(HSnsPostCreateDto postCreateDto) {
		SnsPost snsPost = new SnsPost();
		try {
			BeanUtils.copyProperties(snsPost, postCreateDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		snsPost.setCommentNum(0L);
		snsPost.setUpNum(0L);
		// 0新提交未审核1审核通过3删除

		Integer topicType = postCreateDto.getTopicType();
		if (!SnsConsts.postTypeList.contains(topicType)) {
			throw new HServiceLogicException("类型错误");
		}
		snsPost.setTopicType(topicType);

		SnsForum snsForum = snsForumMng.find(Long.valueOf(2));
		// autditFlow 0 先审后发 1 先发后审 ,,,show_status 0不可见 1可见
		Integer autditFlow = snsForum.getAuditFlow();
		if (0 == autditFlow.intValue()) {// 设置可见状态
			snsPost.setShowStatus(1);
		} else {
			snsPost.setShowStatus(0);
		}

		// auditstatus 0审核未通过 1 审核通过 2 新提交
		snsPost.setAuditStatus(2);
		snsPost.setAuditFlow(snsForum.getAuditFlow());
		snsPost.setSiteId(Constants.siteId);
		snsPost.setCreateTime(TimeUtils.getDateToTimestamp());
		snsPost.setLastModifyTime(TimeUtils.getDateToTimestamp());
		snsPost.setStatus(Short.valueOf("1"));
		Long newsId = postCreateDto.getNewsId();

		Long topicId = snsTopicMng.loadTopicIdForQueryPostByNewsId(newsId);
		if (topicId == null) {
			// 这时候topic是空的，只是标注是新闻的评论TopicType=2
			SnsTopic snsTopic = new SnsTopic();
			snsTopic.setRelatednewsId(newsId);
			snsTopic.setForumId(2L);
			snsTopic.setAuditStatus(1);
			snsTopic.setShowStatus(1);
			snsTopic.setTopicType(2);
			snsTopicMng.save(snsTopic);
			// 设置新的topicid
			topicId = snsTopic.getTopicId();
		}
		snsPost.setTopicId(topicId);// 设置新的topicid=--->用来关联是哪个新闻的评论

		snsPostMng.saveSnsPost(snsPost);
		SnsPostVo snsPostVo = new SnsPostVo();
		UCenterMemberDto postAuthor = userFeignClient.findById(snsPost.getMemberId());
		if (postAuthor != null) {
			snsPostVo.setNickName(postAuthor.getNickName());
			snsPostVo.setSex(postAuthor.getSex());
			snsPostVo.setAvatar(postAuthor.getAvatar());
			if (1 == postAuthor.getSnsDisabled())
				throw new HServiceLogicException("用户被禁言！");
		}
		try {
			BeanUtils.copyProperties(snsPostVo, snsPost);
		} catch (Exception e1) {
			// e1.printStackTrace();
		}
		// 0 新闻 1 帖子/新鲜事 2 评论
		// 判断parent_id是否为空---判断跟帖或回复,如果parentid=postId，则为针对postId的回复
		if (postCreateDto.getParentId() != null) {// 评论的回复
			// 添加用户行为--评论
			AppBehaviorDto behaviorDto = new AppBehaviorDto(postCreateDto.getMemberId(), postCreateDto.getParentId(),
					AppBehaviorDto.BevType.b3_pl, AppBehaviorDto.BevValue.b1_ok, AppBehaviorDto.PlatType.b2_post);
			R res = appFeignClient.createBehavior(behaviorDto);
			if (!res.isOk()) {
				throw new HServiceLogicException(res.getMsg());
			}
			try {
				SnsPost parentPost = snsPostMng.find(postCreateDto.getParentId());

				sendMsg(new UserMessageDto().setMemberId(parentPost.getMemberId())
						.setSendMemberId(postCreateDto.getMemberId()).setResourceId(parentPost.getPostId())
						.setCommonId(snsPost.getId())

						.setSubjectContentId(newsId)// --null
						.setSubjectType(UserMessageDto.SubjectType.b2_xw)//

						.setBevType(UserMessageDto.BevType.b3_pl).setTargetType(UserMessageDto.TargetType.b2_pl));
			} catch (Exception e) {
			}

		} else {// 直接是评论
				//// 评论类型(0:新鲜事评论1:爆料评论2:新闻评论)
			AppBehaviorDto behaviorDto = new AppBehaviorDto(postCreateDto.getMemberId(), postCreateDto.getNewsId(),
					AppBehaviorDto.BevType.b3_pl, AppBehaviorDto.BevValue.b1_ok, AppBehaviorDto.PlatType.b0_news);
			R res2 = appFeignClient.createBehavior(behaviorDto);
			if (!res2.isOk()) {
				throw new HServiceLogicException(res2.getMsg());
			}
		}
		return snsPostVo;
	}

	@Override
	public List<SnsPostVo> loadPostListByTopicId(HBaseSnsQueryDto queryDto) {
		if (queryDto.getTopicId() == null) {
			return Lists.newArrayList();
		}
		// 查询用户自己的信息
		UCenterMemberDto currentUser = userFeignClient.findById(queryDto.getMemberId());

		List<SnsPost> snsPostList = snsPostMng.loadPostListByTopicId(queryDto);

		List<SnsPostVo> snsPostVoList = Lists.newArrayList();

		for (SnsPost snsPost : snsPostList) {

			snsPostVoList.add(constructSnsPostVoForList(snsPost, currentUser, queryDto));
		}
		return snsPostVoList;

	}

	@Override
	public List<SnsPostVo> loadPostListByNewsId(HBaseSnsQueryDto queryDto) {

		Long newsId = queryDto.getNewsId();
		if (newsId == null) {
			return Lists.newArrayList();
		}
		Long topicId = snsTopicMng.loadTopicIdForQueryPostByNewsId(newsId);
		if (topicId == null) {
			return Lists.newArrayList();
		}
		queryDto.setTopicId(topicId);
		return loadPostListByTopicId(queryDto);
	}

	private SnsPostVo constructSnsPostVoForList(SnsPost snsPost, UCenterMemberDto currentUser,
			HBaseSnsQueryDto queryDto) {
		SnsPostVo snsPostVo = new SnsPostVo();

		try {
			BeanUtils.copyProperties(snsPostVo, snsPost);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			double distance = LocationUtils.getDistance(Double.valueOf(queryDto.getLatitude()),
					Double.valueOf(queryDto.getLongitude()), Double.valueOf(snsPost.getLatitude()),
					Double.valueOf(snsPost.getLongitude()));
			snsPostVo.setDistance(distance);
		} catch (Exception e) {
			snsPostVo.setDistance(null);
		}

		UCenterMemberDto postAuthor = userFeignClient.findById(snsPost.getMemberId());
		if (postAuthor != null) {
			snsPostVo.setNickName(postAuthor.getNickName());
			snsPostVo.setSex(postAuthor.getSex());
			snsPostVo.setAvatar(postAuthor.getAvatar());
		}

		Map<String, Integer> behaviorCountMap = appFeignClient.behaviorCount(snsPost.getPostId(),
				AppBehaviorDto.PlatType.b2_post);
		snsPostVo.setUpNum(behaviorCountMap.get("1"));
		// snsPostVo.setCommentNum(behaviorCountMap.get("3"));
		snsPostVo.setCollectNum(behaviorCountMap.get("2"));
		Integer postCount = snsPostMng.loadCountForPost(snsPost.getPostId(), 1);
		snsPostVo.setCommentNum(postCount == null ? 0 : postCount);

		// 是否收藏,点赞-
		// 用户行为类别用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
		// 当前用户登录，可以给自己点赞
		if (currentUser != null && currentUser.getMemberId() != null) {
			// if(currentUser!=null&&currentUser.getMemberId()!=null&&!currentUser.getMemberId().equals(snsPost.getMemberId()))
			// {
			String upFlag = appFeignClient.behaviorExist(
					new AppBehaviorDto(currentUser.getMemberId(), snsPost.getPostId(), AppBehaviorDto.BevType.b1_dz)
							.setPlateType(AppBehaviorDto.PlatType.b2_post));
			snsPostVo.setUp(upFlag);
		}

		// 计算用户关注状态---不是自己的帖子
		if (currentUser != null && currentUser.getMemberId() != null
				&& !currentUser.getMemberId().equals(snsPost.getMemberId())) {
			HUCenterFollowDto concern = userFeignClient.isConcern(currentUser.getMemberId(), snsPost.getMemberId());
			snsPostVo.setConcernType(concern.getConcernType() == null ? 0 : concern.getConcernType());
		} else {
			snsPostVo.setConcernType(0);
		}
		return snsPostVo;
	}

	@Override
	public SnsPostVo loadSnsPostDetail(Long postId) {
		SnsPostVo snsPostVo = new SnsPostVo();
		SnsPost snsPost = snsPostMng.find(postId);
		if (snsPost == null) {
			return snsPostVo;
		}
		try {
			BeanUtils.copyProperties(snsPostVo, snsPost);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		UCenterMemberDto postAuthor = userFeignClient.findById(snsPost.getMemberId());
		if (postAuthor != null) {
			snsPostVo.setNickName(postAuthor.getNickName());
			snsPostVo.setSex(postAuthor.getSex());
			snsPostVo.setAvatar(postAuthor.getAvatar());
		}

		Map<String, Integer> behaviorCountMap = appFeignClient.behaviorCount(snsPost.getPostId(),
				AppBehaviorDto.PlatType.b2_post);
		snsPostVo.setUpNum(behaviorCountMap.get("1"));
		// snsPostVo.setCommentNum(behaviorCountMap.get("3"));
		snsPostVo.setCollectNum(behaviorCountMap.get("2"));

		Integer postCount = snsPostMng.loadCountForPost(snsPost.getPostId(), 1);
		snsPostVo.setCommentNum(postCount == null ? 0 : postCount);
		return snsPostVo;
	}

	@Override
	public SnsPostVo loadSnsPostDetail(HBaseSnsQueryDto snsqueryDto) {
		SnsPostVo snsPostVo = new SnsPostVo();
		SnsPost snsPost = snsPostMng.find(snsqueryDto.getPostId());
		if (snsPost == null) {
			return snsPostVo;
		}
		try {
			BeanUtils.copyProperties(snsPostVo, snsPost);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			double distance = LocationUtils.getDistance(Double.valueOf(snsqueryDto.getLatitude()),
					Double.valueOf(snsqueryDto.getLongitude()), Double.valueOf(snsPost.getLatitude()),
					Double.valueOf(snsPost.getLongitude()));
			snsPostVo.setDistance(distance);
		} catch (Exception e) {
			snsPostVo.setDistance(null);
		}

		UCenterMemberDto postAuthor = userFeignClient.findById(snsPost.getMemberId());
		if (postAuthor != null) {
			snsPostVo.setNickName(postAuthor.getNickName());
			snsPostVo.setSex(postAuthor.getSex());
			snsPostVo.setAvatar(postAuthor.getAvatar());
		}

		Map<String, Integer> behaviorCountMap = appFeignClient.behaviorCount(snsPost.getPostId(),
				AppBehaviorDto.PlatType.b2_post);
		snsPostVo.setUpNum(behaviorCountMap.get("1"));
		// snsPostVo.setCommentNum(behaviorCountMap.get("3"));
		snsPostVo.setCollectNum(behaviorCountMap.get("2"));

		Integer postCount = snsPostMng.loadCountForPost(snsPost.getPostId(), 1);
		snsPostVo.setCommentNum(postCount == null ? 0 : postCount);

		UCenterMemberDto currentUser = userFeignClient.findById(snsqueryDto.getMemberId());
		// 是否收藏,点赞-
		// 用户行为类别用户行为类别0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
		// 当前用户登录，并且不是自己的帖子才会有是否关注收藏点赞的判断
		if (currentUser != null && currentUser.getMemberId() != null) {
			String upFlag = appFeignClient.behaviorExist(
					new AppBehaviorDto(currentUser.getMemberId(), snsPost.getPostId(), AppBehaviorDto.BevType.b1_dz)
							.setPlateType(AppBehaviorDto.PlatType.b2_post));
			snsPostVo.setUp(upFlag);
		}

		// 计算用户关注状态---不是自己的post
		if (currentUser != null && currentUser.getMemberId() != null
				&& !currentUser.getMemberId().equals(snsPost.getMemberId())) {
			HUCenterFollowDto concern = userFeignClient.isConcern(currentUser.getMemberId(), snsPost.getMemberId());
			snsPostVo.setConcernType(concern.getConcernType() == null ? 0 : concern.getConcernType());
		} else {
			snsPostVo.setConcernType(0);
		}

		List<SnsPost> childPostList = snsPostMng.loadChildPostList(snsqueryDto);
		if (childPostList == null || childPostList.isEmpty()) {
			return snsPostVo;
		}
		List<SnsPostVo> childPostVoList = Lists.newArrayList();
		for (SnsPost csnsPost : childPostList) {
			SnsPostVo childSnsPostVo = constructSnsPostVoForList(csnsPost, currentUser, snsqueryDto);
			childPostVoList.add(childSnsPostVo);
		}
		snsPostVo.setSnsPostVoList(childPostVoList);

		return snsPostVo;
	}

	@Override
	public List<SnsPostVo> loadChildPostList(HBaseSnsQueryDto snsqueryDto) {
		List<SnsPostVo> childPostVoList = Lists.newArrayList();
		UCenterMemberDto currentUser = null;
		if (snsqueryDto.getMemberId() != null) {
			currentUser = userFeignClient.findById(snsqueryDto.getMemberId());
		}

		List<SnsPost> childList = snsPostMng.loadChildPostList(snsqueryDto);

		if (childList == null || childList.isEmpty()) {
			return childPostVoList;
		}
		for (SnsPost csnsPost : childList) {
			SnsPostVo childSnsPostVo = constructSnsPostVoForList(csnsPost, currentUser, snsqueryDto);
			childPostVoList.add(childSnsPostVo);
		}
		return childPostVoList;
	}

	@Override
	public void deletePost(HBaseSnsQueryDto snsDeleteDto) {
		Long memberId = snsDeleteDto.getMemberId();
		Long postId = snsDeleteDto.getPostId();

		// SnsPost snsPost = snsPostMng.find(postId);
		// snsPost.setAuditStatus(3);
		// snsPostMng.update(snsPost);
		// Boolean delete = snsPostMng.delete(postId);

		SnsPost snsPost = snsPostMng.loadAuthorAndParentById(postId);
		if (snsPost == null) {
			throw new HServiceLogicException("未查询到数据");
		}
		if (!memberId.equals(snsPost.getMemberId())) {
			throw new HServiceLogicException("只能删除自己的动态");
		}
		snsPostMng.deletePost(postId);
		AppBehaviorDto behaviorDto = null;

		SnsTopic topic = snsTopicMng.find(snsPost.getTopicId());
		if (topic != null) {
			if (snsPost.getParentId() == null) {

			}

			if (2 == topic.getForumId()) {// 如果是个新闻评论

				if (snsPost.getParentId() == null) {// parenid==nuull说明是顶级评论，直接对新闻或者topic的评论
					behaviorDto = new AppBehaviorDto(memberId, topic.getRelatednewsId(), AppBehaviorDto.BevType.b3_pl,
							AppBehaviorDto.BevValue.b0_cancel, AppBehaviorDto.PlatType.b0_news);
				} else {// parenid!=null---说明是对评论的评论
					behaviorDto = new AppBehaviorDto(memberId, snsPost.getParentId(), AppBehaviorDto.BevType.b3_pl,
							AppBehaviorDto.BevValue.b0_cancel, AppBehaviorDto.PlatType.b2_post);
				}

			} else {// 如果是个topic评论
				if (snsPost.getParentId() == null) {// parenid==nuull说明是顶级评论，直接对新闻或者topic的评论
					behaviorDto = new AppBehaviorDto(memberId, snsPost.getTopicId(), AppBehaviorDto.BevType.b3_pl,
							AppBehaviorDto.BevValue.b0_cancel, AppBehaviorDto.PlatType.b1_topic);
				} else {// parenid!=null---说明是对评论的评论
					behaviorDto = new AppBehaviorDto(memberId, snsPost.getParentId(), AppBehaviorDto.BevType.b3_pl,
							AppBehaviorDto.BevValue.b0_cancel, AppBehaviorDto.PlatType.b2_post);
				}
			}
			R res = appFeignClient.createBehavior(behaviorDto);
			if (!res.isOk()) {
				throw new HServiceLogicException(res.getMsg());
			}
		}
		
		try {
			userFeignClient.deleteByCommonIdFeginApi(postId);
		}catch (Exception e) {
		}
	}

	@Override
	public Integer loadCountForNews(Long newsId, Integer showStatus) {
		if (newsId == null) {
			return 0;
		}
		return snsPostMng.loadCountForNews(newsId, showStatus);
	}

	@Override
	public Integer loadCountForTopic(Long topicId, Integer showStatus) {
		if (topicId == null) {
			return 0;
		}
		return snsPostMng.loadCountForTopic(topicId, showStatus);
	}

	@Override
	public Integer loadCountForPost(Long postId, Integer showStatus) {
		if (postId == null) {
			return 0;
		}
		return snsPostMng.loadCountForPost(postId, showStatus);
	}
}
