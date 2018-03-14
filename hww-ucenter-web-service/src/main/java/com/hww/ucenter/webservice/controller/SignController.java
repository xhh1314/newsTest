package com.hww.ucenter.webservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.hww.base.util.R;
import com.hww.ucenter.common.dto.MySignDto;
import com.hww.ucenter.common.dto.MySignListByStreetDTO;
import com.hww.ucenter.common.dto.SignListCheckDTO;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.util.ValidatorUtils;
import com.hww.ucenter.common.vo.MySignListByStreetVO;
import com.hww.ucenter.common.vo.MySignVo;
import com.hww.ucenter.common.vo.SaveSignVo;
import com.hww.ucenter.common.vo.SignVo;
import com.hww.ucenter.webservice.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/ucenter/sign")
public class SignController {

    @Autowired
    private SignService signService;

    /**
     * 判断定位列表是否签到
     *
     * @return
     * @author yx
     * @email 798823035@qq.com
     * @date 2017年11月4日 上午11:29:07
     * @version v0.1
     */
    @RequestMapping(value = "listCheck.do", method = {RequestMethod.GET, RequestMethod.POST})
    public R listCheck(SignListCheckDTO dto) {

        // 解析json，获取地址列表 [{address:"地址描述"}]
        List<SignVo> signs;
        try {
            signs = JSONArray.parseArray(dto.getSignsStr(), SignVo.class);
            if (signs == null || signs.size() == 0) {
                return R.error(1, "定位数据为空");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return R.error(1, "定位数据解析失败");
        }

        try {
            List<SignVo> list = signService.matchingSignList(signs, dto);
            return R.ok().put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询失败");

        }

    }

    /**
     * 保存签到数据
     */
    @RequestMapping(value = "/saveSign.do", method = {RequestMethod.GET, RequestMethod.POST})
    public R saveSign(SaveSignVo vo) {
        Map<String, String> map = ValidatorUtils.validateEntity(vo);
        if (!map.get("status").equals("200")) {
            return R.error(1, map.get("message"));
        }
//        MySignDto signDto = new MySignDto();
//        signDto.setMemberId(vo.getMemberId());
//        if (signService.isTodaySigned(signDto)) {
//            return R.ok("今天已经签到过了");
//        }
        String address=vo.getAddress();
        
       List<Map> siginList= signService.loadInMinutes(vo.getMemberId(), 30,null, null, null);
       
        if(siginList!=null&&!siginList.isEmpty()) {
        	if(address!=null) {
        		for(Map mapx:siginList ) {
            		if((address).equals(mapx.get("address"))) {
            			return R.error(2, "同一地点三十分钟内只能签到一次");
            		}
            	}
        	}
        	
        	//return R.error(2, "同一地点三十分钟内只能签到一次");
        }
        try {
            signService.saveSign(vo);
            return R.ok("签到成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "签到失败");
        }
    }

    /**
     * 根据街道名称分组查询签到列表
     */
    @RequestMapping(value = "/mySignListByStreet.do", method = {RequestMethod.GET, RequestMethod.POST})
    public R mySignListByStreet(MySignListByStreetDTO dto) {
        List<MySignListByStreetVO> signList;
        try {
            signList = signService.mySignListByStreet(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询失败");
        }

        return R.ok().put("data", signList);
    }

    /**
     * 我的足迹
     */
    @RequestMapping(value = "/mySign.do", method = {RequestMethod.GET, RequestMethod.POST})
    public R mySign(MySignVo vo) {

        Map<String, String> map = ValidatorUtils.validateEntity(vo);
        if (!map.get("status").equals("200")) {
            return R.error(500, map.get("message"));
        }

        try {

            // 获取当前时间的前一个月时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String now = format.format(c.getTime());
            c.add(Calendar.MONTH, -1);
            String start = format.format(c.getTime());
            MySignDto dto = new MySignDto();
            dto.setCurrentDate(now);
            dto.setOneMonthDate(start);
            dto.setMemberId(vo.getMemberId());
            List<UCenterSign> list = signService.mySignList(dto);
            return R.ok().put("data", list);
        } catch (Exception e) {

            e.printStackTrace();
            return R.error(500, "查询失败");
        }

    }

    /**
     * 30天最近4次签到记录
     *
     * @return
     * @author lyb
     * @email 674142624@qq.com
     * @date 2017年12月4日 下午2:11:55
     * @version v0.1
     */
    @RequestMapping(value = "/mySignListData.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<UCenterSign> oneCuSign(MySignVo vo) {

        try {
            // 获取当前时间的前一个月时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String now = format.format(c.getTime());
            c.add(Calendar.MONTH, -1);
            String start = format.format(c.getTime());
            MySignDto dto = new MySignDto();
            dto.setCurrentDate(now);
            dto.setOneMonthDate(start);
            dto.setMemberId(vo.getMemberId());
            return signService.mySign(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
