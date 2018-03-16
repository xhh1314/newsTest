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
	 * 记录被点赞过的文章,value组成为:contentId:behaviorType
	 */
	LikeHistory("like:history"),

	/**
	 * 记录谁点赞了文章，key为likeCollection:+contentId,value是memberId
	 */
	LikeCollection("likeCollection:"),
	/**
	 *关注的人
	 */
	Follow("ucenter:follow"),

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
