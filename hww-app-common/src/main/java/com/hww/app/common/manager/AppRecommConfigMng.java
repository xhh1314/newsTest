package com.hww.app.common.manager;

import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.entity.AppRecommConfig;
import com.hww.app.common.vo.AppRecommConfigVo;
import com.hww.base.common.page.Pagination;


import java.util.List;

public interface AppRecommConfigMng {

    void saveDefRecomm(List<AppRecommConfig> list);

    Pagination queryRecommList(Integer type, AppRecommConfigVo vo);
    Pagination queryRecommLists( AppRecommConfigVo vo);

    void updateRecommNum(List<AppRecommConfig> cmsRecommConfigList);

    AppRecommConfig queryRecommById(Long columnId);
    public AppRecommConfig queryAppRecommConfig(Long columnId);
    void saveConfig(AppRecommConfig config);
    List<AppRecommConfig> selectRecommConfigList();
    public List<AppRecommConfigDto> loadAllRecomm() ;
	public List<AppRecommConfigDto> loadRecommByType(Integer type);
	
	public AppRecommConfig queryListById(Long id);
	
	public void updateAppRecomm(AppRecommConfig appRecommConfig);
	
	public void delRecommById(Long columnId);
	
}
