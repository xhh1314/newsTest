package com.hww.cms.common.manager.impl;

import com.hww.base.common.listener.IModifyListener;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.util.BeanMapper;
import com.hww.cms.common.Constants;
import com.hww.cms.common.dao.CmsCategoryDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("cmsCategoryMng")
@Transactional
public class CmsCategoryMngImpl
	extends
		BaseEntityMngImpl<Long, CmsCategory, CmsCategoryDao>
	implements
		CmsCategoryMng {

	private List<IModifyListener<CmsCategory>> listenerList;

	public void setListenerList(List<IModifyListener<CmsCategory>> listenerList) {
		this.listenerList = listenerList;
	}

	@Override
	public List<CmsCategory> getChildList(Long parentId, Short display) {
		// TODO Auto-generated method stub
		return cmsCategoryDao.getChildList(parentId, display);
	}

	@Override
	public List<CmsCategory> getRetrievingFullTree(Long userId, Integer siteId, Short status) {
		// TODO Auto-generated method stub
		return cmsCategoryDao.getRetrievingFullTree(userId, siteId, status);
	}

	@Override
	public List<CmsCategory> getTopList(Long siteId, Short display) {
		// TODO Auto-generated method stub
		return null;
	}

	CmsCategoryDao cmsCategoryDao;

	public CmsCategoryDao getCmsCategoryDao() {
		return cmsCategoryDao;
	}

	@Autowired
	public void setCmsCategoryDao(CmsCategoryDao cmsCategoryDao) {
		super.setEntityDao(cmsCategoryDao);
		this.cmsCategoryDao = cmsCategoryDao;
	}

	@Override
	public List<CmsCategory> getRetrievingASinglePath(Integer categoryId, Integer siteId) {
		// TODO Auto-generated method stub
		return cmsCategoryDao.getRetrievingASinglePath(categoryId, siteId);
	}

	@Override
	public Boolean publishCategory(Long categoryId) {
		// TODO Auto-generated method stub
		CmsCategory entity = new CmsCategory();
		entity.setCategoryId(categoryId);
		afterChange(entity, null);
		return true;
	}

	private void afterChange(CmsCategory entity, List<Map<String, Object>> mapList) {
		if (listenerList != null) {
			Assert.notNull(mapList,"非空");
			Assert.isTrue(mapList.size() == listenerList.size(),"是否相等");
			int len = listenerList.size();
			IModifyListener<CmsCategory> listener;
			for (int i = 0; i < len; i++) {
				listener = listenerList.get(i);
				listener.afterChange(entity, mapList.get(i));
			}
		}
	}

	@Override
	public void saveOrUpdateCategoryAndAndRelationshipWidthTpl(
			CmsCategory entity, CmsCategoryVo form, boolean b) {
		// TODO Auto-generated method stub
		
		entity.setTypeId(Constants.categoryType.listview);
		
		cmsCategoryDao.save(entity);
		
	}

	@Override
	public CmsCategoryVo getCategory(Long categoryId) {
		CmsCategoryVo categoryVo = new CmsCategoryVo();
		if(categoryId!=null) {
			CmsCategory entity = cmsCategoryDao.find(categoryId);
			BeanMapper.copy(entity, categoryVo);
		}
		return categoryVo;
	}

	@Override
	public List<CmsCategory> getCategorysByType(Short typeId, Integer siteId,Long parentId) {
		List<CmsCategory> categorys = null;
		if(siteId!=null) {
			categorys = cmsCategoryDao.getCategorysByType(typeId, siteId,parentId);
		}
		if(categorys==null) {
			categorys = new LinkedList<CmsCategory>();
		}
		return categorys;
	}

  @Override
  public List<CmsCategoryVo> categoryDetail(CmsCategoryVo vo) {
    
    return cmsCategoryDao.categoryDetail(vo);
  }

  @Override
  public List<CmsContentVo> cmsContentByCategoryId(CmsCategoryVo vo) {
    
    return cmsCategoryDao.cmsContentByCategoryId(vo);
  }

  @Override
  public List<Map<String, Object>> cmsCategoryNum() {
    
    return cmsCategoryDao.cmsCategoryNum();
  }

  @Override
  public List<Map<String, Object>> cmsCategorys(Integer num) {
    
    return cmsCategoryDao.cmsCategorys(num);
  }

  @Override
  public List<Map<String, Object>> cmsTopicNum() {
    
    return cmsCategoryDao.cmsTopicNum();
  }

  @Override
  public List<Map<String, Object>> cmsContentByCatId(Integer categoryId, Integer num) {
    
    return cmsCategoryDao.cmsContentByCatId(categoryId, num);
  }



}
