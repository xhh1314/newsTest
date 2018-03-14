package com.hww.app.webservice.controller;

import com.hww.base.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/app/otherConfig")
public class AppOtherConfigController {

    @Value("${colorOfMain}")
    private String color;

    /**
     * 给客户端返回首页颜色，正常或者红色,值只能是: 0 正常 1 红色
     * @return
     */
    @RequestMapping(value = "colorOfMain.do",method = RequestMethod.GET)
    public R colorOfMain(){
        if(!color.equals("0") && !"1".equals(color))
            R.error("错误的配置值！"+color);
        return R.ok().put("data",color);
    }
}
