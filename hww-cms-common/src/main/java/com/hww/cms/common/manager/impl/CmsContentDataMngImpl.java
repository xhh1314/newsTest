package com.hww.cms.common.manager.impl;

import com.hww.base.common.listener.IModifyListener;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.SnowFlake;
import com.hww.cms.common.Constants;
import com.hww.cms.common.dao.CmsContentDao;
import com.hww.cms.common.dao.CmsContentDataDao;
import com.hww.cms.common.dao.CmsOriginDao;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.entity.CmsOrigin;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.manager.CmsContentDataMng;
import com.hww.cms.common.manager.CmsContentTypeMng;
import com.hww.cms.common.util.EsContentCovertUtil;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.common.vo.query.QueryContentVo;
import com.hww.els.common.entity.ESContent;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;

@Service("cmsContentDataMng")
@Transactional
public class CmsContentDataMngImpl extends BaseEntityMngImpl<Long, CmsContentData, CmsContentDataDao>
		implements CmsContentDataMng {

	@Autowired
	CmsContentDao cmsContentDao;

	@Autowired
	CmsContentDataDao cmsContentDataDao;
	
	@Autowired
	CmsOriginDao cmsOriginDao;

	@Autowired
	CmsContentTypeMng cmsContentTypeMng;

	
	@Autowired
	CmsCategoryMng cmsCategoryMng;
	
	private List<IModifyListener<CmsContentData>> listenerList;

	public void setListenerList(List<IModifyListener<CmsContentData>> listenerList) {
		this.listenerList = listenerList;
	}

	@Autowired
	public void setCmsContentDataDao(CmsContentDataDao cmsContentDataDao) {
		super.setEntityDao(cmsContentDataDao);
		this.cmsContentDataDao = cmsContentDataDao;
	}

	@Override
	public CmsContentData loadCmsContentDataByContentId(Long  contentId) {
		Finder hql = Finder.create("from CmsContentData where 1=1");
		hql.append(" and contentId=:contentId");
		hql.setParam("contentId",contentId);
		List<CmsContentData> cmsContentDataList = (List<CmsContentData>) cmsContentDataDao.find(hql);
		if (null == cmsContentDataList || cmsContentDataList.size() == 0) {
			return null;
		}
		CmsContentData cmsContentData = cmsContentDataList.get(0);
		if(cmsContentData!=null&&cmsContentData.getOrigin()!=null) {
		}
		//cmsOriginDao.find(cmsContentData);
		
		return cmsContentData;
	}
	
	
	@Override
	@Transactional
	public void saveOrUpdateContentAndRelationshipWithCategory(CmsContentData entity, CmsContentVo form,
			Boolean isSource) {
		// TODO Auto-generated method stub
		Long categoryId = form.getCategoryId();
		// 判断是否是原始稿件 为了让原始稿件的信息和r里面的信息一致
		if (isSource == null) {
			isSource = entity.getSrcCategoryId() == categoryId;
		}

		if (isSource)// 原始稿件
		{
			entity.setTitle(form.getTitle());
			entity.setShortTitle(form.getShortTitle());
			entity.setAuthor(form.getAuthor());
		}
		if (entity.getContentId() != null && entity.getContentId() > 0) {
			// 执行监听器
			List<Map<String, Object>> mapList = preChange(entity);
			cmsContentDataDao.update(entity);
			afterChange(entity, mapList);
		} else {

			//String allIDCheck = form.getAllIDCheck();

			String[] categoryIds = null;

			//if (allIDCheck != null) {
			categoryIds = form.getCategoryIds().trim().split(",");
			//}

			//List<CmsContent> list = new ArrayList<>();
			//List<CmsContentData> list_data = new ArrayList<>();
			CmsContent cmsContent = new CmsContent();
			CmsContentData cmsContentData = new CmsContentData();
			try {
				BeanUtils.copyProperties(cmsContent, form);
				BeanUtils.copyProperties(cmsContentData, form);
				BeanUtils.copyProperties(cmsContentData, entity);

			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cmsContentData.setCreateTime(new Timestamp(new Date().getTime()));
			cmsContentData.setContent(entity.getContent());
			cmsContentData.setLastModifyTime(new Timestamp(new Date().getTime()));

			if(form.getOriginId() != null) {
				CmsOrigin cmsOrigin = new CmsOrigin();
				cmsOrigin.setOriginId(form.getOriginId());
				cmsContentData.setOrigin(cmsOrigin);
			}
			cmsContentDataDao.save(cmsContentData);
			form.setContentId(cmsContentData.getContentId());
			form.setStatus(Constants.CONTENT_STATUS_NEW);
			for (String id : categoryIds) {
				cmsContent.setContentId(cmsContentData.getContentId());
				cmsContent.setLinkUrl(form.getLinkUrl()+cmsContentData.getContentId());
				cmsContent.setAuditStatus(0);
				if(cmsContentData.getSnsTopicId() != null) {
					cmsContent.setSnsOrginId(cmsContentData.getSnsTopicId());
				}
				cmsContent.setSiteId(1);
				cmsContent.setAuthor(form.getAuthor());
				cmsContent.setEditor(form.getEditor());
				cmsContent.setCategoryId(Long.parseLong(id));
				cmsContent.setThumbIds(form.getThumbIds());
				cmsContent.setCreateTime(new Timestamp(new Date().getTime()));
				cmsContent.setStatus(Constants.CONTENT_STATUS_NEW);
				cmsContent.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
				cmsContentDao.save(cmsContent);
			}
			
				
			//entity.setContentId(contentId);
			// 执行监听器
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("otherCategoryIds", form.getOtherCategoryIds());
			mapList.add(map);

		/*	CmsContentType cmsContentType_type = form.getContentType();
			if (cmsContentType_type.getContentTypeId() == 5) {
				String AttachmentIds = form.getAttachmentIds();
				String thumbUrl = form.getThumbUrl();
				String[] thumbUrls = thumbUrl.split(",");
				String[] AttachmentIdss = AttachmentIds.split(",");
				for (int i = 0; i < AttachmentIdss.length; i++) {
					long contentImageId = new SnowFlake(1, 1).nextId();
					CmsContentImage cmsContentImage = new CmsContentImage();
					cmsContentImage.setImageUrl(thumbUrls[i]);
					cmsContentImage.setImageId(contentImageId);
					cmsContentImage.setCreateTime(new Timestamp(new Date().getTime()));
					cmsContentImage.setSiteId(1);
					cmsContentImage.setStatus(Short.valueOf("1"));
					cmsContentImage.setContentId(entity.getContentId());
					cmsContentImageDao.save(cmsContentImage);
				}
			}*/
			 //preSave(entity);
			//cmsContentDao.save(list);
			//新闻来源
			//if(entity.getOrigin()!=null) {
			//	cmsOriginDao.save(entity.getOrigin());
			//}
			//cmsContentDataDao.save(list_data);
		}

	}

	@Override
	public Pagination listCmsContent(CmsContentVo vo) {
		// TODO 暂时写死
		Integer siteId = 1;
		Finder hql = Finder.create("from ");
		hql.append(CmsContentData.class.getName());
		hql.append(" where 1=1");
		hql.append(" and siteId=:siteIdP").setParam("siteIdP", siteId);
		// 多条件查询
		// 标题
		if (StringUtils.isNotBlank(vo.getTitle())) {
			hql.append(" and title like :titleP").setParam("titleP", "%" + vo.getTitle() + "%");
		}
		// 摘要
		if (StringUtils.isNotEmpty(vo.getDescription())) {
			hql.append(" and description like :description").setParam("description", "%" + vo.getDescription() + "%");
		}
		// 新闻类型
		if (vo.getCategoryId() != null) {
			hql.append(" and id.categoryId=:categoryIdP").setParam("categoryIdP", vo.getCategoryId());
		}
		// 审核状态 TODO
		hql.append(" order by releaseTime desc");

		Pagination entitys = cmsContentDataDao.find(hql, vo.getPageNo(), vo.getPageSize());
		if (entitys.getList() != null) {
			List<CmsContentVo> vos = BeanMapper.mapList(entitys.getList(), CmsContentVo.class);
			entitys.setList(vos);
		}
		return entitys;
	}

	private void preSave(CmsContentData content) {
		if (listenerList != null) {
			for (IModifyListener<CmsContentData> listener : listenerList) {
				listener.preSave(content);
			}
		}
	}

	private void afterSave(CmsContentData content, List<Map<String, Object>> mapList) {
		if (listenerList != null) {
			for (IModifyListener<CmsContentData> listener : listenerList) {
				listener.afterSave(content);
			}
		}
	}

	private List<Map<String, Object>> preChange(CmsContentData content) {
		if (listenerList != null) {
			int len = listenerList.size();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(len);
			for (IModifyListener<CmsContentData> listener : listenerList) {
				list.add(listener.preChange(content));
			}
			return list;
		} else {
			return null;
		}
	}

	private void afterChange(CmsContentData content, List<Map<String, Object>> mapList) {
		if (listenerList != null && mapList != null) {
			if (mapList.size() == listenerList.size()) {
				int len = listenerList.size();
				IModifyListener<CmsContentData> listener;
				for (int i = 0; i < len; i++) {
					listener = listenerList.get(i);
					listener.afterChange(content, mapList.get(i));
				}
			}
		}
	}

	@Override
	public CmsContentVo getDetail(CmsContentVo form) {
		Long contentId = form.getContentId();
		Long categoryId = form.getCategoryId();
		// 复合主键查询唯一
		if (contentId != null && categoryId != null) {
			Finder hql = Finder.create(Finder.FROM);
			hql.append(CmsContent.class.getName());
			hql.append("where id.contentId=:contentId").setParam("contentId", contentId);
			hql.append("and id.categoryId=:categoryId").setParam("categoryId", categoryId);
			List<?> entitys = cmsContentDao.find(hql);
			if (entitys != null && entitys.size() > 0) {
				CmsContentVo vo = BeanMapper.map(entitys.get(0), CmsContentVo.class);
				CmsContentData dataEntity = cmsContentDataDao.find(contentId);
				if(dataEntity!=null) {
					CmsContentDataVo cmsContentDataVo = BeanMapper.map(dataEntity, CmsContentDataVo.class);
					vo.setCmsContentDataVo(cmsContentDataVo);
				}
				// 新闻内容从data表取
				if (vo.getCategoryId() != null) {
					// 新闻所属分类
					CmsCategoryVo categoryVo = cmsCategoryMng.getCategory(vo.getCategoryId());
					vo.setCmsCategoryVo(categoryVo);
				}
				if (vo.getContenttypeId() != null) {
					// 新闻内容分类
					CmsContentType entity = cmsContentTypeMng.findContentType(vo.getContenttypeId());
					vo.setContentType(entity);
				}
				return vo;
			}

		}
		return form;

	}
}
