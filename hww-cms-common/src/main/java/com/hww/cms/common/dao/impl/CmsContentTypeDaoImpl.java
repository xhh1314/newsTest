package com.hww.cms.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.cms.common.dao.CmsContentTypeDao;
import com.hww.cms.common.entity.CmsContentType;
@Repository("cmsContentTypeDao")
public class CmsContentTypeDaoImpl extends LocalDataBaseDaoImpl<Long, CmsContentType> implements CmsContentTypeDao {

}
