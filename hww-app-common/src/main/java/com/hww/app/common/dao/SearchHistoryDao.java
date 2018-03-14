package com.hww.app.common.dao;

import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.app.common.entity.AppSearchHistory;
import com.hww.base.common.dao.IBaseEntityDao;

import java.util.List;

public interface SearchHistoryDao extends IBaseEntityDao<Long, AppSearchHistory> {

    List<AppSearchHistory> laodSearchHistory(SearchHistoryDto searchHistoryDto);

//    Pagination list(CmsSearchHistoryDto searchDto);
//
//    List<AppSearchHistory> querySearchHistoryByContentAndType(SearchNewsVo vo, Integer type);
}
