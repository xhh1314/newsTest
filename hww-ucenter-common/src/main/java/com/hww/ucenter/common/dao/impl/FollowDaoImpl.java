package com.hww.ucenter.common.dao.impl;

import com.hww.base.common.util.Finder;
import com.hww.ucenter.common.dao.FollowDao;
import com.hww.ucenter.common.dto.SaveConcernDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.vo.FollowVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("followDao")
public class FollowDaoImpl extends LocalEntityDaoImpl<Long, UCenterFollow> implements FollowDao {

    @Override
    public List<FollowVo> myFollow(FollowVo vo) {


        Finder f = Finder.create(
                "SELECT u.now_lon as nowLon,u.now_lat as nowLat,u.sex,u.nick_name as nickName,u.avatar,u.address,c.create_time as createTime,"
                + " c.concern_type as concernType,c.tomember_id as tomemberId "
                		
                + " FROM ucenter_follow c, ucenter_member u "
                + " where c.tomember_id = u.member_id and c.member_id =:memberId order by c.create_time desc   "
                + " limit :pageStart,:pageSize ");
        f.setParam("memberId", vo.getMemberId());
        int pageNo=vo.getPageNo()==null?1:vo.getPageNo();
        int pageSize=vo.getPageSize()==null?10:vo.getPageSize();
        
        f.setParam("pageStart", (pageNo - 1) * pageSize);
        f.setParam("pageSize", pageSize);
        List<FollowVo> list = (List<FollowVo>) findJoin(f, FollowVo.class);

        return list;
    }

    @Override
    public List<FollowVo> followMe(FollowVo vo) {

        Finder f = Finder.create(
                "SELECT u.now_lon as nowLon,u.now_lat as nowLat,u.sex,u.nick_name as nickName,u.avatar,u.address,c.create_time as createTime,"
                + " c.concern_type as concernType,c.member_id as tomemberId "
                + " FROM ucenter_follow c,ucenter_member u "
                + " where c.member_id = u.member_id and c.tomember_id =:memberId order by c.create_time desc  "
                + " limit :pageStart,:pageSize");
        f.setParam("memberId", vo.getMemberId());
        int pageNo=vo.getPageNo()==null?1:vo.getPageNo();
        int pageSize=vo.getPageSize()==null?10:vo.getPageSize();
        
        f.setParam("pageStart", (pageNo - 1) * pageSize);
        f.setParam("pageSize", pageSize);
        List<FollowVo> list = (List<FollowVo>) findJoin(f, FollowVo.class);

        return list;
    }

    @Override
    public UCenterFollow queryByMemberId(SaveConcernDto dto) {

        Finder f = Finder.create("from UCenterFollow bean");
        f.append(" where bean.memberId = :memberId");
        f.append(" and bean.tomemberId = :tomemberId");
        f.setParam("memberId", dto.getMemberId());
        f.setParam("tomemberId", dto.getTomemberId());
        List<UCenterFollow> UCenterFollows = (List<UCenterFollow>) find(f);
        if (UCenterFollows != null && UCenterFollows.size() > 0) {
            return UCenterFollows.get(0);
        }
        return null;
    }

    @Override
    public UCenterFollow queryByToMemberId(SaveConcernDto dto) {

        Finder f = Finder.create("from UCenterFollow bean");
        f.append(" where bean.memberId = :memberId");
        f.append(" and bean.tomemberId = :tomemberId");
        f.setParam("memberId", dto.getTomemberId());
        f.setParam("tomemberId", dto.getMemberId());
        List<UCenterFollow> UCenterFollows = (List<UCenterFollow>) find(f);
        if (UCenterFollows != null && UCenterFollows.size() > 0) {
            return UCenterFollows.get(0);
        }
        return null;
    }

    @Override
    public UCenterFollow isFollow(Long memberId, Long tomemberId) {
        try {
            Finder f = Finder.create("from UCenterFollow bean");
            f.append(" where bean.memberId = :memberId");
            f.append(" and bean.tomemberId = :tomemberId");
            f.setParam("memberId", memberId);
            f.setParam("tomemberId", tomemberId);
            List<UCenterFollow> UCenterFollows = (List<UCenterFollow>) find(f);
            if (UCenterFollows != null && UCenterFollows.size() > 0) {
                return UCenterFollows.get(0);
            }
            UCenterFollow follow = new UCenterFollow();
            follow.setConcernType(0);
            return follow;
        } catch (Exception e) {
            e.printStackTrace();
            UCenterFollow follow = new UCenterFollow();
            follow.setConcernType(0);
            return follow;
        }

    }

    @Override
    public List<Long> listAllFollowMemberId(Long memberId) {
        if (memberId==null)
            return null;
        Finder finder=Finder.create("select tomemberId from UcenterFollow where memberId =:memberId ");
        finder.setParam("memberId",memberId);
        return (List<Long>) find(finder);
    }


}
