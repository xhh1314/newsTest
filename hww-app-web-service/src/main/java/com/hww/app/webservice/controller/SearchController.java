package com.hww.app.webservice.controller;

import com.hww.app.common.dto.AppMemberNearbyDto;
import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.app.common.entity.AppSearchHistory;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.app.common.vo.SearchHistoryData;
import com.hww.app.common.vo.SearchHistoryVo;
import com.hww.app.webservice.service.AppMemberNearbyService;
import com.hww.app.webservice.service.SearchHistoryService;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.R;
import com.hww.base.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/search")
public class SearchController {

    private static final Log log = LogFactory.getLog(SearchController.class);


    @Autowired
    private SearchHistoryService searchHistoryService;

    @Autowired
    private AppMemberNearbyService nearbyService;


    @RequestMapping(value = "nearPeople.do", method = {RequestMethod.POST})
    public R nearPeople(AppMemberNearbyVo vo) {
        try {
            if (StringUtils.isEmpty(vo.getLatitude()) || StringUtils.isEmpty(vo.getLongitude())) {
                return R.error(1, "定位失败");
            }
//            if (vo.getMemberId() == null) {
//                return R.error(1, "用户不能为空");
//            }
            if(vo.getMemberId()!=null) {
            	nearbyService.joinInNearbyService(vo);
            }
            List<AppMemberNearbyVo> nearbyVos = nearbyService.searchNearbyPeople(vo);
            return R.ok().put("data", nearbyVos);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询附近的人失败");
        }
    }
    
    @RequestMapping(value = "nearPeopleFeginApi.do", method = {RequestMethod.POST})
    public R nearPeopleFeginApi(@RequestBody AppMemberNearbyDto dto) {
        try {
            if (StringUtils.isEmpty(dto.getLatitude()) || StringUtils.isEmpty(dto.getLongitude())) {
                return R.error(1, "定位失败");
            }
            if (dto.getMemberId() == null) {
                return R.error(1, "用户不能为空");
            }
            AppMemberNearbyVo vo=new AppMemberNearbyVo();
            vo.setMemberId(dto.getMemberId());
            vo.setLongitude(dto.getLongitude());
            vo.setLatitude(dto.getLatitude());
            vo.setPageNo(dto.getPageNo());
            List<AppMemberNearbyVo> nearbyVos = nearbyService.searchNearbyPeople(vo);
            return R.ok().put("data", nearbyVos);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "查询附近的人失败");
        }
    }

    @RequestMapping(value = "quitNearPeople.do", method = {RequestMethod.POST})
    public R quitNearPeople(AppMemberNearbyVo vo) {
        try {
            if (vo.getMemberId() == null) {
                return R.error(1, "用户不能为空");
            }
            nearbyService.exitNearbyService(vo);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "退出附近的人失败");
        }
    }
//=====================================================================================================================
    
    @RequestMapping(value = "history/list.do", method = { RequestMethod.POST})
    public R searchHistory(SearchHistoryVo word) {
        try {
            SearchHistoryData historyData = searchHistoryService.searchHistoryData(word);
            return R.ok().put("data", historyData);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "加载历史列表失败");
        }
    }

    @RequestMapping(value = "history/delete.do", method = {RequestMethod.POST})
    public R deleteHistory(SearchHistoryVo word) {
        try {
            boolean b = searchHistoryService.delSearchHistory(word.getSearchId());
            if (b) {
                SearchHistoryDto dto = new SearchHistoryDto();
                BeanUtils.copyProperties(dto, word);
                return R.ok().put("data", dto);
            }
            return R.error(1, "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "删除失败");
        }
    }


    /**
     * Feign接口
     *
     * @param word memberId 或 imei
     */
    @RequestMapping(value = "history/addSearchHisFeignApi.do", method = { RequestMethod.POST})
    public SearchHistoryDto addHistoryFeign(@RequestBody SearchHistoryDto word) {
        try {
            AppSearchHistory searchHistory = searchHistoryService.saveSearchHistory(word);
            SearchHistoryDto dto = new SearchHistoryDto();
            BeanUtils.copyProperties(dto, searchHistory);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchHistoryDto();
        }

    }
//  @RequestMapping(value = "history/add.do", method = {RequestMethod.GET, RequestMethod.POST})
//  public R addHistory(SearchHistoryVo word) {
//      try {
//          AppSearchHistory searchHistory = searchHistoryService.saveSearchHistory(word);
//          SearchHistoryDto dto = new SearchHistoryDto();
//          BeanUtils.copyProperties(dto, searchHistory);
//          return R.ok().put("data", dto);
//      } catch (Exception e) {
//          e.printStackTrace();
//          return R.error();
//      }
//  }

}
