package com.hww.cms.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.cms.common.dao.CmsCategoryTypeDao;
import com.hww.cms.common.entity.CmsCategoryType;
@Repository("cmsCategoryTypeDao")
public class CmsCategoryTypeDaoImpl
        extends
            LocalDataBaseDaoImpl<Long, CmsCategoryType>
        implements
            CmsCategoryTypeDao
{

}
