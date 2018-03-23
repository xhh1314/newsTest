package com.hww.app.webservice.shedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Service
public class ScheduledWriteLikeNumberToMysqlService implements ApplicationRunner {

  private  static  final Logger log= LoggerFactory.getLogger(ScheduledWriteLikeNumberToMysqlService.class);

  private  final ScheduledExecutorService scheduledExecutorService=  Executors.newScheduledThreadPool(2);


  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("------------执行刷入点赞数据计划任务----------");
    scheduledExecutorService.scheduleAtFixedRate(new RewriteLikeNum(),10,10, TimeUnit.MINUTES);
  }

  @PreDestroy
  public void destory(){
    log.info("-------------关闭线程池：ScheduledWriteLikeNumberToMysqlService-----------------");
    scheduledExecutorService.shutdown();

  }

}
