package com.hww.cms.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.cms.common.dao.CmsCategoryTypeDao;
import com.hww.cms.common.entity.CmsCategoryType;
import com.hww.cms.common.manager.CmsCategoryTypeMng;
@Service("cmsCategoryTypeMng")
@Transactional
public class CmsCategoryTypeMngImpl
    extends
        BaseEntityMngImpl<Long, CmsCategoryType, CmsCategoryTypeDao>
    implements
        CmsCategoryTypeMng
{
    CmsCategoryTypeDao cmsCategoryTypeDao;

    public CmsCategoryTypeDao getCmsCategoryTypeDao()
    {
        return cmsCategoryTypeDao;
    }
    @Autowired
    public void setCmsCategoryTypeDao(CmsCategoryTypeDao cmsCategoryTypeDao)
    {
        super.setEntityDao(cmsCategoryTypeDao);
        this.cmsCategoryTypeDao = cmsCategoryTypeDao;
    }

}
