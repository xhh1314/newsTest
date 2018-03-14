package com.hww.els.common.util;

import com.hww.base.util.NumberUtils;
import com.hww.els.common.entity.ESContent;
import com.hww.els.common.entity.ESMemberLoc;
import com.hww.els.common.entity.ESRecommendHis;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ElasticUtil {
	
	 public static Map<String, Object> toDocument(ESRecommendHis recommendHis) {
	        Map<String, Object> map = new HashMap<String, Object>();
	        if (recommendHis != null) {
	          map.put("hisid",recommendHis.getHisid() );
	          map.put("memberId",recommendHis.getMemberId() );
	          map.put("imei",recommendHis.getImei() );
	          map.put("type",recommendHis.getType() );
	          map.put("contentId",recommendHis.getContentId());
	          map.put("plateType", recommendHis.getPlateType());
	          map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recommendHis.getCreateTime()==null?new Date():recommendHis.getCreateTime()));
	          map.put("recommTimeStamp",recommendHis.getRecommTimeStamp()==null?new Date().getTime():recommendHis.getRecommTimeStamp() );
	        }

	        return map;
	    }
	
	 public static Map<String, Object> toDocument(ESContent esContent) {

	        Map<String, Object> map = new HashMap<String, Object>();
	        if (esContent != null) {
	          map.put("id",esContent.getId());
	          map.put("title",esContent.getTitle() == null ? "" : esContent.getTitle());
	          map.put("shortTitle", esContent.getShortTitle()== null ? "" : esContent.getShortTitle());
	          map.put("summary", esContent.getSummary()== null ? "" : esContent.getSummary());
	          map.put("content",esContent.getContent()==null?"":esContent.getContent() );
	          map.put("keyWords", esContent.getKeywords()==null?"":esContent.getKeywords());
	          map.put("auditStatus", esContent.getAuditStatus());
	          map.put("longitude", esContent.getLongitude());
	          map.put("latitude", esContent.getLatitude());
	          map.put("priority", esContent.getPriority());
	         if(esContent.getLongitude()!=null) {
	        	 map.put("longitude", esContent.getLongitude());
	         }
	         if(esContent.getLatitude()!=null) {
	        	 map.put("latitude", esContent.getLatitude());
	         }
	          if(esContent.getLongitude()!=null && esContent.getLatitude()!=null) {
//	        	  map.put("geolocation", toLocationString(esContent.getLatitude().toString(), esContent.getLongitude().toString()));
	        	  map.put("geolocation",esContent.getLatitude()+"," +esContent.getLongitude());
	          }
	          
	          map.put("address", esContent.getAddress()== null ? "" : esContent.getAddress());
	          map.put("top",esContent.getTop() == null ? "" : esContent.getTop());
	         
	          map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(esContent.getCreateTime()==null?new Date():esContent.getCreateTime()));
	          if(esContent.getReleaseTime()!=null){
	        	  map.put("releaseTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(esContent.getReleaseTime()));
	          }
	          map.put("status", esContent.getStatus());
	          map.put("memberId", esContent.getMemberId());
	          map.put("relatednewsId", esContent.getRelatednewsId());
	          map.put("contentType", esContent.getContentType());
	          map.put("plateType", esContent.getPlateType());
	          map.put("anonymous", esContent.getAnonymous());
	          map.put("topicType", esContent.getTopicType());
	          map.put("publi", esContent.getPubli());
	          map.put("showStatus", esContent.getShowStatus());
	        }

	        return map;
	    }
	 
    public static Map<String, Object> toDocument(ESMemberLoc memberLoc) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (memberLoc != null) {
        	
            map.put("memberId", memberLoc.getMemberId());
            map.put("imei", memberLoc.getImei());
           
            if (memberLoc.getLongitude() != null) {
                map.put("latitude", memberLoc.getLongitude());
            }
            if (memberLoc.getLatitude() != null) {
                map.put("latitude", memberLoc.getLatitude() );
            }
            if(memberLoc.getLongitude()!=null && memberLoc.getLatitude()!=null) {
	        	  map.put("geolocation",memberLoc.getLatitude()+"," +memberLoc.getLongitude());
	        }
            map.put("address", memberLoc.getAddress() == null ? "" : memberLoc.getAddress());
            
            map.put("locationType", memberLoc.getLocationType() == null ? 0 : memberLoc.getLocationType());

            map.put("locTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(memberLoc.getLocTime()==null?new Date():memberLoc.getLocTime()));
            map.put("locTimeStamp",memberLoc.getLocTimeStamp()==null?new Date().getTime():memberLoc.getLocTimeStamp() );
	       
        }
        return map;
    }
    

    public static String toLocationString(String lat, String lon) {
        return lat + "," + lon;
    }

    public static String toLocationString(BigDecimal lat, BigDecimal lon) {
        return lat.doubleValue() + "," + lon.doubleValue();
    }


    private static double doubleValue(Object obj) {
        if (obj instanceof String) {
            return new BigDecimal((String) obj).doubleValue();
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).doubleValue();
        } else {
            return NumberUtils.toDouble(String.valueOf(obj));
        }

    }

    private static double longValue(Object obj) {
        return ((BigDecimal) obj).longValue();
    }

//	public static DataSet searchResponse2Dataset(SearchResponse searchResponse) {
//		DataSet ds  = DataSet.newDs();
//		if(searchResponse==null||searchResponse.getHits()==null||searchResponse.getHits().getHits()==null||searchResponse.getHits().getHits().length==0){
//			return  ds;
//		}
//		SearchHits hits = searchResponse.getHits();
//		SearchHit hitx=hits.getAt(0);
//		String type=hitx.getType();
//		ds.setTotal(hits.getTotalHits());
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		Iterator<SearchHit> it  = hits.iterator();
//		while(it.hasNext()){
//			 Map<String, Object>  map=it.next().getSource();
//			 map.put("_type", type);
//			 list.add(map);
//		}
//		ds.setRows(list);
//		return ds;
//	}


    
    
    
   
    
    
    
    
    
    
    
    
    
}
