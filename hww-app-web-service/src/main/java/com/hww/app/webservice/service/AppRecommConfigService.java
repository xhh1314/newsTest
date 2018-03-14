package com.hww.app.webservice.service;

import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppRecommConfig;
import com.hww.app.common.vo.AppRecommConfigVo;
import com.hww.base.common.page.Pagination;


import java.util.List;

public interface AppRecommConfigService {

    void saveRecomm(Integer siteId, List<AppCategory> appCategoryList);

    Pagination queryRecommList(Integer type, AppRecommConfigVo vo);

    void updateRecommNum(String columnIds);

    void saveNewThing(Integer siteId);

    void saveSpecialList(Integer siteId);
    List<AppRecommConfigDto> loadAllRecomm();
    AppRecommConfigVo queryAppRecommConfig(Long columnId);
    
    /**
     * recomm 按type查询
     */
    
    List<AppRecommConfigDto> loadRecommByType(Integer type);
    

    /**
	 * 新增一个recomm
	 */
	
	public AppRecommConfig saveAppCategory(AppCategory appCategory);
    

	/*
	 * 根据categoryid查询recomm列表中的点赞数
	 */
	
	public AppRecommConfig queryListById(Long id);
	
	/**
	 * 添加有推荐数
	 */
	void updateAppRecomm(AppRecommConfigVo appRecommConfigVo);
	/**
	 * 删除推荐频道
	 * @param columnId
	 */
	public void delRecommById(Long columnId);
	
	  /**
		 * 新增一个recomm 推荐
		 */
		
		public void saveAppRecommConfig(AppRecommConfig appRecommConfig);
    
    
		 Pagination queryRecommLists(AppRecommConfigVo vo);
}
