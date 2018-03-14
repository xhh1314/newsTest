package com.hww.els.webservice.controller;

import com.google.common.collect.Lists;
import com.hww.base.util.R;
import com.hww.els.common.HSearchDto;
import com.hww.els.common.entity.ESMemberLoc;
import com.hww.els.common.enums.ElasticTypes;
import com.hww.els.common.util.ElasticUtil;
import com.hww.els.webservice.config.ElasticsearchConfiguration;
import com.hww.els.webservice.service.ElasticService;
import com.hww.framework.common.exception.HServiceLogicException;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/es/memberLoc")
public class ESMemberLocContorller {

    @Autowired
    ElasticsearchConfiguration elasticsearchConfiguration;

    @Autowired
    ElasticService elasticService;

    @RequestMapping(value = "searchNearByMemberIdsFeginApi.do", method = RequestMethod.POST)
    public List<Long> searchNearByMemberIdsFeginApi(@RequestBody HSearchDto searchDto) {
    	
    	if(searchDto.getLongitude()==null||searchDto.getLatitude()==null) {
    		return Lists.newArrayList();
    	}
    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
  
    	CollapseBuilder collapseBuilder = new CollapseBuilder("memberId");
    			
    	Integer locationType=searchDto.getSearchType();
    	//1 附近的人 2 签到
    	if(locationType!=null) {
    		boolQueryBuilder.must(QueryBuilders.termQuery("locationType", locationType));
    	}
    	GeoDistanceSortBuilder distanceSortBuilder=SortBuilders.geoDistanceSort("geolocation", new GeoPoint(searchDto.getLatitude(), searchDto.getLongitude()));
        SortBuilder<?>  locTimeFieldSortBuilder = SortBuilders.fieldSort("locTime").order(SortOrder.DESC);

        int pageNo = searchDto.getPageNo() == null ? 1 : searchDto.getPageNo();
        int pageSize = searchDto.getPageSize() == null ? 10 : searchDto.getPageSize();

        int from = (pageNo - 1) * pageSize;

        SearchResponse searchResponse = elasticService.getClient().prepareSearch("es_hww").setTypes(ElasticTypes.ESMemberLoc.getTypeName())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .addSort(locTimeFieldSortBuilder)
                .addSort(distanceSortBuilder)
                .setCollapse(collapseBuilder)//按照字段折叠
                .setFrom(from)
                .setSize(pageSize)
                .execute().actionGet();

        SearchHits hits = searchResponse.getHits();
        if (hits == null || hits.totalHits <= 0) {
            return Lists.newArrayList();
        }
        List<Long> dataList = Lists.newArrayList();
        for (SearchHit hit : hits) {
            Map<String, Object> dataEL = hit.getSource();
            Long id = Long.valueOf("" + dataEL.get("memberId"));
            dataList.add(id);
        }
        return dataList;
    }

    @RequestMapping(value = "createMemberLocFeginApi.do", method = RequestMethod.POST)
    public R createMemberLocFeginApi(@RequestBody ESMemberLoc memberLoc) {
        try {
        	if(memberLoc.getLocTimeStamp()!=null){
        		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		Date date = dFormat.parse(dFormat.format(memberLoc.getLocTimeStamp()));
        		memberLoc.setLocTime(date);
        	}
            IndexResponse indexResponse = elasticService.indexDocument(ElasticTypes.ESMemberLoc, ElasticUtil.toDocument(memberLoc));
            Result res=indexResponse.getResult();
            if(!(res.getLowercase().equals("created")||res.getLowercase().equals("updated"))) {
            	throw new HServiceLogicException("新增失败：" + indexResponse.getResult().toString());
            }
            
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return R.ok();

    }



}
