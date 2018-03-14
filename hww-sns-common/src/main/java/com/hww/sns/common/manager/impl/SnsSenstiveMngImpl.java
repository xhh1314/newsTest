package com.hww.sns.common.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.sns.common.dao.SnsSenstiveDao;
import com.hww.sns.common.entity.SnsSenstive;
import com.hww.sns.common.manager.SnsSenstiveMng;
import com.hww.sns.common.vo.SnsSenstiveVo;
@Service("snsSenstiveMng")
public class SnsSenstiveMngImpl
    extends
        BaseEntityMngImpl<Long, SnsSenstive, SnsSenstiveDao>
    implements
        SnsSenstiveMng
{
	
    SnsSenstiveDao snsSenstiveDao;

    @Autowired
    public void setSnsSenstiveDao(SnsSenstiveDao snsSenstiveDao) {
        super.setEntityDao(snsSenstiveDao);
        this.snsSenstiveDao = snsSenstiveDao;
    }

	
	public Integer getWordCount(SnsSenstiveVo vo) {
		Integer wordCount = snsSenstiveDao.getWordCount(vo);
		return wordCount;
	}
    
}
