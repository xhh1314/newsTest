package com.hww.app.common.dao;

import java.util.List;

import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppHotWord;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.base.common.page.Pagination;


public interface AppHotWordDao extends IBaseEntityDao<Long, AppHotWord> {

   Pagination list(AppHotWordDto searchDto, Integer pageNo, Integer pageSize);

    void saveHotWord(AppHotWordDto appHotWordDto);

    boolean queryHotWordByHotWordAndType(AppHotWordDto appHotWordDto);
    
    List<AppHotWord> searchHotWord();
}
