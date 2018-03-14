package com.hww.cms.webservice.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hww.base.util.R;
import com.hww.cms.common.Constants;
import com.hww.cms.common.entity.CmsContentData;
import com.hww.cms.common.entity.CmsContentType;
import com.hww.cms.common.manager.CmsContentDataMng;
import com.hww.cms.common.util.EsContentCovertUtil;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.webservice.service.CmsToContentService;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.entity.ESContent;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.common.dto.SnsTopicToCmsDto;

@Service("cmsToContentService")
@Transactional
public class CmsToContentServiceImpl
	implements
		CmsToContentService {

	private static final Log log = LogFactory.getLog(CmsToContentServiceImpl.class);

	@Autowired
	CmsContentDataMng cmsContentDataMng;
	@Value("${contentUrl}")
	private String contentUrl;
	@Autowired
	ElsFeignClient elsFeignClient;

	public R snsToCmsContent(SnsTopicToCmsDto tocmsdto) {

		CmsContentVo form = new CmsContentVo();

		form.setCategoryId(9999L);
		form.setCategoryIds("9999");

		form.setSummary(tocmsdto.getContent());
		form.setTitle(tocmsdto.getTitle());
		form.setContent(tocmsdto.getContent());

		form.setAttachmentIds(tocmsdto.getFileId());
		form.setThumbIds(tocmsdto.getFileId());

		form.setAuthor(tocmsdto.getAuthorName());
		form.setEditor(tocmsdto.getOperatorUserName());
		form.setAuditHisRecord(1);

		form.setLatitude(tocmsdto.getLatitude());
		form.setLongitude(tocmsdto.getLongitude());
		form.setLocation(tocmsdto.getAddress());

		CmsContentType type = new CmsContentType();
		type.setContentTypeId(7L);
		form.setContentType(type);

		form.setCreateTime(new Timestamp(System.currentTimeMillis()));
		form.setAuditStatus(0);
		form.setLinkUrl(contentUrl);

		form.setCreateTime(new Timestamp(System.currentTimeMillis()));
		form.setAuditStatus(0);
		form.setSiteId(1);
		form.setSnsOrginId(tocmsdto.getTopicId());
		form.setSrcCategoryId(9999L);
		// ====================================

		CmsContentData contentData = new CmsContentData();
		try {
			BeanUtils.copyProperties(contentData, form);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// contentData.setSrcCategoryId(3L);

		contentData.setSnsTopicId(tocmsdto.getTopicId());
		// 新闻内容
		contentData.setContent(tocmsdto.getContent());
		contentData.setAuthor(tocmsdto.getAuthorName());
		contentData.setUserId(tocmsdto.getMemberId());
		contentData.setAttachmentIds(tocmsdto.getFileId());
		contentData.setThumbIds(tocmsdto.getFileId());
		contentData.setUserId(tocmsdto.getMemberId());
		contentData.setSnsTopicId(tocmsdto.getTopicId());
		contentData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		contentData.setStatus(Constants.CONTENT_STATUS_NEW);// 直接发布
		Integer siteId = 1;
		contentData.setSiteId(siteId);

		try {
			ESContent eSContent = EsContentCovertUtil.toEsContent(form);
			R r = elsFeignClient.createContentFeginApi(eSContent);
			if (!r.isOk()) {
				throw new HServiceLogicException("同步失败：" + r.getMsg());
			}

		} catch (Exception e) {
			throw new HServiceLogicException("同步失败：" + e.getMessage());
		}

		try {
			cmsContentDataMng.saveOrUpdateContentAndRelationshipWithCategory(contentData, form, true);
		} catch (Exception e) {
			return R.error("保存错误！").put("result", 0);
		}

		return R.ok("操作成功").put("result", 1);

	}

}
