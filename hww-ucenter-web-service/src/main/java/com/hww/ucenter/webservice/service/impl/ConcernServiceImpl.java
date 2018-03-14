package com.hww.ucenter.webservice.service.impl;

import com.hww.ucenter.common.dto.SaveConcernDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.manager.FollowMng;
import com.hww.ucenter.common.manager.SignMng;
import com.hww.ucenter.common.manager.UCenterMemberMng;
import com.hww.ucenter.common.util.LocationUtils;
import com.hww.ucenter.common.vo.FollowVo;
import com.hww.ucenter.webservice.service.ConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("concernService")
@Transactional
public class ConcernServiceImpl implements ConcernService {

    @Autowired
    FollowMng followMng;

    @Autowired
    UCenterMemberMng UCenterMemberMng;

    @Autowired
    SignMng singMng;

    @Override
    public List<FollowVo> myConcern(FollowVo vo) {
        List<FollowVo> concerns = followMng.myConcern(vo);
        // 根据用户id,查询用户详情
        UCenterMember user = UCenterMemberMng.find(vo.getMemberId());
        // 查询关注用户最后签到地址

        if (concerns != null && concerns.size() > 0) {

            // 循环处理距离
            for (FollowVo co : concerns) {
                Double distance = null;
                try {
                    distance = LocationUtils.getDistance(Double.valueOf(user.getNowLat()),
                            Double.valueOf(user.getNowLon()), Double.valueOf(co.getNowLat()),
                            Double.valueOf(co.getNowLon()));
                    co.setDistance(distance);
                } catch (Exception e) {
                    co.setDistance(distance);
                }
//				if (co.getAvatar() != null) {
//
//					String url = fileFeignClient.getUrlByFileId(co.getAvatar());
//					co.setAvatar(url);
//				}
//                 sign sg = new Sign();
//                 sg.setMemberId(co.getTomemberId());
//                 Sign lastSign = singMng.lastSign(sg);
//                 if(lastSign!=null) {
//                 co.setAddress(lastSign.getAddress());
//                 }
            }
        }
        return concerns;
    }

    @Override
    public List<FollowVo> concernMy(FollowVo vo) {
        List<FollowVo> concerns = followMng.concernMy(vo);
        // 根据用户id,查询用户详情
        UCenterMember user = UCenterMemberMng.find(vo.getMemberId());
        if (concerns != null && concerns.size() > 0) {

            // 循环处理距离
            for (FollowVo co : concerns) {

               try {
            	   double distance = LocationUtils.getDistance(Double.valueOf(user.getNowLat()),
                           Double.valueOf(user.getNowLon()), Double.valueOf(co.getNowLat()),
                           Double.valueOf(co.getNowLon()));
                   co.setDistance(distance);
               }catch (Exception e) {
            	   co.setDistance(null);
			  }

//				if (co.getAvatar() != null) {
//
//					String url = fileFeignClient.getUrlByFileId(co.getAvatar());
//					co.setAvatar(url);
//				}
                // Sign sg = new Sign();
                // sg.setMemberId(co.getTomemberId());
                // Sign lastSign = singMng.lastSign(sg);
                // if(lastSign!=null) {
                // co.setAddress(lastSign.getAddress());
                // }
            }
        }
        return concerns;
    }
   @CacheEvict(value = "ucenter_isConcern",key="'isConcern_'+#dto.memberId+'_'+#dto.tomemberId")
    @Override
    public UCenterFollow SaveConcern(SaveConcernDto dto) {
        UCenterFollow en = new UCenterFollow();
        en.setMemberId(dto.getMemberId());
        en.setTomemberId(dto.getTomemberId());
        en.setConcernType(dto.getConcernType());
        Timestamp timestamp = new Timestamp(new Date().getTime());
        en.setCreateTime(timestamp);
        en.setSiteId(1);
        return followMng.saveMyFollow(en);
    }

    @Override
    public UCenterFollow queryByMemberId(SaveConcernDto dto) {

        return followMng.queryByMemberId(dto);
    }

    @Override
    public UCenterFollow queryByToMemberId(SaveConcernDto dto) {
        return followMng.queryByToMemberId(dto);
    }
    @CacheEvict(value = "ucenter_isConcern",key="'isConcern_'+#en.memberId+'_'+#en.tomemberId")
    @Override
    public UCenterFollow update(UCenterFollow en) {

        return followMng.update(en);
    }
    @CacheEvict(value = "ucenter_isConcern",key="'isConcern_'+#en.memberId+'_'+#en.tomemberId")
    @Override
    public Boolean delete(UCenterFollow en) {

        return followMng.delete(en);
    }
    
    @Scheduled(fixedDelay = 1000*60*10)
   	@CacheEvict(value = "ucenter_isConcern",allEntries=true)
   	   public void isConcern_delete_from_cache() {
   	   }
    
   @Cacheable(value = "ucenter_isConcern",key="'isConcern_'+#memberId+'_'+#tomemberId")
    @Override
    public UCenterFollow isConcern(Long memberId, Long tomemberId) {

        return followMng.isConcern(memberId, tomemberId);
    }
}
