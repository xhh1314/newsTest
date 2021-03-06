package com.hww.ucenter.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.ucenter.common.dao.SignDao;
import com.hww.ucenter.common.dto.MySignDto;
import com.hww.ucenter.common.dto.MySignListByStreetDTO;
import com.hww.ucenter.common.dto.SignListCheckDTO;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.vo.MySignListByStreetVO;

import java.util.List;
import java.util.Map;

public interface SignMng extends IBaseEntityMng<Long, UCenterSign, SignDao> {

    List<UCenterSign> signList(SignListCheckDTO dto);

    List<MySignListByStreetVO> mySignListByStreet(MySignListByStreetDTO dto);

    String findSignLastCreateTime(MySignListByStreetVO item);

    UCenterSign lastSign(UCenterSign UCenterSign);

    Integer signCount(UCenterSign UCenterSign);

    List<UCenterSign> mySignList(MySignDto dto);

    List<UCenterSign> mySign(MySignDto dto);

    Integer cuOneSignCount(MySignDto dto);

    boolean isTodaySigned(MySignDto mySignDto);
    
    
     List<Map> loadInMinutes(Long memberId,int minutes,String address,Double lon,Double lat) ;
}
