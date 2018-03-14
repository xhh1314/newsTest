package com.hww.sns.webadmin.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.R;
import com.hww.base.util.SnowFlake;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.entity.ESContent;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.common.Constants;
import com.hww.sns.common.entity.SnsAudit;
import com.hww.sns.common.entity.SnsPost;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.entity.SnsTopicEsSyncFail;
import com.hww.sns.common.manager.SnsAuditMng;
import com.hww.sns.common.manager.SnsPostMng;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.EsContentCovertUtil;
import com.hww.sns.common.vo.SnsAuditVo;
import com.hww.sns.common.vo.SnsPostVo;
import com.hww.sns.webadmin.service.SnsAuditService;
import com.hww.sns.webadmin.service.SnsTopicEsSyncFailService;
import com.hww.sns.webadmin.service.SnsTopicService;

@Service
public class SnsAuditServiceImpl implements SnsAuditService {

	@Autowired
	SnsAuditMng snsAuditMng;
	@Autowired
	SnsTopicMng snsTopicMng;
	@Autowired
	SnsPostMng snsPostMng;
	@Autowired
	ElsFeignClient elsFeignClient;
	@Autowired
	SnsTopicEsSyncFailService snsTopicEsSyncFailService;
	@Autowired
	SnsTopicService snsTopicService;
	
	
	
	
	@Override
	@Transactional
	public void save(SnsAuditVo snsAuditVo) {
		//当前审核流程是否通过
		Integer currentStatus = snsAuditVo.getCurentstatus();
		int auditStatus = getAuditStatus(currentStatus);
		Long topicId = snsAuditVo.getTopicId();
		Integer topicType = snsAuditVo.getTopicType();
		//先查如果有审核通过记录不能再审核
		Integer dbAuditStatus = getAuditStatus(topicType, topicId, auditStatus);
		if(dbAuditStatus!=null&&dbAuditStatus==auditStatus) {
			return;
		}
		SnsAudit entity = BeanMapper.map(snsAuditVo, SnsAudit.class);
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		//生成主键id
		SnowFlake flake = new SnowFlake(1, 1);
		Long auditId = flake.nextId();
		entity.setAuditId(auditId);
		
		snsAuditMng.save(entity);
		
		//更新对应主题或者评论的状态
		this.updateAuditStatus(topicType, topicId, auditStatus);
		
	}
	
	@Override
	@Transactional
	public void batchSaveAudit(List<SnsAuditVo> asList) {
		if(asList!=null&&asList.size()>0) {
			List<SnsAudit> entitys = BeanMapper.mapList(asList, SnsAudit.class);
			
			for(int i=0;i<entitys.size();i++) {
				SnsAudit entity = entitys.get(i);
				entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			}
			//批量插入
			snsAuditMng.save(entitys);
			for(int i=0;i<asList.size();i++) {
				SnsAuditVo snsAuditVo = asList.get(i);
				//当前审核流程是否通过
				Integer currentStatus = snsAuditVo.getCurentstatus();
				Long topicId = snsAuditVo.getTopicId();
				Integer topicType = snsAuditVo.getTopicType();
				
				int auditStatus = getAuditStatus(currentStatus);
				//更新对应主题或者评论的状态
				this.updateAuditStatus(topicType, topicId, auditStatus);

				
			}
		}
	}
	/**
	 * 查询对应主题或者评论的状态
	 * @param topicType
	 * @param topicId
	 * @param auditStatus
	 */
	public Integer getAuditStatus(Integer topicType,Long topicId,int auditStatus) {
		Integer result = null;
		if(topicType!=null&&topicId!=null){
			Finder queryHql = Finder.create(Finder.FROM);
			if(topicType==0) {
				//主题
				queryHql.append(SnsTopic.class.getName());
				queryHql.append("where topicId=:topicId").setParam("topicId", topicId);
				queryHql.append("and auditStatus=:currentStatus").setParam("currentStatus", auditStatus);
				List<SnsTopic> results = (List<SnsTopic>) snsTopicMng.find(queryHql);
				if(results!=null&&results.size()>0) {
					SnsTopic snsTopic = results.get(0);
					if(snsTopic!=null) {
						result = snsTopic.getAuditStatus();
					}
				}
			}else if(topicType==1) {
				//评论
				queryHql.append(SnsPost.class.getName());
				queryHql.append("where postId=:postId").setParam("postId", topicId);
				queryHql.append("and auditStatus<>:currentStatus").setParam("currentStatus", auditStatus);
				List<SnsPost> results = (List<SnsPost>)snsPostMng.find(queryHql);
				if(results!=null&&results.size()>0) {
					SnsPost snsPost = results.get(0);
					if(snsPost!=null) {
						result = snsPost.getAuditStatus();
					}
				}
			}
		}
		return result;
	}
	/**
	 * 更新对应主题或者评论的状态
	 * @param topicType
	 * @param topicId
	 * @param auditStatus
	 */
	public void updateAuditStatus(Integer topicType,Long topicId,int auditStatus) {
		if(topicType!=null&&topicId!=null){
			Finder updateHql = Finder.create("update");
			if(topicType==0) {
				//主题,更新主题表状态
				updateHql.append(SnsTopic.class.getName());
				updateHql.append("set auditStatus=:auditStatus").setParam("auditStatus", auditStatus);
				updateHql.append(", showStatus=:showStatus").setParam("showStatus", auditStatus);
				updateHql.append("where topicId=:topicId").setParam("topicId", topicId);
				//乐观锁,先审核的先通过
				updateHql.append("and auditStatus<>:currentStatus").setParam("currentStatus", auditStatus);
				snsTopicMng.update(updateHql);
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
			}else if(topicType==1) {
				//评论
				updateHql.append(SnsPost.class.getName());
				updateHql.append("set auditStatus=:auditStatus").setParam("auditStatus", auditStatus);
				updateHql.append(", showStatus=:showStatus").setParam("showStatus", auditStatus);
				updateHql.append("where postId=:postId").setParam("postId", topicId);
				//乐观锁,先审核的先通过
				updateHql.append("and auditStatus<>:currentStatus").setParam("currentStatus", auditStatus);
				snsPostMng.update(updateHql);
			}
		}
	}

	/**
	 * 通用审核详情
	 */
	@Override
	public List<SnsAuditVo> queryAuditDetail(Long topicId) {
		
		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsAudit.class.getName());
		hql.append("where topicId=:topicId").setParam("topicId", topicId);
		//先创建的为一审,倒序排序用于页面显示
		hql.append("order by createTime desc");	
		List list = snsAuditMng.find(hql);
		if(list!=null&&list.size()>0) {
			List<SnsAuditVo> snsAuditVos = BeanMapper.mapList(list, SnsAuditVo.class);
			return snsAuditVos;
		}
		return null;
	}
	
	/**
	 * 获取审核状态
	 * @return
	 * TODO 
	 */
	public int getAuditStatus(Integer currentStatus) {
		if(currentStatus==Constants.auditStatus.committed) {
			//拒绝
			return Constants.auditStatus.committed;
		}else if(currentStatus==Constants.auditStatus.autitpassed) {
			//获取当前topicType的审核深度
			//根据当前登录用户所属深度
			//如果是最终审核返回审核通过状态1
			return Constants.auditStatus.autitpassed;
		}
		return Constants.auditStatus.committed;
	}

	

}
