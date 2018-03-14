package com.hww.app.common.vo;

import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.entity.AppSearchHistory;

import java.io.Serializable;
import java.util.List;

public class SearchHistoryData implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4066078479325262891L;
	private List<AppSearchHistory> searchHistoryList; //搜索历史list
    private List<AppHotWord> searchHotWordList; //热词推荐list

    public List<AppSearchHistory> getSearchHistoryList() {
        return searchHistoryList;
    }

    public void setSearchHistoryList(List<AppSearchHistory> searchHistoryList) {
        this.searchHistoryList = searchHistoryList;
    }

    public List<AppHotWord> getSearchHotWordList() {
        return searchHotWordList;
    }

    public void setSearchHotWordList(List<AppHotWord> searchHotWordList) {
        this.searchHotWordList = searchHotWordList;
    }
}
