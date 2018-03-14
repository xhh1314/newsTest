package com.hww.ucenter.webservice.service.impl;

import com.hww.base.util.Constants;
import com.hww.base.util.CopyBean;
import com.hww.base.util.SnowFlake;
import com.hww.ucenter.common.dto.MySignDto;
import com.hww.ucenter.common.dto.MySignListByStreetDTO;
import com.hww.ucenter.common.dto.SignListCheckDTO;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.manager.SignMng;
import com.hww.ucenter.common.manager.UCenterMemberMng;
import com.hww.ucenter.common.util.LocationUtils;
import com.hww.ucenter.common.vo.MySignListByStreetVO;
import com.hww.ucenter.common.vo.SaveSignVo;
import com.hww.ucenter.common.vo.SignVo;
import com.hww.ucenter.webservice.service.SignService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("signService")
@Transactional
public class SignServiceImpl implements SignService {

    @Resource
    private SignMng signMng;

    @Resource
    private UCenterMemberMng UCenterMemberMng;

    @Override
    public List<SignVo> matchingSignList(List<SignVo> signs, SignListCheckDTO dto) {

        // 获取用户已签到数据
        List<UCenterSign> dbResults = signMng.signList(dto);

        if (dbResults == null || dbResults.size() == 0) {

            for (SignVo unSign : signs) {
                unSign.setIsSign(2);
                // 判断有多少人签到
                UCenterSign si = new UCenterSign();
                si.setAddress(unSign.getAddress());
                Integer cu = signMng.signCount(si);
                unSign.setCu(cu);
                // 计算距离
                double distance = LocationUtils.getDistance(Double.valueOf(dto.getLatitude()),
                        Double.valueOf(dto.getLongitude()), Double.valueOf(unSign.getLatitude()),
                        Double.valueOf(unSign.getLongitude()));
                unSign.setDistance(distance);
            }

            return signs;
        }

        // 匹配数据是否已经签到
        for (SignVo unSign : signs) {
            for (UCenterSign item : dbResults) {
                if (!StringUtils.isBlank(unSign.getAddress()) && !StringUtils.isBlank(item.getAddress())
                        && unSign.getAddress().equals(item.getAddress())) {
                    unSign.setIsSign(1);
                    // 判断有多少人签到
                    Integer cu = signMng.signCount(item);
                    // 计算距离
                    double distance = LocationUtils.getDistance(Double.valueOf(dto.getLatitude()),
                            Double.valueOf(dto.getLongitude()), Double.valueOf(unSign.getLatitude()),
                            Double.valueOf(unSign.getLongitude()));
                    unSign.setDistance(distance);
                    unSign.setCu(cu);
                    break;
                } else {

                    unSign.setIsSign(2);
                    // 判断有多少人签到
                    Integer cu = signMng.signCount(item);
                    // 计算距离
                    double distance = LocationUtils.getDistance(Double.valueOf(dto.getLatitude()),
                            Double.valueOf(dto.getLongitude()), Double.valueOf(unSign.getLatitude()),
                            Double.valueOf(unSign.getLongitude()));
                    unSign.setDistance(distance);
                    unSign.setCu(cu);
                }
            }
        }

        return signs;
    }

    @Override
    public void saveSign(SaveSignVo vo) {
        UCenterSign UCenterSign = new UCenterSign();

        CopyBean.Copy(vo, UCenterSign, false);

        Timestamp timestamp = new Timestamp(new Date().getTime());
        UCenterSign.setCreateTime(timestamp);
        UCenterSign.setLastModifyTime(timestamp);
        UCenterSign.setSiteId(Constants.siteId);
        // 保存签到
        signMng.save(UCenterSign);

        // 更新用户最新坐标
        UCenterMemberMng.updateUserLocation(UCenterSign);

    }

    @Override
    public List<MySignListByStreetVO> mySignListByStreet(MySignListByStreetDTO dto) {
        List<MySignListByStreetVO> list = signMng.mySignListByStreet(dto);
        if (list.size() > 0) {
            SimpleDateFormat format = new SimpleDateFormat("MM.dd HH:mm");
            for (MySignListByStreetVO item : list) {
                /*
                 * Timestamp createTime = signMng.findSignLastCreateTime(item);
                 * item.setCreateTime(format.format(new Date(createTime.getTime())));
                 */
                item.setCreateTime(signMng.findSignLastCreateTime(item));
            }
        }

        return list;
    }

    @Override
    public List<UCenterSign> mySignList(MySignDto dto) {

        return signMng.mySignList(dto);
    }

    @Override
    public List<UCenterSign> mySign(MySignDto dto) {

        return signMng.mySign(dto);
    }

    @Override
    public Integer cuOneSignCount(MySignDto dto) {

        return signMng.cuOneSignCount(dto);
    }

    @Override
    public boolean isTodaySigned(MySignDto mySignDto) {
        if (mySignDto.getMemberId() == null) {
            return false;
        }
        return signMng.isTodaySigned(mySignDto);
    }
    
    @Override
    public List<Map> loadInMinutes(Long memberId,int minutes,String address,Double lon,Double lat) {
        return signMng.loadInMinutes(memberId, minutes, address,lon, lat);
    }

}
