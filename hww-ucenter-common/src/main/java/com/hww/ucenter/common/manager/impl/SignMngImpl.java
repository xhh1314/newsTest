package com.hww.ucenter.common.manager.impl;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.ucenter.common.dao.SignDao;
import com.hww.ucenter.common.dto.MySignDto;
import com.hww.ucenter.common.dto.MySignListByStreetDTO;
import com.hww.ucenter.common.dto.SignListCheckDTO;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.manager.SignMng;
import com.hww.ucenter.common.vo.MySignListByStreetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("signMng")
public class SignMngImpl extends BaseEntityMngImpl<Long, UCenterSign, SignDao> implements SignMng {

    private SignDao signDao;

    @Autowired
    public void setSignDao(SignDao signDao) {
        super.setEntityDao(signDao);
        this.signDao = signDao;
    }

    @Override
    public List<UCenterSign> signList(SignListCheckDTO dto) {
        // TODO Auto-generated method stub
        return signDao.signList(dto);
    }

    @Override
    public List<MySignListByStreetVO> mySignListByStreet(MySignListByStreetDTO dto) {
        // TODO Auto-generated method stub
        return signDao.mySignListByStreet(dto);
    }

    @Override
    public String findSignLastCreateTime(MySignListByStreetVO item) {
        // TODO Auto-generated method stub
        return signDao.findSignLastCreateTime(item);
    }

    @Override
    public UCenterSign lastSign(UCenterSign UCenterSign) {

        return signDao.lastSign(UCenterSign);
    }

    @Override
    public Integer signCount(UCenterSign UCenterSign) {
        // TODO Auto-generated method stub
        return signDao.signCount(UCenterSign);
    }

    @Override
    public List<UCenterSign> mySignList(MySignDto sign) {

        return signDao.mySignList(sign);
    }

    @Override
    public List<UCenterSign> mySign(MySignDto dto) {

        return signDao.mySign(dto);
    }

    @Override
    public Integer cuOneSignCount(MySignDto dto) {

        return signDao.cuOneSignCount(dto);
    }

    @Override
    public boolean isTodaySigned(MySignDto mySignDto) {
        return signDao.isTodaySigned(mySignDto);
    }

    @Override
    public List<Map> loadInMinutes(Long memberId,int minutes,String address,Double lon,Double lat) {
        return signDao.selectInMinits(memberId, minutes, address,lon, lat);
    }
}
