package com.hww.app.webservice.controller;

import com.hww.app.common.entity.AppUninterestWord;
import com.hww.app.webservice.service.AppUninterestWordService;
import com.hww.base.util.R;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/uninterestWord")
public class AppUninterestWordController {

    private static final Log log = LogFactory.getLog(AppUninterestWordController.class);

    @Autowired
    private AppUninterestWordService uninterestWordService;


    @RequestMapping(value = "/loadAll", method = RequestMethod.POST)
    @ResponseBody
    public R loadAll() {
    	try {
    		
    		List<AppUninterestWord> all=uninterestWordService.loadAll();
            return R.ok().put("data", all);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "提交失败");
        }
    }
   

}
