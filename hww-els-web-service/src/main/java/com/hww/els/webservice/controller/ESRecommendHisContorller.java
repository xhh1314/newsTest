package com.hww.els.webservice.controller;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.hww.base.util.R;
import com.hww.els.common.HSearchDto;
import com.hww.els.common.entity.ESRecommendHis;
import com.hww.els.common.enums.ElasticTypes;
import com.hww.els.common.util.ElasticUtil;
import com.hww.els.webservice.config.ElasticsearchConfiguration;
import com.hww.els.webservice.service.ElasticService;

@RestController
@RequestMapping("/es/search/recommhis")
public class ESRecommendHisContorller {

	@Autowired
	ElasticsearchConfiguration elasticsearchConfiguration;

	@Autowired
	ElasticService elasticService;

	@RequestMapping(value = "/createRecommHisFeginApi.do", method = RequestMethod.POST)
	public R createRecommHisFeginApi(@RequestBody List<ESRecommendHis> recommendHisList) {
		try {
			List<Map<String, Object>> dataSource = Lists.newArrayList();
			for (ESRecommendHis his : recommendHisList) {
				Map<String, Object> data = ElasticUtil.toDocument(his);
				data.put("_xid", his.getPlateType() + "_" + his.getHisid());
				dataSource.add(data);
			}
			BulkResponse bulkResponse = elasticService.bulkIndex(ElasticTypes.ESRecommendHis, dataSource);
			boolean hasfailures = bulkResponse.hasFailures();
			if (hasfailures) {
				return R.error("" + hasfailures);
			}
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
		return R.ok();
	}

	@RequestMapping(value = "/searchRecommHisFeginApi.do", method = RequestMethod.POST)
	public List<Long> searchRecommHisIdsFeginApi(@RequestBody HSearchDto searchDto) {
		String imei = searchDto.getImei();
		Long memberId = searchDto.getMemberId();
		if (memberId == null && !StringUtils.hasText(imei)) {
			return Lists.newArrayList();
		}
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if (memberId != null) {
			boolQueryBuilder.must(QueryBuilders.termQuery("memberId", memberId));
		} else if (StringUtils.hasText(imei)) {
			boolQueryBuilder.must(QueryBuilders.termQuery("imei", imei));
		}
		if (searchDto.getPlateType() != null) {
			boolQueryBuilder.must(QueryBuilders.termQuery("plateType", searchDto.getPlateType()));
		}
		SortBuilder<?> fieldSortBuilder = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
		int pageNo = (searchDto.getPageNo() == null || searchDto.getPageNo() < 0) ? 1 : searchDto.getPageNo();
		int pageSize = (searchDto.getPageSize() == null || searchDto.getPageSize() < 0) ? 10 : searchDto.getPageSize();

		int from = (pageNo - 1) * pageSize;

		SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESRecommendHis.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder).addSort(fieldSortBuilder).setFrom(from).setSize(pageSize).execute()
				.actionGet();

		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return Lists.newArrayList();
		}
		List<Long> dataList = Lists.newArrayList();
		for (SearchHit hit : hits) {
			Map<String, Object> dataEL = hit.getSource();
			Long id = Long.valueOf("" + dataEL.get("contentId"));
			dataList.add(id);
		}
		return dataList;
	}

	@RequestMapping(value = "/searchRecommHisByPageFeginApi.do", method = RequestMethod.POST)
	public List<ESRecommendHis> searchRecommHisByPageFeginApi(@RequestBody HSearchDto searchDto) {
		String imei = searchDto.getImei();
		Long memberId = searchDto.getMemberId();

		List<Long> hisids = searchDto.getHisids();

		if (memberId == null && !StringUtils.hasText(imei)) {
			return Lists.newArrayList();
		}
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if (memberId != null) {
			boolQueryBuilder.must(QueryBuilders.termQuery("memberId", memberId));
		} else if (StringUtils.hasText(imei)) {
			boolQueryBuilder.must(QueryBuilders.termQuery("imei", imei));
		}
		// 当前屏幕数据不再加载
		if (hisids != null && !hisids.isEmpty()) {
			for (Long id : hisids) {
				boolQueryBuilder.mustNot(QueryBuilders.termQuery("contentId", id));
			}
		}

		SortBuilder<?> fieldSortBuilder = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
		int pageNo = (searchDto.getPageNo() == null || searchDto.getPageNo() < 0) ? 1 : searchDto.getPageNo();
		int pageSize = (searchDto.getPageSize() == null || searchDto.getPageSize() < 0) ? 10 : searchDto.getPageSize();

		int from = (pageNo - 1) * pageSize;

		SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww")
				.setTypes(ElasticTypes.ESRecommendHis.getTypeName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder).addSort(fieldSortBuilder).setFrom(from).setSize(pageSize).execute()
				.actionGet();

		SearchHits hits = searchResponse.getHits();
		if (hits == null || hits.totalHits <= 0) {
			return Lists.newArrayList();
		}
		List<ESRecommendHis> dataList = Lists.newArrayList();
		for (SearchHit hit : hits) {
			Map<String, Object> dataEL = hit.getSource();
			Long memberIdx = null;
			if (dataEL.get("memberId") != null
					&& com.hww.base.util.StringUtils.isNumeric(dataEL.get("memberId").toString())) {
				memberIdx = Long.valueOf(dataEL.get("memberId").toString());
			}
			String imeix = (String) dataEL.get("imei");
			Integer type = (Integer) dataEL.get("type");
			Long contentId = Long.valueOf("" + dataEL.get("contentId"));
			Integer plateType = (Integer) dataEL.get("plateType");
			// (Long memberId, String imei, Integer type, Long contentId,
			// Integer plateType)
			ESRecommendHis his = new ESRecommendHis(memberIdx, imeix, type, contentId, plateType);
			// Long id = Long.valueOf("" + dataEL.get("contentId"));
			dataList.add(his);
		}
		return dataList;
	}

}
