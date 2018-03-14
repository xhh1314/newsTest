package com.hww.app.webservice.controller;

import com.hww.app.webservice.service.AppMemberBehaviorCountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app/behaviorCount")
public class AppMemberBehaviorCountController {

    private static final Log log = LogFactory.getLog(AppMemberBehaviorCountController.class);

    @Autowired
    private AppMemberBehaviorCountService appMemberBehaviorCountService;

    @RequestMapping(value = "/behaviorCount.do/{contentId}/{plateType}", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Integer> behaviorCount(@PathVariable("contentId")Long contentId,@PathVariable("plateType")Integer plateType) {
        return appMemberBehaviorCountService.behaviorCount(contentId,plateType);

    }

}




