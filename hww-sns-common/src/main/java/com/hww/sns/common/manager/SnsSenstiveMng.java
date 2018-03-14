package com.hww.sns.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sns.common.dao.SnsSenstiveDao;
import com.hww.sns.common.entity.SnsSenstive;
import com.hww.sns.common.vo.SnsSenstiveVo;

public interface SnsSenstiveMng
	extends
		IBaseEntityMng<Long, SnsSenstive, SnsSenstiveDao>
{
	
	Integer getWordCount(SnsSenstiveVo vo);

}
