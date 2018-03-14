package com.hww.cms.common.dao.impl;


import org.springframework.stereotype.Repository;

import com.hww.cms.common.dao.CmsContentDataDao;
import com.hww.cms.common.entity.CmsContentData;

@Repository("cmsContentDataDao")
public class CmsContentDataDaoImpl extends LocalDataBaseDaoImpl<Long, CmsContentData> implements CmsContentDataDao {


}
