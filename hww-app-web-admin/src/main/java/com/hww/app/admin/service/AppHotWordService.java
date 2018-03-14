package com.hww.app.admin.service;


import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppHotWord;
import com.hww.base.common.page.Pagination;


public interface AppHotWordService {

    Pagination list(AppHotWordDto searchDto, Integer pageNo, Integer pageSize);

    void saveHotWord(AppHotWordDto cmsHotWordDto);

    AppHotWord queryAppHotWordById(Long hotId);

    void updateAppHotWord(AppHotWordDto vo);

    void handleStatu(AppHotWordDto cmsHotWordDto);

    void deleteAppHotWord(AppHotWordDto cmsHotWordDto);

    boolean queryHotWordByHotWordAndType(AppHotWordDto cmsHotWordDto);
}
