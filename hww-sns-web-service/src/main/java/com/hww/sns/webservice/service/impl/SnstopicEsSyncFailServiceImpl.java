package com.hww.sns.webservice.service.impl;

import com.hww.base.util.R;
import com.hww.els.api.ElsFeignClient;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;
import com.hww.sns.common.manager.SnsTopicEsSyncFailMng;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.EsContentCovertUtil;
import com.hww.sns.webservice.service.SnstopicEsSyncFailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.transaction.Transactional;
import java.util.List;

@Service("snstopicEsSyncFailService")
@Transactional
public class SnstopicEsSyncFailServiceImpl implements SnstopicEsSyncFailService {
    @Autowired
    SnsTopicEsSyncFailMng snsTopicEsSyncFailMng;
    @Autowired SnsTopicMng snsTopicMng;
    @Autowired ElsFeignClient elsFeignClient;
    private static final Log log = LogFactory.getLog(SnstopicEsSyncFailServiceImpl.class);
	@Override
	public void save(SnsTopicEsSyncFail snsTopicEsSyncFail) {
		snsTopicEsSyncFailMng.save(snsTopicEsSyncFail);
	}

    //每天三点执行
//    @Scheduled(cron="0 0 3 * * ?")
	@Scheduled(fixedRate = 1000*50)
	@Override
	public void doAync() {
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
	}
    
    
   
}
