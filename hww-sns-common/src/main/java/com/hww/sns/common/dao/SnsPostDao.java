package com.hww.sns.common.dao;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.PostListVo;

import java.util.List;

public interface SnsPostDao
	extends
		IBaseEntityDao<Long, SnsPost>
{

//    List<SnsPost> myPosts(SnsTopic myTopicVo);

//    List<SnsPost> smallPostList(PostListVo vo);

    List<SnsPost> loadPostListByTopicId(HBaseSnsQueryDto queryDto);

//    List<SnsPost> smPostList(PostListVo vo);

	List<SnsPost> loadChildPostList(HBaseSnsQueryDto queryDto);
}
