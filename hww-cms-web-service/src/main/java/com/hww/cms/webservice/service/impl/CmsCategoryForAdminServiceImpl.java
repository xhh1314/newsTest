package com.hww.cms.webservice.service.impl;

import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.webservice.service.CmsCategoryForAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("cmsCategoryForAdminService")
@Transactional
public class CmsCategoryForAdminServiceImpl implements CmsCategoryForAdminService {

	@Autowired
	CmsCategoryMng cmsCategoryMng;

	@Override
	public List<CmsCategory> getRetrievingFullTree() {
		// TODO Auto-generated method stub
		return cmsCategoryMng.getRetrievingFullTree(null, null, null);
	}
}
