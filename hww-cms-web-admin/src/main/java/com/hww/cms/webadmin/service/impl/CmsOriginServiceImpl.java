package com.hww.cms.webadmin.service.impl;

import com.hww.base.common.page.Pagination;
import com.hww.cms.common.dto.CmsOriginDto;
import com.hww.cms.common.entity.CmsOrigin;
import com.hww.cms.common.manager.CmsOriginMng;
import com.hww.cms.webadmin.service.CmsOriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service("cmsOriginService")
@Transactional
public class CmsOriginServiceImpl implements CmsOriginService {

    @Autowired
    private CmsOriginMng cmsOriginMng;

    @Override
    public Pagination list(CmsOriginDto dto) {
        return cmsOriginMng.list(dto);
    }

    @Override
    public void saveOrigin(CmsOriginDto dto) {
        cmsOriginMng.saveOrigin(dto);
    }

    @Override
    public CmsOrigin findById(Long orginId) {
        return cmsOriginMng.find(orginId);
    }

    @Override
    public void updateOrigin(CmsOriginDto dto) {
        cmsOriginMng.updateOrigin(dto);
    }

    @Override
    public void deleteOrigin(CmsOrigin cmsOrigin) {
        cmsOriginMng.delete(cmsOrigin);
    }

	@Override
	public List<CmsOrigin> listOrigin() {
		return cmsOriginMng.listOrigin();
		
	}
}
