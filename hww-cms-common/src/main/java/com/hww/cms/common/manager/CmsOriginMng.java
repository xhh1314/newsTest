package com.hww.cms.common.manager;

import java.util.List;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.page.Pagination;
import com.hww.cms.common.dao.CmsOriginDao;
import com.hww.cms.common.dto.CmsOriginDto;
import com.hww.cms.common.entity.CmsOrigin;

public interface CmsOriginMng extends IBaseEntityMng<Long, CmsOrigin, CmsOriginDao> {


    Pagination list(CmsOriginDto dto);

    void saveOrigin(CmsOriginDto dto);

    void updateOrigin(CmsOriginDto dto);

	List<CmsOrigin> listOrigin();
}
