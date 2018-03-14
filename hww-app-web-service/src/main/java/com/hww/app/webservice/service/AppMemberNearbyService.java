package com.hww.app.webservice.service;

import com.hww.app.common.vo.AppMemberNearbyVo;

import java.util.List;

public interface AppMemberNearbyService {

    void joinInNearbyService(AppMemberNearbyVo nearbyVo) throws Exception;

    void exitNearbyService(AppMemberNearbyVo nearbyVo) throws Exception;

    List<AppMemberNearbyVo> searchNearbyPeople(AppMemberNearbyVo vo);
}
