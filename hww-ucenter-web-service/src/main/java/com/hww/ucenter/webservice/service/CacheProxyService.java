package com.hww.ucenter.webservice.service;

import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;

public interface CacheProxyService {
	
     SnsPostVo loadSnsPostVoCacheFirst(Long postId);
     SnsTopicVo loadSnsTopicVoCacheFirst(Long topicId);
     CmsContentDataVo loadCmsContentDataVoCacheFirst(Long subjectContentId,Long currentUserId);
}
