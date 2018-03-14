package com.hww.sns.webadmin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.R;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.entity.ESContent;
import com.hww.file.api.FileFeignClient;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;
import com.hww.sns.common.manager.SnsPostMng;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.EsContentCovertUtil;
import com.hww.sns.common.vo.SnsForumVo;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.sns.webadmin.service.SnsForumService;
import com.hww.sns.webadmin.service.SnsSenstiveService;
import com.hww.sns.webadmin.service.SnsTopicEsSyncFailService;
import com.hww.sns.webadmin.service.SnsTopicService;


@Service
public class SnsTopicServiceImpl implements SnsTopicService {

	
	@Autowired
	SnsTopicMng snsTopicMng;
	
	@Autowired
	SnsPostMng snsPostMng;
	
	@Autowired
	SnsSenstiveService snsSenstiveService;
	
	/*@Autowired
	UCenterMemberService ucenterMemberService;*/
	
	@Autowired
	SnsForumService snsForumService;
	
	@Autowired
	FileFeignClient fileFeignClient;
	
	@Autowired
	ElsFeignClient elsFeignClient;
	@Autowired
	SnsTopicEsSyncFailService snsTopicEsSyncFailService;
	@Override
	@Transactional
	public void save(SnsTopicVo snsTopicVo) {

		if(snsTopicVo.getContent()!=null) {
			SnsTopic snsTopic = BeanMapper.map(snsTopicVo, SnsTopic.class);
			snsTopicMng.save(snsTopic);
		}
		
	}

	@Override
	public SnsTopicVo query(SnsTopicVo snsTopicVo) {

		Long topicId = snsTopicVo.getTopicId();
		if(topicId!=null) {
			//查询主题
			SnsTopic snsTopic = snsTopicMng.find(topicId);
			SnsTopicVo result = BeanMapper.map(snsTopic, SnsTopicVo.class);
			if(StringUtils.isNotEmpty(snsTopic.getFileId())) {
				//图片
				String url = fileFeignClient.getUrlByFileId(snsTopic.getFileId());
				result.setUrl(url);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public SnsTopicVo queryWithPost(SnsTopicVo snsTopicVo) {

		Long topicId = snsTopicVo.getTopicId();
		if(topicId!=null) {
			//查询主题
			SnsTopic snsTopic = snsTopicMng.find(topicId);
			SnsTopicVo result = BeanMapper.map(snsTopic, SnsTopicVo.class);
			
			//查询主题对应前评论
			Finder hql = Finder.create(Finder.FROM);
			hql.append(SnsPost.class.getName());
			hql.append("where topicId=:topicId").setParam("topicId", topicId);
			hql.append("order by createTime asc");
			
			List list = snsPostMng.find(hql);
			if(list!=null&&list.size()>0) {
				List<SnsPostVo> snsPostVos = BeanMapper.mapList(list, SnsPostVo.class);
				result.setSnsPostVos(snsPostVos);
			}
			return result;
		}
		return null;
	}

	/**
	 * 查询评论或回复
	 */
	@Override
	public Pagination list(SnsTopicVo searchVo, Integer pageNo, Integer pageSize) {
		//TODO 不需要展示全部数据
		if(searchVo.getMemberId()!=null) {
			Finder hql = Finder.create(Finder.FROM);
			hql.append(SnsPost.class.getName());
			hql.append("order by createTime desc");
			Pagination pagination = snsPostMng.find(hql, pageNo, 5);
			if(pagination.getList()!=null) {
				List<SnsPostVo> snsPostVos = BeanMapper.mapList(pagination.getList(), SnsPostVo.class);
				pagination.setList(snsPostVos);
				return pagination;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void delete(Integer topicid,Integer userid) {
		if(topicid!=null) {
			Finder hql = Finder.create("delete from");
			hql.append(SnsPost.class.getName());
			hql.append("where topicId=:topicId").setParam("topicId", topicid);
			if(userid!=null) {
				hql.append("and memberId=:memberId").setParam("memberId", userid);
			}
			snsPostMng.update(hql);
		}
		
	}

	

	/**
	 * 新鲜事查询|爆料查询
	 */
	@Override
	public Pagination listNewsByType(SnsTopicVo searchVo, Integer pageNo, Integer pageSize) {
		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsTopic.class.getName());
		hql.append("where 1=1");
		//主题类型
		if(searchVo.getTopicType()!=null) {
			hql.append("and topicType=:topicType").setParam("topicType", searchVo.getTopicType());
		}
		//审核状态
		if(searchVo.getAuditStatus()!=null) {
			hql.append("and auditStatus =:auditStatus").setParam("auditStatus", searchVo.getAuditStatus());
		}
		//根据标题
		if(StringUtils.isNotEmpty(searchVo.getTitle())){
			hql.append("and title like :title").setParam("title", "%"+searchVo.getTitle()+"%");
		}
		//根据内容模糊查询
		if(StringUtils.isNotEmpty(searchVo.getContent())) {
			hql.append("and content like :content").setParam("content", "%"+searchVo.getContent()+"%");
		}
		if(searchVo.getMemberId()!=null) {
			hql.append("and memberId=:memberId").setParam("memberId", searchVo.getMemberId());
		}
		//时间范围查询
		if(searchVo.getBeginTime()!=null&&searchVo.getEndTime()!=null) {
			hql.append("and createTime>=:beginTime").setParam("beginTime", searchVo.getBeginTime());
			hql.append("and createTime<=:endTime").setParam("endTime", searchVo.getEndTime());
		}else {
			if(searchVo.getBeginTime()!=null) {
				hql.append("and createTime>=:beginTime").setParam("beginTime", searchVo.getBeginTime());
			}else if(searchVo.getEndTime()!=null) {
				hql.append("and createTime<=:endTime").setParam("endTime", searchVo.getEndTime());
			}
		}
		hql.append("order by createTime desc");
		Pagination pagination = snsTopicMng.find(hql, pageNo, pageSize);
		if(pagination.getList()!=null) {
			List<SnsTopicVo> snsTopicVos = BeanMapper.mapList(pagination.getList(), SnsTopicVo.class);
			//敏感词标红/过滤词标橘红色显示
			for(int i=0;i<snsTopicVos.size();i++) {
				SnsTopicVo vo = snsTopicVos.get(i);
				if(StringUtils.isNotEmpty(vo.getContent())){
					String parsedContent = snsSenstiveService.parseContent(vo.getContent());
					vo.setContent(parsedContent);
				}
				//前台显示为用户名
				/*if(vo.getMemberId()!=null) {
					UCenterMemberDto umemberDto = ucenterMemberService.findMemberById(vo.getMemberId());
					if(umemberDto!=null) {
						vo.setMemberName(umemberDto.getMemberName());
					}
				}*/
				//前台显示版块名称
				if(vo.getTopicType()!=null) {
					//版块
					//vo.setForumId(vo.getTopicType());
					
					vo.setForumId(vo.getForumId());
					
					SnsForumVo forumVo = snsForumService.findById(vo.getForumId());
					if(forumVo!=null) {
						vo.setForumName(forumVo.getForumName());
					}
				}
			}
			pagination.setList(snsTopicVos);
		}
		return pagination;
	}
	/**
	 * 新鲜事转爆料
	 */
	@Override
	@Transactional
	public void convertfreshToBroke(List<Long> topicIds,Long newsId) {
		Finder hql = Finder.create("update");
		hql.append(SnsTopic.class.getName());
		hql.append("set topicType = 1");
		//关联新闻id
		if(newsId!=null) {
			hql.append(",relatednewsId=:relatednewsId").setParam("relatednewsId", newsId);
		}
		//新鲜事id
		if(topicIds!=null&&topicIds.size()>0) {
			hql.append("where topicId in (:topicIds)").setParamList("topicIds", topicIds);
			snsTopicMng.update(hql);
		}
	}

	@Override
	public List<SnsTopic> findByIDs(List<SnsTopicVo> asList) {
		List<SnsTopic> list=new ArrayList<SnsTopic>();
		for(SnsTopicVo s:asList) {
			SnsTopic snsTopic=snsTopicMng.find(s.getTopicId());
			list.add(snsTopic);
		}
		return list;
	}

	@Override
	public List<SnsTopic> findByForumId(Long ForumId) {
		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsTopic.class.getName());
		hql.append("where 1=1");
		hql.append(" and forumId=:ForumId").setParam("ForumId", ForumId);
		return (List<SnsTopic>) snsTopicMng.find(hql);
	}

	@Override
	public void updateSnsTopic(SnsTopicVo snsTopicVo) {
		Finder hql = Finder.create("update");
		hql.append(SnsTopic.class.getName());
		hql.append("set showStatus = :showStatus").setParam("showStatus", 0);
		hql.append("where topicId=:topicId").setParam("topicId", snsTopicVo.getTopicId());
		snsTopicMng.update(hql);
	}

	@Override
	public List<SnsTopic> alllist() {
		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsPost.class.getName());
		return (List<SnsTopic>) snsTopicMng.find(hql);
	}

	@Override
	public SnsTopicVo findById(Long topicId) {
		Finder hql=Finder.create("from SnsTopic");
		hql.append(" where topicId=:topicId").setParam("topicId", topicId);
		SnsTopic topic= (SnsTopic) snsTopicMng.find(hql).get(0);
		 SnsTopicVo SnsTopicVo=new SnsTopicVo();
		 try {
			BeanUtils.copyProperties(SnsTopicVo, topic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return  SnsTopicVo;
	}

	@Override
	@Transactional
	public void TopicToNews(Long topicId,Long relatednewsId) {
		if(relatednewsId!=null) {
			Finder hql=Finder.create("update SnsTopic");
			hql.append(" set topicType=:topicType");
			hql.append(" , relatednewsId=:relatednewsId");
			hql.append(",isAdminSetBl=:isAdminSetBl");
			hql.append(" where topicId=:topicId");
			hql.setParam("topicType", 1);
			hql.setParam("relatednewsId", relatednewsId);
			hql.setParam("topicId", topicId);
			hql.setParam("isAdminSetBl", 1);
			snsTopicMng.update(hql);
			try {
				SnsTopic snsTopic=snsTopicMng.find(topicId);
				ESContent eSContent = EsContentCovertUtil.toESContent(snsTopic);
				R r = elsFeignClient.createContentFeginApi(eSContent);
				if(!r.isOk()){
					SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
					snsTopicEsSyncFail.setTopicId(topicId);
					snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
					snsTopicEsSyncFail.setFailWhat(0);
					snsTopicEsSyncFail.setIsDealSuccess(0);
					snsTopicEsSyncFailService.save(snsTopicEsSyncFail);
					throw new HServiceLogicException("同步失败：" + r.getMsg());
				}
			}catch(Exception e){
				e.printStackTrace();
				SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
				snsTopicEsSyncFail.setTopicId(topicId);
				snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
				snsTopicEsSyncFail.setFailWhat(0);
				snsTopicEsSyncFail.setIsDealSuccess(0);
				snsTopicEsSyncFailService.save(snsTopicEsSyncFail);
				throw new HServiceLogicException("同步失败：" + e.getMessage());
			}	
		}else {
			SnsTopicVo vo=findById(topicId);
			if(vo.getRelatednewsId()==null) {
				throw new HServiceLogicException(" 已经存在关联新闻不可添加");
			}
			Finder hql=Finder.create("update SnsTopic");
			hql.append(" set topicType=:topicType").setParam("topicType", 1);
			hql.append(" where topicId=:topicId").setParam("topicId", topicId);
			hql.append(",isAdminSetBl=:isAdminSetBl");
			hql.setParam("isAdminSetBl", 1);
			snsTopicMng.update(hql);
			try {
				SnsTopic snsTopic=snsTopicMng.find(topicId);
				ESContent eSContent = EsContentCovertUtil.toESContent(snsTopic);
				R r = elsFeignClient.createContentFeginApi(eSContent);
				if(!r.isOk()){
					SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
					snsTopicEsSyncFail.setTopicId(topicId);
					snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
					snsTopicEsSyncFail.setFailWhat(0);
					snsTopicEsSyncFail.setIsDealSuccess(0);
					snsTopicEsSyncFailService.save(snsTopicEsSyncFail);
					throw new HServiceLogicException("同步失败：" + r.getMsg());
				}
			}catch(Exception e){
				e.printStackTrace();
				SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
				snsTopicEsSyncFail.setTopicId(topicId);
				snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
				snsTopicEsSyncFail.setFailWhat(0);
				snsTopicEsSyncFail.setIsDealSuccess(0);
				snsTopicEsSyncFailService.save(snsTopicEsSyncFail);
				throw new HServiceLogicException("同步失败：" + e.getMessage());
			}	
		}
		
	}

	@Override
	@Transactional
	public void topicNotToHot(Long topicId) {
		Finder hql=Finder.create("update SnsTopic");
		hql.append(" set topicType=:topicType");
		hql.append(",isAdminSetBl=:isAdminSetBl");
		hql.append(" where topicId=:topicId");
		hql.setParam("topicType", 0);
		hql.setParam("topicId", topicId);
		hql.setParam("isAdminSetBl", 1);
		snsTopicMng.update(hql);
	try {
		SnsTopic snsTopic=snsTopicMng.find(topicId);
		ESContent eSContent = EsContentCovertUtil.toESContent(snsTopic);
		R r = elsFeignClient.createContentFeginApi(eSContent);
		if(!r.isOk()){
			SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
			snsTopicEsSyncFail.setTopicId(topicId);
			snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
			snsTopicEsSyncFail.setFailWhat(0);
			snsTopicEsSyncFail.setIsDealSuccess(0);
			snsTopicEsSyncFailService.save(snsTopicEsSyncFail);
			throw new HServiceLogicException("同步失败：" + r.getMsg());
		}
	}catch(Exception e){
		e.printStackTrace();
		SnsTopicEsSyncFail snsTopicEsSyncFail = new SnsTopicEsSyncFail();
		snsTopicEsSyncFail.setTopicId(topicId);
		snsTopicEsSyncFail.setCreateTime(new Timestamp(System.currentTimeMillis()));
		snsTopicEsSyncFail.setFailWhat(0);
		snsTopicEsSyncFail.setIsDealSuccess(0);
		snsTopicEsSyncFailService.save(snsTopicEsSyncFail);
		throw new HServiceLogicException("同步失败：" + e.getMessage());
	}		
		
	}

	

}
