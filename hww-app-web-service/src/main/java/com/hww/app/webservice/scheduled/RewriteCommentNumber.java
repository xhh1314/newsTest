package com.hww.app.webservice.scheduled;

import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.framework.common.constant.HwwConsts;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class RewriteCommentNumber implements Runnable {
	@Autowired
	private AppMemberBehaviorCountDao appMemberBehaviorCountDao;
	private static final Logger log = LoggerFactory.getLogger(RewriteCommentNumber.class);

	@Override
	public void run() {
		writeCommentNumberToDatabase();
	}

	private void writeCommentNumberToDatabase() {
		Jedis conn = null;
		try {
			conn = JedisPoolUtil.getConnection();
			Long size = conn.zcard(RedisKey.CommentedHistoryCollection.getValue());
			if (size == null || size == 0) {
				log.warn("CommentedHistoryCollectionKey  在缓存中不存在！");
				return;
			}
			int begin = 0, end = 0;
			end = size.intValue();
			begin = end - size.intValue() / 10;
			if (begin < 0)
				begin = 0;
			Set<Tuple> members = conn.zrevrangeWithScores(RedisKey.CommentedHistoryCollection.getValue(), begin, end);
			for (Tuple tuple : members) {
				AppMemberBehaviorCount memberBehaviorCount = new AppMemberBehaviorCount();
				memberBehaviorCount.setBevCount((int) tuple.getScore());
				String[] key = tuple.getElement().split(":");
				memberBehaviorCount.setPlateType(Integer.parseInt(key[1]));
				memberBehaviorCount.setBevType(HwwConsts.Behavior.b1_dz);
				memberBehaviorCount.setContentId(Long.parseLong(key[0]));
				appMemberBehaviorCountDao.save(memberBehaviorCount);
				conn.zrem(RedisKey.CommentedHistoryCollection.getValue(), tuple.getElement());
				log.info("contentId:{},commentNumber:{},save comment number success!", key[0], tuple.getScore());
			}
		} catch (NumberFormatException e) {
			log.error("持久化评论数到数据库发生异常！{}", e);
		} finally {
			if (conn != null)
				conn.close();
		}

	}
}
