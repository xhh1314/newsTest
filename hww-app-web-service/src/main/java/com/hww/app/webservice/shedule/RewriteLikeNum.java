package com.hww.app.webservice.shedule;

import com.hww.framework.common.constant.HwwConsts;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.sns.api.SnsFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

public class RewriteLikeNum implements Runnable {

	@Autowired
	private SnsFeignClient snsFeignClient;

	@Override
	public void run() {
		Jedis conn = null;
		conn = JedisPoolUtil.getConnection();
		String value = null;
		while ((value = conn.spop(RedisKey.LikeAndCommentHistory.getValue())) != null) {
			String[] array = value.split(":");
			String contentId = array[0];
			String behaviorType = array[1];
			String plateType = array[2];
			if (!StringUtils.hasText(contentId)) {
				continue;
			}
			// 主题类型
			if (Integer.parseInt(plateType) == HwwConsts.PlateType.topic)
				rewriteTopic(conn, contentId);

		}

	}

	private void rewriteTopic(Jedis conn, String topicId) {
		String upNumStr = conn.hget(RedisKey.SnsTopic.getValue() + topicId, "upNum");
		String commentNumStr = conn.hget(RedisKey.SnsTopic.getValue() + topicId, "commentNum");
		Integer upNum = null, comentNum = null;
		if (upNumStr == null && commentNumStr == null)
			return;
		if (upNumStr == null)
			upNum = 0;
		else
			upNum = Integer.parseInt(upNumStr);

		if (commentNumStr == null)
			comentNum = 0;
		else
			comentNum = Integer.parseInt(commentNumStr);
		snsFeignClient.updateTopicCommentAndLikeNumber(Long.parseLong(topicId), upNum, comentNum);

	}
}
