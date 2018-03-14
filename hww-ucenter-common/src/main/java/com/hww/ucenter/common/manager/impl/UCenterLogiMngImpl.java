package com.hww.ucenter.common.manager.impl;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.ucenter.common.dao.UCenterLoginDao;
import com.hww.ucenter.common.entity.UCenterLogin;
import com.hww.ucenter.common.manager.UCenterLoginMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("UCenterLoginMng")
public class UCenterLogiMngImpl extends BaseEntityMngImpl<Long, UCenterLogin, UCenterLoginDao>
        implements UCenterLoginMng {

    private UCenterLoginDao uCenterLoginDao;

    public UCenterLoginDao getUCenterLoginDao() {
        return uCenterLoginDao;
    }

    @Autowired
    public void setUCenterLoginDao(UCenterLoginDao uCenterLoginDao) {
        super.setEntityDao(uCenterLoginDao);
        this.uCenterLoginDao = uCenterLoginDao;
    }

	

}
