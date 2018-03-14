package com.hww.sns.common.dao.impl;

import org.springframework.stereotype.Service;

import com.hww.base.common.util.Finder;
import com.hww.sns.common.dao.SnsSenstiveDao;
import com.hww.sns.common.entity.SnsSenstive;
import com.hww.sns.common.vo.SnsSenstiveVo;
@Service("snsSenstiveDao")
public class SnsSenstiveDaoImpl
	extends
		LocalDataBaseDaoImpl<Long, SnsSenstive>
	implements
		SnsSenstiveDao
{

	public Integer getWordCount(SnsSenstiveVo vo) {
		  Finder f = Finder.create("SELECT count(*) ");
	      f.append(" FROM sns_senstive");
	      f.append(" where word = :word");
	      f.append(" and word_type = :word_type");
	      f.setParam("word", vo.getWord());
	      f.setParam("word_type", vo.getWordType());
	      Integer wordCount = countQueryResultWithNativeInteger(f);
	      return wordCount;
	}
	
}
