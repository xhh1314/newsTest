package com.hww.cms.common.dao;

import java.util.List;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.base.common.page.Pagination;
import com.hww.cms.common.dto.CmsOriginDto;
import com.hww.cms.common.entity.CmsOrigin;

public interface CmsOriginDao extends IBaseEntityDao<Long, CmsOrigin> {

    Pagination list(CmsOriginDto dto);

	List<CmsOrigin> listOrigin();
}
