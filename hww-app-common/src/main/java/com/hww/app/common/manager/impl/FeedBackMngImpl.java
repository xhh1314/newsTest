package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppFeedBackDao;
import com.hww.app.common.dto.AppFeedBackDto;
import com.hww.app.common.entity.AppFeedBack;
import com.hww.app.common.manager.FeedBackMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.util.TimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("feedBackMng")
@Transactional
public class FeedBackMngImpl extends BaseEntityMngImpl<Long, AppFeedBack, AppFeedBackDao> implements FeedBackMng {

    private AppFeedBackDao feedBackDao;

    @Autowired
    public void setFeedBackDao(AppFeedBackDao feedBackDao) {
        super.setEntityDao(feedBackDao);
        this.feedBackDao = feedBackDao;
    }
    public AppFeedBack saveAppFeedBack(AppFeedBack feedBack) {
    	feedBack.setCreateTime(TimeUtils.getDateToTimestamp());
    	feedBack.setLastModifyTime(TimeUtils.getDateToTimestamp());
        return feedBackDao.save(feedBack);
    }
	public Pagination queryAppFeedBack(AppFeedBackDto dto, Integer pageNo, Integer pageSize) {
		return feedBackDao.queryAppFeedBack(dto, pageNo, pageSize);
	}

	

}
