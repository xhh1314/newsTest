package com.hww.sns.webadmin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.manager.SnsPostMng;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.sns.webadmin.service.SnsPostService;
import com.hww.ucenter.common.dto.UCenterMemberDto;

@Service
public class SnsPostServiceImpl implements SnsPostService {

	@Resource
	SnsPostMng snsPostMng;
	
	/*@Autowired
	UCenterMemberService ucenterMemberService;
*/
	@Override
	public SnsPostVo findPostByIdAndUserid(Integer postid, Integer userid) {

		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsPost.class.getName());
		hql.append(" where 1=1 ");
		if(postid!=null) {
			hql.append("and postId=:postId").setParam("postId", postid);
			if(userid!=null) {
				hql.append("and memberId=:memberId").setParam("memberId", userid);
			}
		}
		List list = snsPostMng.find(hql);
		if(list!=null&list.size()>0) {
			SnsPost snsPost = (SnsPost) list.get(0);
			SnsPostVo snsPostVo = BeanMapper.map(snsPost, SnsPostVo.class);
			return snsPostVo;
		}
		return null;
	}


	@Override
	@Transactional
	public void savePost(SnsPostVo snsPostVo) {
		if(snsPostVo!=null) {
			if(snsPostVo.getContent()!=null) {
				SnsPost snsPost = BeanMapper.map(snsPostVo, SnsPost.class);
				snsPostMng.save(snsPost);
			}
		}
	}


	@Override
	@Transactional
	public void updatePost(SnsPostVo snsPostVo) {
		
		SnsPost snsPost = snsPostMng.find(snsPostVo.getPostId());
		try {
			BeanUtils.copyProperties(snsPost, snsPostVo);
		} catch (InvocationTargetException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		snsPost.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		
		snsPostMng.update(snsPost);
	}

	@Override
	public Pagination listComment(SnsPostVo searchVo, Integer pageNo, Integer pageSize) {
		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsPost.class.getName());
		hql.append("where 1=1");
		if(searchVo!=null) {
			if(searchVo.getParentId()!=null) {
				//parentid为0,null表示一级评论
				//parentid为postid,表示针对评论的回复
				if(searchVo.getParentId()==-1L) {
					//parentid=-1查所有回复
					hql.append("and parentId!=null ");
				}else {
					hql.append("and parentId=:parentId ").setParam("parentId", searchVo.getParentId());
				}
			}
			//爆料评论|新鲜事评论
			if(searchVo.getTopicType()!=null) {
				hql.append("and topicType=:topicType").setParam("topicType", searchVo.getTopicType());
			}
			//多条件查询
			if(searchVo.getAuditStatus()!=null) {
				hql.append("and auditStatus=:auditStatus").setParam("auditStatus", searchVo.getAuditStatus());
			}
			if(StringUtils.isNotEmpty(searchVo.getContent())) {
				hql.append("and content like :content").setParam("content", "%"+searchVo.getContent()+"%");
			}
			if(searchVo.getMemberId()!=null) {
				hql.append("and memberId =:memberId").setParam("memberId", searchVo.getMemberId());
			}
			if(searchVo.getBeginTime()!=null&&searchVo.getEndTime()!=null) {
				hql.append("and createTime>=:beginTime").setParam("beginTime", searchVo.getBeginTime());
				hql.append("and createTime<=:endTime").setParam("endTime", searchVo.getEndTime());
			}else {
				if(searchVo.getBeginTime()!=null){
					hql.append("and createTime>=:beginTime").setParam("beginTime", searchVo.getBeginTime());
				}else if(searchVo.getEndTime()!=null) {
					hql.append("and createTime<=:endTime").setParam("endTime", searchVo.getEndTime());
				}
			}
			hql.append("order by createTime desc");
		}
		
		Pagination p = snsPostMng.find(hql, pageNo, pageSize);
		if(p!=null&&p.getList()!=null) {
			List<SnsPostVo> list = BeanMapper.mapList(p.getList(), SnsPostVo.class);
			for(int i=0;i<list.size();i++) {
				SnsPostVo vo = list.get(i);
				/*if(vo.getMemberId()!=null) {
					//前台显示为用户名
					UCenterMemberDto umemberDto = ucenterMemberService.findMemberById(vo.getMemberId().longValue());
					if(umemberDto!=null) {
						vo.setMemberName(umemberDto.getMemberName());
					}
				}*/
			}
			p.setList(list);
		}
		return p;
	}


	@Override
	public List<SnsPost> findByIDs(List<SnsPostVo> asList) {
		List<SnsPost> list=new ArrayList<SnsPost>();
		for(SnsPostVo s:asList) {
			SnsPost snsPost=snsPostMng.find(s.getPostId());
			list.add(snsPost);
		}
		
		return list;
	}

}
