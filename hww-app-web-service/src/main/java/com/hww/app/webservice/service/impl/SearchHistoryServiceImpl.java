package com.hww.app.webservice.service.impl;

import com.google.common.collect.Lists;
import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.entity.AppSearchHistory;
import com.hww.app.common.manager.AppHotWordMng;
import com.hww.app.common.manager.SearchHistoryMng;
import com.hww.app.common.vo.SearchHistoryData;
import com.hww.app.common.vo.SearchHistoryVo;
import com.hww.app.webservice.service.SearchHistoryService;
import com.hww.base.util.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Autowired
    private SearchHistoryMng searchHistoryMng;


    @Autowired
    private AppHotWordMng searchHotWordMng;

    
    
    @Override
    public List<SearchHistoryDto> laodSearchHistoryList(SearchHistoryDto searchHistoryDto)  {
    	  List<AppSearchHistory> searchHistoryList = searchHistoryMng.laodSearchHistory(searchHistoryDto);
    	  if(searchHistoryList==null||searchHistoryList.isEmpty()) {
    		  return Lists.newArrayList();
    	  }
    	  List<SearchHistoryDto> list = new ArrayList<>();
    	  for (AppSearchHistory history : searchHistoryList) {
              SearchHistoryDto historyDto = new SearchHistoryDto();
              try {
  				BeanUtils.copyProperties(historyDto, history);
  			} catch (Exception e) {
  				e.printStackTrace();
  			}
              list.add(historyDto);
          }
    	  return list;
    }
    
    @Override
    public AppSearchHistory saveSearchHistory(SearchHistoryDto searchHistoryDto) {
    	if(searchHistoryDto==null||!StringUtils.hasText(searchHistoryDto.getSearchContent())) {
    		return null;
    	}
    	AppSearchHistory searchHistory = new AppSearchHistory();
    	try {
			BeanUtils.copyProperties(searchHistory, searchHistoryDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return searchHistoryMng.saveSearchHistory(searchHistory);
    }
 
    
    @Override
    public SearchHistoryData searchHistoryData(SearchHistoryVo vo) {
    	SearchHistoryDto searchHistoryDto=new SearchHistoryDto();
    	searchHistoryDto.setMemberId(vo.getMemberId()).setImei(vo.getImei()).setPageNo(vo.getPageNo()).setPageSize(vo.getPageSize()).setType(vo.getType());
    	
    	List<AppSearchHistory> searchHistoryList = searchHistoryMng.laodSearchHistory(searchHistoryDto);
        List<AppHotWord> searchHotWordList = searchHotWordMng.searchHotWord();
        SearchHistoryData searchHistoryData = new SearchHistoryData();
        searchHistoryData.setSearchHistoryList(searchHistoryList);
        searchHistoryData.setSearchHotWordList(searchHotWordList);
        return searchHistoryData;
    }
    
    

    @Override
    public boolean delSearchHistory(Long id) {
    	if(id!=null) {
    		AppSearchHistory his=searchHistoryMng.find(id);
    		if(his!=null) {
    			searchHistoryMng.deletByContentAndMemberId(his.getMemberId(),his.getImei(),his.getSearchContent());
    			return true;
    		}
    	}
    	return false;
    }

}