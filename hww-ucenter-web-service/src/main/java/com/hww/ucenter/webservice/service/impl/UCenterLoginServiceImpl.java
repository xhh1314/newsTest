package com.hww.ucenter.webservice.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hww.ucenter.common.entity.UCenterLogin;
import com.hww.ucenter.common.manager.UCenterLoginMng;
import com.hww.ucenter.webservice.service.UCenterLoginService;

@Service
@Transactional
public class UCenterLoginServiceImpl implements UCenterLoginService {

    private static final Logger log = LoggerFactory.getLogger(UCenterLoginServiceImpl.class);

    @Resource
    private UCenterLoginMng ucenterLoginMng;

   
    @Override
    public void saveLogin(UCenterLogin ucenterLogin) {
    	ucenterLoginMng.save(ucenterLogin);
    }
    
	
}
