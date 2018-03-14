package com.hww.sns.webadmin.service.impl;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import com.hww.base.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.sns.common.entity.SnsForum;
import com.hww.sns.common.manager.SnsForumMng;
import com.hww.sns.common.vo.SnsForumVo;
import com.hww.sns.webadmin.service.SnsForumService;

@Service("snsForumService")
@Transactional
public class SnsForumServiceImpl implements SnsForumService {

	private static final Log log = LogFactory.getLog(SnsForumServiceImpl.class);
	
	@Resource
	SnsForumMng snsForumMng;
	@Override
	public Pagination list(SnsForumVo searchVo, Integer pageNo, Integer pageSize) {

		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsForum.class.getName());
		hql.append("where 1=1");
		//版块名称
		if(StringUtils.isNotEmpty(searchVo.getForumName())) {
			hql.append("and forumName like :forumName").setParam("forumName", "%"+searchVo.getForumName()+"%");
		}
		if(StringUtils.isNotEmpty(searchVo.getTitle())){
			hql.append("and title like :title").setParam("title", "%"+searchVo.getTitle()+"%");
		}
		hql.append("order by create_time asc");//正序排序
		Pagination pagination = snsForumMng.find(hql, pageNo, pageSize);
		
		if(pagination.getList()!=null) {
			log.info("获取到分类");
			List<SnsForumVo> snsForumVos = BeanMapper.mapList(pagination.getList(), SnsForumVo.class);
			List<Long> categoryIds = new LinkedList<Long>();
			
			pagination.setList(snsForumVos);
			return pagination;
		}
		return null;
	}

	@Override
	@Transactional
	public void saveForum(SnsForumVo vo) {
		if(vo.getForumId()!=null) {
			System.out.println(vo.getForumId());
			//更新
			SnsForum entity = snsForumMng.find(vo.getForumId());
			if(entity!=null) {
				if(!StringUtils.isEmpty(vo.getTitle())) {
					entity.setTitle(vo.getTitle());
				}
				if(!StringUtils.isEmpty(vo.getForumName())) {
					entity.setForumName(vo.getForumName());
				}
				entity.setAuditFlow(vo.getAuditFlow());
				snsForumMng.update(entity);
			}
		}else {
			//新增
			SnsForum entity = BeanMapper.map(vo, SnsForum.class);
			if(entity.getSiteId()==null) {
				entity.setSiteId(1);
			}
			entity.setStatus(Short.valueOf("1"));
			entity.setShowStatus(1);
			if(vo.getParentId()!=null) {
				SnsForum parent=new SnsForum();
				parent.setForumId(vo.getParentId());
				entity.setParent(parent);
			}
			entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			snsForumMng.save(entity);
		}
	}
	
	@Override
	public SnsForumVo findById(Long forumId) {
		SnsForum entity = snsForumMng.find(forumId);
		if(entity!=null) {
			log.info("获取到版块");
			SnsForumVo vo = BeanMapper.map(entity, SnsForumVo.class);
			return vo;
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deleteForum(SnsForumVo vo) {
		if(vo.getForumId()!=null) {
			SnsForum entity = BeanMapper.map(vo, SnsForum.class);
			snsForumMng.delete(entity);
		}
		
	}
	
	@Override
	@Transactional
	public void batchDeleteForum(List<SnsForumVo> vos) {
		if(vos!=null&&vos.size()>0) {
			List<SnsForum> entitys = BeanMapper.mapList(vos, SnsForum.class);
			snsForumMng.delete(entitys);
		}
		
	}

	@Override
	public List<SnsForumVo> listAllSnsForum() {

		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsForum.class.getName());
		hql.append("where 1=1");
		hql.append(" and showStatus=:showStatus").setParam("showStatus",1);
		List list = snsForumMng.find(hql);
		
		if(list!=null) {
			log.info("获取到分类");
			List<SnsForumVo> snsForumVos = BeanMapper.mapList(list, SnsForumVo.class);
			return snsForumVos;
		}
		return null;
	}

	@Override
	public List<SnsForumVo> listSnsForumByVo(SnsForumVo vo) {
		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsForum.class.getName());
		hql.append("where 1=1");
		if(vo!=null) {
			
				//其他
				if (vo.getSiteId() != null) {
					hql.append(" and siteId=:siteIdP").setParam("siteIdP", vo.getSiteId());
				}
				if (vo.getDisplay() != null) {
					hql.append(" and is_display=:displayP").setParam("displayP", vo.getDisplay());
				}
				if (vo.getStatus() != null) {
					hql.append(" and status=:statusP").setParam("statusP", vo.getStatus());
				}
			
		}
		List<?> entitys = snsForumMng.find(hql);
		List<SnsForumVo> vos = new LinkedList<SnsForumVo>();
		if(entitys!=null) {
			log.info("获取到分类");
			
			for(int i=0;i<entitys.size();i++) {
				SnsForum entity = (SnsForum) entitys.get(i);
				SnsForumVo scv = BeanMapper.map(entity, SnsForumVo.class);
				if(entity.getParent()!=null) {
					scv.setParentId(entity.getParent().getId());
				}
				vos.add(scv);
			}
		}
		return vos;
	}

	@Override
	@Transactional
	public void updateForum(SnsForumVo vo) {
		SnsForum s=snsForumMng.find(vo.getForumId());
		s.setShowStatus(0);
		snsForumMng.update(s);
		
	}
}
