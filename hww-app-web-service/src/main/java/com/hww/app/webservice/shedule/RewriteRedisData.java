package com.hww.app.webservice.shedule;

import com.alibaba.fastjson.parser.Feature;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class RewriteRedisData {

    private final ScheduledExecutorService scheduledThreadPool= Executors.newScheduledThreadPool(2);

    public void writeLike(){

        Feature[] features=new Feature[2];
        features[0]=Feature.AllowArbitraryCommas;

    }

}
