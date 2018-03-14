package com.hww.els.webservice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.R;
import com.hww.els.common.HSearchDto;
import com.hww.els.common.dto.ESContentDto;
import com.hww.els.common.entity.ESContent;
import com.hww.els.common.enums.ElasticTypes;
import com.hww.els.common.util.ElasticUtil;
import com.hww.els.webservice.config.ElasticsearchConfiguration;
import com.hww.els.webservice.service.ElasticService;
import com.hww.framework.common.exception.HServiceLogicException;

@RestController
@RequestMapping("/es/search")
public class ContentSearchContorller {

	private static  final Logger log= LoggerFactory.getLogger("contentSearchController-->>");

	@Autowired
	ElasticsearchConfiguration elasticsearchConfiguration;

	@Autowired
	ElasticService elasticService;

	@RequestMapping(value = "searchNearTopicCountFeginApi.do", method = RequestMethod.POST)
	public Long searchContentCountFeginApi(@RequestBody HSearchDto searchDto) {
		String distance = (searchDto.getDistance() == null ? "100000" : searchDto.getDistance()) + "m";
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		// boolQueryBuilder.must(QueryBuilders.termQuery("topicType",
		// 0));//0新鲜事, 1爆料，2 评论
		boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 1));// 0 新闻 1
																	   // topic
																	   // 2 post
		boolQueryBuilder.must(QueryBuilders.termQuery("showStatus", 1));
		boolQueryBuilder.must(QueryBuilders.termQuery("status", 1));
		boolQueryBuilder.must(QueryBuilders.termQuery("publi", 1));

		GeoDistanceQueryBuilder distanceBuilder = new GeoDistanceQueryBuilder("geolocation")
				.point(searchDto.getLatitude(), searchDto.getLongitude()).distance(distance, DistanceUnit.METERS)
				.geoDistance(GeoDistance.ARC);
		boolQueryBuilder.must(distanceBuilder);

		SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESContent.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder)
				// .setQuery(distanceBuilder)
				.execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return 0L;
		}
		return hits.getTotalHits();
	}

	@RequestMapping(value = "searchNearTopicIdsFeginApi.do", method = RequestMethod.POST)
	public List<Long> searchNearTopicIdsFeginApi( @RequestBody HSearchDto searchDto) {
		Integer searchType = searchDto.getSearchType();// 1综合 2爆料 3视频 4 新鲜事 5 用户
													   // 6地点
		if (searchType == null) {
			return Lists.newArrayList();
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 1));// 0 新闻 1 topic 2 post
		boolQueryBuilder.must(QueryBuilders.termQuery("publi", 1));
		boolQueryBuilder.must(QueryBuilders.termQuery("status", 1));

		//用户登录状态下的逻辑，需要显示自己发的未审核数据
		if(searchDto.getMemberId()!=null) {
			//正常附近的人
			BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
			boolQueryBuilder1.must(QueryBuilders.termQuery("showStatus", 1));

			//表示memberid=xxx并且showStatus=0的数据,意为显示出自己发布的待审核数据
			BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
			boolQueryBuilder2.must(QueryBuilders.termQuery("memberId", searchDto.getMemberId()));
			boolQueryBuilder2.must(QueryBuilders.termQuery("showStatus", 0));
			boolQueryBuilder2.mustNot(QueryBuilders.termQuery("auditStatus", 0));

			BoolQueryBuilder boolQueryBuilder3 = QueryBuilders.boolQuery();
			//or条件
			boolQueryBuilder3.should(boolQueryBuilder1);
			boolQueryBuilder3.should(boolQueryBuilder2);
			//这里先封装2个shuoud的子查询，再合并一个must的查询,来实现或的关系
			boolQueryBuilder.must(boolQueryBuilder3);
		}else{
			//用户没有登录，则展示所有可显示的数据即可
			boolQueryBuilder.must(QueryBuilders.termQuery("showStatus", 1));
		}

		String distance = (searchDto.getDistance() == null ? "100000" : searchDto.getDistance()) + "m";

		GeoDistanceQueryBuilder distanceBuilder = new GeoDistanceQueryBuilder("geolocation")
				.point(searchDto.getLatitude(), searchDto.getLongitude()).distance(distance, DistanceUnit.METERS)
				.geoDistance(GeoDistance.ARC);

		boolQueryBuilder.must(distanceBuilder);

		SortBuilder<?> fieldSortBuilder = SortBuilders.fieldSort("createTime");
		if (searchDto.getOrderBy() == null || searchDto.getOrderBy().intValue() == 1) {// 1为按时间排序，2为按位置排序

			fieldSortBuilder = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);

		} else if (searchDto.getOrderBy() != null && searchDto.getOrderBy().intValue() == 2
				&& searchDto.getLatitude() != null && searchDto.getLongitude() != null) {

			fieldSortBuilder = SortBuilders.geoDistanceSort("geolocation",
					new GeoPoint(searchDto.getLatitude(), searchDto.getLongitude()));

		} else {
			fieldSortBuilder = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
			
			
		}
		int pageNo = searchDto.getPageNo() == null ? 1 : searchDto.getPageNo();
		int pageSize = searchDto.getPageSize() == null ? 10 : searchDto.getPageSize();

		int from = (pageNo - 1) * pageSize;
		SearchRequestBuilder searchRequestBuilder=elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESContent.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder)
				.addSort(fieldSortBuilder).setFrom(from).setSize(pageSize);
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		//log.debug("elasticSearch发出的json为:{}",searchRequestBuilder.toString());
		//System.out.println(("elasticSearch发出的json为:"+boolQueryBuilder.toString()));
		//System.out.println(("elasticSearch发出的json为:"+searchRequestBuilder.toString()));
		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return Lists.newArrayList();
		}
		List<Long> dataList = Lists.newArrayList();
		for (SearchHit hit : hits) {
			Map<String, Object> dataEL = hit.getSource();
			Long id = Long.valueOf("" + dataEL.get("id"));
			dataList.add(id);
		}
		return dataList;
	}

	@RequestMapping(value = "searchNearNewsIdsFeginApi.do", method = RequestMethod.POST)
	public List<Long> searchNearNewsIdsFeginApi(@RequestBody HSearchDto searchDto) {
		Integer searchType = searchDto.getSearchType();// 1综合 2爆料 3视频 4 新鲜事 5 用户
													   // 6地点
		if (searchType == null) {
			return Lists.newArrayList();
		}
		String distance = (searchDto.getDistance() == null ? "100000" : searchDto.getDistance()) + "m";
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 0));// 0 新闻 1
																	   // topic
																	   // 2 post
		boolQueryBuilder.must(QueryBuilders.termQuery("contentType", searchDto.getContentType()));// 2图文
																								  // 5图集
																								  // 6视频

		GeoDistanceQueryBuilder distanceBuilder = new GeoDistanceQueryBuilder("geolocation")
				.point(searchDto.getLatitude(), searchDto.getLongitude()).distance(distance, DistanceUnit.METERS)
				.geoDistance(GeoDistance.ARC);

		int pageNo = searchDto.getPageNo() == null ? 1 : searchDto.getPageNo();
		int pageSize = searchDto.getPageSize() == null ? 10 : searchDto.getPageSize();

		int from = (pageNo - 1) * pageSize;
		SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESContent.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder).setQuery(distanceBuilder).setFrom(from).setSize(pageSize).execute()
				.actionGet();
		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return Lists.newArrayList();
		}
		List<Long> dataList = Lists.newArrayList();
		for (SearchHit hit : hits) {
			Map<String, Object> dataEL = hit.getSource();
			Long id = Long.valueOf("" + dataEL.get("id"));
			dataList.add(id);
		}
		return dataList;
	}

	@RequestMapping(value = "searchNewsFeginApi.do", method = RequestMethod.POST)
	public List<Map<String, Object>> searchNewsFeginApi(@RequestBody HSearchDto searchDto) {
		Integer searchType = searchDto.getSearchType();// 1综合 2爆料 3视频 4 新鲜事 5 用户
													   // 6地点
		if (searchType == null) {
			return Lists.newArrayList();
		}
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 0));
	
	                                                                   // 0 新闻 1
																	   // topic
																	   // 2 post
		int pageNo = searchDto.getPageNo() == null ? 1 : searchDto.getPageNo();
		int pageSize = searchDto.getPageSize() == null ? 10 : searchDto.getPageSize();

		int from = (pageNo - 1) * pageSize;
		SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESContent.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder).setFrom(from).setSize(pageSize).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return Lists.newArrayList();
		}
		List<Map<String, Object>> dataList = Lists.newArrayList();
		for (SearchHit hit : hits) {
			Map<String, Object> dataEL = hit.getSource();
			dataList.add(dataEL);
		}
		return dataList;
	}
	
	
/*	
	@RequestMapping(value = "searchTopicFeginApi.do", method = RequestMethod.POST)
	public List<Map<String, Object>> searchTopicFeginApi(@RequestBody HSearchDto searchDto) {
		Integer searchType = searchDto.getSearchType();// 1综合 2爆料 3视频 4 新鲜事 5 用户
													   // 6地点
		if (searchType == null) {
			return Lists.newArrayList();
		}
		String keywords=searchDto.getKeywords();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 1))
						.should(QueryBuilders.matchPhraseQuery("title", keywords))
						.should(QueryBuilders.matchPhraseQuery("shortTitle", keywords))
						.should(QueryBuilders.matchPhraseQuery("summary", keywords))
						.should(QueryBuilders.matchPhraseQuery("content", keywords))
						.should(QueryBuilders.matchPhraseQuery("keywords", keywords)).minimumShouldMatch(1);
		
				
	
	                                                                   // 0 新闻 1
																	   // topic
																	   // 2 post
		int pageNo = searchDto.getPageNo() == null ? 1 : searchDto.getPageNo();
		int pageSize = searchDto.getPageSize() == null ? 10 : searchDto.getPageSize();

		int from = (pageNo - 1) * pageSize;
		SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESContent.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder).setFrom(from).setSize(pageSize).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return Lists.newArrayList();
		}
		List<Map<String, Object>> dataList = Lists.newArrayList();
		for (SearchHit hit : hits) {
			Map<String, Object> dataEL = hit.getSource();
			dataList.add(dataEL);
		}
		return dataList;
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@RequestMapping(value = "searchContentIdFeginApi.do", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> searchContentIdFeginApi(@RequestParam("contentId") Long contentId) {
		if (contentId == null) {
			return Maps.newHashMap();
		}
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.termQuery("id", contentId));//
		boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 0));// 0 新闻 1
																	   // topic
																	   // 2 post

		SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESContent.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return Maps.newHashMap();
		}

		if (hits.totalHits > 0) {
			return hits.getAt(0).getSource();
		}

		return Maps.newHashMap();
	}

	@RequestMapping(value = "searchIdsIncontentFeginApi.do", method = RequestMethod.POST)
	public List<Long> searchIdsIncontentFeginApi(@RequestBody HSearchDto searchDto) {

		Integer searchType = searchDto.getSearchType();// 1综合 2爆料 3视频 4 新鲜事 5 用户
													   // 6地点
		String keywords = searchDto.getKeywords();
		if (searchType == null) {
			return Lists.newArrayList();
		}
		if (!StringUtils.hasText(keywords)) {
			return Lists.newArrayList();
		}
		// String[] fields = {"id"};
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.matchPhraseQuery("title", keywords))
				.should(QueryBuilders.matchPhraseQuery("shortTitle", keywords))
				.should(QueryBuilders.matchPhraseQuery("summary", keywords))
				.should(QueryBuilders.matchPhraseQuery("content", keywords))
				.should(QueryBuilders.matchPhraseQuery("keywords", keywords)).minimumShouldMatch(1);
		if (1 == searchType.intValue()) {

			boolQueryBuilder.must(QueryBuilders.termQuery("auditStatus", 1));
			boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 0));// 0
																		   // 新闻
																		   // 1
																		   // topic
																		   // 2
																		   // post

		} else if (3 == searchType.intValue()) {// 视频

			boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 0));// 0
																		   // 新闻
																		   // 1
																		   // topic
																		   // 2
																		   // post
			boolQueryBuilder.must(QueryBuilders.termQuery("contentType", 6));// 2图文
																			 // 5图集
																			 // 6视频
			boolQueryBuilder.must(QueryBuilders.termQuery("auditStatus", 1));

		} else if (2 == searchType.intValue()) {// 2爆料

			boolQueryBuilder.must(QueryBuilders.termQuery("topicType", 1));// 0新鲜事
																		   // ，
																		   // 1爆料
																		   // 2
																		   // 评论
			boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 1));// 0
																		   // 新闻
																		   // 1
																		   // topic
																		   // 2
																		   // post
			boolQueryBuilder.must(QueryBuilders.termQuery("publi", 1));
			boolQueryBuilder.must(QueryBuilders.termQuery("status", 1));
			boolQueryBuilder.must(QueryBuilders.termQuery("showStatus", 1));

		} else if (4 == searchType.intValue()) {// 4 新鲜事
			boolQueryBuilder.must(QueryBuilders.termQuery("plateType", 1));// 0
																		   // 新闻
																		   // 1
																		   // topic
																		   // 2
																		   // post
			boolQueryBuilder.must(QueryBuilders.termQuery("publi", 1));
			boolQueryBuilder.must(QueryBuilders.termQuery("status", 1));
			boolQueryBuilder.must(QueryBuilders.termQuery("showStatus", 1));
		}
		
		//排序！！！！
		SortBuilder<?> fieldSortBuilder = SortBuilders.fieldSort("createTime");
		if (searchDto.getOrderBy() == null || searchDto.getOrderBy().intValue() == 1) {// 1为按时间排序，2为按位置排序

			fieldSortBuilder = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);

		} else if (searchDto.getOrderBy() != null && searchDto.getOrderBy().intValue() == 2
				&& searchDto.getLatitude() != null && searchDto.getLongitude() != null) {

			fieldSortBuilder = SortBuilders
					.geoDistanceSort("geolocation", new GeoPoint(searchDto.getLatitude(), searchDto.getLongitude()))
					.order(SortOrder.ASC);

		} else {
			fieldSortBuilder = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
		}

		int pageNo = searchDto.getPageNo() == null ? 1 : searchDto.getPageNo();
		int pageSize = searchDto.getPageSize() == null ? 10 : searchDto.getPageSize();

		int from = (pageNo - 1) * pageSize;

		SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESContent.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder).addSort(fieldSortBuilder).setFrom(from).setSize(pageSize).execute()
				.actionGet();

		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return Lists.newArrayList();
		}
		List<Long> dataList = Lists.newArrayList();
		for (SearchHit hit : hits) {
			Map<String, Object> dataEL = hit.getSource();
			Long id = Long.valueOf("" + dataEL.get("id"));
			// 去掉重复
			if (!dataList.contains(id)) {
				dataList.add(id);
			}

		}
		return dataList;
	}

	@RequestMapping(value = "createContentFeginApi.do", method = RequestMethod.POST)
	public R createContentFeginApi(@RequestBody ESContent esContent) {
		try {
			if (esContent.getCreateTimeLong() != null) {
				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = dFormat.parse(dFormat.format(esContent.getCreateTimeLong()));
				esContent.setCreateTime(date);
			}
			if (esContent.getReleaseTimeLong() != null) {
				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = dFormat.parse(dFormat.format(esContent.getReleaseTimeLong()));
				esContent.setReleaseTime(date);
			}
			String _id = esContent.getPlateType() + "_" + esContent.getId();
			IndexResponse indexResponse = elasticService.indexDocument(ElasticTypes.ESContent, String.valueOf(_id),
					ElasticUtil.toDocument(esContent));
			Result res = indexResponse.getResult();
			if (!(res.getLowercase().equals("created") || res.getLowercase().equals("updated"))) {
				throw new HServiceLogicException("新增失败：" + indexResponse.getResult().toString());
			}

		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		return R.ok();

	}

	@RequestMapping(value = "updateContentFeginApi.do", method = RequestMethod.POST)
	public R updateContentFeginApi(@RequestBody ESContent esContent) {
		try {
			String _id = esContent.getPlateType() + "_" + esContent.getId();

			UpdateResponse updateResponse = elasticService.updateDocument(ElasticTypes.ESContent, String.valueOf(_id),
					ElasticUtil.toDocument(esContent));
			if (!updateResponse.getResult().equals(Result.UPDATED)) {
				throw new HServiceLogicException("更新失败：" + updateResponse.getResult().toString());
			}
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		return R.ok();
	}

	@RequestMapping(value = "updateContentFykFeginApi.do", method = RequestMethod.POST)
	public R updateContentFykFeginApi(@RequestBody ESContentDto esContentDto) {
		try {
			String _id = esContentDto.getPlateType() + "_" + esContentDto.getId();
			ESContent esContent = new ESContent();
			BeanUtils.copyProperties(esContent, esContentDto);
			esContent.setCreateTime(new Date(esContentDto.getCreateTimeFyk()));
			esContent.setReleaseTime(new Date(esContentDto.getReleaseTimeFyk()));
			UpdateResponse updateResponse = elasticService.updateDocument(ElasticTypes.ESContent, String.valueOf(_id),
					ElasticUtil.toDocument(esContent));
			if (!updateResponse.getResult().equals(Result.UPDATED)) {
				throw new HServiceLogicException("更新失败：" + updateResponse.getResult().toString());
			}
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		return R.ok();
	}

	@RequestMapping(value = "clearTypeFeginApi.do", method = RequestMethod.POST)
	public R clearTypeFeginApi(String type) {
		try {
			long totle = elasticService.clearType(type);
			return R.ok().put("totle", totle);
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
	}

}
