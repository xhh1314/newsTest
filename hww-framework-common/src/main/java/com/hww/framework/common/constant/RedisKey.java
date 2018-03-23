package com.hww.framework.common.constant;

public enum RedisKey {

	/**
	 * 新鲜事的key前缀+topicId
	 */
	SnsTopic("sns:topic:"),

	/**
	 * 评论的key前缀
	 */
	SnsComment("sns:comment:"),

	/**
	 * 记录被点赞过的文章,使用zSet存储,value is contentId:plateType,score is total number
	 */
	LikedHistoryCollection("like:history"),
	/**
	 * 记录被点赞过的文章,使用zSet存储,value is contentId,score is increment number
	 */
	LikedHistoryIncrement("like:increment"),
	/**
	 * 记录被评论过的文章,使用zSet存储,value is contentId:plateType,score is total number
	 */
	CommentedHistoryCollection("comment:history"),
	/**
	 * 记录被评论过的文章,使用zSet存储,value is contentId,score is increment Number
	 */
	CommentedHistoryIncrement("comment:increment"),

	/**
	 * 记录谁点赞了文章，key为clc:contentId:plateType,value是memberId
	 */
	ContentLikedCollection("clc:"),

	/**
	 * 用户行为对象，存储用户的点赞 评论 收藏 等行为
	 */
	UserBehavior("user:behavior"),
	/**
	 *关注的人
	 */
	Follow("user:follow"),

	/**
	 * 用户发表的新鲜事id集合，"user:topic:"+memberId,使用zset存储，score是时间,value是topicId:createTime
	 */
	UserTopic("user:topic:");

	final String value;

	RedisKey(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
