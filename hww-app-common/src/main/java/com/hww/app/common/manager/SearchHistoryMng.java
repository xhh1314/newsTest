package com.hww.app.common.manager;

import java.util.List;

import com.hww.app.common.dao.SearchHistoryDao;
import com.hww.app.common.dto.CmsSearchHistoryDto;
import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.app.common.entity.AppSearchHistory;
import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.page.Pagination;

public interface SearchHistoryMng extends IBaseEntityMng<Long, AppSearchHistory, SearchHistoryDao> {

    AppSearchHistory saveSearchHistory(AppSearchHistory searchHistory);
    
    
    List<AppSearchHistory> laodSearchHistory(SearchHistoryDto searchHistoryDto);


    Boolean deletByContentAndMemberId(Long memberId,String imei, String searchContent);
    
    
    

//    Pagination list(CmsSearchHistoryDto searchDto);
}
