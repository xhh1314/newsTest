package com.hww.sns.webservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hww.app.api.AppFeignClient;
import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.app.common.dto.SearchHistoryDto;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.R;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.HSearchDto;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.common.dto.HBaseSnsQueryDto;
import com.hww.sns.common.dto.HBaseSnsQueryFeginApiDto;
import com.hww.sns.common.dto.HSnsTopicCreateDto;
import com.hww.sns.common.dto.HSnsTopicUpParamDto;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.ValidatorUtils;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.sns.webservice.service.SnsTopicService;
import com.hww.sns.webservice.service.SnstopicEsSyncFailService;
import com.hww.ucenter.api.UcenterFeignClient;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sns/topic")
public class SnsTopicController {

	@Autowired
	SnsTopicService snsTopicService;
	@Autowired
	SnsTopicMng snsTopicMng;

	@Autowired
	AppFeignClient appFeignClient;
	@Autowired
	UcenterFeignClient userFeignClient;
	@Autowired
	ElsFeignClient elsFeignClient;
	@Autowired
	SnstopicEsSyncFailService esSyncFailService;

	private static final boolean isFromES = true;

	/**
	 * 我的动态---我发的新鲜事等
	 *
	 */
	@RequestMapping(value = "myTopics.do", method = RequestMethod.POST)
	@ResponseBody
	public R myTopics(HBaseSnsQueryDto snsQueryDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(snsQueryDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			snsQueryDto.setAuthorMemberId(snsQueryDto.getMemberId());
			List<SnsTopicVo> myTopics = snsTopicService.loadMyTopicList(snsQueryDto);
			return R.ok().put("data", myTopics);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "查询失败");
		}
	}

	/**
	 * 用户的新鲜事
	 *
	 */
	@RequestMapping(value = "memberTopics.do", method = RequestMethod.POST)
	@ResponseBody
	public R memberTopics(HBaseSnsQueryDto snsQueryDto) {

		Map<String, String> map = ValidatorUtils.validateEntity(snsQueryDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			Map<String, List<SnsTopicVo>> memberTopics = snsTopicService.loadMemberTopicList(snsQueryDto);
			return R.ok().put("data", memberTopics);
		} catch (Exception e) {

			e.printStackTrace();
			return R.error(1, "查询失败");
		}
	}

	/**
	 * 附近新鲜事
	 */
	@RequestMapping(value = "nearTopics.do", method = RequestMethod.POST)
	@ResponseBody
	public R nearTopics(HBaseSnsQueryDto snsQueryDto) {

		Map<String, String> map = ValidatorUtils.validateEntity(snsQueryDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		if (snsQueryDto == null || snsQueryDto.getLongitude() == null || snsQueryDto.getLatitude() == null) {
			return R.ok().put("data", null);
		}

		try {
			if (!isFromES) {
				// 新鲜事
				// List<SnsTopicVo> nearTopics =
				// snsTopicService.loadNearTopics(snsQueryDto);
				// return R.ok().put("data", nearTopics);
				return R.ok().put("data", null);
			} else {
				HSearchDto searchDto = new HSearchDto().setSearchType(4).setOrderBy(1)
						.setLatitude(snsQueryDto.getLatitude()).setLongitude(snsQueryDto.getLongitude())
						.setPageNo(snsQueryDto.getPageNo()).setPageSize(snsQueryDto.getPageSize()).setMemberId(snsQueryDto.getMemberId());
				List<Long> tipicIds = elsFeignClient.searchNearTopicIdsFeginApi(searchDto);
				if (tipicIds == null) {
					return R.ok().put("data", null);
				}
				List<SnsTopicVo> nearTopics = snsTopicService.loadTopicListByIdsShowAllStatus(tipicIds, snsQueryDto);

				return R.ok().put("data", nearTopics);
			}

		} catch (Exception e) {

			e.printStackTrace();
			return R.error(1, "附近新鲜事查询失败");
		}
	}

	@RequestMapping(value = "loadConcernTopics.do", method = RequestMethod.POST)
	@ResponseBody
	public R loadConcernTopics(HBaseSnsQueryDto snsQueryDto) {

		Map<String, String> map = ValidatorUtils.validateEntity(snsQueryDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			if (snsQueryDto.getMemberId() == null) {
				return R.ok();
			}
			List<SnsTopicVo> concernTopics = snsTopicService.loadConcernUserTopics(snsQueryDto);
			int concernCount = 0;
			if (snsQueryDto.getMemberId() != null) {
				concernCount = userFeignClient.concernUserCount(snsQueryDto.getMemberId());
			}

			return R.ok().put("data", concernTopics).put("concernCount", concernCount);
		} catch (Exception e) {

			e.printStackTrace();
			return R.error(1, "关注新鲜事查询失败");
		}
	}

	/**
	 * 发表主题(新鲜事-爆料)
	 *
	 * @author lyb
	 * @email 674142624@qq.com
	 * @date 2017年11月10日 下午6:14:03
	 * @return
	 * @version v0.1
	 */
	@RequestMapping(value = "publishTopic.do", method = RequestMethod.POST)
	@ResponseBody
	public R publishTopic(HSnsTopicCreateDto snsTopicDto) {

		// Map<String, String> map = ValidatorUtils.validateEntity(snsTopicDto);
		// if (!map.get("status").equals("200")) {
		// return R.error(1, map.get("message"));
		// }
		if (snsTopicDto.getMemberId() == null) {
			return R.error(1, "请登录");
		}
		if (snsTopicDto.getAnonymous() == null) {
			return R.error(1, "请请选择是否匿名");
		}
		if (!(snsTopicDto.getAnonymous().intValue() == 1 || snsTopicDto.getAnonymous() == 2)) {
			return R.error(1, "是否匿名参数错误");
		}
		if (snsTopicDto.getPubli() == null) {
			return R.error(1, "请请选择是否公开");
		}
		// 1公开2不公开
		if (!(snsTopicDto.getPubli().intValue() == 1 || snsTopicDto.getPubli() == 2)) {
			return R.error(1, "是否公开参数错误");
		}
		// if(!StringUtils.hasText(snsTopicDto.getContent())) {
		// return R.error(1, "请输入新鲜事内容");
		// }
		// if(snsTopicDto.getContent().length()<5) {
		// return R.error(1, "请至少输入5个字符");
		// }

		if (!StringUtils.hasText(snsTopicDto.getContent()) && !StringUtils.hasText(snsTopicDto.getFileId())) {
			return R.error(1, "新鲜事内容不能为空");
		}
		if (snsTopicDto.getContent() != null && snsTopicDto.getContent().length() > 1000) {
			return R.error(1, "内容超长");
		}
		try {
			SnsTopic snsTopic = snsTopicService.publishTopic(snsTopicDto);
			return R.ok().put("data", snsTopic);
		} catch (HServiceLogicException e) {
			return R.error(1, e.getMessage());
		} catch (Exception e) {

			e.printStackTrace();
			return R.error(1, "发表失败");
		}
	}

	/**
	 * 新鲜事详情
	 *
	 * @author lyb
	 * @email 674142624@qq.com
	 * @date 2017年11月11日 下午3:17:11
	 * @return
	 * @version v0.1
	 */
	@RequestMapping(value = "detail.do", method = RequestMethod.POST)
	@ResponseBody
	public R detail(HBaseSnsQueryDto detailParamDto) {

		Map<String, String> map = ValidatorUtils.validateEntity(detailParamDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			SnsTopicVo topicVo = snsTopicService.detail(detailParamDto);
			return R.ok().put("data", topicVo);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "查询失败");
		}

	}

	/**
	 * 新鲜事点赞
	 *
	 * @return
	 * @author lyb
	 * @email 674142624@qq.com
	 * @date 2017年11月17日 下午4:35:32
	 * @version v0.1
	 */
	@RequestMapping(value = "upTopic.do", method = RequestMethod.POST)
	@ResponseBody
	public R upTopic(HSnsTopicUpParamDto topicUpParamDto) {

		Map<String, String> map = ValidatorUtils.validateEntity(topicUpParamDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			String isExists = appFeignClient
					.behaviorExist(new AppBehaviorDto(topicUpParamDto.getMemberId(), topicUpParamDto.getTopicId(),
							AppBehaviorDto.BevType.b1_dz).setPlateType(AppBehaviorDto.PlatType.b1_topic));
			if ("y".equals(isExists)) {
				return R.error("已经点过赞，请不要重复操作");
			}
			AppBehaviorDto behaviorDto = new AppBehaviorDto(topicUpParamDto.getMemberId(), topicUpParamDto.getTopicId(),
					AppBehaviorDto.BevType.b1_dz, AppBehaviorDto.BevValue.b1_ok, AppBehaviorDto.PlatType.b1_topic);
			return appFeignClient.createBehavior(behaviorDto);
		} catch (HServiceLogicException e) {
			return R.error(1, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "点赞失败");
		}

	}

	/**
	 * 取消点赞
	 *
	 * @return
	 * @author lyb
	 * @email 674142624@qq.com
	 * @date 2017年11月22日 上午11:22:46
	 * @version v0.1
	 */
	@RequestMapping(value = "cancelUpTopic.do", method = RequestMethod.POST)
	@ResponseBody
	public R cancelUpTopic(HSnsTopicUpParamDto topicUpParamDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(topicUpParamDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			// 0 详情查看， 1点赞 ，2 收藏，3评论，4爆料，5 不感兴趣，6内容太水，7看过了
			// 0 新闻 1 帖子/新鲜事 2 评论
			AppBehaviorDto behaviorDto = new AppBehaviorDto(topicUpParamDto.getMemberId(), topicUpParamDto.getTopicId(),
					AppBehaviorDto.BevType.b1_dz, AppBehaviorDto.BevValue.b0_cancel, AppBehaviorDto.PlatType.b1_topic);
			return appFeignClient.createBehavior(behaviorDto);
		} catch (HServiceLogicException e) {
			return R.error(1, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "取消点赞失败");
		}
	}

	/**
	 * 删除动态
	 *
	 * @author lyb
	 * @email 674142624@qq.com
	 * @date 2017年11月21日 下午5:18:35
	 * @return 
	 * @version v0.1
	 */
	@RequestMapping(value = "deleteTopic.do", method = RequestMethod.POST)
	@ResponseBody
	public R delteTopic(HSnsTopicUpParamDto topicUpParamDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(topicUpParamDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			snsTopicService.deleteTopic(topicUpParamDto.getMemberId(), topicUpParamDto.getTopicId());
			return R.ok();
		} catch (HServiceLogicException e) {
			return R.error(1, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "删除失败");
		}
	}

	// 1综合 2爆料 3视频 4 新鲜事 5 用户 6地点
	@Async
	public void saveSearchHis(SearchHistoryDto historyDto) {
		appFeignClient.addSearchHistory(historyDto);
	}

	@ApiOperation(value = "搜索---新鲜事", notes = "")
	@RequestMapping(value = "searchTopics.do", method = RequestMethod.POST)
	@ResponseBody
	public R searchTopics(HSearchDto searchDto) {

		Map<String, String> map = ValidatorUtils.validateEntity(searchDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			// 1综合 2爆料 3视频 4 新鲜事 5 用户 6地点
			List<Long> tipicIds = elsFeignClient.searchIdsIncontentFeginApi(searchDto.setSearchType(4));
			if (tipicIds == null) {
				return R.ok().put("data", null);
			}
			HBaseSnsQueryDto snsQueryDto = new HBaseSnsQueryDto().setMemberId(searchDto.getMemberId())
					.setLatitude(searchDto.getLatitude()).setLongitude(searchDto.getLongitude());
			List<SnsTopicVo> searchTopics = snsTopicService.loadTopicListByIds(tipicIds, snsQueryDto);

			try {
				saveSearchHis(SearchHistoryDto.newForCreate(searchDto.getMemberId(), searchDto.getImei(),
						searchDto.getKeywords(), 4));
			} catch (Exception e) {
			}

			return R.ok().put("data", searchTopics);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "新鲜事查询失败");
		}
	}

	@ApiOperation(value = "搜索---爆料", notes = "")
	@RequestMapping(value = "searchNewsTopics.do", method = RequestMethod.POST)
	@ResponseBody
	public R searchNewsTopics(HSearchDto searchDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(searchDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			// 1综合 2爆料 3视频 4 新鲜事 5 用户 6地点
			List<Long> tipicIds = elsFeignClient.searchIdsIncontentFeginApi(searchDto.setSearchType(2));
			if (tipicIds == null) {
				return R.ok().put("data", null);
			}
			HBaseSnsQueryDto snsQueryDto = new HBaseSnsQueryDto().setMemberId(searchDto.getMemberId())
					.setLatitude(searchDto.getLatitude()).setLongitude(searchDto.getLongitude());
			List<SnsTopicVo> searchTopics = snsTopicService.loadTopicListByIds(tipicIds, snsQueryDto);
			try {
				saveSearchHis(SearchHistoryDto.newForCreate(searchDto.getMemberId(), searchDto.getImei(),
						searchDto.getKeywords(), 2));
			} catch (Exception e) {
			}

			return R.ok().put("data", searchTopics);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "爆料查询失败");
		}
	}
	// =========================Fegin==API=======start==================================================================

	@RequestMapping(value = "loadRecommTopicsFeginApi.do", method = RequestMethod.POST)
	public List<SnsTopicVo> loadRecommTopicsFeginApi(@RequestBody HBaseSnsQueryDto snsQueryDto) {
		List<SnsTopicVo> snsTopicVoList = snsTopicService.loadRecommTopics(snsQueryDto);
		return snsTopicVoList;
	}

	@RequestMapping(value = "loadTopicByIdsFeginApi.do", method = RequestMethod.POST)
	public List<SnsTopicVo> loadTopicByIdsFeginApi(@RequestBody HBaseSnsQueryFeginApiDto queryFeginApiDto) {
		HBaseSnsQueryDto snsQueryDto = new HBaseSnsQueryDto();
		try {
			BeanUtils.copyProperties(snsQueryDto, queryFeginApiDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<SnsTopicVo> topicList = snsTopicService.loadTopicListByIds(queryFeginApiDto.getTopicIds(), snsQueryDto);
		return topicList;
	}

	@RequestMapping(value = "myTopicsFeginApi.do", method = RequestMethod.POST)
	public List<SnsTopicVo> myTopicsFeginApi(@RequestBody HBaseSnsQueryDto snsQueryDto) {
		snsQueryDto.setAuthorMemberId(snsQueryDto.getMemberId());
		List<SnsTopicVo> myTopics = snsTopicService.loadMyTopicList(snsQueryDto);
		return myTopics;
	}

	@RequestMapping(value = "loadTopicByIdFeginApi.do", method = RequestMethod.POST)
	public SnsTopicVo loadTopicByIdFeginApi(@RequestParam("topicId") Long topicId) {
		SnsTopicVo snsTopicVo = snsTopicService.loadSnsTopicDetail(topicId);
		return snsTopicVo;
	}

	/**
	 * 当前新闻爆料总数
	 * @param newId
	 * @return
	 */
	@RequestMapping(value = "topicCountsByNewIdFeginApi.do/{newId}", method = RequestMethod.POST)
	public Integer topicCountsByNewIdFeginApi(@PathVariable("newId") Long newId) {
		return snsTopicService.queryTopicCountsByNewId(newId);
	}

	/**
	 * 新闻爆料列表
	 * @param newId
	 * @return
	 */
	@RequestMapping(value = "newTopicsFeginApi.do", method = RequestMethod.POST)
	public List<SnsTopicVo> newTopicsFeginApi(@RequestBody HBaseSnsQueryDto paramDto) {
		List<SnsTopicVo> snsTopicList = snsTopicService.loadTopicListByNewsId(paramDto);
		return snsTopicList;
	}

	/**
	 * ucenter调用
	 *
	 */
	@RequestMapping(value = "loadUserTopicCountFeginApi.do/{memberId}/{topicType}", method = RequestMethod.POST)
	public Integer loadUserTopicCountFeginApi(@PathVariable Long memberId, @PathVariable Integer topicType) {
		Integer cu = snsTopicService.loadUserTopicCount(memberId, null);
		return cu;
	}

	/**
	 * 舉報接口
	 * 
	 
	
	@RequestMapping(value = "reportSns.do", method = RequestMethod.POST)
	@ResponseBody
	public String reportTopics(@RequestParam("topicId") Long topicId,@RequestParam("reportContent") String reportContent) {
		String result="举报成功";
		if(reportContent!=null && reportContent!="") {
			result="举报成功";
		}else {
			result="举报失败";
		}	
		return result;
	}
	*/

	// /**
	// * 附近新鲜事
	// *
	// */
	// @RequestMapping(value = "nearTopicsFeginApi.do", method =
	// RequestMethod.POST)
	// public List<SnsTopicVo> nearTopicsFeginApi(@RequestBody HBaseSnsQueryDto
	// snsQueryDto) {
	// List<SnsTopicVo> nearTopics =Lists.newArrayList();
	// if(!isFromES) {
	// //新鲜事
	// nearTopics = snsTopicService.loadNearTopics(snsQueryDto);
	// return nearTopics;
	// }else {
	// HSearchDto searchDto=new
	// HSearchDto().setSearchType(4).setOrderBy(2).setLatitude(snsQueryDto.getLatitude()).setLongitude(snsQueryDto.getLongitude());
	// List<Long> tipicIds=elsFeignClient.searchIdsIncontentFeginApi(searchDto);
	// if(tipicIds==null) {
	// return nearTopics;
	// }
	// nearTopics = snsTopicService.loadTopicListByIds(tipicIds,snsQueryDto);
	// }
	// return nearTopics;
	// }

	// /**
	// * 新闻爆料操作
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "publishNewsTopicFeginApi.do", method =
	// RequestMethod.POST)
	// public SnsTopic publishNewsTopicFeginApi(@RequestBody HSnsTopicCreateDto
	// paramDto) {
	// try {
	// return snsTopicService.publishTopic(paramDto);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	// /**
	// * 爆料回复
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "newTopicReply.do")
	// @ResponseBody
	// public R newTopicReply(SnsTopicVo vo) {
	// if (vo == null) {
	// return R.error(1, "参数有误");
	// }
	// SnsTopic snsTopic = snsTopicService.newTopicReply(vo);
	// return R.ok().put("data", snsTopic);
	// }
	//
	// /**
	// * 爆料回复列表
	// * @param topicId
	// * @return
	// */
	// @RequestMapping(value = "newTopicReplyList.do")
	// @ResponseBody
	// public R newTopicReplyList(Long newId, Long topicId) {
	// if (topicId == null || topicId <= 0 || newId == null || newId <= 0) {
	// return R.error(1, "参数有误");
	// }
	// List<SnsTopic> snsTopicList = snsTopicService.newTopicReplyList(newId,
	// topicId);
	// return R.ok().put("data", snsTopicList);
	// }

	// @RequestMapping(value = "newTopicList.do")
	// public List<SnsTopicVo> newTopicList(@RequestBody SearchNewsVo vo) {
	// return snsTopicService.newTopicList(vo);
	// }

	// /**
	// * 查询新鲜事(cms)
	// *
	// * @author lyb
	// * @email 674142624@qq.com
	// * @date 2017年12月15日 下午4:50:01
	// * @return
	// * @version v0.1
	// */
	// @RequestMapping(value = "cmsTopics.do/{num}/{memberId}")
	// public List<Map<String,Object>> topicCountByContentId(@PathVariable
	// Integer num,@PathVariable Long memberId) {
	//
	// return snsTopicService.cmsTopics(num,memberId);
	// }

	// /**
	// * cms的推荐附近的人
	// *
	// * @author lyb
	// * @email 674142624@qq.com
	// * @date 2017年12月21日 下午4:06:41
	// * @return
	// * @version v0.1
	// */
	// @RequestMapping(value =
	// "cmsNearPeoples.do/{longitude}/{latitude}/{memberId}")
	// @ResponseBody
	// public List<NearTopicVo> cmsNearPeoples(@PathVariable("longitude") Double
	// longitude,@PathVariable("latitude") Double
	// latitude,@PathVariable("memberId") Long memberId) {
	// NearTopicVo vo = new NearTopicVo();
	// vo.setLongitude(longitude);
	// vo.setLatitude(latitude);
	// vo.setUmemberId(memberId);
	// if(vo.getUmemberId()==0) {
	// vo.setUmemberId(null);
	// }
	// List<NearTopicVo> nearTopics = snsTopicService.nearPeoples(vo);
	//
	// return nearTopics;
	// }

	// @RequestMapping(value = "removeTopic.do")
	// public boolean removeTopic(@RequestBody SnsTopicVo vo) {
	// return snsTopicService.removeTopic(vo);
	// }

	// /**
	// * 用户详情
	// *
	// * @author lyb
	// * @email 674142624@qq.com
	// * @date 2017年11月22日 下午6:16:38
	// * @return
	// * @version v0.1
	// */
	// @RequestMapping(value = "userTopicDetail.do")
	// @ResponseBody
	// public R userTopicDetail(UserSnsTopicVo vo) {
	//
	// Map<String, String> map = ValidatorUtils.validateEntity(vo);
	// if (!map.get("status").equals("200")) {
	// return R.error(1, map.get("message"));
	// }
	//
	// try {
	// Integer concernType = null;
	// ConcernTopicVo ct = new ConcernTopicVo();
	// ct.setUmemberId(vo.getMemberId());
	// //列表
	// List<MyTopicVo> userTopicsDetail = snsTopicService.userTopicsDetail(vo);
	// //关注人的数量
	// Integer gCount = snsTopicService.concernUserCount(ct);
	// //粉丝的数量
	// Integer fCount = snsTopicService.concernMyCount(ct);
	// //判断是否关注
	// if(vo.getUmemberId()!=null) {
	// UCenterFollow concern = snsTopicService.isConcern(vo.getMemberId(),
	// vo.getUmemberId());
	// if(concern!=null) {
	// concernType = concern.getConcernType();
	// }else {
	// concernType =0;
	// }
	// }else {
	// concernType = 0;
	// }
	//
	// //用户信息
	// UCenterMember ucenterMember = snsTopicService.userDetail(ct);
	// //签到数据
	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	// Calendar c = Calendar.getInstance();
	// String now = format.format(c.getTime());
	// c.add(Calendar.MONTH, -1);
	// String start = format.format(c.getTime());
	// MySignDto dto = new MySignDto();
	// dto.setCurrentDate(now);
	// dto.setOneMonthDate(start);
	// dto.setMemberId(vo.getMemberId());
	// List<UCenterSign> UCenterSigns = snsTopicService.mySign(dto);
	// //刚刚点赞的新鲜事
	// MyTopicVo upTopic = snsTopicService.upTopic(vo);
	// return R.ok().put("data", userTopicsDetail).put("gCount",
	// gCount).put("concernType", concernType).put("fCount",
	// fCount).put("ucenterMember", ucenterMember).put("signs",
	// UCenterSigns).put("upTopic", upTopic);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// return R.error(1, "附近的人失败");
	// }
	//
	// }

	// /**
	// * 详情(用户调用)
	// *
	// * @author lyb
	// * @email 674142624@qq.com
	// * @date 2017年12月7日 下午5:09:09
	// * @return
	// * @version v0.1
	// */
	// @RequestMapping(value = "topicDetail.do/{topicId}")
	// public SnsTopic topicDetail(@PathVariable Long topicId) {
	// SnsTopic detail = snsTopicService.topicDetail(topicId);
	// return detail;
	// }

	// /**
	// * 爆料详情接口
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "newTopicDetail.do")
	// public SnsTopicVo newTopicDetail(@RequestBody SnsTopicVo vo) {
	// SnsTopicVo snsTopicVo = snsTopicService.newTopicDetail(vo);
	// return snsTopicVo;
	// }
}
