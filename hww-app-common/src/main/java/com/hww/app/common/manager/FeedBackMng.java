package com.hww.app.common.manager;

import com.hww.app.common.dao.AppFeedBackDao;
import com.hww.app.common.dto.AppFeedBackDto;
import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppFeedBack;
import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.base.common.page.Pagination;

public interface FeedBackMng extends IBaseEntityMng<Long, AppFeedBack, AppFeedBackDao> {

    AppFeedBack saveAppFeedBack(AppFeedBack feedBack);
    public Pagination queryAppFeedBack(AppFeedBackDto dto ,Integer pageNo, Integer pageSize);
    

}
