package com.hww.sns.webadmin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hww.sns.common.Constants;
import com.hww.sns.common.entity.SnsTopic;
import com.hww.sns.common.manager.SnsTopicMng;
import com.hww.sns.common.util.EsContentCovertUtil;
import com.hww.sns.common.vo.SnsTopicVo;
import com.hww.sns.webadmin.service.SnsTopicEsSyncFailService;
import com.hww.sns.webadmin.service.SnsTopicService;
import com.hww.ucenter.api.UcenterFeignClient;
import com.hww.ucenter.common.dto.MememberSnsDisableDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.R;
import com.hww.els.api.ElsFeignClient;
import com.hww.els.common.HSearchDto;
import com.hww.els.common.entity.ESContent;
import com.hww.file.api.FileFeignClient;
import com.hww.framework.web.mvc.controller.AbsBaseController;

/**
 * 主题/爆料/新鲜事
 * 
 * @author hewei
 *
 */
@Controller
@RequestMapping("/sns/topic")
public class SnsTopicController extends AbsBaseController {
	@Autowired
	private UcenterFeignClient ucenterFeignClient;
	@Autowired
	private Environment env;
	@Autowired
	private FileFeignClient fileInfoFeignClient;
	@Resource
	SnsTopicService snsTopicService;
	@Resource
	SnsTopicMng snsTopicMng;
	@Autowired
	ElsFeignClient elsFeignClient;
	@Autowired
	SnsTopicEsSyncFailService snsTopicEsSyncFailService;

	/*
	 * @Resource UCenterMemberService ucenterMemberService;
	 */
	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	public String saveTopic(HttpServletRequest request, HttpServletResponse response, ModelMap map, SnsTopicVo vo) {
		if (vo.getContent() == null) {
			vo.setContent("");
		}
		snsTopicService.save(vo);

		// TODO 跳转到列表页或朋友圈
		return "redirect:list.do";
	}

	/**
	 * 爆料查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "newsbroke_list.do")
	public String listNewsbroke(HttpServletRequest request, HttpServletResponse response, ModelMap map, SnsTopicVo vo) {
		int pageSize = 10;
		vo.setTopicType(Constants.topicType.newsbrokeType);
		Pagination pagination = snsTopicService.listNewsByType(vo, vo.getPageNo(), pageSize);
		map.addAttribute("page", pagination);
		// 返回查询条件
		map.addAttribute("searchvo", vo);
		return "socialdata/newsbroke_list";
	}

	/**
	 * 爆料详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "newsbroke_detail.do")
	public String newsbrokeDetail(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			@RequestBody SnsTopicVo vo) {

		SnsTopicVo topic = snsTopicService.query(vo);
		map.addAttribute("topic", topic);

		return "socialdata/newsbroke_detail";
	}

	@RequestMapping(value = "newsfresh_details.do", method = RequestMethod.POST)
	public String newsfreshDetails(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			@RequestBody SnsTopicVo vo) {
		SnsTopicVo topic = snsTopicService.query(vo);
		map.addAttribute("topic", topic);
		return "audit/relatedNews";
	}

	/**
	 * 新鲜事查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "freshnews_list.do")
	public String listFreshnews(HttpServletRequest request, HttpServletResponse response, ModelMap map, SnsTopicVo vo) {
		int pageSize = 10;
		vo.setTopicType(Constants.topicType.newsfreshType);
		Pagination pagination = snsTopicService.listNewsByType(vo, vo.getPageNo(), pageSize);
		map.addAttribute("page", pagination);
		// 返回查询条件
		map.addAttribute("searchvo", vo);
		return "socialdata/freshnews_list";
	}

	/**
	 * 新鲜事审核列表
	 * 
	 * @return
	 */

	@RequestMapping(value = "freshnews_audit_list.do")
	public String listFreshnewsAudit(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			SnsTopicVo vo) {
		int pageSize = 10;
		// vo.setTopicType(Constants.topicType.newsfreshType);
		// vo.setTopicType(Constants.topicType.newsbrokeType);
		/*
		 * if(vo.getMemberName()!=null && vo.getMemberName()!="" ) { UCenterMemberDto
		 * dto=ucenterMemberService.findMemberByName(vo.getMemberName());
		 * vo.setMemberId(dto.getMemberId()); }
		 */
		Pagination pagination = snsTopicService.listNewsByType(vo, vo.getPageNo(), 10);
		List<SnsTopicVo> vos = (List<SnsTopicVo>) pagination.getList();
		for (SnsTopicVo v : vos) {
			if (v.getMemberId() != null) {
				UCenterMemberDto dto = ucenterFeignClient.userInfoInFeginApi(v.getMemberId());
				if (dto != null) {
					if (dto.getNickName() != null) {
						v.setMemberName(dto.getNickName());
					}
					v.setDisabled(dto.getSnsDisabled());
				}
				
			}
			if ((v.getAuditFlow() == null || v.getAuditFlow() == 0) && v.getAuditStatus() == 2) {
				v.setShowStatus(0);
			}
			if (v.getAuditFlow() != null && v.getAuditFlow() == 1 && v.getAuditStatus() == 2) {
				v.setShowStatus(1);
			}
			// 根据图片id串查询返回图片访问url串 ,分割
			if (Objects.nonNull(v.getFileId()) && !v.getFileId().trim().isEmpty()) {
				String urlByFileId = fileInfoFeignClient.getUrlByFileId(v.getFileId());
				v.setFileId(env.getProperty("fileservice.domain") + urlByFileId);
			}
		}

		if (Objects.nonNull(vo.getKeyWords()) && vo.getKeyWords().trim().length() > 1) {//传入 keyWords
//			List<Map<String, Object>> list = searchNewsList(vo.getKeyWords());
//			Pagination Pagination = new Pagination();
//			Pagination.setList(list);
//			Pagination.setPageSize(list.size());
//			Pagination.setPageNo(vo.getPageNo());
//			map.addAttribute("page", pagination);
//			map.addAttribute("searchvo", vo);
		} else {
			// 返回查询条件
			map.addAttribute("searchvo", vo);
			pagination.setList(vos);
			map.addAttribute("page", pagination);
		}
		return "audit/freshnews_audit_list";
	}

	// els 全文检索 新鲜事审核列表
/*
	public List<Map<String, Object>> searchNewsList(String keywords) {
		HSearchDto searchDto = new HSearchDto();
		searchDto.setKeywords(keywords);
		searchDto.setPageSize(10);
		searchDto.setPlateType(1);
		searchDto.setSearchType(4);
		List<Map<String, Object>> searchNewsFeginApi = elsFeignClient.searchTopicFeginApi(searchDto);

		// 返回查询条件
		return searchNewsFeginApi;
	}*/

	/**
	 * 新鲜事详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "newsfresh_detail.do", method = RequestMethod.POST)
	public String newsfreshDetail(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			@RequestBody SnsTopicVo vo) {
		SnsTopicVo topic = snsTopicService.query(vo);
		map.addAttribute("topic", topic);

		return "audit/audit_detail";
	}

	@RequestMapping(value = "hot_detail.do", method = RequestMethod.POST)
	@ResponseBody
	public SnsTopicVo newshotDetail(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			SnsTopicVo vo) {
		SnsTopicVo topic = snsTopicService.query(vo);
		return topic;
	}

	/**
	 * 新鲜事转爆料
	 * 
	 * @return
	 */
	@RequestMapping(value = "convertfresh_to_broke.do", method = RequestMethod.POST)
	@ResponseBody
	public String convertfreshToBroke(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			@RequestBody SnsTopicVo[] vos) {
		String result = "success";
		List<Long> topicIds = new LinkedList<Long>();
		try {
			for (int i = 0; i < vos.length; i++) {
				SnsTopicVo vo = vos[i];
				topicIds.add(vo.getTopicId());
			}
			snsTopicService.convertfreshToBroke(topicIds, null);
		} catch (Exception e) {
			e.printStackTrace();
			result = "新鲜事转爆料失败!";
		}
		return result;
	}

	/**
	 * 新鲜事关联新闻后变爆料
	 * 
	 * @return
	 */
	@RequestMapping(value = "fresh_relatewith_news.do", method = RequestMethod.POST)
	@ResponseBody
	public String freshRelateWithNews(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		String topicIds = request.getParameter("topicIds"); // 新鲜事id
		String newsid = request.getParameter("newsid"); // 新闻id
		String result = "success";
		List<Long> topicIdList = new LinkedList<Long>();

		try {
			if (topicIds != null && newsid != null) {
				JSONArray jsonArray = JSONArray.parseArray(topicIds);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject obj = (JSONObject) jsonArray.get(i);
					Long topicId = obj.getLong("topicId");
					topicIdList.add(topicId);
				}
				JSONObject newsObj = JSONObject.parseObject(newsid);
				Long newsId = newsObj.getLong("contentId");

				snsTopicService.convertfreshToBroke(topicIdList, newsId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "新鲜事转爆料失败!";
		}
		return result;
	}
	/*
	 * @RequestMapping("tramCms.do")
	 * 
	 * @ResponseBody public List<BaseTreeVo> tramNewsCms() { List<BaseTreeVo>
	 * tree=topicToNewsService.allCategoryJson(); return tree; }
	 */

	@RequestMapping(value = "trams.do", method = RequestMethod.GET)
	@ResponseBody
	public String tramCms() {
		Finder hql = Finder.create("from ");
		hql.append(SnsTopic.class.getName());

		List<SnsTopic> list = (List<SnsTopic>) snsTopicMng.find(hql);
		for (SnsTopic snsTopic : list) {
			try {
				ESContent eSContent = EsContentCovertUtil.toESContent(snsTopic);
				R r = elsFeignClient.createContentFeginApi(eSContent);
				if (!r.isOk()) {
					return "error1";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}

		}

		return "ok";
	}

	/**
	 * els中获取新闻列表
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping("newsList.do")
	@ResponseBody
	public List<Map<String, Object>> Newslist(ModelMap map, Integer pageNo, String keywords) {
		HSearchDto dto = new HSearchDto();
		if (pageNo != null) {
			dto.setPageNo(pageNo);
		}
		dto.setPageSize(10);
		dto.setSearchType(0);
		dto.setContentType(0);
		List<Map<String, Object>> dtolist = elsFeignClient.searchNewsFeginApi(dto);
		if (keywords != null) {
			List<Map<String, Object>> dlist = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < dtolist.size(); i++) {
				if (keywords.equals(dtolist.get(i).get("keyWords"))) {
					dlist.add(dtolist.get(i));
				}
			}
			return dlist;
		}
		return dtolist;
	}

	// @RequestMapping("findNews.do")
	// @ResponseBody
	// public String findNews(Long topicId,HSearchDto dto,Integer pageNo){
	// if(pageNo==null) {
	// dto.setPageNo(1);
	// }else {
	// dto.setPageNo(pageNo);
	// }
	// dto.setPageSize(10);
	// SnsTopicVo vo=snsTopicService.findById(topicId);
	// List<Map<String, Object>> dtolist= elsFeignClient.searchNewsFeginApi(dto);
	//
	//
	// return "";
	//
	// }
	@RequestMapping("topicToNews.do")
	@ResponseBody
	public String TopicToNews(Long topicId, Long relatednewsId) {
		snsTopicService.TopicToNews(topicId, relatednewsId);
		return "success";
	}

	@RequestMapping(value = "findNewsById.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findNewsById(Long relatednewsId) {
		Map<String, Object> news = elsFeignClient.searchContentIdFeginApi(relatednewsId);
		return news;
	}

	@RequestMapping("topicNotToHot.do")
	@ResponseBody
	public String topicNotToHot(Long topicId) {
		snsTopicService.topicNotToHot(topicId);
		return "success";
	}

	@RequestMapping("disabledUser.do")
	@ResponseBody
	public String disabled(Long memberId, Integer disabled) {
		MememberSnsDisableDto dto = new MememberSnsDisableDto();
		dto.setDisabled(disabled);
		dto.setMemberId(memberId);
		ucenterFeignClient.setMememberSnsDisabled(dto);
		return "success";
	}

}
