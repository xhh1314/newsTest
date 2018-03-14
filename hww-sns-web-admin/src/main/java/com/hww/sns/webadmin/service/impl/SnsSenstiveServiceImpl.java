package com.hww.sns.webadmin.service.impl;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hww.base.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.sns.common.entity.SnsSenstive;
import com.hww.sns.common.manager.SnsSenstiveMng;
import com.hww.sns.common.util.SensitivewordFilter;
import com.hww.sns.common.vo.SnsSenstiveVo;
import com.hww.sns.webadmin.service.SnsSenstiveService;

@Service
@Transactional
public class SnsSenstiveServiceImpl implements SnsSenstiveService {
	
	private static final Log log = LogFactory.getLog(SnsSenstiveServiceImpl.class);

	@Autowired
	SnsSenstiveMng snsSenstiveMng;
	
	@Override
	public Pagination list(SnsSenstiveVo searchVo, Integer pageNo, Integer pageSize) {
		Finder hql = Finder.create(Finder.FROM);
		hql.append(SnsSenstive.class.getName());
		hql.append("where 1=1");
		if(searchVo.getWordType()!=null) {
			hql.append("and wordType=:wordType").setParam("wordType", searchVo.getWordType());
		}
		if(StringUtils.isNotEmpty(searchVo.getWord())) {
			hql.append("and word like :word").setParam("word", "%"+searchVo.getWord()+"%");
		}
		
		Pagination pagination = snsSenstiveMng.find(hql, pageNo, pageSize);
		if(pagination.getList()!=null) {
			List<SnsSenstiveVo> vos = BeanMapper.mapList(pagination.getList(), SnsSenstiveVo.class);
			pagination.setList(vos);
		}
		return pagination;
	}
	
	@Override
	@Transactional
	public void saveSenstive(SnsSenstiveVo snsSenstiveVo) {
		if(snsSenstiveVo.getSenstiveId()!=null) {
			//更新
			SnsSenstive entity = snsSenstiveMng.find(snsSenstiveVo.getSenstiveId());
			if(entity!=null) {
				if(!StringUtils.isEmpty(snsSenstiveVo.getWord())) {
					entity.setWord(snsSenstiveVo.getWord());
				}
				entity.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
				snsSenstiveMng.update(entity);
			}
		}else {
			//新增
			SnsSenstive entity = BeanMapper.map(snsSenstiveVo, SnsSenstive.class);
			/*entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			snsSenstiveMng.save(entity);*/
			if(entity.getSiteId()==null) {
				entity.setSiteId(1);
			}
			entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			//TODO 获取当前登录用户名
			entity.setCreator("hewei");
			snsSenstiveMng.save(entity);
		}
	}

	@Override
	@Transactional
	public void deleteSenstiveword(SnsSenstiveVo vo) {
		if(vo.getSenstiveId()!=null) {
			snsSenstiveMng.delete(vo.getSenstiveId());
		}
		
	}

	@Override
	@Transactional
	public void batchDeleteSenstiveword(List<SnsSenstiveVo> vos) {
		if(vos!=null&&vos.size()>0) {
			List<SnsSenstive> entitys = BeanMapper.mapList(vos, SnsSenstive.class);
			snsSenstiveMng.delete(entitys);
		}
		
	}

	@Override
	public SnsSenstiveVo findById(Long senstiveId) {
		SnsSenstive entity = snsSenstiveMng.find(senstiveId);
		if(entity!=null) {
			log.info("获取到分类");
			SnsSenstiveVo vo = BeanMapper.map(entity, SnsSenstiveVo.class);
			return vo;
		}
		return null;
	}

	@Override
	public String parseContent(String originContent) {
		Finder finder = Finder.create("select wordType,word");
		finder.append(Finder.FROM);
		finder.append(SnsSenstive.class.getName());
		
		List<?> list = snsSenstiveMng.find(finder);
		Set<String> senstiveWordSet = new HashSet<String>();
		Set<String> filterWordSet = new HashSet<String>();
		for(int i=0;i<list.size();i++) {
			Object[] obj = (Object[]) list.get(i);
			Integer wordType = (Integer) obj[0];
			String word = obj[1].toString();
			if(wordType==0) {
				//敏感词
				senstiveWordSet.add(word);
			}else if(wordType==1) {
				//过滤词
				filterWordSet.add(word);
			}
		}
		
		SensitivewordFilter filter = new SensitivewordFilter(senstiveWordSet,filterWordSet);
		String text1 = filter.wrapSensitiveWord(originContent, SensitivewordFilter.maxMatchType, SensitivewordFilter.sensitiveWordMap, "red");
		String text2 = filter.wrapSensitiveWord(text1, SensitivewordFilter.maxMatchType, SensitivewordFilter.filterWordMap, "orangered");
		return text2;
	}

	@Override
	public Integer getWordCount(SnsSenstiveVo vo) {
		Integer wordCount = snsSenstiveMng.getWordCount(vo);
		return wordCount;
	}

}
