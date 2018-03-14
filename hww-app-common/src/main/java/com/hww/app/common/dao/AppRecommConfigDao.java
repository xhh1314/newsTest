package com.hww.app.common.dao;

import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.entity.AppRecommConfig;
import com.hww.app.common.vo.AppRecommConfigVo;
import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.base.common.page.Pagination;


import java.util.List;

public interface AppRecommConfigDao extends IBaseEntityDao<Long,AppRecommConfig> {

    void saveDefRecomm(List<AppRecommConfig> list);

    Pagination queryRecommList(Integer type, AppRecommConfigVo vo);
    
    Pagination queryRecommLists(AppRecommConfigVo vo);

    void updateRecommNum(List<AppRecommConfig> cmsRecommConfigList);

    List<AppRecommConfig> selectRecommConfigList();
   
    
	
 
}
