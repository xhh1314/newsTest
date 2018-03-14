package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.SearchHistoryDao;
import com.hww.app.common.dto.CmsSearchHistoryDto;
import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.app.common.entity.AppSearchHistory;
import com.hww.app.common.vo.SearchHistoryVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.StringUtils;
import com.hww.els.common.vo.SearchNewsVo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchHistoryDaoImpl extends LocalEntityDaoImpl<Long, AppSearchHistory> implements SearchHistoryDao {
	
	
    @Override
    public List<AppSearchHistory> laodSearchHistory(SearchHistoryDto dto) {
        Finder finder = Finder.create("from AppSearchHistory where 1=1");
        if (dto.getMemberId() != null) {
            finder.append(" and member_id=:memberId");
            finder.setParam("memberId", dto.getMemberId());
        } else if (dto.getImei() != null) {
            finder.append(" and imei=:imei");
            finder.setParam("imei", dto.getImei());
        } else {
            return null;
        }
        if (dto.getType() != null) {
            finder.append(" and type=:type");
            finder.setParam("type", dto.getType());
        }
        finder.append(" group by searchContent");
        finder.append(" order by createTime desc");
        if(dto.getPageNo()!=null&&dto.getPageSize()!=null) {
        	
            finder.setFirstResult((dto.getPageNo() - 1)* dto.getPageSize());
            finder.setMaxResults(dto.getPageSize());
        }else {
        	finder.setFirstResult(0);
            finder.setMaxResults(10);
        }
       
        return (List<AppSearchHistory>) find(finder);
    }

//    @Override
//    public Pagination list(CmsSearchHistoryDto searchDto) {
//        Finder finder = Finder.create("select count(1) as num, search_content as searchContent from cms_search_history where 1=1");
//        if (StringUtils.isNotBlank(searchDto.getSearchContent())) {
//            finder.append(" and search_content=:searchContent");
//            finder.setParam("searchContent", searchDto.getSearchContent());
//        }
//        if (searchDto.getType() != null) {
//            finder.append(" and type=:type");
//            finder.setParam("type", searchDto.getType());
//        }
//        finder.append(" group by searchContent");
//        finder.append(" order by num desc");
//        finder.setFirstResult((searchDto.getPageNo() - 1) * searchDto.getPageSize());
//        finder.setMaxResults(searchDto.getPageSize());
//        List<SearchHistoryVo> list = (List<SearchHistoryVo>) findJoin(finder, SearchHistoryVo.class);
//        Finder hql = Finder.create("from AppSearchHistory where 1=1");
//        if (StringUtils.isNotBlank(searchDto.getSearchContent())) {
//            hql.append(" and search_content=:searchContent");
//            hql.setParam("searchContent", searchDto.getSearchContent());
//        }
//        if (searchDto.getType() != null) {
//            hql.append(" and type=:type");
//            hql.setParam("type", searchDto.getType());
//        }
//        hql.append(" group by searchContent");
//        List<AppSearchHistory> searchHistoryList = (List<AppSearchHistory>) find(hql);
//        Pagination p = new Pagination(searchDto.getPageNo(), searchDto.getPageSize(), searchHistoryList.size());
//        p.setList(list);
//        return p;
//    }

 
}