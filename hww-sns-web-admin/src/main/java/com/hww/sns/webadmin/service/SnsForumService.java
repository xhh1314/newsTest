package com.hww.sns.webadmin.service;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.sns.common.vo.SnsForumVo;

public interface SnsForumService {

	//查询所有分类
	Pagination list(SnsForumVo searchVo, Integer pageNo, Integer pageSize);
	
	List<SnsForumVo> listAllSnsForum();
	
	List<SnsForumVo> listSnsForumByVo(SnsForumVo vo);
	
	void saveForum(SnsForumVo snsForumVo);
	
	void deleteForum(SnsForumVo snsForumVo);

	void batchDeleteForum(List<SnsForumVo> asList);

	SnsForumVo findById(Long forumId);	
	
	
	void updateForum(SnsForumVo vo);
}
