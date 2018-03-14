package com.hww.ucenter.common.manager.impl;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.framework.common.tool.JedisPoolUtil;
import com.hww.ucenter.common.dao.FollowDao;
import com.hww.ucenter.common.dto.SaveConcernDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.manager.FollowMng;
import com.hww.ucenter.common.vo.FollowVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("followMng")
public class FollowMngImpl extends BaseEntityMngImpl<Long, UCenterFollow, FollowDao> implements FollowMng {

	FollowDao followDao;

	public FollowDao getConcernDao() {
		return followDao;
	}

	private final int expireTime = 86400;

	@Autowired
	public void setConcernDao(FollowDao concernDao) {
		super.setEntityDao(concernDao);
		this.followDao = concernDao;
	}

	@Override
	public List<FollowVo> myConcern(FollowVo vo) {
		List<FollowVo> myConcern = followDao.myFollow(vo);
		return myConcern;
	}

	@Override
	public List<FollowVo> concernMy(FollowVo vo) {
		List<FollowVo> concernMy = followDao.followMe(vo);
		return concernMy;
	}

	@Override
	public UCenterFollow queryByMemberId(SaveConcernDto dto) {

		return followDao.queryByMemberId(dto);
	}

	@Override
	public UCenterFollow queryByToMemberId(SaveConcernDto dto) {

		return followDao.queryByToMemberId(dto);
	}

	@Override
	public UCenterFollow isConcern(Long memberId, Long tomemberId) {

		return followDao.isFollow(memberId, tomemberId);
	}

	@Override
	public UCenterFollow saveMyFollow(UCenterFollow follow) {
		UCenterFollow uCenterFollow = new UCenterFollow();
		BeanUtils.copyProperties(follow, uCenterFollow);
		save(uCenterFollow);
		Jedis conn = null;
		try {
			conn = JedisPoolUtil.getConnection();
			String key = "ucenter:follow:" + uCenterFollow.getMemberId();
			Set<String> stringSet = conn.smembers(key);
			if (stringSet == null || stringSet.size() == 0) {
				FollowVo followVo = new FollowVo();
				BeanUtils.copyProperties(follow, followVo);
				List<FollowVo> followVos = followDao.myFollow(followVo);
				if (followVos != null || followVos.size() > 0) {
					for (FollowVo vo : followVos) {
						conn.sadd(key, vo.getMemberId().toString());
					}
				}
			}

			conn.expire(key, expireTime);
			conn.sadd(key, uCenterFollow.getTomemberId().toString());
		} finally {
			if (conn != null)
				conn.close();
		}
		return uCenterFollow;
	}

	@Override
	public List<Long> listFollowIdByMemberId(Long memberId) {
		Jedis conn = null;
		Set<String> followId = null;
		String key = "ucenter:follow:" + memberId;
		try {
			conn = JedisPoolUtil.getConnection();
			followId = conn.smembers(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		if (followId != null && followId.size() > 0) {
			List<Long> ids = followId.stream().map(val -> Long.parseLong(val)).collect(Collectors.toList());
			return ids;
		}
		// 继续查询数据库
		List<Long> newIds = followDao.listAllFollowMemberId(memberId);
		if (newIds == null || newIds.size() == 0)
			return null;
		try {
			conn = JedisPoolUtil.getConnection();
			String[] strings = (String[]) newIds.stream().map(val -> val.toString()).collect(Collectors.toList())
					.toArray();
			conn.sadd(key, strings);
			conn.expire(key,expireTime);
		} finally {
			if (conn != null)
				conn.close();
		}
		return newIds;
	}

}
