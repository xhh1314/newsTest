package com.hww.ucenter.common.dao;

import com.hww.base.common.dao.IBaseEntityDao;
import com.hww.ucenter.common.dto.SaveConcernDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.vo.FollowVo;

import java.util.List;

public interface FollowDao extends IBaseEntityDao<Long, UCenterFollow> {

    List<FollowVo> myFollow(FollowVo vo);

    List<FollowVo> followMe(FollowVo vo);

    UCenterFollow queryByMemberId(SaveConcernDto dto);

    UCenterFollow queryByToMemberId(SaveConcernDto dto);

    UCenterFollow isFollow(Long memberId,Long  tomemberId);

    /**
     * 获取某人关注的所有人的Id
     * @param memberId
     * @return
     */
    List<Long> listAllFollowMemberId(Long memberId);
}
