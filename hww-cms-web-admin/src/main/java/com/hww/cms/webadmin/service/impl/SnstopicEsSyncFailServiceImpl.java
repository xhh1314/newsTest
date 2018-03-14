package com.hww.cms.webadmin.service.impl;

import com.hww.cms.common.entity.CmsNewEsSyncFail;
import com.hww.cms.common.manager.CmsNewEsSyncFailMng;
import com.hww.cms.webadmin.service.CmsNewEsSyncFailService;
import com.hww.els.api.ElsFeignClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("snstopicEsSyncFailService")
@Transactional
public class SnstopicEsSyncFailServiceImpl implements CmsNewEsSyncFailService {

    @Autowired
    CmsNewEsSyncFailMng cmsNewEsSyncFailMng;
    @Autowired ElsFeignClient elsFeignClient;
    
	@Override
	public void save(CmsNewEsSyncFail cmsNewEsSyncFail) {
		cmsNewEsSyncFailMng.save(cmsNewEsSyncFail);
	}

    //每天三点执行
//    @Scheduled(cron="0 0 3 * * ?")
    @Scheduled(cron="0 0 3 * * ?")
	@Override
	public void doAync() {
		List<CmsNewEsSyncFail> recordList = cmsNewEsSyncFailMng.loadAllFailRecord();
		if(recordList==null||recordList.isEmpty()) {
			return;
		}
		for(CmsNewEsSyncFail record: recordList) {
//			SnsTopic snsTopic=snsTopicMng.find(record.getContentId());
//			if(snsTopic==null) {
//				continue;
//			}
//			try {
//				R res = elsFeignClient.createContentFeginApi(EsContentCovertUtil.toESContent(snsTopic));
//				if(res.isOk()) {
//					cmsNewEsSyncFailMng.updateIsDealSuccess(record.getId());
//				}
//			}catch (Exception e) {
//				
//			}
			
			
		}
		
	}
    
    
   
}
