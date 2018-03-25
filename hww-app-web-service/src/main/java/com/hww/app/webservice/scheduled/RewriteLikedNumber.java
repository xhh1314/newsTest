package com.hww.app.webservice.scheduled;

import com.hww.app.common.dao.AppMemberBehaviorCountDao;
import com.hww.app.common.entity.AppMemberBehaviorCount;
import com.hww.framework.common.constant.HwwConsts;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.sns.api.SnsFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Random;
import java.util.Set;

public class RewriteLikedNumber implements Runnable {

	private SnsFeignClient snsFeignClient;
	private AppMemberBehaviorCountDao appMemberBehaviorCountDao;

	private final static Logger log = LoggerFactory.getLogger(RewriteLikedNumber.class);

	@Autowired
	public void setSnsFeignClient(SnsFeignClient snsFeignClient) {
		this.snsFeignClient = snsFeignClient;
	}

	@Autowired
	public void setAppMemberBehaviorCountDao(AppMemberBehaviorCountDao appMemberBehaviorCountDao) {
		this.appMemberBehaviorCountDao = appMemberBehaviorCountDao;
	}

	@Override
	public void run() {
		rewriteLikesCount();

	}

	private void rewriteLikesCount() {
		Jedis conn = null;
		try {
			conn = JedisPoolUtil.getConnection();
			String likedHistoryCollection = RedisKey.LikedHistoryCollection.getValue();
			// 有多少文章被点赞
			Long number = conn.zcard(likedHistoryCollection);
			if (number == null || number == 0)
				return;
			int begin = 0;
			int increment = 0;
			int end = 0;
			if (number >= 10) {
				increment = number.intValue() / 10;
				Random random = new Random();
				begin = random.nextInt(number.intValue() - increment);
				end = begin + increment;
			} else {
				end = number.intValue();
			}
			Set<Tuple> members = conn.zrangeWithScores(likedHistoryCollection, begin, end);
			for (Tuple tuple : members) {
				String value = tuple.getElement();
				String[] values = value.split(":");
				AppMemberBehaviorCount behaviorCount = new AppMemberBehaviorCount();
				behaviorCount.setBevCount((int) tuple.getScore());
				behaviorCount.setBevType(HwwConsts.Behavior.b1_dz);
				behaviorCount.setPlateType(Integer.parseInt(values[1]));
				behaviorCount.setContentId(Long.parseLong(values[0]));
				appMemberBehaviorCountDao.save(behaviorCount);
				conn.zrem(RedisKey.LikedHistoryCollection.getValue(), tuple.getElement());
				log.info("contentId:{},likedNumber:{},save liked number success!", values[0], tuple.getScore());
			}
		} catch (NumberFormatException e) {
			log.error("持久化点赞数据发生异常！{}", e);
		} finally {
			if (conn != null)
				conn.close();
		}

	}

}
