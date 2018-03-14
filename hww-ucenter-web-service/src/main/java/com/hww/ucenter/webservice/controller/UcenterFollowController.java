package com.hww.ucenter.webservice.controller;

import com.hww.base.util.BeanUtils;
import com.hww.ucenter.common.dto.HUCenterFollowDto;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.SaveConcernDto;
import com.hww.ucenter.common.entity.UCenterFollow;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.manager.FollowMng;
import com.hww.ucenter.common.util.R;
import com.hww.ucenter.common.util.ValidatorUtils;
import com.hww.ucenter.common.vo.FollowVo;
import com.hww.ucenter.common.vo.SaveConcernVo;
import com.hww.ucenter.webservice.service.ConcernService;
import com.hww.ucenter.webservice.service.UCenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ucenter/concern")
public class UcenterFollowController {

	@Autowired
	private UCenterMemberService UCenterMemberService;

	@Autowired
	private ConcernService concernService;

	@Autowired
	private FollowMng followMng;

	/**
	 * 我关注的人
	 */
	@RequestMapping(value = "myConcerns.do", method = { RequestMethod.POST })
	@ResponseBody
	public R myConcerns(FollowVo vo) {
		Map<String, String> map = ValidatorUtils.validateEntity(vo);
		if (!map.get("status").equals("200")) {
			return R.error(500, map.get("message"));
		}
		try {
			List<FollowVo> myConcern = concernService.myConcern(vo);
			return R.ok().put("data", myConcern);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(500, "我关注的人查询失败");
		}
	}

	/**
	 * 我的粉丝
	 */
	@RequestMapping(value = "concernsMy.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public R concernsMy(FollowVo vo) {

		Map<String, String> map = ValidatorUtils.validateEntity(vo);
		if (!map.get("status").equals("200")) {
			return R.error(500, map.get("message"));
		}
		try {
			List<FollowVo> concernMy = concernService.concernMy(vo);
			return R.ok().put("data", concernMy);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(500, "我的粉丝查询失败");
		}

	}

	/**
	 * 关注
	 */
	@RequestMapping(value = "saveConcern.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public R saveConcerns(SaveConcernVo vo) {

		Map<String, String> map = ValidatorUtils.validateEntity(vo);
		if (!map.get("status").equals("200")) {
			return R.error(500, map.get("message"));
		}
		if (vo.getMemberId() != null && vo.getMemberId().equals(vo.getTomemberId())) {
			return R.error(500, "不能关注自己");
		}
		try {
			SaveConcernDto dto = new SaveConcernDto();
			dto.setMemberId(vo.getMemberId());
			dto.setTomemberId(vo.getTomemberId());
			// 先查询是否关注
			UCenterFollow co = concernService.queryByMemberId(dto);
			if (co != null) {
				return R.error(500, "您已关注,请勿重复关注");
			}
			// 判断对方是否关注
			UCenterFollow ce = concernService.queryByToMemberId(dto);
			UCenterFollow sc;
			if (ce != null) {
				// 制成相互关注
				dto.setConcernType(2);
				// 保存关注信息
				sc = concernService.SaveConcern(dto);
				// 更新
				ce.setConcernType(2);
				concernService.update(ce);
			} else {
				dto.setConcernType(1);
				// 保存关注信息
				sc = concernService.SaveConcern(dto);
			}
			return R.ok().put("data", sc.getConcernType().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(500, "关注失败");
		}
	}

	/**
	 * 取消关注
	 */
	@RequestMapping(value = "removeConcern.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public R removeConcern(SaveConcernVo vo) {

		Map<String, String> map = ValidatorUtils.validateEntity(vo);
		if (!map.get("status").equals("200")) {
			return R.error(500, map.get("message"));
		}
		try {
			SaveConcernDto dto = new SaveConcernDto();
			dto.setMemberId(vo.getMemberId());
			dto.setTomemberId(vo.getTomemberId());
			// 先查询是否关注
			UCenterFollow co = concernService.queryByMemberId(dto);
			// 判断对方是否关注
			UCenterFollow ce = concernService.queryByToMemberId(dto);
			if (co != null) {
				concernService.delete(co);
				if (ce != null) {
					ce.setConcernType(1);
					concernService.update(ce);
				}
			}
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(500, "取消关注失败");
		}
	}

	/**
	 * sns调用 是否关注/关注的人/关注数/粉丝数
	 */
	@RequestMapping(value = "isConcern.do/{memberId}/{toMemberId}", method = { RequestMethod.POST })
	public HUCenterFollowDto isConcern(@PathVariable Long memberId, @PathVariable Long toMemberId) {
		HUCenterFollowDto dto = new HUCenterFollowDto();
		UCenterFollow fl = concernService.isConcern(memberId, toMemberId);
		if (fl == null) {
			dto.setConcernType(0);
			return dto;
		}
		try {
			BeanUtils.copyProperties(dto, fl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@RequestMapping(value = "concernUsersFeginApi.do/{memberId}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String concernUsersFeginApi(@PathVariable("memberId") Long memberId) {
		UCenterMember member = new UCenterMember();
		member.setMemberId(memberId);
		return UCenterMemberService.concernUser(member);
	}

	@RequestMapping(value = "concernUsersCountFeginApi.do/{memberId}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Integer concernUserCount(@PathVariable("memberId") Long memberId) {
		MyConcernDto dto = new MyConcernDto();
		dto.setMemberId(memberId);
		return UCenterMemberService.concernUserCount(dto);
	}

	@RequestMapping(value = "beConcernedCountFeginApi.do/{memberId}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Integer myConcernCount(@PathVariable("memberId") Long memberId) {
		MyConcernDto dto = new MyConcernDto();
		dto.setMemberId(memberId);
		return UCenterMemberService.concernMyCount(dto);
	}

	@RequestMapping(value = "listAllFollowMemberId.do")
	@ResponseBody
	public List<Long> listAllFollowMemberId(@RequestParam("memberId") Long memberId) {
		if (memberId == null)
			return null;
		return followMng.listFollowIdByMemberId(memberId);
	}
}
