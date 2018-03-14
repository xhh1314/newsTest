package com.hww.sns.webadmin.service;

import java.util.List;

import com.hww.base.common.page.Pagination;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.vo.SnsAuditVo;
import com.hww.sns.common.vo.SnsPostVo;

public interface SnsPostService {

	//评论|回复分页
	Pagination listComment(SnsPostVo searchVo, Integer pageNo, Integer pageSize);

	SnsPostVo findPostByIdAndUserid(Integer postid, Integer userid);

	void savePost(SnsPostVo snsPostVo);

	void updatePost(SnsPostVo snsPostVo);
	
	
	List<SnsPost> findByIDs(List<SnsPostVo> asList);
	
}
