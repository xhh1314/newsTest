package com.hww.app.common.manager;


import java.util.List;

import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.vo.AppHotWordVo;
import com.hww.base.common.page.Pagination;


public interface AppHotWordMng {

    Pagination list(AppHotWordDto searchDto, Integer pageNo, Integer pageSize);

    void saveHotWord(AppHotWordDto cmsHotWordDto);

    AppHotWord queryAppHotWordById(Long hotId);

    void updateAppHotWord(AppHotWord cmsHotWord);

    void deleteAppHotWord(AppHotWord entity);

    boolean queryHotWordByHotWordAndType(AppHotWordDto cmsHotWordDto);
    
    List<AppHotWord> searchHotWord();
}
