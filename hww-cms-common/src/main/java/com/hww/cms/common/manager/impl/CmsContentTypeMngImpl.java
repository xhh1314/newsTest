package com.hww.cms.common.manager.impl;

import java.util.LinkedList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.cms.common.dao.CmsContentTypeDao;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.manager.CmsContentTypeMng;
import com.hww.cms.common.vo.CmsContentTypeVo;
@Service("cmsContentTypeMng")
@Transactional
public class CmsContentTypeMngImpl
        extends
        	BaseEntityMngImpl<Long, CmsContentType, CmsContentTypeDao>
        implements
            CmsContentTypeMng
{
    CmsContentTypeDao cmsContentTypeDao;

    @Autowired
    public void setCmsContentTypeDao(CmsContentTypeDao cmsContentTypeDao)
    {
        super.setEntityDao(cmsContentTypeDao);
        this.cmsContentTypeDao = cmsContentTypeDao;
    }


	@Override
	public CmsContentTypeVo getContentType(Long contentTypeId) {
		CmsContentTypeVo contentTypeVo = new CmsContentTypeVo();
		if(contentTypeId!=null) {
			CmsContentType entity = cmsContentTypeDao.find(contentTypeId);
			BeanMapper.copy(entity, contentTypeVo);
		}
		return contentTypeVo;
	}
	
	@Override
	public CmsContentType findContentType(Long contentTypeId) {
		CmsContentType entity = new CmsContentType();
		if(contentTypeId!=null) {
			entity = cmsContentTypeDao.find(contentTypeId);
		}
		return entity;
	}


	@Override
	public List<CmsContentTypeVo> getAllContentTypes() {
		Finder hql = Finder.create(Finder.FROM);
		hql.append(CmsContentType.class.getName());
		hql.append("where 1=1");
		hql.setMaxResults(50);
		List<?> typeList = cmsContentTypeDao.find(hql);
		List<CmsContentTypeVo> vos = new LinkedList<CmsContentTypeVo>();
		if(typeList!=null&&typeList.size()>0) {
			vos = BeanMapper.mapList(typeList, CmsContentTypeVo.class);
		}
		return vos;
	}
	
	
    @Override
    public CmsContentType save(CmsContentType entity) {
    	return super.save(entity);
    }
    
    @Override
    public void updateStatusByProperty(Short status, String proname,
    		Collection<Long> ids) {
    	
    	Finder hql = Finder.create("update");
		hql.append(CmsContentType.class.getName());
		
		if (status!=null) {
			hql.append(" set status=:statusP");
			hql.setParam("statusP", status);
		}
		
		hql.append(" where contentTypeId in (:contentTypeIdsP)");
		hql.setParamList("contentTypeIdsP", ids);

		
    	
    	cmsContentTypeDao.update(hql);
    	
    }




}
