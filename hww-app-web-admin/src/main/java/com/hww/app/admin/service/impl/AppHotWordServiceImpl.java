package com.hww.app.admin.service.impl;

import com.hww.app.admin.service.AppHotWordService;
import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.manager.AppHotWordMng;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.SnowFlake;
import com.hww.base.util.StringUtils;
import com.hww.base.util.TimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service("appHotWordService")
@Transactional
public class AppHotWordServiceImpl implements AppHotWordService {

    @Autowired
    AppHotWordMng appHotWordMng;

    @Override
    public Pagination list(AppHotWordDto searchDto, Integer pageNo, Integer pageSize) {
        return appHotWordMng.list(searchDto, pageNo, pageSize);
    }

    @Override
    public void saveHotWord(AppHotWordDto appHotWordDto) {
        SnowFlake snowFlake = new SnowFlake(1,1);
        Long id = snowFlake.nextId();
        appHotWordDto.setHotId(id);
        appHotWordDto.setCreateTime(TimeUtils.getDateToTimestamp());
        appHotWordDto.setLastModifyTime(TimeUtils.getDateToTimestamp());
        appHotWordMng.saveHotWord(appHotWordDto);
    }

    @Override
    public AppHotWord queryAppHotWordById(Long hotId) {
        return appHotWordMng.queryAppHotWordById(hotId);
    }

    @Override
    public void updateAppHotWord(AppHotWordDto dto) {
    	AppHotWord appHotWord = appHotWordMng.queryAppHotWordById(dto.getHotId());
    	appHotWord.setAuditStatus(dto.getAuditStatus().shortValue());
    	appHotWord.setHotWord(dto.getHotWord());
    	appHotWord.setType(dto.getType());
    	appHotWord.setStatus(dto.getStatus());
    	appHotWord.setLastModifyTime(TimeUtils.getDateToTimestamp());
        appHotWordMng.updateAppHotWord(appHotWord);
    }

    @Override
    public void handleStatu(AppHotWordDto appHotWordDto) {
        if (StringUtils.isNotBlank(appHotWordDto.getAllIDCheck())) {
            String ids = appHotWordDto.getAllIDCheck();
            if (StringUtils.isNotBlank(ids)) {
                ids.substring(0, ids.length() - 1);
                String[] mIdArray = ids.split(",");
                for (int i = 0; i < mIdArray.length; i++) {
                    if (StringUtils.isNumeric(mIdArray[i])) {
                        AppHotWord entity = appHotWordMng.queryAppHotWordById(Long.parseLong(mIdArray[i]));
                        entity.setStatus(appHotWordDto.getStatus());
                        appHotWordMng.updateAppHotWord(entity);
                    }
                }
            }
        } else {
            AppHotWord entity = appHotWordMng.queryAppHotWordById(appHotWordDto.getHotId());
            entity.setStatus(appHotWordDto.getStatus());
            appHotWordMng.updateAppHotWord(entity);
        }
    }

    @Override
    public void deleteAppHotWord(AppHotWordDto appHotWordDto) {
        if (StringUtils.isNotBlank(appHotWordDto.getAllIDCheck())) {
            String ids = appHotWordDto.getAllIDCheck();
            if (StringUtils.isNotBlank(ids)) {
                ids.substring(0, ids.length() - 1);
                String[] mIdArray = ids.split(",");
                for (int i = 0; i < mIdArray.length; i++) {
                    if (StringUtils.isNumeric(mIdArray[i])) {
                        AppHotWord entity = appHotWordMng.queryAppHotWordById(Long.parseLong(mIdArray[i]));
                        appHotWordMng.deleteAppHotWord(entity);
                    }
                }
            }
        } else {
            AppHotWord entity = appHotWordMng.queryAppHotWordById(appHotWordDto.getHotId());
            appHotWordMng.deleteAppHotWord(entity);
        }
    }

    @Override
    public boolean queryHotWordByHotWordAndType(AppHotWordDto appHotWordDto) {
        return appHotWordMng.queryHotWordByHotWordAndType(appHotWordDto);
    }
}
