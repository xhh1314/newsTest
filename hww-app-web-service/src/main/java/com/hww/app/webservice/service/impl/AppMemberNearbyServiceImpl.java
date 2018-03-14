package com.hww.app.webservice.service.impl;

import com.hww.app.common.entity.AppMemberNearby;
import com.hww.app.common.entity.AppMemberNearbyHis;
import com.hww.app.common.entity.AppMemberNearbyQuery;
import com.hww.app.common.manager.AppMemberNearbyHisMng;
import com.hww.app.common.manager.AppMemberNearbyMng;
import com.hww.app.common.manager.AppMemberNearbyQueryMng;
import com.hww.app.common.vo.AppMemberNearbyVo;
import com.hww.app.webservice.service.AppMemberNearbyService;
import com.hww.base.util.TimeUtils;
import com.hww.ucenter.api.UcenterFeignClient;
import com.hww.ucenter.common.dto.HUCenterFollowDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AppMemberNearbyServiceImpl implements AppMemberNearbyService {

    @Autowired
    private AppMemberNearbyMng nearbyMng;

    @Autowired
    private AppMemberNearbyQueryMng nearbyQueryMng;
    @Autowired
    private AppMemberNearbyHisMng nearbyQueryHisMng;
    @Autowired
    private AppMemberNearbyHisMng nearbyHisMng;
    
    @Autowired(required=false)
    private UcenterFeignClient userFeignClient;

    @Override
    public void joinInNearbyService(AppMemberNearbyVo nearbyVo) throws Exception {
    	 if(nearbyVo.getMemberId()==null) {
         	return;
         }
        AppMemberNearbyVo memberNearby = nearbyMng.queryMember(nearbyVo.getMemberId());
        AppMemberNearbyVo memberNearbyQuery = nearbyQueryMng.queryMember(nearbyVo.getMemberId());
        if (memberNearby != null) {
            nearbyMng.delete(memberNearby.getNearbyId());
        }
        if (memberNearbyQuery != null) {
            nearbyQueryMng.delete(memberNearbyQuery.getNearbyId());
        }
       
        UCenterMemberDto member = userFeignClient.findById(nearbyVo.getMemberId());
        if(member==null) {
        	return;
        }
        
        AppMemberNearby nearby = new AppMemberNearby();
        BeanUtils.copyProperties(nearby, nearbyVo);
        
        nearby.setNickName(member.getNickName());
        nearby.setAvatar(member.getAvatar());
        nearby.setSex(member.getSex());
        
        nearby.setAddress(nearbyVo.getAddress());
        nearby.setCountryId(nearbyVo.getCountryId());
        nearby.setCountryName(nearbyVo.getCountryName());
        nearby.setProvinceId(nearbyVo.getProvinceId());
        nearby.setProvinceName(nearbyVo.getProvinceName());
        nearby.setCityId(nearbyVo.getCityId());
        nearby.setCityName(nearbyVo.getCityName());
        
        nearby.setCreateTime(TimeUtils.getDateToTimestamp());
        nearby.setLastModifyTime(TimeUtils.getDateToTimestamp());
        nearby.setLocateTime(TimeUtils.getDateToTimestamp());
    
        nearbyMng.save(nearby);
        

        AppMemberNearbyQuery nearbyQuery = new AppMemberNearbyQuery();
        BeanUtils.copyProperties(nearbyQuery, nearbyVo);
        
        nearbyQuery.setNickName(member.getNickName());
        nearbyQuery.setAvatar(member.getAvatar());
        nearbyQuery.setSex(member.getSex());
        
        nearbyQuery.setCountryId(nearbyVo.getCountryId());
        nearbyQuery.setCountryName(nearbyVo.getCountryName());
        nearbyQuery.setProvinceId(nearbyVo.getProvinceId());
        nearbyQuery.setProvinceName(nearbyVo.getProvinceName());
        nearbyQuery.setCityId(nearbyVo.getCityId());
        nearbyQuery.setCityName(nearbyVo.getCityName());
        
        nearbyQuery.setAddress(nearbyVo.getAddress());
        nearbyQuery.setCreateTime(nearby.getCreateTime());
        nearbyQuery.setLastModifyTime(nearby.getLastModifyTime());
        nearbyQuery.setLocateTime(nearbyQuery.getLocateTime());
        nearbyQueryMng.save(nearbyQuery);
        //保存历史
        try {
        	AppMemberNearbyHis his=new AppMemberNearbyHis();
            BeanUtils.copyProperties(his, nearby);
            nearbyQueryHisMng.save(his);
        }catch (Exception e) {
		}
        
    }
    @Async
    private void saveHis(AppMemberNearbyHis his) {
    	nearbyHisMng.save(his);
    }

    @Override
    public void exitNearbyService(AppMemberNearbyVo nearbyVo) {
        try {
            AppMemberNearbyVo member = nearbyMng.queryMember(nearbyVo.getMemberId());
            AppMemberNearbyVo memberQuery = nearbyQueryMng.queryMember(nearbyVo.getMemberId());
            if (member != null) {
                nearbyMng.delete(member.getNearbyId());
            }
            if (memberQuery != null) {
                nearbyQueryMng.delete(memberQuery.getNearbyId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AppMemberNearbyVo> searchNearbyPeople(AppMemberNearbyVo vo) {
        List<AppMemberNearbyVo> list = nearbyQueryMng.nearPeoples(vo);
        
        if(vo.getMemberId()!=null) {
        	 for (AppMemberNearbyVo v : list) {
                 HUCenterFollowDto type = userFeignClient.isConcern(vo.getMemberId(), v.getMemberId());
                 v.setConcernType(type.getConcernType());
             }
        }
       
        return list;
    }
}
