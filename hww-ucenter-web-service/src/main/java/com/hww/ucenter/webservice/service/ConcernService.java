package com.hww.ucenter.webservice.service;

import com.hww.ucenter.common.dto.SaveConcernDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.vo.FollowVo;
import com.hww.ucenter.common.vo.IsFollowVo;

import java.util.List;

public interface ConcernService {

    List<FollowVo> myConcern(FollowVo vo);

    List<FollowVo> concernMy(FollowVo vo);

    UCenterFollow SaveConcern(SaveConcernDto dto);

    UCenterFollow queryByMemberId(SaveConcernDto dto);

    UCenterFollow queryByToMemberId(SaveConcernDto dto);

    UCenterFollow update(UCenterFollow en);

    Boolean delete(UCenterFollow en);

    UCenterFollow isConcern(Long memberId,Long  tomemberId);
}
