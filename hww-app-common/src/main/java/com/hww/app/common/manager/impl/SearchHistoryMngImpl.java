package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.SearchHistoryDao;
import com.hww.app.common.dto.CmsSearchHistoryDto;
import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.app.common.entity.AppSearchHistory;
import com.hww.app.common.manager.SearchHistoryMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("searchHistoryMng")
@Transactional
public class SearchHistoryMngImpl extends BaseEntityMngImpl<Long, AppSearchHistory, SearchHistoryDao> implements SearchHistoryMng {

    private SearchHistoryDao searchHistoryDao;

    @Autowired
    public void setSearchHistoryDao(SearchHistoryDao searchHistoryDao) {
        super.setEntityDao(searchHistoryDao);
        this.searchHistoryDao = searchHistoryDao;
    }

    @Override
    public AppSearchHistory saveSearchHistory(AppSearchHistory searchHistory) {
        return searchHistoryDao.save(searchHistory);
    }

	@Override
	public List<AppSearchHistory> laodSearchHistory(SearchHistoryDto searchHistoryDto) {
		return searchHistoryDao.laodSearchHistory( searchHistoryDto);
	}

	@Override
	public Boolean deletByContentAndMemberId(Long memberId, String imei,String searchContent) {
		
		if(!StringUtils.hasText(imei)&&memberId==null) {
			return false;
		}
		searchContent=(""+searchContent).trim();
		
		  Finder finder = Finder.create(" delete from AppSearchHistory s ");
		  finder.append(" where searchContent=:searchContent ");
		  finder.setParam("searchContent", searchContent);
		  
		  if(memberId!=null) {
			  finder.append(" and s.memberId=:memberId ");
			  finder.setParam("memberId", memberId);
		  }
		  
		  if(StringUtils.hasText(imei)) {
			  finder.append(" and  s.imei=:imei");
			  finder.setParam("imei", imei);
		  }
		  return searchHistoryDao.update(finder);
	}

//    @Override
//    public Pagination list(CmsSearchHistoryDto searchDto) {
//        return searchHistoryDao.list(searchDto);
//    }

}
