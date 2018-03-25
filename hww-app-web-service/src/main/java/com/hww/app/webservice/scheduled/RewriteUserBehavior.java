package com.hww.app.webservice.scheduled;

import com.alibaba.fastjson.JSON;
import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.entity.AppMemberBehavior;
import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RewriteUserBehavior implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(RewriteUserBehavior.class);

	@Autowired
	private AppMemberBehaviorDao appMemberBehaviorDao;

	@Override
	public void run() {
        writeMemberBehaviorToDatabase();
	}

	private void writeMemberBehaviorToDatabase() {
		Jedis conn = null;
		conn = JedisPoolUtil.getConnection();
		Long size = conn.scard(RedisKey.UserBehavior.getValue());
		if (size == null || size == 0) {
			log.warn("appMemberBehavior 缓存中不存在!");
			return;
		}
		int number = 0;
		if (size >= 10)
			number = size.intValue() / 10;
		else
			number = size.intValue();
		Set<String> members = conn.spop(RedisKey.UserBehavior.getValue(), number);
		for (String str : members) {
			AppMemberBehavior appMemberBehavior = JSON.parseObject(str, AppMemberBehavior.class);
			appMemberBehaviorDao.save(appMemberBehavior);
			log.info("save appMemberBehavior success! {}",appMemberBehavior);
		}

	}
}
