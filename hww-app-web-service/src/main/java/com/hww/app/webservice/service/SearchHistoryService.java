package com.hww.app.webservice.service;

import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.app.common.entity.AppSearchHistory;
import com.hww.app.common.vo.SearchHistoryData;
import com.hww.app.common.vo.SearchHistoryVo;
import java.util.List;

public interface SearchHistoryService {

	
	SearchHistoryData searchHistoryData(SearchHistoryVo vo);
	  
	  
    AppSearchHistory saveSearchHistory(SearchHistoryDto searchHistoryDto);

    public List<SearchHistoryDto> laodSearchHistoryList(SearchHistoryDto searchHistoryDto)  ;


    boolean delSearchHistory(Long id);
}
