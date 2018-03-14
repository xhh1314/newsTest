package com.hww.app.webservice.controller;

import com.hww.app.common.entity.AppFeedBack;
import com.hww.app.webservice.service.FeedBackService;
import com.hww.base.util.R;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/feedBack")
public class FeedBackController {

    private static final Log log = LogFactory.getLog(FeedBackController.class);

    @Autowired
    FeedBackService feedBackService;

    @RequestMapping(value = "save.do", method = RequestMethod.POST)
    @ResponseBody
    public R save(AppFeedBack feedBack) {
        if (feedBack.getContent() == null || "".equals(feedBack.getContent())) {
            return R.error(500, "内容不能为空");
        }
        if (feedBack.getMemberId() == null) {
            return R.error(500, "申请人不能为空");
        }
        try {
            AppFeedBack fb = feedBackService.save(feedBack);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "提交失败");
        }
    }
}
