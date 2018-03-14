package com.hww.app.admin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.hww.app.admin.service.AppTelCodeService;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.app.common.manager.AppTelCodeMng;
import com.hww.app.common.vo.AppTelCodeVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.StringUtils;
@Service("appTelCodeService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class AppTelCodeServiceImpl implements AppTelCodeService{
    @Autowired
    private AppTelCodeMng appTelCodeMng;

	public void addTelCode(AppNationalTelCode telCode) {
		// TODO Auto-generated method stub
		appTelCodeMng.addTelCode(telCode);
	}

	@Override
	public void delTelCode(AppTelCodeVo telCode) {
		if (StringUtils.isNotBlank(telCode.getAllIDCheck())) {
            String ids = telCode.getAllIDCheck();
            if (StringUtils.isNotBlank(ids)) {
                ids.substring(0, ids.length() - 1);
                String[] mIdArray = ids.split(",");
                for (int i = 0; i < mIdArray.length; i++) {
                    if (StringUtils.isNumeric(mIdArray[i])) {
                    	AppNationalTelCode entity = appTelCodeMng.queryOne(Long.parseLong(mIdArray[i]));
                    			appTelCodeMng.delTelCode(entity);
                    }
                }
            }
        } else {
        	AppNationalTelCode entity = appTelCodeMng.queryOne(telCode.getId());
        	appTelCodeMng.delTelCode(entity);
        }
		
	}

	@Override
	public AppNationalTelCode queryOne(Long telId) {
		// TODO Auto-generated method stub
		return appTelCodeMng.queryOne(telId);
	}

	@Override
	public void updateTelCode(AppNationalTelCode telCode) {
		// TODO Auto-generated method stub
		appTelCodeMng.updateTelCode(telCode);
	}

	@Override
	public Pagination telCodeList(Integer pageNo,Integer pageSize) {
		// TODO Auto-generated method stub
		return appTelCodeMng.telCodeList(pageNo, pageSize);
	}

}
