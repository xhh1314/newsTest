package com.hww.sns.common.dao;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.sns.common.entity.SnsSenstive;
import com.hww.sns.common.vo.SnsSenstiveVo;

public interface SnsSenstiveDao extends IBaseEntityDao<Long, SnsSenstive>{

	Integer getWordCount(SnsSenstiveVo vo);

}
