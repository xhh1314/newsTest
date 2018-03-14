package com.hww.ucenter.webservice.service;

import com.hww.ucenter.common.dto.MySignDto;
import com.hww.ucenter.common.dto.MySignListByStreetDTO;
import com.hww.ucenter.common.dto.SignListCheckDTO;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.vo.MySignListByStreetVO;
import com.hww.ucenter.common.vo.SaveSignVo;
import com.hww.ucenter.common.vo.SignVo;

import java.util.List;
import java.util.Map;

public interface SignService {

    List<SignVo> matchingSignList(List<SignVo> signs, SignListCheckDTO dto);

    void saveSign(SaveSignVo vo);

    List<MySignListByStreetVO> mySignListByStreet(MySignListByStreetDTO dto);

    List<UCenterSign> mySignList(MySignDto dto);

    List<UCenterSign> mySign(MySignDto dto);

    Integer cuOneSignCount(MySignDto dto);

    boolean isTodaySigned(MySignDto dto);

	List<Map> loadInMinutes(Long memberId, int minutes,String address, Double lon, Double lat);
}
