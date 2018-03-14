package com.hww.ucenter.common.manager;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.ucenter.common.dao.FollowDao;
import com.hww.ucenter.common.dto.SaveConcernDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.vo.FollowVo;
import com.hww.ucenter.common.vo.IsFollowVo;

import java.util.List;

public interface FollowMng extends IBaseEntityMng<Long, UCenterFollow, FollowDao> {

    List<FollowVo> myConcern(FollowVo vo);

    List<FollowVo> concernMy(FollowVo vo);

    UCenterFollow queryByMemberId(SaveConcernDto dto);

    UCenterFollow queryByToMemberId(SaveConcernDto dto);

    UCenterFollow isConcern(Long memberId,Long  tomemberId);

    UCenterFollow saveMyFollow(UCenterFollow follow);

    List<Long> listFollowIdByMemberId(Long memberId);
}
