package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppHotWordDao;
import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.manager.AppHotWordMng;
import com.hww.app.common.vo.AppHotWordVo;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("cmsHotWordMng")
@Transactional
public class AppHotWordMngImpl extends BaseEntityMngImpl<Long, AppHotWord, AppHotWordDao> implements AppHotWordMng {

    @Autowired
    private AppHotWordDao appHotWordDao;

    @Override
    public Pagination list(AppHotWordDto searchDto, Integer pageNo, Integer pageSize) {
        return appHotWordDao.list(searchDto, pageNo, pageSize);
    }

    @Override
    public void saveHotWord(AppHotWordDto cmsHotWordDto) {
    	appHotWordDao.saveHotWord(cmsHotWordDto);
    }

    @Override
    public AppHotWord queryAppHotWordById(Long hotId) {
        return appHotWordDao.find(hotId);
    }

    @Override
    public void updateAppHotWord(AppHotWord cmsHotWord) {
    	appHotWordDao.update(cmsHotWord);
    }

    @Override
    public void deleteAppHotWord(AppHotWord entity) {
    	appHotWordDao.delete(entity);
    }

    @Override
    public boolean queryHotWordByHotWordAndType(AppHotWordDto appHotWordDto) {
        return appHotWordDao.queryHotWordByHotWordAndType(appHotWordDto);
    }
    

	@Override
	public List<AppHotWord> searchHotWord() {
		return appHotWordDao.searchHotWord();
	}
}
