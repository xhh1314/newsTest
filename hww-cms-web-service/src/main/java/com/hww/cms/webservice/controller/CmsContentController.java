package com.hww.cms.webservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.hww.app.api.AppFeignClient;
import com.hww.app.common.dto.AppBehaviorDto;
import com.hww.app.common.dto.AppMemberNearbyDto;
import com.hww.app.common.dto.SearchHistoryDto;
//import com.hww.app.common.dto.HSearchDto;
import com.hww.base.util.R;
import com.hww.cms.common.dto.HCmsDto;
import com.hww.cms.common.dto.HCmsIndexDto;
import com.hww.cms.common.dto.HCmsInstrDto;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.vo.CmsContentDataVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.common.vo.HCmsSpecialVo;
import com.hww.cms.webservice.service.CmsContentCacheProxyService;
import com.hww.cms.webservice.service.CmsContentService;
import com.hww.cms.webservice.service.CmsSpecialService;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.HSearchDto;
import com.hww.framework.common.exception.HServiceLogicException;
import com.hww.sns.api.SnsFeignClient;
import com.hww.sns.common.util.ValidatorUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cms/content")
public class CmsContentController {

	private static final Log log = LogFactory.getLog(CmsContentController.class);

	@Autowired
	private Environment env;
	@Autowired
	private CmsContentService cmsContentService;

	@Autowired
	AppFeignClient appFeignClient;
	@Autowired
	SnsFeignClient snsFeignClient;
	@Autowired
	private CmsSpecialService cmsSpecialService;
	// @Autowired
	// private SearchHistoryService searchHistoryService;
	@Autowired
	ElsFeignClient elsFeignClient;

	@Autowired
	private CmsContentCacheProxyService cmsContentCacheProxyService;

	// ===========================detail=============================
	/**
	 * APP端新闻详情
	 *  获取规则：单个新闻id
	 *  接口业务：相关新闻列表 + 用户收藏状态  + 用户点赞状态+  新闻类型  + 文件列表（图片、视频、音频） + 爆料数 + 爆料列表（最多返回3个） + 评论数 + 评论列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "新闻详情", notes = "")
	@RequestMapping(value = "newDetail.do", method = RequestMethod.POST)
	public R newDetail(HCmsQueryDto cmsQueryDto) {
		log.info("----------APP端新闻详情开始----------");
		if (cmsQueryDto == null) {
			return R.error(500, "参数为空");
		}
		CmsContentDataVo cmsContent = cmsContentService.loadNewsDetail(cmsQueryDto);
		log.info("----------APP端新闻详情结束----------");
		return R.ok().put("data", cmsContent);
	}

	/**
	 * 图集详情
	 *
	 */
	@ApiOperation(value = "图集新闻详情", notes = "")
	@RequestMapping(value = "atlasDetail.do", method = RequestMethod.POST)
	@ResponseBody
	public R atlasDetail(HCmsQueryDto cmsQueryDto) {
		log.info("----------图集详情开始----------");
		try {
			String result = "";
			if (cmsQueryDto.getContentId() != null) {
				CmsContentDataVo atlasDetail = cmsContentService.loadNewsDetail(cmsQueryDto);
				log.info("----------图集详情结束----------");
				return R.ok().put("data", atlasDetail);
			}
			return R.ok().put("data", result);
		} catch (Exception e) {

			e.printStackTrace();
			return R.error(1, "查询失败");
		}
	}

	/**
	 * 新闻点赞
	 */
	@ApiOperation(value = " 新闻点赞", notes = "")
	@RequestMapping(value = "newsUp.do", method = RequestMethod.POST)
	@ResponseBody
	public R newsUp(HCmsDto cmsDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(cmsDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			String isExists = appFeignClient.behaviorExist(
					new AppBehaviorDto(cmsDto.getMemberId(), cmsDto.getContentId(), AppBehaviorDto.BevType.b1_dz)
							.setPlateType(AppBehaviorDto.PlatType.b0_news));
			if ("y".equals(isExists)) {
				throw new HServiceLogicException("已经点过赞，请不要重复操作");
			}
			AppBehaviorDto behaviorDto = new AppBehaviorDto(cmsDto.getMemberId(), cmsDto.getContentId(),
					AppBehaviorDto.BevType.b1_dz, 1, AppBehaviorDto.PlatType.b0_news)
							.setPlateType(AppBehaviorDto.PlatType.b0_news);
			R res = appFeignClient.createBehavior(behaviorDto);
			return res;
		} catch (HServiceLogicException e) {
			return R.error(1, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "操作失败");
		}
	}

	/**
	 * 新闻取消点赞
	 */
	@ApiOperation(value = " 新闻取消点赞", notes = "")
	@RequestMapping(value = "newsUpCancel.do", method = RequestMethod.POST)
	@ResponseBody
	public R newsUpCancel(HCmsDto cmsDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(cmsDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			AppBehaviorDto behaviorDto = new AppBehaviorDto(cmsDto.getMemberId(), cmsDto.getContentId(),
					AppBehaviorDto.BevType.b1_dz, AppBehaviorDto.BevValue.b0_cancel, AppBehaviorDto.PlatType.b1_topic)
							.setPlateType(AppBehaviorDto.PlatType.b0_news);
			R res = appFeignClient.createBehavior(behaviorDto);
			return res;
		} catch (HServiceLogicException e) {
			return R.error(1, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "操作失败");
		}
	}

	/**
	 * 新闻收藏
	 */
	@ApiOperation(value = "新闻收藏", notes = "")
	@RequestMapping(value = "newsCollect.do", method = RequestMethod.POST)
	@ResponseBody
	public R newsCollect(HCmsDto cmsDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(cmsDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			String isExists = appFeignClient.behaviorExist(
					new AppBehaviorDto(cmsDto.getMemberId(), cmsDto.getContentId(), AppBehaviorDto.BevType.b2_sc)
							.setPlateType(AppBehaviorDto.PlatType.b0_news));
			if ("y".equals(isExists)) {
				throw new HServiceLogicException("已经收藏，请不要重复操作");
			}
			AppBehaviorDto behaviorDto = new AppBehaviorDto(cmsDto.getMemberId(), cmsDto.getContentId(),
					AppBehaviorDto.BevType.b2_sc, AppBehaviorDto.BevValue.b1_ok, AppBehaviorDto.PlatType.b0_news)
							.setPlateType(AppBehaviorDto.PlatType.b0_news);
			return appFeignClient.createBehavior(behaviorDto);

		} catch (HServiceLogicException e) {
			return R.error(1, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "操作失败");
		}
	}

	/**
	 * 新闻取消收藏
	 */
	@ApiOperation(value = "新闻取消收藏", notes = "")
	@RequestMapping(value = "newsCollectCancel.do", method = RequestMethod.POST)
	@ResponseBody
	public R newsCollectCancel(HCmsDto cmsDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(cmsDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		try {
			AppBehaviorDto behaviorDto = new AppBehaviorDto(cmsDto.getMemberId(), cmsDto.getContentId(),
					AppBehaviorDto.BevType.b2_sc, AppBehaviorDto.BevValue.b0_cancel, AppBehaviorDto.PlatType.b0_news)
							.setPlateType(AppBehaviorDto.PlatType.b0_news);
			return appFeignClient.createBehavior(behaviorDto);

		} catch (HServiceLogicException e) {
			return R.error(1, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(1, "操作失败");
		}
	}

	// 注意清理不感兴趣缓存
	@ApiOperation(value = "不感兴趣。。。", notes = "")
	@RequestMapping(value = "newsUninterest.do", method = RequestMethod.POST)
	@ResponseBody
	public R newsUninterest(HCmsInstrDto uninstrDto) {
		Map<String, String> map = ValidatorUtils.validateEntity(uninstrDto);
		if (!map.get("status").equals("200")) {
			return R.error(1, map.get("message"));
		}
		return cmsContentCacheProxyService.newsUninterest(uninstrDto);
	}

	// ===========================List=============================

	/**
	 * 根据频道查询
	 * @return
	 */
	@ApiOperation(value = "根据频道查询新闻列表")
	@RequestMapping(value = "loadByColumnId.do", method = RequestMethod.POST)
	public R loadByColumnId(HCmsQueryDto cmsQueryDto) {
		log.info("----------APP根据频道查询 开始----------");
		List<CmsContentVo> cmsContentList = cmsContentService.loadCmsContentByColumnId(cmsQueryDto);
		log.info("----------AP根据频道查询 结束----------");
		return R.ok().put("data", cmsContentList);
	}

	// ===========================专题=============================

	@ApiOperation(value = "查询专题--内容包含子专题", notes = "")
	@RequestMapping(value = "loadNewsBySpecialId.do", method = RequestMethod.POST)
	public R loadNewsBySpecialId(HCmsQueryDto cmsQueryDto) {
		log.info("----------loadSpecial----------");
		// 修改为根据前端传过来分页显示
		HCmsSpecialVo cmsSpecialVo = cmsSpecialService.loadCmsSpecialVoById(cmsQueryDto);
		if (cmsSpecialVo == null) {
			return R.ok().put("data", null);
		}
		log.info("----------loadSpecial---------");
		return R.ok().put("data", cmsSpecialVo);
	}

	@ApiOperation(value = "查询指定专题的新闻", notes = "")
	@RequestMapping(value = "loadContentBySpecialId.do", method = RequestMethod.POST)
	public R loadContentBySpecialId(HCmsQueryDto cmsQueryDto) {
		log.info("----------loadNewsBySpecialId----------");
		List<CmsContentVo> cmsContentList = cmsContentService.loadCmsContentBySpecilId(cmsQueryDto);
		log.info("----------loadNewsBySpecialId---------");
		return R.ok().put("data", cmsContentList);
	}

	@ApiOperation(value = "获取我收藏的新闻的列表", notes = "")
	@RequestMapping(value = "loadUserCollectNews.do", method = RequestMethod.POST)
	public R loadUserCollectNews(HCmsQueryDto cmsQueryDto) {
		if (cmsQueryDto.getMemberId() == null) {
			return R.error(500, "未登录");
		}
		List<CmsContentVo> collectionList = cmsContentService.loadUserCollectNews(cmsQueryDto);

		return R.ok().put("data", collectionList);
	}

	@ApiOperation(value = "搜索---视频", notes = "")
	@RequestMapping(value = "searchVideo.do", method = RequestMethod.POST)
	public R searchVideo(HSearchDto searchDto) {
		// 1综合 2爆料 3视频 4 新鲜事 5 用户 6地点
		List<Long> vedioIds = elsFeignClient.searchIdsIncontentFeginApi(searchDto.setSearchType(3));
		if (vedioIds == null) {
			return R.ok().put("data", null);
		}
		List<CmsContentVo> cmsContentList = cmsContentService.loadCmsContentByIds(vedioIds, searchDto.getMemberId(),
				true);
		try {
			saveSearchHis(SearchHistoryDto.newForCreate(searchDto.getMemberId(), searchDto.getImei(),
					searchDto.getKeywords(), 3));
		} catch (Exception e) {
		}
		return R.ok().put("data", cmsContentList);

	}

	/**
	 * 综合搜索(新闻)
	 * 
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "搜索--综合", notes = "")
	@RequestMapping(value = "searchNews.do", method = RequestMethod.POST)
	public R searchNews(HSearchDto searchDto) {
		if (searchDto == null) {
			return R.error(500, "参数为空");
		}
		// 1综合 2爆料 3视频 4 新鲜事 5 用户 6地点
		List<Long> newsIds = elsFeignClient.searchIdsIncontentFeginApi(searchDto.setSearchType(1));
		if (newsIds == null) {
			return R.ok().put("data", null);
		}
		List<CmsContentVo> cmsContentList = cmsContentService.loadCmsContentByIds(newsIds, searchDto.getMemberId(),
				true);
		try {
			saveSearchHis(SearchHistoryDto.newForCreate(searchDto.getMemberId(), searchDto.getImei(),
					searchDto.getKeywords(), 1));
		} catch (Exception e) {
		}
		return R.ok().put("data", cmsContentList);
	}

	@Async
	public void saveSearchHis(SearchHistoryDto historyDto) {
		appFeignClient.addSearchHistory(historyDto);
	}

	/**
	 * APP端用户新闻收藏列表
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "loadUserCollectNewsFeginApi.do", method = RequestMethod.POST)
	public List<CmsContentVo> loadUserCollectNewsFeginApi(@RequestBody HCmsQueryDto cmsQueryDto) {
		return cmsContentService.loadUserCollectNews(cmsQueryDto);
	}

	public R homeHis(HCmsIndexDto cmsIndexDto) {
		R res = R.ok();
		// 上拉必须参数
		if (cmsIndexDto.getPageNo() == null || cmsIndexDto.getPageSize() == null || cmsIndexDto.getIsPullUp() == null) {
			res.put("data", null);
		}

		List<Map<String, Object>> data = cmsContentService.homeCmsSnsUpPull(cmsIndexDto);

		return res.put("list", data);
	}

	@RequestMapping(value = "home.do", method = RequestMethod.POST)
	@ResponseBody
	public R home(HCmsIndexDto cmsIndexDto) {
		if (cmsIndexDto.getIsPullUp() != null && cmsIndexDto.getIsPullUp() > 0) {
			return homeHis(cmsIndexDto);
		}
		R res = R.ok();
		long a = System.currentTimeMillis();
		log.debug("--------home---start-||" + a / 1000);
		// 置顶暂时给三条
		List<CmsContentVo> topNewsList = cmsContentService.loadTopNews(
				new HCmsQueryDto().setMemberId(cmsIndexDto.getMemberId()).setPageNo(1).setPageSize(3), false);

		List<Long> topNewsIds = Lists.newArrayList();
		if (topNewsList != null && topNewsList.size() > 0) {
			topNewsIds = topNewsList.stream().map(val -> val.getContentId()).collect(Collectors.toList());
		}
		log.debug("-----after---topNewsList.---t:||" + (System.currentTimeMillis() - a) / 1000);

		long b = System.currentTimeMillis();
		List<Map<String, Object>> homeCmsSnsList = cmsContentService.homeCmsSns(cmsIndexDto, topNewsIds);

		// ---------------------------------------------
		Map<String, Object> indexIdsMap = homeCmsSnsList.get(homeCmsSnsList.size() - 1);
		Set<Long> indexIdsList = (Set<Long>) indexIdsMap.get("indexIds");
		String indexIds = "";
		if (indexIdsList != null && !indexIdsList.isEmpty()) {

			for (Long id : indexIdsList) {
				indexIds += id + ",";
			}
			res.put("indexIds", indexIds);
		}
		homeCmsSnsList.remove(homeCmsSnsList.size() - 1);

		// 因为数据量太少，将第一页的历史也调出来
		cmsIndexDto.setIndexIds(indexIds);
		// 数据少时加载第一页
		List<Map<String, Object>> hisData = cmsContentService.homeCmsSnsUpPull(cmsIndexDto);
		if (hisData != null && !hisData.isEmpty()) {
			CmsContentVo c = null;
			for (Map<String, Object> map : hisData) {
				if (map != null && map.get("content") != null) {
					c = (CmsContentVo) map.get("content");
					if (c != null) {
						indexIds += c.getContentId() + ",";
					}
				}
			}
			res.put("indexIds", indexIds);
		}
		homeCmsSnsList.addAll(hisData);
		// 因为数据量太少，将第一页的历史也调出来

		// --------------------------------------------------------
		long c = System.currentTimeMillis();
		log.debug("-----after---homeCmsSnsList.----|t1: +" + (c - b) / 1000 + "||t2: " + (c - a) / 1000 + "");
		// 对数据去重
		List<Map<String, Object>> distinctHomeCmsSnsList = cmsContentService.distinctRecommendData(homeCmsSnsList);
		res.put("topList", topNewsList);
		res.put("list", distinctHomeCmsSnsList);

		long d = System.currentTimeMillis();

		AppMemberNearbyDto dto = new AppMemberNearbyDto().setMemberId(cmsIndexDto.getMemberId());
		dto.setLongitude("" + cmsIndexDto.getLongitude()).setLatitude("" + cmsIndexDto.getLatitude()).setRandom(false)
				.setPageNo(1).setPageSize(10);

		R resx = appFeignClient.searchNearsPeaople(dto);

		long e = System.currentTimeMillis();
		log.debug("-----after---searchNearsPeaople.----|t1: +" + (e - d) / 1000 + "||t2: " + (e - a) / 1000 + "");

		if (resx.isOk()) {
			res.put("nearPeoples", resx.get("data"));
		} else {
			res.put("nearPeoples", Lists.newArrayList());
		}
		if (cmsIndexDto.getLongitude() != null && cmsIndexDto.getLatitude() != null) {
			Long nearTopicNum = elsFeignClient
					.searchNearTopicCountFeginApi(new HSearchDto().setLongitude(cmsIndexDto.getLongitude())
							.setLatitude(cmsIndexDto.getLatitude()).setDistance(100000));
			res.put("nearTopicNum", nearTopicNum == null ? 0L : nearTopicNum);
		} else {
			res.put("nearTopicNum", 0);
		}
		return res;
	}
	// ===========================feginAPI=============================

	/**
	 * APP端新闻详情
	 *  获取规则：单个新闻id
	 *  接口业务：相关新闻列表 + 用户收藏状态  + 用户点赞状态+  新闻类型  + 文件列表（图片、视频、音频） + 爆料数 + 爆料列表（最多返回3个） + 评论数 + 评论列表
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "新闻详情FeginApi", notes = "")
	@RequestMapping(value = "loadNewsDetailFeginApi.do", method = RequestMethod.POST)
	public CmsContentDataVo loadNewsDetailFeginApi(@RequestBody HCmsQueryDto cmsQueryDto) {

		CmsContentDataVo cmsContent = cmsContentService.loadNewsDetail(cmsQueryDto);
		return cmsContent;
	}

	@ApiOperation(value = "新闻详情FeginApi", notes = "")
	@RequestMapping(value = "loadNewsNoPostAndTopicFeginApi.do", method = RequestMethod.POST)
	public CmsContentDataVo loadNewsNoPostAndTopicFeginApi(@RequestBody HCmsQueryDto cmsQueryDto) {
		CmsContentDataVo cmsContent = cmsContentService.loadNewsDetailWithoutPostAndTopic(cmsQueryDto);
		return cmsContent;
	}

	@ApiOperation(value = "新闻详情---分享也专用", notes = "")
	@RequestMapping(value = "newsShareDetail.do", method = RequestMethod.POST)
	@ResponseBody
	public R newsShareDetail(String id) {
		if (!StringUtils.hasText(id)) {
			return R.error();
		}
		CmsContentDataVo cmsContent = cmsContentService.loadNewsDetailForShare(Long.valueOf(id));
		log.debug("----------APP端新闻详情结束----------" + cmsContent != null ? id : -1);
		return R.ok().put("data", cmsContent).put("filedomain", env.getProperty("fileservice.domain"));
	}

	// /**
	// * APP端查询专题列表
	// */
	// @ApiOperation(value = "专题列表", notes ="")
	// @RequestMapping(value = "loadSpecials.do", method = RequestMethod.POST)
	// public R querySpecials() {
	// log.info("----------APP首页查询专题列表开始----------");
	// List<CmsSpecial> cmsSpecialList =
	// cmsSpecialService.loadAllSpecialList(Short.valueOf("1"));
	// log.info("----------APP首页查询专题列表结束----------");
	// return R.ok().put("data", cmsSpecialList);
	// }

	// @ApiOperation(value = "查询子专题", notes ="")
	// @RequestMapping(value = "loadChildSpecials.do", method =
	// RequestMethod.POST)
	// public R loadChildSpecials(Long specialId) {
	// log.info("----------queryChildSpecials----------");
	// List<CmsSpecial> cmsSpecialList =
	// cmsSpecialService.loadByParentId(specialId);
	// log.info("----------queryChildSpecials---------");
	// return R.ok().put("data", cmsSpecialList);
	// }

	// @RequestMapping(value = "list.do", method = RequestMethod.POST)
	// public Pagination list(@RequestBody(required = false) CmsContentDto
	// cmsContentDto) {
	// Pagination pagination = new Pagination();
	// try {
	// pagination = cmsContentService.listContent(cmsContentDto);
	// // spring自带jackson解析失败,故手动解析
	// // String result = JSON.toJSONString(p);
	// log.info("接口返回数据成功!");
	// return pagination;
	// } catch (Exception e) {
	// e.printStackTrace();
	// log.error("接口返回数据失败!");
	// }
	// return pagination;
	// }
	//
	//
	// /**
	// * 查询置顶新闻列表
	// * 获取规则：后台设置置顶新闻，可以多个
	// * 接口业务：列表（不分页） + 爆料数 + 距离当前时间字符串
	// * @return
	// */
	// @ApiOperation(value = "查询置顶新闻列表", notes ="")
	// @RequestMapping(value = "queryTopNew.do", method = RequestMethod.POST)
	// public R queryTopNew(HCmsQueryDto cmsQueryDto) {
	// log.info("----------APP端首页置顶新闻开始----------");
	// List<CmsContentVo> cmsContentList =
	// cmsContentService.loadTopNews(cmsQueryDto);
	// log.info("----------APP端首页置顶新闻结束----------");
	// return R.ok().put("data", cmsContentList);
	// }
	//
	// /**
	// * APP端推荐新闻列表
	// * 获取规则：推荐当前位置由近及远经纬度新闻列表，分页为20条，按发布时间降序排列
	// * 接口业务：分页 （地理位置） + 爆料数 + 距离当前时间字符串
	// * @param vo
	// * @return
	// */
	// @ApiOperation(value = "端推荐新闻列表", notes ="未实现")
	// @RequestMapping(value = "queryPromoteNews.do", method =
	// RequestMethod.POST)
	// public R queryPromoteNews(QueryContentVo vo) {
	// log.info("----------APP端首页推荐新闻开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// List<CmsContentVo> list = cmsContentService.queryPromoteNewList(vo);
	// log.info("----------APP端首页推荐新闻结束----------");
	// return R.ok().put("data", list);
	// }

	// /**
	// * APP端查询其他新闻列表
	// *
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "queryOtherNews.do", method = RequestMethod.POST)
	// public R queryOtherNews(QueryContentVo vo) {
	//
	// if (vo.getPageNo()==null) {
	// vo.setPageNo(1);
	// }
	//
	// if (vo.getPageSize()==null) {
	// vo.setPageNo(10);
	// }
	//
	// log.info("----------APP端其他新闻列表开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// List<CmsContentVo> cmsContentList = cmsContentService.queryOtherNews(vo);
	// log.info("----------APP端其他新闻列表结束----------");
	// return R.ok().put("data", cmsContentList);
	// }

	// /**
	// * APP端新闻爆料
	// *
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "saveNewTopic.do")
	// public R saveNewTopic(SnsTopicVo vo) {
	// log.info("----------APP端新闻爆料开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// SnsTopicVo snsTopicVo = cmsContentService.saveNewTopic(vo);
	// log.info("----------APP端新闻爆料结束----------");
	// return R.ok().put("data", snsTopicVo);
	// }

	// /**
	// * APP端新闻爆料列表
	// *
	// * @param newId
	// * @return
	// */
	// @RequestMapping(value = "newTopicList.do")
	// public R newTopicList(Long newId, Long userId, String imei) {
	// log.info("----------APP端新闻爆料开始----------");
	// if (newId == null || newId <= 0) {
	// return R.error(500, "参数为空");
	// }
	// List<SnsTopicVo> snsTopicList = cmsContentService.newTopicList(newId,
	// userId, imei);
	// log.info("----------APP端新闻爆料结束----------");
	// return R.ok().put("data", snsTopicList);
	// }

	// /**
	// * APP端新闻评论列表
	// *
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "newPostList.do")
	// public R newPostList(SnsPostVo vo) {
	// log.info("----------APP端新闻评论列表开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// List<SnsPostVo> snsNewsPostList = cmsContentService.newPostList(vo);
	// log.info("----------APP端新闻评论列表结束----------");
	// return R.ok().put("data", snsNewsPostList);
	// }

	// /**
	// * APP端新闻评论的详情
	// *
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "newPostDetail.do")
	// public R newPostDetail(SnsPostVo vo) {
	// log.info("----------APP端新闻评论的详情开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// SnsPostVo snsNewsPostVo = cmsContentService.newPostDetail(vo);
	// log.info("----------APP端新闻评论的详情结束----------");
	// return R.ok().put("data", snsNewsPostVo);
	// }

	// /**
	// * APP端新闻评论的评论
	// *
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "newPostOnReview.do")
	// public R newPostOnReview(SnsPostVo vo) {
	// log.info("----------APP端新闻评论的评论开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// SnsPostVo snsNewsPostVo = cmsContentService.newPostOnReview(vo);
	// log.info("----------APP端新闻评论的评论结束----------");
	// return R.ok().put("data", snsNewsPostVo);
	// }

	// /**
	// * APP端新闻爆料详情
	// *
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "newTopicDetail.do")
	// public R newTopicDetail(SnsTopicVo vo) {
	// log.info("----------APP端新闻爆料详情开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// SnsTopicVo snsTopicVo = cmsContentService.newTopicDetail(vo);
	// log.info("----------APP端新闻爆料详情结束----------");
	// return R.ok().put("data", snsTopicVo);
	// }

	// /**
	// * 搜索爆料
	// *
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "searchTopics.do")
	// public R searchTopics(SearchNewsVo vo) {
	// log.info("----------APP端爆料/新鲜事搜索开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// List<SnsTopicVo> snsTopicVoList = cmsContentService.searchTopics(vo);
	// log.info("----------APP端爆料/新鲜事搜索结束----------");
	// return R.ok().put("data", snsTopicVoList);
	// }

	// /**
	// * 搜索视频
	// *
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "searchVideos.do")
	// public R searchVideos(SearchNewsVo vo) {
	// log.info("----------APP端视频搜索开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// List<CmsContentDataVo> cmsContentVoList =
	// cmsContentService.searchVideos(vo);
	// log.info("----------APP端视频搜索结束----------");
	// return R.ok().put("data", cmsContentVoList);
	// }

	// /**
	// * APP端评论删除
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "removePost.do")
	// public R removePost(SnsPostVo vo) {
	// log.info("----------APP端新闻搜索开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// boolean flag = cmsContentService.removePost(vo);
	// log.info("----------APP端新闻搜索结束----------");
	// return R.ok().put("data", flag);
	// }

	// /**
	// * APP端爆料删除
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "removeTopic.do")
	// public R removeTopic(SnsTopicVo vo) {
	// log.info("----------APP端爆料删除开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// boolean flag = cmsContentService.removeTopic(vo);
	// log.info("----------APP端爆料删除结束----------");
	// return R.ok().put("data", flag);
	// }

	// /**
	// * APP端搜索历史/热词推荐数据
	// * @param vo
	// * @return
	// */
	// @RequestMapping(value = "searchHistoryData.do")
	// public R searchHistoryData(SearchHistoryVo vo) {
	// log.info("----------APP端搜索历史/热词推荐开始----------");
	// if (vo == null) {
	// return R.error(500, "参数为空");
	// }
	// SearchHistoryData searchHistoryData =
	// searchHistoryService.searchHistoryData(vo);
	// log.info("----------APP端搜索历史/热词推荐结束----------");
	// return R.ok().put("data", searchHistoryData);
	// }

	// /**
	// * 专题更多
	// *
	// * @author lyb
	// * @email 674142624@qq.com
	// * @date 2017年12月15日 下午6:20:36
	// * @return
	// * @version v0.1
	// */
	// @RequestMapping(value = "contentsBycategoryId.do", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public R contentsBycategoryId(CmsContentVo vo) {
	// List<CmsContentVo> list = cmsContentService.contentsBycategoryId(vo);
	// return R.ok().put("list", list);
	// }

	// /**
	// * 附近新闻(sns)
	// *
	// * @author lyb
	// * @email 674142624@qq.com
	// * @date 2017年12月25日 下午2:16:04
	// * @return
	// * @version v0.1
	// */
	// @RequestMapping(value = "nearContents.do", method = RequestMethod.POST)
	// public List<CmsContentVo> nearContents(@RequestBody CmsContentVo vo) {
	//
	// return cmsContentService.nearContents(vo);
	// }
}
