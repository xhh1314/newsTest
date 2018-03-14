package com.hww.sns.webadmin.service.impl;

//import java.sql.Timestamp;
//import java.util.List;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.hww.base.common.util.Finder;
//import com.hww.base.util.BeanMapper;
//import com.hww.sns.common.entity.SnsAudit;
//import com.hww.sns.common.entity.SnsNewsAudit;
//import com.hww.sns.common.entity.SnsPost;
//import com.hww.sns.common.manager.SnsNewsAuditMng;
//import com.hww.sns.common.manager.SnsPostMng;
//import com.hww.sns.webadmin.service.SnsNewsAuditService;
//import com.hww.sns.webadmin.vo.SnsNewsAuditVo;
//
//@Service
//public class SnsNewsAuditServiceImpl implements SnsNewsAuditService {
//
//	private static final Log log = LogFactory.getLog(SnsNewsAuditServiceImpl.class);
//	@Autowired
//	SnsNewsAuditMng snsNewsAuditMng;
//	@Autowired
//	SnsPostMng snsPostMng;
//	
//	/**
//	 * 新闻评论审核
//	 */
//	@Override
//	@Transactional
//	public void saveUCenterMember(SnsNewsAuditVo snsNewsAuditVo) {
//		//当前审核流程是否通过
//		Integer currentStatus = snsNewsAuditVo.getCurentstatus();
//		//Long topicId = snsNewsAuditVo.getTopicId();
//		//评论id
//		Long postId = snsNewsAuditVo.getPostId();
//		
//		SnsNewsAudit entity = BeanMapper.map(snsNewsAuditVo, SnsNewsAudit.class);
//		
//		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
//		snsNewsAuditMng.saveUCenterMember(entity);
//		
//		int auditStatus = getAuditStatus(currentStatus);
//		//更新新闻评论
//		if(postId!=null){
//			log.info("begin update news_post");
//			Finder updateHql = Finder.create("update");
//			updateHql.append(SnsPost.class.getName());
//			updateHql.append("set auditStatus=:auditStatus").setParam("auditStatus", auditStatus);
//			updateHql.append("where postId=:postId").setParam("postId", postId);
//			//乐观锁,先审核的先通过
//			updateHql.append("and auditStatus<>:currentStatus").setParam("currentStatus", auditStatus);
//			snsPostMng.update(updateHql);
//		}
//	}
//	
//	@Override
//	@Transactional
//	public void batchSaveAudit(List<SnsNewsAuditVo> asList) {
//		if(asList!=null&&asList.size()>0) {
//			List<SnsNewsAudit> entitys = BeanMapper.mapList(asList, SnsNewsAudit.class);
//			
//			for(int i=0;i<entitys.size();i++) {
//				SnsNewsAudit entity = entitys.get(i);
//				entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			}
//			//批量插入
//			snsNewsAuditMng.saveUCenterMember(entitys);
//			
//			for(int i=0;i<asList.size();i++) {
//				SnsNewsAuditVo snsNewsAuditVo = asList.get(i);
//				//当前审核流程是否通过
//				Integer currentStatus = snsNewsAuditVo.getCurentstatus();
//				//Long topicId = SnsNewsAuditVo.getTopicId();
//				//评论id
//				Long postId = snsNewsAuditVo.getPostId();
//				
//				int auditStatus = getAuditStatus(currentStatus);
//				if(postId!=null){
//					Finder updateHql = Finder.create("update");
//					updateHql.append(SnsPost.class.getName());
//					//主题,更新主题表状态
//					updateHql.append("set auditStatus=:auditStatus").setParam("auditStatus", auditStatus);
//					updateHql.append("where postId=:postId").setParam("postId", postId);
//					//乐观锁,先审核的先通过
//					updateHql.append("and auditStatus<>:currentStatus").setParam("currentStatus", auditStatus);
//					snsPostMng.update(updateHql);
//				}
//			}
//		}
//	}
//
//	/**
//	 * 根据新闻id查询审核详情
//	 */
//	@Override
//	public List<SnsNewsAuditVo> queryAuditDetail(SnsNewsAuditVo vo) {
//		
//		Finder hql = Finder.create(Finder.FROM);
//		hql.append(SnsAudit.class.getName());
//		hql.append("where postId=:postId").setParam("postId", vo.getPostId());
//		//先创建的为一审,倒序排序用于页面显示
//		hql.append("order by createTime desc");	
//		List list = snsNewsAuditMng.find(hql);
//		if(list!=null&&list.size()>0) {
//			List<SnsNewsAuditVo> snsAuditVos = BeanMapper.mapList(list, SnsNewsAuditVo.class);
//			return snsAuditVos;
//		}
//		return null;
//	}
//	
//	/**
//	 * 获取审核状态
//	 * @return
//	 * TODO 
//	 */
//	public int getAuditStatus(Integer currentStatus) {
//		if(currentStatus==0) {
//			//拒绝
//			return 0;
//		}else if(currentStatus==1) {
//			//获取当前topicType的审核深度
//			//根据当前登录用户所属深度
//			//如果是最终审核返回审核通过状态1
//			return 1;
//		}
//		return 0;
//	}


//}
