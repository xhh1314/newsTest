package com.hww.cms.webadmin.push;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.push.model.notification.*;

import com.hww.base.common.util.Finder;
import com.hww.base.util.TimeUtils;
import com.hww.cms.common.dto.HJPushDataDto;
import com.hww.cms.common.entity.CmsContentPush;
import com.hww.cms.common.manager.CmsContentPushMng;
import com.hww.framework.common.idgen.SnowFlakeIdGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
//\"newsID\":0,
//\"newsTitle\":\"\",
//\"newsType\":1,
//\"newsUrl\":\"http://m.haiwainet.cn/phone/3541093/2018/0108/content_31229185_1.html\"}
@Service
@Transactional
@ConfigurationProperties(prefix="jpush")
public class JPushService implements InitializingBean{
    protected static final Logger LOG = LoggerFactory.getLogger(JPushService.class);

    // demo App defined in resources/jpush-api.conf 
//    protected static final String APP_KEY ="d4ee2375846bc30fa51334f5";
//    protected static final String MASTER_SECRET = "f3b222f7e0dde430b6d8fa5a";
//    protected static final String GROUP_PUSH_KEY = "2c88a01e073a0fe4fc7b167c";
//    protected static final String GROUP_MASTER_SECRET = "b11314807507e2bcfdeebe2e";
//	
//	public static final String TITLE = "Test from API example";
//    public static final String ALERT = "Test from API Example - alert";
//    public static final String MSG_CONTENT = "Test from API Example - msgContent";
//    public static final String REGISTRATION_ID = "0900e8d85ef";
//    public static final String TAG = "tag_api";
//    public static long sendCount = 0;
//    private static long sendTotalTime = 0;

    @Autowired CmsContentPushMng cmsContentPushMng;
	
    private String appKey;
    private String masterSecet;
    
    
	@Autowired
	private Environment env;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		appKey=env.getProperty("jpush.appKey");
		masterSecet=env.getProperty("jpush.masterSecet");
	}
	

	public  void sendPush(HJPushDataDto extrasData) {
		  
		CmsContentPush push= consruct( extrasData) ;
		ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient( masterSecet,appKey, null, clientConfig);
        final PushPayload payload = buildPushObject2_android_and_ios(extrasData);
        try {
            PushResult result = jpushClient.sendPush(payload);
            push.setPushResults(1);
           push.setJpSendno(payload.getSendno());
            LOG.info("Got result - " + result);
            System.out.println(result);
        } catch (APIConnectionException e) {
        	
        	
        	push.setPushResults(0);
        	push.setJpSendno(payload.getSendno());
        	push.setJpErrorMsg(e.getMessage()!=null?"":e.getMessage().substring(0, 490));
        	
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());
            
        } catch (APIRequestException e) {
        	push.setPushResults(0);
        	push.setJpSendno(payload.getSendno());
        	push.setJpHttpStatus(e.getStatus());
        	push.setJpErrorCode(e.getErrorCode());
        	push.setJpErrorMsg(e.getErrorMessage()!=null?"":e.getErrorMessage().substring(0, 490));
        	
        	
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
        cmsContentPushMng.save(push);
    }

	 public  PushPayload buildPushObject2_android_and_ios(HJPushDataDto extrasData) {
	        Map<String, String> extras = new HashMap<String, String>();
	        extras.put("newsID", ""+extrasData.getNewsID());
	        extras.put("newsTitle", ""+extrasData.getNewsTitle());
	        extras.put("newsType", ""+extrasData.getNewsType());
	        extras.put("newsUrl", extrasData.getNewsUrl());
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.all())
	                .setNotification(Notification.newBuilder()
	                		.setAlert(extrasData.getAlertContent())
	                		//android
	                		.addPlatformNotification(AndroidNotification.newBuilder()
	                				.setTitle("")
	                                .addExtras(extras).build())
	                		//ios
	                		.addPlatformNotification(IosNotification.newBuilder()
	                				.incrBadge(1)
	                				.addExtras(extras).build())
//	                				.addExtra("extra_key", "extra_value").build())
	                		.build())
	                .build();
	    }
	 
		private CmsContentPush consruct(HJPushDataDto extrasData) {
			CmsContentPush push=new CmsContentPush().setContentId(extrasData.getNewsID());
	        push.setCreateTime(TimeUtils.getDateToTimestamp());
	        push.setLastModifyTime(TimeUtils.getDateToTimestamp());
	        push.setPushAlert(extrasData.getAlertContent());
	        push.setPushNewsTitle(extrasData.getNewsTitle());
	        push.setPushNewsType(extrasData.getNewsType());
	        push.setPushNewsUrl(extrasData.getNewsUrl());
	        return push;
		}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getMasterSecet() {
		return masterSecet;
	}
	public void setMasterSecet(String masterSecet) {
		this.masterSecet = masterSecet;
	}
	 
	 
   
}

