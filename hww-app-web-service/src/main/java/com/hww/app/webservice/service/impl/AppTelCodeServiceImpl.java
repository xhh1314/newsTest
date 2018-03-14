package com.hww.app.webservice.service.impl;

import com.hww.app.common.dao.AppTelCodeDao;
import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.app.webservice.service.AppTelCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service("appTelCodeService")
@Transactional
public class AppTelCodeServiceImpl implements AppTelCodeService {

    @Autowired
    private AppTelCodeDao appTelCodeDao;

    
      @Scheduled(fixedDelay = 1000*60*30)
	   @CacheEvict(value = "app_listNationalTelephoneCode",allEntries=true)
	   public void listNationalTelephoneCode_delete_from_cache() {
	   }
    
    @Cacheable(value = "app_listNationalTelephoneCode",key="'listNationalTelephoneCode_'")
    public List<AppNationalTelCode> listNationalTelephoneCode() {
        return appTelCodeDao.codeList();
    }
}
