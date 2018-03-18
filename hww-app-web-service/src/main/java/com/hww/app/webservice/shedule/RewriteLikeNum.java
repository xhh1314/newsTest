package com.hww.app.webservice.shedule;

import com.hww.framework.common.constant.RedisKey;
import com.hww.framework.common.tool.JedisPoolUtil;
import redis.clients.jedis.Jedis;

public class RewriteLikeNum implements Runnable {


    @Override
    public void run() {
        Jedis conn=null;
        conn= JedisPoolUtil.getConnection();
        String value=conn.spop(RedisKey.LikeHistory.getValue());


    }
}
