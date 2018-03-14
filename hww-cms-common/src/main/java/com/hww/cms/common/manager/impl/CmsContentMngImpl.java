package com.hww.cms.common.manager.impl;

import com.hww.base.common.listener.IModifyListener;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.CopyBean;
import com.hww.base.util.SnowFlake;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.dao.CmsContentDao;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.manager.CmsContentDataMng;
import com.hww.cms.common.manager.CmsContentMng;
import com.hww.cms.common.util.EsContentCovertUtil;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.common.vo.query.QueryContentVo;
import com.hww.els.common.entity.ESContent;
import com.hww.els.common.vo.SearchNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("cmsContentMng")
@Transactional
public class CmsContentMngImpl extends BaseEntityMngImpl<Long, CmsContent, CmsContentDao> implements CmsContentMng {
	private List<IModifyListener<CmsContent>> listenerList;

	public void setListenerList(List<IModifyListener<CmsContent>> listenerList) {
		this.listenerList = listenerList;
	}
	
	CmsContentDao cmsContentDao;

	@Autowired
	CmsContentDataMng cmsContentDataMng;

	public CmsContentDao getCmsContentDao() {
		return cmsContentDao;
	}

	@Autowired
	public void setCmsContentDao(CmsContentDao cmsContentDao) {
		super.setEntityDao(cmsContentDao);
		this.cmsContentDao = cmsContentDao;
	}
	
	@Override
	public List<CmsContent> loadCmsContentByCateIds(HCmsQueryDto cmsQueryDto, List<Long> categoryIds, List<Long> uninterestedContentIds){
		if(categoryIds==null||categoryIds.isEmpty()) {
			return new ArrayList<CmsContent>();
		}
		Finder hql = Finder.create("from CmsContent where 1=1");
		hql.append(" and auditStatus=1");
		hql.append(" and status=1 ");
		hql.append(" and categoryId in (:categoryIds)");
		hql.setParamList("categoryIds", categoryIds);
		
		if (uninterestedContentIds!=null&&uninterestedContentIds.size() > 0) {
			hql.append(" and contentId not in (:contentIds)");
			hql.setParamList("contentIds", uninterestedContentIds);
		}
		hql.append(" and status=1 ");
		hql.append(" group by contentId ");
		hql.append(" order by releaseTime desc");
//		if (cmsQueryDto.getOrderBy() != null) {
//			if (cmsQueryDto.getOrderBy() == 1) {
//				hql.append(" order by releaseTime desc");
//			}
//			if (cmsQueryDto.getOrderBy() == 2&&cmsQueryDto.getLongitude()!=null&&cmsQueryDto.getLatitude()!=null) {
//				hql.append(" order by (POWER(MOD(ABS(longitude - "+cmsQueryDto.getLongitude()+"),360),2) + POWER(ABS(latitude - "+cmsQueryDto.getLatitude()+"),2))");
//			}
//		}else {
//			hql.append(" order by releaseTime desc");
//		}
		if(cmsQueryDto.getPageNo()!=null&&cmsQueryDto.getPageSize()!=null) {
			hql.setFirstResult((cmsQueryDto.getPageNo() - 1) * cmsQueryDto.getPageSize());
			hql.setMaxResults(cmsQueryDto.getPageSize());
		}
		List<CmsContent> list = (List<CmsContent>) find(hql);
		return list;
	}
//	@Override
//	public List<CmsContent> loadCmsContentByContentType(HCmsQueryDto cmsQueryDto, List<Long> uninterestedContentIds) {
//		Finder hql = Finder.create(" from CmsContent c where 1=1 ");
//		hql.append(" and c.auditStatus=1");
//		hql.append(" and c.contenttypeId = :contentType ");
//		hql.setParam("contentType", cmsQueryDto.getContentType());
//		
//		if (cmsQueryDto.getOrderBy() != null) {
//			if (cmsQueryDto.getOrderBy() == 1) {
//				hql.append(" order by releaseTime desc");
//			}
//			
//			if (cmsQueryDto.getOrderBy() == 2&&cmsQueryDto.getLongitude()!=null&&cmsQueryDto.getLatitude()!=null) {
//				hql.append(" order by (POWER(MOD(ABS(longitude - "+cmsQueryDto.getLongitude()+"),360),2) + POWER(ABS(latitude - "+cmsQueryDto.getLatitude()+"),2))");
//			}
//		}
//		if(cmsQueryDto.getPageNo()!=null&&cmsQueryDto.getPageSize()!=null) {
//			hql.setFirstResult((cmsQueryDto.getPageNo() - 1) * cmsQueryDto.getPageSize());
//			hql.setMaxResults(cmsQueryDto.getPageSize());
//		}
//		List<CmsContent> list = (List<CmsContent>) find(hql);
//		return list;
//	}
	
	@Override
	public  List<CmsContent> loadCmsContentList(List<Long> contentIds){
		if(contentIds==null||contentIds.isEmpty()) {
			return new ArrayList<CmsContent>(0);
		}
		contentIds=contentIds.stream().distinct().collect(Collectors.toList());
		
		String contentIdsx= contentIds.stream().map(val->String.valueOf(val)).collect(Collectors.joining(","));

		Finder finder = Finder.create("from CmsContent c where 1=1");
		finder.append(" and auditStatus=1");
		finder.append(" and status=1 ");
		finder.append(" and contentId in (:contentIds)  group by contentId order by find_in_set(c.contentId,:contentIdsx) ");
		finder.append(" ,releaseTime desc");
		finder.setParamList("contentIds", contentIds);
		finder.setParam("contentIdsx", contentIdsx);
		List<CmsContent> cmsContentList = (List<CmsContent>) cmsContentDao.find(finder);
		return cmsContentList;
	}
	@Override
	public  List<CmsContent> loadCmsContentLists(HCmsQueryDto cmsQueryDto,List<Long> contentIds){
		if(contentIds==null||contentIds.isEmpty()) {
			return new ArrayList<CmsContent>(0);
		}
		contentIds=contentIds.stream().distinct().collect(Collectors.toList());
		
		String contentIdsx= contentIds.stream().map(val->String.valueOf(val)).collect(Collectors.joining(","));

		Finder finder = Finder.create("from CmsContent c where 1=1");
		finder.append(" and auditStatus=1");
		finder.append(" and status=1 ");
		finder.append(" and contentId in (:contentIds)  group by contentId order by find_in_set(c.contentId,:contentIdsx) ");
		finder.append(" ,releaseTime desc");
		finder.setParamList("contentIds", contentIds);
		finder.setParam("contentIdsx", contentIdsx);
		if(cmsQueryDto.getPageNo()!=null&&cmsQueryDto.getPageSize()!=null) {
			finder.setFirstResult((cmsQueryDto.getPageNo() - 1) * cmsQueryDto.getPageSize());
			finder.setMaxResults(cmsQueryDto.getPageSize());
		}
		List<CmsContent> cmsContentList = (List<CmsContent>) cmsContentDao.find(finder);
		return cmsContentList;
	}

	@Override
	public CmsContent findOneByContentId(Long contentId) {
		return cmsContentDao.findOneByContentId(contentId);
//		Finder finder = Finder.create("from CmsContent where 1=1");
//		//finder.append(" and auditStatus=1");
//		finder.append(" and contentId=:contentId");
//		finder.setParam("contentId", contentId);
//		List<CmsContent> cmsContentList = (List<CmsContent>) cmsContentDao.find(finder);
//		if (null == cmsContentList || cmsContentList.size() == 0) {
//			return null;
//		}
//		return cmsContentList.get(0);
	}
	@Override
	public List<CmsContent> loadAbountCmsContentList(String aboutNewIds){
		if (StringUtils.isNotEmpty(aboutNewIds)) {
			Finder f = Finder.create("from CmsContent where 1=1");
			f.append(" and auditStatus=1");
			f.append(" and status=1 ");
			f.append(" and FIND_IN_SET(contentId,:contentIds)>0");
			f.setParam("contentIds", aboutNewIds);
			List<CmsContent> abountNewList = (List<CmsContent>) cmsContentDao.find(f);
			return abountNewList;
		}
		return new ArrayList<CmsContent>();
	}
	
    @Override
	@Transactional
	public List<CmsContent> updateRelationshipStatus(Long categoryId, List<Long> contentIds, Short status) {
		Finder hql = Finder.create("from ");
		hql.append(CmsContent.class.getName());
		hql.append(" where 1=1 and contentId in (:contentIdsP)");
		hql.setParamList("contentIdsP", contentIds);

		if (categoryId != null) {
			hql.append(" and categoryId=:categoryIdP");
			hql.setParam("categoryIdP", categoryId);
		}
		List<CmsContent> rList = (List<CmsContent>)cmsContentDao.find(hql);
		if (rList != null && rList.size() > 0) {
			CmsContent r = null;
			for (int i = 0; i < rList.size(); i++) {
				r = rList.get(i);
				r.setStatus(status);
				r.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
				cmsContentDao.update(r);
				afterChange(r, null);
			}
		}
		return rList;
	}

	private void afterChange(CmsContent entity, List<Map<String, Object>> mapList) {
		if (listenerList != null) {
			Assert.notNull(mapList,"not null");
			Assert.isTrue(mapList.size() == listenerList.size(),"not equal");
			int len = listenerList.size();
			IModifyListener<CmsContent> listener;
			for (int i = 0; i < len; i++) {
				listener = listenerList.get(i);
				listener.afterChange(entity, mapList.get(i));
			}
		}
	}

	@Override
	public Pagination listCmsContent(CmsContentVo vo) {
		// TODO 暂时写死
		Integer siteId = 1;
		Finder hql = Finder.create("from ");
		hql.append(CmsContent.class.getName());
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
		// 审核状态
		if(vo.getAuditStatus()!=null) {
			hql.append("and auditStatus=:auditStatus").setParam("auditStatus", vo.getAuditStatus());
		}
		//页面排序
		hql.append(" order by createTime desc");

		Pagination entitys = cmsContentDao.find(hql, vo.getPageNo(), vo.getPageSize());
		if (entitys.getList() != null) {
			List<CmsContent> cmsContents = (List<CmsContent>) entitys.getList();
			// TODO 内容模糊查询需要关联contentdata表查
			List<CmsContentVo> vos = BeanMapper.mapList(cmsContents, CmsContentVo.class);
			entitys.setList(vos);
		}

		return entitys;
	}

	@Override
	public List<CmsContent> queryUserNewCollection(QueryContentVo vo) {
		Finder finder = Finder.create("from CmsMemberBehavior where 1=1");
		finder.append(" and uuid=:uuid");
		finder.append(" and type=2");
		finder.setParam("uuid", vo.getImei());
		/*List<CmsMemberBehavior> cmsMemberBehaviorList = (List<CmsMemberBehavior>) find(finder);
		if (null == cmsMemberBehaviorList || cmsMemberBehaviorList.size() == 0) {
			return null;
		}*/
		List<CmsContent> cmsContentList = new ArrayList<>();
		/*for (CmsMemberBehavior cmsMemberBehavior : cmsMemberBehaviorList) {
			Long contentId = cmsMemberBehavior.getContentId();
			Finder f = Finder.create("from CmsContent where 1=1");
			f.append(" and contentId=:contentId");
			f.setParam("contentId", contentId);
			List<CmsContent> list = (List<CmsContent>) cmsContentDao.find(f);
			if (null == list || list.size() == 0) {
				continue;
			}
			cmsContentList.add(list.get(0));
		}*/
		return cmsContentList;
	}

	@Override
	public List<CmsContent> queryPromoteNewsByPage(List<Long> contentIds, QueryContentVo vo) {
		Finder hql = Finder.create("from CmsContent");
		hql.append(" where 1=1");
		hql.append(" and auditStatus=1");
		hql.append(" and status=1 ");
		hql.append(" and top=0");
		if (contentIds.size() > 0) {
			hql.append(" and contentId not in (:contentIds)");
			hql.setParamList("contentIds", contentIds);
		}
		hql.append(" order by (POWER(MOD(ABS(longitude - "+vo.getLongitude()+"),360),2) + POWER(ABS(latitude - "+vo.getLatitude()+"),2)),priority desc,releaseTime desc");
		hql.setFirstResult((vo.getPageNo() - 1) * vo.getPageSize());
		hql.setMaxResults(vo.getPageSize());
		List<CmsContent> cmsContentList = (List<CmsContent>) find(hql);
		return cmsContentList;
	}
	@Override
	public List<CmsContent> queryTopNewList(HCmsQueryDto cmsQueryDto,List<Long> uninterestedContentIds) {
		Finder hql = Finder.create("from CmsContent");
		hql.append(" where 1=1");
		hql.append(" and auditStatus=1");
		hql.append(" and status=1 ");
		hql.append(" and top=1");
		if (uninterestedContentIds != null && uninterestedContentIds.size() > 0) {
			hql.append(" and contentId not in (:contentIds)");
			hql.setParamList("contentIds", uninterestedContentIds);
		}
		hql.append(" group by contentId  ");
		hql.append(" order by releaseTime desc,priority desc");
		if(cmsQueryDto.getPageNo()!=null&&cmsQueryDto.getPageSize()!=null) {
			hql.setFirstResult((cmsQueryDto.getPageNo() - 1) * cmsQueryDto.getPageSize());
			hql.setMaxResults(cmsQueryDto.getPageSize());
		}
		List<CmsContent> cmsContentList = (List<CmsContent>) find(hql);
		return cmsContentList;
	}

	@Override
	public List<CmsContent> queryCmsContentPageList(QueryContentVo vo, List<Long> categoryIds, List<Long> contentIds) {
		Finder hql = Finder.create("from CmsContent where 1=1");
		hql.append(" and auditStatus=1");
		hql.append(" and status=1 ");
		hql.append(" and categoryId in (:categoryIds)");
		if (contentIds.size() > 0) {
			hql.append(" and contentId not in (:contentIds)");
			hql.setParamList("contentIds", contentIds);
		}
		hql.setParamList("categoryIds", categoryIds);
		hql.setFirstResult((vo.getPageNo() - 1) * vo.getPageSize());
		hql.setMaxResults(vo.getPageSize());
		List<CmsContent> list = (List<CmsContent>) find(hql);
		return list;
	}

	
	

	@Override
	public List<CmsContent> queryNewsByEls(SearchNewsVo vo) {
		if (null == vo.getIds() || vo.getIds().size() == 0) {
			return null;
		}
		Finder hql = Finder.create("from CmsContent where 1=1");
		hql.append(" and auditStatus=1");
		if (vo.getIds() != null && vo.getIds().size() > 0) {
			hql.append(" and contentId in (:contentIds)");
			hql.setParamList("contentIds", vo.getIds());
		}
		if (vo.getOrderBy() != null) {
			if (vo.getOrderBy() == 1) {
				hql.append(" order by releaseTime desc");
			}
			if (vo.getOrderBy() == 2) {
				hql.append(" order by (POWER(MOD(ABS(longitude - "+vo.getLongitude()+"),360),2) + POWER(ABS(latitude - "+vo.getLatitude()+"),2))");
			}
		}
		hql.setFirstResult((vo.getPageNo() - 1) * vo.getPageSize());
		hql.setMaxResults(vo.getPageSize());
		List<CmsContent> list = (List<CmsContent>) find(hql);
		return list;
	}
	
	@Override
	public void saveOrUpdateCmsContent(CmsContentVo vo) {
		// TODO Auto-generated method stub
		String categoryIds = vo.getCategoryIds();
		String[] categoryIdArray = categoryIds.split(",");
		
		if(vo.getContentId()!=null) {
			//更新,只更新cmscontent
			
		}else {
			//添加，一条cmscontentsdata,多条cmscontent
			CmsContentDataVo dataVo = vo.getCmsContentDataVo();
			if(dataVo==null) {
				dataVo = BeanMapper.map(vo, CmsContentDataVo.class);
			}else {
				CopyBean.copyNotNull(vo, dataVo);
			}
			SnowFlake snowFlake = new SnowFlake(1,1);
			long contentId = snowFlake.nextId();
			
			CmsContentData dataEntity = BeanMapper.map(dataVo, CmsContentData.class);
			dataEntity.setContentId(contentId);
			dataEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			cmsContentDataMng.save(dataEntity);
			
			//保存多条cmscontent
			CmsContent entity = BeanMapper.map(vo, CmsContent.class);
			//内容类型
			if(vo.getContenttypeId()!=null){
				CmsContentType contentType = new CmsContentType();
				contentType.setContentTypeId(vo.getContenttypeId());
				entity.setContentType(contentType);
			}
			for(int i=0;i<categoryIdArray.length;i++) {
				Long categoryId = Long.parseLong(categoryIdArray[i]);
				entity.setContentId(contentId);
				entity.setCategoryId(categoryId);
				entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
				cmsContentDao.save(entity);
			}
			
		}
	}

  @Override
  public List<CmsContentVo> topCmsContentList() {
    
    return cmsContentDao.topCmsContentList();
  }

  @Override
  public List<Map<String, Object>> cmsContentByColum(Integer columnId, Integer num) {
    
    return cmsContentDao.cmsContentByColum(columnId, num);
  }

  @Override
  public List<Map<String, Object>> cmsContentColum() {
    
    return cmsContentDao.cmsContentColum();
  }

  @Override
  public List<CmsContentVo> contentsBycategoryId(CmsContentVo vo) {
    
    return cmsContentDao.contentsBycategoryId(vo);
  }

  @Override
  public String noInterests(QueryContentVo vo) {
    
    return cmsContentDao.noInterests(vo);
  }

  @Override
  public List<CmsContentVo> cmsContentByColumId(QueryContentVo vo) {
    
    return cmsContentDao.cmsContentByColumId(vo);
  }

@Override
public List<Map<String, Object>> cmsContentByColums(CmsContentVo vo) {
    
    return cmsContentDao.cmsContentByColums(vo);
}

@Override
public List<CmsContentVo> nearContents(CmsContentVo vo) {
    
    return cmsContentDao.nearContents(vo);
}

@Override
public CmsContent findById(Long contentId) {
	
	return cmsContentDao.findOneByContentId(contentId);
}

@Override
public CmsContentVo updateTop(Long contentId, Long categoryId) {
	Finder hql = Finder.create("from CmsContent where 1=1");
	hql.append(" and contentId = :contentId ").setParam("contentId", contentId);
	hql.append(" and categoryId = :categoryId ").setParam("categoryId", categoryId);
	List<CmsContent> cmsContent= (List<CmsContent>)cmsContentDao.find(hql);
	if(cmsContent!=null){
		Short top = cmsContent.get(0).getTop();
		if(top==Short.valueOf("1")){
			cmsContent.get(0).setTop(Short.valueOf("0"));
		}else{
			cmsContent.get(0).setTop(Short.valueOf("1"));
		}
		CmsContentData CmsContentData = cmsContentDataMng.find(contentId);
		CmsContentVo vo = BeanMapper.map(cmsContent.get(0),CmsContentVo.class);
		if(CmsContentData.getContent()!=null){
			vo.setContent(CmsContentData.getContent());
		}
		cmsContent.get(0).setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		cmsContentDao.update(cmsContent.get(0));
		return vo;
	}
	return new CmsContentVo();
	
}

@Override
public List<?> findJoin(Finder f,Class<?> o) {
	// TODO Auto-generated method stub
	
	return cmsContentDao.findJoin(f, o);
}



}
