package com.hww.sns.webadmin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hww.base.util.R;
import com.hww.els.api.ElsFeignClient;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;
import com.hww.sns.common.manager.SnsTopicEsSyncFailMng;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.EsContentCovertUtil;
import com.hww.sns.webadmin.controller.SnsAuditController;
import com.hww.sns.webadmin.service.SnsTopicEsSyncFailService;



@Service("snstopicEsSyncFailService")
@Transactional
@EnableScheduling
public class SnsTopicEsSyncFailServiceImpl implements SnsTopicEsSyncFailService {

	private static final Log log = LogFactory.getLog(SnsTopicEsSyncFailServiceImpl.class);
    @Autowired
    SnsTopicEsSyncFailMng snsTopicEsSyncFailMng;
    @Autowired ElsFeignClient elsFeignClient;
    @Autowired SnsTopicMng snsTopicMng;
	@Override
	public void save(SnsTopicEsSyncFail snsTopicEsSyncFail) {
		snsTopicEsSyncFailMng.save(snsTopicEsSyncFail);
	}
	/*@Override
	public void doAync() {
		
	}*/

    //每天三点执行
//    @Scheduled(cron="0 0 3 * * ?")
//    @Scheduled(cron="0 0 3 * * ?")
	/*@Scheduled(fixedRate = 1000*3)
	@Override
	public void doAync() {
		log.debug("------------doAync----------------1");
		List<SnsTopicEsSyncFail> recordList = snsTopicEsSyncFailMng.loadAllFailRecord();
		log.debug("------------doAync----------------2-recordList.size()"+recordList==null?0:recordList.size());
		if(recordList==null||recordList.isEmpty()) {
			log.debug("------------doAync----------------3");
			return;
		}
		for(SnsTopicEsSyncFail topic: recordList) {
			log.debug("------------doAync----------------4");
			SnsTopic snsTopic=snsTopicMng.find(topic.getTopicId());
			log.debug("------------doAync----------------5--snsTopic"+snsTopic);
			if(snsTopic==null) {
				continue;
			}
			try {
				R res = elsFeignClient.createContentFeginApi(EsContentCovertUtil.toESContent(snsTopic));
				log.debug("------------doAync----------------6--res"+res);
				if(res.isOk()) {
					log.debug("------------doAync----------------7--res"+res);
					snsTopicEsSyncFailMng.updateIsDealSuccess(topic.getId());
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/
}
