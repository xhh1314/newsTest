package com.hww.sns.common.dao.impl;

import com.hww.base.common.util.Finder;
import com.hww.sns.common.dao.SnsPostDao;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.vo.PostListVo;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.TopPostListVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("snsPostDao")
public class SnsPostDaoImpl extends LocalDataBaseDaoImpl<Long, SnsPost> implements SnsPostDao {

	@Override
	public List<SnsPost> loadChildPostList(HBaseSnsQueryDto queryDto) {
		Finder f = Finder.create("from SnsPost bean");
		f.append(" where bean.parentId=:parentId");
		f.append(" and bean.status=1 ");
		if (queryDto.getMemberId() != null) {
			f.append(" and (bean.showStatus=1 ");
			f.append(" or (bean.memberId= :memberId and bean.auditStatus !=0 ) ) ");
			f.setParam("memberId", queryDto.getMemberId());
		} else {
			f.append(" and bean.showStatus=1 ");
		}
		f.append(" order by bean.createTime desc");
		f.setParam("parentId", queryDto.getPostId());
		if (queryDto.getPageNo() != null && queryDto.getPageSize() != null) {
			f.setFirstResult((queryDto.getPageNo() - 1) * queryDto.getPageSize());
			f.setMaxResults(queryDto.getPageSize());
		}

		List<SnsPost> snsPosts = (List<SnsPost>) find(f);
		return snsPosts;
	}

	@Override
	public List<SnsPost> loadPostListByTopicId(HBaseSnsQueryDto queryDto) {
		// 需显示用户自己刚刚发送的待审核的评论
		Finder f = Finder.create("from SnsPost bean");
		f.append(" where bean.topicId= :topicId");
		f.append(" and bean.parentId is null ");
		f.append(" and bean.status=1 ");
		if (queryDto.getMemberId() != null) {
			f.append(" and (bean.showStatus= :showStatus ");
			f.append(" or (bean.memberId= :memberId and bean.auditStatus !=0 ) ) ");
			f.setParam("memberId", queryDto.getMemberId());
		} else {
			f.append(" and bean.showStatus= :showStatus ");
		}
		f.append(" order by bean.createTime desc");
		f.setParam("topicId", queryDto.getTopicId());
		f.setParam("showStatus", 1);

		if (queryDto.getPageNo() != null && queryDto.getPageSize() != null) {
			f.setFirstResult((queryDto.getPageNo() - 1) * queryDto.getPageSize());
			f.setMaxResults(queryDto.getPageSize());
		}
		List<SnsPost> snsPosts = (List<SnsPost>) find(f);
		return snsPosts;
	}

	// @Override
	// public List<SnsPost> myPosts(SnsTopic myTopicVo) {
	// Finder f = Finder.create("from SnsPost bean");
	// f.append(" where bean.topicId=:topicId");
	// f.append(" order by bean.createTime desc");
	// f.setParam("topicId", myTopicVo.getTopicId());
	//
	// List<SnsPostVo> ss = new ArrayList<>();
	//
	// List<SnsPost> snsPosts = (List<SnsPost>) find(f);
	// return snsPosts;
	// }

	// @Override
	// public List<SnsPost> smallPostList(PostListVo vo) {
	//
	// Finder f = Finder.create("from SnsPost bean");
	// f.append(" where bean.parentId=:parentId");
	// f.append(" and bean.auditStatus=:auditStatus");
	// f.append(" order by bean.createTime desc");
	// f.setParam("parentId", vo.getPostId());
	// f.setParam("auditStatus", 1);
	//
	// List<SnsPostVo> ss = new ArrayList<>();
	//
	// List<SnsPost> snsPosts = (List<SnsPost>) find(f);
	// return snsPosts;
	// }

	// @Override
	// public List<SnsPost> smPostList(PostListVo vo) {
	//
	// Finder f = Finder.create("from SnsPost bean");
	// f.append(" where bean.parentId= :parentId");
	// f.append(" order by bean.createTime desc");
	// f.setParam("parentId", vo.getPostId());
	// f.setFirstResult((vo.getPageNo()-1)*vo.getPageSize());
	// f.setMaxResults(vo.getPageSize());
	// List<SnsPost> snsPosts = (List<SnsPost>)find(f);
	// return snsPosts;
	// }

}
