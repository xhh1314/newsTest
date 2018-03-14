package com.hww.app.webservice.service.impl;

import com.hww.app.common.entity.AppFeedBack;
import com.hww.app.common.manager.FeedBackMng;
import com.hww.app.webservice.service.FeedBackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service("feedBackService")
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

	@Resource
    private FeedBackMng fedBackMng;

	@Override
    public AppFeedBack save(AppFeedBack feedBack) {
        return fedBackMng.save(feedBack);
	}
}
