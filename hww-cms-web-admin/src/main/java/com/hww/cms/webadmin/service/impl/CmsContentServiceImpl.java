package com.hww.cms.webadmin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import com.hww.cms.common.entity.CmsOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.R;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.manager.CmsContentDataMng;
import com.hww.cms.common.manager.CmsContentMng;
import com.hww.cms.common.util.EsContentCovertUtil;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.webadmin.service.CmsContentService;
import com.hww.cms.webadmin.service.CmsNewEsSyncFailService;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.entity.ESContent;
import com.hww.framework.common.exception.HServiceLogicException;

@Service("cmsContentService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class CmsContentServiceImpl implements CmsContentService {

	@Autowired
	CmsContentMng cmsContentMng;

	@Autowired
	CmsContentDataMng cmsContentdataMng;

	@Autowired
	private CmsNewEsSyncFailService cmsNewEsSyncFailService;
	@Autowired
	ElsFeignClient elsFeignClient;

	@Value("${contentUrl}")
	private String contentUrl;

	@Override
	public CmsContentVo findCmsContentById(long contentId, Long categoryId) {

		Finder hql = Finder.create("from ");
		hql.append(CmsContent.class.getName());
		hql.append(" where 1=1");
		hql.append(" and id.contentId=:contentIdP").setParam("contentIdP", contentId);
		if (categoryId != null) {
			hql.append(" and id.categoryId=:categoryIdP").setParam("categoryIdP", categoryId);
		}
		List<CmsContent> contentList = (List<CmsContent>) cmsContentMng.find(hql);
		CmsContentVo contentVo = new CmsContentVo();
		if (contentList != null && contentList.size() > 0) {
			contentVo = BeanMapper.map(contentList.get(0), CmsContentVo.class);
		}
		return contentVo;
	}

	@Override
	@Transactional
	public void updateNewWithContent(CmsContentVo cmsContentVo) {
		Finder f = Finder.create("from CmsContent where 1=1");
		f.append("and contentId=:ContentIdP").setParam("ContentIdP", cmsContentVo.getContentId());

		List<CmsContent> listEntity = (List<CmsContent>) cmsContentMng.find(f);

		String categoryIdsStr = cmsContentVo.getCategoryIds();
		String[] categoryIdsStrs = categoryIdsStr.split(",");
		List<Long> categoryIds = new ArrayList<>();
		for (String s : categoryIdsStrs) {
			categoryIds.add(Long.parseLong(s));
		}
		String admin = "";
		String author = "";
		String location = "";
		// 刷洗旧的
		for (CmsContent entity : listEntity) {
			admin = entity.getEditor();
			author = entity.getAuthor();
			location = entity.getLocation();
			if (categoryIds.contains(entity.getCategoryId())) {
				entity = voToEntity(entity, cmsContentVo);
				entity.setContentId(cmsContentVo.getContentId());
				if (entity.getAuditStatus() != null) {
					entity.setAuditStatus(0);
					entity.setAuditHisRecord(entity.getAuditHisRecord() == null ? 1 : entity.getAuditHisRecord() + 1);
					entity.setAuditStep(0);
					entity.setAuditStepResult(1);
					cmsContentVo.setAuditStatus(0);
				}
			} else {
				entity.setStatus(Short.valueOf("0"));
				entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
			}
			categoryIds.remove(entity.getCategoryId());
			cmsContentMng.update(entity);
		}
		// 保存新的
		for (Long id : categoryIds) {
			CmsContent last = new CmsContent();
			last = voToEntity(last, cmsContentVo);
			last.setContentId(cmsContentVo.getContentId());
			last.setCategoryId(id);
			last.setLinkUrl(contentUrl + cmsContentVo.getContentId());
			last.setSiteId(1);
			last.setCreateTime(new Timestamp(System.currentTimeMillis()));
			last.setAuditStatus(0);
			last.setAuthor(author);
			last.setEditor(admin);
			last.setLocation(location);
			cmsContentMng.save(last);
		}

		// 先保存新闻信息
		cmsContentVo.setStatus(Short.valueOf("1"));

		CmsContentData cmsContentData = cmsContentdataMng.find(cmsContentVo.getContentId());
		// 保存新闻内容
		if (cmsContentVo.getAttachmentIds() != null) {
			cmsContentData.setAttachmentIds(cmsContentVo.getAttachmentIds());
		}
		if (cmsContentVo.getAuthor() != null) {
			cmsContentData.setAuthor(cmsContentVo.getAuthor());
		}
		if (cmsContentVo.getContent() != null) {
			cmsContentData.setContent(cmsContentVo.getContent());
		}
		if (cmsContentVo.getContentId() != null) {
			cmsContentData.setContentId(cmsContentVo.getContentId());
		}
		cmsContentData.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		if (cmsContentVo.getDescription() != null) {
			cmsContentData.setDescription(cmsContentVo.getDescription());
		}
		if (cmsContentVo.getKeywordIds() != null) {
			cmsContentData.setKeywordIds(cmsContentVo.getKeywordIds());
		}
		if (cmsContentVo.getShortTitle() != null) {
			cmsContentData.setShortTitle(cmsContentVo.getShortTitle());
		}
		if (cmsContentVo.getSiteId() != null) {
			cmsContentData.setSiteId(cmsContentVo.getSiteId());
		}
		cmsContentData.setStatus((short) 1);

		if (cmsContentVo.getTagIds() != null) {
			cmsContentData.setTagIds(cmsContentVo.getTagIds());
		}
		if (cmsContentVo.getThumbIds() != null) {
			cmsContentData.setThumbIds(cmsContentVo.getThumbIds());
		}
		if (cmsContentVo.getThumbUrl() != null) {
			cmsContentData.setThumbUrl(cmsContentVo.getThumbUrl());
		}
		if (cmsContentVo.getTitle() != null) {
			cmsContentData.setTitle(cmsContentVo.getTitle());
		}
		if (cmsContentVo.getSrcCategoryId() != null) {
			cmsContentData.setSrcCategoryId(cmsContentVo.getSrcCategoryId());
		}
		if (cmsContentVo.getContent() != null) {
			cmsContentData.setContent(cmsContentVo.getContent());
		}
		if(cmsContentVo.getOriginId()!=null){
			CmsOrigin cmsOrigin= new CmsOrigin();
			cmsOrigin.setOriginId(cmsContentVo.getOriginId());
			cmsContentData.setOrigin(cmsOrigin);
		}

		try {
			EsContentCovertUtil eSContentUtil = new EsContentCovertUtil();
			ESContent eSContent = eSContentUtil.toEsContent(cmsContentVo);
			R r = elsFeignClient.createContentFeginApi(eSContent);
			if (!r.isOk()) {
//				CmsNewEsSyncFail cmsNewEsSyncFail = new CmsNewEsSyncFail();
//				cmsNewEsSyncFail.setContentId(cmsContentVo.getContentId());
//				cmsNewEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
//				cmsNewEsSyncFail.setFailWhat(1);
//				cmsNewEsSyncFail.setIsDealSuccess(0);
//				cmsNewEsSyncFailService.save(cmsNewEsSyncFail);
				throw new HServiceLogicException("同步失败：" + r.getMsg());
			}
		} catch (Exception e) {
//			e.printStackTrace();
//			CmsNewEsSyncFail cmsNewEsSyncFail = new CmsNewEsSyncFail();
//			cmsNewEsSyncFail.setContentId(cmsContentVo.getContentId());
//			cmsNewEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			cmsNewEsSyncFail.setFailWhat(1);
//			cmsNewEsSyncFail.setIsDealSuccess(0);
//			cmsNewEsSyncFailService.save(cmsNewEsSyncFail);
			throw new HServiceLogicException("同步失败：" + e.getMessage());
		}

		cmsContentData.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		cmsContentdataMng.update(cmsContentData);
	}

	public CmsContent voToEntity(CmsContent entity, CmsContentVo cmsContentVo) {
		if (cmsContentVo.getAttachmentIds() != null) {
			entity.setAttachmentIds(cmsContentVo.getAttachmentIds());
		}
		if (cmsContentVo.getAuthor() != null) {
			entity.setAuthor(cmsContentVo.getAuthor());
		}
		if (cmsContentVo.getContentType() != null) {
			entity.setContentType(cmsContentVo.getContentType());
		}
		if (cmsContentVo.getContenttypeId() != null) {
			entity.setContenttypeId(cmsContentVo.getContenttypeId());
		}
		entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		if (cmsContentVo.getDescription() != null) {
			entity.setDescription(cmsContentVo.getDescription());
		}
		if (cmsContentVo.getEditor() != null) {
			entity.setEditor(cmsContentVo.getEditor());
		}
		if (cmsContentVo.getKeywordIds() != null) {
			entity.setKeywordIds(cmsContentVo.getKeywordIds());
		}
		if (cmsContentVo.getLatitude() != null) {
			entity.setLatitude(cmsContentVo.getLatitude());
		}
		if (cmsContentVo.getLinkUrl() != null) {
			entity.setLinkUrl(cmsContentVo.getLinkUrl());
		}
		if (cmsContentVo.getLocation() != null) {
			entity.setLocation(cmsContentVo.getLocation());
		}
		if (cmsContentVo.getLongitude() != null) {
			entity.setLongitude(cmsContentVo.getLongitude());
		}
		if (cmsContentVo.getPriority() != null) {
			entity.setPriority(cmsContentVo.getPriority());
		}
		if (cmsContentVo.getShortTitle() != null) {
			entity.setShortTitle(cmsContentVo.getShortTitle());
		}
		if (cmsContentVo.getSiteId() != null) {
			entity.setSiteId(cmsContentVo.getSiteId());
		}
		entity.setStatus((short) 1);
		if (cmsContentVo.getSummary() != null) {
			entity.setSummary(cmsContentVo.getSummary());
		}
		if (cmsContentVo.getTagIds() != null) {
			entity.setTagIds(cmsContentVo.getTagIds());
		}
		if (cmsContentVo.getThumbIds() != null) {
			entity.setThumbIds(cmsContentVo.getThumbIds());
		}
		if (cmsContentVo.getThumbUrl() != null) {
			entity.setThumbUrl(cmsContentVo.getThumbUrl());
		}
		if (cmsContentVo.getTitle() != null) {
			entity.setTitle(cmsContentVo.getTitle());
		}
		if (cmsContentVo.getTop() != null) {
			entity.setTop(cmsContentVo.getTop());
		}
		if (cmsContentVo.getShortTitle() != null) {
			entity.setShortTitle(cmsContentVo.getShortTitle());
		}
		if (cmsContentVo.getManuallySortNum() != null) {
			entity.setManuallySortNum(cmsContentVo.getManuallySortNum());
		}
		if (cmsContentVo.getVideoLength() != null) {
			entity.setVideoLength(cmsContentVo.getVideoLength());
		}
		return entity;
	}

	@Override
	public void updateAuditstatus(CmsContentVo cmsContentVo) {
		CmsContentVo vo = findCmsContentById(cmsContentVo.getContentId(), cmsContentVo.getCategoryId());
		vo.setAuditStatus(cmsContentVo.getAuditStatus());
		vo.setAuditStep(cmsContentVo.getAuditStep());
		vo.setAuditStepResult(cmsContentVo.getAuditStepResult());
		CmsContent entity = BeanMapper.map(vo, CmsContent.class);
		cmsContentMng.update(entity);
	}

	@Override
	public void saveESContentByContentId(Long contentId) {
		CmsContent cmsContent = cmsContentMng.findById(contentId);
		CmsContentData cmsContentData = cmsContentdataMng.find(contentId);
		if (cmsContent != null) {
			CmsContentVo vo = BeanMapper.map(cmsContent, CmsContentVo.class);
			vo.setContent(cmsContentData.getContent());
			try {
				EsContentCovertUtil eSContentUtil = new EsContentCovertUtil();
				ESContent eSContent = eSContentUtil.toEsContent(vo);
				elsFeignClient.createContentFeginApi(eSContent);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public CmsContentData getContentById(Long contentId) {
		// TODO Auto-generated method stub
		return cmsContentdataMng.find(contentId);
	}

	@Override
	public List<CmsContent> getListByLocation(CmsContentVo vo) {
		// TODO Auto-generated method stub
		Finder f = Finder.create("select cms_content.* from cms_content where status>0 "
				+ "order by st_distance(point(longitude,latitude), point( :longitude,:latitude)) * 111195 asc ");
		f.append(" limit :pageStart,:pageSize");
		f.setParam("latitude", vo.getLatitude());
		f.setParam("longitude", vo.getLongitude());
		f.setParam("pageStart", (vo.getPageNo() - 1) * vo.getPageSize());
		f.setParam("pageSize", vo.getPageSize());
		return (List<CmsContent>) cmsContentMng.findJoin(f, CmsContent.class);
	}

	@Override
	public Long countContent() {
		// TODO Auto-generated method stub
		Finder f = Finder.create("select count(c.contentId) from CmsContent c where status>0");
		List<?> result = cmsContentMng.find(f);
		if (result != null && result.size() > 0) {
			return (Long) result.get(0);
		}
		return 0L;
	}

	@Override
	@Transactional
	public void updateContent(CmsContentVo cmsContentVo) {
		Finder f=Finder.create("update CmsContent");
		f.append(" set releaseTime=:releaseTime");
		f.append(" where contentId=:contentId");
		f.setParam("releaseTime", cmsContentVo.getReleaseTime());
		f.setParam("contentId", cmsContentVo.getContentId());
		cmsContentMng.update(f);
	}



}
