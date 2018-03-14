package com.hww.cms.webadmin.service;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.cms.common.dto.CmsOriginDto;
import com.hww.cms.common.entity.CmsOrigin;

public interface CmsOriginService {

    Pagination list(CmsOriginDto dto);

    void saveOrigin(CmsOriginDto dto);

    CmsOrigin findById(Long originId);

    void updateOrigin(CmsOriginDto dto);

    void deleteOrigin(CmsOrigin cmsOrigin);
    
    List<CmsOrigin> listOrigin();
}
